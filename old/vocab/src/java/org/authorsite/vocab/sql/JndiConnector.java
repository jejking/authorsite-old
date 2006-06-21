/*
 *  JndiConnector.java, created on 08-Oct-2003 at 23:12:31
 * 
 *  Copyright Alan Tibbetts, 2003.
 *
 *  JndiConnector.java is part of authorsite.org's VocabManager program.
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

import java.sql.*;
import javax.sql.*;
import javax.naming.InitialContext;
import javax.naming.Context;

import org.apache.log4j.Logger;

import org.authorsite.vocab.exceptions.DatabaseException;

/**
 *  Provides access to a sql database via JNDI.
 *  </p><p>
 *  It is assumed that the JNDI name provided at construction time points to a
 *  connection pool of some description.
 *  
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class JndiConnector implements SqlConnector {

    private static Logger log = Logger.getLogger("main");
    
    private Context jndiContext = null;
    private String jndiLookupName = null;
    
    /**
     * Construct a Jndi Connector for the given lookup name.
     * 
     * @param jndiLookupName
     */
    public JndiConnector(String jndiLookupName) {
        this.jndiLookupName = jndiLookupName;
    }
    
    /**
     * Retrieves a database connection from the connection pool referenced by the 
     * JNDI name.
     * 
     * @return
     * @throws DatabaseException
     */
    public Connection getConnection() throws DatabaseException {
        
        Connection conn = null;
        
        try {
            if (jndiContext == null) {
                jndiContext  = new InitialContext();
    
                if(jndiContext == null ) {
                    throw new DatabaseException(DatabaseException.CANNOT_CREATE_JNDI_CONTEXT);
                }
            }

            DataSource ds = (DataSource)jndiContext.lookup(jndiLookupName);

            if (ds != null) {
                if (log.isDebugEnabled()) {
                    log.debug("Created DataSource: " + jndiLookupName);
                }                
                
                conn = ds.getConnection();
            } else if (log.isDebugEnabled()) {
                log.debug("Failed to create Data Source: " + jndiLookupName);
            }
                        
        } catch (DatabaseException ae) {
            throw ae;
        } catch (Exception e) {
            DatabaseException ae = new DatabaseException(DatabaseException.GET_CONNECTION_FAILED, e);
            log.fatal(DatabaseException.getStackTrace(ae));
            throw ae;
        }
        
        log.info("Opening Connection: " + conn);
        
        return conn;
    }
    
    /**
     * Returns a previously retrieved connection to the connection pool.
     * 
     * @param conn
     * @throws DatabaseException
     */
    public void closeConnection(Connection conn) throws DatabaseException {
        
        try {
            log.info("Closing connection: " + conn);
            conn.close();
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.CANNOT_CLOSE_CONNECTION, e);
        }
    }
    
    /**
     * Closes down the JNDI based db connection pool.
     * 
     * @throws DatabaseException
     */
    public void closePool() throws DatabaseException {
        
        try {
            log.info("Closing connection pool");
            jndiContext.close();
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.CANNOT_CLOSE_POOL, e);
        }
    }
}
