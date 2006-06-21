/*
 * AbstractSemanticRelTest.java, created on 07-Oct-2003 at 22:14:35
 * 
 * Copyright John King, 2003.
 *
 *  AbstractSemanticRelTest.java is part of authorsite.org's VocabManager program.
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
import junit.framework.*;
import junit.textui.*;
import org.authorsite.vocab.exceptions.*;
/**
 * Tests the methods and constructors in <code>AbstractSemanticRel</code>
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class AbstractSemanticRelTest extends TestCase {

	public AbstractSemanticRelTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(AbstractSemanticRelTest.class);
		return suite;
	}

	// test equals
	public void testEqualsWithEqualObjects() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "noun", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "run", "verb", Locale.ENGLISH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "homograph", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node1, "homograph", node2);
			Assert.assertEquals(rel1, rel2);
			Assert.assertEquals(rel2, rel1);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testEqualsWithDifferentFromNodes() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			VocabNode node3 = new StubAbstractVocabNode(new Integer(3), "laufen", "verb", Locale.GERMAN);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node3, "translation", node2);
			Assert.assertFalse(rel1.equals(rel2));
			Assert.assertFalse(rel2.equals(rel1));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testEqualsWithDifferentRelTypes() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "laugh", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "lachen", "verb", Locale.GERMAN);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node1, "derivedFromEtymologically", node2);
			Assert.assertFalse(rel1.equals(rel2));
			Assert.assertFalse(rel2.equals(rel1));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testEqualsWithDifferentToNodeIds() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			VocabNode node3 = new StubAbstractVocabNode(new Integer(3), "laufen", "verb", Locale.GERMAN);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node1, "translation", node3);
			Assert.assertFalse(rel1.equals(rel2));
			Assert.assertFalse(rel2.equals(rel1));
			Assert.assertFalse(rel1.equals(rel2));
			Assert.assertFalse(rel2.equals(rel1));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testEqualsWithSameRel() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			Assert.assertEquals(rel1, rel1);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testHashCodeWithEqualObjects() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "noun", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "run", "verb", Locale.ENGLISH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "homograph", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node1, "homograph", node2);
			Assert.assertEquals(rel1.hashCode(), rel2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testHashCodeWithDifferentFromNodes() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			VocabNode node3 = new StubAbstractVocabNode(new Integer(3), "laufen", "verb", Locale.GERMAN);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node3, "translation", node2);
			Assert.assertFalse(rel1.hashCode() == rel2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testHashCodeWithDifferentRelTypes() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "laugh", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "lachen", "verb", Locale.GERMAN);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node1, "derivedFromEtymologically", node2);
			Assert.assertFalse(rel1.hashCode() == rel2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testHashCodeWithDifferentToNodeIds() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			VocabNode node3 = new StubAbstractVocabNode(new Integer(3), "laufen", "verb", Locale.GERMAN);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			SemanticRel rel2 = new StubAbstractSemanticRel(node1, "translation", node3);
			Assert.assertFalse(rel1.hashCode() == rel2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testConstructorIdenticalNodeIds() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node1);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.REL_POINTS_TO_SELF)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	// nodes with different ids but which are actually equal
	public void testConstructorIdenticalNodesDifferentIds() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "run", "verb", Locale.ENGLISH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.REL_POINTS_TO_SELF)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	// null fromNode
	public void testConstructorWithNullFromNode() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "run", "verb", Locale.ENGLISH);
			SemanticRel rel1 = new StubAbstractSemanticRel(null, "translation", node1);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_FROM_NODE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	// null relationshipType or relationshipType of zero length
	public void testConstructorWithMissingRelationshipType1() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "", node2);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_RELATIONSHIP_TYPE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	public void testConstructorWithMissingRelationshipType2() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(2), "courir", "verb", Locale.FRENCH);
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, null, node2);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_RELATIONSHIP_TYPE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	// null toNode
	public void testConstructorWithNullToNodeId() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			Integer node2id = null;
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2id);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_TO_NODE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	public void testConstructorWithNullToNode() {
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "run", "verb", Locale.ENGLISH);
			VocabNode node2 = null;
			SemanticRel rel1 = new StubAbstractSemanticRel(node1, "translation", node2);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_TO_NODE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

}