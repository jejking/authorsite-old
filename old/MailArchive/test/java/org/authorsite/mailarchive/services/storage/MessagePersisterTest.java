/*
 * MessagePersisterTest.java, created on 14-Sep-2004 at 16:54:19
 * 
 * Copyright John King, 2004.
 *
 *  MessagePersisterTest.java is part of authorsite.org's MailArchive program.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.authorsite.mailarchive.model.EmailMessage;
import org.authorsite.mailarchive.model.impl.AbstractPersistenceTestCase;
import org.authorsite.mailarchive.model.impl.EmailMessageImpl;
import org.authorsite.mailarchive.services.loader.EmailMessageBuilder;
import org.authorsite.mailarchive.services.storage.impl.HibernateEmailMessageDAO;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author jejking
 * @version $Revision: 1.6 $
 */
public class MessagePersisterTest extends AbstractPersistenceTestCase {

    private List emailMsgs;
    
    private EmailAddressDAO addressDAO;
    private EmailMessageDAO messageDAO;
    private EmailMessageBuilder builder;

    /**
     * @param name
     * @throws Exception
     */
    public MessagePersisterTest(String name) throws Exception {
        super(name);
        messageDAO = new HibernateEmailMessageDAO(sessionFactory);
    }
    
    public void testSaveEmailMessage() throws Exception {
        // trivial test. the main ones are in EmailMessagePersistenceTest
        EmailMessage mpt1 = new EmailMessageImpl("MessagePersisterTest1");
        mpt1.setSubject("message persister test 1");
        
        messageDAO.saveMessage(mpt1);
        
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterTest1'");
            EmailMessage foundMsg = (EmailMessage) query.uniqueResult();
            Assert.assertEquals(mpt1, foundMsg);
        }
        finally {
            session.close();
        }
    }
    
    public void testSaveEmailMessages() throws Exception {
        EmailMessage mpt2 = new EmailMessageImpl("MessagePersisterTest2");
        EmailMessage mpt3 = new EmailMessageImpl("MessagePersisterTest3");
        List msgs = new ArrayList();
        msgs.add(mpt2);
        msgs.add(mpt3);
        
        messageDAO.saveMessages(msgs);
        
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterTest2' or msg.msgID = 'MessagePersisterTest3'");
            List results = query.list();
            Assert.assertEquals(2, results.size());
            boolean found2 = false;
            boolean found3 = false;
            Iterator it = results.iterator();
            while (it.hasNext()) {
                EmailMessage msg = (EmailMessage) it.next();
                if (msg.equals(mpt2)) {
                    found2 = true;
                }
                if (msg.equals(mpt3)) {
                    found3 = true;
                }
            }
            Assert.assertTrue(found2 & found3);
        }
        finally {
            session.close();
        }
    }
        
    
    public void testDeleteEmailMessage() throws Exception { 
        // load up the EmailMessages like MessagePersisterDataLoader_
        Session session = null;
        EmailMessage msg1 = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterDataLoader1'");
            msg1 = (EmailMessage) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        messageDAO.deleteMessage(msg1);
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterDataLoader1'");
            List results = query.list();
            Assert.assertTrue(results.isEmpty());
        }
        finally {
            session.close();
        }
    }
    
    public void testDeleteEmailMessages() throws Exception { 
        Session session = null;
        
        EmailMessage msg2 = null;
        EmailMessage msg3 = null;
        
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterDataLoader2'");
            msg2 = (EmailMessage) query.uniqueResult();
            query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterDataLoader3'");
            msg3 = (EmailMessage) query.uniqueResult();
        }
        finally {
            session.close();
        }
        
        List msgsToDelete = new ArrayList();
        msgsToDelete.add(msg2);
        msgsToDelete.add(msg3);
        
        messageDAO.deleteMessages(msgsToDelete);
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from EmailMessageImpl as msg where msg.msgID = 'MessagePersisterDataLoader2' or msg.msgID = 'MessagePersisterDataLoader3'");
            List results = query.list();
            Assert.assertTrue(results.isEmpty());
        }
        finally {
            session.close();
        }
    }
}
