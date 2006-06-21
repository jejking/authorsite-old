/*
 * MessageLocatorRoleTest.java, created on 12-Oct-2004 at 09:12:23
 * 
 * Copyright John King, 2004.
 *
 *  MessageLocatorRoleTest.java is part of authorsite.org's MailArchive program.
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
public class MessageLocatorRoleTest extends AbstractPersistenceTestCase {

    private static Logger logger = Logger.getLogger(MessageLocatorRoleTest.class);
    
    // declare all the things we created in MessageLocatorRoleDataLoader
    
    private Person jack;
    private EmailAddress jack1;
    private EmailAddress jack2;
    
    private Person jill;
    private EmailAddress jill1;
    private EmailAddress jill2;
    
    // from jack1 to jill
    private EmailMessage jack1_2_jill1_1;
    private EmailMessage jack1_2_jill1_2;
    private EmailMessage jack1_2_jill2_1;
    private EmailMessage jack1_2_jill2_2;
    
    // from jack2 to jill
    private EmailMessage jack2_2_jill1_1;
    private EmailMessage jack2_2_jill1_2;
    private EmailMessage jack2_2_jill2_1;
    private EmailMessage jack2_2_jill2_2;
    
    // from jill1 to jack
    private EmailMessage jill1_2_jack1_1;
    private EmailMessage jill1_2_jack1_2;
    private EmailMessage jill1_2_jack2_1;
    private EmailMessage jill1_2_jack2_2;
    
    // from jill2 to jack
    private EmailMessage jill2_2_jack1_1;
    private EmailMessage jill2_2_jack1_2;
    private EmailMessage jill2_2_jack2_1;
    private EmailMessage jill2_2_jack2_2;
    
    private EmailMessageDAO locator;
    
    /**
     * @param name
     * @throws Exception
     */
    public MessageLocatorRoleTest(String name) throws Exception {
        super(name);
        Session session = null;
        
        try {
            session = sessionFactory.openSession();
            Query getPersonQuery = session.createQuery("from PersonImpl person where person.givenName = :givenName and person.mainName = :mainName");
            jack = (Person) getPersonQuery.setString("givenName", "Jack").setString("mainName", "Fairytale").list().get(0);
            jill = (Person) getPersonQuery.setString("givenName", "Jill").setString("mainName", "vom Maerchen").list().get(0);
                        
            Query getEmailQuery = session.createQuery("from EmailAddressImpl emailaddr where emailaddr.address = :address");
            jack1 = (EmailAddress) getEmailQuery.setString("address", "jack1@messagelocator.role.com").list().get(0);
            jack2 = (EmailAddress) getEmailQuery.setString("address", "jack2@messagelocator.role.com").list().get(0);
            jill1 = (EmailAddress) getEmailQuery.setString("address", "jill1@messagelocator.role.com").list().get(0);
            jill2 = (EmailAddress) getEmailQuery.setString("address", "jill2@messagelocator.role.com").list().get(0);;
                        
            Query getEmailMessageQuery = session.createQuery("from EmailMessageImpl msg where msg.msgID = :msgid");
            jack1_2_jill1_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack1_2_jill1_1").list().get(0);
            jack1_2_jill1_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack1_2_jill1_2").list().get(0);
            jack1_2_jill2_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack1_2_jill2_1").list().get(0);
            jack1_2_jill2_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack1_2_jill2_2").list().get(0);
            
            jack2_2_jill1_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack2_2_jill1_1").list().get(0);
            jack2_2_jill1_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack2_2_jill1_2").list().get(0);
            jack2_2_jill2_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack2_2_jill2_1").list().get(0);
            jack2_2_jill2_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jack2_2_jill2_2").list().get(0);
            
            jill1_2_jack1_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill1_2_jack1_1").list().get(0);
            jill1_2_jack1_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill1_2_jack1_2").list().get(0);
            jill1_2_jack2_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill1_2_jack2_1").list().get(0);
            jill1_2_jack2_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill1_2_jack2_2").list().get(0);
            
            jill2_2_jack1_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill2_2_jack1_1").list().get(0);
            jill2_2_jack1_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill2_2_jack1_2").list().get(0);
            jill2_2_jack2_1 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill2_2_jack2_1").list().get(0);
            jill2_2_jack2_2 = (EmailMessage) getEmailMessageQuery.setString("msgid", "jill2_2_jack2_2").list().get(0);
        }
        catch (HibernateException he) {
            logger.error(he);
        }
        finally {
            session.close();
        }
        
        locator = new HibernateEmailMessageDAO(sessionFactory);
        
    }
    
    public void testFindByEmailAddressInFromRoleAsc() throws ArchiveStorageException {
        // i.e. find mails sent by an email address
        
        // find the emails sent by jack1 - there are 4
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.FROM, new DateQualifier(), new ResultQualifier(), true);
        // there should be 4
        Assert.assertEquals(4, results.size());
        Assert.assertEquals(jack1_2_jill1_1, results.get(0));
        Assert.assertEquals(jack1_2_jill1_2, results.get(1));
        Assert.assertEquals(jack1_2_jill2_1, results.get(2));
        Assert.assertEquals(jack1_2_jill2_2, results.get(3));
    }
    
    public void testFindByEmailAddressInFromRoleDesc() throws ArchiveStorageException {
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.FROM, new DateQualifier(), new ResultQualifier(), false);
        Assert.assertEquals(4, results.size());
        Assert.assertEquals(jack1_2_jill1_1, results.get(3));
        Assert.assertEquals(jack1_2_jill1_2, results.get(2));
        Assert.assertEquals(jack1_2_jill2_1, results.get(1));
        Assert.assertEquals(jack1_2_jill2_2, results.get(0));
    }
    
    public void testFindByEmailAddressInToRoleAsc() throws ArchiveStorageException {
        // i.e find emails sent to an email address, sort by received date ascending order
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.TO, new DateQualifier(), new ResultQualifier(), true);
        // how many mails sent to jack1 ? 4 two from jill1, two from jill2
        Assert.assertEquals(4, results.size());
        Assert.assertEquals(jill1_2_jack1_1, results.get(0));
        Assert.assertEquals(jill2_2_jack1_1, results.get(1));
        Assert.assertEquals(jill1_2_jack1_2, results.get(2));
        Assert.assertEquals(jill2_2_jack1_2, results.get(3));
    }
    
    public void testFindByEmailAddressInToRoleDesc() throws ArchiveStorageException {
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.TO, new DateQualifier(), new ResultQualifier(), false);
        // how many mails sent to jack1 ? 4 two from jill1, two from jill2
        Assert.assertEquals(4, results.size());
        Assert.assertEquals(jill1_2_jack1_1, results.get(3));
        Assert.assertEquals(jill2_2_jack1_1, results.get(2));
        Assert.assertEquals(jill1_2_jack1_2, results.get(1));
        Assert.assertEquals(jill2_2_jack1_2, results.get(0));
    }
    
    public void testFindByEmailAddressInRoleFromFromAsc() throws ArchiveStorageException {
        // find mails sent by jack 1 from jan 5th
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.JANUARY, 5);
        Date fromDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier();
        dateQual.setFromDate(fromDate);
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.FROM, dateQual, new ResultQualifier(), true);
        // should be 3
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(jack1_2_jill1_2, results.get(0));
        Assert.assertEquals(jack1_2_jill2_1, results.get(1));
        Assert.assertEquals(jack1_2_jill2_2, results.get(2));
    }
    
    public void testFindByEmailAddressInRoleFromFromDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.JANUARY, 5);
        Date fromDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier();
        dateQual.setFromDate(fromDate);
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.FROM, dateQual, new ResultQualifier(), false);
        // should be 3
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(jack1_2_jill1_2, results.get(2));
        Assert.assertEquals(jack1_2_jill2_1, results.get(1));
        Assert.assertEquals(jack1_2_jill2_2, results.get(0));
    }
    
    public void testFindByEmailAddressInRoleToToAsc() throws ArchiveStorageException {
        // find emails sent to jack1 in the period before Feb 20
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.FEBRUARY, 20);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier();
        dateQual.setToDate(toDate);
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.TO, dateQual, new ResultQualifier(), true);
        // should be three of them again
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(jill1_2_jack1_1, results.get(0));
        Assert.assertEquals(jill2_2_jack1_1, results.get(1));
        Assert.assertEquals(jill1_2_jack1_2, results.get(2));
    }
    
    public void testFindByEmailAddressInRoleToToDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.FEBRUARY, 20);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier();
        dateQual.setToDate(toDate);
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.TO, dateQual, new ResultQualifier(), false);
        // should be three of them again
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(jill1_2_jack1_1, results.get(2));
        Assert.assertEquals(jill2_2_jack1_1, results.get(1));
        Assert.assertEquals(jill1_2_jack1_2, results.get(0));
    }
    
    public void testFindByEmailAddressInRoleFromFromToAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.JANUARY, 5);
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.MARCH, 20);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, toDate);
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.FROM, dateQual, new ResultQualifier(), true);
        // should only be two
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(jack1_2_jill1_2, results.get(0));
        Assert.assertEquals(jack1_2_jill2_1, results.get(1));
    }
    
    public void testFindByEmailAddressInRoleFromFromToDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.JANUARY, 5);
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.MARCH, 20);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, toDate);
        List results = locator.getByEmailAddressInRole(jack1, EmailAddressRole.FROM, dateQual, new ResultQualifier(), false);
        // should only be two
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(jack1_2_jill1_2, results.get(1));
        Assert.assertEquals(jack1_2_jill2_1, results.get(0));
    }
    
    public void testFindByPersonInFromRoleAsc() throws ArchiveStorageException {
        // find all emails sent by Jack, sorted in ascending order
        List results = locator.getByPersonInRole(jack, EmailAddressRole.FROM, new DateQualifier(), new ResultQualifier(), true);
        Assert.assertEquals(8, results.size());
        Assert.assertEquals(this.jack1_2_jill1_1, results.get(0));
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(7));
    }
    
    public void testFindByPersonInToRoleDesc() throws ArchiveStorageException {
        // find all emails setn to Jill, sorted in descending order
        List results = locator.getByPersonInRole(jill, EmailAddressRole.TO, new DateQualifier(), new ResultQualifier(), false);
        Assert.assertEquals(8, results.size());
        Assert.assertEquals(this.jack1_2_jill1_1, results.get(7));
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0));
    }
    
    public void testFindByPersonInFromRoleFromAsc() throws ArchiveStorageException {
        // find all emails sent by Jack after March 3rd
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.MARCH, 3);
        Date fromDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, null);
        List results = locator.getByPersonInRole(jack, EmailAddressRole.FROM, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(this.jack2_2_jill2_1, results.get(0)); // march 10
        Assert.assertEquals(this.jack1_2_jill2_2, results.get(1)); // april 2nd
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(2)); // april 10
    }
    
    public void testFindByPersonInFromRoleFromDesc() throws ArchiveStorageException {
        // find all emails sent by Jack after March 3rd
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.MARCH, 3);
        Date fromDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, null);
        List results = locator.getByPersonInRole(jack, EmailAddressRole.FROM, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(this.jack2_2_jill2_1, results.get(2)); // march 10
        Assert.assertEquals(this.jack1_2_jill2_2, results.get(1)); // april 2nd
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0)); // april 10
    }
    
    public void testFindByPersonInToRoleToAsc() throws ArchiveStorageException {
        // find all emails sent to Jill before February 7th
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.FEBRUARY, 7);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(null, toDate);
        List results = locator.getByPersonInRole(jill, EmailAddressRole.TO, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(this.jack1_2_jill1_1, results.get(0)); // 2nd jan
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(1)); // 10th jan
        Assert.assertEquals(this.jack1_2_jill1_2, results.get(2)); // 2nd feb
    }
    
    public void testFindByPersonInToRoleToDesc() throws ArchiveStorageException {
        //  find all emails sent to Jill before February 7th
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.FEBRUARY, 7);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(null, toDate);
        List results = locator.getByPersonInRole(jill, EmailAddressRole.TO, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(3, results.size());
        Assert.assertEquals(this.jack1_2_jill1_1, results.get(2)); // 2nd jan
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(1)); // 10th jan
        Assert.assertEquals(this.jack1_2_jill1_2, results.get(0)); // 2nd feb
    }
    
    public void testFindByPersonInFromRoleFromToAsc() throws ArchiveStorageException {
        // find emails sent by Jill between February 15th and April 5th
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.FEBRUARY, 15);
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.APRIL, 5);
        Date toDate = cal.getTime();
        DateQualifier dateQual  = new DateQualifier(fromDate, toDate);
        List results = locator.getByPersonInRole(jill, EmailAddressRole.FROM, dateQual, new ResultQualifier(), true);
        
        Assert.assertEquals(4, results.size());
        Assert.assertEquals(this.jill1_2_jack1_2, results.get(0)); // 19th feb
        Assert.assertEquals(this.jill2_2_jack1_2, results.get(1)); // 25th feb
        Assert.assertEquals(this.jill1_2_jack2_1, results.get(2)); // 19th march
        Assert.assertEquals(this.jill2_2_jack2_1, results.get(3)); // 25th march
        
    }
    
    public void testFindByPersonInFromRoleFromToDesc() throws ArchiveStorageException {
        //      find emails sent by Jill between February 15th and April 5th
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.FEBRUARY, 15);
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.APRIL, 5);
        Date toDate = cal.getTime();
        DateQualifier dateQual  = new DateQualifier(fromDate, toDate);
        List results = locator.getByPersonInRole(jill, EmailAddressRole.FROM, dateQual, new ResultQualifier(), false);
        
        Assert.assertEquals(4, results.size());
        Assert.assertEquals(this.jill1_2_jack1_2, results.get(3)); // 19th feb
        Assert.assertEquals(this.jill2_2_jack1_2, results.get(2)); // 25th feb
        Assert.assertEquals(this.jill1_2_jack2_1, results.get(1)); // 19th march
        Assert.assertEquals(this.jill2_2_jack2_1, results.get(0)); // 25th march
    }
    
    // there now follow some simple tests of the getBySubject functionality
    public void testFindBySubjectAsc() throws ArchiveStorageException {
        List results = locator.getBySubject("a question", false, new DateQualifier(), new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jill2_2_jack1_1, results.get(0));
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(1));
    }
    
    public void testFindBySubjectDesc() throws ArchiveStorageException {
        List results = locator.getBySubject("a question", false, new DateQualifier(), new ResultQualifier(), false);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jill2_2_jack1_1, results.get(1));
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0));
    }
    
    public void testFindBySubjectFromAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.APRIL, 9);
        Date fromDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, null);
        List results = locator.getBySubject("a question", false, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0));
    }
    
    // following tests are really basic, all they are there for is to ensure the query is actually run ok, really
    public void testFindBySubjectFromDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.APRIL, 9);
        Date fromDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, null);
        List results = locator.getBySubject("a question", false, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0));
    }
    
    public void testFindBySubjectToAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.APRIL, 9);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(null, toDate);
        List results = locator.getBySubject("a question", false, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(this.jill2_2_jack1_1, results.get(0));
    }
    
    public void testFindBySubjectToDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.APRIL, 9);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(null, toDate);
        List results = locator.getBySubject("a question", false, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(this.jill2_2_jack1_1, results.get(0));
    }
    
    public void testFindBySubjectFromToAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.APRIL, 9);
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.APRIL, 11);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, toDate);
        List results = locator.getBySubject("a question", false, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0));
    }
    
    public void testFindBySubjectFromToDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.APRIL, 9);
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.APRIL, 11);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, toDate);
        List results = locator.getBySubject("a question", false, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(this.jack2_2_jill2_2, results.get(0));
    }

    // and some tests of getBySubjectLike
    public void testFindBySubjectLikeAsc() throws ArchiveStorageException {
        List results = locator.getBySubject("th_ng", true, new DateQualifier(), new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(0));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(1));
    }
    
    public void testFindBySubjectLikeDesc() throws ArchiveStorageException {
        List results = locator.getBySubject("th_ng", true, new DateQualifier(), new ResultQualifier(), false);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(1));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(0));
    }
    
    public void testFindBySubjectLikeFromAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2003, Calendar.DECEMBER, 25); // will  get the same two as above
        DateQualifier dateQual = new DateQualifier(cal.getTime(), null);
        List results = locator.getBySubject("th_ng", true, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(0));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(1));
    }
    
    public void testFindBySubjectLikeFromDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2003, Calendar.DECEMBER, 25); // will  get the same two as above
        DateQualifier dateQual = new DateQualifier(cal.getTime(), null);
        List results = locator.getBySubject("th_ng", true, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(1));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(0));
    }
    
    public void testFindBySubjectLikeToAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.DECEMBER, 25); // will  get the same two as above
        DateQualifier dateQual = new DateQualifier(null, cal.getTime());
        List results = locator.getBySubject("th_ng", true, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(0));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(1));
    }
    
    public void testFindBySubjectLikeToDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2004, Calendar.DECEMBER, 25); // will  get the same two as above
        DateQualifier dateQual = new DateQualifier(null, cal.getTime());
        List results = locator.getBySubject("th_ng", true, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(1));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(0));
    }
    
    public void testFindBySubjectLikeFromToAsc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2003, Calendar.DECEMBER, 25); // will  get the same two as above
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.DECEMBER, 25);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, toDate);
        List results = locator.getBySubject("th_ng", true, dateQual, new ResultQualifier(), true);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(0));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(1));
    }
    
    public void testFindBySubjectLikeFromToDesc() throws ArchiveStorageException {
        Calendar cal = new GregorianCalendar();
        cal.set(2003, Calendar.DECEMBER, 25); // will  get the same two as above
        Date fromDate = cal.getTime();
        cal.set(2004, Calendar.DECEMBER, 25);
        Date toDate = cal.getTime();
        DateQualifier dateQual = new DateQualifier(fromDate, toDate);
        List results = locator.getBySubject("th_ng", true, dateQual, new ResultQualifier(), false);
        Assert.assertEquals(2, results.size());
        Assert.assertEquals(this.jack2_2_jill1_1, results.get(1));
        Assert.assertEquals(this.jack1_2_jill2_1, results.get(0));
    }
    
}
