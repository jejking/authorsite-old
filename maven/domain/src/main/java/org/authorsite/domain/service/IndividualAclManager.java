package org.authorsite.domain.service;

import org.authorsite.domain.Individual;
import org.authorsite.security.SystemUser;

public interface IndividualAclManager {

    public void createIndividualAcl(Individual i);
    
    public void addEditorRoleToIndividualAcl(Individual i);
    
    public void removeEditorRoleFromIndividualAcl(Individual i);
    
    public void grantSystemUserAdminOnIndividual(Individual i, SystemUser user);
    
    public void removeSystemUserAdminOnIndividual(Individual i, SystemUser user);
    
    public void deleteIndividualAcl(Individual i);
    
}
