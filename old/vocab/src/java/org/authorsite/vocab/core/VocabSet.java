/*
 * VocabSet.java, created on 08-Sep-2003 at 22:58:07
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
import org.authorsite.vocab.exceptions.*;

/**
 * Defines the generic functionality required to manage a set of <code>VocabNode</code>s.
 * 
 * <p><code>VocabSet</code> defines the interface for managing <code>VocabNodes</code>s.  
 * A group of <code>VocabNode</code>s, each representing a discrete item of vocabulary is best 
 * thought of as a set. This interface therefore builds on the standard Java <code>Set</code>
 * interface and provides additional methods to manage the <code>VocabNodes</code>s and to 
 * query the collection.</p>
 * 
 * <p><code>VocabSet</code> also functions as a Factory for <code>VocabNode</code>s as these should
 * have no public constructors.</p> 
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 * @see java.util.Set
 * @see org.authorsite.vocab.core.VocabNode
 */
public interface VocabSet extends Set {
	
	/**
	 * Adds an object to the <code>VocabSet</code>, subject to it being a <code>VocabNode</code>
	 * and unique within the set.
	 * 
	 * @return true if object could be added to the <code>VocabSet</code>, else false
	 * @param obj an object
	 * @see java.util.Collection#add(java.lang.Object)
	 * @see org.authorsite.vocab.core.VocabSet#createVocabNode(String text, String nodeType, Locale locale)
	 */
	public boolean add(Object obj);
	
	/**
	 * Deletes the object from the <code>VocabSet</code>, subject to it being a valid <code>VocabNode</code>.
	 * 
	 * <p>Implementations must take steps to ensure that when a given <code>VocabNode</code> is deleted that
	 * all its associated <code>SemanticRel<code>s are deleted too. This includes <code>SemanticRel</code>s that
	 * point <em>to</em> it. Generally, client code should ensure that two <code>SemanticRel</code>s link two
	 * <code>VocabNode</code>s to ensure reciprocality and to ensure clean deletion of nodes such that there
	 * are no <code>SemanticRel</code>s pointing to non-existent <code>VocabNode</code>s.</p>
	 * 
	 * @param o an object to be deleted from the <code>VocabSet</code>
	 * @return true if object was successfully deleted, false if it wasn't a <code>VocabNode</code> or wasn't
	 * actually contained in the <code>VocabSet</code> in the first place.
	 */
	public boolean remove(Object o);
	
	/**
	 * Initialises the <code>VocabSet</code> following construction.
	 * 
	 * <p>It is anticipated that implementations of <code>VocabSet</code> will need
	 * to carry out actions such as opening files, obtaining database connectionsm, etc. It may
	 * then initialise itself by reading objects in from the file or performing some initial
	 * database queries, as required.</p>
	 * 
	 * <p>This method should be called by client classes before attempting to carry out
	 * any further operations on the <code>VocabSet</code>.
	 * 
	 * @throws VocabException if any unexpected conditions were encountered
	 */
	public void init() throws VocabException;
	
	/**
	 * Cleans up resources used by the <code>VocabSet</code> before the reference
	 * is disposed of.
	 * 
	 * <p>If the the <code>VocabSet</code> implementation is has any open resources such 
	 * as file handles, database connections, etc, these should be closed explicitly. Client
	 * classes should call this method when they are finished with the <code>VocabSet</code>
	 * reference. The implementation must ensure that it has persisted its current state to
	 * the permanent store it is using.</p>
	 * 
	 * @throws VocabException if any unexpected conditions were encountered
	 */
	public void dispose() throws VocabException;
	
	/**
	 * Creates a new <code>VocabNode</code>.
	 * 
	 * <p><code>VocabSet</code> effectively functions as a factory for <code>VocabNode</code>s. This 
	 * is so that it can guarantee the uniqueness of the <code>VocabNode</code>s it manages.</p>
	 * 
	 * @param text the text of the new <code>VocabNode</code>
	 * @param nodeType the type of the new <code>VocabNode</code>
	 * @param locale the <code>Locale</code> (here, natural language) of the new <code>VocabNode</code> 
	 * @return a new <code>VocabNode</code>
	 * @throws VocabException if the combination of text, nodeType and locale produces a duplicate node, or if any of the 
	 * input variables are null or of zero length, or if there were unexpected problems creating the new node
	 * @see org.authorsite.vocab.core.VocabNode
	 */
	public VocabNode createVocabNode(String text, String nodeType, Locale locale) throws VocabException;
	
	/**
	 * Locates and returns a <code>VocabNode</code> by its integer id.
	 * 
	 * <p>Each <code>VocabNode</code> has a unique integer id field. This method locates and returns the <code>VocabNode</code>
	 * corresponding to the id specified.</p>
	 * 
	 * @param id integer id of node to return
	 * @return specified <code>VocabNode</code> if found, else <code>null</code>.
	 * @throws VocabException
	 */
	public VocabNode findVocabNodeById(Integer id) throws VocabException;
	
	/**
	 * Executes a simple query against the contents of the <code>VocabSet</code>.
	 * 
	 * <p>This provides a very simple, abstracted method of specifying a query to run against the <code>VocabSet</code> to identify
	 * criteria defined in a <code>NodeQueryBean</code>. Implementations must interpret the query and translate
	 * it into an appropriate underlying idiom for execution and then return a Set of <code>VocabNode</code>s.
	 * 
	 * @param nodeQuery
	 * @return Set of vocabNodes that match the query criteria. The Set will be empty if nothing is found that matches.
	 * @throws VocabException
	 * @see org.authorsite.vocab.core.NodeQueryBean
	 */
	public Set findNodes(NodeQueryBean nodeQuery) throws VocabException;
		
}
