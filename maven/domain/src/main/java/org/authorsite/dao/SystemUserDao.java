package org.authorsite.dao;

import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface SystemUserDao {
    
    public SystemUser findById(long id) throws DataAccessException;
    
    public int countUsers() throws DataAccessException;
    
    public void save(SystemUser user) throws DataAccessException;
    
    public SystemUser update(SystemUser user) throws DataAccessException;
    
    public void delete(SystemUser user) throws DataAccessException;
    
    public SystemUser findUserByUsername(String username) throws DataAccessException;
    
}
