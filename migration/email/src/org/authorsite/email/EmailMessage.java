package org.authorsite.email;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EmailMessage extends AbstractMailItem {

	private EmailFolder parent;
	private Set<EmailAddressing> addressings = new HashSet<EmailAddressing>();
	private String subject;
	private Date sentDate;
	private Date receivedDate;
	private String msgId;
	private String inReplyTo;
	private String msgReferences;
	private String content;
	private MessagePartContainer multipartContainer;
	
	public EmailMessage() {
		super();
	}
	
	public void addEmailAddressing(EmailAddressing addressing) {
		assert addressing != null;
		addressings.add(addressing);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgReferences() {
		return msgReferences;
	}

	public void setMsgReferences(String msgReferences) {
		this.msgReferences = msgReferences;
	}

	public MessagePartContainer getMultipartContainer() {
		return multipartContainer;
	}

	public void setMultipartContainer(MessagePartContainer multipartContainer) {
		this.multipartContainer = multipartContainer;
	}

	public EmailFolder getParent() {
		return parent;
	}

	public void setParent(EmailFolder parent) {
		this.parent = parent;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	
}
