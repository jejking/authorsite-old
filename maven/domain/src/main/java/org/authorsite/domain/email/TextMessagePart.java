package org.authorsite.domain.email;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Textual message part, content is represented as a string.
 * 
 * @author jejking
 *
 */
public class TextMessagePart extends AbstractMessagePart {

    private String content;

    /**
     * Default constructor.
     */
    public TextMessagePart() {
	super();
    }

    /**
     * Constructs object, setting properties from parameters.
     * 
     * @param mimeType
     * @param content
     */
    public TextMessagePart(String mimeType, String content) {
	super.setMimeType(mimeType);
	this.content = content;
    }

    /**
     * @return content
     */
    public String getContent() {
	return content;
    }

    /**
     * Sets content.
     * 
     * @param content
     */
    public void setContent(String content) {
	this.content = content;
    }

    @Override
    public int getSize() {
	if (content != null) {
	    try {
		return content.getBytes("UTF-8").length;
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return -1;
	    }
	} else {
	    return 0;
	}
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().appendSuper(super.hashCode()).append(
		this.mimeType).append(this.content).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final TextMessagePart other = (TextMessagePart) obj;
	return new EqualsBuilder().appendSuper(super.equals(obj)).append(
		this.mimeType, other.mimeType).append(this.content,
		other.content).isEquals();
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("TextMessagePart:\n");
	builder.append("File Name: " + this.fileName + "\n");
	builder.append("Mime Type: " + this.mimeType + "\n");
	if (this.description != null) {
	    builder.append("Description: " + this.description + "\n");
	}
	if (this.disposition != null) {
	    builder.append("Disposition: " + this.disposition + "\n");
	}
	builder.append("Content:\n" + this.content + "\n");
	return builder.toString();
    }
}
