/*
 * MessageEmailAddressMappingPersistenceTest.java, created on 07-Sep-2004 at 14:41:10
 * 
 * Copyright John King, 2004.
 *
 *  MessageEmailAddressMappingPersistenceTest.java is part of authorsite.org's MailArchive program.
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
 * @version $Revision: 1.4 $
 */
public class MessageEmailAddressMappingPersistenceTest extends AbstractPersistenceTestCase {

    /**
     * @param name
     * @throws Exception
     */
    public MessageEmailAddressMappingPersistenceTest(String name) throws Exception {
        super(name);
    }
    
    public void testMessageEmailAddressCreate() throws HibernateException {
        EmailAddress address1 = new EmailAddressImpl();
        address1.setAddress("messageEmailAddressTest1@test.com");
        
        EmailMessage mesg1 = new EmailMessageImpl();
        mesg1.setMsgID("messageEmailAddressTest1");
        mesg1.setSentDate(new Date());
        
        MessageEmailAddress mea1 = new MessageEmailAddressImpl();
        mea1.setEmailAddress(address1);
        mea1.setRole(EmailAddressRole.FROM);
        
        mesg1.addMessageEmailAddress(mea1);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(address1);
            session.save(mesg1);
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
        
        // load it up
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'messageEmailAddressTest1'").list();
            EmailMessage loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(mesg1, loadedMsg);
            Set meas = loadedMsg.getMessageEmailAddresses();
            Assert.assertEquals(1, meas.size());
            Iterator measIt = meas.iterator();
            boolean foundMea = false;
            while (measIt.hasNext()) {
                MessageEmailAddress loadedMea = (MessageEmailAddress) measIt.next();
                if (loadedMea.equals(mea1)) {
                    foundMea = true;
                    Assert.assertEquals(address1, loadedMea.getEmailAddress());
                }
            }
            Assert.assertTrue(foundMea);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    /**
     * Tests creating email with from, multiple to, cc, bcc
     */
    public void testMessageEmailAddressCreateMultiple() throws HibernateException {
        EmailAddress fromAddr = new EmailAddressImpl();
        fromAddr.setAddress("from1@messageEmailAddressTest2.com");
        MessageEmailAddress fromMea = new MessageEmailAddressImpl();
        fromMea.setEmailAddress(fromAddr);
        fromMea.setRole(EmailAddressRole.FROM);
        
        EmailAddress toAddr1 = new EmailAddressImpl();
        toAddr1.setAddress("to1@messageEmailAddressTest2.com");
        MessageEmailAddress toMea1 = new MessageEmailAddressImpl();
        toMea1.setEmailAddress(toAddr1);
        toMea1.setRole(EmailAddressRole.TO);
        
        EmailAddress toAddr2 = new EmailAddressImpl();
        toAddr2.setAddress("to2@messageEmailAddressTest2.com");
        MessageEmailAddress toMea2 = new MessageEmailAddressImpl();
        toMea2.setEmailAddress(toAddr2);
        toMea2.setRole(EmailAddressRole.TO);
        
        EmailAddress ccAddr1 = new EmailAddressImpl();
        ccAddr1.setAddress("cc1@messageEmailAddressTest2.com");
        MessageEmailAddress ccMea1 = new MessageEmailAddressImpl();
        ccMea1.setEmailAddress(ccAddr1);
        ccMea1.setRole(EmailAddressRole.CC);
        
        EmailAddress bccAddr1 = new EmailAddressImpl();
        bccAddr1.setAddress("bcc1@messageEmailAddressTest2.com");
        MessageEmailAddress bccMea1 = new MessageEmailAddressImpl();
        bccMea1.setEmailAddress(bccAddr1);
        bccMea1.setRole(EmailAddressRole.BCC);
        
        EmailMessage msg1 = new EmailMessageImpl();
        msg1.setMsgID("messageEmailAddressTest2");
        msg1.addMessageEmailAddress(fromMea);
        msg1.addMessageEmailAddress(toMea1);
        msg1.addMessageEmailAddress(toMea2);
        msg1.addMessageEmailAddress(ccMea1);
        msg1.addMessageEmailAddress(bccMea1);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(fromAddr);
            session.save(toAddr1);
            session.save(toAddr2);
            session.save(ccAddr1);
            session.save(bccAddr1);
            session.save(msg1);
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
        
        // now try and find it all again ok
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'messageEmailAddressTest2'").list();
            EmailMessage loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg1, loadedMsg);
            Set meas = loadedMsg.getMessageEmailAddresses();
            Assert.assertEquals(5, meas.size());
            Iterator measIt = meas.iterator();
            boolean foundFromMea = false;
            boolean foundToMea1 = false;
            boolean foundToMea2 = false;
            boolean foundCcMea1 = false;
            boolean foundBccMea1 = false;
            while (measIt.hasNext()) {
                MessageEmailAddress loadedMea = (MessageEmailAddress) measIt.next();
                if (loadedMea.equals(fromMea)) {
                    foundFromMea = true;
                    Assert.assertEquals(fromAddr, loadedMea.getEmailAddress());
                    continue;
                }
                if (loadedMea.equals(toMea1)) {
                    foundToMea1 = true;
                    Assert.assertEquals(toAddr1, loadedMea.getEmailAddress());
                    continue;
                }
                if (loadedMea.equals(toMea2)) {
                    foundToMea2 = true;
                    Assert.assertEquals(toAddr2, loadedMea.getEmailAddress());
                    continue;
                }
                if (loadedMea.equals(ccMea1)) {
                    foundCcMea1 = true;
                    Assert.assertEquals(ccAddr1, loadedMea.getEmailAddress());
                    continue;
                }
                if (loadedMea.equals(bccMea1)) {
                    foundBccMea1 = true;
                    Assert.assertEquals(bccAddr1, loadedMea.getEmailAddress());
                    continue;
                }
            }
            Assert.assertTrue(foundFromMea & foundToMea1 & foundToMea2 & foundCcMea1 & foundBccMea1);
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // update - add a MEA
    public void testUpdateAddMea() throws HibernateException {
        EmailAddress fromAddr = new EmailAddressImpl();
        fromAddr.setAddress("from1@messageEmailAddressTest3.com");
        MessageEmailAddress fromMea = new MessageEmailAddressImpl();
        fromMea.setEmailAddress(fromAddr);
        fromMea.setRole(EmailAddressRole.FROM);
        
        EmailAddress toAddr1 = new EmailAddressImpl();
        toAddr1.setAddress("to1@messageEmailAddressTest3.com");
        MessageEmailAddress toMea1 = new MessageEmailAddressImpl();
        toMea1.setEmailAddress(toAddr1);
        toMea1.setRole(EmailAddressRole.TO);
        
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("messageEmailAddressTest3");
        msg.addMessageEmailAddress(fromMea);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(fromAddr);
            session.save(toAddr1);
            session.save(msg);
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
        
       msg.addMessageEmailAddress(toMea1);
       
       // do the update
       try {
           session = sessionFactory.openSession();
           tx = session.beginTransaction();
           session.update(msg);
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
       
       // load it up and see if we can find both addresses attached to the msg
       try {
           session = sessionFactory.openSession();
           List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'messageEmailAddressTest3'").list();
           EmailMessage loadedMsg = (EmailMessage) results.get(0);
           Assert.assertEquals(msg, loadedMsg);
           Set meas = loadedMsg.getMessageEmailAddresses();
           Assert.assertEquals(2, meas.size());
           Iterator measIt = meas.iterator();
           boolean foundFromMea = false;
           boolean foundToMea1 = false;
           while (measIt.hasNext()) {
               MessageEmailAddress loadedMea = (MessageEmailAddress) measIt.next();
               if (loadedMea.equals(fromMea)) {
                   foundFromMea = true;
                   Assert.assertEquals(fromAddr, loadedMea.getEmailAddress());
                   continue;
               }
               if (loadedMea.equals(toMea1)) {
                   foundToMea1 = true;
                   Assert.assertEquals(toAddr1, loadedMea.getEmailAddress());
                   continue;
               }
           }
           Assert.assertTrue(foundFromMea & foundToMea1);
       }
       catch (HibernateException he) {
           throw he;
       }
       finally {
           session.close();
       }
    }
    
    // update - remove MEA
    public void testUpdateRemoveMea() throws HibernateException {
        EmailAddress fromAddr = new EmailAddressImpl();
        fromAddr.setAddress("from1@messageEmailAddressTest4.com");
        MessageEmailAddress fromMea = new MessageEmailAddressImpl();
        fromMea.setEmailAddress(fromAddr);
        fromMea.setRole(EmailAddressRole.FROM);
        
        EmailAddress toAddr1 = new EmailAddressImpl();
        toAddr1.setAddress("to1@messageEmailAddressTest4.com");
        MessageEmailAddress toMea1 = new MessageEmailAddressImpl();
        toMea1.setEmailAddress(toAddr1);
        toMea1.setRole(EmailAddressRole.TO);
        
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("messageEmailAddressTest4");
        msg.addMessageEmailAddress(fromMea);
        msg.addMessageEmailAddress(toMea1);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(fromAddr);
            session.save(toAddr1);
            session.save(msg);
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
               
       msg.removeMessageEmailAddress(toMea1);
        
       // do the update
       try {
           session = sessionFactory.openSession();
           tx = session.beginTransaction();
           session.update(msg);
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
       
       // load it up and see if we can find both addresses attached to the msg
       try {
           session = sessionFactory.openSession();
           List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'messageEmailAddressTest4'").list();
           EmailMessage loadedMsg = (EmailMessage) results.get(0);
           Assert.assertEquals(msg, loadedMsg);
           Set meas = loadedMsg.getMessageEmailAddresses();
           Assert.assertEquals(1, meas.size());
           Iterator measIt = meas.iterator();
           boolean foundFromMea = false;
           boolean foundToMea1 = false;
           while (measIt.hasNext()) {
               MessageEmailAddress loadedMea = (MessageEmailAddress) measIt.next();
               if (loadedMea.equals(fromMea)) {
                   foundFromMea = true;
                   Assert.assertEquals(fromAddr, loadedMea.getEmailAddress());
                   continue;
               }
               if (loadedMea.equals(toMea1)) {
                   foundToMea1 = true;
                   Assert.assertEquals(toAddr1, loadedMea.getEmailAddress());
                   continue;
               }
           }
           Assert.assertTrue(foundFromMea & !foundToMea1);
       }
       catch (HibernateException he) {
           throw he;
       }
       finally {
           session.close();
       }
        
    }
    
    // delete an EmailMessage, EmailAddresses should be left intact
    public void testDeleteEmailMessage() throws HibernateException {
        EmailAddress fromAddr = new EmailAddressImpl();
        fromAddr.setAddress("from1@messageEmailAddressTest5.com");
        MessageEmailAddress fromMea = new MessageEmailAddressImpl();
        fromMea.setEmailAddress(fromAddr);
        fromMea.setRole(EmailAddressRole.FROM);
        
        EmailAddress toAddr1 = new EmailAddressImpl();
        toAddr1.setAddress("to1@messageEmailAddressTest5.com");
        MessageEmailAddress toMea1 = new MessageEmailAddressImpl();
        toMea1.setEmailAddress(toAddr1);
        toMea1.setRole(EmailAddressRole.TO);
        
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("messageEmailAddressTest5");
        msg.addMessageEmailAddress(fromMea);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(fromAddr);
            session.save(toAddr1);
            session.save(msg);
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
        
              
       // do the delete
       try {
           session = sessionFactory.openSession();
           tx = session.beginTransaction();
           session.delete(msg);
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
       
       // attempt to load up the email message, shouldn't be there
       try {
           session = sessionFactory.openSession();
           List msgResults = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'messageEmailAddressTest5'").list();
           Assert.assertTrue(msgResults.isEmpty());
           
           // check the addresses are still there
           EmailAddress loadedFromAddr = (EmailAddress) session.load(EmailAddressImpl.class, ((Identifiable)fromAddr).getID());
           EmailAddress loadedToAddr = (EmailAddress) session.load(EmailAddressImpl.class, ((Identifiable)toAddr1).getID());
           
           Assert.assertEquals(fromAddr, loadedFromAddr);
           Assert.assertEquals(toAddr1, loadedToAddr);
       }
       catch (HibernateException he) {
           throw he;
       }
       finally {
           session.close();
       }    
       
       
      
    }

    
}
