/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.dao.bib;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Journal;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class JournalDaoJPA implements JournalDao {

    private EntityManager entityManager;
    
    /**
     * Sets the entity manager reference.
     * 
     * <p>IoC use only.</p>
     * 
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    /**
     * Default constructor.
     */
    public JournalDaoJPA() {
        super();
    }
    
    @Transactional(readOnly=true)
    public int countJournals() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("JournalCount").getSingleResult();
        return n.intValue();
    }

    

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findAllJournals() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllJournals");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findAllJournals(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllJournals");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    public Journal findById(long id) throws DataAccessException {
        return this.entityManager.find(Journal.class, new Long(id));
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findJournalsAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("JournalsAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findJournalsBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("JournalsBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findJournalsBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("JournalsBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findJournalsByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("JournalsByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findJournalsByTitleWildcard(String titleWildcard) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("JournalsByTitleWildcard");
        q.setParameter("title", titleWildcard);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Journal> findJournalsWithPublisher(AbstractHuman publisher) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("JournalsWithPublisher");
        q.setParameter("publisher", publisher);
        return q.getResultList();
    }
    
    @Transactional(readOnly=false)
    public void saveJournal(Journal journal) throws DataAccessException {
        this.entityManager.persist(journal);
    }

    @Transactional(readOnly=false)
    public Journal updateJournal(Journal journal) throws DataAccessException {
        return this.entityManager.merge(journal);
    }
        
    @Transactional(readOnly=false)
    public void deleteJournal(Journal journal) throws DataAccessException {
        this.entityManager.remove(journal);
    }

}
