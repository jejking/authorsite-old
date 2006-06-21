/*
 * VocabPersistenceException.java, created on 10-Aug-2003 at 17:33:32
 * 
 * Copyright John King, 2003.
 *
 *  VocabPersistenceException.java is part of authorsite.org's VocabManager program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.vocab.exceptions;

/**
 * A generic exception to wrap exceptions caused by writing to and reading from
 * the various persistent stores for which <code>PersistenceHandlers</code>
 * have been implemented.
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class VocabPersistenceException extends ChainedException {

	public static final String COULD_NOT_OPEN_STORE = "couldNotOpenPersistentStore";
	public static final String COULD_NOT_READ_FROM_STORE = "couldNotReadFromPersistentStore";
	public static final String COULD_NOT_WRITE_TO_STORE = "couldNotWriteToPersistentStore";
	public static final String COULD_NOT_CLOSE_STORE = "couldNotClosePersistenStore";
	

	/**
	 * Creates new VocabPersistenceException.
	 */
	public VocabPersistenceException() {
		super();
	}

	/**
	 * Creates new VocabPersistenceException.
	 * 
	 * @param message
	 */
	public VocabPersistenceException(String message) {
		super(message);
	}

	/**
	 * Creates new VocabPersistenceException.
	 * 
	 * @param message
	 * @param cause
	 */
	public VocabPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}
}
