package org.authorsite.email;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.authorsite.email.db.EmailVisitor;

public final class BinaryMessagePart extends MessagePart {

	private byte[] content;

	public BinaryMessagePart() {
		super();
	}

	public BinaryMessagePart(String mimeType) {
		super(mimeType);
	}
	
	public BinaryMessagePart(String mimeType, byte[] content) {
		super(mimeType);
		this.content = content;
	}
	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(super.hashCode())
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
		final BinaryMessagePart other = (BinaryMessagePart) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj))
								  .append(this.mimeType, other.mimeType)
								  .append(this.content, other.content)
								  .isEquals();
	}

	@Override
	public void acceptEmailVisitor(EmailVisitor visitor) {
		visitor.visit(this);
	}
	
	
	
}
