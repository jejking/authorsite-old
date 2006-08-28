package org.authorsite.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;

public final class JavamailAdapter {

	public EmailFolder convertFolder(Folder javaMailFolder) throws MessagingException, IOException {
        System.out.println("converting folder " + javaMailFolder.getFullName());
		EmailFolder myFolder = new EmailFolder(javaMailFolder.getName());
		Message[] javaMailMessages = javaMailFolder.getMessages();
		for ( Message javaMailMessage : javaMailMessages ) {
			EmailMessage myMessage = this.convertMessage( javaMailMessage );
			myMessage.setParent(myFolder);
		}
		Folder[] childJavaMailFolders = javaMailFolder.list();
		for ( Folder childJavaMailFolder : childJavaMailFolders ) {
			EmailFolder childFolder = convertFolder( childJavaMailFolder );
			childFolder.setParent(myFolder);
		}
		return myFolder;
	}
	
	public EmailMessage convertMessage(Message javaMailMessage) throws MessagingException, IOException {
        
		EmailMessage myMessage = new EmailMessage();
		EmailAddressingContainer addressingContainer = new EmailAddressingContainer();
		// from
		InternetAddress[] fromAddresses = (InternetAddress[]) javaMailMessage.getFrom();
		for ( InternetAddress fromAddress : fromAddresses ) {
			EmailAddressing fromAddressing = convertEmailAddress(fromAddress);
			fromAddressing.setType(EmailAddressingType.FROM);
			addressingContainer.add(fromAddressing);
		}
		// to, cc, bcc
		Address[] recipientAddresses =  javaMailMessage.getAllRecipients();
		for ( Address recipientAddress : recipientAddresses ) {
            if ( recipientAddress instanceof InternetAddress ) {
                 InternetAddress realRecipientAddress = (InternetAddress) recipientAddress;
                 EmailAddressing recipientAddressing = convertEmailAddress(realRecipientAddress);
                if ( recipientAddress.getType().equals(Message.RecipientType.TO)) {
                    recipientAddressing.setType(EmailAddressingType.TO);
                }
                else if (recipientAddress.getType().equals(Message.RecipientType.CC)) {
                    recipientAddressing.setType(EmailAddressingType.CC);
                }
                else if (recipientAddress.getType().equals(Message.RecipientType.BCC)) {
                    recipientAddressing.setType(EmailAddressingType.BCC);
                }
                addressingContainer.add(recipientAddressing);
            }
            else {
                System.err.println("ODD ADDRESS" + recipientAddress);
            }
		}
		// replyTo
		InternetAddress[] replyToAddresses = (InternetAddress[]) javaMailMessage.getReplyTo();
		for ( InternetAddress replyToAddress : replyToAddresses ) {
			EmailAddressing replyToAddressing = convertEmailAddress(replyToAddress);
			replyToAddressing.setType(EmailAddressingType.REPLY_TO);
		}
		
		// sender
		String[] senderAddressStrings = javaMailMessage.getHeader("Sender");
        if (senderAddressStrings != null) {
            InternetAddress[] senderAddresses = new InternetAddress[senderAddressStrings.length];
            for (InternetAddress senderAddress : senderAddresses ) {
                EmailAddressing senderAddressing = convertEmailAddress(senderAddress);
                senderAddressing.setType(EmailAddressingType.SENDER);
                addressingContainer.add(senderAddressing);
            }
        }
		
		// subject
		myMessage.setSubject(javaMailMessage.getSubject());
		
		myMessage.setSentDate(javaMailMessage.getSentDate());
//		myMessage.setReceivedDate(javaMailMessage.getReceivedDate());
		myMessage.setMsgId(handleHeader(javaMailMessage, "Message-Id"));
		myMessage.setInReplyTo(handleHeader(javaMailMessage, "In-Reply-To"));
		myMessage.setMsgReferences(handleReferencesHeader(javaMailMessage, "References"));
		Object content = javaMailMessage.getContent();
        System.out.println("--- content is of class " + content.getClass());
		if (content != null ) {
			if ( content instanceof String) {
				myMessage.setContent((String) content);
			}
            else if ( content instanceof MimeMultipart ) {
                System.out.println("handling a multipart");
                MessagePartContainer myMultipartContainer = convertMultipart((Multipart) content);
                myMessage.setMultipartContainer(myMultipartContainer);
            }
		}
		
		
		System.out.println(myMessage);
		return myMessage;
	}
	
    public EmailAddressing convertEmailAddress(InternetAddress address) {
		EmailAddressing addressing = new EmailAddressing();
		addressing.setEmailAddress(address.getAddress());
		addressing.setPersonalName(address.getPersonal());
		return addressing;
	}
	
	public MessagePartContainer convertMultipart(Multipart multipart) throws MessagingException, IOException {
		MessagePartContainer myContainer = new MessagePartContainer();
		int partCount = multipart.getCount();
        for (int i = 0; i < partCount; i++) {
            BodyPart bp = multipart.getBodyPart(i);
            if (bp.getContent() instanceof String) {
                handleTextAttachment(myContainer, bp);
            }
            else if (bp.getContent() instanceof Multipart) { // go off recursively...
                handleChildMultipart(myContainer, (Multipart) bp.getContent());
            }
            else {
            	handleBinaryAttachment(myContainer, bp);
            }
        }
        return myContainer;
    }
		
	private void handleTextAttachment(MessagePartContainer container, BodyPart bp) throws IOException, MessagingException {
		
		TextMessagePart textContent = new TextMessagePart();
		
		textContent.setContent(bp.getContent().toString());
        textContent.setDescription(bp.getDescription());
        textContent.setDisposition(bp.getDisposition());
        textContent.setFileName(bp.getFileName());
        textContent.setSize(bp.getSize());
        textContent.setMimeType(bp.getContentType());
        container.addChildPart(textContent);
	}
	
	private void handleBinaryAttachment(MessagePartContainer container, BodyPart bp) throws IOException, MessagingException {
		
		BinaryMessagePart binContent = new BinaryMessagePart();
		InputStream input = bp.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        byte[] buf = new byte[1024];
        int len;
        while ((len = input.read(buf)) > 0) {
            output.write(buf, 0, len);
        }
        
        input.close();
        output.close(); // yes, i know this doesn't really do anything

        binContent.setContent(output.toByteArray());
        binContent.setSize(bp.getSize());
        binContent.setDescription(bp.getDescription());
        binContent.setDisposition(bp.getDisposition());
        binContent.setFileName(bp.getFileName());
        binContent.setMimeType(bp.getContentType());

        container.addChildPart(binContent);
		
	}

    private void handleChildMultipart(MessagePartContainer myContainer, Multipart multipart) throws MessagingException, IOException {
		MessagePartContainer childContainer = this.convertMultipart(multipart);
		myContainer.addChildPart(childContainer);
	}

	
	private String handleHeader(Message in, String header) throws MessagingException {
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
    
    private List<String> handleReferencesHeader(Message javaMailMessage, String string) {
        // TODO Auto-generated method stub
        return null;
    }
}
