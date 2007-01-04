/*
 * IndividualDaoJPA.java
 *
 * Created on 04 January 2007, 22:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.domain.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 *
 * @author jejking
 */
public class IndividualDaoJPA extends JpaDaoSupport implements IndividualDao {
    
    /** Creates a new instance of IndividualDaoJPA */
    public IndividualDaoJPA() {
    }

    public Individual findById(long id) throws DataAccessException {
        return this.getJpaTemplate().find(Individual.class, id);
    }

    public void save(Individual i) throws DataAccessException {
        this.getJpaTemplate().persist(i);
    }

    public Individual update(Individual i) throws DataAccessException {
        return this.getJpaTemplate().merge(i);
    }

    public void delete(Individual i) throws DataAccessException {
        this.getJpaTemplate().remove(i);
    }

    public int countIndividuals() throws DataAccessException {
        Number n = (Number) this.getJpaTemplate().findByNamedQuery("IndividualCount").iterator().next();
        return n.intValue();
    }

    public List<Individual> findIndividualsByName(String name) throws DataAccessException {
        Map params = new HashMap();
        params.put("individualName", name);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("IndividualsByName", params);
    }

    public List<Individual> findIndividualsByNameWildcard(String name) throws DataAccessException {
        Map params = new HashMap();
        params.put("individualName", name);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("IndividualsByNameWildcard", params);
    }

    public List<Individual> findIndividualsByNameAndGivenNames(String name, String givenNames) throws DataAccessException {
        Map params = new HashMap();
        params.put("individualName", name);
        params.put("givenNames", givenNames);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("IndividualsByNameAndGivenNames", params);
    }

    public List<Individual> findIndividualsByNameAndGivenNamesWildcard(String name, String givenNames) throws DataAccessException {
        Map params = new HashMap();
        params.put("individualName", name);
        params.put("givenNames", givenNames);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("IndividualsByNameAndGivenNamesWildcard", params);
    }
    
}
