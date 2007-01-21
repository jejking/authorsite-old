package org.authorsite.security;

import java.util.Set;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

/**
 *
 * @author jejking
 */
public class AuthorsiteUserDetails implements UserDetails {

    private SystemUser systemUser;
    private GrantedAuthority[] grantedAuthorities;
    
    /** Creates a new instance of AuthorsiteUserDetails */
    public AuthorsiteUserDetails(SystemUser systemUser) {
        this.systemUser = systemUser;
        
        Set<Authority> userAuthorities = systemUser.getAuthorities();
        this.grantedAuthorities = new GrantedAuthority[userAuthorities.size()];
        int i = 0;
        for (Authority userAuth : userAuthorities) {
            grantedAuthorities[i] = new AuthorsiteGrantedAuthority(userAuth);
            i++;
        }
    }

    public GrantedAuthority[] getAuthorities() {
        return grantedAuthorities;
    }

    public String getPassword() {
        return this.systemUser.getPassword();
    }

    public String getUsername() {
        return this.systemUser.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.systemUser.isEnabled();
    }
    
    public SystemUser getSystemUser() {
        return this.systemUser;
    }
    
}
