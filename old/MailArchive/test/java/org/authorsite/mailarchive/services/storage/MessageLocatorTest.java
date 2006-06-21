/*
 * MessageLocatorTest.java, created on 30-Sep-2004 at 15:54:16
 * 
 * Copyright John King, 2004.
 *
 *  MessageLocatorTest.java is part of authorsite.org's MailArchive program.
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

import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.model.impl.*;
import org.authorsite.mailarchive.services.storage.impl.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.6 $
 */
public class MessageLocatorTest extends AbstractPersistenceTestCase {

    private static Logger logger = Logger.getLogger(MessageLocatorTest.class);
    
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
    
    private EmailMessageDAO locator;
    
    /**
     * @param name
     * @throws Exception
     */
    public MessageLocatorTest(String name) throws Exception {
        super(name);
        logger.debug("Creating MessageLocatorTest with constructor");
        Session session = null;
        
        // load up the various things we'll be using from the database
        try {
             session = sessionFactory.openSession();
             Query getPersonQuery = session.createQuery("from PersonImpl person where person.givenName = :givenName and person.mainName = :mainName");
             person1  = (Person) getPersonQuery.setString("givenName", "TFBP1").setString("mainName", "MessageLocatorTest1").list().get(0);
             Assert.assertNotNull( ((Identifiable)person1).getID());
             person2  = (Person) getPersonQuery.setString("givenName", "TFBP2").setString("mainName", "MessageLocatorTest2").list().get(0);
             Assert.assertNotNull( ((Identifiable)person2).getID());
             person3  = (Person) getPersonQuery.setString("givenName", "TFBP3").setString("mainName", "MessageLocatorTest3").list().get(0);
             Assert.assertNotNull( ((Identifiable)person3).getID());
             person4  = (Person) getPersonQuery.setString("givenName", "TFBP4").setString("mainName", "MessageLocatorTest4").list().get(0);
             Assert.assertNotNull( ((Identifiable)person4).getID());
             
             Query getEmailQuery = session.createQuery("from EmailAddressImpl emailaddr where emailaddr.address = :address");
             email1 = (EmailAddress) getEmailQuery.setString("address", "tfbp1@message.locator.test.com").list().get(0);
             Assert.assertNotNull( ((Identifiable)email1).getID());
             email2 = (EmailAddress) getEmailQuery.setString("address", "tfbp2@message.locator.test.com").list().get(0);
             Assert.assertNotNull( ((Identifiable)email2).getID());
             email3 = (EmailAddress) getEmailQuery.setString("address", "tfbp3@message.locator.test.com").list().get(0);
             Assert.assertNotNull( ((Identifiable)email3).getID());
             email4 = (EmailAddress) getEmailQuery.setString("address", "tfbp4@message.locator.test.com").list().get(0);;
             Assert.assertNotNull( ((Identifiable)email4).getID());
             
             Query getEmailMessageQuery = session.createQuery("from EmailMessageImpl msg where msg.msgID = :msgid");
             msg1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "MessageLocatorTest1").list().get(0);
             Assert.assertNotNull( ((Identifiable)msg1).getID());
             msg2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "MessageLocatorTest2").list().get(0);
             Assert.assertNotNull( ((Identifiable)msg2).getID());
             msg3 = (EmailMessage) getEmailMessageQuery.setString("msgid", "MessageLocatorTest3").list().get(0);
             Assert.assertNotNull( ((Identifiable)msg3).getID());
             msg4 = (EmailMessage) getEmailMessageQuery.setString("msgid", "MessageLocatorTest4").list().get(0);
             Assert.assertNotNull( ((Identifiable)msg4).getID());
             msg5 = (EmailMessage) getEmailMessageQuery.setString("msgid", "MessageLocatorTest5").list().get(0);
             Assert.assertNotNull( ((Identifiable)msg5).getID());
             msg6 = (EmailMessage) getEmailMessageQuery.setString("msgid", "MessageLocatorTest6").list().get(0);
             Assert.assertNotNull( ((Identifiable)msg6).getID());
        }
        catch (HibernateException he) {
            logger.error(he);
            throw he;
        }
        finally {
            session.close();
        }
        
        locator = new HibernateEmailMessageDAO(sessionFactory);
    }

    public void testFindChronologicallyAsc() throws ArchiveStorageException {
        // we can't be sure that there won't be other messages already loaded from other tests
        // test the first one comes before the last one rather than looking for the specific ones loaded in the constructor

        List results = locator.getChronologically(new DateQualifier(), new ResultQualifier(), true);
        
        // need to do this because some of the messages inserted by some of the test cases don't have received dates
        for (int i = 1; i < results.size(); i++) { // start one in...
            EmailMessage msg = (EmailMessage) results.get(i);
            EmailMessage previousMsg = (EmailMessage) results.get(i  - 1);
            Date msgReceived = msg.getReceivedDate();
            Date previousMsgReceived = previousMsg.getReceivedDate();
            if (msgReceived != null && previousMsgReceived != null) { 
                Assert.assertTrue(msgReceived.after(previousMsgReceived));
            }
        }
	}
    
    public void testFindChronologicallyDesc() throws ArchiveStorageException {
        List results = locator.getChronologically(new DateQualifier(), new ResultQualifier(), false);
        
        for (int i = 1; i < results.size(); i++) { // start one in...
            EmailMessage msg = (EmailMessage) results.get(i);
            EmailMessage previousMsg = (EmailMessage) results.get(i  - 1);
            Date msgReceived = msg.getReceivedDate();
            Date previousMsgReceived = previousMsg.getReceivedDate();
            if (msgReceived != null && previousMsgReceived != null) { 
                Assert.assertTrue(msgReceived.before(previousMsgReceived));
            }
        }
    }
    
    public void testFindChronologicallyFromAsc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setFromDate(fromCal.getTime());
        
        List results = locator.getChronologically(dateQualifier, new ResultQualifier(), true);
        
        // last one returned, which will be the most recent, should be after the date in date qualifier
        EmailMessage msg = (EmailMessage) results.get(results.size() - 1);
        Assert.assertTrue(msg.getReceivedDate().after(dateQualifier.getFromDate()));
    }
    
    public void testFindChronologicallyFromDesc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setFromDate(fromCal.getTime());
        
        List results = locator.getChronologically(dateQualifier, new ResultQualifier(), true);
        
        // first one returned, whcih will be the latest, should be after the date in date qualifier
        EmailMessage msg = (EmailMessage) results.get(0);
        Assert.assertTrue(msg.getReceivedDate().after(dateQualifier.getFromDate()));
    }
    
    public void testFindChronologicallyToAsc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); 
        List results = locator.getChronologically(dateQualifier, new ResultQualifier(), true);
        
        // last message, which will be the latest received, should be before the toDate
        EmailMessage msg = (EmailMessage) results.get(results.size() - 1);
        Assert.assertTrue(msg.getReceivedDate().before(dateQualifier.getToDate()));
    }
    
    public void testFindChronlogicallyToDesc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); 
        List results = locator.getChronologically(dateQualifier, new ResultQualifier(), false);
        
        // first message, which will be the latest received, should be before the toDate
        EmailMessage msg = (EmailMessage) results.get(0);
        Assert.assertTrue(msg.getReceivedDate().before(dateQualifier.getToDate()));
    }
    
    public void testFindChronologicallyFromToAsc() throws ArchiveStorageException {
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 20);
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.JANUARY, 3); // should get msg 2 and msg 3
        DateQualifier dateQualifier = new DateQualifier(fromCal.getTime(), toCal.getTime());
        List results = locator.getChronologically(dateQualifier, new ResultQualifier(), true);
        
        // first message, which will be the earliest must be after from
        EmailMessage earliestMsg = (EmailMessage) results.get(0);
        Assert.assertTrue(earliestMsg.getReceivedDate().after(dateQualifier.getFromDate()));
        
        // last message, which will be the most recent, must be before to
        EmailMessage latestMsg = (EmailMessage) results.get(results.size() - 1);
        Assert.assertTrue(latestMsg.getReceivedDate().before(dateQualifier.getToDate()));
    }
    
    public void testFindChronologicallyFromToDesc() throws ArchiveStorageException {
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 20);
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.JANUARY, 3); // should get msg 2 and msg 3
        DateQualifier dateQualifier = new DateQualifier(fromCal.getTime(), toCal.getTime());
        List results = locator.getChronologically(dateQualifier, new ResultQualifier(), false);
        
        // first message, which will be the most recent must be before to
        EmailMessage earliestMsg = (EmailMessage) results.get(0);
        Assert.assertTrue(earliestMsg.getReceivedDate().before(dateQualifier.getToDate()));
        
        // last message, which will be the earliest, must be after from
        EmailMessage latestMsg = (EmailMessage) results.get(results.size() - 1);
        Assert.assertTrue(latestMsg.getReceivedDate().after(dateQualifier.getFromDate()));
    }
                                                                              
    
    public void testFindByPersonAsc() throws ArchiveStorageException {
        List list1 = locator.getByPerson(person1, new DateQualifier(), new ResultQualifier(), true);
        Assert.assertEquals(2, list1.size());
        Assert.assertEquals(msg1, list1.get(0));
        Assert.assertEquals(msg2, list1.get(1));
    }
    
    public void testFindByPersonDesc() throws ArchiveStorageException {
        // sort date descending
        List list2 = locator.getByPerson(person1, new DateQualifier(), new ResultQualifier(), false);
        Assert.assertEquals(2, list2.size());
        Assert.assertEquals(msg2, list2.get(0));
        Assert.assertEquals(msg1, list2.get(1));
    }
    
        
    public void testFindByPersonFromAsc() throws ArchiveStorageException {
        // test find by person from asc - find mail by person 1 from 1st Feb... should get msg 3 and msg 4 in that order
        DateQualifier dateQualifier = new DateQualifier();
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setFromDate(fromCal.getTime());
        List personFromAscList = locator.getByPerson(person3, dateQualifier, new ResultQualifier(), true);
        
        Assert.assertEquals(2, personFromAscList.size());
        Assert.assertEquals(msg5, personFromAscList.get(0));
        Assert.assertEquals(msg6, personFromAscList.get(1));
    }
    
    public void testFindByPersonFromDesc() throws ArchiveStorageException {
        //      test find by person from desc

        DateQualifier dateQualifier = new DateQualifier();
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setFromDate(fromCal.getTime());
        List personFromDescList = locator.getByPerson(person3, dateQualifier, new ResultQualifier(), false);
        
        Assert.assertEquals(2, personFromDescList.size());
        Assert.assertEquals(msg6, personFromDescList.get(0));
        Assert.assertEquals(msg5, personFromDescList.get(1));
    }
    
    public void testFindByPersonToAsc() throws ArchiveStorageException {

        // test find by person to asc
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); // should find msg 3 and msg 4
        List personToAscList = locator.getByPerson(person3, dateQualifier, new ResultQualifier(), true);
        Assert.assertEquals(2, personToAscList.size());
        Assert.assertEquals(msg3, personToAscList.get(0));
        Assert.assertEquals(msg4, personToAscList.get(1));
    }
    
    public void testFindByPersonToDesc() throws ArchiveStorageException {

        // test find by person to desc
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); // should find msg 1 and msg 2
        List personToDescList = locator.getByPerson(person3, dateQualifier, new ResultQualifier(), false);
        
        Assert.assertEquals(2, personToDescList.size());
        Assert.assertEquals(msg3, personToDescList.get(1));
        Assert.assertEquals(msg4, personToDescList.get(0));
    }
    
    public void testFindByPersonFromToAsc() throws ArchiveStorageException {

        //      test find by person from to asc
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 20);
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.JANUARY, 3); // should get msg 2 and msg 3
        DateQualifier dateQualifier = new DateQualifier(fromCal.getTime(), toCal.getTime());
        List personFromToAscList = locator.getByPerson(person4, dateQualifier, new ResultQualifier(), true);
        Assert.assertEquals(2, personFromToAscList.size());
        Assert.assertEquals(msg4, personFromToAscList.get(0));
        Assert.assertEquals(msg5, personFromToAscList.get(1));
    }
    
    public void testFindByPersonFromToDesc() throws ArchiveStorageException {

        // test find by person from to desc
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 20);
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.JANUARY, 3); // should get msg 2 and msg 3
        DateQualifier dateQualifier = new DateQualifier(fromCal.getTime(), toCal.getTime());
        List personFromToDescList = locator.getByPerson(person4, dateQualifier, new ResultQualifier(), false);
        Assert.assertEquals(2, personFromToDescList.size());
        Assert.assertEquals(msg4, personFromToDescList.get(1));
        Assert.assertEquals(msg5, personFromToDescList.get(0));
    }
            
    public void testFindByEmailAddressAsc() throws ArchiveStorageException {
        List list = locator.getByEmailAddress(email1, new DateQualifier(), new ResultQualifier(), true);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(msg1, list.get(0));
        Assert.assertEquals(msg2, list.get(1));
    }
    
    public void testFindByEmailAddressDesc() throws ArchiveStorageException {
        List list2 = locator.getByEmailAddress(email1, new DateQualifier(), new ResultQualifier(), false);
        Assert.assertEquals(2, list2.size());
        Assert.assertEquals(msg2, list2.get(0));
        Assert.assertEquals(msg1, list2.get(1));
    }
    
    public void testFindByEmailAddressFromAsc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setFromDate(fromCal.getTime());
        List results = locator.getByEmailAddress(email3, dateQualifier, new ResultQualifier(), true);
        
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(msg5, results.get(0));
        Assert.assertEquals(msg6, results.get(1));
    }
    
    public void testFindByEmailAddressFromDesc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setFromDate(fromCal.getTime());
        List results = locator.getByEmailAddress(email3, dateQualifier, new ResultQualifier(), false);
        
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(msg6, results.get(0));
        Assert.assertEquals(msg5, results.get(1));
    }
    
    public void testFindByEmailAddressToAsc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); // should find msg 3 and msg 4
        List results = locator.getByEmailAddress(email3, dateQualifier, new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(msg3, results.get(0));
        Assert.assertEquals(msg4, results.get(1));
    }
    
    public void testFindByEmailAddressToDesc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); // should find msg 1 and msg 2
        List results = locator.getByEmailAddress(email3, dateQualifier, new ResultQualifier(), false);
        
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(msg3, results.get(1));
        Assert.assertEquals(msg4, results.get(0));
  
    }
    
    public void testFindByEmailAddressFromToAsc() throws ArchiveStorageException {
        DateQualifier dateQualifier = new DateQualifier();
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 1);
        dateQualifier.setToDate(toCal.getTime()); // should find msg 3 and msg 4
        List results = locator.getByEmailAddress(email3, dateQualifier, new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(msg3, results.get(0));
        Assert.assertEquals(msg4, results.get(1));
    }
    
    public void testFindByEmailAddressFromToDesc() throws ArchiveStorageException {
        Calendar toCal = new GregorianCalendar();
        toCal.set(2004, Calendar.FEBRUARY, 20);
        Calendar fromCal = new GregorianCalendar();
        fromCal.set(2004, Calendar.JANUARY, 3); // should get msg 2 and msg 3
        DateQualifier dateQualifier = new DateQualifier(fromCal.getTime(), toCal.getTime());
        List results = locator.getByEmailAddress(email4, dateQualifier, new ResultQualifier(), false);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(msg4, results.get(1));
        Assert.assertEquals(msg5, results.get(0));
    }
    
    public void testResultQualifierMaxResults() throws ArchiveStorageException {
        ResultQualifier resultQual = new ResultQualifier(0, 2);
        List results = locator.getChronologically(new DateQualifier(), resultQual, true);
        Assert.assertEquals(2, results.size());
    }
    
    public void testResultQualifierFirstElement() throws ArchiveStorageException {
        ResultQualifier resultQual = new ResultQualifier(2,2);
        List unQualifiedResults = locator.getChronologically(new DateQualifier(), new ResultQualifier(), true);
        List qualifiedResults = locator.getChronologically(new DateQualifier(), resultQual, true);
        Assert.assertEquals(2, qualifiedResults.size());
        Assert.assertEquals(unQualifiedResults.get(2), qualifiedResults.get(0));
        Assert.assertEquals(unQualifiedResults.get(3), qualifiedResults.get(1));
    }
   
}
