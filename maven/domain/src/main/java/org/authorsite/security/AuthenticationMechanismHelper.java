package org.authorsite.security;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.acegisecurity.providers.UsernamePasswordAuthenticationToken;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public class AuthenticationMechanismHelper {

    private AuthenticationManager authenticationManager;
    
    /** Creates a new instance of TestAuthenticationMechanism */
    public AuthenticationMechanismHelper() {
        super();
    }
    
    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }
    
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    public void logUserIn(String username, String password) throws UsernameNotFoundException, DataAccessException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = this.authenticationManager.authenticate(token);
        SecurityContextImpl securityContextImpl = new SecurityContextImpl();
        securityContextImpl.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContextImpl);
    }
    
}
