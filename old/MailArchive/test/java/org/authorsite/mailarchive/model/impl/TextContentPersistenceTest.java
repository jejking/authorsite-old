/*
 * TextContentPersistenceTest.java, created on 19-Aug-2004 at 17:57:10
 * 
 * Copyright John King, 2004.
 *
 *  TextContentPersistenceTest.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.model.impl;

import java.util.*;
import org.authorsite.mailarchive.model.*;
import org.hibernate.*;
import junit.framework.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class TextContentPersistenceTest extends AbstractPersistenceTestCase {
    	
    	public TextContentPersistenceTest(String name) throws Exception {
    	    super(name);
    	}
    	
    	// need to test creating, with languages, with a role
    	public void testCreate() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this is some text - 1");
    	    text.setMimeType("text/plain");
    	    text.setRole(TextContentRole.BODY_PART);
    	    text.setDescription("some text");
    	    text.setDisposition("inline");
    	    text.setFileName("file.txt");
    	    text.setSize("this is some text - 1".length());
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    TextContent loaded = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text - 1'").list();
    	        loaded = (TextContentImpl) results.get(0);
    	        Assert.assertEquals(text, loaded);
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	}
    	
    	// attempt to save without a role - should fail
    	public void testCreateNoRole() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this is some content - 2");
    	    text.setDescription("some text");
    	    text.setMimeType("text/plain");
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    boolean caughtCorrectException = false;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (PropertyValueException pve) {
    	        // this is what we expect to see because of the not null setting in the hibernate descriptors
    	        caughtCorrectException = true;
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    Assert.assertTrue(caughtCorrectException);
    	}
    	
    	public void testCreateWithLanguages() throws HibernateException {
    	    	TextContent text = new TextContentImpl();
    	    	text.setContent("dieser Text ist auf Deutsch - 1");
    	    	text.setMimeType("text/plain");
    	    	text.setRole(TextContentRole.MSG_BODY);
    	    	
    	    	Set langs = new HashSet();
    	    	langs.add(Language.DE);
    	    	
    	    	text.setLanguages(langs);
    	    	
    	    	Session session = null;
    	    	Transaction tx = null;
    	    	
    	    	try {
    	    	    session = sessionFactory.openSession();
    	    	    tx = session.beginTransaction();
    	    	    session.save(text);
    	    	    tx.commit();
    	    	}
    	    	catch (HibernateException he) {
    	    	    if (tx != null) {
    	    	        tx.rollback();
    	    	    }
    	    	}
    	    	finally {
    	    	    session.close();
    	    	}
    	    	
    	    	TextContent loaded = null;
    	    	try {
    	    	    session = sessionFactory.openSession();
    	    	    List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
    				" where textContent.content = 'dieser Text ist auf Deutsch - 1'").list();
    	    	    loaded = (TextContent) results.get(0);
    	    	    Assert.assertEquals(text, loaded);
    	    	    session.close();
    	    	    Set loadedLangs = loaded.getLanguages();
    	    	    Assert.assertTrue(loadedLangs.size() == 1);
    	    	    boolean foundGerman = false;
    	    	    Iterator langsIt = loadedLangs.iterator();
    	    	    while (langsIt.hasNext()) {
    	    	        Language lang = (Language) langsIt.next();
    	    	        if (lang.equals(Language.DE)) {
    	    	            foundGerman = true;
    	    	        }
    	    	    }
    	    	    Assert.assertTrue(foundGerman);
    	    	}
    	    	catch (HibernateException he) {
    	    	    throw he;
    	    	}
    	    	finally {
    	    	    session.close();
    	    	}
    	}
    	
    	// update and set role to null - should fail on save
    	public void testUpdateWithRoleTurnedToNull() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this is some text - 3");
    	    text.setMimeType("text/plain");
    	    text.setRole(TextContentRole.BODY_PART);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    text.setRole(null); // irritatingly, code to catch this on the POJO breaks hibernate
    	    boolean rolledBack = false;
    	    boolean caughtCorrectException = false;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.update(text);
    	        tx.commit();
    	    }
    	    catch (PropertyValueException pve) {
    	        caughtCorrectException = true;
    	        tx.rollback();
    	        rolledBack = true;
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    Assert.assertTrue(caughtCorrectException);
    	    Assert.assertTrue(rolledBack);
    	    
    	    TextContent loaded = null;
    	    
    	    // reset text to have the old text content role
    	    text.setRole(TextContentRole.BODY_PART);
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text - 3'").list();
    	        loaded = (TextContentImpl) results.get(0);
    	        Assert.assertEquals(text, loaded);
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	}
    	
    	// update
    	public void testUpdate() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this is some text - 4");
    	    text.setMimeType("text/plain");
    	    text.setRole(TextContentRole.BODY_PART);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    // update
    	    
    	    text.setContent("this is some text - 5");
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.update(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    // have a look in the db. should return nothing on text - 4, and one on text - 5
    	    try {
    	        session = sessionFactory.openSession();
    	        TextContent loaded = null;
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text - 5'").list();
    	        loaded = (TextContentImpl) results.get(0);
    	        Assert.assertEquals(text, loaded);
    	        List nextResults = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text - 4'").list(); // should have been updated
    	        Assert.assertTrue(nextResults.isEmpty());
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	}
    	
    	// update with adding languages
    	public void testUpdateAddingLanguages() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this is some text - 6");
    	    text.setMimeType("text/plain");
    	    text.setRole(TextContentRole.MSG_BODY);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he ) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    text.setContent("this is some text. Diesmal auch auf Deutsch - 1");
    	    
    	    // add a couple of languages
    	    Set langs = new HashSet();
    	    langs.add(Language.EN);
    	    langs.add(Language.DE);
    	    
    	    text.setLanguages(langs);
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.update(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    TextContent loaded = null;
    	    // load up and compare
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text. Diesmal auch auf Deutsch - 1'").list();
    	        loaded = (TextContent) results.get(0);
    	        session.close();
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    Assert.assertEquals(text, loaded);
    	    Set loadedLangs = loaded.getLanguages();
    	    Assert.assertEquals(2, loadedLangs.size());
    	    boolean foundEnglish = false;
    	    boolean foundGerman = false;
    	    Iterator langsIt = loadedLangs.iterator();
    	    while (langsIt.hasNext()) {
    	        Language lang = (Language) langsIt.next();
    	        if (lang.equals(Language.EN)) {
    	            foundEnglish = true;
    	            continue;
    	        }
    	        if (lang.equals(Language.DE)) {
    	            foundGerman = true;
      	        }
    	    }
    	    Assert.assertTrue(foundEnglish & foundGerman);
    	    
    	}
    	
    	// update removing languages
    	public void testUpdateRemovingLanguages() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this some text. Diesmal auch auf Deutsch - 2");
    	    text.setMimeType("text/plain");
    	    text.setRole(TextContentRole.MSG_BODY);
    	    
    	    Set langs = new HashSet();
    	    langs.add(Language.EN);
    	    langs.add(Language.DE);
    	    
    	    text.setLanguages(langs);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    Set newLangs = new HashSet();
    	    newLangs.add(Language.EN);
    	    
    	    text.setLanguages(newLangs);
    	    text.setContent("this is some text. Just in English - 1");
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.update(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    TextContent loaded = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text. Just in English - 1'").list();
    	        loaded = (TextContent) results.get(0);
    	        session.close();
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    Assert.assertEquals(text, loaded);
    	    Set loadedLangs = loaded.getLanguages();
    	    Assert.assertEquals(1, loadedLangs.size());
    	    boolean foundEnglish = false;
    	    boolean foundGerman = false;
    	    Iterator langsIt = loadedLangs.iterator();
    	    while (langsIt.hasNext()) {
    	        Language lang = (Language) langsIt.next();
    	        if (lang.equals(Language.EN)) {
    	            foundEnglish = true;
    	            continue;
    	        }
    	        if (lang.equals(Language.DE)) {
    	            foundGerman = true;
      	        }
    	    }
    	    Assert.assertTrue(foundEnglish);
    	    Assert.assertFalse(foundGerman);
    	    session = sessionFactory.openSession();
    	                                  
    	}
    	
    	// update, leaving languages untouched
    	public void testUpdateNoLanguageChange() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this is some text. Diesmal auch auf Deutsch - 2");
    	    text.setMimeType("text/plain");
    	    text.setRole(TextContentRole.MSG_BODY);
    	    
    	    Set langs = new HashSet();
    	    langs.add(Language.EN);
    	    langs.add(Language.DE);
    	    text.setLanguages(langs);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he ) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    text.setContent("this is some text. Diesmal auch auf Deutsch - 3");
    	    text.setRole(TextContentRole.BODY_PART);
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.update(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    TextContent loaded = null;
    	    // load up and compare
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this is some text. Diesmal auch auf Deutsch - 3'").list();
    	        loaded = (TextContent) results.get(0);
    	        session.close();
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    Assert.assertEquals(text, loaded);
    	    Set loadedLangs = loaded.getLanguages();
    	    Assert.assertEquals(2, loadedLangs.size());
    	    boolean foundEnglish = false;
    	    boolean foundGerman = false;
    	    Iterator langsIt = loadedLangs.iterator();
    	    while (langsIt.hasNext()) {
    	        Language lang = (Language) langsIt.next();
    	        if (lang.equals(Language.EN)) {
    	            foundEnglish = true;
    	            continue;
    	        }
    	        if (lang.equals(Language.DE)) {
    	            foundGerman = true;
      	        }
    	    }
    	    Assert.assertTrue(foundEnglish & foundGerman);
    	}
    	   	
    	// delete
    	public void testDelete() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this will be deleted - 1");
    	    text.setRole(TextContentRole.MSG_BODY);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    // delete it
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.delete(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    // try and find it, it should be gone
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this will be deleted - 1'").list();
    	        session.close();
    	        Assert.assertTrue(results.isEmpty());
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	}
    	
    	// delete with languages
        public void testDeleteWithLanguages() throws HibernateException {
    	    TextContent text = new TextContentImpl();
    	    text.setContent("this will be deleted - 2");
    	    text.setRole(TextContentRole.MSG_BODY);
    	    
    	    Set langs = new HashSet();
    	    langs.add(Language.EN);
    	    
    	    Session session = null;
    	    Transaction tx = null;
    	    
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.save(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    // delete it
    	    try {
    	        session = sessionFactory.openSession();
    	        tx = session.beginTransaction();
    	        session.delete(text);
    	        tx.commit();
    	    }
    	    catch (HibernateException he) {
    	        if (tx != null) {
    	            tx.rollback();
    	        }
    	    }
    	    finally {
    	        session.close();
    	    }
    	    
    	    // try and find it, it should be gone
    	    try {
    	        session = sessionFactory.openSession();
    	        List results = session.createQuery("from org.authorsite.mailarchive.model.impl.TextContentImpl as textContent " +
				" where textContent.content = 'this will be deleted - 2'").list();
    	        session.close();
    	        Assert.assertTrue(results.isEmpty());
    	    }
    	    catch (HibernateException he) {
    	        throw he;
    	    }
    	    finally {
    	        session.close();
    	    }
        }
}
