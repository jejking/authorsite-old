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
 * Enumeration of privileges in the authorsite system.
 * 
 * @author jejking
 */
public enum Authority {

    /**
     * Super user.
     */
    ADMIN("Administrator"),
    /**
     * Editor. Will have privileges to edit almost all bibliographic content.
     */
    EDITOR("Editor"),
    /**
     * Community member. 
     */
    COMMUNITY("Community");

    private String roleName;

    /** Creates a new instance of Authority */
    Authority(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return this.roleName;
    }

    /**
     * @return role name
     */
    public String getRoleName() {
        return this.roleName;
    }

}
