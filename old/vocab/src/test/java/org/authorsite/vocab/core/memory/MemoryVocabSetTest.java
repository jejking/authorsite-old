/*
 * MemoryVocabSetTest.java, created on 12-Oct-2003 at 22:40:54
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabSetTest.java is part of authorsite.org's VocabManager program.
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

import junit.framework.*;
import junit.textui.*;
import org.authorsite.vocab.core.*;
import org.authorsite.vocab.VocabConstants;
import org.authorsite.vocab.exceptions.*;
import org.authorsite.vocab.core.dto.*;
/**
 * Tests the <code>MemoryVocabSet</code> implementation.
 * 
 * @author jejking
 * @version $Revision: 1.9 $
 */
public class MemoryVocabSetTest extends TestCase {

	private HashSet getTestSerializedSet() {
		HashSet set = new HashSet();
		VocabNodeDTO mannSer = new VocabNodeDTO();
		mannSer.setId(new Integer(1));
		mannSer.setLocale(Locale.GERMAN);
		mannSer.setNodeType("noun");
		mannSer.setText("Mann");
		mannSer.setAnnotation("Mann is a German noun");

		// build some semantic rels
		SemanticRelDTO mann2man = new SemanticRelDTO();
		mann2man.setFromNodeId(new Integer(1));
		mann2man.setToNodeId(new Integer(2));
		mann2man.setRelationshipType("equivalent");
		SemanticRelDTO mann2homme = new SemanticRelDTO();
		mann2homme.setFromNodeId(new Integer(1));
		mann2homme.setToNodeId(new Integer(3));
		mann2homme.setRelationshipType("equivalent");
		mannSer.addSemanticRel(mann2man);
		mannSer.addSemanticRel(mann2homme);

		VocabNodeDTO manSer = new VocabNodeDTO();
		manSer.setId(new Integer(2));
		manSer.setLocale(Locale.ENGLISH);
		manSer.setNodeType("noun");
		manSer.setText("man");
		SemanticRelDTO man2mann = new SemanticRelDTO();
		man2mann.setFromNodeId(new Integer(2));
		man2mann.setToNodeId(new Integer(1));
		man2mann.setRelationshipType("equivalent");
		SemanticRelDTO man2homme = new SemanticRelDTO();
		man2homme.setFromNodeId(new Integer(2));
		man2homme.setToNodeId(new Integer(3));
		man2homme.setRelationshipType("equivalent");
		manSer.addSemanticRel(man2mann);
		manSer.addSemanticRel(man2homme);

		VocabNodeDTO hommeSer = new VocabNodeDTO();
		hommeSer.setId(new Integer(3));
		hommeSer.setLocale(Locale.FRENCH);
		hommeSer.setNodeType("noun");
		hommeSer.setText("homme");
		SemanticRelDTO homme2mann = new SemanticRelDTO();
		homme2mann.setFromNodeId(new Integer(3));
		homme2mann.setToNodeId(new Integer(1));
		homme2mann.setRelationshipType("equivalent");
		SemanticRelDTO homme2man = new SemanticRelDTO();
		homme2man.setFromNodeId(new Integer(3));
		homme2man.setToNodeId(new Integer(2));
		homme2man.setRelationshipType("equivalent");
		hommeSer.addSemanticRel(homme2mann);
		hommeSer.addSemanticRel(homme2man);

		set.add(mannSer);
		set.add(manSer);
		set.add(hommeSer);

		return set;
	}

	public MemoryVocabSetTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(MemoryVocabSetTest.class);
		return suite;
	}

	// test constructors
	public void testConstructor() {
		MemoryVocabSet vocabSet = new MemoryVocabSet();
		Assert.assertEquals(Locale.getDefault(), vocabSet.getLocale());
	}

	public void testConstructorWithLocale() {
		MemoryVocabSet vocabSet = new MemoryVocabSet(Locale.GERMAN);
		Assert.assertEquals(Locale.GERMAN, vocabSet.getLocale());
	}

	// test nodes file immutability
	public void testNodesFileImmutability() {
		MemoryVocabSet vocabSet = new MemoryVocabSet();
		try {
			vocabSet.setNodesFile(new File("test1.dat"));
			Assert.assertEquals("test1.dat", vocabSet.getNodesFile().getName());
			vocabSet.setNodesFile(new File("/home/jejking/vocab/test/test2.dat")); // should be ignored
			Assert.assertEquals("test1.dat", vocabSet.getNodesFile().getName());
		}
		catch (VocabException e) {
			Assert.fail("VocabException produced");
		}
	}

	// test non-existent file
	public void testNodesFileNonExistentFile() {
		// if the file doesn't exist, it should try and create it
		MemoryVocabSet vocabSet = new MemoryVocabSet();
		try {
			File testFile = new File("testFile");
			if (testFile.exists()) {
				testFile.delete();
			}
			Assert.assertFalse(testFile.exists());
			vocabSet.setNodesFile(testFile);
			Assert.assertTrue(testFile.exists());
			Assert.assertTrue(testFile.length() == 0L);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	// test directory
	public void testNodesFileWithDirectory() {
		MemoryVocabSet vocabSet = new MemoryVocabSet();
		try {
			File testDir = new File("testDir");
			if (testDir.exists()) {
				testDir.delete();
			}
			testDir.mkdir();
			Assert.assertTrue(testDir.isDirectory());
			vocabSet.setNodesFile(testDir); // should fail
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.PERSISTENCE, ve.getMessage());
		}
	}

	//	// test non readable -- this is non portable test. Uncomment if you're running on a Unix, with root using bash ;-)
	//	public void testNodesFileWithNonReadableFile() {
	//		MemoryVocabSet vocabSet = new MemoryVocabSet();
	//		try {
	//			File testFile = new File("/root/.bash_history"); // if we can read that, then we're in trouble. Or we're runnign this as root, which is almost as bad
	//			vocabSet.setNodesFile(testFile);
	//			Assert.fail("Expected exception");
	//		}
	//		catch (VocabException ve) {
	//			Assert.assertEquals(VocabException.PERSISTENCE, ve.getMessage());
	//		}
	//	}

	// test non writable
	public void testNodesFileWithNonWritableFile() {
		MemoryVocabSet vocabSet = new MemoryVocabSet();
		try {
			File testFile = new File("test2.dat");
			if (testFile.exists()) {
				testFile.delete();
			}
			testFile.createNewFile();
			testFile.setReadOnly();
			vocabSet.setNodesFile(testFile);
			Assert.fail("expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.PERSISTENCE, ve.getMessage());
		}
		catch (IOException ioe) {
			Assert.fail(ioe.getMessage());
		}
	}

	// test loading from a file. Simulate a serialized object stream with objects that the loader should handle ok
	public void testSimpleLoadFromFile() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet();

			// at this point the vocabset should have loaded up the data file
			VocabNode mannNode = vocabSet.findVocabNodeById(new Integer(1));
			Assert.assertEquals("Mann", mannNode.getText());
			Assert.assertEquals(Locale.GERMAN, mannNode.getLocale());
			Assert.assertEquals("noun", mannNode.getNodeType());

			VocabNode manNode = vocabSet.findVocabNodeById(new Integer(2));
			Assert.assertEquals("man", manNode.getText());
			Assert.assertEquals(Locale.ENGLISH, manNode.getLocale());
			Assert.assertEquals("noun", manNode.getNodeType());

			VocabNode hommeNode = vocabSet.findVocabNodeById(new Integer(3));
			Assert.assertEquals("homme", hommeNode.getText());
			Assert.assertEquals(Locale.FRENCH, hommeNode.getLocale());
			Assert.assertEquals("noun", hommeNode.getNodeType());

			Assert.assertEquals(3, vocabSet.size());

			// check the semantic rels were loaded up ok as well
			Assert.assertEquals(2, mannNode.getSemanticRels().size());
			Assert.assertEquals(2, manNode.getSemanticRels().size());
			Assert.assertEquals(2, hommeNode.getSemanticRels().size());

			Set mannNodeSemanticRels = mannNode.getSemanticRels();
			Iterator mannNodeSemanticRelsIt = mannNodeSemanticRels.iterator();
			while (mannNodeSemanticRelsIt.hasNext()) {
				SemanticRel rel = (SemanticRel) mannNodeSemanticRelsIt.next();
				Assert.assertTrue(rel.getRelationshipType().equals("equivalent"));
				Assert.assertTrue(rel.getToNodeId().equals(new Integer(2)) || rel.getToNodeId().equals(new Integer(3)));
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
			Assert.fail(ioe.getMessage());
		}
		catch (VocabException ve) {
			ve.printStackTrace();
			Assert.fail("VocabException produced " + ve.getMessage());
		}
	}

	// test loading from files with mistakes in it
	//	multiple identical IDs, but distinct logical serialized relations
	public void testLoadIdenticalIDs() {
		try {
			HashSet set = getTestSerializedSet();
			VocabNodeDTO woman = new VocabNodeDTO();
			woman.setId(new Integer(1)); // collides
			woman.setText("woman");
			woman.setNodeType("noun");
			woman.setLocale(Locale.ENGLISH);
			set.add(woman);

			VocabNodeDTO frau = new VocabNodeDTO();
			frau.setId(new Integer(2)); // again, collides
			frau.setText("Frau");
			frau.setNodeType("noun");
			frau.setLocale(Locale.GERMAN);
			set.add(frau);

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet();
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INITIALIZATION, ve.getMessage());
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

	// nodes with semantic rels pointing to invalid nodes
	public void testLoadInvalidNodes() {
		try {
			HashSet set = getTestSerializedSet();
			VocabNodeDTO woman = new VocabNodeDTO();
			woman.setId(new Integer(4)); 
			woman.setText("woman");
			woman.setNodeType("noun");
			woman.setLocale(Locale.ENGLISH);
			SemanticRelDTO woman2fictional = new SemanticRelDTO();
			woman2fictional.setFromNodeId(new Integer(4));
			woman2fictional.setToNodeId(new Integer(10000)); // doesn't exist
			woman2fictional.setRelationshipType("blah");
			woman.addSemanticRel(woman2fictional);
			set.add(woman);

			VocabNodeDTO frau = new VocabNodeDTO();
			frau.setId(new Integer(5));
			frau.setText("Frau");
			frau.setNodeType("noun");
			frau.setLocale(Locale.GERMAN);
			set.add(frau);

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet();
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INITIALIZATION, ve.getMessage());
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

	// test node creation
	/**
	 * Tests creation of an additional node with a valid data
	 * that does not break the uniqueness constraint.
	 */
	public void testCreateNodeValid() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("text", "noun", Locale.ENGLISH);
			Assert.assertNotNull(node);
			Assert.assertEquals("text", node.getText());
			Assert.assertEquals("noun", node.getNodeType());
			Assert.assertEquals(Locale.ENGLISH, node.getLocale());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException generated");
		}
	}

	/**
	 * Tests creation of an additional node with duplicate data. An exception
	 * must be thrown and caught for the test to pass
	 */
	public void testCreateNodeDuplicate() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("text", "noun", Locale.ENGLISH);
			VocabNode node2 = set.createVocabNode("text", "noun", Locale.ENGLISH);
			Assert.fail("Failed to catch duplicate node");
		}
		catch (VocabException ve) {
			Assert.assertTrue(true);
		}
	}

	/**
	 * Tests ability to look up a known node by its Integer id.
	 */
	public void testFindNodeById() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("text", "noun", Locale.ENGLISH);
			VocabNode foundNode = set.findVocabNodeById(node.getId());
			Assert.assertNotNull("found node must not be null", foundNode);
			Assert.assertEquals("hello and found node must be equal", node, foundNode);
		}
		catch (VocabException ve) {
			ve.printStackTrace();
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests look up with a known non-valid id. Expects to receive null response.
	 *
	 */
	public void testFindNodeByIdNonExisentNode() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			VocabNode foundNode = set.findVocabNodeById(new Integer(-1));
			Assert.assertNull("found node must be null as it doesn't exist", foundNode);
		}
		catch (VocabException e) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests ability to modify the text of an existing node with new text that 
	 * does not break the uniqueness constraint. Expects to retrieve the new text
	 * from the node.
	 */
	public void testUpdateNodeValid() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("hello", "phrase", Locale.ENGLISH);
			node.updateNodeText("g'day");
			node.setAnnotation("Turned Australian");
			Assert.assertEquals("hello should now be g'day", "g'day", node.getText());
		}
		catch (VocabException ve) {
			Assert.fail("Vocab Exception produced");
		}
	}

	/**
	 * Tests the node update functionality by attempting to set node text that 
	 * would create a duplicate node. Expects an exception to be thrown, and for
	 * the text of the node to have not been changed.
	 */
	public void testUpdateNodeDuplicateText() {
		VocabNode node2 = null;
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("hello", "phrase", Locale.ENGLISH);
			node2 = set.createVocabNode("g'day", "phrase", Locale.ENGLISH); // sort of English ;-)
			node2.updateNodeText("hello"); // produces duplicate, should produce exception
			Assert.fail("insertion of duplicate node did not produce exception");
		}
		catch (VocabException ve) {
			Assert.assertEquals("text should be as original", "g'day", node2.getText());
		}
	}

	/**
	 * Tests simple node deletion functionality. Test creates a node, notes its id, then deletes
	 * the node. It then attempts to retrieve that node form the list using the id and expects
	 * to be returned null. 
	 */
	public void testNodeDeleteNoRels() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet set = factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("hello", "phrase", Locale.ENGLISH);
			Integer nodeId = node.getId();
			set.remove(node);
			VocabNode foundNode = set.findVocabNodeById(nodeId);
			Assert.assertNull("deleted object id should return null", foundNode);
		}
		catch (VocabException ve) {
			Assert.fail("Vocab Exception produced");
		}
	}

	/**
	 * Tests more complex node deletion functionality. Test creates two nodes and establishes
	 * SemanticRelationships each way between the two of them, effectively bi-directional. The second
	 * node should have one semantic relationship. The first is then deleted. The second node should
	 * now have no semantic relationships.
	 */
	public void testNodeDeleteWithSemanticRels() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet(); // set up with the default test set

			// now, delete a node
			VocabNode nodeToDelete = vocabSet.findVocabNodeById(new Integer(1));
			vocabSet.remove(nodeToDelete);

			// now, get the two remaining Nodes and check that they have the correct semantic rels in place
			VocabNode node2 = vocabSet.findVocabNodeById(new Integer(2));
			Assert.assertNotNull(node2);
			VocabNode node3 = vocabSet.findVocabNodeById(new Integer(3));
			Assert.assertNotNull(node3);

			// check node2
			Set node2Rels = node2.getSemanticRels();
			Assert.assertEquals(1, node2Rels.size());
			Iterator node2It = node2Rels.iterator();
			while (node2It.hasNext()) {
				SemanticRel rel = (SemanticRel) node2It.next();
				Assert.assertEquals(new Integer(3), rel.getToNodeId());
				Assert.assertEquals(node3, rel.getToNode());
			}

			// check node3
			Set node3Rels = node3.getSemanticRels();
			Assert.assertEquals(1, node3Rels.size());
			Iterator node3It = node3Rels.iterator();
			while (node3It.hasNext()) {
				SemanticRel rel = (SemanticRel) node3It.next();
				Assert.assertEquals(new Integer(2), rel.getToNodeId());
				Assert.assertEquals(node2, rel.getToNode());
			}
		}
		catch (IOException ioe) {
			Assert.fail("IOException " + ioe);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced: " + ve.getMessage());
		}
	}

	// test where we've implemented bits of the Set API
	public void testAddCompletelyValidNode() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			Assert.assertTrue(set.size() == 0);

			// cheat and create a memory vocab node directly
			MemoryVocabNode node = new MemoryVocabNode(new Integer(1), "test", "noun", Locale.ENGLISH, set);

			// get a brand new MemoryVocabSet instance that isn't loaded from file
			factory.removeProperty(VocabConstants.MEMORY_NODESFILE_PROP); // so it doesn't reinitialise from file
			MemoryVocabSet set2 = (MemoryVocabSet) factory.getVocabSet();
			Assert.assertFalse(set.equals(set2));
			Assert.assertTrue(set2.add(node));

			// try it with a different implementation of VocabNode
			StubAbstractVocabNode testNode = new StubAbstractVocabNode(new Integer(1), "test", "verb", Locale.ENGLISH);
			Assert.assertTrue(set2.add(testNode));

			VocabNode retrievedNode = set2.findVocabNodeById(new Integer(1)); // we know it'll be one because we've cleared the set previously
			Assert.assertEquals(node, retrievedNode);
			Assert.assertFalse(node == retrievedNode); // they shouldn't be equal by reference, but logically equals
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced" + ve);
		}
	}

	public void testAddNonVocabNodeObject() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			Assert.assertFalse(set.add("a completely random string that isn't an instance of VocabNode, oddly enough"));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testAddDuplicateData() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			set.createVocabNode("test", "noun", Locale.ENGLISH);
			StubAbstractVocabNode testNode = new StubAbstractVocabNode(new Integer(1), "test", "noun", Locale.ENGLISH);
			Assert.assertFalse(set.add(testNode));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests implementation of the Set <code>addAll</code> method.
	 * 
	 * Expects to catch an <code>UnsupportedOperationException</code>.
	 */
	public void testAddAll() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.addAll(new ArrayList());
			Assert.fail("expected unsupported operation exception");
		}
		catch (UnsupportedOperationException uoe) {
			Assert.assertTrue(true);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	public void testClearAndIsEmpty() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet(); // set up with the default test set

			Assert.assertEquals(3, vocabSet.size());
			Assert.assertFalse(vocabSet.isEmpty());
			vocabSet.clear();
			Assert.assertEquals(0, vocabSet.size());
			Assert.assertTrue(vocabSet.isEmpty());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

	public void testContains() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode node = set.createVocabNode("test", "noun", Locale.ENGLISH);
			Assert.assertTrue(set.contains(node));
			Assert.assertFalse(set.contains("a string it obviously doesn't contain"));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testIterator() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet(); // set up with the default test set

			boolean foundMann = false;
			boolean foundMan = false;
			boolean foundHomme = false;
			int nodeCount = 0;

			Iterator vocabSetIt = vocabSet.iterator();
			while (vocabSetIt.hasNext()) {
				nodeCount++;
				VocabNode node = (VocabNode) vocabSetIt.next();
				if (node.getText().equals("Mann")) {
					foundMann = true;
					continue;
				}
				if (node.getText().equals("man")) {
					foundMan = true;
					continue;
				}
				if (node.getText().equals("homme")) {
					foundHomme = true;
				}
			}
			Assert.assertTrue(foundMann);
			Assert.assertTrue(foundMan);
			Assert.assertTrue(foundHomme);
			Assert.assertEquals(3, nodeCount);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

	public void testToArray() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet(); // set up with the default test set

			boolean foundMann = false;
			boolean foundMan = false;
			boolean foundHomme = false;

			Object[] nodes = vocabSet.toArray();
			Assert.assertEquals(3, nodes.length);
			for (int i = 0; i < nodes.length; i++) {
				VocabNode node = (VocabNode) nodes[i];
				if (node.getText().equals("Mann")) {
					foundMann = true;
					continue;
				}
				if (node.getText().equals("man")) {
					foundMan = true;
					continue;
				}
				if (node.getText().equals("homme")) {
					foundHomme = true;
				}
			}
			Assert.assertTrue(foundMann);
			Assert.assertTrue(foundMan);
			Assert.assertTrue(foundHomme);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

	public void testToArrayOfVocabularyNodes() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet(); // set up with the default test set

			boolean foundMann = false;
			boolean foundMan = false;
			boolean foundHomme = false;

			VocabNode[] nodes = (VocabNode[]) vocabSet.toArray(new VocabNode[0]);
			Assert.assertEquals(3, nodes.length);
			for (int i = 0; i < nodes.length; i++) {
				if (nodes[i].getText().equals("Mann")) {
					foundMann = true;
					continue;
				}
				if (nodes[i].getText().equals("man")) {
					foundMan = true;
					continue;
				}
				if (nodes[i].getText().equals("homme")) {
					foundHomme = true;
				}
			}
			Assert.assertTrue(foundMann);
			Assert.assertTrue(foundMan);
			Assert.assertTrue(foundHomme);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

	public void testToArrayOfInvalidThings() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			VocabNode[] nodeArray = (VocabNode[]) set.toArray(new String[0]);
			Assert.fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	// test ability to persist a set to stream of serialized objects
	// start with a blank as yet nonexistent file, create a bunch of nodes, call dispose. Examine the contents of that object stream
	public void testPersistingToNewFile() {
		try {
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet();

			// at this stage the vocabset should be quite empy
			Assert.assertTrue(vocabSet.isEmpty());

			// create a couple of nodes and interrelationships between them
			VocabNode nodeBeer = vocabSet.createVocabNode("beer", "noun", Locale.ENGLISH);
			VocabNode nodeBier = vocabSet.createVocabNode("Bier", "noun", Locale.GERMAN);
			VocabNode nodeBirra = vocabSet.createVocabNode("birra", "noun", Locale.ITALIAN);

			nodeBeer.createSemanticRel(nodeBier, "equivalent");
			nodeBeer.createSemanticRel(nodeBirra, "equivalent");

			nodeBier.createSemanticRel(nodeBeer, "equivalent");
			nodeBier.createSemanticRel(nodeBirra, "equivalent");

			nodeBirra.createSemanticRel(nodeBeer, "equivalent");
			nodeBirra.createSemanticRel(nodeBier, "equivalent");

			vocabSet.dispose();

			// now, lets look up the data file produced and verify that all the data we entered has been serialized ok
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(test));
			HashSet inputSet = (HashSet) in.readObject();
			in.close();

			VocabNodeDTO serBeer = null;
			VocabNodeDTO serBier = null;
			VocabNodeDTO serBirra = null;

			Iterator inputIt = inputSet.iterator();
			while (inputIt.hasNext()) {
				VocabNodeDTO foundNode = (VocabNodeDTO) inputIt.next();
				if (foundNode.getText().equals("beer")) {
					serBeer = foundNode;
					continue;
				}
				if (foundNode.getText().equals("Bier")) {
					serBier = foundNode;
					continue;
				}
				if (foundNode.getText().equals("birra")) {
					serBirra = foundNode;
				}
			}

			Assert.assertNotNull(serBeer);
			Assert.assertNotNull(serBier);
			Assert.assertNotNull(serBirra);

			// examine each node in turn to ensure it's ok
			Assert.assertEquals(nodeBeer.getText(), serBeer.getText());
			Assert.assertEquals(nodeBeer.getNodeType(), serBeer.getNodeType());
			Assert.assertEquals(nodeBeer.getLocale(), serBeer.getLocale());
			Assert.assertEquals(nodeBeer.getDateCreated(), serBeer.getDateCreated());
			Assert.assertEquals(nodeBeer.getId(), serBeer.getId());

			Assert.assertEquals(nodeBier.getText(), serBier.getText());
			Assert.assertEquals(nodeBier.getNodeType(), serBier.getNodeType());
			Assert.assertEquals(nodeBier.getLocale(), serBier.getLocale());
			Assert.assertEquals(nodeBier.getDateCreated(), serBier.getDateCreated());
			Assert.assertEquals(nodeBier.getId(), serBier.getId());

			Assert.assertEquals(nodeBirra.getText(), serBirra.getText());
			Assert.assertEquals(nodeBirra.getNodeType(), serBirra.getNodeType());
			Assert.assertEquals(nodeBirra.getLocale(), serBirra.getLocale());
			Assert.assertEquals(nodeBirra.getDateCreated(), serBirra.getDateCreated());
			Assert.assertEquals(nodeBirra.getId(), serBirra.getId());

			// now examine the serialized semantic rels
			Set serBeerSemanticRels = serBeer.getSemanticRels();
			Assert.assertEquals(2, serBeerSemanticRels.size());
			SemanticRelDTO serBeerToBier = null;
			SemanticRelDTO serBeerToBirra = null;
			Iterator serBeerSemanticRelsIt = serBeerSemanticRels.iterator();
			while (serBeerSemanticRelsIt.hasNext()) {
				SemanticRelDTO foundRel = (SemanticRelDTO) serBeerSemanticRelsIt.next();
				if (foundRel.getToNodeId().equals(nodeBier.getId())) {
					serBeerToBier = foundRel;
					continue;
				}
				if (foundRel.getToNodeId().equals(nodeBirra.getId())) {
					serBeerToBirra = foundRel;
					continue;
				}
			}
			Assert.assertNotNull(serBeerToBier);
			Assert.assertNotNull(serBeerToBirra);

			Assert.assertEquals(nodeBier.getId(), serBeerToBier.getToNodeId());
			Assert.assertEquals(nodeBeer.getId(), serBeerToBier.getFromNodeId());
			Assert.assertEquals("equivalent", serBeerToBier.getRelationshipType());

			Assert.assertEquals(nodeBirra.getId(), serBeerToBirra.getToNodeId());
			Assert.assertEquals(nodeBeer.getId(), serBeerToBirra.getFromNodeId());
			Assert.assertEquals("equivalent", serBeerToBirra.getRelationshipType());

			Set serBierSemanticRels = serBier.getSemanticRels();
			Assert.assertEquals(2, serBierSemanticRels.size());

			Set serBirraSemanticRels = serBirra.getSemanticRels();
			Assert.assertEquals(2, serBirraSemanticRels.size());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
		catch (ClassNotFoundException cnfe) {
			Assert.fail("ClassNotFoundException produced " + cnfe);
		}
	}

	// test ability to save modified version to disk and then for the set to reinitialise itself from that modified version
	public void testPersistenceModifiedFile() {
		try {
			HashSet set = getTestSerializedSet();

			// write our little hash set to disk
			File test = new File("test.dat");
			if (test.exists()) {
				test.delete();
			}
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(test));
			out.writeObject(set);
			out.flush();
			out.close();

			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, test.getAbsolutePath());
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet(); // set up with the default test set

			// we'll now modify the set. Remove homme and add uomo
			VocabNode nodeHomme = vocabSet.findVocabNodeById(new Integer(3));
			VocabNode nodeMan = vocabSet.findVocabNodeById(new Integer(1));
			VocabNode nodeMann = vocabSet.findVocabNodeById(new Integer(2));

			// remove homme
			vocabSet.remove(nodeHomme);
			// add uomo and create links to the remaining nodes
			VocabNode nodeUomo = vocabSet.createVocabNode("uomo", "node", Locale.ITALIAN);
			nodeUomo.createSemanticRel(nodeMan, "equivalent");
			nodeMan.createSemanticRel(nodeUomo, "equivalent");
			nodeUomo.createSemanticRel(nodeMann, "equivalent");
			nodeMann.createSemanticRel(nodeUomo, "equivalent");

			vocabSet.dispose();

			boolean foundMann = false;
			boolean foundMan = false;
			boolean foundHomme = false;
			boolean foundUomo = false;

			factory.addProperty("dummy", "dummy");
			vocabSet = (MemoryVocabSet) factory.getVocabSet();
			VocabNode[] nodes = (VocabNode[]) vocabSet.toArray(new VocabNode[0]);
			for (int i = 0; i < nodes.length; i++) {
				if (nodes[i].getText().equals("man")) {
					foundMan = true;
					continue;
				}
				if (nodes[i].getText().equals("Mann")) {
					foundMann = true;
					continue;
				}
				if (nodes[i].getText().equals("homme")) {
					foundHomme = true;
					continue;
				}
				if (nodes[i].getText().equals("uomo")) {
					foundUomo = true;
					Set uomoSemanticRels = nodes[i].getSemanticRels();
					Assert.assertEquals(2, uomoSemanticRels.size());
					Iterator uomoSemanticRelsIt = uomoSemanticRels.iterator();
					SemanticRel uomoToMann = null;
					SemanticRel uomoToMan = null;
					while (uomoSemanticRelsIt.hasNext()) {
						SemanticRel foundRel = (SemanticRel) uomoSemanticRelsIt.next();
						if (foundRel.getToNodeId().equals(nodeMann.getId())) {
							uomoToMann = foundRel;
							continue;
						}
						if (foundRel.getToNodeId().equals(nodeMan.getId())) {
							uomoToMan = foundRel;
							continue;
						}
					}
					Assert.assertEquals("equivalent", uomoToMann.getRelationshipType());
					Assert.assertEquals("equivalent", uomoToMan.getRelationshipType());
				}
			}

			Assert.assertTrue(foundMann);
			Assert.assertTrue(foundMan);
			Assert.assertFalse(foundHomme);
			Assert.assertTrue(foundUomo);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
		catch (IOException ioe) {
			Assert.fail("IOException produced " + ioe);
		}
	}

}
