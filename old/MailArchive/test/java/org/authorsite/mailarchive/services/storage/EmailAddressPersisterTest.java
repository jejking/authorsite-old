/*
 * EmailAddressDAOTest.java, created on 28-Oct-2004 at 23:46:49
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressDAOTest.java is part of authorsite.org's MailArchive program.
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
 * Tests those parts of the DAO that do CRUD ops.
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class EmailAddressPersisterTest extends AbstractPersistenceTestCase {

    private EmailAddressDAO addressDAO;
    
    /**
     * @param name
     * @throws Exception
     */
    public EmailAddressPersisterTest(String name) throws Exception {
        super(name);
        EmailMessageDAO emailMessageDAO = new HibernateEmailMessageDAO(sessionFactory);
        addressDAO = new HibernateEmailAddressDAO(sessionFactory, emailMessageDAO);
    }
    
    public void testSaveEmailAddress() throws Exception {
        EmailAddress addr = new EmailAddressImpl("emailAddressPersisterTest1@test.com");
        addressDAO.saveEmailAddress(addr);
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressPersisterTest1@test.com'");
            Assert.assertEquals(addr, query.uniqueResult());
        }
        finally {
            session.close();
        }
    }
    
    public void testSaveEmailAddresses() throws Exception {
        EmailAddress addr2 = new EmailAddressImpl("emailAddressPersisterTest2@test.com");
        EmailAddress addr3 = new EmailAddressImpl("emailAddressPersisterTest3@test.com");
        List addrs = new ArrayList();
        addrs.add(addr2);
        addrs.add(addr3);
        
        addressDAO.saveEmailAddresses(addrs);
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressPersisterTest2@test.com' or addr.address = 'emailAddressPersisterTest3@test.com'");
            List results = query.list();
            Assert.assertEquals(2, results.size());
            boolean found2 = false;
            boolean found3 = false;
            Iterator it = results.iterator();
            while (it.hasNext()) {
                EmailAddress addr = (EmailAddress) it.next();
                if (addr.equals(addr2)) {
                    found2 = true;
                }
                if (addr.equals(addr3)) {
                    found3 = true;
                }
            }
            Assert.assertTrue(found2 & found3);
        }
        finally {
            session.close();
        }
        
    }
    
    public void testDeleteEmailAddress() throws Exception {
        Session session = null;
        EmailAddress addr1 = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoader1@test.com'");
            addr1 = (EmailAddress) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        addressDAO.deleteEmailAddress(addr1);
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoader1@test.com'");
            List results  = query.list();
            Assert.assertTrue(results.isEmpty());
        }
        finally {
            session.close();
        }
    }
    
    public void testDeleteEmailAddresses() throws Exception {
        Session session = null;
        EmailAddress addr2 = null;
        EmailAddress addr3 = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoader2@test.com'");
            addr2 = (EmailAddress) query.uniqueResult();
            query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoader3@test.com'");
            addr3 = (EmailAddress) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        List addrs = new ArrayList();
        addrs.add(addr2);
        addrs.add(addr3);
        
        addressDAO.deleteEmailAddresses(addrs);
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoader2@test.com' or addr.address='emailAddressDataLoader3@test.com'");
            List results  = query.list();
            Assert.assertTrue(results.isEmpty());
        }
        finally {
            session.close();
        }
    }
    
    public void testNoDeleteWhenAddressReferencedInEmail() throws Exception {
        Session session = null;
        EmailAddress addr8 = null;
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoaderFind8@test.com'");
            addr8 = (EmailAddress) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        try {
            addressDAO.deleteEmailAddress(addr8);
            Assert.fail("expected archive storage exception");
        }
        catch (AddressStillInUseException asiue) {
            Assert.assertTrue(true);
        }
    }
    
    public void testNoDeleteWhenAddressesReferencedInEmail() throws Exception {
        Session session = null;
        EmailAddress addr8 = null;
        EmailAddress addr9 = null;
        List list = new ArrayList();
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoaderFind8@test.com'");
            addr8 = (EmailAddress) query.uniqueResult();
            Query query2 = session.createQuery("from EmailAddressImpl as addr where addr.address = 'emailAddressDataLoaderFind9@test.com'");
            addr9 = (EmailAddress) query2.uniqueResult();
        }
        finally {
            session.close();
        }
        
        list.add(addr8);
        list.add(addr9);
        
        try {
            addressDAO.deleteEmailAddresses(list);
            Assert.fail("expected archive storage exception");
        }
        catch (AddressStillInUseException asiue) {
            Assert.assertTrue(true);
        }
    }
    
}
