package org.authorsite.maildb;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.BreakIterator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import sun.tools.tree.LengthExpression;

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
                System.err.println(e + folder.getFullName());
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
     * @throws IOException
     * @throws MessagingException
     */
    public SimpleMailMessage buildSimpleMailMessage(Message in) throws Exception {
        logger.debug("loading message " + in.getMessageNumber());
        SimpleMailMessage message = new SimpleMailMessage();


        // set all the properties on the EmailMessage
        logger.debug("loading basic properties");
        if (in.getSentDate() != null) {
            message.setDateSent(in.getSentDate());
        }
        else {
            message.setDateSent(in.getReceivedDate());
        }


        message.setSubject(in.getSubject());
        
        message.setMesgId(handleHeader(message, in, "Message-Id"));
        if (message.getMesgId() == null) {
            // oops, got lost in the process somwhere
            message.setMesgId(UUID.randomUUID().toString());
        }
        message.setInReplyTo(handleHeader(message, in, "In-Reply-To"));
        message.setMesgReferences(handleHeader(message, in, "References"));


        // deal with main body of mail
        // need to get content.
        handleMessageBody(message, in);

        if (in.getContent() instanceof Multipart) {
            handleParts(message, (Multipart) in.getContent());
        }

        // set sender data

        try {
            Address[] fromAddresses = in.getFrom();
            if (fromAddresses != null && fromAddresses.length > 0) {
                Address basicFrom = fromAddresses[0]; // assume first is the relevant one
                if (basicFrom instanceof InternetAddress) {
                    InternetAddress from = (InternetAddress) basicFrom;
                    message.setSenderName(from.getPersonal());
                    message.setSenderAddress(from.getAddress());
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
                message.setSenderAddress(newAttempt[0].getAddress());
                message.setSenderName(newAttempt[0].getPersonal());
            } else {
                System.err.println(ae + " in " + in.getFolder().getName());
            }
            
        }

        return message;
    }


    /**
     * @param message
     * @param in
     * @param string
     * @throws MessagingException
     */
    private String handleHeader(SimpleMailMessage message, Message in, String header) throws Exception {
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
     * @param message
     * @param object
     */
    private void handleParts(SimpleMailMessage message, Multipart multipart) throws Exception {
        logger.debug("handling multipart content");
        int partCount;
        partCount = multipart.getCount();
        for (int i = 0; i < partCount; i++) {
            BodyPart bp = multipart.getBodyPart(i);
            if (bp.getDisposition() != null && bp.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                handleBinaryAttachment(message, bp);
            }
        }
    }

    /**
     * @param message
     * @param bp
     */
    private void handleBinaryAttachment(SimpleMailMessage message, BodyPart bp) throws Exception {
        logger.debug("handling binary attachment");
        Attachment binContent = new Attachment(message);

        InputStream input = bp.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024]; 
        int len; 
        while ((len = input.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
        input.close();
        output.close(); // yes, i know this doesn't really do anything

        binContent.setContents(output.toByteArray());
        binContent.setSize(bp.getSize());
        binContent.setDescription(bp.getDescription());
        binContent.setDisposition(bp.getDisposition());
        if (bp.getFileName() != null) {
            binContent.setFileName(bp.getFileName());
        }
        else {
            binContent.setFileName("file");
        }
        
        binContent.setMimeType(bp.getContentType());

        message.addAttachment(binContent);
    }

    /**
     * @param message
     * @param bp
     */
    private void handleStringAttachment(SimpleMailMessage message, BodyPart bp) throws Exception {
        logger.debug("handling text attachment");
        Attachment attachment = new Attachment(message);

        attachment.setContents(bp.getContent().toString().getBytes("UTF-8"));
        attachment.setDescription(bp.getDescription());
        attachment.setDisposition(bp.getDisposition());
        attachment.setFileName(bp.getFileName());
        attachment.setSize(attachment.getContents().length);
        attachment.setMimeType(bp.getContentType());
        message.addAttachment(attachment);

    }

    /**
     * @param message
     * @param in
     */
    private void handleMessageBody(SimpleMailMessage message, Message in) throws Exception {
        logger.debug("handling Message Body for text content");
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
                        message.setText(bp.getContent().toString());
                    }
                    
                }
            } else {
                message.setText("Could not find plain text in multipart...");
            }
            
        } else if (o instanceof String) { // should be just a string
            message.setText(in.getContent().toString());
        }
        if (message.getText() != null && message.getText().startsWith("<")) {
            // strip HTML
            Parser htmlParser = new Parser(message.getText());
            TextExtractingVisitor visitor = new TextExtractingVisitor();
            htmlParser.visitAllNodesWith(visitor);
            String textInPage = visitor.getExtractedText();
            message.setText(textInPage);
        }
        // format text to 80 chars width..
        if (message.getText() != null) {
            message.setText(SimpleMailMessageFactory.formatLines(message.getText(), 80));
        }
        
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
