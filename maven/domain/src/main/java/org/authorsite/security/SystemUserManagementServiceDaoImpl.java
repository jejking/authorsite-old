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
 * Implementation of {@link SystemUserManagementService}.
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

    /**
         * Default constructor.
         */
    public SystemUserManagementServiceDaoImpl() {
	super();
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void createNewSystemUser(String username, String password, String name, String givenNames,
	    String nameQualification) throws DataAccessException {
	Individual i = new Individual();
	i.setName(name);
	i.setGivenNames(givenNames);
	i.setNameQualification(nameQualification);
	this.individualDao.save(i);

	SystemUser user = new SystemUser(i, username, this.passwordEncoder.encodePassword(password, username));
	HashSet<Authority> authorities = new HashSet<Authority>();
	authorities.add(Authority.COMMUNITY);
	user.setAuthorities(authorities);
	this.systemUserDao.save(user);

	this.systemUserAclManager.addSystemUserChangePasswordPermission(user);
	this.individualAclManager.createIndividualAcl(i);
	this.individualAclManager.grantSystemUserAdminOnIndividual(i, user);
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void createSystemUserFromIndividual(String username, String password, Individual individual)
	    throws DataAccessException {
	SystemUser user = new SystemUser(individual, username, this.passwordEncoder.encodePassword(password, username));
	HashSet<Authority> authorities = new HashSet<Authority>();
	authorities.add(Authority.COMMUNITY);
	user.setAuthorities(authorities);

	this.systemUserDao.save(user);
	this.systemUserAclManager.addSystemUserChangePasswordPermission(user);

	this.individualAclManager.removeEditorRoleFromIndividualAcl(individual);
	this.individualAclManager.grantSystemUserAdminOnIndividual(individual, user);
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void disableSystemUser(SystemUser user) throws DataAccessException {
	user.setEnabled(false);
	getSystemUserDao().update(user);
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void enableSystemUser(SystemUser user) throws DataAccessException {
	user.setEnabled(true);
	getSystemUserDao().update(user);
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void deleteSystemUser(SystemUser user) throws DataAccessException {

	// user loses permissions on his/her individual, generic Editor perms
        // restored
	this.individualAclManager.removeSystemUserAdminOnIndividual(user.getIndividual(), user);
	this.individualAclManager.addEditorRoleToIndividualAcl(user.getIndividual());

	// do the actual deletion
	this.systemUserAclManager.removeSystemUserChangePasswordPermission(user);
	this.systemUserDao.delete(user);
    }

    @Secured( { "ROLE_ADMINISTRATOR", "ACL_USER_CHANGEPASSWORD" })
    public void changePassword(SystemUser user, String newPassword) throws DataAccessException {
	user.setPassword(this.passwordEncoder.encodePassword(newPassword, user.getUserName()));
	getSystemUserDao().update(user);
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void grantAuthority(SystemUser user, Authority authority) throws DataAccessException {
	Set<Authority> auths = user.getAuthorities();
	if (auths == null) {
	    auths = new HashSet<Authority>();
	    user.setAuthorities(auths);
	}
	auths.add(authority);
	getSystemUserDao().update(user);
    }

    @Secured( { "ROLE_ADMINISTRATOR" })
    public void removeAuthority(SystemUser user, Authority authority) throws DataAccessException {
	Set<Authority> auths = user.getAuthorities();
	if (auths != null) {
	    auths.remove(authority);
	    getSystemUserDao().update(user);
	}

    }

    /**
         * @return individual dao
         */
    public IndividualDao getIndividualDao() {
	return this.individualDao;
    }

    /**
         * Injects individual dao.
         * 
         * <p>
         * For IoC use.
         * </p>
         * 
         * @param individualDao
         */
    public void setIndividualDao(IndividualDao individualDao) {
	this.individualDao = individualDao;
    }

    /**
         * @return system user dao
         */
    public SystemUserDao getSystemUserDao() {
	return this.systemUserDao;
    }

    /**
         * Injects reference to system user dao.
         * 
         * <p>
         * For IoC use.
         * </p>
         * 
         * @param systemUserDao
         */
    public void setSystemUserDao(SystemUserDao systemUserDao) {
	this.systemUserDao = systemUserDao;
    }

    /**
         * @return password encode
         */
    public PasswordEncoder getPasswordEncoder() {
	return this.passwordEncoder;
    }

    /**
         * Injects password encoder.
         * 
         * <p>
         * For IoC use.
         * </p>
         * 
         * @param passwordEncoder
         */
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
	this.passwordEncoder = passwordEncoder;
    }

    /**
         * @return system user acl manager
         */
    public SystemUserAclManager getSystemUserAclManager() {
	return this.systemUserAclManager;
    }

    /**
         * Injects system user acl manager.
         * 
         * <p>
         * For IoC use.
         * </p>
         * 
         * @param systemUserAclManager
         */
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
         * @param individualAclManager
         *                the individualAclManager to set
         */
    public void setIndividualAclManager(IndividualAclManager individualAclManager) {
	this.individualAclManager = individualAclManager;
    }

}
