/*
 * ArchiveStorageException.java, created on 14-Sep-2004 at 16:23:35
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


/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class ArchiveStorageException extends Exception {

    /**
     * @param e
     */
    public ArchiveStorageException(Exception e) {
        super(e);
    }

    /**
     * @param string
     */
    public ArchiveStorageException(String message) {
        super(message);
    }

}
