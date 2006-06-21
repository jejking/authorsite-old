/*
 * PersonDAOTest.java, created on 30-Oct-2004 at 21:52:38
 * 
 * Copyright John King, 2004.
 *
 *  PersonDAOTest.java is part of authorsite.org's MailArchive program.
 *
 *  MailArchive is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  MailArchive is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.services.storage;

import java.util.*;

import junit.framework.*;

import org.hibernate.*;

import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.model.impl.*;
import org.authorsite.mailarchive.services.storage.impl.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class PersonDAOTest extends AbstractPersistenceTestCase {

    private PersonDAO personDAO;
    
    /**
     * @param name
     * @throws Exception
     */
    public PersonDAOTest(String name) throws Exception {
        super(name);
        personDAO = new HibernatePersonDAO(sessionFactory);
    }
    
    public void testSavePerson() throws Exception {
        Person person = new PersonImpl("PersonDAOTest", "Person");
        personDAO.savePerson(person);  
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from PersonImpl as person where person.mainName = 'PersonDAOTest'");
            List results = query.list();
            Assert.assertEquals(1, results.size());
            Person found = (Person) results.get(0);
            Assert.assertEquals(person, found);
        }
        finally {
            session.close();
        }
                
    }
    
    public void testDeletePerson() throws Exception {
        Session session = null;
        Person person = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from PersonImpl as person where person.mainName = 'Delete' and person.givenName = 'Me' ");
            person = (Person) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        personDAO.deletePerson(person);
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from PersonImpl as person where person.mainName = 'Delete' and person.givenName = 'Me' ");
            List results = query.list();
            Assert.assertTrue(results.isEmpty());
        }
        finally {
            session.close();
        }
        
    }

    public void testGetByMainName() throws Exception {
        List results = personDAO.getByMainName("King");
        Assert.assertEquals(2, results.size());
        boolean foundKing = false;
        boolean foundJohnKing = false;
        Iterator it = results.iterator();
        while (it.hasNext()) {
            Person person = (Person) it.next();
            if (person.getMainName().equals("King") && person.getGivenName() == null) {
                foundKing = true;
            }
            else if (person.getMainName().equals("King") && person.getGivenName().equals("John")) {
                foundJohnKing = true;
            }
            
        }
        Assert.assertTrue(foundKing & foundJohnKing);
    }
    
    public void testGetByMainNameAndGiveName() throws Exception {
        List results = personDAO.getByMainNameAndGivenName("King", "John");
        Assert.assertEquals(1, results.size());
        Person person = (Person) results.get(0);
        Assert.assertEquals("King", person.getMainName());
        Assert.assertEquals("John", person.getGivenName());
    }
    
    public void testGetByMainNameLike() throws Exception {
        List results = personDAO.getByMainNameLike("Kin%");
        Assert.assertEquals(3, results.size());
        Iterator it = results.iterator();
        while (it.hasNext()) {
            Person person = (Person) it.next();
            Assert.assertTrue(person.getMainName().startsWith("Kin"));
        }
    }
    
    public void testGetByMainNameLikeAndGivenNameLike() throws Exception {
        List results = personDAO.getByMainNameLikeAndGivenNameLike("Kin%", "Jo%");
        Iterator it = results.iterator();
        while (it.hasNext()) {
            Person person = (Person) it.next();
            Assert.assertTrue(person.getMainName().startsWith("Kin"));
            Assert.assertTrue(person.getGivenName().startsWith("Jo"));
        }
    }
                                                              
}