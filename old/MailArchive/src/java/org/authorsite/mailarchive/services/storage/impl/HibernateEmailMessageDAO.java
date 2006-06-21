/*
 * HibernateMessageLocator.java, created on 26-Sep-2004 at 10:01:43
 * 
 * Copyright John King, 2004.
 *
 *  HibernateMessageLocator.java is part of authorsite.org's MailArchive program.
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
import org.authorsite.mailarchive.model.impl.*;
import org.authorsite.mailarchive.services.storage.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class HibernateEmailMessageDAO implements EmailMessageDAO {

    private static final String CHRONOLOGICAL = "Chronological";
    private static final String PERSON = "Person";
    private static final String PERSON_IN_ROLE = "PersonInRole";
    private static final String EMAILADDRESS = "EmailAddress";
    private static final String EMAILADDRESS_IN_ROLE = "EmailAddressInRole";
    private static final String SUBJECT = "Subject";
    private static final String SUBJECT_LIKE="SubjectLike";

    private static final String FROM = "From";
    private static final String TO = "To";
    private static final String ASC = "Asc";
    private static final String DESC = "Desc";
    
    private static Logger logger = Logger.getLogger(HibernateEmailMessageDAO.class);
    
    private SessionFactory sessionFactory;

    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public HibernateEmailMessageDAO() {
        super();
    }
    
    public HibernateEmailMessageDAO(SessionFactory sessionFactory) {
        logger.debug("Created HMEL with a sessionfactory");
        this.sessionFactory = sessionFactory;
    }
    
    /**
     * Returns list of messages sorted chronologically
     * 
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getChronologically(org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier)
     */
    public List getChronologically(DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {

        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = getQuery(HibernateEmailMessageDAO.CHRONOLOGICAL, dateQual, ascending, session);
                logger.debug("Got query: " + query.getQueryString() + " for getChronlogically DateQualifier " + dateQual + " ResultQualifer " + resultQual + "asc: " + ascending);
                qualifyResults(query, resultQual);
                results = query.list();
            } 
            catch (HibernateException he) {
                throw new ArchiveStorageException (he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            throw new ArchiveStorageException (he);
        }
        catch (ArchiveStorageException ase) {
            throw ase;
        }
        
        return results;
    }

    

    /**
     * Returns List of messages associated with a particular person instance. Sorted by date.
     * 
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByPerson(org.authorsite.mailarchive.model.Person, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier)
     */
    public List getByPerson(Person person, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                logger.debug("got person " + person + " with ID " + ((Identifiable)person).getID() + "\nDate Qualifier " + dateQual + "\nResult Qualifier " + resultQual);
                session = sessionFactory.openSession();
                Query query = getQuery(HibernateEmailMessageDAO.PERSON, dateQual, ascending, session);
                qualifyResults(query, resultQual);
                query.setEntity("personToFind", person);
                logger.debug("Got query: " + query.getQueryString() + " for getByPerson person: " + person + " DateQualifier " + dateQual + " ResultQualifer " + resultQual + "asc: " + ascending);
                results = query.list();
            } 
            catch (HibernateException he) {
                throw new ArchiveStorageException (he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            throw new ArchiveStorageException (he);
        }
        catch (ArchiveStorageException ase) {
            throw ase;
        }
        
        return results;
    }
    
    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByPersonInRole(org.authorsite.mailarchive.model.Person, org.authorsite.mailarchive.model.EmailAddressRole, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier, boolean)
     */
    public List getByPersonInRole(Person person, EmailAddressRole role, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                logger.debug("got person " + person + " with ID " + ((Identifiable)person).getID() + "\nDate Qualifier " + dateQual + "\nResult Qualifier " + resultQual);
                session = sessionFactory.openSession();
                Query query = getQuery(HibernateEmailMessageDAO.PERSON_IN_ROLE, dateQual, ascending, session);
                qualifyResults(query, resultQual);
                query.setEntity("personToFind", person);
                query.setString("role", role.toString());
                results = query.list();
            } 
            catch (HibernateException he) {
                throw new ArchiveStorageException (he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            throw new ArchiveStorageException (he);
        }
        catch (ArchiveStorageException ase) {
            throw ase;
        }
        
        return results;
    }

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByEmailAddress(org.authorsite.mailarchive.model.EmailAddress, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier)
     */
    public List getByEmailAddress(EmailAddress addr, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = getQuery(HibernateEmailMessageDAO.EMAILADDRESS, dateQual, ascending, session);
                qualifyResults(query, resultQual);
                query.setEntity("emailAddress", addr);
                results = query.list();
            } 
            catch (HibernateException he) {
                throw new ArchiveStorageException (he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            throw new ArchiveStorageException (he);
        }
        catch (ArchiveStorageException ase) {
            throw ase;
        }
        
        return results;
    }    

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getByEmailAddressInRole(org.authorsite.mailarchive.model.EmailAddress, org.authorsite.mailarchive.model.EmailAddressRole, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier)
     */
    public List getByEmailAddressInRole(EmailAddress addr, EmailAddressRole role, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = getQuery(HibernateEmailMessageDAO.EMAILADDRESS_IN_ROLE, dateQual, ascending, session);
                qualifyResults(query, resultQual);
                query.setEntity("emailAddress", addr);
                query.setString("role", role.toString());
                results = query.list();
            } 
            catch (HibernateException he) {
                throw new ArchiveStorageException (he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            throw new ArchiveStorageException (he);
        }
        catch (ArchiveStorageException ase) {
            throw ase;
        }
        
        return results;
    }
   

    /**
     * @see org.authorsite.mailarchive.services.storage.EmailMessageDAO#getBySubject(java.lang.String, boolean, org.authorsite.mailarchive.services.storage.DateQualifier, org.authorsite.mailarchive.services.storage.ResultQualifier)
     */
    public List getBySubject(String subject, boolean likeFlag, DateQualifier dateQual, ResultQualifier resultQual, boolean ascending) throws ArchiveStorageException {
        Session session = null;
        List results = null;
        try {
            try {
                session = sessionFactory.openSession();
                Query query = null;
                if (likeFlag) {
                    query = getQuery(HibernateEmailMessageDAO.SUBJECT_LIKE, dateQual, ascending, session);
                }
                else {
                    query = getQuery(HibernateEmailMessageDAO.SUBJECT, dateQual, ascending, session);
                }
                qualifyResults(query, resultQual);
                query.setString("subject", subject);
                results = query.list();
            } 
            catch (HibernateException he) {
                throw new ArchiveStorageException (he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            throw new ArchiveStorageException (he);
        }
        catch (ArchiveStorageException ase) {
            throw ase;
        }
        
        return results;
    }
    
    /**
     * @see org.authorsite.mailarchive.services.storage.MessagePersister#saveMessage(org.authorsite.mailarchive.model.EmailMessage)
     */
    public void saveMessage(EmailMessage message) throws ArchiveStorageException {
        Session session = null;
        Transaction tx = null;
        
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                persistMsg(message, session);
                tx.commit();
            }
            catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.error(he);
            }
            finally {
                session.close();
            }
        } catch (HibernateException he) {
            logger.fatal(he);
            throw new ArchiveStorageException (he);
        }

    }

    /**
     * @see org.authorsite.mailarchive.services.storage.MessagePersister#saveMessages(java.util.List)
     */
    public void saveMessages(List messages) throws ArchiveStorageException {
        Iterator it = messages.iterator();
        Session session = null;
        Transaction tx = null;

        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                while (it.hasNext()) {
                    try {
                        EmailMessage msg = (EmailMessage) it.next();
                        persistMsg(msg, session);
                    } catch (ClassCastException cce) {
                        tx.rollback();
                        logger.warn("ClassCastException: ", cce);
                        break;
                    }
                }
                tx.commit();
            } 
            catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.error(he);
                throw new ArchiveStorageException(he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            logger.fatal(he);
            throw new ArchiveStorageException(he);
        }

    }

    /**
     * @param msg
     * @param session
     */
    private void persistMsg(EmailMessage msg, Session session) throws HibernateException {
        // break down the message and write it out in the correct order
        
        // text content
        Set textContent = msg.getTextParts();
        Iterator textContentIt = textContent.iterator();
        while (textContentIt.hasNext()) {
            TextContent txt = (TextContent) textContentIt.next();
            session.save(txt);
        }
        
        // binary content
        Set binContent = msg.getBinaryParts();
        Iterator binContentIt = binContent.iterator();
        while (binContentIt.hasNext()) {
            BinaryContent bin = (BinaryContent) binContentIt.next();
            session.save(bin);
        }
        
        // email addresses should be already handled by the correct EmailAddressFactory strategy
        
        // now write the email message
        session.save(msg);

    }

    /**
     * @see org.authorsite.mailarchive.services.storage.MessagePersister#deleteMessage(org.authorsite.mailarchive.model.EmailMessage)
     */
    public void deleteMessage(EmailMessage message) throws ArchiveStorageException {
        Session session = null;
        Transaction tx = null;
        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                session.delete(message);
                tx.commit();
            }
            catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.error(he);
                throw new ArchiveStorageException(he);
            }
            finally {
                session.close();
            }
        }
        catch (HibernateException he) {
            logger.fatal(he);
            throw new ArchiveStorageException(he);
        }
    }
    

    /**
     * @see org.authorsite.mailarchive.services.storage.MessagePersister#deleteMessages(java.util.List)
     */
    public void deleteMessages(List messages) throws ArchiveStorageException {
        Iterator it = messages.iterator();
        Session session = null;
        Transaction tx = null;

        try {
            try {
                session = sessionFactory.openSession();
                tx = session.beginTransaction();
                while (it.hasNext()) {
                    try {
                        EmailMessage msg = (EmailMessage) it.next();
                        session.delete(msg);
                    } catch (ClassCastException cce) {
                        tx.rollback();
                        logger.warn("ClassCastException: ", cce);
                        break;
                    }
                }
                tx.commit();
            } 
            catch (HibernateException he) {
                if (tx != null) {
                    tx.rollback();
                }
                logger.error(he);
                throw new ArchiveStorageException(he);
            }
            finally {
                session.close();
            }
        } 
        catch (HibernateException he) {
            logger.fatal(he);
            throw new ArchiveStorageException(he);
        }

        
    }
    
    /**
     * @param queryType
     * @param dateQual
     * @param ascending
     * @return
     * @throws HibernateException
     * @ TODO optimise the string processing with an internal cache
     */
    private Query getQuery(String queryType, DateQualifier dateQual, boolean ascending, Session session) throws HibernateException {
        
        StringBuffer buffer = new StringBuffer(queryType);
        
        // from date only
        if (dateQual.getFromDate() != null && dateQual.getToDate() == null) {
            buffer.append(HibernateEmailMessageDAO.FROM);
        }
        // to date only
        else if (dateQual.getFromDate() == null && dateQual.getToDate() != null) {
            buffer.append(HibernateEmailMessageDAO.TO);
        }
        // from and to date both set
        else if (dateQual.getFromDate() != null && dateQual.getToDate() != null){
          buffer.append(HibernateEmailMessageDAO.FROM);
          buffer.append(HibernateEmailMessageDAO.TO);
        }
        // if neither are set, leave alone
        // ascending flag
        if (ascending) {
            buffer.append(HibernateEmailMessageDAO.ASC);
        }
        else {
            buffer.append(HibernateEmailMessageDAO.DESC);
        }
        logger.debug("identified query as : " + buffer.toString());
        Query query = session.getNamedQuery(buffer.toString());
    
        // set date params on query if needed
        
        //      from date only
        if (dateQual.getFromDate() != null) {
            query.setDate("from", dateQual.getFromDate());
        }
        // to date only
        if (dateQual.getToDate() != null) {
            query.setDate("to", dateQual.getToDate());
        }
        
        return query;
    }

    
    /**
     * @param query
     */
    private void qualifyResults(Query query, ResultQualifier resultQual) {
        if (resultQual != null) {	
            int maxResults = resultQual.getMaxResults();
            int firstResult = resultQual.getFirstResult();
            
            if (maxResults > 0) {
                query.setMaxResults(maxResults);
            }
            if (firstResult > 0) {
                query.setFirstResult(firstResult);
            }
        }
    }

   


}
