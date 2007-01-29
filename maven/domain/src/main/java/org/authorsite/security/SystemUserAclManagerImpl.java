/*
 * SystemUserAclManagerImpl.java
 *
 * Created on 28 January 2007, 14:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security;

import org.acegisecurity.acls.MutableAcl;
import org.acegisecurity.acls.MutableAclService;
import org.acegisecurity.acls.domain.BasePermission;
import org.acegisecurity.acls.objectidentity.ObjectIdentity;
import org.acegisecurity.acls.objectidentity.ObjectIdentityImpl;
import org.acegisecurity.acls.sid.PrincipalSid;
import org.apache.log4j.Logger;

/**
 *
 * @author jejking
 */
public class SystemUserAclManagerImpl implements SystemUserAclManager {
    
    
    private MutableAclService mutableAclService;
    
    public void addSystemUserChangePasswordPermission(SystemUser user) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(SystemUser.class, user.getId());
        MutableAcl acl = getMutableAclService().createAcl(objectIdentity);
        acl.insertAce(null, BasePermission.WRITE, new PrincipalSid(user.getUserName()), true);
        acl.insertAce(null, BasePermission.READ, new PrincipalSid(user.getUserName()), true);
        System.out.println("Added base permissions WRITE and READ to user: " + user);
        getMutableAclService().updateAcl(acl);
    }

    public void removeSystemUserChangePasswordPermission(SystemUser user) {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(SystemUser.class, user.getId());
        getMutableAclService().deleteAcl(objectIdentity, true);
    }

    public MutableAclService getMutableAclService() {
        return mutableAclService;
    }

    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }
    
    
    
}
