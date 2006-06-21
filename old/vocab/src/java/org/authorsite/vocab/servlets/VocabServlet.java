/*
 *  VocabServlet.java, created on 23-Sep-2003 at 21:00:31
 * 
 *  Copyright Alan Tibbetts, 2003.
 *
 *  VocabException.java is part of authorsite.org's VocabManager program.
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

package org.authorsite.vocab.servlets;

import javax.servlet.http.HttpServlet;

import java.util.Properties;

import org.authorsite.vocab.util.Log4JInitialiser;
import org.authorsite.vocab.web.WebXmlConstants;
import org.authorsite.vocab.exceptions.ChainedException;
import org.authorsite.vocab.exceptions.DatabaseException;
import org.authorsite.vocab.core.memory.*;
import org.authorsite.vocab.sql.ConnectionManager;
import org.authorsite.vocab.sql.SqlConstants;
import org.authorsite.vocab.sql.DatabaseProperties;

/**
 *  Used to initialise the vocab system from within a servlet engine.  Reads
 *  parameters from web.xml to determine which initialisation stages will be 
 *  performed.  
 *  </p>
 *  <p>
 *  Parameters:
 *  <ul>
 *  <li>Log4JConfigFile - name of Log4J config file, expected to be a full path.</li> 
 *  <li>Persistence - [none,memory,postgresql]</li>
 *  </ul>
 * 
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.3 $
 */
public class VocabServlet extends HttpServlet {
    
    /**
     * Servlet initialisation.
     */
    public void init() {
        try {                
          initialiseLog4J();            
          initialisePersistence();                    
        } catch (Exception e) {
            /*
             *  If its fucked at this point, just throw the stack trace onto the 
             *  screen, there's a fair bet the logging isn't initialised, so no 
             *  point trying to put it there! 
             */
            e.printStackTrace();
        }
    }

    /**
     * Initialises the Log4J system using the parameter file specified in web.xml.
     * 
     * @throws ApplicationException
     */
    private void initialiseLog4J() throws ChainedException {
       
        String file = null;
       
       try {
          file = getInitParameter(WebXmlConstants.LOG4J_CONFIG_FILE);
        
          /*
           *  if the log4j-init-file is not set, throw an exception - we need
           *  that file!!!
           */
          if(file != null) {
              Log4JInitialiser.init(file);
          } else {
              throw new ChainedException(WebXmlConstants.LOG4J_CONFIG_FILE + 
                                         "Parameter not set in web.xml");
          }
        } catch (ChainedException ce) {
            throw ce;            
        } catch (Exception e) {
            throw new ChainedException("Unable to initialise Log4J with file : " + file);
        }
    }    

    /**
     * Performs various initialisation tasks dependant upon the persistence 
     * method specified in web.xml.
     * 
     * @throws ChainedException
     */
    private void initialisePersistence() throws ChainedException {
        
        String persistenceType = null;
        
        try {
            persistenceType = getInitParameter(WebXmlConstants.PERSISTENCE_METHOD);
            
            if (persistenceType.equalsIgnoreCase(WebXmlConstants.PERSIST_MEMORY)) {
               initialiseMemoryPersistence();                   
                 
            } else if (persistenceType.equalsIgnoreCase(WebXmlConstants.PERSIST_JNDI)) {
                
                DatabaseProperties dbProps = new DatabaseProperties();
                dbProps.setConnectorType(SqlConstants.POSTGRES_JNDI_CONNECTOR);
                dbProps.setJndiName(getInitParameter(WebXmlConstants.JNDI_DB_NAME));
                
                ConnectionManager.initialise(dbProps);
                
            } else {
                throw new ChainedException ("Unkown persistence method : " + persistenceType);
            }
            
        } catch (DatabaseException de) {
            throw de;  
        } catch (ChainedException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ChainedException("Unable to initialise persistence mechanism : " +
                                       persistenceType, e);
        }
        
    }
    
    /**
     * Initialises the memory persistence store based on values in web.xml.
     * 
     * @throws ChainedException
     */
    private void initialiseMemoryPersistence() throws ChainedException {
        
        try {
            /*
             * Create the properties object required to initialise the memory set
             * using values from web.xml.
             */
            Properties props = new Properties();
            
            String setLanguage = getInitParameter(WebXmlConstants.MEMORY_LANGUAGE_PROP);
            
            if (setLanguage != null) {
                 props.setProperty(WebXmlConstants.MEMORY_LANGUAGE_PROP, setLanguage);
            }
             
            String nodesFile = getInitParameter(WebXmlConstants.MEMORY_NODESFILE_PROP);
            
            if (nodesFile != null) {
                props.setProperty(WebXmlConstants.MEMORY_NODESFILE_PROP, nodesFile); 
            } else {
                throw new ChainedException(WebXmlConstants.MEMORY_NODESFILE_PROP +
                                           " has not been defined in web.xml.");
            }
            
            String xmlLoad = getInitParameter(WebXmlConstants.MEMORY_XML_LOAD);
            
            if (xmlLoad != null) {
                props.setProperty(WebXmlConstants.MEMORY_XML_LOAD, xmlLoad);
            }
            
            /*
             * Create the memory set.
             */ 
            MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
            factory.initialise(props);
            
        } catch (ChainedException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ChainedException("Unable to initialise memory persistence mechanism", e);
        }        
    }    
}
