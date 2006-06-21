/*
 * HibernateEmailAddressDAO.java, created on 24-Oct-2004 at 02:11:14
 * 
 * Copyright John King, 2004.
 *
 *  HibernateEmailAddressDAO.java is part of authorsite.org's MailArchive program.
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

/**
 * 
 * @author jejking
 * @version $Revision: 1.7 $
 */
public class HibernateEmailAddressDAO implements EmailAddressDAO {

    private static Logger logger = Logger.getLogger(HibernateEmailAddressDAO.class);
    
    private SessionFactory sessionFactory;
    
    private EmailMessageDAO messageDAO;
    
    public HibernateEmailAddressDAO() {
        super();
    }

    public HibernateEmailAddressDAO(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }
    
    public HibernateEmailAddressDAO(SessionFactory sessionFactory, EmailMessageDAO messageDAO) {
        this(sessionFactory);
        setMessageDAO(messageDAO);
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public void setMessageDAO(EmailMessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    
    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#saveEmailAddress(org.authorsite.mailarchive.model.EmailAddress)
     */
    public void saveEmailAddress(EmailAddress addressToSave) throws ArchiveStorageException {
       Session session = null;
       Transaction tx = null;
       try {
           try {
               session = sessionFactory.openSession();
               tx = session.beginTransaction();
               session.saveOrUpdate(addressToSave);
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
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#saveEmailAddresses(java.util.List)
     */
    public void saveEmailAddresses(List addressesToSave) throws ArchiveStorageException {
        Session session = null;
        Transaction tx = null;
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                Iterator it = addressesToSave.iterator();
                while (it.hasNext()) {
                    EmailAddress addressToSave = (EmailAddress) it.next();
                    session.saveOrUpdate(addressToSave);
                }
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
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#deleteEmailAddress(org.authorsite.mailarchive.model.EmailAddress)
     */
    public void deleteEmailAddress(EmailAddress addressToDelete) throws ArchiveStorageException, AddressStillInUseException {
        Session session = null;
        Transaction tx = null;
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                List relatedMessages = messageDAO.getByEmailAddress(addressToDelete, new DateQualifier(), new ResultQualifier(), true);
                if (! relatedMessages.isEmpty()) {
                    tx.rollback();
                    throw new AddressStillInUseException(addressToDelete);
                }
                session.delete(addressToDelete);
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
        }
        catch (HibernateException he) {
            throw new ArchiveStorageException(he);
        }

    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#deleteEmailAddresses(java.util.List)
     */
    public void deleteEmailAddresses(List addressesToDelete) throws ArchiveStorageException, AddressStillInUseException {
        Session session = null;
        Transaction tx = null;
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                Iterator it = addressesToDelete.iterator();
                while (it.hasNext()) {
                    EmailAddress addressToDelete = (EmailAddress) it.next();
                    List relatedMessages = messageDAO.getByEmailAddress(addressToDelete, new DateQualifier(), new ResultQualifier(), true);
                    if (! relatedMessages.isEmpty()) {
                        tx.rollback();
                        throw new AddressStillInUseException(addressToDelete);
                    }
                    session.delete(addressToDelete);
                }
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
        }
        catch (HibernateException he) {
            throw new ArchiveStorageException(he);
        }

    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddress(java.lang.String)
     */
    public EmailAddress getEmailAddress(String addressString) throws ArchiveStorageException {
        Session session = null;
        EmailAddress addr = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("EmailAddressString");
                query.setString("addressString", addressString);
                addr = (EmailAddress) query.uniqueResult(); // this is fine here because of the unique index...
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
        return addr;
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesLike(java.lang.String)
     */
    public List getEmailAddressesLike(String addressPattern) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("EmailAddressStringLike");
                query.setString("addressString", addressPattern);
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
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesByPerson(org.authorsite.mailarchive.model.Person)
     */
    public List getEmailAddressesByPerson(Person person) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("EmailAddressPerson");
                query.setEntity("person", person);
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
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesByPersonalName(java.lang.String)
     */
    public List getEmailAddressesByPersonalName(String personalName) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("EmailAddressPersonalName");
                query.setString("personalName", personalName);
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
     * @see org.authorsite.mailarchive.services.storage.EmailAddressDAO#getEmailAddressesByPersonalNameLike(java.lang.String)
     */
    public List getEmailAddressesByPersonalNameLike(String personalNamePattern) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = session.getNamedQuery("EmailAddressPersonalNameLike");
                query.setString("personalName", personalNamePattern);
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
