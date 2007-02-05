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
import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;

/**
 * Defines functionality to manipulate {@link SystemUser} entities
 * in the database.
 * 
 * @author jejking
 */
public interface SystemUserDao {

    /**
     * Loads single user by primary key.
     * 
     * @param id
     * @return the user, <code>null</code> if not found
     * @throws DataAccessException
     */
    public SystemUser findById(long id) throws DataAccessException;

    /**
     * Counts all user instances in the database.
     * 
     * @return count of users
     * @throws DataAccessException
     */
    public int countUsers() throws DataAccessException;

    /**
     * Saves <em>new</em> instance to the database.
     * 
     * @param user
     * @throws DataAccessException
     */
    public void save(SystemUser user) throws DataAccessException;

    /**
     * Updates the persistent storage of a system user instance.
     * A reference to it must already exist in the database.
     * 
     * @param user
     * @return the user
     * @throws DataAccessException
     */
    public SystemUser update(SystemUser user) throws DataAccessException;

    /**
     * Permanently delete the user from the database.
     * 
     * @param user
     * @throws DataAccessException
     */
    public void delete(SystemUser user) throws DataAccessException;

    /**
     * Loads the user matching the username parameter.
     * 
     * @param username
     * @return the user, <code>null</code> if no match found
     * @throws DataAccessException
     */
    public SystemUser findUserByUsername(String username)
	    throws DataAccessException;
    
    
    /**
     * Returns all known system users in the system.
     *
     * @return list of all known system users
     * @throws DataAccessException
     */
    public List<SystemUser> findAllSystemUsers() throws DataAccessException;
    
    /**
     * Returns "page" of list of all known users in the system.
     * 
     * @param pageNumber 
     * @param pageSize 
     * @return page of list of all known system users
     * @throws DataAccessException
     */
    public List<SystemUser> findAllSystemUsers(int pageNumber, int pageSize) throws DataAccessException;

}
