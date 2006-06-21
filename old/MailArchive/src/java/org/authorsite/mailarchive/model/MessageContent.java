/*
 * Content.java, created on 06-Mar-2004 at 22:06:43
 * 
 * Copyright John King, 2004.
 *
 *  Content.java is part of authorsite.org's MailArchive program.
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
 * Abstraction of the common parts of a message component.
 * 
 * <p>Interface representing the common aspects of binary and text parts of an 
 * email message. The mime type is used to identify what type of content is being
 * represented, so that it can be handled appropriately for viewing or further processing.
 * </p>
 * 
 * <p>Additionally, the <code>MessageContent</code> can be specified as being
 * in one or more natural languages, if these are known, or can be inferred from the mail
 * headers.</p>
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public interface MessageContent extends Identifiable {
	
	public String getMimeType();

	public void setMimeType(String newMimeType);
	
	public int getSize();
	
	public void setSize(int newSize);
	
	public String getFileName();
	
	public void setFileName(String newFileName);
	
	public String getDisposition();

	public void setDisposition(String newDisposition);
	
	public String getDescription();
	
	public void setDescription(String newDescription);
	

	/**
	 * Returns Set of Languages.
	 * 
	 * @see org.authorsite.mailarchive.model.Language
	 * @return Set of Language instances
	 */
	public Set getLanguages();

	/**
	 * Specifies that the <code>MessageContent</code> is in one or more distinct
	 * Languages.
	 * 
	 * @see org.authorsite.mailarchive.model.Language
	 * @param newLanguages Set of <code>Language</code> instances
	 */
	public void setLanguages(Set newLanguages);
}