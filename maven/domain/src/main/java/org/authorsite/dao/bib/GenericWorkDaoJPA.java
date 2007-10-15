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
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.WorkProducerType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jking
 */
@Transactional()
@Repository
public class GenericWorkDaoJPA implements GenericWorkDao {

    private EntityManager entityManager;

    public GenericWorkDaoJPA() {
        super();
    }
        
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

    
    @Transactional(readOnly=true)
    public int countWorks() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("AbstractWorkCount").getSingleResult();
        return n.intValue();
    }

    @Transactional(readOnly=true)
    public AbstractWork findWorkById(long id) throws DataAccessException {
        return this.entityManager.find(AbstractWork.class, new Long(id));
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findAllWorks() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllAbstractWorks");
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findAllWorks(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllAbstractWorks");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorkByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorkByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorkByTitleWildcard(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorkByTitleWildcard");
        q.setParameter("title", title);
        return q.getResultList();
    }
    
    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorksWithProducer(AbstractHuman producer) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorksWithProducer");
        q.setParameter("workProducer", producer);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorksWithProducerOfType(AbstractHuman producer, WorkProducerType workProducerType) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorksWithProducerOfType");
        q.setParameter("workProducer", producer);
        q.setParameter("workProducerType", workProducerType);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorksBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorksBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorksAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorksAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    @SuppressWarnings("unchecked")
    public List<AbstractWork> findWorksBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AbstractWorksBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

}
