package org.authorsite.domain.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.authorsite.domain.AbstractEntry;

/**
 * Class representing a discrete email address, contained
 * within an {@link EmailFolder}.
 * 
 * @author jejking
 *
 */
public class EmailMessage extends AbstractEntry {

    private EmailFolder parent;

    private Set<EmailAddressing> emailAddressings = new HashSet<EmailAddressing>();

    private String subject;

    private Date sentDate;

    private Date receivedDate;

    private String msgId;

    private String inReplyTo;

    private List<String> msgReferences = new ArrayList<String>();

    private String content;

    private MessagePartContainer multipartContainer;

    /**
     * Default constructor.
     */
    public EmailMessage() {
	super();
    }

    /**
     * Adds email addressing
     * 
     * @param addressing may not be <code>null</code>.
     * @throws IllegalArgumentException if param is <code>null</code>.
     */
    public void addEmailAddressing(EmailAddressing addressing) {
	if ( addressing == null ) {
	    throw new IllegalArgumentException("addressing param cannot be null");
	}
	this.emailAddressings.add(addressing);
    }

    /**
     * Gets email addressings.
     * 
     * @return set of email addressings
     */
    public Set<EmailAddressing> getEmailAddressings() {
	return this.emailAddressings;
    }

    
    /**
     * @return the content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the inReplyTo
     */
    public String getInReplyTo() {
        return this.inReplyTo;
    }

    /**
     * @param inReplyTo the inReplyTo to set
     */
    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    /**
     * @return the msgId
     */
    public String getMsgId() {
        return this.msgId;
    }

    /**
     * @param msgId the msgId to set
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return the msgReferences
     */
    public List<String> getMsgReferences() {
        return this.msgReferences;
    }

    /**
     * @param msgReferences the msgReferences to set
     */
    public void setMsgReferences(List<String> msgReferences) {
        this.msgReferences = msgReferences;
    }

    /**
     * @return the multipartContainer
     */
    public MessagePartContainer getMultipartContainer() {
        return this.multipartContainer;
    }

    /**
     * @param multipartContainer the multipartContainer to set
     */
    public void setMultipartContainer(MessagePartContainer multipartContainer) {
        this.multipartContainer = multipartContainer;
    }

    /**
     * @return the parent
     */
    public EmailFolder getParent() {
        return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(EmailFolder parent) {
        this.parent = parent;
    }

    /**
     * @return the receivedDate
     */
    public Date getReceivedDate() {
        return this.receivedDate;
    }

    /**
     * @param receivedDate the receivedDate to set
     */
    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * @return the sentDate
     */
    public Date getSentDate() {
        return this.sentDate;
    }

    /**
     * @param sentDate the sentDate to set
     */
    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return this.subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @param emailAddressings the emailAddressings to set
     */
    public void setEmailAddressings(Set<EmailAddressing> emailAddressings) {
        this.emailAddressings = emailAddressings;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((this.content == null) ? 0 : this.content.hashCode());
	result = PRIME * result + ((this.emailAddressings == null) ? 0 : this.emailAddressings.hashCode());
	result = PRIME * result + ((this.inReplyTo == null) ? 0 : this.inReplyTo.hashCode());
	result = PRIME * result + ((this.msgId == null) ? 0 : this.msgId.hashCode());
	result = PRIME * result + ((this.msgReferences == null) ? 0 : this.msgReferences.hashCode());
	result = PRIME * result + ((this.multipartContainer == null) ? 0 : this.multipartContainer.hashCode());
	result = PRIME * result + ((this.parent == null) ? 0 : this.parent.hashCode());
	result = PRIME * result + ((this.receivedDate == null) ? 0 : this.receivedDate.hashCode());
	result = PRIME * result + ((this.sentDate == null) ? 0 : this.sentDate.hashCode());
	result = PRIME * result + ((this.subject == null) ? 0 : this.subject.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final EmailMessage other = (EmailMessage) obj;
	if (this.content == null) {
	    if (other.content != null)
		return false;
	} else if (!this.content.equals(other.content))
	    return false;
	if (this.emailAddressings == null) {
	    if (other.emailAddressings != null)
		return false;
	} else if (!this.emailAddressings.equals(other.emailAddressings))
	    return false;
	if (this.inReplyTo == null) {
	    if (other.inReplyTo != null)
		return false;
	} else if (!this.inReplyTo.equals(other.inReplyTo))
	    return false;
	if (this.msgId == null) {
	    if (other.msgId != null)
		return false;
	} else if (!this.msgId.equals(other.msgId))
	    return false;
	if (this.msgReferences == null) {
	    if (other.msgReferences != null)
		return false;
	} else if (!this.msgReferences.equals(other.msgReferences))
	    return false;
	if (this.multipartContainer == null) {
	    if (other.multipartContainer != null)
		return false;
	} else if (!this.multipartContainer.equals(other.multipartContainer))
	    return false;
	if (this.parent == null) {
	    if (other.parent != null)
		return false;
	} else if (!this.parent.equals(other.parent))
	    return false;
	if (this.receivedDate == null) {
	    if (other.receivedDate != null)
		return false;
	} else if (!this.receivedDate.equals(other.receivedDate))
	    return false;
	if (this.sentDate == null) {
	    if (other.sentDate != null)
		return false;
	} else if (!this.sentDate.equals(other.sentDate))
	    return false;
	if (this.subject == null) {
	    if (other.subject != null)
		return false;
	} else if (!this.subject.equals(other.subject))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder buffer = new StringBuilder();
//	buffer.append(emailAddressingContainer);
	buffer.append("Subject: " + subject);
	buffer.append("\n");
	buffer.append("MsgID: " + msgId);
	buffer.append("\n");
	buffer.append("Sent: " + sentDate);
	buffer.append("\n");
	buffer.append("Received: " + receivedDate);
	buffer.append("\n");
	if (content != null) {
	    buffer.append("Content:");
	    buffer.append("\n");
	    buffer.append(content);
	}
	if (multipartContainer != null) {
	    buffer.append(multipartContainer);
	}
	return buffer.toString();
    }

}
