package org.authorsite.email;

public enum EmailAddressingType {

	TO("To"),
	FROM("From"),
	CC("cc"),
	BCC("bcc"),
	REPLY_TO("Reply-To"),
	SENDER("Sender");
	
	private String typeName;
	
	EmailAddressingType(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}
	
	
	
}
