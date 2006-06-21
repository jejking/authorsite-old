/*
 * TextContentRole.java, created on 14-Mar-2004 at 16:57:38
 * 
 * Copyright John King, 2004.
 *
 *  TextContentRole.java is part of authorsite.org's MailArchive program.
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
 * Typesafe enumeration class specifying the possible roles a <code>TextContent</code>
 * object may play in relation to a <code>EmailMessage</code>.
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class TextContentRole implements Serializable {

	private String role;
	
	private TextContentRole(String role) {
		this.role = role;
	}
	
	public String toString() {
		return role;
	}
	
	public static final TextContentRole HEADERS = new TextContentRole("Headers");
	public static final TextContentRole MSG_BODY = new TextContentRole("Message Body");
	public static final TextContentRole BODY_PART = new TextContentRole("Body Part");
}
