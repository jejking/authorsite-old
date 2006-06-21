/*
 * MessageLocatorDataLoader.java, created on 07-Oct-2004 at 16:28:57
 * 
 * Copyright John King, 2004.
 *
 *  MessageLocatorDataLoader.java is part of authorsite.org's MailArchive program.
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

import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.model.impl.*;

import org.hibernate.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class MessageLocatorDataLoader implements HibernateDataLoader {

    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    
    private EmailAddress email1;
    private EmailAddress email2;
    private EmailAddress email3;
    private EmailAddress email4;
    
    private EmailMessage msg1;
    private EmailMessage msg2;
    private EmailMessage msg3;
    private EmailMessage msg4;
    private EmailMessage msg5;
    private EmailMessage msg6;
    
    private static Logger logger = Logger.getLogger(MessageLocatorDataLoader.class);
    
    
    /**
     * @see org.authorsite.mailarchive.services.storage.HibernateDataLoader#loadData(net.sf.hibernate.SessionFactory)
     */
    public void loadData(SessionFactory sessionFactory) throws HibernateException {
        Session session = null;
        Transaction tx = null;
        
        try {
            logger.debug("loading data for MessageLocatorTest");
            // in this case we'll load up our test data once...
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            
            // create some people
            person1= new PersonImpl();
            person1.setMainName("MessageLocatorTest1");
            person1.setGivenName("TFBP1");
            
            person2 = new PersonImpl();
            person2.setMainName("MessageLocatorTest2");
            person2.setGivenName("TFBP2");
            
            session.save(person1);
            session.save(person2);
            
            // relate them email addresses
            email1 = new EmailAddressImpl();
            email1.setAddress("tfbp1@message.locator.test.com");
            
            email2 = new EmailAddressImpl();
            email2.setAddress("tfbp2@message.locator.test.com");
            
            email1.setPerson(person1);
            email2.setPerson(person2);
            
            session.save(email1);
            session.save(email2);
            
            
            
            //      create some more people
            person3= new PersonImpl();
            person3.setMainName("MessageLocatorTest3");
            person3.setGivenName("TFBP3");
            
            person4 = new PersonImpl();
            person4.setMainName("MessageLocatorTest4");
            person4.setGivenName("TFBP4");
            
            session.save(person3);
            session.save(person4);
            
            // relate them email addresses
            email3 = new EmailAddressImpl();
            email3.setAddress("tfbp3@message.locator.test.com");
            
            email4 = new EmailAddressImpl();
            email4.setAddress("tfbp4@message.locator.test.com");
            
            email3.setPerson(person3);
            email4.setPerson(person4);
            
            session.save(email3);
            session.save(email4);
            
            // create some messages that use those email addresses
            
            msg1 = new EmailMessageImpl();
            msg1.setSubject("testFindByPerson 1");
            TextContent msg1Content = new TextContentImpl();
            msg1Content.setContent("test find by person 1 content");
            msg1Content.setRole(TextContentRole.BODY_PART);
            msg1.addTextPart(msg1Content);
            MessageEmailAddress fromPerson1 = new MessageEmailAddressImpl();
            fromPerson1.setEmailAddress(email1);
            fromPerson1.setRole(EmailAddressRole.FROM);
            msg1.addMessageEmailAddress(fromPerson1);
            MessageEmailAddress toPerson1  = new MessageEmailAddressImpl();
            toPerson1.setEmailAddress(email2);
            toPerson1.setRole(EmailAddressRole.TO);
            msg1.addMessageEmailAddress(toPerson1);
            msg1.setMsgID("MessageLocatorTest1");
            Calendar cal1 = new GregorianCalendar();
            cal1.set(2004, Calendar.OCTOBER, 2);
            msg1.setReceivedDate(cal1.getTime());
            
            session.save(msg1);
            
            msg2 = new EmailMessageImpl();
            msg2.setSubject("testFindByPerson 2");
            TextContent msg2Content = new TextContentImpl();
            msg2Content.setContent("test find by person 2 content");
            msg2Content.setRole(TextContentRole.BODY_PART);
            msg2.addTextPart(msg2Content);
            MessageEmailAddress fromPerson2 = new MessageEmailAddressImpl();
            fromPerson2.setEmailAddress(email2);
            fromPerson2.setRole(EmailAddressRole.FROM);
            msg2.addMessageEmailAddress(fromPerson2);
            MessageEmailAddress toPerson2 = new MessageEmailAddressImpl();
            toPerson2.setEmailAddress(email1);
            toPerson2.setRole(EmailAddressRole.TO);
            msg2.addMessageEmailAddress(toPerson2);
            msg2.setMsgID("MessageLocatorTest2");
            Calendar cal2 = new GregorianCalendar();
            cal2.set(2004, Calendar.OCTOBER, 3); // one day after the first one
            msg2.setReceivedDate(cal2.getTime());
            
            session.save(msg2);
            
           
            
            // create some messages that use those email addresses
            
            // msg 3 - from person1 to person 2, 2nd January
            msg3 = new EmailMessageImpl();
            msg3.setSubject("testFindByPerson 3");
            TextContent msg3Content = new TextContentImpl();
            msg3Content.setContent("test find by person 3 content");
            msg3Content.setRole(TextContentRole.BODY_PART);
            msg3.addTextPart(msg3Content);
            MessageEmailAddress fromPerson3 = new MessageEmailAddressImpl();
            fromPerson3.setEmailAddress(email3);
            fromPerson3.setRole(EmailAddressRole.FROM);
            msg3.addMessageEmailAddress(fromPerson3);
            MessageEmailAddress toPerson3  = new MessageEmailAddressImpl();
            toPerson3.setEmailAddress(email4);
            toPerson3.setRole(EmailAddressRole.TO);
            msg3.addMessageEmailAddress(toPerson3);
            msg3.setMsgID("MessageLocatorTest3");
            Calendar cal3 = new GregorianCalendar();
            cal3.set(2004, Calendar.JANUARY, 2);
            msg3.setReceivedDate(cal3.getTime());
            
            session.save(msg3);
            
            // msg 4 from person 4 to person 3,4th January
            msg4 = new EmailMessageImpl();
            msg4.setSubject("testFindByPerson 4");
            TextContent msg4Content = new TextContentImpl();
            msg4Content.setContent("test find by person 4 content");
            msg4Content.setRole(TextContentRole.BODY_PART);
            msg4.addTextPart(msg4Content);
            MessageEmailAddress fromPerson4 = new MessageEmailAddressImpl();
            fromPerson4.setEmailAddress(email4);
            fromPerson4.setRole(EmailAddressRole.FROM);
            msg4.addMessageEmailAddress(fromPerson4);
            MessageEmailAddress toPerson4 = new MessageEmailAddressImpl();
            toPerson4.setEmailAddress(email3);
            toPerson4.setRole(EmailAddressRole.TO);
            msg4.addMessageEmailAddress(toPerson4);
            msg4.setMsgID("MessageLocatorTest4");
            Calendar cal4 = new GregorianCalendar();
            cal4.set(2004, Calendar.JANUARY, 4);
            msg4.setReceivedDate(cal4.getTime());
            
            session.save(msg4);
            
            // msg 5 from person 4 to person 3, 4th Feb
            msg5 = new EmailMessageImpl();
            msg5.setSubject("testFindByPerson 5");
            TextContent msg5Content = new TextContentImpl();
            msg5Content.setContent("test find by person5 content");
            msg5Content.setRole(TextContentRole.BODY_PART);
            msg5.addTextPart(msg5Content);
            MessageEmailAddress fromPerson5 = new MessageEmailAddressImpl();
            fromPerson5.setEmailAddress(email4);
            fromPerson5.setRole(EmailAddressRole.FROM);
            msg5.addMessageEmailAddress(fromPerson5);
            MessageEmailAddress toPerson5 = new MessageEmailAddressImpl();
            toPerson5.setEmailAddress(email3);
            toPerson5.setRole(EmailAddressRole.TO);
            msg5.addMessageEmailAddress(toPerson5);
            msg5.setMsgID("MessageLocatorTest5");
            Calendar cal5 = new GregorianCalendar();
            cal5.set(2004, Calendar.FEBRUARY, 3);
            msg5.setReceivedDate(cal5.getTime());
            
            session.save(msg5);

            // msg 6 from person 3 to person 4, 5th March
            msg6 = new EmailMessageImpl();
            msg6.setSubject("testFindByPerson 6");
            TextContent msg6Content = new TextContentImpl();
            msg6Content.setContent("test find by person 6 content");
            msg6Content.setRole(TextContentRole.BODY_PART);
            msg6.addTextPart(msg6Content);
            MessageEmailAddress from6 = new MessageEmailAddressImpl();
            from6.setEmailAddress(email3);
            from6.setRole(EmailAddressRole.FROM);
            msg6.addMessageEmailAddress(from6);
            MessageEmailAddress to6 = new MessageEmailAddressImpl();
            to6.setEmailAddress(email4);
            to6.setRole(EmailAddressRole.TO);
            msg6.addMessageEmailAddress(to6);
            msg6.setMsgID("MessageLocatorTest6");
            Calendar cal6 = new GregorianCalendar();
            cal6.set(2004, Calendar.MARCH, 5);
            msg6.setReceivedDate(cal6.getTime());
            
            session.save(msg6);

            tx.commit();
        }
        catch (HibernateException e) {
            logger.debug("Caught hibernate exception, rolling back");
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
        finally {
            session.close();
        }
    
    }

 
}
