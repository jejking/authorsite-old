/*
 * MemoryVocabTest.java, created on 25-Oct-2003 at 23:10:52
 * 
 * Copyright John King, 2003.
 *
 *  MemoryVocabTest.java is part of authorsite.org's VocabManager program.
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
import junit.framework.*;
import junit.textui.*;

/**
 * Runs all the tests in the test package for <code>org.authorsite.vocab.core.memory</code>.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class MemoryVocabTest extends TestCase {

	public MemoryVocabTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(MemoryVocabSetFactoryTest.class);
		suite.addTestSuite(MemoryVocabSetTest.class);
		suite.addTestSuite(MemoryVocabSetQueryTest.class);
		suite.addTestSuite(MemoryVocabNodeTest.class);
		return suite;
	}
}
