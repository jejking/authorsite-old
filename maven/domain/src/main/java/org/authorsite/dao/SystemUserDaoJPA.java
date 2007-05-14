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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jejking
 */
@Transactional()
@Repository
public class SystemUserDaoJPA implements SystemUserDao {

    private EntityManager entityManager;

    /**
     * Creates a new instance of SystemUserDaoJPA
     */
    public SystemUserDaoJPA() {
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

    @Transactional(readOnly = true)
    public SystemUser findById(long id) throws DataAccessException {
        return this.entityManager.find(SystemUser.class, new Long(id));
    }

    @Transactional(readOnly = true)
    public int countUsers() {
        Number n = (Number) this.entityManager.createNamedQuery("SystemUserCount").getSingleResult();
	return n.intValue();
    }

    public void save(SystemUser user) {
        this.entityManager.persist(user);
    }

    public SystemUser update(SystemUser user) {
        return this.entityManager.merge(user);
    }

    public void delete(SystemUser user) {
        this.entityManager.remove(user);
//        this.entityManager.flush();
    }

    @Transactional(readOnly = true)
    @SuppressWarnings(value = { "unchecked" })
    public SystemUser findUserByUsername(String username) {
        Query q = this.entityManager.createNamedQuery("SystemUserByUserName");
        q.setParameter("userName", username);
        
        List resultList = q.getResultList();
        if ( resultList.isEmpty()) {
            return null;
        }
        else {
            return (SystemUser) resultList.iterator().next();
        }
    }

    @Transactional(readOnly = true)
    @SuppressWarnings(value = { "unchecked" })
    public List<SystemUser> findAllSystemUsers() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllSystemUsers");
        return q.getResultList();
    }

    @Transactional(readOnly = true)
    @SuppressWarnings(value = { "unchecked" })
    public List<SystemUser> findAllSystemUsers(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllSystemUsers");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

}
