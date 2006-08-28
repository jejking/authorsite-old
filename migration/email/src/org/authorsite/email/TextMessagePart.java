package org.authorsite.email;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class TextMessagePart extends MessagePart {

	private String content;

	public TextMessagePart() {
		super();
	}

	public TextMessagePart(String mimeType) {
		super(mimeType);
	}
	

	public TextMessagePart(String mimeType, String content) {
		super(mimeType);
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().appendSuper(super.hashCode())
									.append(this.mimeType)
									.append(this.content)
									.toHashCode();
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
		return new EqualsBuilder().appendSuper(super.equals(obj))
								  .append(this.mimeType, other.mimeType)
								  .append(this.content, other.content)
								  .isEquals();
	}
	
	public String toString() {
	    StringBuilder builder = new StringBuilder();
        builder.append("TextMessagePart:\n");
        builder.append("File Name: " + this.fileName + "\n");
        builder.append("Mime Type: " + this.mimeType + "\n");
        builder.append("Content:\n" + this.content + "\n");
        return builder.toString();
    }
}
