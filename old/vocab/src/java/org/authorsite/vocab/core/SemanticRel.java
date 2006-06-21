/*
 * SemanticRel.java, created on 08-Sep-2003 at 22:56:42
 * 
 * Copyright John King, 2003.
 *
 *  SemanticRel.java is part of authorsite.org's VocabManager program.
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

/**
 * Representation of the semantic relationship between two <code>VocabNode</code>s.
 * 
 * <p>The <code>SemanticRel</code> interface represents the semantic relationship between two <code>VocabNode</code>
 * instances. It is a simple labelled edge in a graph of inter-related nodes. The label is represented by the node's <code>relationshipType</code>
 * attribute.</p>
 * 
 * <p>Implementations should ensure that the <code>relationshipType</code> comes from a simple controlled vocabulary. This
 * controlled vocabulary should also define the corresponding reciprocal relationship. For example, the relationship type "equivalence" might be the label
 * indicating semantic equivalence between synonyms and translations and this would be defined as being used in both directions. Conversely, when giving
 * examples of usage, one might define the English word "go" and define an "hasExample" relationship to "go for a walk" whilst "go for a walk" would have
 * an "isExampleOf" relationship to "go". Implementations may implement logic according to the type of relationship they represent.</p> 
 * 
 * <p>The uniqueness of a <code>SemanticRel</code> instance is defined by the combination of its relationship type, to node and from node.</p>
 *  
 * @author jejking
 * @version $Revision: 1.2 $
 */
public interface SemanticRel {
	
	/**
	 * Gets the string representing the type of semantic relationship.
	 * @return relationship type
	 */
	public String getRelationshipType();
	
	/**
	 * Gets the <code>VocabNode</code> from which the relationship originates.
	 * @return node from which this labelled edge starts
	 */
	public VocabNode getFromNode();
	
	/**
	 * Gets the id of the <code>VocabNode</code> which the relationship points to.
	 * 
	 * <p>Note, implementations are quite free to adopt a lazy loading strategy and only load the 
	 * id of the node pointed to in the first instance.</p> 
	 * @return the id of the node pointed to
	 */
	public Integer getToNodeId();
	
	/**
	 * Gets a reference to the <code>VocabNode</code> which the relationship points to.
	 * @return the node pointed to
	 */
	public VocabNode getToNode();
	
}
