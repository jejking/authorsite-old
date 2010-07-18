/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package org.authorsite.simplemailarchive.domain;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.authorsite.simplemailarchive.domain.SimpleAttachment.AttachmentBuilder;
import org.authorsite.simplemailarchive.domain.SimpleMailMessage.SimpleMailMessageBuilder;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;

/**
 * Assembles simple mail message from a java mail message.
 *
 * @author jejking
 */
public class SimpleMailMessageFactory {

    private static final Logger logger = Logger.getLogger(SimpleMailMessageFactory.class);

    public List<SimpleMailMessage> buildSimpleMailMessages(Folder folder) throws Exception {

        List<SimpleMailMessage> messages = new LinkedList<SimpleMailMessage>();
        for (Message message : folder.getMessages()) {
            try {
                messages.add(this.buildSimpleMailMessage(message));
            } catch (Exception e) {
                logger.warn(e + folder.getFullName());
            }
        }
        Folder[] children = folder.list();
        for (Folder child : children) {
            messages.addAll(this.buildSimpleMailMessages(child));
        }
       return messages;
    }

    /**
     * Constructs a single {@link  SimpleMailMessage} object from a standard JavaMail <code>Message</code>
     *
     * @param in a standard JavaMail message object to be converted
     * @return a new corresponding <code>EmailMessage</code> object
     * @throws Exception
     */
    public SimpleMailMessage buildSimpleMailMessage(Message in) throws Exception {
        logger.debug("loading message " + in.getMessageNumber());
   
        // set all the properties on the EmailMessage
        logger.debug("loading basic properties");
        
        Date sentDate = buildSentDate(in);
        String subject = in.getSubject();
        String mesgId = buildMesgId(in); 
        String inReplyTo = handleHeader(in, "In-Reply-To");
        String mesgReferences = handleHeader(in, "References");
        String text = buildText(in);
        SimpleEmailAddress senderEmailAddress = buildSenderEmailAddress(in);
        
        List<SimpleAttachment> attachments = buildAttachments(in);
        
        SimpleMailMessageBuilder builder = new SimpleMailMessageBuilder(mesgId, senderEmailAddress, sentDate, text);
        builder.inReplyTo(inReplyTo);
        builder.mesgReferences(mesgReferences);
        builder.subject(subject);
        for (SimpleAttachment attachment : attachments) {
        	builder.attachment(attachment);
        }

        return builder.build();
    }

	

    
	private List<SimpleAttachment> buildAttachments(Message in) throws IOException, MessagingException, Exception {
		
		if (in.getContent() instanceof Multipart) {
			return this.handleParts((Multipart) in.getContent());
		} else {
			return Collections.emptyList();
		}
	}

	private SimpleEmailAddress buildSenderEmailAddress(Message in) throws MessagingException {
		
		SimpleEmailAddress simpleEmailAddress = null;

        try {
            Address[] fromAddresses = in.getFrom();
            if (fromAddresses != null && fromAddresses.length > 0) {
                Address basicFrom = fromAddresses[0]; // assume first is the relevant one
                if (basicFrom instanceof InternetAddress) {
                    InternetAddress from = (InternetAddress) basicFrom;
                    simpleEmailAddress = new SimpleEmailAddress(from.getAddress(), from.getPersonal());
                }

            }
        } catch (AddressException ae) {
            InternetAddress[] newAttempt = null;
            String[] froms = in.getHeader("From");
            String fromString = froms[0];
            String[] bits = fromString.split(" ");
            if (ae.getMessage().contains("Missing '\"'")) {
                fromString = "\"" + fromString;
                newAttempt = InternetAddress.parse(fromString);
            }
            if (ae.getMessage().contains("Illegal whitespace")) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bits.length - 1; i++) {
                    builder.append(bits[i]);
                    builder.append(" ");
                }
                builder.append("<");
                builder.append(bits[bits.length - 1]);
                builder.append(">");
                newAttempt = InternetAddress.parse(builder.toString());
            }
            if (newAttempt != null) {
                simpleEmailAddress = new SimpleEmailAddress(newAttempt[0].getAddress(), newAttempt[0].getPersonal());
                
            } else {
                System.err.println(ae + " in " + in.getFolder().getName());
            }
        }
           
        return simpleEmailAddress;
	}

	private String buildMesgId(Message in) throws Exception {
     	
    	String mesgId = null;
    	this.handleHeader(in, "Message-Id");
        if (mesgId == null) {
            // oops, got lost in the process somwhere
            mesgId = UUID.randomUUID().toString();
        }
        return mesgId;
	}

	private Date buildSentDate(Message in) throws MessagingException {
    	
    	Date sentDate = null;
    	
    	if (in.getSentDate() != null) {
            sentDate = in.getSentDate();
        }
        else if (in.getReceivedDate() != null) {
            sentDate = in.getReceivedDate();
        } else {
        	logger.warn("Could not resolve sent date, using now instead");
        	sentDate = new Date();
        }
    	
    	return sentDate;
	}

	/**
     * @param message
     * @param in
     * @param string
     * @throws MessagingException
     */
    private String handleHeader(Message in, String header) throws Exception {
        String[] headerValue = null;
        String propValue = null;
        headerValue = in.getHeader(header);
        if (headerValue != null) {
            if (headerValue.length == 1) {
                propValue = headerValue[0];
            }
            else {
                StringBuilder buffer = new StringBuilder();
                for (int i = 0; i < headerValue.length; i++) {
                    if (i > 0) {
                        buffer.append("; ");
                    }
                    buffer.append(headerValue[i]);
                }
                propValue = buffer.toString();
            }
        }
        return propValue;
    }
    
    /**
     * @param object
     */
    private List<SimpleAttachment> handleParts(Multipart multipart) throws Exception {
    	
    	List<SimpleAttachment> attachments = new ArrayList<SimpleAttachment>();
        logger.debug("handling multipart content");
        int partCount;
        partCount = multipart.getCount();
        for (int i = 0; i < partCount; i++) {
            BodyPart bp = multipart.getBodyPart(i);
            if (bp.getDisposition() != null && bp.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                attachments.add(handleBinaryAttachment(bp));
            }
        }
        return attachments;
    }

    /**
     * @param message
     * @param bp
     */
    private SimpleAttachment handleBinaryAttachment(BodyPart bp) throws Exception {
        logger.debug("handling binary attachment");
        
        InputStream input = bp.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024]; 
        int len; 
        while ((len = input.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
        input.close();
        output.close(); // yes, i know this doesn't really do anything

        byte[] contents = output.toByteArray();
        
        return new AttachmentBuilder(contents, bp.getFileName() != null ? bp.getFileName() : "file", bp.getContentType())
        										.description(bp.getDescription())
        										.disposition(bp.getDisposition())
        										.build();

    }

        /**
     * @param message
     * @param in
     * @return plain text representation of message body
     */
    private String buildText(Message in) throws Exception {
        logger.debug("handling Message Body for text content");
        
        String text = null;
        
        Object o;
        o = in.getContent();
        if (o instanceof MimeMultipart) {
            logger.debug("handling a Multipart body part 0");
            MimeMultipart mp = (MimeMultipart) o;
            if (mp.getContentType().startsWith("multipart/alternative")) {
                int count = mp.getCount();
                for (int i = 0; i < count; i++) {
                    BodyPart bp = mp.getBodyPart(i);
                    if (bp.getContentType().startsWith("text/plain")) {
                        text = bp.getContent().toString();
                    }
                    
                }
            } else {
                text = ("Could not find plain text in multipart...");
            }
            
        } else if (o instanceof String) { // should be just a string
            text = (String) o;
        }
        if (text != null && text.startsWith("<")) {
            // strip HTML
            Parser htmlParser = new Parser(text);
            TextExtractingVisitor visitor = new TextExtractingVisitor();
            htmlParser.visitAllNodesWith(visitor);
            String textInPage = visitor.getExtractedText();
            text = textInPage;
        }
        // format text to 80 chars width..
        if (text != null) {
            text = (SimpleMailMessageFactory.formatLines(text, 80));
        }
     
        return text;
    }

    

    public static String formatLines(String target, int maxLength) {

        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        BreakIterator boundary =
                BreakIterator.getLineInstance(Locale.UK);
        boundary.setText(target);
        int start = boundary.first();
        int end = boundary.next();
        int lineLength = 0;

        while (end != BreakIterator.DONE) {
            String word = target.substring(start, end);
            lineLength = lineLength + word.length();
            if (lineLength >= maxLength) {
                out.println();
                lineLength = word.length();
            }
            out.print(word);
            start = end;
            end = boundary.next();
        }

        return writer.toString();
    }
}
