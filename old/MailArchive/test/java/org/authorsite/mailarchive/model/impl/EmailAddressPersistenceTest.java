/*
 * EmailAddressPersistenceTest.java, created on 23-Aug-2004 at 17:25:05
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressPersistenceTest.java is part of authorsite.org's MailArchive program.
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

import junit.framework.*;
import org.hibernate.*;

import org.authorsite.mailarchive.model.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.5 $
 */
public class EmailAddressPersistenceTest extends AbstractPersistenceTestCase {
    
    public EmailAddressPersistenceTest(String name) throws Exception {
        super(name);
    }
    
    // create with all fields set
    public void testCreateAllFieldsNoPerson() throws HibernateException {
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("test1@test.com");
        addr.setPersonalName("Testus McTest");
        addr.setMailingList(false);
        addr.setProcess(false);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr);
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
        
        // check we can load it up again
        EmailAddress loaded = null;
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test1@test.com'").list();
	        loaded = (EmailAddress) results.get(0);
	        Assert.assertEquals(addr, loaded);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
        Assert.assertFalse(loaded.isMailingList());
        Assert.assertFalse(loaded.isProcess());
    }
    
    // nullability of the email address - create
    public void testCreateNullAddress() throws HibernateException {
        EmailAddress addr = new EmailAddressImpl();
        addr.setMailingList(false);
        addr.setProcess(false);
        addr.setPersonalName("Mr Null");
        
        Session session = null;
        Transaction tx = null;
        boolean caughtCorrectException = false;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr);
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
    
    // nullability of the email address - update 
    public void testUpdateNullAddress() throws HibernateException {
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("test2@test.com");
        
        Session session = null;
        Transaction tx = null;
        boolean caughtCorrectException = false;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr);
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
        
        // now do something foolish!
        addr.setAddress(null);
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(addr);
            tx.commit();
        }
        catch (PropertyValueException pve) {
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
        
        EmailAddress loaded = null;
        // make sure the persisted object is untouched
        try {
            session = sessionFactory.openSession();
            loaded = (EmailAddress) session.load(EmailAddressImpl.class, ((Identifiable)addr).getID());
            Assert.assertEquals("test2@test.com", loaded.getAddress());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
        Assert.assertEquals("test2@test.com", loaded.getAddress());
       
    }
    
    // unique constraint on email address
    public void testUniqueAddressConstraint() throws HibernateException {
        EmailAddress addr1 = new EmailAddressImpl();
        addr1.setAddress("test3@test.com");
        
        EmailAddress addr2 = new EmailAddressImpl();
        addr2.setAddress("test3@test.com"); // oops, it's the same as the last one
        
        Session session = null;
        Transaction tx = null;
        
        boolean caughtCorrectException = false;
        
        // try it in one transaction
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr1);
            session.save(addr2); // should fail here
            tx.commit(); 
        }
        catch (org.hibernate.JDBCException e) {
            caughtCorrectException = true;
            if (tx != null) {
                tx.rollback();
            }
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
        
        // check that nothing got written to the db
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test3@test.com'").list();
            Assert.assertTrue(results.isEmpty());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
        // and try in two separate transactions
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr1);
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
        
        caughtCorrectException = false;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr2);
            tx.commit();
        }
        catch (JDBCException e) {
            caughtCorrectException = true;
            if (tx != null) {
                tx.rollback();
            }
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
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test3@test.com'").list();
            Assert.assertEquals(1, results.size());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // create with Person ref
    public void testCreateWithPerson() throws HibernateException {
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("test4@test.com");
        Person person = new PersonImpl();
        person.setMainName("EmailAddressTest1");
        person.setGivenName("Test1");
        addr.setPerson(person);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
            session.save(addr);
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
        
        EmailAddress loadedAddr = null;
        Person loadedPerson = null;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test4@test.com'").list();
            loadedAddr = (EmailAddress) results.get(0);
            Assert.assertEquals(addr, loadedAddr);
            loadedPerson = loadedAddr.getPerson();
            Assert.assertEquals(person, loadedPerson);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // change person ref, update
    public void testUpdateWithPerson() throws HibernateException {
        
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("test5@test.com");
        
        Person person1 = new PersonImpl();
        person1.setMainName("EmailAddressTest2");
        person1.setGivenName("Testus2");
        
        Person person2 = new PersonImpl();
        person2.setMainName("EmailAddressTest3");
        person2.setGivenName("Testus3");
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person1);
            session.save(person2);
            session.save(addr);
            addr.setPerson(person1);
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
        
        // now change the person...
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            addr.setPerson(person2);
            session.update(addr);
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
        
        EmailAddress loadedAddr = null;
        Person loadedPerson = null;
        
        // load up the email address, we should have person 2...
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test5@test.com'").list();
            loadedAddr = (EmailAddress) results.get(0);
            Assert.assertEquals(addr, loadedAddr);
            loadedPerson = loadedAddr.getPerson();
            Assert.assertEquals(person2, loadedPerson);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // delete, check person ref is not deleted
    public void testDeleteWithPerson() throws HibernateException {
        
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("test6@test.com");
        
        Person person = new PersonImpl();
        person.setMainName("EmailAddressTest4");
        person.setGivenName("Testus4");
        
        addr.setPerson(person);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
            session.save(addr);
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
        
        // now delete the email address object, we should still be able to find person
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.delete(addr);
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
        
        // check the address can't be found, and that the person can
        try {
            session = sessionFactory.openSession();
            List addrResults = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test6@test.com'").list();
            Assert.assertTrue(addrResults.isEmpty());
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'EmailAddressTest4'").list();
            Person loadedPerson = (Person) personResults.get(0);
            Assert.assertEquals(person, loadedPerson);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // remove the person ref, update
    public void testUpdateRemovePersonRef() throws HibernateException {
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("test7@test.com");
        
        Person person = new PersonImpl();
        person.setMainName("EmailAddressTest5");
        person.setGivenName("Testus5");
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
            addr.setPerson(person);
            session.save(addr);
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
        
        // now remove any association between the person and the email address
        addr.setPerson(null);
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(addr);
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
        
        // load up the email address, check person is null
        try {
            session = sessionFactory.openSession();
            List addrResults = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'test7@test.com'").list();
            EmailAddress loadedAddr = (EmailAddress) addrResults.get(0);
            Assert.assertEquals(addr, loadedAddr);
            Assert.assertNull(loadedAddr.getPerson()); // no person ref in addr
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'EmailAddressTest5'").list(); 
            Person loadedPerson = (Person) personResults.get(0);
            Assert.assertEquals(person, loadedPerson); // person object still persisted happily
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }

}
