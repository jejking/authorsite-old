/*
 * MdirFolderBuilder.java, created on 07-Nov-2004 at 18:03:38
 * 
 * Copyright John King, 2004.
 *
 *  MdirFolderBuilder.java is part of authorsite.org's MailArchive program.
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

import org.apache.log4j.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class MaildirFolderBuilder implements FolderBuilder {

    private static Logger logger = Logger.getLogger(MaildirFolderBuilder.class);
    
    private Properties props = new Properties();
    private Folder folder;
    private Store store;
    
    /**
     * @param Properties Must contain property maildir.path with full path to the maildir to load
     * @see org.authorsite.mailarchive.services.loader.FolderBuilder#setConnectionProperties(java.util.Properties)
     */
    public void setConnectionProperties(Properties props) {
        this.props = props;
    }

    /**
     * @see org.authorsite.mailarchive.services.loader.FolderBuilder#getFolder()
     */
    public Folder getFolder() throws MessagingException {
        folder = null;
        StringBuffer sb = new StringBuffer();
        sb.append("maildir://");
        sb.append(props.get("maildir.path"));
        URLName urlName = new URLName(sb.toString());
        Session session = Session.getDefaultInstance(props);
        try {
            store = session.getStore(urlName);
            folder = store.getDefaultFolder();
            folder.open(Folder.READ_ONLY);
        } 
        catch (MessagingException e) {
            logger.warn(e);
            throw e;
        }
        return folder;
    }

    /**
     * @see org.authorsite.mailarchive.services.loader.FolderBuilder#clearUp()
     */
    public void clearUp() throws MessagingException {
        folder.close(false);
        store.close();
    }

}
