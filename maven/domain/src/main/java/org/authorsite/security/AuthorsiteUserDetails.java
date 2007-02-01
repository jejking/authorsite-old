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

import java.util.Set;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;

/**
 * Implementation of Acegi's {@link UserDetails} which wraps
 * an instance of {@link SystemUser}.
 * 
 * @author jejking
 */
public class AuthorsiteUserDetails implements UserDetails {

    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = -9027982891920221561L;

    private SystemUser systemUser;

    private GrantedAuthority[] grantedAuthorities;

    /** 
     * Creates a new instance of AuthorsiteUserDetails
     *  
     * @param systemUser 
     */
    public AuthorsiteUserDetails(SystemUser systemUser) {
	this.systemUser = systemUser;

	Set<Authority> userAuthorities = systemUser.getAuthorities();
	this.grantedAuthorities = new GrantedAuthority[userAuthorities.size()];
	int i = 0;
	for (Authority userAuth : userAuthorities) {
	    this.grantedAuthorities[i] = new AuthorsiteGrantedAuthority(userAuth);
	    i++;
	}
    }

    public GrantedAuthority[] getAuthorities() {
	return this.grantedAuthorities;
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

    /**
     * @return the internal system user
     */
    public SystemUser getSystemUser() {
	return this.systemUser;
    }

}
