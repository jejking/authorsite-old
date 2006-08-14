package org.authorsite.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.CompareToBuilder;

public final class EmailAddressing implements Comparable {

	public static final Pattern ADDRESS_PATTERN =
		Pattern.compile(
		"([\\p{ASCII}&&[^/<>()@,\\.;:\"\\[\\]\\p{Space}\\p{Cntrl}]])+((\\.){1}([\\p{ASCII}&&[^/<>()@,\\.;:\"\\[\\]\\p{Space}\\p{Cntrl}]])+)*(@){1}((\\p{Alnum})+((\\-){1}(\\p{Alnum})+)*)+((\\.{1}(\\p{Alnum})+((\\-){1}(\\p{Alnum})+)*))*");
	
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
		assert emailAddress != null;
	    Matcher matcher = ADDRESS_PATTERN.matcher(emailAddress);
		if (matcher.matches()) {
			this.emailAddress = emailAddress;
		}
		else {
			throw new IllegalArgumentException(emailAddress + " does not look like a valid email address");
		}
		
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
	
	

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = PRIME * result + ((personalName == null) ? 0 : personalName.hashCode());
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EmailAddressing other = (EmailAddressing) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (personalName == null) {
			if (other.personalName != null)
				return false;
		} else if (!personalName.equals(other.personalName))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int compareTo(Object o) {
		EmailAddressing rhs = (EmailAddressing) o;
		return new CompareToBuilder().append(this.type, rhs.type)
									 .append(this.emailAddress, rhs.emailAddress)
									 .append(this.personalName, rhs.personalName)
									 .toComparison();
	}
	
	
	
}
