/*
 * CollectiveDaoJPA.java
 *
 * Created on 01 January 2007, 16:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import org.authorsite.domain.Collective;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jejking
 */
@Transactional()
public class CollectiveDaoJPA extends JpaDaoSupport implements CollectiveDao {
    
    /** Creates a new instance of CollectiveDaoJPA */
    public CollectiveDaoJPA() {
        super();
    }
    
    public Collective findById(long id) throws DataAccessException {
        return this.getJpaTemplate().find(Collective.class, id);
    }

    public void save(final Collective c) throws DataAccessException {
        this.getJpaTemplate().persist(c);
    }

    public Collective update(Collective c) throws DataAccessException {
        return this.getJpaTemplate().merge(c);
    }

    public void delete(Collective c) throws DataAccessException {
        this.getJpaTemplate().remove(c);
    }

    public int countCollectives() throws DataAccessException {
        Number n = (Number) this.getJpaTemplate().findByNamedQuery("CollectiveCount").iterator().next();
        return n.intValue();
    }

    public List<Collective> findCollectivesByName(String name) throws DataAccessException {
        Map params = new HashMap();
        params.put("collectiveName", name);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("CollectivesByName", params);
    }

    public List<Collective> findCollectivesByNameWildcard(String name) throws DataAccessException {
        Map params = new HashMap();
        params.put("collectiveName", name);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("CollectivesByNameWildcard", params);
    }

    public List<Collective> findCollectivesByPlace(String placeName) throws DataAccessException {
        Map params = new HashMap();
        params.put("placeName", placeName);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("CollectivesByPlace", params);
    }

    public List<Collective> findCollectivesByPlaceWildcard(String placeName) throws DataAccessException {
        Map params = new HashMap();
        params.put("placeName", placeName);
        return this.getJpaTemplate().findByNamedQueryAndNamedParams("CollectivesByPlaceWildcard", params);
    }
    
}
