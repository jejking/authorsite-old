/*
 * VocabNode.java, created on 08-Sep-2003 at 22:56:54
 * 
 * Copyright John King, 2003.
 *
 *  VocabNode.java is part of authorsite.org's VocabManager program.
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

import org.authorsite.vocab.core.dto.*;
import org.authorsite.vocab.exceptions.*;
/**
 * Representation of an individual item in a <code>VocabSet</code>.
 * 
 * <p>A <code>VocabNode</code> is a generic abstraction of a discrete item of vocabulary in a human language. The item of 
 * vocabulary has three key features:</p>
 * <table>
 * 	<tr>
 * 		<td>Text</td>
 * 		<td>A String representing the sequence of characters which represent the item in a natural language. The 
 * 		item should not be thought of as necessarily being a single word or indeed a single lexeme. The system should
 * 		also be able to provide nodes which exemplify the use of particular words or phrases and should be able to 
 * 		capture complex idiomatic expressions.</td>
 * 	</tr>
 * 	<tr>
 * 		<td>Locale</td>
 * 		<td>Because each <code>VocabNode</code> represents an item of vocabulary in a human language, that language
 * 		must be specified, here as a Java Locale object. Care should be taken to use Locale to specify a particular language as some
 * 		country Locales will have multiple languages associated with them - such as French and Flemish in Belgium. The model does not 
 * 		currently provide for an adequate coverage of word borrowings from one language to another. For the purposes of this model,
 * 		the world <cite>Weltgeist</cite>, which is obviously German but used in English should be documented as being English when
 * 		documenting its English language use. The definition of Locale also helps disambiguate words with identical spellings in different
 * 		languages.</td>
 * 	</tr>
 * 	<tr>
 * 		<td>NodeType</td>
 * 		<td>Each <code>VocabNode</code> should be assigned a NodeType from a controlled vocabulary. This will normally be a list of
 * 		core grammatical elements: noun, verb, adjective, adverb, conjunction, etc. This is important to help disambiguate some homographs. An
 * 		example might be the English word "run", which can be a verb, as in to run somewhere, or a noun, as in to go for a run.</td>
 * 	</tr>
 * </table>
 * <p>A <code>VocabNode</code> also provides some further simple data about itself:</p>
 * <ul>
 * 	<li>Date node created: allows for tracking of the progress of entering vocabulary items and to extract items accordingly.</li>
 * 	<li>Annotation. Allows users to attach arbitrary text discussing the node, its use, etc.</li>
 * 	<li>Register. Should be entered from a controlled vocabulary to describe the register of the language node, i.e the social context in which
 * 	the node might be used.</li>
 * </ul>
 * <p>Whilst a <code>VocabNode</code> provides a degree of information in its own right, it only acquires its full semantic value when
 * located in the context of other <code>VocabNode</code>s in a <code>VocabSet</code>. Now, whilst the <code>VocabSet</code> behaves
 * as a straightforward set with some additional methods, including creational methods, the <code>VocabNode</code>s can be interlinked by 
 * semantic relationships, modelled as implementations of the <code>SemanticRel</code> interface. In this sense, <code>VocabNode</code>s are
 * nodes interlinked by edges. In the current model, we effectively have a labelled directed graph of <code>VocabNode</code>s interlinked by
 * <code>SemanticRel</code>s.</p>
 * <p>Implementations of <code>VocabNode</code> provide methods to query and manage the <code>SemanticRel</code>s which point
 * <em>from</em> it to other nodes. In almost every case, the creation of a semantic relationship from one node to another should be accompanied
 * by the creation of the appropriate reciprocal relationship back.</p> 
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public interface VocabNode extends Comparable {

	/**
	 * Gets the unique Integer object representing the node's id.
	 * 
	 * <p>Note that implementations should make id immutable. There should be no corresponding public setter method.</p>
	 * 
	 * @return Integer representing the node's id.
	 */
	public Integer getId();
	
	/**
	 * Gets the text that the node represents and contextualises.
	 * 
	 * <p>Note that there is no directly corresponding public setter method. The text of a node is a component of its unique identity
	 * within a <code>VocabSet</code> and changes to it must be managed carefully by using the <code>updateNodeText</code> method
	 * </p>
	 * 
	 * @see org.authorsite.vocab.core.VocabNode#updateNodeText(String)
	 * @return the text represented by the node object
	 */
	public String getText();
	
	/**
	 * Tests that the proposed change will not compromise the uniqueness of the node and if it does not changes it accordingly.
	 * 
	 * <p>The uniqueness of a <code>VocabNode</code> is described by the combination of its text, locale and nodeType attributes. Editing
	 * the text thus poses a risk that the uniqueness of the <code>VocabNode</code> might be compromised. Rather than presenting a public
	 * setter method, implementations should use this method to examine the new text before making changes to the node in question. Only when
	 * it has been ascertained that the new text does not compromise the uniqueness of the node should the text be updated.</p>
	 * 
	 * @param text new text
	 * @throws VocabException if the text breaks the uniqueness constraint
	 */
	public void updateNodeText(String text) throws VocabException;
	
	/**
	 * Returns the Locale that describes the human language of the node.
	 * 
	 * @return the node's Locale 
	 */
	public Locale getLocale();
	
	/**
	 * Gets the Date object recording when the node was created.
	 * 
	 * <p>Note, implementations should make their representation of when an object was created immmutable. There should be 
	 * no corresponding setter method.</p>
	 * 
	 * @return Date recording when the node was created.
	 */
	public Date getDateCreated();
	
	/**
	 * Gets the String representing the annotation on the node.
	 * 
	 * @return node annotation
	 */
	public String getAnnotation();
	
	/**
	 * Sets or updates the annotation on the node.
	 * 
	 * <p>The annotation has absolutely no impact on the uniqueness of the node and can thus be edited at will by using a public setter method.</p>
	 *
	 *  @param annotation String representing the new annoation.
	 */
	public void setAnnotation(String annotation);
	
	/**
	 * Gets a string representing the node's <code>NodeType</code>
	 * 
	 * <p>Note, implementations should make their representation of when an object was created immmutable. There should be 
	 * no corresponding setter method.</p>
	 * 
	 * @return node type
	 */
	public String getNodeType();
	
	/**
	 * Gets string representing the node's register, if one has been set.
	 * 
	 * @return string representing the node's register, null if not set
	 */
	public String getRegister();
	
	/**
	 * Sets or updates the string representing the node's register.
	 * 
	 * <p>The register has absolutely no impact on the uniqueness of the node and can thus be edited at will by using a public setter method.</p>
	 * 
	 * @param register String representing the node's register 
	 */
	public void setRegister(String register);

	/**
	 * Gets a reference to the <code>VocabSet</code> in which the node is contained.
	 * 
	 * @return the containing <code>VocabSet</code> implementation.
	 */
	public VocabSet getVocabSet();

	/**
	 * Gets a Set containing the <code>SemanticRel</code> representing all semantic relationships <em>from</em> the node.
	 * 
	 * @return Set of <code>SemanticRel</code>s. If the node has no semantic relationships, then this set will be empty.
	 */
	public Set getSemanticRels();

	/**
	 * States whether a node has any <code>SemanticRel</code>s <em>from</em> it.
	 * 
	 * @return true if the node has one or more semantic relationships, false otherwise
	 */
	public boolean hasSemanticRels();
	
	/**
	 * States whether a node has any <code>SemanticRel</code>s of the specified type.
	 * 
	 * @param relationshipType the type of semantic relationship
	 * @return true if the node has one or more semantic relationships of the specified type, false otherwise
	 */
	public boolean hasSemanticRelsOfType(String relationshipType);
	
	/**
	 * Gets a Set of all <code>SemanticRel</code>s of the specified type <em>from</em> the node.
	 *  
	 * @param relationshipType the type of the semantic relationships to be returned 
	 * @return Set of all the semantic relationships of the type specified. If none match, returns an empty Set.
	 */
	public Set getSemanticRelsOfType(String relationshipType);
	
	/**
	 * States whether a node has any <code>SemanticRel</code>s pointing to the node identified by its id.
	 * 
	 * @param toNodeId Integer representing the id of the node
	 * @return true if any semantic relationships point to the node in question, false otherwise
	 */
	public boolean hasSemanticRelsToNode(Integer toNodeId);
			
	/**
	 * States whether the node has any <code>SemanticRel</code> pointing to the node specified.
	 * 
	 * @param toNode 
	 * @return true if any semantic relationships point to the node in question, false otherwise
	 */			
	public boolean hasSemanticRelsToNode(VocabNode toNode);
	
	/**
	 * Gets all the <code>SemanticRel</code>s pointing to the node identified by its id.
	 * 
	 * @param toNodeId
	 * @return Set of all the semantic relationships pointing to the specified node. If there are none, returns an empty Set.
	 */		
	public Set getSemanticRelsToNode(Integer toNodeId);

	/**
	 * Creates a new <code>SemanticRel</code> <em>from</em> the node to the node specified.
	 * 
	 * <p>A <code>VocabNode</code> instance effectively functions as a Factory for <code>SemanticRel</code> instances as these
	 * only make any sense linking two nodes. Implementations must ensure that duplicate <code>SemanticRel</code>s are not created.</p>
	 *  
	 * @param newToNode the node to which the new semantic relationship points
	 * @param newRelationshipType the type of the new semantic relationship
	 * @throws VocabException if the parameters would creat a duplicate node or if any unexpected conditions occurred
	 */
	public void createSemanticRel(VocabNode newToNode, String newRelationshipType) throws VocabException;
	
	/**
	 * Removes a <code>SemanticRel</code> of the specified type that is pointing <em>from</em> the node to the node specifed.
	 * 
	 * <p>It should be noted that because a <code>SemanticRel</code> simply points <em>from</em> one node <em>to</em>
	 * another and labels that relationship, implementations of client software must take steps to remove the corresponding <code>SemanticRel</code>
	 * pointing the other way, which may not necessarily have the an identical label.</p> 
	 *  
	 * @param node the node to which the relationship should be deleted
	 * @param relationshipType the relationship type of the relationship to be deleted
	 * @throws VocabException if the relationship could not be deleted due to unexpected conditions
	 */
	public void removeSemanticRel(VocabNode node, String relationshipType) throws VocabException;
	
	/**
	 * Removes the specified <code>SemanticRel</code>.
	 * 
	 * @param semanticRelToRemove the semantic relationship to be removed
	 * @throws VocabException if the semantic relationship is not present for the node or if it could not be deleted due to unexpected conditions
	 */
	public void removeSemanticRel(SemanticRel semanticRelToRemove) throws VocabException;
	
	/**
	 * Removes all the <code>SemanticRel</code>s to the node specified.
	 * 
	 * @param node
	 * @throws VocabException if there are no semantic relationships to the node specified or if they could not be deleted due to unexpected
	 * conditions
	 */
	public void removeAllSemanticRels(VocabNode node) throws VocabException;
	
	/**
	 * Factory method to obtain the ultra lightweight bean representation of the data for serialization.
	 * 
	 * @return Bean DTO representation of the Node for storage or transport.
	 */
	public VocabNodeDTO getVocabNodeDTO();
	
}
