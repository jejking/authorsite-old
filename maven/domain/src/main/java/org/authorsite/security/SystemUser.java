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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import javax.persistence.Table;
import org.authorsite.domain.AbstractEntry;
import org.authorsite.domain.Individual;
import org.hibernate.annotations.CollectionOfElements;

/**
 * A user of the authorsite system.
 *
 * <p>A User has a set of {@link Authority} instances defining what
 * he or she is allowed to do.</p>
 * 
 * <p>Personal information about a User is encapsulated in a related
 * {@link Individual} references. This can continue to exist regardless
 * of whether the User is deleted from the system or not.</p>
 * 
 * <p>Additionally, it allows us to consider a User <em>as</em> an Individual
 * without having to extend the class, allowing tighter security, better 
 * logic in generic types and, essentially, a decoupling between system
 * security concerns on the one hand and the management of bibliographic
 * and other metadata on the other.</p>
 * 
 * @author jejking
 */
@Entity
@NamedQueries( {
	@NamedQuery(name = "SystemUserCount", query = "select count(su) from SystemUser su"),
	@NamedQuery(name = "SystemUserByUserName", query = "select su from SystemUser su where su.userName = :userName"),
        @NamedQuery(name = "AllSystemUsers", query = "select su from SystemUser su order by id asc") })
@Table(name="systemuser")
public class SystemUser extends AbstractEntry {

    /**
     * Generated by Eclipse. 
     */
    private static final long serialVersionUID = -4583776157554526526L;

    private String userName;

    private String password;

    private boolean isEnabled;

    private Set<Authority> authorities;

    private Individual individual;

    /**
     * Creates a new instance of SystemUser
     */
    public SystemUser() {
        super();
    }

    /**
     * Constructs user with the given name and password.
     * 
     * <p>Note, an {@link Individual} reference must also be 
     * supplied.</p>
     * 
     * @param userName
     * @param password
     */
    public SystemUser(String userName, String password) {
    	this.setUserName(userName);
    	this.setPassword(password);
    	this.setEnabled(true);
    }

    /**
     * Constructs user with reference to the given Individual
     * and username and password pair.
     * 
     * @param individual
     * @param userName
     * @param password
     */
    public SystemUser(Individual individual, String userName, String password) {
    	this(userName, password);
    	this.individual = individual;
    }

    /**
     * Gets the username.
     * 
     * @return username
     */
    @Column(unique = true, nullable = false, updatable = false)
    public String getUserName() {
        return this.userName;
    }

    /**
     * Sets the username.
     * 
     * @param userName
     */
    public void setUserName(String userName) {
            this.userName = userName;
    }

    /**
     * Gets the password.
     * 
     * @return password
     */
    @Column(nullable = false)
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password.
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns whether the user is enabled or not.
     * 
     * @return enabled flag
     */
    @Column(nullable = false)
    public boolean isEnabled() {
        return this.isEnabled;
    }

    /**
     * Sets the enabled flag.
     * 
     * @param isEnabled
     */
    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * Returns the authorities granted to the User.
     * 
     * @return set of authorities.
     */
    @CollectionOfElements(fetch = FetchType.EAGER)
    @JoinTable(name = "systemuser_authorities", joinColumns = @JoinColumn(name = "SystemUser_id"))
    public Set<Authority> getAuthorities() {
        if (this.authorities == null) {
            return new HashSet<Authority>();
        } else {
            return this.authorities;
        }
    }

    protected void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    /**
     * @return the individual associated with the User
     */
    @OneToOne(optional = false, fetch=FetchType.EAGER)
    public Individual getIndividual() {
        return this.individual;
    }

    /**
     * Sets the individual to associate with the User.
     * 
     * @param individual
     */
    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((this.authorities == null) ? 0 : this.authorities.hashCode());
        result = PRIME * result + ((this.individual == null) ? 0 : this.individual.hashCode());
        result = PRIME * result + (this.isEnabled ? 1231 : 1237);
        result = PRIME * result + ((this.password == null) ? 0 : this.password.hashCode());
        result = PRIME * result + ((this.userName == null) ? 0 : this.userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (! (obj instanceof SystemUser ))
            return false;
        final SystemUser other = (SystemUser) obj;
        if (this.authorities == null) {
            if (other.authorities != null)
                return false;
        } else if (!this.authorities.equals(other.authorities))
            return false;
        if (this.individual == null) {
            if (other.individual != null)
                return false;
        } else if (!this.individual.equals(other.individual))
            return false;
        if (this.isEnabled != other.isEnabled)
            return false;
        if (this.password == null) {
            if (other.password != null)
                return false;
        } else if (!this.password.equals(other.password))
            return false;
        if (this.userName == null) {
            if (other.userName != null)
                return false;
        } else if (!this.userName.equals(other.userName))
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("System User ");
        sb.append(super.toString());
        sb.append(", Username: " + this.userName);
        sb.append(", enabled: " + this.isEnabled);
        sb.append(", Authorities: (");
        if ( this.authorities != null) {
            for (Authority authority : this.authorities) {
                sb.append(authority);
            }
        }
        sb.append(")");
        sb.append(", Individual: " + this.individual);
        return sb.toString();
    }
    
    
}
