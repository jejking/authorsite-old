package org.authorsite.security;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.authorsite.dao.SystemUserDao;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public class AuthorsiteUserDetailsService implements UserDetailsService {
    
    private SystemUserDao systemUserDao;
    
    
    /** Creates a new instance of AuthorsiteUserDetailsService */
    public AuthorsiteUserDetailsService() {
        super();
    }

    public SystemUserDao getSystemUserDao()  {
        return this.systemUserDao;
    }
    
    public void setSystemUserDao(SystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
    }
    
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        
        SystemUser user = systemUserDao.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }
        if ( user.getAuthorities() == null || user.getAuthorities().isEmpty() ) {
            throw new UsernameNotFoundException("User " + username + " could be loaded but has no authorities");
        }
        AuthorsiteUserDetails userDetails = new AuthorsiteUserDetails(user);
        return userDetails;
    }
    
}
