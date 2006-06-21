/*
 * AbstractVocabSetTest.java, created on 21-Sep-2003 at 17:12:25
 * 
 * Copyright John King, 2003.
 *
 *  AbstractVocabSetTest.java is part of authorsite.org's VocabManager program.
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

/**
 * Tests the methods implemented in <code>AbstractVocabSet</code> using the
 * <code>StubAbstractVocabSet</code>.
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 * @see org.authorsite.vocab.core.AbstractVocabSet
 * @see org.authorsite.vocab.core.StubAbstractVocabSet
 */
public class AbstractVocabSetTest extends TestCase {
	
	public AbstractVocabSetTest(String name) {
		super(name);
	}
	
	public static void main(String[] args) {
		TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite = new TestSuite(AbstractVocabSetTest.class);
		return suite;
	}
	
	public void testCreation() {
		AbstractVocabSet abstractSet = new StubAbstractVocabSet();
		Assert.assertEquals(Locale.getDefault(), abstractSet.getLocale());
	}
	
	public void testCreationWithLocale() {
		AbstractVocabSet abstractSet = new StubAbstractVocabSet(Locale.GERMAN);
		Assert.assertEquals(Locale.GERMAN, abstractSet.getLocale());
	}
}
