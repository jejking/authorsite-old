/*
 * MailFolderLoader.java, created on 20-Jun-2004 at 17:47:17
 * 
 * Copyright John King, 2004.
 *
 *  MailFolderLoader.java is part of authorsite.org's MailArchive program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.services.loader;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.commons.io.*;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.model.impl.*;
import org.authorsite.mailarchive.services.storage.*;

/**
 * Builder class which assembles MailArchive <code>EmailMessage</code>
 * objects from JavaMail messages.
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class EmailMessageBuilder {
    
    private static Logger logger = Logger.getLogger(EmailMessageBuilder.class);
    
    private EmailAddressDAO addressDAO;
    private EmailMessageDAO messageDAO;

    public EmailMessageBuilder(EmailAddressDAO addressDAO, EmailMessageDAO messageDAO) {
        super();
        setEmailAddressDAO(addressDAO);
        setEmailMessageDAO(messageDAO);
    }
    
    /**
     * 
     */
    public EmailMessageBuilder() {
        super();
    }

    public void setEmailAddressDAO(EmailAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
    
    public void setEmailMessageDAO(EmailMessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    
        
    /**
     * Constructs a <code>List</code> of <code>EmailMessage</code> objects from a 
     * <code>Folder</code> of standard JavaMail message objects.
     * 
     * @param folder JavaMail folder containing messages to be converted
     * @param recursive boolean flag. If <code>true</code>, then recursively loads messages contained
     * in sub-folders into the <code>List</code> to be returned. If <code>false</code> then just loads
     * the messages in the top level of the <code>Folder</code>.
     * @return a <code>List</code> of <code>EmailMessage</code> objects. Note that the authorstie.org
     * domain model does not currently support any notion of folders so all hierarchy is flattened out.
     * @throws IOException
     * @throws MessagingException
     */
    public List loadFolder(Folder folder, boolean recursive) throws ArchiveStorageException {
        List list = new LinkedList();
        list = loadFolder(folder, list, recursive);
        return list;
    }
    
    /**
     * Constructs a single <code>EmailMessage</code> object from a standard JavaMail <code>Message</code>
     * 
     * @param in a standard JavaMail message object to be converted
     * @return a new corresponding <code>EmailMessage</code> object
     * @throws IOException
     * @throws MessagingException
     */
    public EmailMessage loadMessage(Message in) throws ArchiveStorageException {
        logger.debug("loading message " + in.getMessageNumber());
        EmailMessage message = new EmailMessageImpl();
        
        try {
            // set all the properties on the EmailMessage
            logger.debug("loading basic properties");
            message.setSentDate(in.getSentDate());
            
            // we need to do this double check because a lot of implementations return null on a getReceivedDate call
            Date receivedDate = in.getReceivedDate();
            if (receivedDate != null) {
                message.setReceivedDate(in.getReceivedDate());
            }
            else {
                message.setReceivedDate(handleReceivedDate(in));
            }
            
            message.setSubject(in.getSubject());
            message.setMsgID(handleHeader(message, in, "Message-Id"));
            message.setInReplyTo(handleHeader(message, in, "In-Reply-To"));
            message.setMsgReferences(handleHeader(message, in, "References"));
            message.setImportance(handleHeader(message, in, "Importance"));
            message.setSensitivity(handleHeader(message, in, "Sensitivity"));
            
            // deal with main body of mail
            // need to get content. 
            handleMessageBody(message, in);
            
            if (in.getContent() instanceof Multipart) {
                handleParts(message, (Multipart) in.getContent());
            }
            
            // work through sender, to, from, reply-to, cc, bcc
            handleAddresses(message, in);
            
            // handle constructing the full headers
            handleFullHeaders(message, in);
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        // persist the message
        messageDAO.saveMessage(message);
        return message;
    }
    
    /**
     * Looks at all the "Received" headers, finds the earliest one, turns it into a java.util.Date
     * 
     * @param in
     * @return
     * @throws MessagingException
     */
    private Date handleReceivedDate(Message in) throws MessagingException {
        String[] recvHeaders = in.getHeader("Received");
        Date latestDate =  null;
        if (recvHeaders == null) { // i.e there really aren't any, see if there's a Date header (rfc 822 specifies this as original date)
            recvHeaders = in.getHeader("Date");
            if (recvHeaders != null && recvHeaders.length == 1) { // i.e we expect only one of them to have been inserted
                try {
                    MailDateFormat format = new MailDateFormat();
                    ParsePosition pp = new ParsePosition(0);
                    latestDate = format.parse(recvHeaders[0], pp);
                }
                catch (Exception e) {
                    logger.warn(e);
                }
            }
            else { // at this stage there is no obvious information to fall back on, return the current date and time
                latestDate = new Date();
            }
        }
        else {
            for (int i = 0; i < recvHeaders.length; i++) {
                try { // wrap in try block in case the header is broken...
                    StringTokenizer tokenizer = new StringTokenizer(recvHeaders[i], ";"); // as far as I can see from rfc 2821, datestamps are split off with a ; in Received headers
                    tokenizer.nextToken(); // erm, discard this...
                    String timeStampData = tokenizer.nextToken();
                    if (timeStampData== null || timeStampData.length() == 0) {
                        logger.warn("Null or zero length datestamp in received header in message " + in.getMessageNumber());
                        continue;
                    }
                    MailDateFormat format = new MailDateFormat();
                    ParsePosition pp = new ParsePosition(0);
                    Date currentDate = format.parse(timeStampData.trim(), pp);
                    if (latestDate == null) {
                        latestDate = currentDate;
                    }
                    else if (currentDate.after(latestDate)) {
                        latestDate = currentDate; // swap round.
                    }
                }
                catch (Exception e) { // such as there being no more tokens because the header was broken
                    logger.warn(e);
                    continue;
                }
            }
        }
        // at this stage we should have examined all the Received headers, found the latest one
        // and assigned that to latestDate, which we now return. Else we'll have seen if there is a Date header, or else 
        // resorted to the current date and time
        
        return latestDate;
    }

    private List loadFolder(Folder folder, List list, boolean recursive) throws ArchiveStorageException {
        logger.debug("loading folder" + folder.getFullName());
        try {
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                list.add(loadMessage(messages[i]));
            }
            if (recursive) {
                Folder[] children = folder.list();
                if (children.length > 0) {
                    for (int i = 0; i < children.length; i++) {
                        loadFolder(children[i], list, true);
                    }
                }
            }
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ArchiveStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Constructs the TextContent with the TextContentRole.HEADERS role
     * @param message
     * @param in
     * @throws MessagingException
     */
    private void handleFullHeaders(EmailMessage message, Message in) throws MessagingException {
        logger.debug("handling full headers");
        StringBuffer textContentBuffer = new StringBuffer();
        Enumeration headers = in.getAllHeaders();
        while (headers.hasMoreElements()) {
            Header header = (Header) headers.nextElement();
            textContentBuffer.append(header.getName() + ": " + header.getValue());
            if (headers.hasMoreElements()) {
                textContentBuffer.append("\n");
            }
        }
        TextContent headersContent = new TextContentImpl();
        headersContent.setContent(textContentBuffer.toString());
        headersContent.setRole(TextContentRole.HEADERS);
        message.addTextPart(headersContent);
    }
    
    /**
     * @param message
     * @param object
     */
    private void handleParts(EmailMessage message, Multipart multipart) throws MessagingException, IOException{
        logger.debug("handling multipart content");
        int partCount;
        partCount = multipart.getCount();
        for (int i = 0; i < partCount; i++) {
            BodyPart bp = multipart.getBodyPart(i);
            if (i == 0 && bp.getContent() instanceof String) {
                continue; // this counts as the msg body, so ignore
            }
            if (bp.getContent() instanceof String) {
                handleStringAttachment(message, bp);
            }
            else if (bp.getContent() instanceof Multipart) { // go off recursively...
                handleParts(message, (Multipart) bp.getContent());
            }
            else {
                handleBinaryAttachment(message, bp);
            }
        }
    }
    
    /**
     * @param message
     * @param bp
     */
    private void handleBinaryAttachment(EmailMessage message, BodyPart bp) throws MessagingException, IOException {
        logger.debug("handling binary attachment");
        BinaryContent binContent = new BinaryContentImpl();
        
        InputStream input = bp.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        CopyUtils.copy(input, output);
        input.close();
        output.close(); // yes, i know this doesn't really do anything
        
        binContent.setContent(output.toByteArray());
        binContent.setSize(bp.getSize());
        binContent.setDescription(bp.getDescription());
        binContent.setDisposition(bp.getDisposition());
        binContent.setFileName(bp.getFileName());
        binContent.setMimeType(bp.getContentType());
        
        message.addBinaryPart(binContent);
    }
    
    /**
     * @param message
     * @param bp
     */
    private void handleStringAttachment(EmailMessage message, BodyPart bp) throws MessagingException, IOException {
        logger.debug("handling text attachment");
        TextContent textContent = new TextContentImpl();
        
        textContent.setContent(bp.getContent().toString());
        textContent.setDescription(bp.getDescription());
        textContent.setDisposition(bp.getDisposition());
        textContent.setFileName(bp.getFileName());
        textContent.setSize(bp.getSize());
        textContent.setMimeType(bp.getContentType());
        textContent.setRole(TextContentRole.BODY_PART);
        message.addTextPart(textContent);
        
    }
    
    /**
     * @param message
     * @param in
     */
    private void handleMessageBody(EmailMessage message, Message in) throws MessagingException, IOException {
        logger.debug("handling Message Body for text content");
        Object o;
        o = in.getContent();
        TextContent msgBody = new TextContentImpl();
        msgBody.setRole(TextContentRole.MSG_BODY);
        if (o instanceof Multipart) {
            logger.debug("handling a Multipart body part 0");
            Multipart mp = (Multipart) o;
            BodyPart bp = mp.getBodyPart(0);
            if (bp.getContent() instanceof String) {
                msgBody.setContent(bp.getContent().toString());
                msgBody.setMimeType(bp.getContentType());
                msgBody.setDescription(bp.getDescription());
                msgBody.setDisposition(bp.getDisposition());
            }
        }
        else if (o instanceof String) { // should be just a string
            msgBody.setContent(in.getContent().toString());
            msgBody.setMimeType(in.getContentType());
            msgBody.setDescription(in.getDescription());
            msgBody.setDisposition(in.getDisposition());
        }
        if (msgBody.getContent() != null) { // i.e. we found a msg body OK
            message.addTextPart(msgBody);
        }
    }
    
    /**
     * @param message
     * @param in
     * @param string
     * @throws MessagingException
     */
    private String handleHeader(EmailMessage message, Message in, String header) throws MessagingException {
        String[] headerValue = null;
        String propValue = null;
        headerValue = in.getHeader(header);
        if (headerValue != null) {
            if (headerValue.length == 1) {
                propValue = headerValue[0];
            }
            else {
                StringBuffer buffer = new StringBuffer();
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
     * Extracts from, to, cc, bcc, reply-to, etc
     * 
     * @param message
     * @param in
     * @throws MessagingException
     * @throws ArchiveStorageException
     */
    private void handleAddresses(EmailMessage message, Message in) throws ArchiveStorageException, MessagingException {
        logger.debug("handling addresses");
        processAddresses(message, in.getFrom(), EmailAddressRole.FROM);
        processAddresses(message, in.getReplyTo(), EmailAddressRole.REPLY_TO);
        processAddresses(message, in.getRecipients(Message.RecipientType.TO), EmailAddressRole.TO);
        processAddresses(message, in.getRecipients(Message.RecipientType.CC), EmailAddressRole.CC);
        processAddresses(message, in.getRecipients(Message.RecipientType.BCC), EmailAddressRole.BCC);
        
        // sender is in RFC 822 but no special method is provided in JavaMail to get it
        String[] senderAddressStrings = in.getHeader("Sender");
        if (senderAddressStrings != null) {
            Address[] senderAddresses = new Address[senderAddressStrings.length];
            for (int i = 0; i < senderAddresses.length; i++) {
                senderAddresses[i] = new InternetAddress(senderAddressStrings[i]);
            }
            processAddresses(message, senderAddresses, EmailAddressRole.SENDER);
        }
    }
    
    private void processAddresses(EmailMessage message, Address[] addresses, EmailAddressRole role) throws ArchiveStorageException {
        if (addresses != null) {
            for (int i = 0; i < addresses.length; i++) {
                
                InternetAddress inetAddress = (InternetAddress) addresses[i];
                
                
                // EmailAddress address = EmailAddressFactory.getEmailAddress(inetAddress.getAddress());
                EmailAddress address = addressDAO.getEmailAddress(inetAddress.getAddress());
                if (address == null) {                
                    address = new EmailAddressImpl(inetAddress.getAddress());
                    addressDAO.saveEmailAddress(address);
                }
                address.setPersonalName(inetAddress.getPersonal());
                
                MessageEmailAddress mea = new MessageEmailAddressImpl();
                mea.setEmailAddress(address);
                mea.setRole(role);
                
                message.addMessageEmailAddress(mea);
            }
        }
    }
    
}