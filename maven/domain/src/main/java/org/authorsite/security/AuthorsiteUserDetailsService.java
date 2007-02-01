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

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.authorsite.dao.SystemUserDao;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of Acegi's {@link UserDetailsService} based on a
 * {@link SystemUserDao}.
 * 
 * @author jejking
 */
@Transactional
public class AuthorsiteUserDetailsService implements UserDetailsService {

    private SystemUserDao systemUserDao;

    /** Creates a new instance of AuthorsiteUserDetailsService */
    public AuthorsiteUserDetailsService() {
	super();
    }

    /**
         * @return wrapped dao
         */
    public SystemUserDao getSystemUserDao() {
	return this.systemUserDao;
    }

    /**
         * Inject system user dao.
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

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

	SystemUser user = this.systemUserDao.findUserByUsername(username);
	if (user == null) {
	    throw new UsernameNotFoundException("Could not find user: " + username);
	}
	if (user.getAuthorities() == null || user.getAuthorities().isEmpty()) {
	    throw new UsernameNotFoundException("User " + username + " could be loaded but has no authorities");
	}
	AuthorsiteUserDetails userDetails = new AuthorsiteUserDetails(user);
	return userDetails;
    }

}
