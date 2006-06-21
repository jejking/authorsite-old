/*
 * AbstractSemanticRel.java, created on 08-Sep-2003 at 22:45:34
 * 
 * Copyright John King, 2003.
 *
 *  AbstractSemanticRel.java is part of authorsite.org's VocabManager program.
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
import org.authorsite.vocab.exceptions.*;
/**
 * Skeleton implementation of the <code>SemanticRel</code> interface.
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 * 
 */
public abstract class AbstractSemanticRel implements SemanticRel {

	private VocabNode fromNode;
	private String relationshipType;
	protected VocabNode toNode;
	private Integer toNodeId;

	protected AbstractSemanticRel(VocabNode fromNode, String relationshipType, Integer toNodeId) throws VocabException {
		init(fromNode, relationshipType, toNodeId);
	}

	protected AbstractSemanticRel(VocabNode fromNode, String relationshipType, VocabNode toNode) throws VocabException {
		if (toNode != null) {
			init(fromNode, relationshipType, toNode.getId());
		}
		else {
			throw new VocabException(VocabException.MISSING_TO_NODE);
		}
		// now double check to make sure that fromNode is not equal to toNode (they may have got different ids by mistake elsewhere)
		if (fromNode.equals(toNode)) {
			throw new VocabException(VocabException.REL_POINTS_TO_SELF);
		}
	}

	// utility method to check incoming values
	private void init(VocabNode fromNode, String relationshipType, Integer toNodeId) throws VocabException {
		if (fromNode != null) {
			this.fromNode = fromNode;
		}
		else {
			throw new VocabException(VocabException.MISSING_FROM_NODE);
		}
		if (relationshipType != null && relationshipType.length() > 0) {
			this.relationshipType = relationshipType;
		}
		else {
			throw new VocabException(VocabException.MISSING_RELATIONSHIP_TYPE);
		}
		if (toNodeId != null) {
			this.toNodeId = toNodeId;
		}
		else {
			throw new VocabException(VocabException.MISSING_TO_NODE);
		}

		// having set that lot up, make sure that fromNode's id is not the same as the toNodeId
		if (fromNode.getId().equals(toNodeId)) {
			throw new VocabException(VocabException.REL_POINTS_TO_SELF);
		}
	}

	/**
	 * @see org.authorsite.vocab.core.SemanticRel#getFromNode()
	 */
	public VocabNode getFromNode() {
		return fromNode;
	}

	/**
	 * @see org.authorsite.vocab.core.SemanticRel#getRelationshipType()
	 */
	public String getRelationshipType() {
		return relationshipType;
	}

	/**
	 * @see org.authorsite.vocab.core.SemanticRel#getToNode()
	 */
	public abstract VocabNode getToNode();

	/**
	 * @see org.authorsite.vocab.core.SemanticRel#getToNodeId()
	 */
	public Integer getToNodeId() {
		return toNodeId;
	}

	/**
	 * States whether the object passed in is logically equal to the node.
	 * 
	 * @return true if object is an instance of <code>SemanticRel</code> and its from node, to node and relationship type are identical, else false
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof SemanticRel) {
			// we are of the correct type, downcast and do some comparisons on values
			SemanticRel relToCheck = (SemanticRel) obj;
			if (relToCheck.getFromNode().equals(this.fromNode) && relToCheck.getToNodeId().equals(this.toNodeId) && relToCheck.getRelationshipType().equals(this.relationshipType)) {
				return true;
			}
			else {
				// logical values are different
				return false;
			}
		}
		else { // instance of wrong type
			return false;
		}
	}

	/**
	 * Calculates a hashCode for the <code>SemanticRel</code>.
	 * 
	 * <p>Provides an implementation that is consistent with equals. Implementation is as suggested by Bloch in <cite>Effective Java</cite>.</p>
	 * @see java.lang.Object#hashCode()
	 * @return int as hashCode
	 */
	public int hashCode() {
		int result = 13; // a number I thought of...
		result = 37 * result + fromNode.hashCode();
		result = 37 * result + toNodeId.hashCode();
		result = 37 * result + relationshipType.hashCode();
		return result;
	}

	/**
	 * Returns a simple String representation of the <code>SemanticRel</code>.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Semantic Relationship of type ");
		buffer.append(relationshipType);
		buffer.append(" from Node ");
		buffer.append(fromNode.getId());
		buffer.append(" to Node ");
		buffer.append(toNodeId);
		return buffer.toString();
	}

}