/*
 * MessageLocator.java, created on 19-Sep-2004 at 15:31:44
 * 
 * Copyright John King, 2004.
 *
 *  MessageLocator.java is part of authorsite.org's MailArchive program.
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
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public interface EmailMessageDAO {
    
    public void saveMessage(EmailMessage message) throws ArchiveStorageException;
    
    public void saveMessages(List messages) throws ArchiveStorageException;
    
    public void deleteMessage(EmailMessage message) throws ArchiveStorageException;
    
    public void deleteMessages(List messgaes) throws ArchiveStorageException;

    public List getChronologically(DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException;
    
    public List getByPerson(Person person, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException;
    
    public List getByPersonInRole(Person person, EmailAddressRole role, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException;
    
    public List getByEmailAddress(EmailAddress addr, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException;
    
    public List getByEmailAddressInRole(EmailAddress addr, EmailAddressRole role, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException;
    
    public List getBySubject(String subject, boolean likeFlag, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException;
    
}
