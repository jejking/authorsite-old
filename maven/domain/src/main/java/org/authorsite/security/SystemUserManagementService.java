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
package org.authorsite.security;

import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;

/**
 * Core interface for manipulating System Users (and their ACLs) in the database.
 * 
 * @author jejking
 */
public interface SystemUserManagementService {

    /**
     * Returns lists of all system users.
     * 
     * @return list of all system users
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public List<SystemUser> findAllSystemUsers() throws DataAccessException;
    
    /**
     * Returns "page" of all system users.
     * 
     * @param pageNumber
     * @param pageSize
     * @return page of list of all system users
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public List<SystemUser> findAllSystemUsers(int pageNumber, int pageSize) throws DataAccessException;
    
    /**
     * Creates brand new System User and the associated Individual
     * in the database.
     * 
     * @param username
     * @param password
     * @param name (of the {@link Individual})
     * @param givenNames (of the {@link Individual})
     * @param nameQualification (of the {@link Individual})
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void createNewSystemUser(String username, String password, String name, String givenNames,
	    String nameQualification) throws DataAccessException;

    /**
     * Creates a system user from the specified individual.
     * 
     * <p>Note, the individual must already be persisted in the database.</p>
     * 
     * @param username
     * @param password
     * @param individual
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void createSystemUserFromIndividual(String username, String password, Individual individual)
	    throws DataAccessException;

    /**
     * Disables the system user. The user can no longer login
     * until the reenabled.
     * 
     * @param user
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void disableSystemUser(SystemUser user) throws DataAccessException;

    /**
     * Reenables a system user whose account has been disabled.
     * 
     * @param user
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void enableSystemUser(SystemUser user) throws DataAccessException;

    /**
     * Permanently deletes a system user. The {@link Individual} associated
     * with the user remains - and becomes available for editing by any 
     * user with the <code>ROLE_EDITOR</code>.
     * 
     * @param user
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void deleteSystemUser(SystemUser user) throws DataAccessException;

    /**
     * Changes the user's password.
     * 
     * @param user
     * @param newPassword
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ACL_USER_CHANGEPASSWORD" })
    public void changePassword(SystemUser user, String newPassword) throws DataAccessException;

    /**
     * Grants the specified authority to the user.
     * 
     * @param user
     * @param authority
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void grantAuthority(SystemUser user, Authority authority) throws DataAccessException;

    /**
     * Removes the specified authority from the user.
     * 
     * @param user
     * @param authority
     * @throws DataAccessException
     */
    @Secured( { "ROLE_ADMINISTRATOR" })
    public void removeAuthority(SystemUser user, Authority authority) throws DataAccessException;

}
