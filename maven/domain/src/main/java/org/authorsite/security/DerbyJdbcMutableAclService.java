/*
 * DerbyJdbcMutableAclService.java
 *
 * Created on 11 May 2007, 02:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security;


import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.acegisecurity.acls.AccessControlEntry;
import org.acegisecurity.acls.MutableAcl;
import org.acegisecurity.acls.domain.AccessControlEntryImpl;
import org.acegisecurity.acls.jdbc.AclCache;
import org.acegisecurity.acls.jdbc.JdbcMutableAclService;
import org.acegisecurity.acls.jdbc.LookupStrategy;
import org.acegisecurity.acls.objectidentity.ObjectIdentity;
import org.acegisecurity.acls.sid.GrantedAuthoritySid;
import org.acegisecurity.acls.sid.PrincipalSid;
import org.acegisecurity.acls.sid.Sid;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

/**
 * Implementation of Acegi's mutable ACL Service which doesn't
 * insert "null" into ID columns.
 *
 * @author jejking
 */
public class DerbyJdbcMutableAclService extends JdbcMutableAclService {
    
    private String selectClassPrimaryKey = "SELECT id FROM acl_class WHERE class=?";
    private String insertClass = "INSERT INTO acl_class (class) VALUES (?)";
    private String identityQuery = "values IDENTITY_VAL_LOCAL()";
    
    private String insertEntry = "INSERT INTO acl_entry "
            + "(acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private String insertObjectIdentity = "INSERT INTO acl_object_identity "
            + "(object_id_class, object_id_identity, owner_sid, entries_inheriting) " + "VALUES ( ?, ?, ?, ?)";
    
    private String selectSidPrimaryKey = "SELECT id FROM acl_sid WHERE principal=? AND sid=?";
    
    private String insertSid = "INSERT INTO acl_sid (principal, sid) VALUES ( ?, ?)";
    
    public DerbyJdbcMutableAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
    }
    
    protected Long createOrRetrieveClassPrimaryKey(Class clazz, boolean allowCreate) {
        List classIds = jdbcTemplate.queryForList(selectClassPrimaryKey, new Object[] {clazz.getName()}, Long.class);
        Long classId = null;
        
        if (classIds.isEmpty()) {
            if (allowCreate) {
                classId = null;
                jdbcTemplate.update(insertClass, new Object[] {clazz.getName()});
                Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "Transaction must be running");
                classId = new Long(jdbcTemplate.queryForLong(identityQuery));
            }
        } else {
            classId = (Long) classIds.iterator().next();
        }
        
        return classId;
    }
    
    /***
     * Creates a new row in acl_entry for every ACE defined in the passed MutableAcl object.
     *
     * @param acl containing the ACEs to insert
     */
    protected void createEntries(final MutableAcl acl) {
        jdbcTemplate.batchUpdate(insertEntry, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return acl.getEntries().length;
            }
            
            public void setValues(PreparedStatement stmt, int i)
            throws SQLException {
                AccessControlEntry entry_ = (AccessControlEntry) Array.get(acl.getEntries(), i);
                Assert.isTrue(entry_ instanceof AccessControlEntryImpl, "Unknown ACE class");
                
                AccessControlEntryImpl entry = (AccessControlEntryImpl) entry_;
                
                stmt.setLong(1, ((Long) acl.getId()).longValue());
                stmt.setInt(2, i);
                stmt.setLong(3, createOrRetrieveSidPrimaryKey(entry.getSid(), true).longValue());
                stmt.setInt(4, entry.getPermission().getMask());
                stmt.setBoolean(5, entry.isGranting());
                stmt.setBoolean(6, entry.isAuditSuccess());
                stmt.setBoolean(7, entry.isAuditFailure());
            }
        });
    }
    
    /***
     * Creates an entry in the acl_object_identity table for the passed ObjectIdentity. The Sid is also
     * necessary, as acl_object_identity has defined the sid column as non-null.
     *
     * @param object to represent an acl_object_identity for
     * @param owner for the SID column(will be created if there is no acl_sid entry for this particular Sid already)
     */
    protected void createObjectIdentity(ObjectIdentity object, Sid owner) {
        Long sidId = createOrRetrieveSidPrimaryKey(owner, true);
        Long classId = createOrRetrieveClassPrimaryKey(object.getJavaType(), true);
        jdbcTemplate.update(insertObjectIdentity,
                new Object[] {classId, object.getIdentifier().toString(), sidId, new Boolean(true)});
    }
    
    /***
     * Retrieves the primary key from acl_sid, creating a new row if needed and the allowCreate property is
     * true.
     *
     * @param sid to find or create
     * @param allowCreate true if creation is permitted if not found
     *
     * @return the primary key or null if not found
     *
     * @throws IllegalArgumentException DOCUMENT ME!
     */
    protected Long createOrRetrieveSidPrimaryKey(Sid sid, boolean allowCreate) {
        Assert.notNull(sid, "Sid required");
        
        String sidName = null;
        boolean principal = true;
        
        if (sid instanceof PrincipalSid) {
            sidName = ((PrincipalSid) sid).getPrincipal();
        } else if (sid instanceof GrantedAuthoritySid) {
            sidName = ((GrantedAuthoritySid) sid).getGrantedAuthority();
            principal = false;
        } else {
            throw new IllegalArgumentException("Unsupported implementation of Sid");
        }
        
        List sidIds = jdbcTemplate.queryForList(selectSidPrimaryKey, new Object[] {new Boolean(principal), sidName},
                Long.class);
        Long sidId = null;
        
        if (sidIds.isEmpty()) {
            if (allowCreate) {
                sidId = null;
                jdbcTemplate.update(insertSid, new Object[] {new Boolean(principal), sidName});
                Assert.isTrue(TransactionSynchronizationManager.isSynchronizationActive(), "Transaction must be running");
                sidId = new Long(jdbcTemplate.queryForLong(identityQuery));
            }
        } else {
            sidId = (Long) sidIds.iterator().next();
        }
        
        return sidId;
    }
    
}
