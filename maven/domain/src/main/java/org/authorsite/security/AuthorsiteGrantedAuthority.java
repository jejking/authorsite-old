package org.authorsite.security;

import org.acegisecurity.GrantedAuthority;

/**
 *
 * @author jejking
 */
public class AuthorsiteGrantedAuthority implements GrantedAuthority {
    
    private final Authority authority;
    
    /** Creates a new instance of AuthorsiteGrantedAuthority */
    public AuthorsiteGrantedAuthority(Authority authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return "ROLE_" + this.authority.getRoleName().toUpperCase();
    }
    
    @Override
    public String toString() {
        return this.getAuthority();
    }
    
}
