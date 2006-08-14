package org.authorsite.email;

public class EmailAddressing {

	private String personalName;
	private String emailAddress;
	private EmailAddressingType type;
	
	public EmailAddressing() {
		super();
	}
	
	public EmailAddressing(String personalName, String emailAddress, EmailAddressingType type) {
		super();
		this.personalName = personalName;
		this.emailAddress = emailAddress;
		this.type = type;
	}

	
	public EmailAddressing(String emailAddress, EmailAddressingType type) {
		super();
		this.emailAddress = emailAddress;
		this.type = type;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getPersonalName() {
		return personalName;
	}
	
	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
	
	public EmailAddressingType getType() {
		return type;
	}
	
	public void setType(EmailAddressingType type) {
		this.type = type;
	}
	
	
	
}
