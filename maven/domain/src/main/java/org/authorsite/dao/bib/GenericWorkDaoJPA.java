/*
 * GenericWorkDaoJPA.java
 * 
 * Created on 20.09.2007, 17:52:04
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.dao.bib;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this. entityManager = entityManager;
    }
    
    @Transactional(readOnly=true)
    public int countWorks() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly=true)
    public AbstractWork findWorkById(long id) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly=true)
    public List<AbstractWork> findAllWorks() throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly=true)
    public List<AbstractWork> findAllWorks(int pageNumber, int pageSize) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly=true)
    public List<AbstractWork> findWorkByTitle(String title) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly=true)
    public List<AbstractWork> findWorkByTitleWildcard(String title) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Transactional(readOnly=true)
    public List<AbstractWork> findWorksWithProducer(AbstractHuman producer) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Transactional(readOnly=true)
    public List<AbstractWork> findWorksWithProducer(AbstractHuman producer, WorkProducerType workProducerType) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
