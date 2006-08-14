package org.authorsite.email;

import java.util.Date;

import org.authorsite.email.db.EmailVisitor;

public class EmailMessage extends AbstractMailItem {

	private EmailFolder parent;
	private EmailAddressingContainer addressings = new EmailAddressingContainer();
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
	
	public EmailAddressingContainer getAddressings() {
		return addressings;
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

	

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((addressings == null) ? 0 : addressings.hashCode());
		result = PRIME * result + ((content == null) ? 0 : content.hashCode());
		result = PRIME * result + ((inReplyTo == null) ? 0 : inReplyTo.hashCode());
		result = PRIME * result + ((msgId == null) ? 0 : msgId.hashCode());
		result = PRIME * result + ((msgReferences == null) ? 0 : msgReferences.hashCode());
		result = PRIME * result + ((multipartContainer == null) ? 0 : multipartContainer.hashCode());
		result = PRIME * result + ((parent == null) ? 0 : parent.hashCode());
		result = PRIME * result + ((receivedDate == null) ? 0 : receivedDate.hashCode());
		result = PRIME * result + ((sentDate == null) ? 0 : sentDate.hashCode());
		result = PRIME * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EmailMessage other = (EmailMessage) obj;
		if (addressings == null) {
			if (other.addressings != null)
				return false;
		} else if (!addressings.equals(other.addressings))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (inReplyTo == null) {
			if (other.inReplyTo != null)
				return false;
		} else if (!inReplyTo.equals(other.inReplyTo))
			return false;
		if (msgId == null) {
			if (other.msgId != null)
				return false;
		} else if (!msgId.equals(other.msgId))
			return false;
		if (msgReferences == null) {
			if (other.msgReferences != null)
				return false;
		} else if (!msgReferences.equals(other.msgReferences))
			return false;
		if (multipartContainer == null) {
			if (other.multipartContainer != null)
				return false;
		} else if (!multipartContainer.equals(other.multipartContainer))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (receivedDate == null) {
			if (other.receivedDate != null)
				return false;
		} else if (!receivedDate.equals(other.receivedDate))
			return false;
		if (sentDate == null) {
			if (other.sentDate != null)
				return false;
		} else if (!sentDate.equals(other.sentDate))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(addressings);
		buffer.append("Subject: " + subject);
		buffer.append("\n");
		buffer.append("MsgID: " + msgId);
		buffer.append("\n");
		buffer.append("Sent: " + sentDate);
		buffer.append("\n");
		buffer.append("Received: " + receivedDate);
		return buffer.toString();
	}
	
	@Override
	public void acceptEmailVisitor(EmailVisitor visitor) {
		visitor.visit(this);
	}

	
}
