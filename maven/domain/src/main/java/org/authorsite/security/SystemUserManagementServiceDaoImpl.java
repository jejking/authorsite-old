package org.authorsite.security;

import java.util.HashSet;
import java.util.Set;
import org.acegisecurity.annotation.Secured;
import org.acegisecurity.providers.encoding.PasswordEncoder;
import org.authorsite.dao.IndividualDao;
import org.authorsite.dao.SystemUserDao;
import org.authorsite.domain.Individual;
import org.authorsite.domain.service.IndividualAclManager;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jejking
 */
@Transactional
public class SystemUserManagementServiceDaoImpl implements SystemUserManagementService {
    
    private IndividualDao individualDao;
    private SystemUserDao systemUserDao;
    private PasswordEncoder passwordEncoder;
    private SystemUserAclManager systemUserAclManager;
    private IndividualAclManager individualAclManager;
    
    /** Creates a new instance of SystemUserManagementServiceDaoImpl */
    public SystemUserManagementServiceDaoImpl() {
        super();
    }
        

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void createNewSystemUser(String username, String password, String name, String givenNames, String nameQualification) throws DataAccessException {
        Individual i = new Individual();
        i.setName(name);
        i.setGivenNames(givenNames);
        i.setNameQualification(nameQualification);
        individualDao.save(i);
        
        SystemUser user = new SystemUser(i, username, passwordEncoder.encodePassword(password, username));
        HashSet<Authority> authorities = new HashSet<Authority>();
        authorities.add(Authority.COMMUNITY);
        user.setAuthorities(authorities);
        this.systemUserDao.save(user);
        
        this.systemUserAclManager.addSystemUserChangePasswordPermission(user);
        this.individualAclManager.createIndividualAcl(i);
        this.individualAclManager.grantSystemUserAdminOnIndividual(i, user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void createSystemUserFromIndividual(String username, String password, Individual individual)  throws DataAccessException {
        SystemUser user = new SystemUser(individual, username, passwordEncoder.encodePassword(password, username));
        
        this.systemUserDao.save(user);
        this.systemUserAclManager.addSystemUserChangePasswordPermission(user);
        
        this.individualAclManager.removeEditorRoleFromIndividualAcl(individual);
        this.individualAclManager.grantSystemUserAdminOnIndividual(individual, user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void disableSystemUser(SystemUser user) throws DataAccessException {
        user.setEnabled(false);
        getSystemUserDao().update(user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void enableSystemUser(SystemUser user) throws DataAccessException {
        user.setEnabled(true);
        getSystemUserDao().update(user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void deleteSystemUser(SystemUser user) throws DataAccessException {
        
	// user loses permissions on his/her individual, generic Editor perms restored
        this.individualAclManager.removeSystemUserAdminOnIndividual(user.getIndividual(), user);
        this.individualAclManager.addEditorRoleToIndividualAcl(user.getIndividual());
        
        // do the actual deletion
        this.systemUserAclManager.removeSystemUserChangePasswordPermission(user);
        this.systemUserDao.delete(user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR",
            "ACL_USER_CHANGEPASSWORD"
        })
    public void changePassword(SystemUser user, String newPassword) throws DataAccessException {
        user.setPassword(passwordEncoder.encodePassword(newPassword, user.getUserName()));
        getSystemUserDao().update(user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void grantAuthority(SystemUser user, Authority authority) throws DataAccessException {
        Set<Authority> auths = user.getAuthorities();
        if (auths == null) {
            auths = new HashSet<Authority>();
            user.setAuthorities(auths);
        }
        auths.add(authority);
        getSystemUserDao().update(user);
    }

    @Secured( {
            "ROLE_ADMINISTRATOR"
        })
    public void removeAuthority(SystemUser user, Authority authority) throws DataAccessException {
        Set<Authority> auths = user.getAuthorities();
        if (auths != null) {
            auths.remove(authority);
            getSystemUserDao().update(user);
        }
            
    }

    public IndividualDao getIndividualDao() {
        return individualDao;
    }

    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }

    public SystemUserDao getSystemUserDao() {
        return systemUserDao;
    }

    public void setSystemUserDao(SystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public SystemUserAclManager getSystemUserAclManager() {
        return systemUserAclManager;
    }

    public void setSystemUserAclManager(SystemUserAclManager systemUserAclManager) {
        this.systemUserAclManager = systemUserAclManager;
    }


    /**
     * @return the individualAclManager
     */
    public IndividualAclManager getIndividualAclManager() {
        return this.individualAclManager;
    }


    /**
     * @param individualAclManager the individualAclManager to set
     */
    public void setIndividualAclManager(IndividualAclManager individualAclManager) {
        this.individualAclManager = individualAclManager;
    }
 
    
    
}
