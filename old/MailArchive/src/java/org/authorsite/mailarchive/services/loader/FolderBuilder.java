/*
 * FolderBuilder.java, created on 07-Nov-2004 at 18:00:01
 * 
 * Copyright John King, 2004.
 *
 *  FolderBuilder.java is part of authorsite.org's MailArchive program.
 *
 *  MailArchive is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  MailArchive is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.services.loader;

import java.util.*;

import javax.mail.*;

/**
 * Convenience interface for obtaiining a JavaMail Folder reference. This would normally be
 * passed to the <code>EmailMesageBuilder</code> to construct persistent <code>EmailMessage</code>
 * instances.
 * 
 * Also provides a method to clear up neatly.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public interface FolderBuilder {

    public void setConnectionProperties(Properties props);
    
    public Folder getFolder() throws MessagingException;
    
    /**
     * Closes resources used to construct the Folder.
     * 
     * @throws MessagingException
     */
    public void clearUp() throws MessagingException;
}
