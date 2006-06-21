/*
 * MemoryVocabSet.java, created on 09-Sep-2003 at 22:57:56
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabSet.java is part of authorsite.org's VocabManager program.
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
import java.io.*;

import org.apache.log4j.Logger;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.core.dto.*;
import org.authorsite.vocab.exceptions.*;

/**
 * Implementation of <code>VocabSet</code> in memory using simple object
 * serialization to and from a file for persistence.
 * 
 * <p>This is a lightweight implementation backed by standard Java Collections to organise the 
 * storage of the <code>VocabNode</code>s. It handles persistence using standard Java object
 * serialization, but uses extra lightweight bean data classes to represent its content in an attempt to
 * avoid some of the complications of object serialization.</p>
 * 
 * <p>It is not particularly robust, but should be quite fast and usable for a scenario involving a single
 * user.</p>
 * 
 * @author jejking
 * @version $Revision: 1.13 $
 * @see org.authorsite.vocab.core.VocabSet
 */
public class MemoryVocabSet extends AbstractVocabSet {

	private static Logger log = Logger.getLogger("org.authorsite.vocab.core.memory.MemoryVocabSet");

	private Set nodesSet; // we use set functionality to check for duplicates
	private TreeMap nodesMap; // for quick look up of nodes by id
	private DateManager dateManager;

	private QueryProcessor processor;

	private File nodesFile;
	private boolean isNodesFileSet;

	protected MemoryVocabSet() {
		super();
	}

	protected MemoryVocabSet(Locale locale) {
		super(locale);
	}

	/**
	 * Defines the file from which to load a persisted <code>MemoryVocabSet</code> and to which
	 * to persist it.
	 * 
	 * Note that the file is immutable once set. This may change in the future if we implement a "Save As" 
	 * like functionality.
	 * 
	 * @param file to be used to load and save the <code>MemoryVocabSet</code>
	 * @throws NullPointerException if the file reference is null.
	 */
	protected void setNodesFile(File file) throws VocabException {
		if (file == null) {
			throw new NullPointerException();
		}
		if (!isNodesFileSet) {
			nodesFile = file;
			if (!nodesFile.exists()) {
				try {
					file.createNewFile();
				}
				catch (IOException ioe) {
					throw new VocabException(VocabException.PERSISTENCE, ioe);
				}
			}
			// check we've got proper access
			if (file.canRead() && nodesFile.canWrite() && nodesFile.isFile()) {
				isNodesFileSet = true; // everything's ok
				return;
			}
			else {
				throw new VocabException(VocabException.PERSISTENCE);
			}
		}
		else {
			// ignore it. file is immutable once set
		}
	}

	/**
	 * Returns the File which this <code>MemoryVocabSet</code> instance is using for persistence.
	 * 
	 * @return the File, if one is set, else null.
	 */
	public File getNodesFile() {
		return nodesFile;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#init()
	 */
	public void init() throws VocabException {
		log.debug("initialising MemoryVocabSet");
		// set up backing Sets. later we'll read in the serialized objects from a file
		nodesSet = new TreeSet();
		nodesMap = new TreeMap();
		dateManager = new DateManager();
		createQueryProcessor();
		if (isNodesFileSet) {
			loadNodesFromFile();
			checkIntegrity();
		}
	}

	protected void createQueryProcessor() {
		processor = new QueryProcessor();
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#dispose()
	 */
	public void dispose() throws VocabException {
		if (isNodesFileSet) {
			saveNodesToFile();
		}
		clear();
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#createVocabNode(java.lang.String, java.lang.String, java.util.Locale)
	 */
	public VocabNode createVocabNode(String text, String nodeType, Locale locale) throws VocabException {
		log.debug("creating Vocab Node: " + text + ", " + nodeType + ", " + locale);
		Integer nextKeyInteger = getNextKey();

		// instantiate new object with text + locale
		Date newNodeDate = new Date();
		VocabNode newNode = new MemoryVocabNode(nextKeyInteger, text, nodeType, locale, newNodeDate, this);

		// check this is not a duplicate of anything we have already by attempting 
		// to add it to the nodesSet
		if (nodesSet.add(newNode)) {
			nodesMap.put(nextKeyInteger, newNode);
			dateManager.addNode(newNode);
			//			return the object ref for further manipulation by the client
			return newNode;
		}
		else {
			newNode = null;
			throw new VocabException(VocabException.DUPLICATE_NODE);
		}
	}

	private Integer getNextKey() {
		//		obtain an id
		Integer nextKeyInteger = null;

		int highestKey = 0;
		if (this.nodesMap.size() > 0) {
			highestKey = ((Integer) nodesMap.lastKey()).intValue();
			nextKeyInteger = new Integer(++highestKey);
		}
		else {
			nextKeyInteger = new Integer(1); // just for starters....
		}
		return nextKeyInteger;
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#findNodes(org.authorsite.vocab.core.NodeQueryBean)
	 */
	public Set findNodes(NodeQueryBean nodeQuery) {
		return processor.processQuery(nodeQuery);
	}

	/**
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return nodesSet.size();
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		return nodesSet.isEmpty();
	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		if (o instanceof MemoryVocabNode) { // the backing set can only contain instances of MemoryVocabNode
			return nodesSet.contains(o);
		}
		else {
			return false;
		}
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	public Iterator iterator() {
		return nodesSet.iterator();
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		return nodesSet.toArray();
	}

	/**
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a) {
		if (a.getClass().getComponentType().equals(VocabNode.class)) {
			return nodesSet.toArray(a);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds an object, which must be an instance of <code>VocabNode</code> to the set.
	 * 
	 * <p>
	 * It should be noted that the best way to add a new <code>VocabNode</code> to the collection is to use the 
	 * node creation methods with data from an existing node, followed by whichever method
	 * calls are necessary to recreate the existing <code>SemanticRel</code>s correctly.
	 * </p>
	 * 
	 * <p>
	 *  This method is an alternative way of adding a <code>VocabNode</code> to the set. The object reference
	 * passed in is not used directly, but a new <code>MemoryVocabNode</code> instantiated to hold the data. A
	 * new internal id is also generated at this point to avoid collisions. Note that because only a <code>boolean</code>
	 * is returned by this method, that there is currently no way to determine the id of the copy created directly. A workaround
	 * would be to use a query to locate the <code>VocabNode</code> which matches the data passed in.
	 * </p>
	 * 
	 * <p>The current implementation makes no attempt to reconstruct the object's semantic relationships.</p>
	 * 
	 * @param an Object which should be an instance of <code>VocabNode</code>
	 * @return  true if the Object is an instance of <code>VocabNode</code> and a new <code>MemoryVocabNode</code>
	 * could be instantiated from its data and added to the backing set
	 * @return false if the Object is not an instance of <code>VocabNode</code>, if a new <code>MemoryVocabNode</code>
	 * could not be instantiated from its data or if the copy could not be added to the backing set because its data is duplicate.
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		if (o instanceof VocabNode) {
			try {
				// downcast the object
				VocabNode node = (VocabNode) o;

				// we will now carry out a defensive copy type operation and
				// attempt to construct a new MemoryVocabNode using the data
				// - the MemoryVocabNode constructor should catch defects in the data
				Integer nextKey = getNextKey();
				MemoryVocabNode nodeCopy = null;
				if (node.getDateCreated() != null) {
					nodeCopy = new MemoryVocabNode(getNextKey(), node.getText(), node.getNodeType(), node.getLocale(), node.getDateCreated(), this);
				}
				else {
					nodeCopy = new MemoryVocabNode(getNextKey(), node.getText(), node.getNodeType(), node.getLocale(), this);
				}
				if (nodesSet.add(nodeCopy)) { // having constructed our copy successfully, see if it can be added to the backing set
					nodesMap.put(nodeCopy.getId(), nodeCopy);
					dateManager.addNode(nodeCopy);
					//	finally, set our copy up with any other data held in the object passed in
					nodeCopy.setAnnotation(node.getAnnotation());
					nodeCopy.setRegister(node.getRegister());
					return true;
				}
				else { // we couldn't add it to the set as it wasn't unique
					nodeCopy = null;
					return false;
				}
			}
			catch (VocabException ve) {
				// assume the VocabException was caused by a failure to create the copy, i.e. the object passed in was duff
				ve.printStackTrace();
				return false;
			}
		}
		else { // not an instance of VocabNode. Obviously can't be added
			return false;
		}
	}

	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		if (o instanceof VocabNode) {
			VocabNode nodeToDelete = (VocabNode) o;
			// first, check we actually contain this node, if so take them out of the cache
			if (nodesSet.contains(nodeToDelete)) {
				Set semanticRels = nodeToDelete.getSemanticRels();
				Iterator relsIt = semanticRels.iterator();
				while (relsIt.hasNext()) {
					SemanticRel currentRel = (SemanticRel) relsIt.next();
					// look up the node pointed to...
					VocabNode toNode = currentRel.getToNode();
					// for this node, see if it has semantic rels pointing back to the node we're going to delete. If so, delete them
					Set relsPointingBack = toNode.getSemanticRelsToNode(nodeToDelete.getId());
					Iterator relsBackIt = relsPointingBack.iterator();
					while (relsBackIt.hasNext()) {
						SemanticRel relToDelete = (SemanticRel) relsBackIt.next();
						try {
							toNode.removeSemanticRel(relToDelete);
						}
						catch (VocabException e) {
							// this shouldn't ever happen in this particular implementation, but is defined in the interface
							e.printStackTrace();
						}
					}
				}
				// remove node from node set's caches
				nodesSet.remove(nodeToDelete);
				nodesMap.remove(nodeToDelete.getId());
				dateManager.removeNode(nodeToDelete);
				return true; // node successfully removed
			}
			else {
				return false; // the set didn't contain the ref passed in
			}
		}
		else {
			return false; // the ref passed in was of the wrong type
		}
	}

	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		return nodesSet.containsAll(c);
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		nodesSet.clear();
		nodesMap.clear();
		dateManager.clear();
	}

	/**
	 * @see org.authorsite.vocab.core.VocabSet#findVocabNodeById(java.lang.Integer)
	 */
	public VocabNode findVocabNodeById(Integer id) throws VocabException {
		return (VocabNode) nodesMap.get(id);
	}

	protected void updateNodeText(MemoryVocabNode node, String newText) throws VocabException {
		//		act on the version in the cache first...
		if (nodesSet.contains(node)) {

			// temporarily remove node from set
			nodesSet.remove(node);

			// extract original text...
			String originalText = node.getText();

			node.setText(newText); //use the protected mutator

			// attempt to put it back in the set with new text
			if (!nodesSet.add(node)) {
				node.setText(originalText);
				nodesSet.add(node); // restore it and throw exception
				throw new VocabException(VocabException.DUPLICATE_NODE);
			}
		}

	}

	public String toString() {
		StringBuffer sb = new StringBuffer(1000);
		Iterator it = nodesSet.iterator();
		while (it.hasNext()) {
			MemoryVocabNode node = (MemoryVocabNode) it.next();
			sb.append(node);
			sb.append("\n");
		}
		return sb.toString();
	}

	private void loadNodesFromFile() throws VocabException {
		try {
			// attempt to open the file and construct an objectinputstream
			if (nodesFile.length() > 0) { // if it's a fresh file of length zero, don't bother trying to read it in as there's nothing there
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(nodesFile));
				HashSet inputSet = (HashSet) in.readObject();
				in.close();
				Iterator inputSetIt = inputSet.iterator();
				while (inputSetIt.hasNext()) {
					VocabNodeDTO serializedNode = (VocabNodeDTO) inputSetIt.next();
					MemoryVocabNode node = new MemoryVocabNode(serializedNode, this);
					this.nodesSet.add(node);
					this.nodesMap.put(node.getId(), node);
					dateManager.addNode(node);
				}
			}
		}
		catch (IOException ioe) {
			throw new VocabException(VocabException.PERSISTENCE, ioe);
		}
		catch (ClassNotFoundException cnfe) {
			throw new VocabException(VocabException.PERSISTENCE, cnfe);
		}
	}

	private void saveNodesToFile() throws VocabException {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nodesFile));
			HashSet outputSet = new HashSet();
			Iterator nodesSetIt = nodesSet.iterator();
			while (nodesSetIt.hasNext()) {
				MemoryVocabNode node = (MemoryVocabNode) nodesSetIt.next();
				outputSet.add(node.getVocabNodeDTO());
			}
			out.writeObject(outputSet);
			out.flush();
			out.close();
		}
		catch (IOException ioe) {
			throw new VocabException(VocabException.PERSISTENCE, ioe);
		}
	}

	/**
	 * Checks that the set is in a consistent state.
	 * 
	 * After the set has been initialised from file, we need to check that the data loaded is coherent. In particular,
	 * we need to check that the backing maps and set match up and that there are no SemanticRels that 
	 * point to non-existent VocabNodes.  
	 *
	 */
	private void checkIntegrity() throws VocabException {
		// check nodesMap, dateManager and nodesSet are all the same size
		if (nodesMap.size() != dateManager.getNodeCount() || nodesMap.size() != nodesSet.size()) {
			throw new VocabException(VocabException.INITIALIZATION);
		}
		// next, go throw all the VocabNodes and check that all their SemanticRels point to VocabNodes in the nodesMap
		Iterator nodesIt = nodesSet.iterator();
		while (nodesIt.hasNext()) {
			MemoryVocabNode node = (MemoryVocabNode) nodesIt.next();
			Set nodeRels = node.getSemanticRels();
			Iterator nodeRelsIt = nodeRels.iterator();
			while (nodeRelsIt.hasNext()) {
				MemorySemanticRel nodeRel = (MemorySemanticRel) nodeRelsIt.next();
				if (!nodesMap.containsKey(nodeRel.getToNodeId())) {
					// we have a problem, it points to a node we outside the set.
					throw new VocabException(VocabException.INITIALIZATION);
				}
			}
		}
	}

	private class QueryProcessor {

		QueryProcessor() {
		}

		Set processQuery(NodeQueryBean query) {
			Set timeResults = new HashSet();
			boolean isTimeQuery = false;
			if (query.getAfterDate() != null || query.getBeforeDate() != null) {
				isTimeQuery = true;
			}

			// if nothing is set, return empty Set
			if (query.getText() == null && query.getNodeType() == null && query.getLocale() == null && query.getAfterDate() == null && query.getBeforeDate() == null) {
				return timeResults;
			}

			// process the dates first using the enclosing instance's dateManager
			if (query.getAfterDate() != null & query.getBeforeDate() != null) { // both set
				timeResults.addAll(dateManager.getNodesBetween(query.getAfterDate(), query.getBeforeDate()));
			}
			else if (query.getAfterDate() != null & query.getBeforeDate() == null) { // only after date is set
				timeResults.addAll(dateManager.getNodesAfter(query.getAfterDate()));
			}
			else if (query.getAfterDate() == null & query.getBeforeDate() != null) { // only before date is set
				timeResults.addAll(dateManager.getNodesBefore(query.getBeforeDate()));
			}

			Set setToExamine = null;
			if (isTimeQuery && timeResults.isEmpty()) { // it was a time based query, but we found nothing. Don't even bother looking at the rest
				return timeResults;
			}
			else if (isTimeQuery && !timeResults.isEmpty()) { // it was time based query and we've found something. Only look at what matched the time query
				setToExamine = timeResults;
			}
			else {
				setToExamine = nodesSet;
			}

			Set results = new HashSet(setToExamine);

			//	now, iterate through setToExamine. removing nodes which don't match the query from the results set
			Iterator it = setToExamine.iterator();
			while (it.hasNext()) {
				VocabNode node = (VocabNode) it.next();
				if (query.getLocale() != null) {
					if (!query.getLocale().equals(node.getLocale())) {
						results.remove(node);
					}
				}
				if (query.getText() != null) {
					if (!query.getText().equals(node.getText())) {
						results.remove(node);
					}
				}
				if (query.getNodeType() != null) {
					if (!query.getNodeType().equals(node.getNodeType())) {
						results.remove(node);
					}
				}
			}
			return results;
		}

	}

	/**
	 * Little utility data structure that maps Lists of VocabNodes to keys based on Dates.
	 * 
	 * We cannot guarantee that <code>VocabNode</code>s in the set will all have distinct
	 * dates of Creation - for instance, an application could have recorded a bunch as created
	 * on a particular day rather than at a level down to the millisecond. However, we still need to be
	 * able to find <em>all</em> nodes created before, after or between certain times.
	 * 
	 * @author jejking
	 */
	private class DateManager {

		private TreeMap dateMap;
		private int nodeCount = 0;

		DateManager() {
			dateMap = new TreeMap();
		}

		int getNodeCount() {
			return nodeCount;
		}

		Set getNodesBefore(Date date) {
			Set finalResults = new HashSet();
			Collection matchingSets = dateMap.headMap(date).values();
			Iterator matchingSetsIt = matchingSets.iterator();
			while (matchingSetsIt.hasNext()) {
				finalResults.addAll((HashSet) matchingSetsIt.next());
			}
			return finalResults;
		}

		Set getNodesAfter(Date date) {
			Set finalResults = new HashSet();
			Collection matchingSets = dateMap.tailMap(date).values();
			Iterator matchingSetsIt = matchingSets.iterator();
			while (matchingSetsIt.hasNext()) {
				finalResults.addAll((HashSet) matchingSetsIt.next());
			}
			return finalResults;
		}

		Set getNodesBetween(Date startDate, Date endDate) {
			Set finalResults = new HashSet();
			Collection matchingSets = dateMap.subMap(startDate, endDate).values();
			Iterator matchingSetsIt = matchingSets.iterator();
			while (matchingSetsIt.hasNext()) {
				finalResults.addAll((HashSet) matchingSetsIt.next());
			}
			return finalResults;
		}

		boolean addNode(VocabNode node) {
			HashSet dateSet = null;
			Date dateCreated = node.getDateCreated();
			if (dateMap.containsKey(node.getDateCreated())) {
				dateSet = (HashSet) dateMap.get(dateCreated);
				if (dateSet.add(node)) {
					nodeCount++;
					return true;
				}
				else {
					return false;
				}
			}
			else {
				dateSet = new HashSet();
				dateSet.add(node);
				dateMap.put(dateCreated, dateSet);
				nodeCount++;
				return true;
			}
		}

		boolean removeNode(VocabNode node) {
			Date dateCreated = node.getDateCreated();
			if (dateMap.containsKey(dateCreated)) {
				HashSet dateSet = (HashSet) dateMap.get(node.getDateCreated());
				if (dateSet.remove(node)) {
					nodeCount--;
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		
		void clear() {
			dateMap.clear();
		}
	}

}