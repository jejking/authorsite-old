/*
 * PersonDAO.java, created on 30-Oct-2004 at 19:22:30
 * 
 * Copyright John King, 2004.
 *
 *  PersonDAO.java is part of authorsite.org's MailArchive program.
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
public interface PersonDAO {

    public void savePerson(Person person) throws ArchiveStorageException;
    
    public void deletePerson(Person person) throws ArchiveStorageException;
    
    public List getByMainName(String mainName) throws ArchiveStorageException;
    
    public List getByMainNameLike(String mainNamePattern) throws ArchiveStorageException;
    
    public List getByMainNameAndGivenName(String mainName, String givenName) throws ArchiveStorageException;
    
    public List getByMainNameLikeAndGivenNameLike(String mainNamePattern, String givenNamePattern) throws ArchiveStorageException;
    
}
