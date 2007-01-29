package org.authorsite.security;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.Individual;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface SystemUserManagementService {
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void createNewSystemUser(String username, String password, String name, String givenNames, String nameQualification) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void createSystemUserFromIndividual(String username, String password, Individual individual) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void disableSystemUser(SystemUser user) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void enableSystemUser(SystemUser user) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void deleteSystemUser(SystemUser user) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR", "ACL_USER_CHANGEPASSWORD"})
    public void changePassword(SystemUser user, String newPassword) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void grantAuthority(SystemUser user, Authority authority) throws DataAccessException;
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void removeAuthority(SystemUser user, Authority authority) throws DataAccessException;
    
}
