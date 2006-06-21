/*
 * PersonDataLoader.java, created on 30-Oct-2004 at 21:53:17
 * 
 * Copyright John King, 2004.
 *
 *  PersonDataLoader.java is part of authorsite.org's MailArchive program.
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

import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.model.impl.*;

import org.hibernate.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class PersonDataLoader implements HibernateDataLoader {

    /**
     * @see org.authorsite.mailarchive.services.storage.HibernateDataLoader#loadData(net.sf.hibernate.SessionFactory)
     */
    public void loadData(SessionFactory sessionFactory) throws HibernateException {
        Person person1 = new PersonImpl("King");
        Person person2 = new PersonImpl("King", "John");
        Person person3 = new PersonImpl("Kingdom", "Jonathan");
                
        Person person4 = new PersonImpl("Delete", "Me");
        
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(person1);
            session.save(person2);
            session.save(person3);
            session.save(person4);
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
