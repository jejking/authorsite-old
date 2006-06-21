/*
 * MessageLocatorRoleDataLoader.java, created on 12-Oct-2004 at 09:13:10
 * 
 * Copyright John King, 2004.
 *
 *  MessageLocatorRoleDataLoader.java is part of authorsite.org's MailArchive program.
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
 * Utility to seed data for use in MessageLocatorRoleTest
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class MessageLocatorRoleDataLoader implements HibernateDataLoader {

    private static Logger logger = Logger.getLogger(MessageLocatorRoleDataLoader.class);
    
    /**
     * @see org.authorsite.mailarchive.services.storage.HibernateDataLoader#loadData(net.sf.hibernate.SessionFactory)
     */
    public void loadData(SessionFactory sessionFactory) throws HibernateException {
        	
        /*
         * Person Jack has two email addresses (jackMail1)= jack1@messagelocator.role.com
         * 														(jackMail2)	jack2@messaglocator.role.com
         * 
         * Person  Jill has two email addresses (jillMail1)- jill1@messagelocator.role.com
         * 														(jillMail2)   jill2@messagelocator.role.com
         * 
         * Jack sends Jill four emails from jack1 (two to jill1, two to jill2) and four from jack2 (two to jill1, two to jill 2)
         * Jill sends Jack four emails from jill1 (two to jack1, two to jack2) and four from jill2 (two to jack1, two to jack2)
         * 
         */
        Session session = null;
        Transaction tx = null;
        Calendar cal = new GregorianCalendar();
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            
            /*
             * set up Jack and his email addresses
             */
            Person jack = new PersonImpl();
            jack.setGivenName("Jack");
            jack.setMainName("Fairytale");
            
            EmailAddress jack1 = new EmailAddressImpl("jack1@messagelocator.role.com");
            jack1.setPerson(jack);
            EmailAddress jack2 = new EmailAddressImpl("jack2@messagelocator.role.com");
            jack2.setPerson(jack);

            session.save(jack1);
            session.save(jack2);
            
            jack.addEmailAddress(jack1);
            jack.addEmailAddress(jack2);
            
            session.save(jack);
                     
            
            /*
             * set up Jill and her email addresses
             */
            Person jill = new PersonImpl();
            jill.setGivenName("Jill");
            jill.setMainName("vom Maerchen"); // gratuitous German surname.
            
            EmailAddress jill1 = new EmailAddressImpl("jill1@messagelocator.role.com");
            jill1.setPerson(jill);
            EmailAddress jill2 = new EmailAddressImpl("jill2@messagelocator.role.com");
            jill2.setPerson(jill);
            
            session.save(jill2);
            session.save(jill1);
            
            session.save(jill);
            
            /*
             * Jack sends jill four emails over a period of four months in 2004 to her two email addresses from jack1
             */
            EmailMessage jack1_2_jill1_1 = new EmailMessageImpl("jack1_2_jill1_1", "stuff");
            cal.set(2004, Calendar.JANUARY, 2);
            jack1_2_jill1_1.setReceivedDate(cal.getTime());
            MessageEmailAddress fromJack1_2_jill1_1 = new MessageEmailAddressImpl(jack1, EmailAddressRole.FROM);
            MessageEmailAddress toJack1_2_jill_1_1 = new MessageEmailAddressImpl(jill1, EmailAddressRole.TO);
            jack1_2_jill1_1.addMessageEmailAddress(fromJack1_2_jill1_1);
            jack1_2_jill1_1.addMessageEmailAddress(toJack1_2_jill_1_1);
            session.save(jack1_2_jill1_1);
            
            EmailMessage jack1_2_jill1_2 = new EmailMessageImpl("jack1_2_jill1_2", "stoff");
            cal.set(2004, Calendar.FEBRUARY, 2);
            jack1_2_jill1_2.setReceivedDate(cal.getTime());
            MessageEmailAddress fromJack1_2_jill1_2 = new MessageEmailAddressImpl(jack1, EmailAddressRole.FROM);
            MessageEmailAddress toJack1_2_jill1_2 = new MessageEmailAddressImpl(jill1, EmailAddressRole.TO);
            jack1_2_jill1_2.addMessageEmailAddress(fromJack1_2_jill1_2);
            jack1_2_jill1_2.addMessageEmailAddress(toJack1_2_jill1_2);
            session.save(jack1_2_jill1_2);
            
            EmailMessage jack1_2_jill2_1 = new EmailMessageImpl("jack1_2_jill2_1", "thing");
            cal.set(2004, Calendar.MARCH, 2);
            jack1_2_jill2_1.setReceivedDate(cal.getTime());
            MessageEmailAddress fromJack1_2_jill2_1 = new MessageEmailAddressImpl(jack1, EmailAddressRole.FROM);
            MessageEmailAddress toJack1_2_jill2_1 = new MessageEmailAddressImpl(jill2, EmailAddressRole.TO);
            jack1_2_jill2_1.addMessageEmailAddress(fromJack1_2_jill2_1);
            jack1_2_jill2_1.addMessageEmailAddress(toJack1_2_jill2_1);
            session.save(jack1_2_jill2_1);
            
            EmailMessage jack1_2_jill2_2 = new EmailMessageImpl("jack1_2_jill2_2", "thingymybob");
            cal.set(2004, Calendar.APRIL, 2);
            jack1_2_jill2_2.setReceivedDate(cal.getTime());
            jack1_2_jill2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack1, EmailAddressRole.FROM));
            jack1_2_jill2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.TO));
            session.save(jack1_2_jill2_2);
            
            
            /*
             * jack sends jill four emails over a periof of four months in 2004 to her two email addresses from jack2
             */
            
            EmailMessage jack2_2_jill1_1 = new EmailMessageImpl("jack2_2_jill1_1", "thang");
            cal.set(2004, Calendar.JANUARY, 10);
            jack2_2_jill1_1.setReceivedDate(cal.getTime());
            jack2_2_jill1_1.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.FROM));
            jack2_2_jill1_1.addMessageEmailAddress(new MessageEmailAddressImpl(jill1, EmailAddressRole.TO));
            session.save(jack2_2_jill1_1);
            
            EmailMessage jack2_2_jill1_2 = new EmailMessageImpl("jack2_2_jill1_2", "gizmos");
            cal.set(2004, Calendar.FEBRUARY, 10);
            jack2_2_jill1_2.setReceivedDate(cal.getTime());
            jack2_2_jill1_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.FROM));
            jack2_2_jill1_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill1, EmailAddressRole.TO));
            session.save(jack2_2_jill1_2);
            
            EmailMessage jack2_2_jill2_1 = new EmailMessageImpl("jack2_2_jill2_1", "thingies");
            cal.set(2004, Calendar.MARCH, 10);
            jack2_2_jill2_1.setReceivedDate(cal.getTime());
            jack2_2_jill2_1.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.FROM));
            jack2_2_jill2_1.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.TO));
            session.save(jack2_2_jill2_1);
            
            EmailMessage jack2_2_jill2_2 = new EmailMessageImpl("jack2_2_jill2_2", "a question");
            cal.set(2004, Calendar.APRIL, 10);
            jack2_2_jill2_2.setReceivedDate(cal.getTime());
            jack2_2_jill2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.FROM));
            jack2_2_jill2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.TO));
            session.save(jack2_2_jill2_2);
            
            /*
             * jill sends jack four emails to his two email addresses from jill1
             */
            EmailMessage jill1_2_jack1_1 = new EmailMessageImpl("jill1_2_jack1_1", "two questions of sport");
            cal.set(2004, Calendar.JANUARY, 19);
            jill1_2_jack1_1.setReceivedDate(cal.getTime());
            jill1_2_jack1_1.addMessageEmailAddress(new MessageEmailAddressImpl(jill1, EmailAddressRole.FROM));
            jill1_2_jack1_1.addMessageEmailAddress(new MessageEmailAddressImpl(jack1, EmailAddressRole.TO));
            session.save(jill1_2_jack1_1);
            
            EmailMessage jill1_2_jack1_2 = new EmailMessageImpl("jill1_2_jack1_2", "greetings");
            cal.set(2004, Calendar.FEBRUARY, 19);
            jill1_2_jack1_2.setReceivedDate(cal.getTime());
            jill1_2_jack1_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill1, EmailAddressRole.FROM));
            jill1_2_jack1_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack1, EmailAddressRole.TO));
            session.save(jill1_2_jack1_2);
            
            EmailMessage jill1_2_jack2_1 = new EmailMessageImpl("jill1_2_jack2_1", "hi again");
            cal.set(2004, Calendar.MARCH, 19);
            jill1_2_jack2_1.setReceivedDate(cal.getTime());
            jill1_2_jack2_1.addMessageEmailAddress(new MessageEmailAddressImpl(jill1, EmailAddressRole.FROM));
            jill1_2_jack2_1.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.TO));
            session.save(jill1_2_jack2_1);
            
            EmailMessage jill1_2_jack2_2 = new EmailMessageImpl("jill1_2_jack2_2", "bonjour");
            cal.set(2004, Calendar.APRIL, 19);
            jill1_2_jack2_2.setReceivedDate(cal.getTime());
            jill1_2_jack2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill1, EmailAddressRole.FROM));
            jill1_2_jack2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.TO));
            session.save(jill1_2_jack2_2);
            
            /*
             * jill sends jack four emails to his two email addresses from jill2
             */
            EmailMessage jill2_2_jack1_1 = new EmailMessageImpl("jill2_2_jack1_1", "a question");
            cal.set(2004, Calendar.JANUARY, 25);
            jill2_2_jack1_1.setReceivedDate(cal.getTime());
            jill2_2_jack1_1.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.FROM));
            jill2_2_jack1_1.addMessageEmailAddress(new MessageEmailAddressImpl(jack1, EmailAddressRole.TO));
            session.save(jill2_2_jack1_1);
            
            EmailMessage jill2_2_jack1_2 = new EmailMessageImpl("jill2_2_jack1_2", "no this is better");
            cal.set(2004, Calendar.FEBRUARY, 25);
            jill2_2_jack1_2.setReceivedDate(cal.getTime());
            jill2_2_jack1_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.FROM));
            jill2_2_jack1_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack1, EmailAddressRole.TO));
            session.save(jill2_2_jack1_2);
            
            EmailMessage jill2_2_jack2_1 = new EmailMessageImpl("jill2_2_jack2_1", "think you should see this");
            cal.set(2004, Calendar.MARCH, 25);
            jill2_2_jack2_1.setReceivedDate(cal.getTime());
            jill2_2_jack2_1.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.FROM));
            jill2_2_jack2_1.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.TO));
            session.save(jill2_2_jack2_1);
            
            EmailMessage jill2_2_jack2_2 = new EmailMessageImpl("jill2_2_jack2_2", "and this is a must");
            cal.set(2004, Calendar.APRIL, 25);
            jill2_2_jack2_2.setReceivedDate(cal.getTime());
            jill2_2_jack2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jill2, EmailAddressRole.FROM));
            jill2_2_jack2_2.addMessageEmailAddress(new MessageEmailAddressImpl(jack2, EmailAddressRole.TO));
            session.save(jill2_2_jack2_2);
            
            tx.commit();
        }
        catch (HibernateException he) {
            logger.error(he);
            if (tx != null) {
                tx.rollback();
            }
        }
        finally {
            session.close();
        }

    }

}
