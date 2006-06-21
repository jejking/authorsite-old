/*
 * VocabSetFactory.java, created on 10-Sep-2003 at 21:14:04
 * 
 * Copyright John King, 2003.
 *
 *  VocabSetFactory.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.core;
import java.util.*;
import org.authorsite.vocab.exceptions.*;

/**
 * Defines a generic factory interface for creating <code>VocabSet</code> instances.
 * 
 * <p>In order to provide a generic way of instantiating different implementations of
 * the <code>VocabSet</code> interface, a generic factory interface is defined. The variables
 * needed are defined as members of a <code>Properties</code> instance. It thus resembles
 * the way in which, for example, JNDI contexts are initialised.</p>
 * 
 * @author jejking
 * @version $Revision: 1.5 $
 */
public interface VocabSetFactory {

	/**
	 * 
	 * @param props
	 * @throws VocabException
	 */
	public void setProperties(Properties props) throws VocabException;
	
	public void addProperty(String key, String value) throws VocabException;
	
	public void removeProperty(String key) throws VocabException;

	public VocabSet getVocabSet() throws VocabException;

}
