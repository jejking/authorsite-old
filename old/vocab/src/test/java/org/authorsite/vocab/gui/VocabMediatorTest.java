/*
 * VocabMediatorTest.java, created on 23-Nov-2003 at 21:01:58
 * 
 * Copyright John King, 2003.
 *
 *  VocabMediatorTest.java is part of authorsite.org's VocabManager program.
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
package org.authorsite.vocab.gui;

import java.lang.reflect.*;
import java.util.*;

import org.authorsite.vocab.core.*;
import org.authorsite.vocab.exceptions.*;

import junit.framework.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class VocabMediatorTest extends TestCase {

	public VocabMediatorTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite(VocabMediatorTest.class);
		return suite;
	}

	public void testAddListener() throws VocabException {
		VocabMediator mediator = new VocabMediator(new StubVocabSetFactory().getVocabSet());
		VocabEventListener creationListener = new StubVocabEventListener();
		mediator.addListener(VocabEventType.NODE_CREATED, creationListener);

		Field[] fields = mediator.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals("eventMap")) {
				fields[i].setAccessible(true);
				try {
					HashMap map = (HashMap) fields[i].get(mediator);
					List list = (List) map.get(VocabEventType.NODE_CREATED);
					Assert.assertEquals(creationListener, list.get(0));
				}
				catch (IllegalArgumentException e) {
					throw new VocabException(e.getMessage());
				}
				catch (IllegalAccessException e) {
					throw new VocabException(e.getMessage());
				}
				break;
			}
		}
	}

	public void testRemoveListener() throws VocabException {
		VocabMediator mediator = new VocabMediator(new StubVocabSetFactory().getVocabSet());
		VocabEventListener creationListener = new StubVocabEventListener();
		mediator.addListener(VocabEventType.NODE_CREATED, creationListener);
		VocabEventListener creationListener2 = new StubVocabEventListener();
		mediator.addListener(VocabEventType.NODE_CREATED, creationListener2);
		mediator.removeListener(VocabEventType.NODE_CREATED, creationListener);

		Field[] fields = mediator.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getName().equals("eventMap")) {
				fields[i].setAccessible(true);
				try {
					HashMap map = (HashMap) fields[i].get(mediator);
					List list = (List) map.get(VocabEventType.NODE_CREATED);
					Assert.assertEquals(creationListener2, list.get(0));
				}
				catch (IllegalArgumentException e) {
					throw new VocabException(e.getMessage());
				}
				catch (IllegalAccessException e) {
					throw new VocabException(e.getMessage());
				}
				break;
			}
		}
	}
	
	public void testNotifyListeners1() throws VocabException {
		// pretend to create a node. a listener listening should pick it up, a listener listening for other events shouldn't
		VocabMediator mediator = new VocabMediator(new StubVocabSetFactory().getVocabSet());
		StubVocabEventListener creationListener = new StubVocabEventListener();
		mediator.addListener(VocabEventType.NODE_CREATED, creationListener);
		StubVocabEventListener deletionListener = new StubVocabEventListener();
		mediator.addListener(VocabEventType.NODE_DELETED, deletionListener);
		
		StubCreateNodeCommand createCommand = new StubCreateNodeCommand();
		createCommand.execute(mediator);
		
		Assert.assertNull(deletionListener.getVocabEvent());
		Assert.assertEquals(new StubAbstractVocabNode(new Integer(1), "test", "noun", Locale.GERMAN), creationListener.getVocabEvent().getVocabNode());
	}

}
