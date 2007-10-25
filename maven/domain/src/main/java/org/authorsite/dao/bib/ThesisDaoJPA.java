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

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ThesisDaoJPA implements ThesisDao {

    private EntityManager entityManager;
    
    /**
     * Default constructor.
     */
    public ThesisDaoJPA() {
        super();
    }
    
    /**
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Transactional(readOnly=true)
    public int countTheses() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("ThesisCount").getSingleResult();
        return n.intValue();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findAllTheses() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllTheses");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findAllTheses(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllTheses");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    public Thesis findById(long id) throws DataAccessException {
        return this.entityManager.find(Thesis.class, new Long(id));
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesByTitleWildcard(String titleWildcard) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesByTitleWildcard");
        q.setParameter("title", titleWildcard);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesWithAuthor(Individual author) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesWithAuthor");
        q.setParameter("author", author);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Thesis> findThesesWithAwardingBody(Collective awardingBody) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ThesesWithAwardingBody");
        q.setParameter("awardingBody", awardingBody);
        return q.getResultList();
    }
    
    @Transactional(readOnly=false)
    public void saveThesis(Thesis thesis) throws DataAccessException {
        this.entityManager.persist(thesis);
    }
    
    @Transactional(readOnly=false)
    public Thesis updateThesis(Thesis thesis) throws DataAccessException {
        return this.entityManager.merge(thesis);
    }
    
    @Transactional(readOnly=false)
    public void deleteThesis(Thesis thesis) throws DataAccessException {
       this.entityManager.remove(thesis);
    }

}
