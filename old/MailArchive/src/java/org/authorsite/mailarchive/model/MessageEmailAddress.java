/*
 * EmailMessageAddress.java, created on 07-Mar-2004 at 23:11:58
 * 
 * Copyright John King, 2004.
 *
 *  EmailMessageAddress.java is part of authorsite.org's MailArchive program.
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

/**
 * Representation of an Email Address in a particular role.
 * 
 * <p>Essentially an association interface linking an <code>EmailMessage</code>
 * with an <code>EmailAddress</code> but with the additional information about the
 * role, embodied by an <code>EmailAddressRole</code> that the address play in the
 * message in question - such as whether it is From, To, cc'ed that email Address.</p>
 *
 * @see org.authorsite.mailarchive.model.EmailMessage
 * @see org.authorsite.mailarchive.model.EmailAddress
 * @see org.authorsite.mailarchive.model.EmailAddressRole
 *  
 * @author jejking
 * @version $Revision: 1.3 $
 */
public interface MessageEmailAddress {

	public EmailAddress getEmailAddress();

	public void setEmailAddress(EmailAddress newEmailAddress);

	public EmailAddressRole getRole();

	public void setRole(EmailAddressRole newRole);

}
