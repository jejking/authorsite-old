/*
 * EmailAddressDataLoader.java, created on 29-Oct-2004 at 00:22:54
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressDataLoader.java is part of authorsite.org's MailArchive program.
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

import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.model.impl.*;

import org.hibernate.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class EmailAddressDataLoader implements HibernateDataLoader {

    /**
     * Loads some email addresses to delete and to find.
     * 
     * @see org.authorsite.mailarchive.services.storage.HibernateDataLoader#loadData(net.sf.hibernate.SessionFactory)
     */
    public void loadData(SessionFactory sessionFactory) throws HibernateException {
        EmailAddress addr1 = new EmailAddressImpl("emailAddressDataLoader1@test.com");
        EmailAddress addr2 = new EmailAddressImpl("emailAddressDataLoader2@test.com");
        EmailAddress addr3 = new EmailAddressImpl("emailAddressDataLoader3@test.com");
        
        
        EmailAddress addr4 = new EmailAddressImpl("emailAddressDataLoaderFind4@test.com");
        addr4.setPersonalName("EmailAddressDataLoaderTest Four");
        
        EmailAddress addr5 = new EmailAddressImpl("emailAddressDataLoaderFind5@test.com");
        addr5.setPersonalName("EmailAddressDataLoaderTest Five");
        
        Person addr6Person = new PersonImpl();
        addr6Person.setMainName("EmailAddressDataLoaderTestPerson");
        
        EmailAddress addr6a = new EmailAddressImpl("emailAddressDataLoaderFind6a@test.com");
        addr6a.setPerson(addr6Person);
        EmailAddress addr6b = new EmailAddressImpl("emailAddressDataLoaderFind6b@test.com");
        addr6b.setPerson(addr6Person);
        
        EmailAddress addr7 = new EmailAddressImpl("emailAddressDataLoaderFind7@test.com");
        // this'll confuse 'em ;-)
        addr7.setPersonalName("EmailAddressDataLoaderTest Four");
        
        EmailAddress addr8 = new EmailAddressImpl("emailAddressDataLoaderFind8@test.com");
        EmailAddress addr9 = new EmailAddressImpl("emailAddressDataLoaderFind9@test.com");
        EmailMessage msg = new EmailMessageImpl("emailAddressDataLoaderTestMail1", "don't delete referenced addresses", new Date());
        msg.addMessageEmailAddress(new MessageEmailAddressImpl(addr8, EmailAddressRole.FROM));
        msg.addMessageEmailAddress(new MessageEmailAddressImpl(addr9, EmailAddressRole.TO));
        
        Session session = null;
        Transaction tx = null;
        
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(addr1);
            session.save(addr2);
            session.save(addr3);
            session.save(addr4);
            session.save(addr5);
            
            session.save(addr6Person);
            session.save(addr6a);
            session.save(addr6b);
            
            session.save(addr7);
            
            session.save(addr8);
            session.save(addr9);
            session.save(msg);
            tx.commit();
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
        }
        finally {
            session.close();
        }

    }

}
