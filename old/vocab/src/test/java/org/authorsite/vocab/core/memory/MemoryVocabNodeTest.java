/*
 * MemoryVocabNodeTest.java, created on 25-Oct-2003 at 18:01:23
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabNodeTest.java is part of authorsite.org's VocabManager program.
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
import junit.framework.*;
import junit.textui.*;
import org.authorsite.vocab.core.*;
import org.authorsite.vocab.core.dto.*;
import org.authorsite.vocab.exceptions.*;
/**
 * Test cases for <code>MemoryVocabNode</code>.
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class MemoryVocabNodeTest extends TestCase {

	public MemoryVocabNodeTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(MemoryVocabNodeTest.class);
		return suite;
	}

	// test constructors - most of this is handled in AbstractVocabNode
	// just need to make sure that attempts to pass in a null vocab MemoryVocabSet fail
	public void testConstructorNoDateNullSet() {
		try {
			MemoryVocabNode node = new MemoryVocabNode(new Integer(1), "test text", "verb", Locale.ENGLISH, null);
			Assert.fail("expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INITIALIZATION, ve.getMessage());
		}
	}

	public void testConstructorDateNullSet() {
		try {
			MemoryVocabNode node = new MemoryVocabNode(new Integer(1), "test text", "verb", Locale.ENGLISH, new Date(), null);
			Assert.fail("expected VocabExeption");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INITIALIZATION, ve.getMessage());
		}
	}

	// test construction using SerializableMemoryVocabNodes
	public void testConstructorSerializableNullSet() {
		try {
			VocabNodeDTO ser = new VocabNodeDTO();
			ser.setId(new Integer(1));
			ser.setText("test");
			ser.setNodeType("noun");
			ser.setDateCreated(new Date());
			ser.setLocale(Locale.ENGLISH);
			MemoryVocabNode node = new MemoryVocabNode(ser, null);
			Assert.fail("expected vocab exception");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INITIALIZATION, ve.getMessage());
		}
	}

	public void testConstructorSerializableFullData() {
		try {
			VocabNodeDTO ser = new VocabNodeDTO();
			ser.setId(new Integer(1));
			ser.setText("test");
			ser.setNodeType("noun");
			Date dateCreated = new GregorianCalendar(2003, Calendar.OCTOBER, 25).getTime();
			ser.setDateCreated(dateCreated);
			ser.setLocale(Locale.ENGLISH);
			ser.setAnnotation("this is an annotation");
			ser.setRegister("formal");
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			MemoryVocabNode node = new MemoryVocabNode(ser, set);
			// now examine the node
			Assert.assertEquals(new Integer(1), node.getId());
			Assert.assertEquals("test", node.getText());
			Assert.assertEquals("noun", node.getNodeType());
			Assert.assertEquals(dateCreated, node.getDateCreated());
			Assert.assertEquals(Locale.ENGLISH, node.getLocale());
			Assert.assertEquals("this is an annotation", node.getAnnotation());
			Assert.assertEquals("formal", node.getRegister());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	// test creation of semantic rels
	public void testCreateSemanticRelOK() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode wine = set.createVocabNode("wine", "noun", Locale.ENGLISH);
			VocabNode wein = set.createVocabNode("Wein", "noun", Locale.GERMAN);
			Assert.assertFalse(wine.hasSemanticRels());
			Assert.assertFalse(wein.hasSemanticRels());
			wine.createSemanticRel(wein, "equivalent");
			wein.createSemanticRel(wine, "equivalent");
			Assert.assertTrue(wine.hasSemanticRelsToNode(wein));
			Assert.assertTrue(wein.hasSemanticRelsToNode(wine));
			Assert.assertTrue(wine.hasSemanticRelsToNode(wein.getId()));
			Assert.assertTrue(wein.hasSemanticRelsToNode(wine.getId()));
			Assert.assertTrue(wine.hasSemanticRels());
			Assert.assertTrue(wein.hasSemanticRels());
			Assert.assertTrue(wine.hasSemanticRelsOfType("equivalent"));
			Assert.assertTrue(wein.hasSemanticRelsOfType("equivalent"));
			Assert.assertEquals(1, wein.getSemanticRels().size());
			Assert.assertEquals(1, wine.getSemanticRels().size());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	public void testCreateSemanticRelNonExistentVocabNode() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode computer = set.createVocabNode("computer", "noun", Locale.ENGLISH);
			VocabNode ordinateur = set.createVocabNode("ordinateur", "noun", Locale.FRENCH);
			set.remove(ordinateur);
			computer.createSemanticRel(ordinateur, "equivalent"); // should fail, we have removed ordinateur
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INVALID_NODE, ve.getMessage());
		}
	}

	public void testCreateSemanticRelDuffNodeCopy() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode computer = set.createVocabNode("computer", "noun", Locale.ENGLISH);
			VocabNode ordinateur = set.createVocabNode("ordinateur", "noun", Locale.FRENCH);
			StubAbstractVocabNode ordinateurCopy = new StubAbstractVocabNode(ordinateur.getId(), "ordinateur", "noun", Locale.CHINESE); // :-)
			computer.createSemanticRel(ordinateurCopy, "equivalent"); // should fail, it's a duff copy
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INVALID_NODE, ve.getMessage());
		}
	}

	public void testCreateSemanticRelGoodCopy() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode computer = set.createVocabNode("computer", "noun", Locale.ENGLISH);
			VocabNode ordinateur = set.createVocabNode("ordinateur", "noun", Locale.FRENCH);
			StubAbstractVocabNode ordinateurCopy = new StubAbstractVocabNode(ordinateur.getId(), "ordinateur", "noun", Locale.FRENCH); // is equal 
			computer.createSemanticRel(ordinateurCopy, "equivalent"); // should be ok. SemanticRel should include ref to original ordinateur
			// now, change something on ordinateurCopy
			ordinateurCopy.setAnnotation("test annotation");
			Assert.assertNull(ordinateur.getAnnotation());
			Set computerSemanticRels = computer.getSemanticRels();
			Iterator it = computerSemanticRels.iterator();
			while (it.hasNext()) {
				SemanticRel rel = (SemanticRel) it.next();
				VocabNode toNode = rel.getToNode();
				Assert.assertTrue(toNode == ordinateur); // should be ref to the object managed by the set
				Assert.assertFalse(toNode == ordinateurCopy);
			}
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	// test removal of semantic rels
	public void testRemovingRels() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode computer = set.createVocabNode("Computer", "noun", Locale.GERMAN);
			VocabNode ordinateur = set.createVocabNode("ordinateur", "noun", Locale.FRENCH);
			VocabNode rechner = set.createVocabNode("Rechner", "noun", Locale.GERMAN);
			VocabNode rechnerPhrase = set.createVocabNode("Ich liebe meinen Rechner", "phrase", Locale.GERMAN);

			Assert.assertFalse(computer.hasSemanticRels());
			Assert.assertFalse(ordinateur.hasSemanticRels());
			Assert.assertFalse(rechner.hasSemanticRels());
			Assert.assertFalse(rechnerPhrase.hasSemanticRels());

			computer.createSemanticRel(ordinateur, "equivalent");
			ordinateur.createSemanticRel(computer, "equivalent");

			computer.createSemanticRel(rechner, "equivalent");
			rechner.createSemanticRel(computer, "equivalent");

			ordinateur.createSemanticRel(rechner, "equivalent");
			rechner.createSemanticRel(ordinateur, "equivalent");

			Assert.assertEquals(2, computer.getSemanticRels().size());
			Assert.assertEquals(2, rechner.getSemanticRels().size());
			Assert.assertEquals(2, ordinateur.getSemanticRels().size());

			rechner.createSemanticRel(rechnerPhrase, "hasExample");
			rechnerPhrase.createSemanticRel(rechner, "isExampleOf");

			Assert.assertEquals(3, rechner.getSemanticRels().size());
			Assert.assertTrue(rechner.hasSemanticRelsOfType("hasExample"));

			rechner.removeSemanticRel(ordinateur, "equivalent");
			ordinateur.removeSemanticRel(rechner, "equivalent");

			Assert.assertEquals(2, rechner.getSemanticRels().size());
			Assert.assertEquals(1, ordinateur.getSemanticRels().size());
			Assert.assertTrue(rechner.hasSemanticRelsOfType("hasExample"));
			Assert.assertFalse(rechner.hasSemanticRelsToNode(ordinateur.getId()));
			Assert.assertFalse(rechner.hasSemanticRelsToNode(ordinateur));

			computer.removeAllSemanticRels(rechner);
			rechner.removeAllSemanticRels(computer);

			Assert.assertEquals(1, computer.getSemanticRels().size());
			Assert.assertFalse(computer.hasSemanticRelsToNode(rechner));
			Assert.assertEquals(1, rechner.getSemanticRels().size());
			Assert.assertFalse(rechner.hasSemanticRelsOfType("equivalent"));

			rechner.createSemanticRel(computer, "equivalent");
			computer.createSemanticRel(rechner, "equivalent");

			Set rechnerRels = rechner.getSemanticRels();
			Iterator rechnerRelsIt = rechnerRels.iterator();
			while (rechnerRelsIt.hasNext()) {
				SemanticRel rel = (SemanticRel) rechnerRelsIt.next();
				if (rel.getToNode().equals(rechnerPhrase)) {
					rechner.removeSemanticRel(rel);
				}
			}

			Assert.assertFalse(rechner.hasSemanticRelsOfType("hasExample"));
			Assert.assertFalse(rechner.hasSemanticRelsToNode(rechnerPhrase));
			Assert.assertFalse(rechner.hasSemanticRelsToNode(rechnerPhrase.getId()));
			Assert.assertEquals(1, rechner.getSemanticRels().size());

		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	// test finding semantic rels
	public void testFindingSemanticRels() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode computerDE = set.createVocabNode("Computer", "noun", Locale.GERMAN);
			VocabNode ordinateur = set.createVocabNode("ordinateur", "noun", Locale.FRENCH);
			VocabNode rechner = set.createVocabNode("Rechner", "noun", Locale.GERMAN);
			VocabNode rechnerPhrase = set.createVocabNode("Ich liebe meinen Rechner", "phrase", Locale.GERMAN);
			VocabNode computerEN = set.createVocabNode("computer", "noun", Locale.ENGLISH);

			computerDE.createSemanticRel(ordinateur, "equivalent");
			ordinateur.createSemanticRel(computerDE, "equivalent");

			computerDE.createSemanticRel(rechner, "equivalent");
			rechner.createSemanticRel(computerDE, "equivalent");

			computerDE.createSemanticRel(computerEN, "equivalent");
			computerEN.createSemanticRel(computerDE, "equivalent");
			computerDE.createSemanticRel(computerEN, "isDerivedFrom");
			computerEN.createSemanticRel(computerDE, "hasDerivation");

			ordinateur.createSemanticRel(rechner, "equivalent");
			rechner.createSemanticRel(ordinateur, "equivalent");

			ordinateur.createSemanticRel(computerEN, "equivalent");
			computerEN.createSemanticRel(ordinateur, "equivalent");

			rechner.createSemanticRel(computerEN, "equivalent");
			computerEN.createSemanticRel(rechner, "equivalent");

			rechner.createSemanticRel(rechnerPhrase, "hasExample");
			rechnerPhrase.createSemanticRel(rechner, "isExampleOf");

			Assert.assertEquals(4, rechner.getSemanticRels().size());
			Assert.assertTrue(rechner.hasSemanticRelsOfType("hasExample"));

			Assert.assertTrue(rechner.hasSemanticRelsToNode(rechnerPhrase.getId()));
			Assert.assertTrue(rechner.hasSemanticRelsToNode(rechnerPhrase));

			Set computerENEquivRels = computerEN.getSemanticRelsOfType("equivalent");
			boolean foundComputerEN2ComputerDE_equiv = false;
			boolean foundComputerEN2Rechner_equiv = false;
			Iterator computerENEquivRelsIt = computerENEquivRels.iterator();
			while (computerENEquivRelsIt.hasNext()) {
				SemanticRel rel = (SemanticRel) computerENEquivRelsIt.next();
				Assert.assertTrue(rel.getRelationshipType().equals("equivalent"));
				Assert.assertEquals(computerEN, rel.getFromNode());
				if (rel.getToNode().equals(computerDE)) {
					foundComputerEN2ComputerDE_equiv = true;
				}
				if (rel.getToNode().equals(rechner)) {
					foundComputerEN2Rechner_equiv = true;
				}
			}
			Assert.assertTrue(foundComputerEN2ComputerDE_equiv);
			Assert.assertTrue(foundComputerEN2Rechner_equiv);

			Set computerEN2computerDERels = computerEN.getSemanticRelsToNode(computerDE.getId());
			foundComputerEN2ComputerDE_equiv = false;
			boolean foundComputerEN2ComputerDE_deriv = false;
			Iterator computerEN2computerDERelsIt = computerEN2computerDERels.iterator();
			while (computerEN2computerDERelsIt.hasNext()) {
				SemanticRel rel = (SemanticRel) computerEN2computerDERelsIt.next();
				Assert.assertEquals(computerEN, rel.getFromNode());
				Assert.assertEquals(computerDE, rel.getToNode());
				if (rel.getRelationshipType().equals("equivalent")) {
					foundComputerEN2ComputerDE_equiv = true;
				}
				if (rel.getRelationshipType().equals("hasDerivation")) {
					foundComputerEN2ComputerDE_deriv = true;
				}
			}
			Assert.assertTrue(foundComputerEN2ComputerDE_equiv);
			Assert.assertTrue(foundComputerEN2ComputerDE_deriv);

		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	// test getting serializable version
	public void testGetSerializableNoSemanticRels() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			MemoryVocabNode minNode = (MemoryVocabNode) set.createVocabNode("minimal", "adjective", Locale.ENGLISH);
			VocabNodeDTO minSer = minNode.getSerializableMemoryVocabNode();
			Assert.assertEquals(minNode.getId(), minSer.getId());
			Assert.assertEquals(minNode.getText(), minSer.getText());
			Assert.assertEquals(minNode.getNodeType(), minSer.getNodeType());
			Assert.assertEquals(minNode.getLocale(), minSer.getLocale());

			Date october25 = new GregorianCalendar(2003, Calendar.OCTOBER, 25).getTime();
			MemoryVocabNode maxNode = new MemoryVocabNode(new Integer(222), "maximum", "noun", Locale.ENGLISH, october25, set);
			maxNode.setAnnotation("a maximum amount of annotation");
			maxNode.setRegister("formal");
			VocabNodeDTO maxSer = maxNode.getSerializableMemoryVocabNode();
			Assert.assertEquals("a maximum amount of annotation", maxSer.getAnnotation());
			Assert.assertEquals("formal", maxSer.getRegister());
			Assert.assertEquals(october25, maxSer.getDateCreated());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	public void testGetSerializableSemanticRels() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			MemoryVocabNode minNodeEN = (MemoryVocabNode) set.createVocabNode("minimum", "noun", Locale.ENGLISH);
			MemoryVocabNode maxNodeEN = (MemoryVocabNode) set.createVocabNode("maximum", "noun", Locale.ENGLISH);
			MemoryVocabNode minNodeFR = (MemoryVocabNode) set.createVocabNode("minimum", "noun", Locale.FRENCH);
			MemoryVocabNode maxNodeFR = (MemoryVocabNode) set.createVocabNode("maximum", "noun", Locale.FRENCH);

			minNodeEN.createSemanticRel(maxNodeEN, "opposite");
			maxNodeEN.createSemanticRel(minNodeEN, "opposite");
			minNodeFR.createSemanticRel(maxNodeFR, "opposite");
			maxNodeFR.createSemanticRel(minNodeFR, "opposite");

			minNodeEN.createSemanticRel(minNodeFR, "equivalent");
			minNodeFR.createSemanticRel(minNodeEN, "equivalent");
			maxNodeEN.createSemanticRel(maxNodeFR, "equivalent");
			maxNodeFR.createSemanticRel(minNodeEN, "equivalent");

			VocabNodeDTO minNodeENSer = minNodeEN.getSerializableMemoryVocabNode();
			Set minNodeENSerRels = minNodeENSer.getSemanticRels();
			Assert.assertEquals(2, minNodeENSerRels.size());
			boolean found_minNodeEN_maxNodeEN = false;
			boolean found_minNodeEN_minNodeFR = false;
			Iterator minNodeENSerRelsIt = minNodeENSerRels.iterator();
			while (minNodeENSerRelsIt.hasNext()) {
				SemanticRelDTO rel = (SemanticRelDTO) minNodeENSerRelsIt.next();
				if (rel.getToNodeId().equals(maxNodeEN.getId())) {
					found_minNodeEN_maxNodeEN = true;
					Assert.assertEquals("opposite", rel.getRelationshipType());
					Assert.assertEquals(minNodeEN.getId(), rel.getFromNodeId());
				}
				if (rel.getToNodeId().equals(minNodeFR.getId())) {
					found_minNodeEN_minNodeFR = true;
					Assert.assertEquals("equivalent", rel.getRelationshipType());
					Assert.assertEquals(minNodeEN.getId(), rel.getFromNodeId());
				}
			}
			Assert.assertTrue(found_minNodeEN_maxNodeEN);
			Assert.assertTrue(found_minNodeEN_minNodeFR);

		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}

	public void testManipulatingSemanticRelSet() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			MemoryVocabSet set = (MemoryVocabSet) factory.getVocabSet();
			set.clear();
			VocabNode computerDE = set.createVocabNode("Computer", "noun", Locale.GERMAN);
			VocabNode ordinateur = set.createVocabNode("ordinateur", "noun", Locale.FRENCH);
			VocabNode rechner = set.createVocabNode("Rechner", "noun", Locale.GERMAN);
			VocabNode rechnerPhrase = set.createVocabNode("Ich liebe meinen Rechner", "phrase", Locale.GERMAN);
			VocabNode computerEN = set.createVocabNode("computer", "noun", Locale.ENGLISH);

			computerDE.createSemanticRel(ordinateur, "equivalent");
			ordinateur.createSemanticRel(computerDE, "equivalent");

			computerDE.createSemanticRel(rechner, "equivalent");
			rechner.createSemanticRel(computerDE, "equivalent");

			computerDE.createSemanticRel(computerEN, "equivalent");
			computerEN.createSemanticRel(computerDE, "equivalent");
			computerDE.createSemanticRel(computerEN, "isDerivedFrom");
			computerEN.createSemanticRel(computerDE, "hasDerivation");

			ordinateur.createSemanticRel(rechner, "equivalent");
			rechner.createSemanticRel(ordinateur, "equivalent");

			ordinateur.createSemanticRel(computerEN, "equivalent");
			computerEN.createSemanticRel(ordinateur, "equivalent");

			rechner.createSemanticRel(computerEN, "equivalent");
			computerEN.createSemanticRel(rechner, "equivalent");

			rechner.createSemanticRel(rechnerPhrase, "hasExample");
			rechnerPhrase.createSemanticRel(rechner, "isExampleOf");
			
			// get a ref to rechner's SemanticRels
			Set rechnerSemanticRels = rechner.getSemanticRels();
			rechnerSemanticRels.clear();
			
			// this shouldn't have had any effect on the internal representation
			Assert.assertEquals(4, rechner.getSemanticRels().size());
			
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve);
		}
	}
}