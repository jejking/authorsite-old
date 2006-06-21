/*
 * PersonPersistenceTest.java, created on 30-Aug-2004 at 13:36:24
 * 
 * Copyright John King, 2004.
 *
 *  PersonPersistenceTest.java is part of authorsite.org's MailArchive program.
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

import org.authorsite.mailarchive.model.*;

import org.hibernate.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class PersonImplPersistenceTest extends AbstractPersistenceTestCase {

    public PersonImplPersistenceTest(String name) throws Exception {
        super(name);
    }
    
    // test create, no email addresses, including dates of birth and death
    public void testCreateNoEmail() throws HibernateException {
        Person person = new PersonImpl();
        person.setMainName("PersonTest1");
        person.setGivenName("PersonTestus1");
        person.setGenderCode(Person.MALE);
        person.setOtherNames("Test Test McTest");
        person.setPrefix("Professor");
        person.setSuffix("The Third");
        Calendar cal1871 = new GregorianCalendar(1871, Calendar.NOVEMBER, 10); 
        Date date1871 = cal1871.getTime();
        person.setDateOfBirth(date1871);
        
        Calendar cal1914 = new GregorianCalendar(1914, Calendar.FEBRUARY, 11);
        Date date1914 = cal1914.getTime();
        person.setDateOfDeath(date1914);
		
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
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
        
        // now try and load our person and check that it's equal
        Person loaded = null;
        try {
            session = sessionFactory.openSession();
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest1'").list();
            loaded = (Person) personResults.get(0);
            Assert.assertEquals(person, loaded);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test update, no email addresses, including dates of birth and death
    public void testUpdateNoEmailAddresses() throws HibernateException {
        Person person = new PersonImpl();
        person.setMainName("PersonTest2");
        person.setGivenName("PersonTestus2");
        
        Calendar cal1871 = new GregorianCalendar(1871, Calendar.NOVEMBER, 10); 
        Date date1871 = cal1871.getTime();
        person.setDateOfBirth(date1871);
        
        Calendar cal1914 = new GregorianCalendar(1914, Calendar.FEBRUARY, 11);
        Date date1914 = cal1914.getTime();
        person.setDateOfDeath(date1914);
		
        person.setDateOfBirth(date1871);
        person.setDateOfDeath(date1914);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
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
        
        Date date1960 = new GregorianCalendar(1960, Calendar.MAY, 23).getTime();
        person.setDateOfBirth(date1914);
        person.setDateOfDeath(date1960);
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.update(person);
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
        
        
        // now try and load our person and check that it's equal
        Person loaded = null;
        try {
            session = sessionFactory.openSession();
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest2'").list();
            loaded = (Person) personResults.get(0);
            Assert.assertEquals(person, loaded);
            Assert.assertEquals(date1914, loaded.getDateOfBirth());
            Assert.assertEquals(date1960, loaded.getDateOfDeath());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // test create, one email address -- email address should be the one controlling this bidirectional rel from a a hibernate perspective
    public void testCreateOneEmailAddress() throws HibernateException {
        Person person = new PersonImpl();
        person.setMainName("PersonTest3");
        person.setGivenName("PersonTestus3");
        
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("persontest3@test.com");
        
        Session session = null;
        Transaction tx = null;
                
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
            session.save(addr);
            person.addEmailAddress(addr); // nb, this causes no persistence change
            addr.setPerson(person); // whereas this does. email addr set in person is marked as inverse
            session.update(person);
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
        
        Person loadedPerson = null;
                
        try {
            session = sessionFactory.openSession();
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest3'").list();
            loadedPerson = (Person) personResults.get(0);
            Assert.assertEquals(person, loadedPerson);
            Set loadedAddresses = loadedPerson.getEmailAddresses();
            Assert.assertEquals(1, loadedAddresses.size());
            Iterator addrIt = loadedAddresses.iterator();
            boolean foundAddr = false;
            while (addrIt.hasNext()) {
                EmailAddress currentAddr = (EmailAddress) addrIt.next();
                if (currentAddr.equals(addr)) {
                    foundAddr = true;
                }
            }
            Assert.assertTrue(foundAddr);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test create, multiple email addresses
    public void testCreateMultipleEmailAddresses() throws HibernateException {
        Person person = new PersonImpl();
        person.setMainName("PersonTest4");
        person.setGivenName("PersonTestus4");
        
        EmailAddress addr1 = new EmailAddressImpl();
        addr1.setAddress("persontest4@test1.com");
        
        EmailAddress addr2 = new EmailAddressImpl();
        addr2.setAddress("persontest4@test2.com");
        
        EmailAddress addr3 = new EmailAddressImpl();
        addr3.setAddress("persontest4@test3.com");
        
        Set emailAddresses = new HashSet();
        emailAddresses.add(addr1);
        emailAddresses.add(addr2);
        emailAddresses.add(addr3);
        
        person.setEmailAddresses(emailAddresses); // but hibernate doesn't know about this...
        
        addr1.setPerson(person);
        addr2.setPerson(person);
        addr3.setPerson(person); // now hibernate will know
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person);
            session.save(addr1);
            session.save(addr2);
            session.save(addr3);
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
        
        Person loadedPerson = null;
        
        try {
            session = sessionFactory.openSession();
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest4'").list();
            loadedPerson = (Person) personResults.get(0);
            Assert.assertEquals(person, loadedPerson); 
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
        Set loadedAddresses = loadedPerson.getEmailAddresses();
        Assert.assertEquals(3, loadedAddresses.size());
        Iterator addrIt = loadedAddresses.iterator();
        boolean foundAddr1 = false;
        boolean foundAddr2 = false;
        boolean foundAddr3 = false;
        while (addrIt.hasNext()) {
            EmailAddress currentAddr = (EmailAddress) addrIt.next();
            if (currentAddr.equals(addr1)) {
                foundAddr1 = true;
                continue;
            }
            if (currentAddr.equals(addr2)) {
                foundAddr2 = true;
                continue;
            }
            if (currentAddr.equals(addr3)) {
                foundAddr3 = true;
                continue;
            }
        }
        Assert.assertTrue(foundAddr1 & foundAddr2 & foundAddr3);
        
    }
    
    // test delete, email addresses left intact, email loses person ref...
    public void testDeletePersonWithEmailAddress() throws HibernateException {
        Person person = new PersonImpl();
        person.setMainName("PersonTest5");
        person.setGivenName("PersonTestus5");
        
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("persontest5@test.com");
        
        addr.setPerson(person);
        person.addEmailAddress(addr);
        
        Session session = null;
        Transaction tx = null;
        
        // first create person and addr in a rel
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
        
        // now, delete the person
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            addr.setPerson(null);
            session.update(addr);
            session.delete(person);
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
        
        // when we look in the db, the person ref should be gone, the email addr should have no person ref left
        try {
            session = sessionFactory.openSession();
            List personResults = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest5'").list();
            Assert.assertTrue(personResults.isEmpty());
            
            List addrResults = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'persontest5@test.com'").list();
            EmailAddress loadedAddr = (EmailAddress) addrResults.get(0);
            Assert.assertEquals(addr, loadedAddr);
            Assert.assertNull(loadedAddr.getPerson()); // no person ref in addr
            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
     
    }
    
    // put different person into the email address, check that original person has lost ref to email addr
    public void testSwapPersonInEmailAddr() throws HibernateException {
        Person person1 = new PersonImpl();
        person1.setMainName("PersonTest6");
        
        Person person2 = new PersonImpl();
        person2.setMainName("PersonTest7");
        
        EmailAddress addr = new EmailAddressImpl();
        addr.setAddress("persontest67@test.com");
        addr.setPerson(person1);
        person1.addEmailAddress(addr);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person1);
            session.save(person2);
            session.save(addr);
            tx.commit();
        }
        catch (HibernateException he){
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        finally {
            session.close();
        }
        
        // now set addr's person to person2
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
        
        // load up copies of person1, person2 and addr and see everything's ok
        try {
            session = sessionFactory.openSession();
            List addrResults = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailAddressImpl as emailAddr " +
			" where emailAddr.address = 'persontest67@test.com'").list();
            EmailAddress loadedAddr = (EmailAddress) addrResults.get(0);
            Assert.assertEquals(addr, loadedAddr);
            Assert.assertEquals(person2, addr.getPerson());
            
            List personResults1 = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest6'").list();
            Person loadedPerson1 = (Person) personResults1.get(0);
            Assert.assertEquals(0, loadedPerson1.getEmailAddresses().size());
            
            List personResults2 = session.createQuery("from org.authorsite.mailarchive.model.impl.PersonImpl as p " +
			" where p.mainName = 'PersonTest7'").list();
            Person loadedPerson2 = (Person) personResults2.get(0);
            Assert.assertEquals(1, loadedPerson2.getEmailAddresses().size());
            Set loadedAddresses = loadedPerson2.getEmailAddresses();
            boolean foundAddr = false;
            Iterator loadedAddressesIt = loadedAddresses.iterator();
            while (loadedAddressesIt.hasNext()) {
                EmailAddress currentAddr = (EmailAddress) loadedAddressesIt.next();
                if (currentAddr.equals(addr)) {
                    foundAddr = true;
                }
            }
            Assert.assertTrue(foundAddr);
            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    
    
}
