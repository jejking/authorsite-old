/*
 * MemoryEmailMessageDAO.java, created on 24-Oct-2004 at 19:50:55
 * 
 * Copyright John King, 2004.
 *
 *  MemoryEmailMessageDAO.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.services.storage;

import java.util.*;

import org.authorsite.mailarchive.model.*;

/**
 * Stub implementation of EmailMessageDAO for use in testing. Holds references in memory.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class MemoryEmailMessageDAO implements EmailMessageDAO {

    private HashMap messages;
    
    /**
     * 
     */
    public MemoryEmailMessageDAO() {
        super();
        messages = new HashMap();
    }
    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#saveMessage(org.authorsite.mailarchive.model.EmailMessage)
     */
    public void saveMessage(EmailMessage message) throws ArchiveStorageException {
        messages.put(message.getMsgID(), message);
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#saveMessages(java.util.List)
     */
    public void saveMessages(List messages) throws ArchiveStorageException {
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            saveMessage((EmailMessage) it.next());
        }
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#deleteMessage(org.authorsite.mailarchive.model.EmailMessage)
     */
    public void deleteMessage(EmailMessage message) throws ArchiveStorageException {
        messages.remove(message.getMsgID());
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#deleteMessages(java.util.List)
     */
    public void deleteMessages(List messages) throws ArchiveStorageException {
        Iterator it = messages.iterator();
        while (it.hasNext()) {
            deleteMessage((EmailMessage) it.next());
        }
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getChronologically(org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getChronologically(DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByPerson(org.authorsite.mailarchive.model.Person, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getByPerson(Person person, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByPersonInRole(org.authorsite.mailarchive.model.Person, org.authorsite.mailarchive.model.EmailAddressRole, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getByPersonInRole(Person person, EmailAddressRole role, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByEmailAddress(org.authorsite.mailarchive.model.EmailAddress, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getByEmailAddress(EmailAddress addr, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending)
            throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByEmailAddressInRole(org.authorsite.mailarchive.model.EmailAddress, org.authorsite.mailarchive.model.EmailAddressRole, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getByEmailAddressInRole(EmailAddress addr, EmailAddressRole role, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending)
            throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getBySubject(java.lang.String, boolean, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getBySubject(String subject, boolean likeFlag, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

}
