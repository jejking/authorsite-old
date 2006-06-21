/*
 * MemoryVocabNode.java, created on 09-Sep-2003 at 22:58:59
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabNode.java is part of authorsite.org's VocabManager program.
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

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.core.dto.*;
import org.authorsite.vocab.exceptions.*;

/**
 * Implementation of <code>VocabNode</code> in memory.
 * 
 * @author jejking
 * @version $Revision: 1.10 $
 * @see org.authorsite.vocab.core.VocabNode
 */
public class MemoryVocabNode extends AbstractVocabNode {

	private MemoryVocabSet memoryVocabSet;
	private Set semanticRelSet;

	protected MemoryVocabNode(Integer id, String text, String nodeType, Locale locale, MemoryVocabSet memoryVocabSet) throws VocabException {
		super(id, text, nodeType, locale);
		if (memoryVocabSet != null) {
			this.memoryVocabSet = memoryVocabSet;
		}
		else {
			throw new VocabException(VocabException.INITIALIZATION);
		}
		semanticRelSet = new SemanticRelSet();
	}

	protected MemoryVocabNode(Integer id, String text, String nodeType, Locale locale, Date date, MemoryVocabSet memoryVocabSet) throws VocabException {
		super(id, text, nodeType, locale, date);
		if (memoryVocabSet != null) {
			this.memoryVocabSet = memoryVocabSet;
		}
		else {
			throw new VocabException(VocabException.INITIALIZATION);
		}
		semanticRelSet = new SemanticRelSet();
	}

	protected MemoryVocabNode(VocabNodeDTO serialized, MemoryVocabSet memoryVocabSet) throws VocabException {
		super (serialized.getId(), serialized.getText(), serialized.getNodeType(), serialized.getLocale(), serialized.getDateCreated());
		
		if (memoryVocabSet == null ) {
			throw new VocabException(VocabException.INITIALIZATION);
		}
		
		this.memoryVocabSet = memoryVocabSet;
		setAnnotation(serialized.getAnnotation());
		setRegister(serialized.getRegister());
		
		try {
			semanticRelSet = new SemanticRelSet();
			// extract the serialized semantic rels
			Set serializedSemanticRelSet = serialized.getSemanticRels();
			Iterator it = serializedSemanticRelSet.iterator();
			while (it.hasNext()) {
				SemanticRelDTO rel = (SemanticRelDTO) it.next();
				semanticRelSet.add(new MemorySemanticRel(this, rel.getRelationshipType(), rel.getToNodeId()));
			}
		}
		catch (NullPointerException npe) {
			throw new VocabException(VocabException.INITIALIZATION, npe);
		}
		catch (ClassCastException cce) {
			throw new VocabException(VocabException.INITIALIZATION, cce);
		}
	}

	protected void setText(String newText) {
		super.setText(newText);
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getSemanticRels()
	 */
	public Set getSemanticRels() {
		return new HashSet(semanticRelSet); // don't give anyone else a reference to our internal set
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRels()
	 */
	public boolean hasSemanticRels() {
		return semanticRelSet.size() >= 1;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRelsOfType(java.lang.String)
	 */
	public boolean hasSemanticRelsOfType(String relationshipType) {
		Iterator it = semanticRelSet.iterator();
		while (it.hasNext()) {
			SemanticRel nextSemanticRel = (SemanticRel) it.next();
			if (nextSemanticRel.getRelationshipType().equals(relationshipType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getSemanticRelsOfType(java.lang.String)
	 */
	public Set getSemanticRelsOfType(String relationshipType) {
		Set results = new HashSet();
		if (!this.hasSemanticRels()) {
			return results; // there are no relationships, so don't even bother iterating trying to look for relationships of a particular type
		}
		Iterator it = semanticRelSet.iterator();
		while (it.hasNext()) {
			SemanticRel nextSemanticRel = (SemanticRel) it.next();
			if (nextSemanticRel.getRelationshipType().equals(relationshipType)) {
				results.add(nextSemanticRel);
			}
		}
		return results;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRelsToNode(java.lang.Integer)
	 */
	public boolean hasSemanticRelsToNode(Integer toNodeId) {
		if (this.getId().equals(toNodeId)) {
			return false; // obviously, as this isn't allowed at all
		}
		Iterator it = this.getSemanticRels().iterator();
		while (it.hasNext()) {
			SemanticRel semanticRel = (SemanticRel) it.next();
			if (semanticRel.getToNodeId().equals(toNodeId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#hasSemanticRelsToNode(org.authorsite.vocab.core.VocabNode)
	 */
	public boolean hasSemanticRelsToNode(VocabNode toNode) {
		if (toNode.equals(this)) {
			return false; // it can't have relationships to itself
		}
		Iterator it = this.getSemanticRels().iterator();
		while (it.hasNext()) {
			SemanticRel semanticRel = (SemanticRel) it.next();
			if (semanticRel.getToNodeId().equals(toNode.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#getSemanticRelsToNode(java.lang.Integer)
	 */
	public Set getSemanticRelsToNode(Integer toNodeId) {
		Set results = new HashSet();
		if (!hasSemanticRels()) {
			return results; // return empty set. we know there are no semantic rels, so don't bother trying to find rels pointing to a particular node
		}
		else {
			Iterator it = semanticRelSet.iterator();
			while (it.hasNext()) {
				SemanticRel nextSemanticRel = (SemanticRel) it.next();
				if (nextSemanticRel.getToNodeId().equals(toNodeId)) {
					results.add(nextSemanticRel);
				}
			}
			return results;
		}
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#createSemanticRel(org.authorsite.vocab.core.VocabNode, java.lang.String)
	 */
	public void createSemanticRel(VocabNode newToNode, String newRelationshipType) throws VocabException {
		// examine the incoming node to make sure it's ok - get its id, fetch the existing node out, compare.
		// If we can't find it, that's bad. Or, if we can and it's not equal, it's a duff replica of a node in the set. That's bad too.
		// only add the existing node reference to avoid external manipulation and to help retain set integrity
		VocabNode existingNode = memoryVocabSet.findVocabNodeById(newToNode.getId());
		if (existingNode != null && existingNode.equals(newToNode)) {
			SemanticRel newRel = new MemorySemanticRel(this, newRelationshipType, existingNode);
			semanticRelSet.add(newRel);		
		}
		else {
			throw new VocabException(VocabException.INVALID_NODE);
		}
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#removeSemanticRel(org.authorsite.vocab.core.VocabNode, java.lang.String)
	 */
	public void removeSemanticRel(VocabNode node, String relationshipType) throws VocabException {
		Iterator semanticRelIt = semanticRelSet.iterator();
		while (semanticRelIt.hasNext()) {
			SemanticRel currentRel = (SemanticRel) semanticRelIt.next();
			if (currentRel.getToNodeId().equals(node.getId()) && currentRel.getRelationshipType().equals(relationshipType)) {
				semanticRelSet.remove(currentRel);
				break;
			}
		}
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#removeSemanticRel(org.authorsite.vocab.core.SemanticRel)
	 */
	public void removeSemanticRel(SemanticRel semanticRelToRemove) throws VocabException {
		semanticRelSet.remove(semanticRelToRemove);
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#removeAllSemanticRels(org.authorsite.vocab.core.VocabNode)
	 */
	public void removeAllSemanticRels(VocabNode node) throws VocabException {
		Iterator it = semanticRelSet.iterator();
		HashSet removalSet = new HashSet();
		while (it.hasNext()) {
			SemanticRel rel = (SemanticRel) it.next();
			if (rel.getToNode() != null && rel.getToNode().equals(node)) {
				removalSet.add(rel);
			}
		}
		semanticRelSet.removeAll(removalSet);
	}

	/**
	 * @see org.authorsite.vocab.core.VocabNode#updateNodeText(org.authorsite.vocab.core.VocabNode, java.lang.String)
	 */
	public void updateNodeText(String text) throws VocabException {
		memoryVocabSet.updateNodeText(this, text); // call back to the vocab set to ensure integrity of the set
	}

	public VocabSet getVocabSet() {
		return memoryVocabSet;
	}

	/**
	 * Factory method to obtain the ultra lightweight bean representation of the data for serialization.
	 *  
	 * Note that this also includes the equivalent bean representations of all the <code>MemorySemanticRel</code>s as well.
	 * 
	 * @return bean representation of the node data
	 */
	public VocabNodeDTO getVocabNodeDTO() {
		VocabNodeDTO serialized = new VocabNodeDTO();
		// call all the setters
		serialized.setAnnotation(getAnnotation());
		serialized.setDateCreated(getDateCreated());
		serialized.setId(getId());
		serialized.setLocale(getLocale());
		serialized.setNodeType(getNodeType());
		serialized.setRegister(getRegister());
		serialized.setText(getText());

		// set up the serialized forms of the semantic rels
		Iterator relsIt = semanticRelSet.iterator();
		while (relsIt.hasNext()) {
			MemorySemanticRel rel = (MemorySemanticRel) relsIt.next();
			serialized.addSemanticRel(rel.getSerializableMemorySemanticRel());
		}
		return serialized;
	}

	/**
	 * Private implementation of <code>Set</code> used to manage <code>MemorySemanticRel</code>s
	 * belonging to the class.
	 * 
	 * @author jejking
	 */
	private class SemanticRelSet implements Set {

		private Set internalSet;

		public SemanticRelSet() {
			internalSet = new HashSet();
		}

		/**
		* @see java.util.Collection#add(java.lang.Object)
		*/
		public boolean add(Object o) {
			if (o instanceof SemanticRel) {
				internalSet.add(o);
				return true;
			}
			else {
				return false;
			}
		}

		/**
		 * @see java.util.Collection#addAll(java.util.Collection)
		 */
		public boolean addAll(Collection c) {
			return false;
		}

		/**
		 * @see java.util.Collection#clear()
		 */
		public void clear() {
			internalSet.clear();
		}

		/**
		 * @see java.util.Collection#contains(java.lang.Object)
		 */
		public boolean contains(Object o) {
			return internalSet.contains(o);
		}

		/**
		 * @see java.util.Collection#containsAll(java.util.Collection)
		 */
		public boolean containsAll(Collection c) {
			return internalSet.containsAll(c);
		}

		/**
		 * @see java.util.Collection#isEmpty()
		 */
		public boolean isEmpty() {
			return internalSet.isEmpty();
		}

		/**
		 * @see java.util.Collection#iterator()
		 */
		public Iterator iterator() {
			return internalSet.iterator();
		}

		/**
		 * @see java.util.Collection#remove(java.lang.Object)
		 */
		public boolean remove(Object o) {
			return internalSet.remove(o);
		}

		/**
		 * @see java.util.Collection#removeAll(java.util.Collection)
		 */
		public boolean removeAll(Collection c) {
			return internalSet.removeAll(c);
		}

		/**
		 * @see java.util.Collection#retainAll(java.util.Collection)
		 */
		public boolean retainAll(Collection c) {
			return false;
		}

		/**
		 * @see java.util.Collection#size()
		 */
		public int size() {
			return internalSet.size();
		}

		/**
		 * @see java.util.Collection#toArray()
		 */
		public Object[] toArray() {
			return internalSet.toArray();
		}

		/**
		 * @see java.util.Collection#toArray(java.lang.Object[])
		 * @throws IllegalArgumentException if array is not of type <code>MemorySemanticRel</code>
		 */
		public Object[] toArray(Object[] a) {
			if (a.getClass().getComponentType().equals(SemanticRel.class)) {
				return internalSet.toArray(a);
			}
			else {
				throw new IllegalArgumentException();
			}
		}

	}

}
