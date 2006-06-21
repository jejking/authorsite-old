/*
 * POP3FolderBuilder.java, created on 07-Nov-2004 at 18:04:01
 * 
 * Copyright John King, 2004.
 *
 *  POP3FolderBuilder.java is part of authorsite.org's MailArchive program.
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
public class POP3FolderBuilder implements FolderBuilder {

    private Folder folder;
    private Store store;
    private Properties props;
    
    private static Logger logger = Logger.getLogger(POP3FolderBuilder.class);
    
    /**<p>Properties must contain:</p>
     * <li>
     * <ul><code>mail.user</code></ul> as per JavaMail
     * <ul><code>mail.host</code></ul> as per JavaMail
     * <ul><code>mail.password</code></ul> not specified in JavaMail, but needed to connect to the POP3 server
     * </li>
     * <p>Property may also specify:</p>
     * <li>
     * <ul><code>mail.port</code></ul> as per JavaMail
     * </li>
     * 
     * <p>If <code>mail.port</code> is not specified, the standard port is used (110).</p>
     * 
     * @see org.authorsite.mailarchive.services.loader.FolderBuilder#setConnectionProperties(java.util.Properties)
     */
    public void setConnectionProperties(Properties props) {
        this.props = props;
    }

    /**
     * Returns folder.
     * 
     * @see org.authorsite.mailarchive.services.loader.FolderBuilder#getFolder()
     */
    public Folder getFolder() throws MessagingException {
        folder = null;
        props.setProperty("mail.store.protocol", "pop3");
        Session session = Session.getDefaultInstance(props);
        try {
            store = session.getStore();
            store.connect(props.getProperty("mail.host"), props.getProperty("mail.user"), props.getProperty("mail.password"));
            Folder root = store.getDefaultFolder();
            folder = root.getFolder("INBOX");
            folder.open(Folder.READ_ONLY);
        } 
        catch (MessagingException e) {
            logger.warn(e);
            e.printStackTrace();
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
