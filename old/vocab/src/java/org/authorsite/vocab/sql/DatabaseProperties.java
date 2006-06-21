/*
 * DatabaseProperties.java, created on 08-Oct-2003 at 22:03:52
 * 
 * Copyright Alan Tibbetts, 2003.
 *
 *  DatabaseProperties.java is part of authorsite.org's VocabManager program.
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

/**
 *  A bespoke properties object used to initialise the connection pool for a 
 *  database.  Not all properties will be appropriate for all databases and/or
 *  connection pool types. 
 * 
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class DatabaseProperties {

    private int connectorType;
    
    private String jndiName;
    
	/**
	 * @return
	 */
	public int getConnectorType() {
		return connectorType;
	}

	/**
	 * @return
	 */
	public String getjndiName() {
		return jndiName;
	}

	/**
	 * @param i
	 */
	public void setConnectorType(int connectorType) {
		this.connectorType = connectorType;
	}

	/**
	 * @param string
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

}
