/*
 * StubAbstractVocabNode.java, created on 21-Sep-2003 at 16:59:58
 * 
 * Copyright John King, 2003.
 *
 *  StubAbstractVocabNode.java is part of authorsite.org's VocabManager program.
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
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class StubAbstractVocabNode extends AbstractVocabNode {

	public StubAbstractVocabNode(Integer id, String text, String nodeType, Locale locale) throws VocabException {
		super(id, text, nodeType, locale);
	}

	public StubAbstractVocabNode(Integer id, String text, String nodeType, Locale locale, Date date) throws VocabException {
		super(id, text, nodeType, locale, date);
	}

	public VocabSet getVocabSet() {
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#updateNodeText(java.lang.String)
	 */
	public void updateNodeText(String text) throws VocabException {
		//  Auto-generated method stub

	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getSemanticRels()
	 */
	public Set getSemanticRels() {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRels()
	 */
	public boolean hasSemanticRels() {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRelsOfType(java.lang.String)
	 */
	public boolean hasSemanticRelsOfType(String relationshipType) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getSemanticRelsOfType(java.lang.String)
	 */
	public Set getSemanticRelsOfType(String relationshipType) {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRelsToNode(java.lang.Integer)
	 */
	public boolean hasSemanticRelsToNode(Integer toNodeId) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRelsToNode(org.authorsite.vocab.core.VocabNode)
	 */
	public boolean hasSemanticRelsToNode(VocabNode toNode) {
		//  Auto-generated method stub
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getSemanticRelsToNode(java.lang.Integer)
	 */
	public Set getSemanticRelsToNode(Integer toNodeId) {
		//  Auto-generated method stub
		return null;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#createSemanticRel(org.authorsite.vocab.core.VocabNode, java.lang.String)
	 */
	public void createSemanticRel(VocabNode newToNode, String newRelationshipType) throws VocabException {
		//  Auto-generated method stub

	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#removeSemanticRel(org.authorsite.vocab.core.VocabNode, java.lang.String)
	 */
	public void removeSemanticRel(VocabNode node, String relationshipType) throws VocabException {
		//  Auto-generated method stub

	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#removeSemanticRel(org.authorsite.vocab.core.SemanticRel)
	 */
	public void removeSemanticRel(SemanticRel semanticRelToRemove) throws VocabException {
		//  Auto-generated method stub

	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#removeAllSemanticRels(org.authorsite.vocab.core.VocabNode)
	 */
	public void removeAllSemanticRels(VocabNode node) throws VocabException {
		//  Auto-generated method stub

	}

}
