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
package org.authorsite.domain.service;

import org.authorsite.domain.Individual;
import org.authorsite.security.SystemUser;

/**
 * Defines functionality to manipulate persistent ACLs on 
 * {@link Individual} references.
 * 
 * @author jejking
 */
public interface IndividualAclManager {

    /**
     * Create brand new Individual ACL. The ACL
     * is constructed such that any <code>ROLE_EDITOR</code>
     * user can edit the Individual.
     * 
     * @param i
     */
    public void createIndividualAcl(Individual i);

    /**
     * Adds ACL permissions for <code>ROLE_EDITOR</code>
     * to the Individual.
     * 
     * <p>This would typically be used when the Individual
     * had been associated with a System User, but that System User 
     * is then deleted.</p>
     * 
     * @param i
     */
    public void addEditorRoleToIndividualAcl(Individual i);

    /**
     * Removes ACL permissions for <code>ROLE_EDITOR</code>
     * from the Individual.
     * 
     * <p>This would typically be used when the Individual
     * has been created "normally" but is then associated
     * with a newly created System User. In this case, only
     * that System User or <code>ROLE_ADMINISTRATOR</code>
     * has permission to edit the Individual.</p>
     * 
     * @param i
     */
    public void removeEditorRoleFromIndividualAcl(Individual i);

    /**
     * Assigns ACL permissions on the Individual to the
     * specified system user.
     * 
     * <p>This would typically be used to give a System User
     * permissions to edit the Individual associated with
     * the User.</p>
     * 
     * @param i
     * @param user
     */
    public void grantAdminOnIndividualToSystemUser(Individual i, SystemUser user);

    /**
     * Removes ACL permissions on the Individual from
     * the specified system user.
     * 
     * <p>Typically, this would be used when a System User
     * is deleted and thus permissions also need to 
     * be removed.</p>
     * 
     * @param i
     * @param user
     */
    public void removeAdminOnIndividualFromSystemUser(Individual i, SystemUser user);

    /**
     * Fully deletes ACL on an Individual. 
     * 
     * <p>This would be used when the Individual is deleted.</p> 
     * 
     * @param i
     */
    public void deleteIndividualAcl(Individual i);

}
