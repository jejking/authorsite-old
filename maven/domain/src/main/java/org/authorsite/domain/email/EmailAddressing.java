/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.domain.email;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Class representing the specific use of an email address within the context of
 * an {@link EmailMessage}. The actual usage is specified by the
 * {@link EmailAddressingType} enumeration.
 * 
 * @author jejking
 * 
 */
public class EmailAddressing implements Comparable<EmailAddressing> {

    /**
     * Regular expression defining rules for a valid email address string.
     */
    public static final Pattern ADDRESS_PATTERN = Pattern
	    .compile("([\\p{ASCII}&&[^/<>()@,\\.;:\"\\[\\]\\p{Space}\\p{Cntrl}]])+((\\.){1}([\\p{ASCII}&&[^/<>()@,\\.;:\"\\[\\]\\p{Space}\\p{Cntrl}]])+)*(@){1}((\\p{Alnum})+((\\-){1}(\\p{Alnum})+)*)+((\\.{1}(\\p{Alnum})+((\\-){1}(\\p{Alnum})+)*))*");

    private String personalName;

    private String emailAddress;

    private EmailAddressingType type;

    /**
     * Default constructor.
     */
    public EmailAddressing() {
	super();
    }

    /**
     * Constructs email addressing with given parameters.
     * 
     * @param personalName
     * @param emailAddress
     * @param type
     */
    public EmailAddressing(String personalName, String emailAddress,
	    EmailAddressingType type) {
	super();
	this.setPersonalName(personalName);
	this.setEmailAddress(emailAddress);
	this.setType(type);
    }

    /**
     * Constructs email address with given parameters.
     * 
     * @param emailAddress
     * @param type
     */
    public EmailAddressing(String emailAddress, EmailAddressingType type) {
	super();
	this.setEmailAddress(emailAddress);
	this.setType(type);
    }

    /**
     * @return email address string
     */
    public String getEmailAddress() {
	return this.emailAddress;
    }

    /**
     * Sets the email address.
     * 
     * @param emailAddress may not be <code>null</code> and must be a valid
     * email address according to the regular expression {@link EmailAddressing#ADDRESS_PATTERN}.
     * @throws IllegalArgumentException if above conditions are not met.
     */
    public void setEmailAddress(String emailAddress) {
	if (emailAddress == null) {
	    throw new IllegalArgumentException("parameter may not be null");
	}
	Matcher matcher = ADDRESS_PATTERN.matcher(emailAddress);
	if (matcher.matches()) {
	    this.emailAddress = emailAddress;
	} else {
	    throw new IllegalArgumentException(emailAddress
		    + " does not look like a valid email address");
	}

    }

    /**
     * Gets personal name.
     * 
     * @return personal name
     */
    public String getPersonalName() {
	return this.personalName;
    }

    /**
     * Sets personal name.
     * 
     * @param personalName
     */
    public void setPersonalName(String personalName) {
	this.personalName = personalName;
    }

    /**
     * Gets addressing type.
     * 
     * @return type.
     */
    public EmailAddressingType getType() {
	return this.type;
    }

    /**
     * Sets type.
     * 
     * @param type may not be <code>null</code>.
     * @throws IllegalArgumentException if type parameter is <code>null</code>.
     */
    public void setType(EmailAddressingType type) {
	if (type == null) {
	    throw new IllegalArgumentException("Type may not be set to null");
	}
	this.type = type;
    }

    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result
		+ ((this.emailAddress == null) ? 0 : this.emailAddress.hashCode());
	result = PRIME * result
		+ ((this.personalName == null) ? 0 : this.personalName.hashCode());
	result = PRIME * result + ((this.type == null) ? 0 : this.type.hashCode());
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
	if (this.emailAddress == null) {
	    if (other.emailAddress != null)
		return false;
	} else if (!this.emailAddress.equals(other.emailAddress))
	    return false;
	if (this.personalName == null) {
	    if (other.personalName != null)
		return false;
	} else if (!this.personalName.equals(other.personalName))
	    return false;
	if (this.type == null) {
	    if (other.type != null)
		return false;
	} else if (!this.type.equals(other.type))
	    return false;
	return true;
    }

    public int compareTo(EmailAddressing emailAddressing) {
	return new CompareToBuilder().append(this.type, emailAddressing.type).append(
		this.emailAddress, emailAddressing.emailAddress).append(this.personalName,
		emailAddressing.personalName).toComparison();
    }

    @Override
    public String toString() {
	StringBuilder buffer = new StringBuilder();
	buffer.append(this.type);
	buffer.append(": ");
	if (this.personalName != null) {
	    buffer.append(this.personalName);
	    buffer.append(" ");
	}
	if (this.emailAddress != null) {
	    buffer.append(this.emailAddress);
	}
	return buffer.toString();
    }

}
