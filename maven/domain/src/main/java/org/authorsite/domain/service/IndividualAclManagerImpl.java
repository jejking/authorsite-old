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

import org.acegisecurity.acls.AccessControlEntry;
import org.acegisecurity.acls.MutableAcl;
import org.acegisecurity.acls.MutableAclService;
import org.acegisecurity.acls.domain.AclImpl;
import org.acegisecurity.acls.domain.BasePermission;
import org.acegisecurity.acls.objectidentity.ObjectIdentity;
import org.acegisecurity.acls.objectidentity.ObjectIdentityImpl;
import org.acegisecurity.acls.sid.GrantedAuthoritySid;
import org.acegisecurity.acls.sid.PrincipalSid;
import org.apache.log4j.Logger;
import org.authorsite.domain.Individual;
import org.authorsite.security.SystemUser;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation based on Acegi Security.
 * 
 * @author jking
 */
@Transactional()
public class IndividualAclManagerImpl implements IndividualAclManager {

    private static final Logger LOGGER = Logger.getLogger(IndividualAclManagerImpl.class);
    private MutableAclService mutableAclService;

    /**
     * @return the mutableAclService
     */
    public MutableAclService getMutableAclService() {
        return this.mutableAclService;
    }

    /**
     * @param mutableAclService
     *            the mutableAclService to set
     */
    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    public void addEditorRoleToIndividualAcl(Individual i) {
        ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, new Long(i.getId()));
        AclImpl acl = getAcl(oid);
        acl.insertAce(null, BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_EDITOR"), true);
        //acl.insertAce(null, BasePermission.WRITE, new GrantedAuthoritySid("ROLE_EDITOR"), true);
        this.mutableAclService.updateAcl(acl);
        LOGGER.info("Added Admin permissions for the Editor Role to individual " + i);
    }

    public void createIndividualAcl(Individual i) {
        ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, new Long(i.getId()));
        MutableAcl acl = this.mutableAclService.createAcl(oid);
        acl.insertAce(null, BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_EDITOR"), true);
        this.mutableAclService.updateAcl(acl);
        LOGGER.info("Created new ACL with Admin permissions for the Editor Role on individual " + i);
    }

    public void deleteIndividualAcl(Individual i) {
        ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, new Long(i.getId()));
        this.mutableAclService.deleteAcl(oid, true);
        LOGGER.info("Deleted all ACL Entries for individual "+ i );
    }

    public void grantAdminOnIndividualToSystemUser(Individual i, SystemUser user) {
        ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, new Long(i.getId()));
        AclImpl acl = getAcl(oid);
        acl.insertAce(null, BasePermission.WRITE, new PrincipalSid(user.getUserName()), true);
        this.mutableAclService.updateAcl(acl);
        LOGGER.info("Granted system user " + user + " Write permissions on individual " + i);
    }

    public void removeEditorRoleFromIndividualAcl(Individual i) {
        ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, new Long(i.getId()));
        AclImpl acl = getAcl(oid);
        for (AccessControlEntry entry : acl.getEntries()) {
            if (entry.getSid().equals(new GrantedAuthoritySid("ROLE_EDITOR"))) {
                acl.deleteAce(entry.getId());
            }
        }
        this.mutableAclService.updateAcl(acl);
        LOGGER.info("Removed all permissions for the Editor Role from individual " + i);
    }

    public void removeAdminOnIndividualFromSystemUser(Individual i, SystemUser user) {
        ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, new Long(i.getId()));
        AclImpl acl = getAcl(oid);
        for (AccessControlEntry entry : acl.getEntries()) {
            if (entry.getSid().equals(new PrincipalSid(user.getUserName()))) {
                acl.deleteAce(entry.getId());
            }
        }
        this.mutableAclService.updateAcl(acl);
        LOGGER.info("Removed the permissions of system user " + user + " on individual " + i);
    }

    private AclImpl getAcl(ObjectIdentity oid) {
        // TODO the cast is a nasty hack
        AclImpl acl = (AclImpl) this.mutableAclService.readAclById(oid);
        return acl;
    }

}
