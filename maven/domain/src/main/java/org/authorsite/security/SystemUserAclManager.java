package org.authorsite.security;

/**
 *
 * @author jejking
 */
public interface SystemUserAclManager {
    
    public void addSystemUserChangePasswordPermission(SystemUser user);
    
    public void removeSystemUserChangePasswordPermission(SystemUser user);
    
}
