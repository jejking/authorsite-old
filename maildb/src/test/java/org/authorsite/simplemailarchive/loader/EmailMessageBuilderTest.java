/*
 * EmailMessageLoaderTest.java, created on 26-Jul-2004 at 22:38:49
 * 
 * Copyright John King, 2004.
 *
 *  EmailMessageLoaderTest.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.simplemailarchive.loader;


import java.util.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.*;

import org.apache.log4j.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class EmailMessageBuilderTest {
    
//    private Session session;
//    private Store store;
//    private Folder testing;
//    
//    /**
//     * Tests the very basic functionality.
//     * @throws MessagingException
//     * @throws IOException
//     */
//    public void testSimpleMailProps() throws ArchiveStorageException, MessagingException {
//        Message simpleTextMail = testing.getMessage(1);
//        EmailMessage loadedMail = builder.loadMessage(simpleTextMail);
//        
//        // test the main properties of the EmailMessage object we've made
//        Assert.assertEquals("test", loadedMail.getSubject());
//        Assert.assertEquals("<1087662698.be01b76d05958@www.vfemail.net>", loadedMail.getMsgID());
//        Assert.assertEquals(simpleTextMail.getSentDate(), loadedMail.getSentDate());
//    }
//    
//    public void testSimpleMailText() throws MessagingException, IOException, ArchiveStorageException {
//        Message simpleTextMail = testing.getMessage(1);
//        EmailMessage loadedMail = builder.loadMessage(simpleTextMail);
//        
//        // test the content's got through correctly from the inline content
//        Set textParts = loadedMail.getTextParts();
//        TextContent mailContent = null;
//        Iterator textPartsIterator = textParts.iterator();
//        while (textPartsIterator.hasNext()) {
//            TextContent textContent = (TextContent) textPartsIterator.next();
//            if (textContent.getRole().equals(TextContentRole.MSG_BODY)) {
//                mailContent = textContent;
//                break;
//            }
//        }
//        Assert.assertEquals(simpleTextMail.getContent(), mailContent.getContent());
//    }
//    
//    public void testAddressExtraction() throws MessagingException, ArchiveStorageException  {
//        Message simpleTextMail = testing.getMessage(1);
//        EmailMessage loadedMail = builder.loadMessage(simpleTextMail);
//        // test the email addresses are correct
//        Set fromAddresses = loadedMail.getMessageEmailAddressesInRole(EmailAddressRole.FROM);
//        EmailAddress fromAddress = null;
//        Iterator fromAddressesIt = fromAddresses.iterator();
//        while (fromAddressesIt.hasNext()) {
//            MessageEmailAddress addr = (MessageEmailAddress) fromAddressesIt.next();
//            fromAddress = addr.getEmailAddress();
//        }
//        Assert.assertEquals("jejking2@vfemail.net", fromAddress.getAddress());
//        
//        Set toAddresses = loadedMail.getMessageEmailAddressesInRole(EmailAddressRole.TO);
//        EmailAddress toAddress = null;
//        Iterator toAddressesIt = toAddresses.iterator();
//        while (toAddressesIt.hasNext()) {
//            MessageEmailAddress addr = (MessageEmailAddress) toAddressesIt.next();
//            toAddress = addr.getEmailAddress();
//        }
//        Assert.assertEquals("jejking@pair.com", toAddress.getAddress());
//        Assert.assertEquals("John King", toAddress.getPersonalName());
//        
//        Set ccAddresses = loadedMail.getMessageEmailAddressesInRole(EmailAddressRole.CC);
//        EmailAddress ccAddress = null;
//        Iterator ccAddressesIt = ccAddresses.iterator();
//        while (ccAddressesIt.hasNext()) {
//            MessageEmailAddress addr = (MessageEmailAddress) ccAddressesIt.next();
//            ccAddress = addr.getEmailAddress();
//        }
//        Assert.assertEquals("john_e_j_king@hotmail.com", ccAddress.getAddress());
//    }
//    
//    public void testFullHeaders() throws MessagingException, ArchiveStorageException {
//        Message simpleTextMail = testing.getMessage(1);
//        EmailMessage loadedMail = builder.loadMessage(simpleTextMail);
//        Set textParts = loadedMail.getTextParts();
//        TextContent headersContent = null;
//        Iterator textIterator = textParts.iterator();
//        while (textIterator.hasNext()) {
//            TextContent content = (TextContent) textIterator.next();
//            if (content.getRole().equals(TextContentRole.HEADERS)) {
//                headersContent = content;
//                break;
//            }
//        }
//        Assert.assertNotNull(headersContent); 
//        // can't really think of any tests to do here that doesn't involve duplicating the same
//        // code that I used in the class I'm testing. This at least uses a slightly different method
//        MimeMessage simpleMimeMail = (MimeMessage) simpleTextMail;
//        Enumeration headerStrings = simpleMimeMail.getAllHeaderLines();
//        StringBuffer buffer = new StringBuffer();
//        while (headerStrings.hasMoreElements()) {
//            String string = (String) headerStrings.nextElement();
//            buffer.append(string);
//            if (headerStrings.hasMoreElements()) {
//                buffer.append("\n");
//            }
//        }
//        Assert.assertEquals(buffer.toString(), headersContent.getContent());
//    }
//    
//    public void testAnsweringID() throws MessagingException, ArchiveStorageException {
//        Message simpleTextMail = testing.getMessage(1);
//        EmailMessage loadedMail = builder.loadMessage(simpleTextMail);
//        
//        Message answer = testing.getMessage(3);
//        EmailMessage answerMail = builder.loadMessage(answer);
//        
//        Assert.assertEquals(loadedMail.getMsgID(), answerMail.getMsgReferences());
//        Assert.assertEquals(loadedMail.getMsgID(), answerMail.getInReplyTo());
//    }
//    
//    public void testGetBodyFromMultipart() throws MessagingException, ArchiveStorageException {
//        Message textMailWithAttachment = testing.getMessage(7); // msg from hotmail with two pics
//        EmailMessage loadedMail = builder.loadMessage(textMailWithAttachment);
//        Set textParts = loadedMail.getTextParts();
//        Assert.assertNotNull(textParts);
//        TextContent msgBody = null;
//        Iterator textPartsIterator = textParts.iterator();
//        while (textPartsIterator.hasNext()) {
//            TextContent content = (TextContent) textPartsIterator.next();
//            if (content.getRole().equals(TextContentRole.MSG_BODY)) {
//                msgBody = content;
//                break;
//            }
//        }
//        Assert.assertNotNull(msgBody);
//        Assert.assertTrue(msgBody.getContent().startsWith("These pictures may be very useful for testing purposes"));
//    }
//    
//    public void testGetTextFromAttachment() throws MessagingException, ArchiveStorageException {
//        Message textAttachmentMsg  = testing.getMessage(8); // attached text file
//        EmailMessage loadedMail = builder.loadMessage(textAttachmentMsg);
//        Set textParts = loadedMail.getTextParts();
//        TextContent attachment = null;
//        Iterator textIterator = textParts.iterator();
//        while (textIterator.hasNext()) {
//            TextContent content = (TextContent) textIterator.next();
//            if (content.getRole().equals(TextContentRole.BODY_PART)) {
//                attachment = content;
//                break;
//            }
//        }
//        Assert.assertNotNull(attachment);
//        Assert.assertTrue(attachment.getContent().startsWith("This is a little document about testing."));
//        Assert.assertEquals("testing.txt", attachment.getFileName());
//        Assert.assertEquals("attachment", attachment.getDisposition());
//        Assert.assertTrue(attachment.getMimeType().startsWith("text/plain"));
//    }
//    
//    public void testGetTextFromInline() throws MessagingException, ArchiveStorageException {
//        Message textInlineMsg  = testing.getMessage(10); // attached text file
//        EmailMessage loadedMail = builder.loadMessage(textInlineMsg);
//        Set textParts = loadedMail.getTextParts();
//        TextContent attachment = null;
//        Iterator textIterator = textParts.iterator();
//        while (textIterator.hasNext()) {
//            TextContent content = (TextContent) textIterator.next();
//            if (content.getRole().equals(TextContentRole.BODY_PART)) {
//                attachment = content;
//                break;
//            }
//        }
//        Assert.assertNotNull(attachment);
//        Assert.assertTrue(attachment.getContent().startsWith("This is a little document about testing."));
//        Assert.assertEquals("testing.txt", attachment.getFileName());
//        Assert.assertEquals("inline", attachment.getDisposition());
//        Assert.assertTrue(attachment.getMimeType().startsWith("text/plain"));
//        
//        Set binaryParts = loadedMail.getBinaryParts();
//        Assert.assertEquals(0, binaryParts.size());
//    }
//    
//    public void testGettingAttachedPictures() throws IOException, MessagingException, ArchiveStorageException {
//        Message textInlineMsg  = testing.getMessage(7); // attached text file
//        EmailMessage loadedMail = builder.loadMessage(textInlineMsg);
//        
//        // we should have one msg body, one header text parts
//        Set textParts = loadedMail.getTextParts();
//        Assert.assertEquals(2, textParts.size());
//        
//        // and two binary parts
//        Set binaryParts = loadedMail.getBinaryParts();
//        Assert.assertEquals(2, binaryParts.size());
//        Iterator binPartsIt = binaryParts.iterator();
//        Map binMap = new HashMap();
//        while (binPartsIt.hasNext()) {
//            BinaryContent binContent = (BinaryContent) binPartsIt.next();
//            binMap.put(binContent.getFileName(), binContent.getContent());
//        }
//        
//        // the two binary parts are the pictures we use for testing, so they're in the JAR file
//        // we can get hold of them and calculate a hash.
//        // the hash must be the same as the hash of the byte array extracted from the 
//        // newly instantiated BinaryContent object
//        
//        InputStream jejking1Stream = EmailMessageBuilderTest.class.getResourceAsStream("/org/authorsite/mailarchive/model/impl/jejking1.jpg");
//        Assert.assertNotNull(jejking1Stream);
//        byte [] jejking1bytes = EmailMessageBuilderTest.getBytesFromInputStream(jejking1Stream);
//        int jejking1bytesHash = new HashCodeBuilder().append(jejking1bytes).toHashCode();
//        int jejking1binContentHash = new HashCodeBuilder().append((byte []) binMap.get("jejking1.jpg")).toHashCode();
//        Assert.assertEquals(jejking1bytesHash, jejking1binContentHash);
//        
//        InputStream jejking2Stream = EmailMessageBuilderTest.class.getResourceAsStream("/org/authorsite/mailarchive/model/impl/jejking2.jpg");
//        byte[] jejking2bytes = EmailMessageBuilderTest.getBytesFromInputStream(jejking2Stream);
//        int jejking2bytesHash = new HashCodeBuilder().append(jejking2bytes).toHashCode();
//        int jejking2binContentHash = new HashCodeBuilder().append((byte []) binMap.get("jejking2.jpg")).toHashCode();
//        Assert.assertEquals(jejking2bytesHash, jejking2binContentHash);
//    }
//    
//    private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
//		
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		int byteIn = is.read();
//		while (byteIn >= 0) {
//		    out.write(byteIn);
//		    byteIn = is.read();
//		}
//		is.close();
//		return out.toByteArray();
//	}

    
}