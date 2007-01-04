package org.authorsite.domain.email;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Binary message part wraps raw binary content.
 * 
 * @author jejking
 *
 */
public class BinaryMessagePart extends AbstractMessagePart {

    private byte[] content;

    /**
     * Default constructor.
     */
    public BinaryMessagePart() {
	super();
    }

    /**
     * Constructs object setting properties to the values
     * in the parameters.
     * 
     * @param mimeType
     * @param content
     */
    public BinaryMessagePart(String mimeType, byte[] content) {
	super.setMimeType(mimeType);
	this.setContent(content);
    }

    /**
     * @return content as raw byte array
     */
    public byte[] getContent() {
	return content;
    }

    /**
     * Set content.
     * 
     * @param content
     */
    public void setContent(byte[] content) {
	this.content = content;
    }

    @Override
    public int getSize() {
	if (this.content != null) {
	    return content.length;
	} else {
	    return 0;
	}
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(super.hashCode()).append(
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
	final BinaryMessagePart other = (BinaryMessagePart) obj;
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
	return builder.toString();
    }

}
