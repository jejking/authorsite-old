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

package org.authorsite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.domain.Collective;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of collective DAO based on JPA.
 *
 * @author jejking
 */
@Transactional()
@Repository
public class CollectiveDaoJPA implements CollectiveDao {

    private EntityManager entityManager;
    
    /** 
     * Creates a new instance of CollectiveDaoJPA.
     */
    public CollectiveDaoJPA() {
	super();
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public Collective findById(long id) throws DataAccessException {
        return this.entityManager.find( Collective.class, new Long(id));
    }

    public void save(final Collective c) throws DataAccessException {
        this.entityManager.persist(c);
    }

    public Collective update(Collective c) throws DataAccessException {
        return this.entityManager.merge(c);
    }

    public void delete(Collective c) throws DataAccessException {
        this.entityManager.remove(c);
    }

    @Transactional(readOnly = true)
    public int countCollectives() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("CollectiveCount").getSingleResult();
	return n.intValue();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByName(String name)
	    throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("CollectivesByName");
        q.setParameter("collectiveName", name);
        return q.getResultList();
        
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByNameWildcard(String name)
	    throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("CollectivesByNameWildcard");
        q.setParameter("collectiveName", name);
        return q.getResultList();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByPlace(String placeName)
	    throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("CollectivesByPlace");
        q.setParameter("placeName", placeName);
        return q.getResultList();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findCollectivesByPlaceWildcard(String placeName)
	    throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("CollectivesByPlaceWildcard");
        q.setParameter("placeName", placeName);
        return q.getResultList();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findAllCollectives() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllCollectives");
        return q.getResultList();
    }
    
    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Collective> findAllCollectives(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllCollectives");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

}
