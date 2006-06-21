/*
 * MailMessage.java, created on 06-Mar-2004 at 20:45:39
 * 
 * Copyright John King, 2004.
 *
 *  MailMessage.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.model;

import java.util.*;

/**
 * Abstraction of an  Email Message.
 * 
 * <p>In the Mailarchive application, email messages are broken down into their 
 * constituent parts in order to enable objects to be enter into associations that go
 * beyond the store/folder view of JavaMail.</p>
 * 
 * <p>One consequence of this is that the <code>EmailMessage</code> interface
 * specifies a set of central headers as member properties and regards the remainder as
 * associations. This applies to:</p>
 * 
 * <ul>
 * <li>Email Addresses</li>
 * <li>Text Content</li>
 * <li>Binary Content</li>
 * </ul>
 * 
 * <p>Note that the Email Address association is implemented using <code>MessageEmailAddress</code>
 * association class. These relate an email address to an email message using an 
 * <code>EmailAddressRole</code>.</p>
 * 
 * <p>Text content is related using a collection of <code>TextContent</code> objects. This interface
 * also has a built in notion of the role played by the particular <code>TextContent</code> in the 
 * constitution of the related email message, as defined by the typesafe enumeration <code>TextContentRole</code>.
 * It is up to the implementation classes to ensure that a given email message only has one Text Content
 * in the Headers Role, one in the Body Role and one or more in the Body Part Role.</p>
 * 
 * @see org.authorsite.mailarchive.model.MessageEmailAddress
 * @see org.authorsite.mailarchive.model.TextContent
 * @see org.authorsite.mailarchive.model.BinaryContent
 * @author jejking
 * @version $Revision: 1.5 $
 */
public interface EmailMessage extends Identifiable {

	// DATES

	public Date getSentDate();

	public void setSentDate(Date newSentDate);

	public Date getReceivedDate();

	public void setReceivedDate(Date newReceivedDate);

	// SUBJECT
	
	public String getSubject();

	public void setSubject(String newSubject);

	// MSG ID related
	
	public String getMsgID();

	public void setMsgID(String newMsgID);

	public String getInReplyTo();

	public void setInReplyTo(String newInReplyTo);

	public String getMsgReferences();

	public void setMsgReferences(String newMsgReferences);

	// RELATED MessageEmailAddresses
	
	public Set getMessageEmailAddresses();
	
	public void setMessageEmailAddresses(Set newMessageEmailAddresses);
	
	public Set getMessageEmailAddressesInRole(EmailAddressRole role);
	
	public void addMessageEmailAddress(MessageEmailAddress newMessageEmailAddress);
	
	public void removeMessageEmailAddress(MessageEmailAddress messageEmailAddressToRemove);

	public Set getTextParts();

	public void setTextParts(Set newTextParts);
	
	public void addTextPart(TextContent newTextContent);
	
	public void removeTextContent(TextContent textContentToRemove);

	public Set getBinaryParts();

	public void setBinaryParts(Set newBinaryParts);
	
	public void addBinaryPart(BinaryContent newBinaryContent);
	
	public void removeBinaryPart(BinaryContent binaryContentToRemove);

	// MISC HEADERS WORTH CAPTURING IN INDIVIDUAL FIELDS

	public String getSensitivity();

	public void setSensitivity(String newSensitivity);

	public String getImportance();
	
	public void setImportance(String newImportance);

}
