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

import org.acegisecurity.acls.MutableAcl;
import org.acegisecurity.acls.MutableAclService;
import org.acegisecurity.acls.domain.BasePermission;
import org.acegisecurity.acls.objectidentity.ObjectIdentity;
import org.acegisecurity.acls.objectidentity.ObjectIdentityImpl;
import org.acegisecurity.acls.sid.PrincipalSid;

/**
 *
 * @author jejking
 */
public class SystemUserAclManagerImpl implements SystemUserAclManager {
    
    
    private MutableAclService mutableAclService;
    
    public void addSystemUserChangePasswordPermission(SystemUser user) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(SystemUser.class, new Long(user.getId()));
        MutableAcl acl = getMutableAclService().createAcl(objectIdentity);
        acl.insertAce(null, BasePermission.WRITE, new PrincipalSid(user.getUserName()), true);
        acl.insertAce(null, BasePermission.READ, new PrincipalSid(user.getUserName()), true);
        System.out.println("Added base permissions WRITE and READ to user: " + user);
        getMutableAclService().updateAcl(acl);
    }

    public void removeSystemUserChangePasswordPermission(SystemUser user) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(SystemUser.class, new Long(user.getId()));
        getMutableAclService().deleteAcl(objectIdentity, true);
    }

    /**
     * @return the mutable acl service.
     */
    public MutableAclService getMutableAclService() {
        return this.mutableAclService;
    }

    /**
     * Injects the mutable acl service.
     * 
     * <p>For IoC use.</p>
     * 
     * @param mutableAclService
     */
    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }
    
    
    
}
