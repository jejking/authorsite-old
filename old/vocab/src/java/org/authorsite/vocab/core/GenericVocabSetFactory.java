/*
 * GenericVocabSetFactory.java, created on 03-Nov-2003 at 22:17:42
 * 
 * Copyright John King, 2003.
 *
 *  GenericVocabSetFactory.java is part of authorsite.org's VocabManager program.
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

import java.lang.reflect.*;
import java.util.Properties;

import org.authorsite.vocab.*;
import org.authorsite.vocab.exceptions.VocabException;

/**
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class GenericVocabSetFactory implements VocabSetFactory {

	private static GenericVocabSetFactory instance;

	private VocabSetFactory vocabSetFactory;
	private VocabSet vocabSet;
	private Properties factoryProps;
	private boolean factoryPropsChanged = false;

	private GenericVocabSetFactory() {
	}

	public static GenericVocabSetFactory getInstance() {
		if (instance == null) {
			instance = new GenericVocabSetFactory();
		}
		return instance;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) throws VocabException {
		if (props == null) {
			throw new NullPointerException();
		}
		factoryProps = (Properties) props.clone(); // clone it so we have a full, private copy and any changes must happen via this class
		vocabSet = null;
		vocabSetFactory = null; // full reset of properties
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
		if (key.equals(VocabConstants.VOCAB_FACTORY_CLASS)) {
			vocabSetFactory = null; // reset the internal vocabSetFactory completely
		}
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#removeProperty(java.lang.String)
	 */
	public void removeProperty(String key) throws VocabException {
		if (factoryProps != null && key != null) {
			factoryProps.remove(key);
			vocabSet = null;
		}
		else {
			throw new NullPointerException();
		}
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#getVocabSet()
	 */
	public VocabSet getVocabSet() throws VocabException {
		if (vocabSet != null) {
			return vocabSet;
		}

		if (vocabSetFactory == null) {
			if (factoryProps != null && factoryProps.containsKey(VocabConstants.VOCAB_FACTORY_CLASS)) {
				try {
					Class factoryClass = Class.forName(factoryProps.getProperty(VocabConstants.VOCAB_FACTORY_CLASS));

					// if we have a default public constructor, use it..
					Constructor[] constructors = factoryClass.getConstructors();
					if (constructors.length > 0) {
						vocabSetFactory = (VocabSetFactory) factoryClass.newInstance();
					}
					else { // else locate the standard singleton static factory method and invoke it
						Method factoryMethod = factoryClass.getMethod("getInstance", new Class[0]);
						vocabSetFactory = (VocabSetFactory) factoryMethod.invoke(null, null);
					}
				}
				catch (ClassNotFoundException cnfe) {
					throw new VocabException(VocabException.INITIALIZATION, cnfe);
				}
				catch (InstantiationException ie) {
					throw new VocabException(VocabException.INITIALIZATION, ie);
				}
				catch (IllegalAccessException iae) {
					throw new VocabException(VocabException.INITIALIZATION, iae);
				}
				catch (SecurityException se) {
					throw new VocabException(VocabException.INITIALIZATION, se);
				}
				catch (NoSuchMethodException nsme) {
					throw new VocabException(VocabException.INITIALIZATION, nsme);
				}
				catch (IllegalArgumentException iae) {
					throw new VocabException(VocabException.INITIALIZATION, iae);
				}
				catch (InvocationTargetException ite) {
					throw new VocabException(VocabException.INITIALIZATION, ite);
				}
			}
			else {
				throw new VocabException(VocabException.INITIALIZATION);
			}
		}

		vocabSetFactory.setProperties(factoryProps);
		vocabSet = vocabSetFactory.getVocabSet();
		
		return vocabSet;
	}

}
