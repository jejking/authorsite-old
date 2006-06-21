/*
 * EmailAddressLocatorTest.java, created on 30-Oct-2004 at 18:04:50
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressLocatorTest.java is part of authorsite.org's MailArchive program.
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
public class EmailAddressLocatorTest extends AbstractPersistenceTestCase {

    private EmailAddressDAO addressDAO;

    /**
     * @param name
     * @throws Exception
     */
    public EmailAddressLocatorTest(String name) throws Exception {
        super(name);
        addressDAO = new HibernateEmailAddressDAO(sessionFactory);
    }
    
    public void testGetEmailAddress() throws Exception {
        EmailAddress original = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoaderFind4@test.com'");
            original = (EmailAddress) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        EmailAddress fromDAO = addressDAO.getEmailAddress("emailAddressDataLoaderFind4@test.com");
        
        Assert.assertEquals(original, fromDAO);
    }
    
    public void testGetEmailAddressesByPerson() throws Exception {
        // find the addresses belonging to addr6Person
        Person addr6Person = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from PersonImpl as person where person.mainName = 'EmailAddressDataLoaderTestPerson'");
            addr6Person = (Person) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        List addresses = addressDAO.getEmailAddressesByPerson(addr6Person);
        Assert.assertEquals(2, addresses.size());
        boolean foundA = false;
        boolean foundB = false;
        Iterator it = addresses.iterator();
        while (it.hasNext()) {
            EmailAddress addr = (EmailAddress) it.next();
            if (addr.getAddress().equals("emailAddressDataLoaderFind6a@test.com")) {
                foundA = true;
            }
            if (addr.getAddress().equals("emailAddressDataLoaderFind6b@test.com")) {
                foundB = true;
            }
        }
        Assert.assertTrue(foundA & foundB);
    }
    
    public void testGetEmailAddressesByPersonalName() throws Exception {
        // find ones belonging to "Test Four"
        List addresses = addressDAO.getEmailAddressesByPersonalName("EmailAddressDataLoaderTest Four");
        Assert.assertEquals(2, addresses.size());
        boolean found4 = false;
        boolean found7 = false;
        Iterator it = addresses.iterator();
        while (it.hasNext()) {
            EmailAddress addr = (EmailAddress) it.next();
            System.out.println(addr);
            if (addr.getAddress().equals("emailAddressDataLoaderFind4@test.com")) {
                found4 = true;
            }
            if (addr.getAddress().equals("emailAddressDataLoaderFind7@test.com")) {
                found7 = true;
            }
        }
        Assert.assertTrue(found4 & found7);
    }
    
    public void testGetEmailAddressesByPersonalNameLike() throws Exception {
        List addresses = addressDAO.getEmailAddressesByPersonalNameLike("EmailAddressDataLoaderTest%");
        Assert.assertEquals(3, addresses.size());
        Iterator it = addresses.iterator();
        while (it.hasNext()) {
            EmailAddress addr = (EmailAddress) it.next();
            Assert.assertTrue(addr.getPersonalName().startsWith("EmailAddressDataLoaderTest"));
        }
    }
    
    public void testGetEmailAddressesLike() throws Exception {
        List addresses = addressDAO.getEmailAddressesLike("emailAddressDataLoaderFind%@test.com");
        Iterator it = addresses.iterator();
        while (it.hasNext()) {
            EmailAddress addr = (EmailAddress) it.next();
            Assert.assertTrue(addr.getAddress().startsWith("emailAddressDataLoaderFind"));
        }
    }
    
    

}
