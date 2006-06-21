/*
 * StubVocabSetFactory.java, created on 23-Nov-2003 at 18:44:19
 * 
 * Copyright John King, 2003.
 *
 *  StubVocabSetFactory.java is part of authorsite.org's VocabManager program.
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
 * Thin test stub implementation of <code>VocabSetFactory</code> 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class StubVocabSetFactory implements VocabSetFactory {

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#addProperty(java.lang.String, java.lang.String)
	 */
	public void addProperty(String key, String value) throws VocabException {
		// do nothing
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#getVocabSet()
	 */
	public VocabSet getVocabSet() throws VocabException {
		return new StubAbstractVocabSet();
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#removeProperty(java.lang.String)
	 */
	public void removeProperty(String key) throws VocabException {
		// do nothing
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSetFactory#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) throws VocabException {
		// do nothing
	}

}