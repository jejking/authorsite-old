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
			myFolder.addEmailMessage(myMessage);
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
		// from
		InternetAddress[] fromAddresses = (InternetAddress[]) javaMailMessage.getFrom();
		for ( InternetAddress fromAddress : fromAddresses ) {
			EmailAddressing fromAddressing = convertEmailAddress(fromAddress);
			fromAddressing.setType(EmailAddressingType.FROM);
			myMessage.addEmailAddressing(fromAddressing);
		}

		// to
		Address[] toAddresses = javaMailMessage.getRecipients(Message.RecipientType.TO);
		if ( toAddresses != null ) {
			for ( Address toAddress : toAddresses ) {
				InternetAddress realRecipientAddress = (InternetAddress) toAddress;
	            EmailAddressing recipientAddressing = convertEmailAddress(realRecipientAddress);
	            recipientAddressing.setType(EmailAddressingType.TO);
	            myMessage.addEmailAddressing(recipientAddressing);
			}
		}
		
		
		// cc
		Address[] ccAddresses = javaMailMessage.getRecipients(Message.RecipientType.CC);
		if ( ccAddresses != null ) {
			for ( Address ccAddress : ccAddresses ) {
				InternetAddress realRecipientAddress = (InternetAddress) ccAddress;
	            EmailAddressing recipientAddressing = convertEmailAddress(realRecipientAddress);
	            recipientAddressing.setType(EmailAddressingType.CC);
	            myMessage.addEmailAddressing(recipientAddressing);
			}
		}
		
		
		// bc
		Address[] bccAddresses = javaMailMessage.getRecipients(Message.RecipientType.BCC);
		if ( bccAddresses != null ) {
			for ( Address bccAddress : bccAddresses ) {
				InternetAddress realRecipientAddress = (InternetAddress) bccAddress;
	            EmailAddressing recipientAddressing = convertEmailAddress(realRecipientAddress);
	            recipientAddressing.setType(EmailAddressingType.BCC);
	            myMessage.addEmailAddressing(recipientAddressing);
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
                myMessage.addEmailAddressing(senderAddressing);
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
