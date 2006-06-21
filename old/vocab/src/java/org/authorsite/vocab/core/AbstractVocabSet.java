/*
 * VocabSet.java, created on 08-Sep-2003 at 22:27:52
 * 
 * Copyright John King, 2003.
 *
 *  VocabSet.java is part of authorsite.org's VocabManager program.
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
/**
 * Skeleton implementation of <code>VocabSet</code> defining an internal Locale variable
 * 
 * <p>The <code>AbstractVocabSet</code> defines a mechanism which can be used for determining the standard
 * internal ordering of the <code>VocabNode.</code>s in implementation instances. This is used by the convenience
 * implementation of the compareTo method in <code>AbstractVocabNode</cod>.</p>
 * 
 * <p>Note, further work remains to be done on determining exactly how to cope with multiple requests to a <code>VocabSet</code>
 * implementation
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public abstract class AbstractVocabSet implements VocabSet {

	private Locale locale;
	
	/**
	 * Constructs the super class part of an <code>AbstractVocabSet</code> subclass implementation, setting 
	 * its Locale to the default on the system.
	 *
	 */
	public AbstractVocabSet() {
		locale = Locale.getDefault();
	}
	
	/**
	 * Constructs the super class part of an <code>AbstractVocabSet</code> subclass implementation, setting
	 * its Locale to the one defined. 
	 * @param newLocale
	 */
	public AbstractVocabSet(Locale newLocale) {
		locale = newLocale;
	}
	
	/**
	 * Gets the standard Locale defined for this <code>AbstractVocabSet</code>. 
	 * 
	 * @return the Locale defined for this <code>AbstractVocabSet</code>
	 */
	public Locale getLocale() {
		return locale;
	}

}
