/*
 * EmailAddressRole.java, created on 14-Mar-2004 at 15:44:05
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressRole.java is part of authorsite.org's MailArchive program.
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

import java.io.*;

/**
 * Typesafe enumeration listing the roles an email address can play in an email message.
 * 
 * <p>Class with <code>static final</code> members listing the various, familiar
 * roles an email address can play in an email message. These correspond to the standard
 * email headers.</p>
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class EmailAddressRole implements Serializable {

	private final String role;
	
	private EmailAddressRole(String role) {
		this.role = role;
	}
	
	public String toString() {
		return role;
	}
	
	public static final EmailAddressRole FROM = new EmailAddressRole("From");
	public static final EmailAddressRole TO = new EmailAddressRole("To");
	public static final EmailAddressRole CC = new EmailAddressRole("cc");
	public static final EmailAddressRole BCC = new EmailAddressRole("bcc");
	public static final EmailAddressRole REPLY_TO = new EmailAddressRole("Reply-To");
	public static final EmailAddressRole SENDER = new EmailAddressRole("Sender");

}
