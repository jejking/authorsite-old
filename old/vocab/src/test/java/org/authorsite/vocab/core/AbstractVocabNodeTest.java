/*
 * AbstractVocabNodeTest.java, created on 23-Sep-2003 at 22:07:10
 * 
 * Copyright John King, 2003.
 *
 *  AbstractVocabNodeTest.java is part of authorsite.org's VocabManager program.
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
 * Tests the methods implemented in <code>AbstractVocabNode</code> using the
 * <code>StubAbstractVocabNode</code>.
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 * @see org.authorsite.vocab.core.AbstractVocabNode
 * @see org.authorsite.vocab.core.StubAbstractVocabNode
 */
public class AbstractVocabNodeTest extends TestCase {

	public AbstractVocabNodeTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(AbstractVocabNodeTest.class);
		return suite;
	}

	public void testEqualsWithEqualObjects() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(2), "Test", "noun", Locale.GERMAN);
			Assert.assertTrue(test.equals(test2));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testEqualsWithDifferentClassType() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			Assert.assertFalse(test.equals("A String"));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	  * Tests equals with nodes that are identical except for Locale. Expects false.
	 */
	public void testEqualsWithDifferentLocales() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.ENGLISH);
			Assert.assertFalse(test.equals(test2));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests equals with nodes that are identical except for node type. Expects false.
	 */
	public void testEqualsWithDifferentNodeTypes() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1), "Test", "verb", Locale.ENGLISH);
			Assert.assertFalse(test.equals(test2));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	  * Tests equals with nodes that are identical except for text. Expects false.
	  */
	public void testEqualsWithDifferentTexts() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1), "Tester", "noun", Locale.GERMAN);
			Assert.assertFalse(test.equals(test2));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests hashCode with equal nodes. Expects equal hashCodes.
	 */
	public void testHashCodeWithEqualObjects() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1000), "Test", "noun", Locale.GERMAN);
			Assert.assertEquals(test.hashCode(), test2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests hashCode with objects that are identical except for Locale. Expects different values.
	 */
	public void testHashCodeWithDifferentLocales() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.GERMAN);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1), "Test", "noun", Locale.ENGLISH);
			Assert.assertFalse(test.hashCode() == test2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests hashCode with objects that are identical except for node type. Expects different values.
	 */
	public void testHashCodeWithDifferentNodeTypes() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "test", "noun", Locale.ENGLISH);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1), "test", "verb", Locale.ENGLISH);
			Assert.assertFalse(test.hashCode() == test2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	/**
	 * Tests hashCode with objects that are identical except for their text. Expects different values.
	 */
	public void testHashCodeWithDifferentTexts() {
		try {
			VocabNode test = new StubAbstractVocabNode(new Integer(1), "test", "noun", Locale.ENGLISH);
			VocabNode test2 = new StubAbstractVocabNode(new Integer(1), "tester", "verb", Locale.ENGLISH);
			Assert.assertFalse(test.hashCode() == test2.hashCode());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testCompareToIdenticalNodes() {
		Locale.setDefault(Locale.ENGLISH);
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "one", "adjective", Locale.ENGLISH);
			VocabNode node2 = node1; // two refs to same object. must be equal, etc
			Assert.assertEquals(node1, node2);
			Assert.assertEquals(0, node1.compareTo(node2)); // means we're compatible with equals
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testCompareToDifferentLocales() {
		Locale.setDefault(Locale.ENGLISH);
		try {
			VocabNode node1 = new StubAbstractVocabNode(new Integer(1), "random", "adjective", Locale.ENGLISH);
			VocabNode node2 = new StubAbstractVocabNode(new Integer(1), "random", "adjective", Locale.FRENCH);
			// right in our Locale, node 1 is "English", node 2 is "French" 
			Assert.assertTrue(node1.compareTo(node2) <= -1);
			Assert.assertTrue(node2.compareTo(node1) >= 1);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testCompareToDifferentText() {
		Locale.setDefault(Locale.ENGLISH);
		try {
			VocabNode hello = new StubAbstractVocabNode(new Integer(1), "hello", "phrase", Locale.ENGLISH);
			VocabNode goodbye = new StubAbstractVocabNode(new Integer(2), "goodbye", "phrase", Locale.ENGLISH);
			// goodbye comes before hello
			Assert.assertTrue(goodbye.compareTo(hello) <= -1);
			Assert.assertTrue(hello.compareTo(goodbye) >= 1);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testCompareToDifferentNodeTypes() {
		try {
			VocabNode run_noun = new StubAbstractVocabNode(new Integer(1), "run", "noun", Locale.ENGLISH);
			VocabNode run_verb = new StubAbstractVocabNode(new Integer(2), "run", "verb", Locale.ENGLISH);
			// nouns come before verbs in this implementation
			Assert.assertTrue(run_verb.compareTo(run_noun) >= 1);
			Assert.assertTrue(run_noun.compareTo(run_verb) <= 1);
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testConstructorFourValidArgs() {
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "run", "noun", Locale.ENGLISH);
			Assert.assertEquals(new Integer(1), node.getId());
			Assert.assertEquals("run", node.getText());
			Assert.assertEquals("noun", node.getNodeType());
			Assert.assertEquals(Locale.ENGLISH, node.getLocale());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testConstructorFiveValidArgs() {
		Date aDate = new Date();
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "run", "noun", Locale.ENGLISH, aDate);
			Assert.assertEquals(new Integer(1), node.getId());
			Assert.assertEquals("run", node.getText());
			Assert.assertEquals("noun", node.getNodeType());
			Assert.assertEquals(Locale.ENGLISH, node.getLocale());
			Assert.assertEquals(aDate, node.getDateCreated());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced");
		}
	}

	public void testConstructorInvalidId() {
		try {
			VocabNode node = new StubAbstractVocabNode(null, "run", "noun", Locale.ENGLISH);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_NODE_ID)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	public void testConstructorInvalidText1() {
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), null, "noun", Locale.ENGLISH);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_NODE_TEXT)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	public void testConstructorInvalidText2() {
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "", "noun", Locale.ENGLISH);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_NODE_TEXT)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	public void testConstructorInvalidNodeType1() {
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "run", null, Locale.ENGLISH);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_NODE_TYPE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}

	public void testConstructorInvalidNodeType2() {
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "run", "", Locale.ENGLISH);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_NODE_TYPE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}
	
	public void testConstructorInvalidLocale() {
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "run", "noun", null);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.MISSING_NODE_LOCALE)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}
	
	public void testConstructorDateOfCreationInFuture() {
		Calendar cal = new GregorianCalendar(2011, Calendar.NOVEMBER, 10); // eek, my 40th birthday!!
		Date johnAt40 = cal.getTime();
		try {
			VocabNode node = new StubAbstractVocabNode(new Integer(1), "run", "noun", Locale.ENGLISH, johnAt40);
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			if (ve.getMessage().equals(VocabException.INVALID_DATE_OF_CREATION)) {
				Assert.assertTrue(true);
			}
			else {
				Assert.fail("Wrong VocabException message");
			}
		}
	}
}