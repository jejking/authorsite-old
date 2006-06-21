/*
 * BinaryContentImplPersistenceTest.java, created on 09-Aug-2004 at 20:46:54
 * 
 * Copyright John King, 2004.
 *
 *  BinaryContentImplPersistenceTest.java is part of authorsite.org's MailArchive program.
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

import java.io.*;
import java.util.*;

import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;

import org.hibernate.*;

import junit.framework.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class BinaryContentImplPersistenceTest extends AbstractPersistenceTestCase {
    
    private static byte[] jk1Bytes;
	private static byte[] jk2Bytes;
	private static Logger logger = Logger.getLogger(BinaryContentImplPersistenceTest.class);

	static {
		try {
			//	load up the two test images as byte arrays
			InputStream jk1 = BinaryContentImplTest.class.getResourceAsStream("jejking1.jpg");
			InputStream jk2 = BinaryContentImplTest.class.getResourceAsStream("jejking2.jpg");
			jk1Bytes = getBytesFromInputStream(jk1);
			jk2Bytes = getBytesFromInputStream(jk2);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
    public BinaryContentImplPersistenceTest(String name) throws Exception {
        super(name);
    }
    
    // need to test...
    // creating just a binary content impl with all fields
    
    public void testCreationSimple() throws HibernateException {
        
        // create a basic binary content impl object
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test1.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
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
        
        // now, load a copy of the object from the db using hibernate
        BinaryContentImpl loaded = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from org.authorsite.mailarchive.model.impl.BinaryContentImpl as binaryContent " +
            " where binaryContent.fileName = 'test1.jpg'");
            List results = query.list();
            loaded = (BinaryContentImpl) results.get(0);
            Assert.assertEquals(impl1, loaded);
            
        }
        catch (HibernateException he ) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // updating the binary content impl
    public void testUpdateInSameSession() throws HibernateException {
        //      create a basic binary content impl object
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test2.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            logger.info(impl1.getID());
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
        
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.BinaryContentImpl as binaryContent " +
			" where binaryContent.fileName = 'test2.jpg'").list();
            impl1 =  (BinaryContentImpl) results.get(0);
            impl1.setDescription("a different test description");
            impl1.setDisposition("attachment");
            impl1.setContent(jk2Bytes);
            impl1.setFileName("test3.jpg");
            impl1.setSize(jk2Bytes.length);
            //session.update(impl1);
            //session.flush();
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
        
        // now, load a copy of the object from the db using hibernate
        BinaryContentImpl loaded = null;
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.BinaryContentImpl as binaryContent " +
                    				" where binaryContent.fileName = 'test3.jpg'").list();
            loaded = (BinaryContentImpl) results.get(0);
            Assert.assertEquals(impl1, loaded);
            Assert.assertEquals(impl1.getID(), loaded.getID());
        }
        catch (HibernateException he ) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    public void testUpdateSeparateSessions() throws HibernateException {
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test4.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            logger.info(impl1.getID());
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
        
        impl1.setDescription("a different test description");
        impl1.setDisposition("attachment");
        impl1.setContent(jk2Bytes);
        impl1.setFileName("test5.jpg");
        impl1.setSize(jk2Bytes.length);
        
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(impl1);
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
        
        // now, load a copy of the object from the db using hibernate
        BinaryContentImpl loaded = null;
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.BinaryContentImpl as binaryContent " +
                    				" where binaryContent.fileName = 'test5.jpg'").list();
            loaded = (BinaryContentImpl) results.get(0);
            Assert.assertEquals(impl1, loaded);
            Assert.assertEquals(impl1.getID(), loaded.getID());
        }
        catch (HibernateException he ) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // deleting the binary content impl
    public void testDelete() throws HibernateException {
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test6.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            tx.commit();
            logger.info(impl1.getID());
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
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.delete(impl1);
            tx.commit();
        }
        catch (HibernateException he ) {
            throw he;
        }
        finally {
            session.close();
        }
    
        
        // now, try to load a copy of the object from the db using hibernate - it shouldn't be there
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.BinaryContentImpl as binaryContent " +
                    				" where binaryContent.fileName = 'test6.jpg'").list();
            Assert.assertTrue(results.isEmpty());
        }
        catch (HibernateException he ) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
     // creating binary content impl with two languages
    public void testCreateWithTwoLanguages() throws HibernateException {
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test7.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Set impl1Langs = new HashSet();
        impl1Langs.add(Language.EN);
        impl1Langs.add(Language.DE);
        
        impl1.setLanguages(impl1Langs);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            tx.commit();
            logger.info(impl1.getID());
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
        
        // now load it up and check the langs are the same
        
        try {
            session = sessionFactory.openSession();
            BinaryContent loaded = (BinaryContent) session.load(BinaryContentImpl.class, impl1.getID());
            Assert.assertTrue(loaded.getLanguages().size() == 2);
            Iterator it = loaded.getLanguages().iterator();
            boolean foundEnglish = false;
            boolean foundGerman = false;
            while (it.hasNext()) {
                Language lang = (Language) it.next();
                if (lang.equals(Language.EN)) {
                    foundEnglish = true;
                    continue;
                }
                if (lang.equals(Language.DE)) {
                    foundGerman = true;
                    continue;
                }
            }
            Assert.assertTrue(foundEnglish & foundGerman);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // updating binary content impl with two languages - check languages are same
    public void testUpdateWithLanguages() throws HibernateException {
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test8.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Set impl1Langs = new HashSet();
        impl1Langs.add(Language.EN);
        impl1Langs.add(Language.DE);
        
        impl1.setLanguages(impl1Langs);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            tx.commit();
            logger.info(impl1.getID());
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
        
        // change impl1 a bit
        impl1.setContent(jk2Bytes);
        impl1.setDescription("a different test description");
        impl1.setDisposition("attachment");
        impl1.setFileName("test9.jpg");
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(impl1);
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
        
        // now, lets load it up and see what happened. Should be no changes to langs
        try {
            session = sessionFactory.openSession();
            BinaryContent loaded = (BinaryContent) session.load(BinaryContentImpl.class, impl1.getID());
            Assert.assertTrue(loaded.getLanguages().size() == 2);
            Iterator it = loaded.getLanguages().iterator();
            boolean foundEnglish = false;
            boolean foundGerman = false;
            while (it.hasNext()) {
                Language lang = (Language) it.next();
                if (lang.equals(Language.EN)) {
                    foundEnglish = true;
                    continue;
                }
                if (lang.equals(Language.DE)) {
                    foundGerman = true;
                    continue;
                }
            }
            Assert.assertTrue(foundEnglish & foundGerman);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // update it by changing the languages
//  updating binary content impl with two languages - check languages are same
    public void testUpdateLanguages() throws HibernateException {
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test10.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Set impl1Langs = new HashSet();
        impl1Langs.add(Language.EN);
        impl1Langs.add(Language.DE);
        
        impl1.setLanguages(impl1Langs);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            tx.commit();
            logger.info(impl1.getID());
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
        
        // change impl1 a bit
        impl1.setContent(jk2Bytes);
        impl1.setDescription("a different test description");
        impl1.setDisposition("attachment");
        impl1.setFileName("test11.jpg");
        
        // and its langs
        Set newLangs = new HashSet();
        // curiously, it's now in french and arabic...
        newLangs.add(Language.FR);
        newLangs.add(Language.AR);
        
        impl1.setLanguages(newLangs);
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(impl1);
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
        
        // now, lets load it up and see what happened. Should be no changes to langs
        try {
            session = sessionFactory.openSession();
            BinaryContent loaded = (BinaryContent) session.load(BinaryContentImpl.class, impl1.getID());
            Assert.assertTrue(loaded.getLanguages().size() == 2);
            Iterator it = loaded.getLanguages().iterator();
            boolean foundFrench = false;
            boolean foundArabic = false;
            while (it.hasNext()) {
                Language lang = (Language) it.next();
                if (lang.equals(Language.FR)) {
                    foundFrench = true;
                    continue;
                }
                if (lang.equals(Language.AR)) {
                    foundArabic = true;
                    continue;
                }
            }
            Assert.assertTrue(foundFrench & foundArabic);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // deleting binary content impl with two languages - check language-link table is correctly emptied
    public void testDeletionWithLanguages() throws HibernateException {
        BinaryContentImpl impl1 = new BinaryContentImpl();
        impl1.setContent(jk1Bytes);
        impl1.setDescription("a test description");
        impl1.setDisposition("inline");
        impl1.setFileName("test12.jpg");
        impl1.setMimeType("image/jpeg");
        impl1.setSize(jk1Bytes.length);
        
        Set impl1Langs = new HashSet();
        impl1Langs.add(Language.EN);
        impl1Langs.add(Language.DE);
        
        impl1.setLanguages(impl1Langs);
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(impl1);
            tx.commit();
            logger.info(impl1.getID());
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
        
        // now decide we don't want it after all
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.delete(impl1);
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
        
        // have a look for it and check it's gone
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.BinaryContentImpl as binaryContent " +
			" where binaryContent.fileName = 'test12.jpg'").list();
            Assert.assertTrue(results.isEmpty());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }

    private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int byteIn = is.read();
		while (byteIn >= 0) {
		    out.write(byteIn);
		    byteIn = is.read();
		}
		is.close();
		return out.toByteArray();
	}

    
    
    
}
