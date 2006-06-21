/*
 * EmailAddressLocator.java, created on 19-Sep-2004 at 15:59:12
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressLocator.java is part of authorsite.org's MailArchive program.
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
 * Interface defining persistence and location operations on <code>EmailAddress</code> objects.
 *
 * @see org.authorsite.mailarchive.model.EmailAddress
 * @see org.authorsite.mailarchive.model.impl.EmailAdressImpl
 * @author jejking
 * @version $Revision: 1.3 $
 */
public interface EmailAddressDAO {

    
    public void saveEmailAddress(EmailAddress addressToSave) throws ArchiveStorageException;
    
    public void saveEmailAddresses(List addressesToSave) throws ArchiveStorageException;
    
    public void deleteEmailAddress(EmailAddress addressToDelete) throws ArchiveStorageException, AddressStillInUseException;
    
    public void deleteEmailAddresses(List addressesToDelete) throws ArchiveStorageException, AddressStillInUseException;
    
    public EmailAddress getEmailAddress(String addressString) throws ArchiveStorageException;
    
    public List getEmailAddressesLike(String addressPattern) throws ArchiveStorageException;
    
    public List getEmailAddressesByPerson(Person person) throws ArchiveStorageException;
    
    public List getEmailAddressesByPersonalName(String personalName) throws ArchiveStorageException;
    
    public List getEmailAddressesByPersonalNameLike(String personalNamePattern) throws ArchiveStorageException;
    
}
