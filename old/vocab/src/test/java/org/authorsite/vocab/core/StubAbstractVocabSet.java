/*
 * StubAbstractVocabSet.java, created on 21-Sep-2003 at 16:57:33
 * 
 * Copyright John King, 2003.
 *
 *  StubAbstractVocabSet.java is part of authorsite.org's VocabManager program.
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

import org.authorsite.vocab.exceptions.VocabException;

/**
 * Test class with stub implementations of abstract methods to facilitate testing of methods
 * implemented in the <code>AbstractVocabSet</code>
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 * 
 */
public class StubAbstractVocabSet extends AbstractVocabSet {

	protected StubAbstractVocabSet() {
		super();	
	}
	
	protected StubAbstractVocabSet(Locale locale) {
		super(locale);
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#createVocabNode(java.lang.String, java.lang.String, java.util.Locale, java.util.Locale)
	 */
	public VocabNode createVocabNode(String text, String nodeType, Locale locale, Locale newDisplayLocale) throws VocabException {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#createVocabNode(java.lang.String, java.lang.String, java.util.Locale)
	 */
	public VocabNode createVocabNode(String text, String nodeType, Locale locale) throws VocabException {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#dispose()
	 */
	public void dispose() throws VocabException {
		//  Auto-generated method stub

	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#findNodes(org.authorsite.vocab.core.NodeQueryBean)
	 */
	public Set findNodes(NodeQueryBean nodeQuery) throws VocabException {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#findVocabNodeById(java.lang.Integer)
	 */
	public VocabNode findVocabNodeById(Integer id) throws VocabException {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#init()
	 */
	public void init() throws VocabException {
		//  Auto-generated method stub

	}

	/**
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		//  Auto-generated method stub

	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	public Iterator iterator() {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see java.util.Collection#size()
	 */
	public int size() {
		//  Auto-generated method stub
		return 0;
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a) {
		//  Auto-generated method stub
		return null;
	}

}
