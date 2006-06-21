/*
 * MemoryEmailAddressDAO.java, created on 24-Oct-2004 at 19:59:11
 * 
 * Copyright John King, 2004.
 *
 *  MemoryEmailAddressDAO.java is part of authorsite.org's MailArchive program.
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
 * Stub implementation of EmailAddressDAO for use in testing.
 * 
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class MemoryEmailAddressDAO implements EmailAddressDAO {

    private HashMap addresses;
    
    public MemoryEmailAddressDAO() {
        addresses = new HashMap();
    }
    
    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#saveEmailAddress(org.authorsite.mailarchive.model.EmailAddress)
     */
    public void saveEmailAddress(EmailAddress addressToSave) throws ArchiveStorageException {
        addresses.put(addressToSave.getAddress(), addressToSave);
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#saveEmailAddresses(java.util.List)
     */
    public void saveEmailAddresses(List addressesToSave) throws ArchiveStorageException {
        Iterator it = addressesToSave.iterator();
        while (it.hasNext()) {
            saveEmailAddress((EmailAddress) it.next());
        }
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#deleteEmailAddress(org.authorsite.mailarchive.model.EmailAddress)
     */
    public void deleteEmailAddress(EmailAddress addressToDelete) throws ArchiveStorageException {
        addresses.remove(addressToDelete);
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#deleteEmailAddresses(java.util.List)
     */
    public void deleteEmailAddresses(List addressesToDelete) throws ArchiveStorageException {
        Iterator it = addressesToDelete.iterator();
        while (it.hasNext()) {
            deleteEmailAddress((EmailAddress) it.next());
        }

    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddress(java.lang.String)
     */
    public EmailAddress getEmailAddress(String addressString) throws ArchiveStorageException {
        Object address = addresses.get(addressString);
        if (address == null) {
            return null;
        }
        else {
            return (EmailAddress) address;
        }
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesLike(java.lang.String)
     */
    public List getEmailAddressesLike(String addressPattern) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesByPerson(org.authorsite.mailarchive.model.Person)
     */
    public List getEmailAddressesByPerson(Person person) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesByPersonalName(java.lang.String)
     */
    public List getEmailAddressesByPersonalName(String personalName) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesByPersonalNameLike(java.lang.String)
     */
    public List getEmailAddressesByPersonalNameLike(String personalNamePattern) throws ArchiveStorageException {
        throw new UnsupportedOperationException();
    }

}
