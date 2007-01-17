package org.authorsite.security;

/**
 *
 * @author jejking
 */
public enum Authority {
    
    ADMIN ("Administrator"),
    EDITOR("Editor"),
    COMMUNITY("Community");

    private String roleName;
    
    /** Creates a new instance of Authority */
     Authority(String roleName) {
        this.roleName = roleName;
    }
    
    @Override
    public String toString() {
        return this.roleName;
    }
    
    public String getRoleName() {
        return this.roleName;
    }
    
}
