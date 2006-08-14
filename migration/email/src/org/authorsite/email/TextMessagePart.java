package org.authorsite.email;

public class TextMessagePart extends MessagePart {

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
	
	
	
}
