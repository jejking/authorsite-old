/*
 *  WebXmlConstants.java, created on 23-Sep-2003 at 21:00:31
 * 
 *  Copyright Alan Tibbetts, 2003.
 *
 *  WebXmlConstants.java is part of authorsite.org's VocabManager program.
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

package org.authorsite.vocab.web;
/**
 *  A list of constants relating to nodes within the web.xml file.  These are either
 *  node names or node values.
 * 
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class WebXmlConstants {

    /*
     * Test Initialisation constants.
     */
     
    public static final String LOG4J_CONFIG_FILE = "Log4JConfigFile";
    public static final String PERSISTENCE_METHOD = "Persistence";
    
    public static final String PERSIST_MEMORY = "memory";
    public static final String PERSIST_JNDI = "jndi";
        
    /*
     * Memory Persistence Constants
     */
    
    public static final String MEMORY_LANGUAGE_PROP = "SetLanguage";
    public static final String MEMORY_NODESFILE_PROP = "NodesFile"; 
    public static final String MEMORY_XML_LOAD = "XmlLoad";
    
    /*
     *  JNDI Persistence constants
     */
    public static final String JNDI_DB_NAME = "JndiDbName";
}
