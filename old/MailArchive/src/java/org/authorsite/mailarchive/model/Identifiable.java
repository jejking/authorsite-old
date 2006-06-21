/*
 * Identifiable.java, created on 29-Mar-2004 at 23:07:57
 * 
 * Copyright John King, 2004.
 *
 *  Identifiable.java is part of authorsite.org's MailArchive program.
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
 * Allows an instance of an object to be identified by numeric ID.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public interface Identifiable extends Serializable {

	public Integer getID();

	public void setID(Integer newID);
	
}
