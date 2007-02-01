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

import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jejking
 */
@Transactional()
public class SystemUserDaoJPA extends JpaDaoSupport implements SystemUserDao {

    /**
     * Creates a new instance of SystemUserDaoJPA
     */
    public SystemUserDaoJPA() {
	super();
    }

    @Transactional(readOnly = true)
    public SystemUser findById(long id) throws DataAccessException {
	return this.getJpaTemplate().find(SystemUser.class, new Long(id));
    }

    public int countUsers() {
	Number n = (Number) this.getJpaTemplate().findByNamedQuery(
		"SystemUserCount").iterator().next();
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

    @Transactional(readOnly = true)
    @SuppressWarnings(value = { "unchecked" })
    public SystemUser findUserByUsername(String username) {
	Map<String, String> params = new HashMap<String, String>();
	params.put("userName", username);
	List<SystemUser> results = this.getJpaTemplate()
		.findByNamedQueryAndNamedParams("SystemUserByUserName", params);
	if (results.isEmpty()) {
	    return null;
	} else {
	    return results.iterator().next();
	}
    }

}
