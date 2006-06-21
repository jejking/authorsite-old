/*
 * AddressStillInUseException.java, created on 19 December 2004, 21:40
 * 
 * Copyright John King, 2004.
 *
 *  ArchiveStorageException.java is part of authorsite.org's MailArchive program.
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

import org.authorsite.mailarchive.model.*;

/**
 *
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class AddressStillInUseException extends Exception {
    
    private EmailAddress address;
    
    /** Creates a new instance of AddressStillInUseException */
    public AddressStillInUseException() {
    }
    
    public AddressStillInUseException(EmailAddress address) {
        this.address = address;
    }
    
}
