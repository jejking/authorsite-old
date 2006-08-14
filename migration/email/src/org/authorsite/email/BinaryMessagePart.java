package org.authorsite.email;

public class BinaryMessagePart extends MessagePart {

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
	
}
