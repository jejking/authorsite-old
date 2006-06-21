/*
 *  DatabaseException.java, created on 23-Sep-2003 at 21:45:31
 * 
 *  Copyright John King, 2003.
 *
 *  DatabaseException.java is part of authorsite.org's VocabManager program.
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
 *  Exception class encapsulating exceptional conditions for the Database Access
 * 
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class DatabaseException extends ChainedException {

    public static final String CONNECTION_MANAGER_NOT_INITIALISED = "managerNotInitialised";
    public static final String UNKNOWN_CONNECTOR_TYPE = "unknownConnectorType";
    public static final String CANNOT_CREATE_JNDI_CONTEXT="cannotCreateJndiContext";
    public static final String GET_CONNECTION_FAILED="getConnectionFailed";
    public static final String CANNOT_CLOSE_CONNECTION="cannotCloseConnection";
    public static final String CANNOT_CLOSE_POOL="cannotClosePool";
        
    public DatabaseException() {
        super("GenericDatabaseException");
    }
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException (String message, Throwable throwable) {
        super(message, throwable);
    }
}
