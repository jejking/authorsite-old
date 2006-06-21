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
import junit.framework.*;
import junit.textui.*;
import org.authorsite.vocab.exceptions.*;
import org.authorsite.vocab.VocabConstants;
/**
 * Tests the <code>MemoryVocabSetFactory</code> implementation.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class MemoryVocabSetFactoryTest extends TestCase {

	public MemoryVocabSetFactoryTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(MemoryVocabSetFactoryTest.class);
		return suite;
	}

	/**
	 * Uncomment this to test the singleton - must be run before any properties are set at all to test the effect.
	 *
	  public void testWithNoPropsSet() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			VocabSet vocabSet = factory.getVocabSet();
			Assert.fail("Expected VocabException");
		}
		catch (VocabException ve) {
			Assert.assertEquals(VocabException.INITIALIZATION, ve.getMessage());
		}
	}
	
	 *
	 */

	// first bunch of tests look at manipulating the properties in the factory
	public void testSetProperties() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test1.dat"); // yes, that's an absolute file on my box. You'll want to change this
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet();
			Assert.assertEquals("/home/jejking/vocab/test/test1.dat", vocabSet.getNodesFile().getAbsolutePath());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve.getMessage() + ve.getCause());
		}
	}

	public void testChangeInProperties() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test1.dat"); // yes, that's an absolute file on my box. You'll want to change this
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet1 = (MemoryVocabSet) factory.getVocabSet();
			factory.removeProperty(VocabConstants.MEMORY_LANGUAGE_PROP);
			MemoryVocabSet vocabSet2 = (MemoryVocabSet) factory.getVocabSet();
			Assert.assertFalse(vocabSet1.equals(vocabSet2));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve.getMessage() + ve.getCause());
		}
	}

	public void testChangeInProperties2() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test1.dat"); // yes, that's an absolute file on my box. You'll want to change this
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet1 = (MemoryVocabSet) factory.getVocabSet();
			factory.addProperty("blah", "blahh");
			MemoryVocabSet vocabSet2 = (MemoryVocabSet) factory.getVocabSet();
			Assert.assertFalse(vocabSet1.equals(vocabSet2));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve.getMessage() + ve.getCause());
		}
	}

	public void testChangeInProperties3() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test1.dat"); // yes, that's an absolute file on my box. You'll want to change this
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet1 = (MemoryVocabSet) factory.getVocabSet();

			Properties testProps2 = new Properties();
			testProps2.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test2.dat");
			factory.setProperties(testProps2);
			MemoryVocabSet vocabSet2 = (MemoryVocabSet) factory.getVocabSet();
			Assert.assertFalse(vocabSet1.equals(vocabSet2));
			Assert.assertEquals("/home/jejking/vocab/test/test2.dat", vocabSet2.getNodesFile().getAbsolutePath());
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve.getMessage() + ve.getCause());
		}
	}

	public void testChangiPropsPassedIn() {
		try {
			MemoryVocabSetFactory factory = MemoryVocabSetFactory.getInstance();
			Properties testProps = new Properties();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test1.dat"); // yes, that's an absolute file on my box. You'll want to change this
			factory.setProperties(testProps);
			MemoryVocabSet vocabSet = (MemoryVocabSet) factory.getVocabSet();
			testProps.put(VocabConstants.MEMORY_NODESFILE_PROP, "/home/jejking/vocab/test/test3.dat"); // shouldn't show up because of the defensive clone
			Assert.assertFalse(vocabSet.getNodesFile().getAbsolutePath().equals("/home/jejking/vocab/test/test3.dat"));
		}
		catch (VocabException ve) {
			Assert.fail("VocabException produced " + ve.getMessage() + ve.getCause());
		}
	}
	
}