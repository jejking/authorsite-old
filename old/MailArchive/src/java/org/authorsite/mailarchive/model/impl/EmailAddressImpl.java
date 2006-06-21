/*
 * EmailAddressImpl.java, created on 07-Mar-2004 at 13:30:29
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressImpl.java is part of authorsite.org's MailArchive program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.model.impl;

import java.util.regex.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;

/**
 * Plain Java implementation of <code>EmailAddress</code> interface.
 * 
 * @hibernate.query name="EmailAddressString" query="from EmailAddressImpl as addr where addr.address = :addressString"
 * @hibernate.query name="EmailAddressStringLike" query="from EmailAddressImpl as addr where addr.address like :addressString"
 * @hibernate.query name="EmailAddressPerson" query="from EmailAddressImpl as addr where addr.person = :person"
 * @hibernate.query name="EmailAddressPersonalName" query="from EmailAddressImpl as addr where addr.personalName = :personalName"
 * @hibernate.query name="EmailAddressPersonalNameLike" query="from EmailAddressImpl as addr where addr.personalName like :personalName"
 * 
 * @hibernate.class table="EmailAddress" proxy="org.authorsite.mailarchive.model.EmailAddress"
 * @hibernate.cache usage="nonstrict-read-write"
 * 
 * @see org.authorsite.mailarchive.model.EmailAddress
 * @author jejking
 * @version $Revision: 1.18 $
 */
public final class EmailAddressImpl implements EmailAddress, Versionable, Comparable {

	private Integer ID;
	private Integer version;
	private String address;
	private String personalName;
	private Person person;
	private boolean isProcess;
	private boolean isMailingList;

	private static Logger logger = Logger.getLogger(EmailAddressImpl.class);

	/**
	 * Regex that draws on grammar rules in rfc 822 for mailbox, then standard domain type rules
	 * 
	 * mailbox: one or more words consisting of at least one ASCII characters except specials, white space and control chars
	 * specials are ()/<>@,;:\[]. - each word these can be separated by a .
	 * 
	 * a single @ 
	 * 
	 * domain, consists of one or more subdomains (ok, including hostname)
	 * each hostname/subdomain consists only of ASCII alphanumeric characters permitted along with hyphens
	 * each hostname/subdomain may only start or end with an alphanumeric character
	 * separated by . 
	 */
	public static final Pattern ADDRESS_PATTERN =
		Pattern.compile(
			"([\\p{ASCII}&&[^/<>()@,\\.;:\"\\[\\]\\p{Space}\\p{Cntrl}]])+((\\.){1}([\\p{ASCII}&&[^/<>()@,\\.;:\"\\[\\]\\p{Space}\\p{Cntrl}]])+)*(@){1}((\\p{Alnum})+((\\-){1}(\\p{Alnum})+)*)+((\\.{1}(\\p{Alnum})+((\\-){1}(\\p{Alnum})+)*))*");

	/**
	 * Default constructor. 
	 */
	public EmailAddressImpl() {
		logger.debug("New EmailAddressImpl created using default constructor");
	}
	
	/**
	 * Creates new EmailAddressImpl with the given String as the address.
	 * @param newEmailAddress
	 * @throws IllegalArgumentException if the string does not resemble a valid email address.
	 */
	public EmailAddressImpl(String newEmailAddress) {
	    setAddress(newEmailAddress);
	}
	
	public EmailAddressImpl(String newEmailAddress, String newPersonalName) {
	    this(newEmailAddress);
	    setPersonalName(newPersonalName);
	}

	/**
	 * @hibernate.id column="EmailAddressID" generator-class="native"
	 * @see org.authorsite.mailarchive.model.Identifiable#getID()
	 */
	public Integer getID() {
		return ID;
	}

	/**
	 * @see org.authorsite.mailarchive.model.Identifiable#setID(int)
	 */
	public void setID(Integer newID) {
		ID = newID;
	}

	/**
	 * @hibernate.version
	 * @see org.authorsite.mailarchive.model.impl.Versionable#getVersion()
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * @see org.authorsite.mailarchive.model.impl.Versionable#setVersion(java.lang.Integer)
	 */
	public void setVersion(Integer newVersion) {
		version = newVersion;
	}

	/**
	 * @hibernate.property type="string" length="300" unique="true" not-null="true"
	 * @see org.authorsite.mailarchive.domain.EmailAddress#getAddress()
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.EmailAddress#setAddress(java.lang.String)
	 * @param newAddress a string representing a valid email address per rfc 822 with at least one domain part, which may be just a hostname
	 * @throws IllegalArgumentException if the string does not resemble a valid email address
	 */
	public void setAddress(String newAddress) {
		if (newAddress == null) {
			logger.debug("newAddress is null on EmailAddressImpl " + ID);
			address = null;
		}
		else {
			//	check it's vaguely feasible as an email address
			Matcher matcher = ADDRESS_PATTERN.matcher(newAddress);
			if (matcher.matches()) {
				address = newAddress;
			}
			else {
				logger.warn("newAddress " + newAddress + "does not look like a valid email address on EmailAddressImpl " + getID());
				throw new IllegalArgumentException(newAddress + " does not look like a valid email address");
			}
		}
	}

	/**
	 * @hibernate.property type="string" length="500"
         * @hibernate.column name="personalname" index="idx_personal_name"
	 * @see org.authorsite.mailarchive.domain.EmailAddress#getPersonalName()
	 */
	public String getPersonalName() {
		return personalName;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.EmailAddress#setPersonalName(java.lang.String)
	 */
	public void setPersonalName(String newPersonalName) {
		personalName = newPersonalName;
	}

	/**
	 * @hibernate.property type="boolean"
	 * @see org.authorsite.mailarchive.domain.EmailAddress#isMailingList()
	 */
	public boolean isMailingList() {
		return isMailingList;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.EmailAddress#setMailingList(boolean)
	 */
	public void setMailingList(boolean newMailingList) {
		isMailingList = newMailingList;
	}

	/**
	 * @hibernate.property type="boolean"
	 * @see org.authorsite.mailarchive.domain.EmailAddress#isProcess()
	 */
	public boolean isProcess() {
		return isProcess;
	}

	/**
	 * @see org.authorsite.mailarchive.domain.EmailAddress#setProcess(boolean)
	 */
	public void setProcess(boolean newProcess) {
		isProcess = newProcess;
	}

	/**
	 * 
	 * @see org.authorsite.mailarchive.model.EmailAddress#getPerson()
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * @hibernate.many-to-one column="PersonID" class="org.authorsite.mailarchive.model.impl.PersonImpl"
	 * @param Person newPerson  can be null, to remove any ref to a Person
	 * @see org.authorsite.mailarchive.model.EmailAddress#setPerson(org.authorsite.mailarchive.model.Person)
	 */
	public void setPerson(Person newPerson) {
		if (newPerson == null) {
			logger.debug("Received a null Person. EmailAddressImpl " + getID());
		}
		person = newPerson;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof EmailAddress) {
			EmailAddress input = (EmailAddress) o;
			return new EqualsBuilder().append(input.getAddress(), address).isEquals();
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(address).toHashCode();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("EmailAddressImpl:\n");
		sb.append("Email Address:\t");
		sb.append(getAddress());
		sb.append("\n");
		sb.append("Personal Name: \t");
		sb.append(getPersonalName());
		sb.append("Person: \t");
		sb.append(person);
		sb.append("\n");
		return sb.toString();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		EmailAddressImpl input = (EmailAddressImpl) o;
		return new CompareToBuilder().append(address, input.address).toComparison();
	}

}