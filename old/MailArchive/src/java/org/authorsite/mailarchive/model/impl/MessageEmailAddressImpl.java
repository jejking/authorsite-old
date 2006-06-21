/*
 * MessageEmailAddressImpl.java, created on 09-Mar-2004 at 21:25:17
 * 
 * Copyright John King, 2004.
 *
 *  MessageEmailAddressImpl.java is part of authorsite.org's MailArchive program.
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

import java.io.*;

import org.apache.commons.lang.builder.*;
import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;

/**
 * Plain Java implementation of <code>MessageEmailAddress</code> interface.
 * 
 * 
 * @author jejking
 * @version $Revision: 1.12 $
 */
public final class MessageEmailAddressImpl implements MessageEmailAddress, Comparable, Serializable {

	private Integer version;
	private EmailAddress emailAddress;
	private EmailAddressRole role;

	private static Logger logger = Logger.getLogger(MessageEmailAddressImpl.class);

	/**
	 * Default constructor
	 *
	 */
	public MessageEmailAddressImpl() {
		logger.debug("Created new MessageEmailAddressImpl using default constructor");
	}
	
	public MessageEmailAddressImpl(EmailAddress address, EmailAddressRole role) {
	    setEmailAddress(address);
	    setRole(role);
	}

	
	/**
	 * @hibernate.many-to-one class="org.authorsite.mailarchive.model.impl.EmailAddressImpl" not-null="true"
	 * @see org.authorsite.mailarchive.model.MessageEmailAddress#getEmailAddress()
	 */
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @see org.authorsite.mailarchive.model.MessageEmailAddress#setEmailAddress(org.authorsite.mailarchive.model.EmailAddress)
	 */
	public void setEmailAddress(EmailAddress newEmailAddress) {
		emailAddress = newEmailAddress;
	}

	/**
	 * @hibernate.property name="role" type="org.authorsite.mailarchive.model.impl.hibernate.EmailAddressRoleType"
	 * @hibernate.column name="role" length="8" not-null="true"
	 * @see org.authorsite.mailarchive.model.MessageEmailAddress#getRole()
	 */
	public EmailAddressRole getRole() {
		return role;
	}

	/**
	 * @see org.authorsite.mailarchive.model.MessageEmailAddress#setRole(java.lang.String)
	 */
	public void setRole(EmailAddressRole newRole) {
		role = newRole;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (o instanceof MessageEmailAddress) {
			MessageEmailAddress input = (MessageEmailAddress) o;
			return new EqualsBuilder().append(emailAddress, input.getEmailAddress()).append(role, input.getRole()).isEquals();
		}
		else {
			return false;
		}
	}
	
	public int hashCode() {
		return new HashCodeBuilder().append(emailAddress).append(role).toHashCode();
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		MessageEmailAddressImpl input = (MessageEmailAddressImpl) o;
		return new CompareToBuilder().append(emailAddress, input.emailAddress).append(role, input.role).toComparison();
	}

}