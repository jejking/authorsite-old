package org.authorsite.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 *
 * @author jejking
 */
public class SystemUserDaoJPA extends JpaDaoSupport implements SystemUserDao {
    
    /**
     * Creates a new instance of SystemUserDaoJPA
     */
    public SystemUserDaoJPA() {
        super();
    }
    
    public SystemUser findById(long id) throws DataAccessException {
        return this.getJpaTemplate().find(SystemUser.class, id);
    }

    public int countUsers() {
        Number n = (Number) this.getJpaTemplate().findByNamedQuery("SystemUserCount").iterator().next();
        return n.intValue();
    }

    public void save(SystemUser user) {
        this.getJpaTemplate().persist(user);
    }

    public SystemUser update(SystemUser user) {
        return this.getJpaTemplate().merge(user);
    }

    public void delete(SystemUser user) {
        this.getJpaTemplate().remove(user);
        this.getJpaTemplate().flush();
    }

    public SystemUser findUserByUsername(String username) {
        Map params = new HashMap();
        params.put("userName", username);
        List<SystemUser> results = this.getJpaTemplate().findByNamedQueryAndNamedParams("SystemUserByUserName", params);
        if ( results.isEmpty() ) {
            return null;
        }
        else {
            return results.iterator().next();
        }
    }

    
}
