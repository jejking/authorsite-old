/*
 * MemoryVocabSetFactory.java, created on 10-Sep-2003 at 22:58:31
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabSetFactory.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.core.memory;

import java.util.*;
import java.io.*;

import org.apache.log4j.Logger;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.exceptions.*;
import org.authorsite.vocab.VocabConstants;

/**
 * Factory class which initialises and returns instances of <code>MemoryVocabSet</code> using values
 * defined in properties.
 * 
 * @author jejking
 * @version $Revision: 1.10 $
 */
public class MemoryVocabSetFactory implements VocabSetFactory {

	/**
	 * Holds the Properties which will be used to return correctly initialised instances.
	 */
	private Properties factoryProps = null;
	private boolean factoryPropsChanged = false;

	private static MemoryVocabSetFactory factory = null;
	private MemoryVocabSet vocabSet = null;

	private static Logger log = Logger.getLogger("org.authorsite.vocab.core.memory.MemoryVocabSetFactory");

	private MemoryVocabSetFactory() {
		super();
	}

	/**
	 * Singleton method that returns (and possibly initialises) the 
	 * MemoryVocabSetFactory.
	 * 
	 * @return
	 * @throws VocabException
	 */
	public static MemoryVocabSetFactory getInstance() throws VocabException {

		try {
			if (factory == null) {
				if (log.isDebugEnabled()) {
					log.debug("Creating a MemoryVocabFactory instance.");
				}
				factory = new MemoryVocabSetFactory();
			}
		}
		catch (Exception e) {
			throw new VocabException("Unable to retrieve the MemoryVocabSetFactory", e);
		}

		return factory;
	}

	/**
	 * Defines the properties with which the <code>MemoryVocabSet</code> should be initialised.
	 * 
	 * The most particular use for this is to specify the file of serialized objects from which the 
	 * <code>MemoryVocabSet</code> should be initialised.
	 * 
	 * @param props
	 * @throws VocabException
	 * @throws NullPointerException if Properties passed in is a null reference
	 * @see org.authorsite.vocab.core.VocabSetFactory#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) throws VocabException {
		if (props == null) {
			throw new NullPointerException();
		}
		factoryProps = (Properties) props.clone(); // clone it so we have a full, private copy and any changes must happen via this class
		vocabSet = null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#addProperty(java.lang.String, java.lang.String)
	 */
	public void addProperty(String key, String value) throws VocabException {
		if (factoryProps == null) {
			factoryProps = new Properties();
		}
		factoryProps.put(key, value);
		vocabSet = null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#removeProperty(java.lang.String)
	 */
	public void removeProperty(String key) throws VocabException {
		factoryProps.remove(key);
		vocabSet = null;
	}

	/**
	 * Returns an initialised instance of a <code>MemoryVocabSet</code>.
	 * 
	 * Use this method to get hold of an instance of <code>MemoryVocabSet</code> using the 
	 * values defined in the Factory's properties. If the VocabConstants.MEMORY_NODESFILE_PROP
	 * has been set, then the <code>MemoryVocabSet</code> will be instructed to use the file specified
	 * for its persistence operations. If that property has not been set, then the <code>MemoryVocabSet</code>
	 * will be a non-persistent, memory only instance.
	 * 
	 * @throws VocabException if no properties have been set at all
	 */
	public VocabSet getVocabSet() throws VocabException {

		if (vocabSet != null) {
			return vocabSet;
		}
		else {
			vocabSet = new MemoryVocabSet();
			if (factoryProps != null && factoryProps.containsKey(VocabConstants.MEMORY_NODESFILE_PROP)) {
				vocabSet.setNodesFile(new File(factoryProps.getProperty(VocabConstants.MEMORY_NODESFILE_PROP)));
			}
			vocabSet.init();
		}
		return vocabSet;
	}
	
	/**
	 * @deprecated use setProperties
	 * 
	 * @param props
	 * @throws VocabException
	 */
	public void initialise(Properties props) throws VocabException {
		setProperties(props);
	}

}
