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

import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of individual DAO based on JPA.
 * 
 *
 * @author jejking
 */
@Transactional
@Repository
public class IndividualDaoJPA implements IndividualDao {

    private EntityManager entityManager;
    
    /** Creates a new instance of IndividualDaoJPA */
    public IndividualDaoJPA() {
	super();
    }
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public Individual findById(long id) throws DataAccessException {
	// return this.getJpaTemplate().find(Individual.class, new Long(id));
        return this.entityManager.find(Individual.class, new Long(id));
    }

    public void save(Individual i) throws DataAccessException {
	//this.getJpaTemplate().persist(i);
        this.entityManager.persist(i);
    }

    public Individual update(Individual i) throws DataAccessException {
	//return this.getJpaTemplate().merge(i);
        return this.entityManager.merge(i);
    }

    public void delete(Individual i) throws DataAccessException {
	//this.getJpaTemplate().remove(i);
        this.entityManager.remove(i);
    }

    @Transactional(readOnly = true)
    public int countIndividuals() throws DataAccessException {
	/*Number n = (Number) this.getJpaTemplate().findByNamedQuery(
		"IndividualCount").iterator().next();
	return n.intValue();
         **/
        Query q = this.entityManager.createNamedQuery("IndividualCount");
        Number n = (Number) q.getSingleResult();
        return n.intValue();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByName(String name)
	    throws DataAccessException {
	/*Map<String, String> params = new HashMap<String, String>();
	params.put("individualName", name);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"IndividualsByName", params);*/
        Query q = this.entityManager.createNamedQuery("IndividualsByName");
        q.setParameter("individualName", name);
        return q.getResultList();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByNameWildcard(String name)
	    throws DataAccessException {
	/*Map<String, String> params = new HashMap<String, String>();
	params.put("individualName", name);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"IndividualsByNameWildcard", params);*/
        Query q = this.entityManager.createNamedQuery("IndividualsByNameWildcard");
        q.setParameter("individualName", name);
        return q.getResultList();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByNameAndGivenNames(String name,
	    String givenNames) throws DataAccessException {
	/*Map<String, String> params = new HashMap<String, String>();
	params.put("individualName", name);
	params.put("givenNames", givenNames);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"IndividualsByNameAndGivenNames", params);*/
        Query q = this.entityManager.createNamedQuery("IndividualsByNameAndGivenNames");
        q.setParameter("individualName", name);
        q.setParameter("givenNames", givenNames);
        return q.getResultList();
    }

    @SuppressWarnings(value = { "unchecked" })
    @Transactional(readOnly = true)
    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(
	    String name, String givenNames) throws DataAccessException {
	Map<String, String> params = new HashMap<String, String>();
	/*params.put("individualName", name);
	params.put("givenNames", givenNames);
	return this.getJpaTemplate().findByNamedQueryAndNamedParams(
		"IndividualsByNameAndGivenNamesWildcard", params);*/
        Query q = this.entityManager.createNamedQuery("IndividualsByNameAndGivenNamesWildcard");
        q.setParameter("individualName", name);
        q.setParameter("givenNames", givenNames);
        return q.getResultList();
                
    }

}
