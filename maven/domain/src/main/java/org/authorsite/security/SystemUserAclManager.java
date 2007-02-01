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

/**
 * Defines functionality to manipulate persistent ACLs on 
 * {@link SystemUser} references.
 * 
 * @author jejking
 */
public interface SystemUserAclManager {

    /**
     * Adds permission for a user to change their own password.
     * 
     * <p>Typically, this would be called when creating the user.</p>
     * 
     * @param user
     */
    public void addSystemUserChangePasswordPermission(SystemUser user);

    /**
     * Removes permission for a user to change their own password.
     * 
     * <p>Typically, this would be called when deleting the user.</p>
     * 
     * @param user
     */
    public void removeSystemUserChangePasswordPermission(SystemUser user);

}
