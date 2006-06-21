/*
 * TextContent.java, created on 06-Mar-2004 at 21:54:44
 * 
 * Copyright John King, 2004.
 *
 *  TextContent.java is part of authorsite.org's MailArchive program.
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
 * Abstraction of any textual content constituting an email address.
 * 
 * <p><code>TextContent</code> is intended to support all text content associated
 * with an email message. This includes the full headers, stored in case they are needed,
 * but not for full searching, the main message body, in the case where the message
 * has not been broken down in the original into mime parts, and any textual attachments.</p>
 * 
 * <p>The precise role played is determined by the <code>TextContentRole</code>
 * type safe enumeration. Implementations must take care to ensure the logical
 * integrity of the records</p>
 * 
 * @see org.authorsite.mailarchive.model.TextContentRole
 * @author jejking
 * @version $Revision: 1.3 $
 */
public interface TextContent extends MessageContent {

	public String getContent();
	
	public void setContent(String newContent);
	
	public TextContentRole getRole();
	
	public void setRole(TextContentRole newRole);
	
}
