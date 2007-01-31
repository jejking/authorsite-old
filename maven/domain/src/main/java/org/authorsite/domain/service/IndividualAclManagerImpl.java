package org.authorsite.domain.service;

import java.security.acl.AclEntry;

import org.acegisecurity.acls.AccessControlEntry;
import org.acegisecurity.acls.Acl;
import org.acegisecurity.acls.MutableAcl;
import org.acegisecurity.acls.MutableAclService;
import org.acegisecurity.acls.domain.AclImpl;
import org.acegisecurity.acls.domain.BasePermission;
import org.acegisecurity.acls.objectidentity.ObjectIdentity;
import org.acegisecurity.acls.objectidentity.ObjectIdentityImpl;
import org.acegisecurity.acls.sid.GrantedAuthoritySid;
import org.acegisecurity.acls.sid.PrincipalSid;
import org.authorsite.domain.Individual;
import org.authorsite.security.SystemUser;

public class IndividualAclManagerImpl implements IndividualAclManager {

    private MutableAclService mutableAclService;
    
    /**
     * @return the mutableAclService
     */
    public MutableAclService getMutableAclService() {
        return this.mutableAclService;
    }

    /**
     * @param mutableAclService the mutableAclService to set
     */
    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    public void addEditorRoleToIndividualAcl(Individual i) {
	ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, i.getId());
	AclImpl acl = getAcl(oid);
	acl.insertAce(null, BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_EDITOR"), true);
        acl.insertAce(null, BasePermission.WRITE, new GrantedAuthoritySid("ROLE_EDITOR"), true);
	this.mutableAclService.updateAcl(acl);
    }


    public void createIndividualAcl(Individual i) {
	ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, i.getId());
	MutableAcl acl = this.mutableAclService.createAcl(oid);
	acl.insertAce(null, BasePermission.ADMINISTRATION, new GrantedAuthoritySid("ROLE_EDITOR"), true);
        acl.insertAce(null, BasePermission.WRITE, new GrantedAuthoritySid("ROLE_EDITOR"), true);
	this.mutableAclService.updateAcl(acl);
    }

    public void deleteIndividualAcl(Individual i) {
	ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, i.getId());
	this.mutableAclService.deleteAcl(oid, true);
    }

    public void grantSystemUserAdminOnIndividual(Individual i, SystemUser user) {
	ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, i.getId());
	AclImpl acl = getAcl(oid);
	acl.insertAce(null, BasePermission.WRITE, new PrincipalSid(user.getUserName()), true);
	this.mutableAclService.updateAcl(acl);
    }

    public void removeEditorRoleFromIndividualAcl(Individual i) {
	ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, i.getId());
	AclImpl acl = getAcl(oid);
	for (AccessControlEntry entry : acl.getEntries()) {
	    if (entry.getSid().equals(new GrantedAuthoritySid("ROLE_EDITOR")))
	    {
		acl.deleteAce(entry.getId());
	    }
	}
	this.mutableAclService.updateAcl(acl);
    }
    

    public void removeSystemUserAdminOnIndividual(Individual i, SystemUser user) {
	ObjectIdentity oid = new ObjectIdentityImpl(Individual.class, i.getId());
	AclImpl acl = getAcl(oid);
	for (AccessControlEntry entry : acl.getEntries()) {
	    if (entry.getSid().equals(new PrincipalSid(user.getUserName())))
	    {
		acl.deleteAce(entry.getId());
	    }
	}
	this.mutableAclService.updateAcl(acl);
    }
    
    
    private AclImpl getAcl(ObjectIdentity oid) {
	// TODO the cast is a nasty hack
	AclImpl acl = (AclImpl) this.mutableAclService.readAclById(oid);
	return acl;
    }



}
