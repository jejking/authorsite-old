/*
 * HibernatePersonDAO.java, created on 30-Oct-2004 at 19:38:03
 * 
 * Copyright John King, 2004.
 *
 *  HibernatePersonDAO.java is part of authorsite.org's MailArchive program.
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
package org.authorsite.mailarchive.services.storage.impl;

import java.util.*;

import org.hibernate.*;

import org.apache.log4j.*;
import org.authorsite.mailarchive.model.*;
import org.authorsite.mailarchive.services.storage.*;
import org.hibernate.SessionFactory;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class HibernatePersonDAO implements PersonDAO {

    private static Logger logger = Logger.getLogger(HibernatePersonDAO.class);
    
    private SessionFactory sessionFactory;
    
    public HibernatePersonDAO() {
        super();
    }
    
    public HibernatePersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * @see org.authorsite.mailarchive.services.storage.PersonDAO#savePerson(org.authorsite.mailarchive.model.Person)
     */
    public void savePerson(Person person) throws ArchiveStorageException {
        Session session = null;
        Transaction tx = null;
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                session.saveOrUpdate(person);
                tx.commit();
            }
            catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.warn(he);
                throw he;
            }
            finally {
                session.close();
            }
        }
        catch (HibernateException he) {
            throw new ArchiveStorageException(he);
        }

    }

    /**
     * @see org.authorsite.mailarchive.services.storage.PersonDAO#deletePerson(org.authorsite.mailarchive.model.Person)
     */
    public void deletePerson(Person person) throws ArchiveStorageException {
        Session session = null;
        Transaction tx = null;
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                session.delete(person);
                tx.commit();
            }
            catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.warn(he);
                throw he;
            }
            finally {
                session.close();
            }
        }
        catch (HibernateException he) {
            throw new ArchiveStorageException(he);
        }
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.PersonDAO#getByMainName(java.lang.String)
     */
    public List getByMainName(String mainName) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("PersonMainName");
                query.setString("mainName", mainName);
                results = query.list();
            }
           catch (HibernateException he) {
               logger.warn(he);
           }
           finally {
               session.close();
           }
        }
        catch (HibernateException he) {
            logger.warn(he);
            throw new ArchiveStorageException (he);
        }
        return results;
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.PersonDAO#getByMainNameLike(java.lang.String)
     */
    public List getByMainNameLike(String mainNamePattern) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("PersonMainNameLike");
                query.setString("mainName", mainNamePattern);
                results = query.list();
            }
           catch (HibernateException he) {
               logger.warn(he);
           }
           finally {
               session.close();
           }
        }
        catch (HibernateException he) {
            logger.warn(he);
            throw new ArchiveStorageException (he);
        }
        return results;
    }


    /**
     * @see org.authorsite.mailarchive.services.storage.PersonDAO#getByMainNameAndGivenName(java.lang.String, java.lang.String)
     */
    public List getByMainNameAndGivenName(String mainName, String givenName) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("PersonMainNameGivenName");
                query.setString("mainName", mainName);
                query.setString("givenName", givenName);
                results = query.list();
            }
           catch (HibernateException he) {
               logger.warn(he);
           }
           finally {
               session.close();
           }
        }
        catch (HibernateException he) {
            logger.warn(he);
            throw new ArchiveStorageException (he);
        }
        return results;
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.PersonDAO#getByMainNameLikeAndGivenNameLike(java.lang.String, java.lang.String)
     */
    public List getByMainNameLikeAndGivenNameLike(String mainNamePattern, String givenNamePattern) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("PersonMainNameGivenNameLike");
                query.setString("mainName", mainNamePattern);
                query.setString("givenName", givenNamePattern);
                results = query.list();
            }
           catch (HibernateException he) {
               logger.warn(he);
           }
           finally {
               session.close();
           }
        }
        catch (HibernateException he) {
            logger.warn(he);
            throw new ArchiveStorageException (he);
        }
        return results;
    }

}
