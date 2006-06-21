/*
 * GenericVocabSetFactoryTest.java, created on 23-Nov-2003 at 16:47:44
 * 
 * Copyright John King, 2003.
 *
 *  GenericVocabSetFactoryTest.java is part of authorsite.org's VocabManager program.
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
import org.authorsite.vocab.VocabConstants;
import org.authorsite.vocab.core.memory.*;
import org.authorsite.vocab.exceptions.*;
/**
 * Tests the <code>GenericVocabSetFactory</code> class.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class GenericVocabSetFactoryTest extends TestCase {

	/**
	 * Constructor for GenericVocabSetFactoryTest.
	 * @param name
	 */
	public GenericVocabSetFactoryTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(GenericVocabSetFactoryTest.class);
		return suite;
	}

	public void testSetProperties() throws VocabException {
		Properties props1 = new Properties();
		props1.put(VocabConstants.VOCAB_FACTORY_CLASS, "org.authorsite.vocab.core.StubVocabSetFactory");
		GenericVocabSetFactory genFactory = GenericVocabSetFactory.getInstance();
		genFactory.setProperties(props1);
		VocabSet set = genFactory.getVocabSet();
		Assert.assertTrue(set instanceof StubAbstractVocabSet);

		Properties props2 = new Properties();
		props2.put(VocabConstants.VOCAB_FACTORY_CLASS, "org.authorsite.vocab.core.memory.MemoryVocabSetFactory");
		genFactory.setProperties(props2);
		set = genFactory.getVocabSet();
		Assert.assertTrue(set instanceof MemoryVocabSet);

	}

	public void testAddProperties() throws VocabException {
		Properties props1 = new Properties();
		props1.put(VocabConstants.VOCAB_FACTORY_CLASS, "org.authorsite.vocab.core.memory.MemoryVocabSetFactory");
		GenericVocabSetFactory genFactory = GenericVocabSetFactory.getInstance();
		genFactory.setProperties(props1);
		VocabSet set1 = genFactory.getVocabSet();

		genFactory.addProperty("some", "thing");
		VocabSet set2 = genFactory.getVocabSet();

		Assert.assertFalse(set1 == set2);
	}

	public void testRemoveProperties() throws VocabException {
		Properties props1 = new Properties();
		props1.put(VocabConstants.VOCAB_FACTORY_CLASS, "org.authorsite.vocab.core.memory.MemoryVocabSetFactory");
		props1.put("some", "thing"); // meaningless property
		GenericVocabSetFactory genFactory = GenericVocabSetFactory.getInstance();
		genFactory.setProperties(props1);
		VocabSet set1 = genFactory.getVocabSet();

		genFactory.removeProperty("some");
		VocabSet set2 = genFactory.getVocabSet();

		Assert.assertFalse(set1 == set2);
	}

}
