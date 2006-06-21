/*
 * BinaryContent.java, created on 06-Mar-2004 at 21:55:15
 * 
 * Copyright John King, 2004.
 *
 *  BinaryContent.java is part of authorsite.org's MailArchive program.
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
 * Represents any binary content attached to an email.
 * 
 * <p>Such content might include:</p>
 * <ul>
 * <ol>images and other multimedia</ol>
 * <ol>zip files</ol>
 * <ol>office documents in propietrary formats</ol>
 * </ul>
 * 
 * <p>In fact, anything that does not have a MIME type of text/* should
 * be stored as <code>BinaryContent</code>.
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public interface BinaryContent extends MessageContent {
	
	public byte[] getContent();

	public void setContent(byte[] newContent);
}
