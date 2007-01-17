package org.authorsite.security;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.authorsite.domain.AbstractEntry;
import org.authorsite.domain.Individual;
import org.hibernate.annotations.CollectionOfElements;

/**
 *
 * @author jejking
 */
@Entity
@NamedQueries(
    {
    @NamedQuery(name="SystemUserCount",query="select count(su) from SystemUser su"),
    @NamedQuery(name="SystemUserByUserName", query="select su from SystemUser su where su.userName = :userName")
})
public class SystemUser extends AbstractEntry {
    
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
    
    public SystemUser(String userName, String password) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setEnabled(true);
    }
    
    
    @Column(unique=true, nullable=false)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(nullable=false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable=false)
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    /*
    @CollectionOfElements
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    protected void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }*/
    
    @OneToOne(mappedBy="systemUser")
    public Individual getIndividual() {
        return this.individual;
    }
    
    protected void setIndividual(Individual individual) {
        this.individual = individual;
    }
   
    
}
