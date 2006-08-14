package org.authorsite.email;

public abstract class MessagePart extends AbstractMailItem {
	
	private MessagePartContainer parent;
	private String mimeType;
	
	
	public MessagePart() {
		super();
	}
	
	public MessagePart(String mimeType) {
		super();
		this.mimeType = mimeType;
	}
	
		public String getMimeType() {
		return mimeType;
	}
	
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public MessagePartContainer getParent() {
		return parent;
	}
	
	public void setParent(MessagePartContainer parent) {
		this.parent = parent;
	}
	
	
	
	
}
