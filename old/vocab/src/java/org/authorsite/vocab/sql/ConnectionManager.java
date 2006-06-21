/*
 *  ConnectionManager.java, created on 08-Oct-2003 at 23:12:31
 * 
 *  Copyright Alan Tibbetts, 2003.
 *
 *  ConnectionManager.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.sql;
import java.sql.Connection;

import org.apache.log4j.Logger;

import org.authorsite.vocab.exceptions.ChainedException;
import org.authorsite.vocab.exceptions.DatabaseException;

/**
 *  Manages connections to a database.
 *  </p><p>
 *  The connection manager should be initialised using a set of external parameters.
 *  These are initially envisioned to have come from Tomcat's web.xml file.  These
 *  will determine the type of connector used and the specific properties of that
 *  connector.
 *  </p><p>
 *  Once the manager has been initialised, connections can be requested with code such
 *  as:
 *  <code>
 *  ConnectionManager manager = ConnectionManager.getInstance();
 *  Connection conn = manager.getConnection();
 *  </code>
 *  When the connection is no longer required, it should be released as per...
 *  <code>
 *  manager.closeConnection(conn);
 *  </code>
 *  </p><p>
 *  When the application closes done, the connection manager should be told to close
 *  the connection pool...
 *  <code>
 *  manager.closePool();
 *  </code>
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class ConnectionManager {

    private static Logger log = Logger.getLogger("main");
    
    private static ConnectionManager manager = null;
    
    private SqlConnector connector = null;
    
    /**
     * This class is a singleton, so shouldn't be able to instantiate from outside the
     * class...
     * 
     * @param connectorType
     */
    private ConnectionManager (DatabaseProperties dbProps) throws DatabaseException {
        
        try {                    
            switch (dbProps.getConnectorType()) {
                case SqlConstants.POSTGRES_JNDI_CONNECTOR 
                                : connector = new JndiConnector(dbProps.getjndiName());     
                                  log.info("Created a Postgres JNDI Connector");
                                  break;
                default:
                    log.fatal("Unknown connector type: " + dbProps.getConnectorType());
                    throw new DatabaseException (DatabaseException.UNKNOWN_CONNECTOR_TYPE);
            }
            
        } catch (DatabaseException de) {
            throw de;
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.CONNECTION_MANAGER_NOT_INITIALISED, e);
        }
    }
    
    /**
     * Initialises the connection manager with the specified type of connector.
     * 
     * @param connectorType
     * @throws ChainedException
     */
    public static void initialise (DatabaseProperties dbProps) throws DatabaseException {
        manager = new ConnectionManager(dbProps);
    }
    
    /**
     * Returns the connection manager, or throws an exception if it has yet to be
     * initialised.
     * 
     * @return ConnectionManager
     * @throws ChainedException
     */
    public static ConnectionManager getInstance() throws DatabaseException {
        
        if (manager == null) {
            if (log.isDebugEnabled()) {
                log.debug("Creating Connection Manager");
            }
            throw new DatabaseException(DatabaseException.CONNECTION_MANAGER_NOT_INITIALISED);
        }
                    
        return manager;
    }
    
    /**
     * Uses the current connector to retrieve a connection to the database.
     * 
     * @return
     * @throws ChainedException
     */
    public Connection getConnection() throws ChainedException {        
        return connector.getConnection();        
    }
    
    /**
     * Uses the current connector to close a connection to the database.
     * 
     * @param conn
     * @throws ChainedException
     */
    public void closeConnection(Connection conn) throws ChainedException {
        connector.closeConnection(conn);
    }
    
    /**
     * Uses the current connector to close the connection pool.
     * 
     * @throws ChainedException
     */
    public void closePool() throws ChainedException {
        connector.closePool();        
    }
}
