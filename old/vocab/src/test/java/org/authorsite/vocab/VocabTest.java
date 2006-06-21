/*
 * VocabTest.java, created on 26-Oct-2003 at 22:27:46
 * 
 * Copyright John King, 2003.
 *
 *  VocabTest.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab;
import org.authorsite.vocab.core.AbstractVocabTest;
import org.authorsite.vocab.core.memory.MemoryVocabTest;

import junit.framework.*;
import junit.textui.*;
/**
 * Runs all unit tests for the Vocab project.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class VocabTest extends TestCase {
	
	public VocabTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite("All authorsite.org vocab tests");
		suite.addTest(MemoryVocabTest.suite());
		suite.addTest(AbstractVocabTest.suite());
		return suite;
	}
}
