/*
 * EmailMessagePersistenceTest.java, created on 11-Sep-2004 at 14:49:22
 * 
 * Copyright John King, 2004.
 *
 *  EmailMessagePersistenceTest.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.model.impl;

import java.util.*;

import junit.framework.*;

import org.authorsite.mailarchive.model.*;

import org.hibernate.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class EmailMessagePersistenceTest extends AbstractPersistenceTestCase {

    /**
     * @param name
     * @throws Exception
     */
    public EmailMessagePersistenceTest(String name) throws Exception {
        super(name);
    }
    
    // test create with all basic properties
    public void testCreateWithAllProps() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setImportance("very");
        msg.setInReplyTo("aMessage");
        msg.setMsgID("EmailMessagePersistenceTest1");
        msg.setMsgReferences("anotherMessage");
        Date received = new GregorianCalendar(2004, Calendar.SEPTEMBER, 11, 21, 30).getTime();
        msg.setReceivedDate(received);
        Date sent = new GregorianCalendar(2004, Calendar.SEPTEMBER, 11, 21, 28).getTime();
        msg.setSentDate(sent);
        msg.setSensitivity("highly confidential");
        msg.setSubject("test1");
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        // load the message object up
        EmailMessage loadedMsg = null;
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest1'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            Assert.assertEquals("very", loadedMsg.getImportance());
            Assert.assertEquals("aMessage", loadedMsg.getInReplyTo());
            Assert.assertEquals("anotherMessage", loadedMsg.getMsgReferences());
            Assert.assertEquals(received, loadedMsg.getReceivedDate());
            Assert.assertEquals(sent, loadedMsg.getSentDate());
            Assert.assertEquals("highly confidential", loadedMsg.getSensitivity());
            Assert.assertEquals("test1", msg.getSubject());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
        
    }
    
    // test create with null msgID - shouldn't work
    public void testCreateNullMsgID() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID(null);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(msg);
            tx.commit(); // shouldn't happen!
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            if (he instanceof org.hibernate.PropertyValueException) {
                Assert.assertTrue(true);
            }
            else {
                throw he;
            }
        }
        finally {
            session.close();
        }
    }
    
    // test create with duplicate msgID
    public void testCreateDuplicateMsgID() throws HibernateException {
        EmailMessage msg1 = new EmailMessageImpl();
        msg1.setMsgID("EmailMessagePersistenceTest3");
        
        EmailMessage msg2 = new EmailMessageImpl();
        msg2.setMsgID("EmailMessagePersistenceTest3"); // identical! - should fail on save
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(msg1);
            session.save(msg2);
            tx.commit();
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            if (he instanceof org.hibernate.JDBCException) { // sql insert will fail because of unique index
                Assert.assertTrue(true);
            }
            else {
                throw he;
            }
        }
        finally {
            session.close();
        }
    }
    
    
    // test create with text content
    public void testCreateWithTextContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest4");
        
        TextContent txt = new TextContentImpl();
        txt.setContent("some text");
        txt.setRole(TextContentRole.MSG_BODY);
        
        msg.addTextPart(txt);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            // session.save(txt);
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
        
        // try and load up
        EmailMessage loadedMsg = null;
        boolean foundText = false;
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest4'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set textSet = loadedMsg.getTextParts();
            Assert.assertEquals(1, textSet.size());
            Iterator textSetIt = textSet.iterator();
            while (textSetIt.hasNext()) {
                TextContent content = (TextContent) textSetIt.next();
                if (content.equals(txt)) {
                    foundText = true;
                }
            }
            Assert.assertTrue(foundText);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test create with binary content
    public void testCreateWithBinaryContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest5");
        
        BinaryContent bin1 = new BinaryContentImpl();
        bin1.setContent(new byte[] {1, 2, 3, 4});
        
        BinaryContent bin2 = new BinaryContentImpl();
        bin2.setContent(new byte[] {4, 3, 2, 1});
        
        msg.addBinaryPart(bin1);
        msg.addBinaryPart(bin2);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        EmailMessage loadedMsg = null;
        boolean foundBin1 = false;
        boolean foundBin2 = false;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest5'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set binSet = loadedMsg.getBinaryParts();
            Assert.assertEquals(2, binSet.size());
            Iterator binSetIt = binSet.iterator();
            while (binSetIt.hasNext()) {
                BinaryContent content = (BinaryContent) binSetIt.next();
                if (content.equals(bin1)) {
                    foundBin1= true;
                    continue;
                }
                if (content.equals(bin2)) {
                    foundBin2 = true;
                    continue;
                }
            }
            Assert.assertTrue(foundBin1 & foundBin2);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test create binary and text content together
    public void testCreateBinaryAndText() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest6");
        
        BinaryContent bin1 = new BinaryContentImpl();
        bin1.setContent(new byte[] {1, 2, 3, 4});
        
        BinaryContent bin2 = new BinaryContentImpl();
        bin2.setContent(new byte[] {4, 3, 2, 1});
        
        TextContent txt1 = new TextContentImpl();
        txt1.setContent("some text");
        txt1.setRole(TextContentRole.BODY_PART);
        
        TextContent txt2 = new TextContentImpl();
        txt2.setContent("some more text");
        txt2.setRole(TextContentRole.MSG_BODY);
        
        msg.addBinaryPart(bin1);
        msg.addBinaryPart(bin2);
        
        msg.addTextPart(txt1);
        msg.addTextPart(txt2);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        EmailMessage loadedMsg = null;
        boolean foundBin1 = false;
        boolean foundBin2 = false;
        boolean foundTxt1 = false;
        boolean foundTxt2 = false;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest6'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set binSet = loadedMsg.getBinaryParts();
            Assert.assertEquals(2, binSet.size());
            Iterator binSetIt = binSet.iterator();
            while (binSetIt.hasNext()) {
                BinaryContent content = (BinaryContent) binSetIt.next();
                if (content.equals(bin1)) {
                    foundBin1= true;
                    continue;
                }
                if (content.equals(bin2)) {
                    foundBin2 = true;
                    continue;
                }
            }
            
            Set textSet = loadedMsg.getTextParts();
            Assert.assertEquals(2, textSet.size());
            Iterator textSetIt = textSet.iterator();
            while (textSetIt.hasNext()) {
                TextContent content = (TextContent) textSetIt.next();
                if (content.equals(txt1)) {
                    foundTxt1 = true;
                    continue;
                }
                if (content.equals(txt2)) {
                    foundTxt2 = true;
                    continue;
                }
            }
            
            Assert.assertTrue(foundBin1 & foundBin2 & foundTxt1 & foundTxt2);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test update add text content
    public void testUpdateAddText() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest7");
          
        TextContent txt1 = new TextContentImpl();
        txt1.setContent("some text");
        txt1.setRole(TextContentRole.BODY_PART);
        
        TextContent txt2 = new TextContentImpl();
        txt2.setContent("some more text");
        txt2.setRole(TextContentRole.MSG_BODY);
        
        msg.addTextPart(txt1);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        msg.addTextPart(txt2);
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
        
        EmailMessage loadedMsg = null;
        boolean foundTxt1 = false;
        boolean foundTxt2 = false;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest7'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set textSet = loadedMsg.getTextParts();
            Assert.assertEquals(2, textSet.size());
            Iterator textSetIt = textSet.iterator();
            while (textSetIt.hasNext()) {
                TextContent content = (TextContent) textSetIt.next();
                if (content.equals(txt1)) {
                    foundTxt1 = true;
                    continue;
                }
                if (content.equals(txt2)) {
                    foundTxt2 = true;
                    continue;
                }
            }
            
            Assert.assertTrue(foundTxt1 & foundTxt2);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test update remove text content
    public void testUpdateRemoveTextContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest8");
          
        TextContent txt1 = new TextContentImpl();
        txt1.setContent("some text");
        txt1.setRole(TextContentRole.BODY_PART);
        
        TextContent txt2 = new TextContentImpl();
        txt2.setContent("EmailPersistenceTest8 - to be deleted");
        txt2.setRole(TextContentRole.MSG_BODY);
        
        msg.addTextPart(txt1);
        msg.addTextPart(txt2);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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

        
        
        try {
            session = sessionFactory.openSession();
            session.lock(msg, LockMode.NONE);
            msg.removeTextContent(txt2);
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
        
        EmailMessage loadedMsg = null;
        boolean foundTxt1 = false;
        boolean foundTxt2 = false;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest8'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set textSet = loadedMsg.getTextParts();
            Assert.assertEquals(1, textSet.size());
            Iterator textSetIt = textSet.iterator();
            while (textSetIt.hasNext()) {
                TextContent content = (TextContent) textSetIt.next();
                if (content.equals(txt1)) {
                    foundTxt1 = true;
                    continue;
                }
                if (content.equals(txt2)) {
                    foundTxt2 = true;
                    continue;
                }
            }
            
            Assert.assertTrue(foundTxt1 & !foundTxt2);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test update add binary content
    public void testUpdateAddBinaryContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest9");
        
        BinaryContent bin1 = new BinaryContentImpl();
        bin1.setContent(new byte[] {1, 2, 3, 4});
        
        BinaryContent bin2 = new BinaryContentImpl();
        bin2.setContent(new byte[] {4, 3, 2, 1});
        
        msg.addBinaryPart(bin1);
                
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        msg.addBinaryPart(bin2);
        
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
        
        
        EmailMessage loadedMsg = null;
        boolean foundBin1 = false;
        boolean foundBin2 = false;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest9'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set binSet = loadedMsg.getBinaryParts();
            Assert.assertEquals(2, binSet.size());
            Iterator binSetIt = binSet.iterator();
            while (binSetIt.hasNext()) {
                BinaryContent content = (BinaryContent) binSetIt.next();
                if (content.equals(bin1)) {
                    foundBin1= true;
                    continue;
                }
                if (content.equals(bin2)) {
                    foundBin2 = true;
                    continue;
                }
            }
            Assert.assertTrue(foundBin1 & foundBin2);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test update remove binary content
    public void testUpdateRemoveBinaryContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest10");
        
        BinaryContent bin1 = new BinaryContentImpl();
        bin1.setContent(new byte[] {1, 2, 3, 4});
        
        BinaryContent bin2 = new BinaryContentImpl();
        bin2.setContent(new byte[] {4, 3, 2, 1});
        
        msg.addBinaryPart(bin1);
        msg.addBinaryPart(bin2);
                
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        msg.removeBinaryPart(bin2);
        
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
                
        EmailMessage loadedMsg = null;
        boolean foundBin1 = false;
        boolean foundBin2 = false;
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest10'").list();
            loadedMsg = (EmailMessage) results.get(0);
            Assert.assertEquals(msg, loadedMsg);
            
            Set binSet = loadedMsg.getBinaryParts();
            Assert.assertEquals(1, binSet.size());
            Iterator binSetIt = binSet.iterator();
            while (binSetIt.hasNext()) {
                BinaryContent content = (BinaryContent) binSetIt.next();
                if (content.equals(bin1)) {
                    foundBin1= true;
                    continue;
                }
                if (content.equals(bin2)) {
                    foundBin2 = true;
                    continue;
                }
            }
            Assert.assertTrue(foundBin1 & !foundBin2);            
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test delete with text content - text content
    public void testDeleteWithTextContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest11");
          
        TextContent txt1 = new TextContentImpl();
        txt1.setContent("some text");
        txt1.setRole(TextContentRole.BODY_PART);
        
        TextContent txt2 = new TextContentImpl();
        txt2.setContent("EmailPersistenceTest11 - to be deleted");
        txt2.setRole(TextContentRole.MSG_BODY);
        
        msg.addTextPart(txt1);
        msg.addTextPart(txt2);
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
        
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest11'").list();
            Assert.assertTrue(results.isEmpty());
        }
        catch (HibernateException he) {
            throw he;
        }
        finally {
            session.close();
        }
    }
    
    // test delete with binary content - binary content
    public  void testDeleteWithBinaryContent() throws HibernateException {
        EmailMessage msg = new EmailMessageImpl();
        msg.setMsgID("EmailMessagePersistenceTest12");
        
        BinaryContent bin1 = new BinaryContentImpl();
        bin1.setContent(new byte[] {1, 2, 3, 4});
        
        BinaryContent bin2 = new BinaryContentImpl();
        bin2.setContent(new byte[] {4, 3, 2, 1});
        
        msg.addBinaryPart(bin1);
        msg.addBinaryPart(bin2);
                
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
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
                
        try {
            session = sessionFactory.openSession();
            List results = session.createQuery("from org.authorsite.mailarchive.model.impl.EmailMessageImpl as emailMsg " +
			" where emailMsg.msgID = 'EmailMessagePersistenceTest12'").list();
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
