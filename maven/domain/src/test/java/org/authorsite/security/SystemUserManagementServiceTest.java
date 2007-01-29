/*
 * SystemUserManagementServiceTest.java
 *
 * Created on 28 January 2007, 13:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.providers.encoding.ShaPasswordEncoder;
import org.authorsite.dao.AbstractJPATest;
import org.authorsite.dao.SystemUserDao;
import org.authorsite.security.test.TestSecured;
import org.authorsite.security.test.UsernameCollector;

/**
 *
 * @author jejking
 */
public class SystemUserManagementServiceTest extends AbstractJPATest {
    
    /**
     * Creates a new instance of SystemUserManagementServiceTest
     */
    public SystemUserManagementServiceTest() {
    }
    
    
    private TestAuthenticationMechanism authenticationMechanism;
    private ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
    private SystemUserDao systemUserDao;
    private SystemUserManagementService systemUserManagementService;
    
    static {
        
    }
    
       
    protected void onSetUpInTransaction() throws Exception {
        
      jdbcTemplate.execute(
        "CREATE TABLE ACL_SID(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,PRINCIPAL BOOLEAN NOT NULL,SID VARCHAR_IGNORECASE(100) NOT NULL,CONSTRAINT UNIQUE_UK_1 UNIQUE(SID,PRINCIPAL));");
      jdbcTemplate.execute(
          "CREATE TABLE ACL_CLASS(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,CLASS VARCHAR_IGNORECASE(100) NOT NULL,CONSTRAINT UNIQUE_UK_2 UNIQUE(CLASS));");
      jdbcTemplate.execute(
          "CREATE TABLE ACL_OBJECT_IDENTITY(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,OBJECT_ID_CLASS BIGINT NOT NULL,OBJECT_ID_IDENTITY BIGINT NOT NULL,PARENT_OBJECT BIGINT,OWNER_SID BIGINT,ENTRIES_INHERITING BOOLEAN NOT NULL,CONSTRAINT UNIQUE_UK_3 UNIQUE(OBJECT_ID_CLASS,OBJECT_ID_IDENTITY),CONSTRAINT FOREIGN_FK_1 FOREIGN KEY(PARENT_OBJECT)REFERENCES ACL_OBJECT_IDENTITY(ID),CONSTRAINT FOREIGN_FK_2 FOREIGN KEY(OBJECT_ID_CLASS)REFERENCES ACL_CLASS(ID),CONSTRAINT FOREIGN_FK_3 FOREIGN KEY(OWNER_SID)REFERENCES ACL_SID(ID));");
      jdbcTemplate.execute(
          "CREATE TABLE ACL_ENTRY(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,ACL_OBJECT_IDENTITY BIGINT NOT NULL,ACE_ORDER INT NOT NULL,SID BIGINT NOT NULL,MASK INTEGER NOT NULL,GRANTING BOOLEAN NOT NULL,AUDIT_SUCCESS BOOLEAN NOT NULL,AUDIT_FAILURE BOOLEAN NOT NULL,CONSTRAINT UNIQUE_UK_4 UNIQUE(ACL_OBJECT_IDENTITY,ACE_ORDER),CONSTRAINT FOREIGN_FK_4 FOREIGN KEY(ACL_OBJECT_IDENTITY) REFERENCES ACL_OBJECT_IDENTITY(ID),CONSTRAINT FOREIGN_FK_5 FOREIGN KEY(SID) REFERENCES ACL_SID(ID));");


        

        String hansWurstPassword = getPasswordEncoder().encodePassword("secret", "hanswurst");
        String johnnySausagePassword = getPasswordEncoder().encodePassword("porkscratchings", "johnnysausage");
        String johanWurstPassword = getPasswordEncoder().encodePassword("foobar", "johanwurst");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, null, null, null, null, 0, null, 'Wurst', 'Hans', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, userName, password, enabled) " +
                "values (1, 1, null, null, null, null, 0, 'hanswurst', '" + hansWurstPassword + "', 1)"  );
        jdbcTemplate.execute("insert into SystemUser_Authorities(SystemUser_id, element) " +
                "values ( 1, 0 )");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, null, null, null, null, 0, null, 'Sausage', 'Johnny', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, userName, password, enabled) " +
                "values (2, 2, null, null, null, null, 0, 'johnnysausage', '" + johnnySausagePassword + "', 0)"  ); // not enabled...
        jdbcTemplate.execute("insert into SystemUser_Authorities(SystemUser_id, element) " +
                "values ( 2, 0 )");
        
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, null, null, null, null, 0, null, 'Wurst', 'Johannes', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, userName, password, enabled) " +
                "values (3, 3, null, null, null, null, 0, 'johanwurst', '" + johanWurstPassword + "', 1)"  );
        // johanwurst is an Editor
        jdbcTemplate.execute("insert into SystemUser_Authorities(SystemUser_id, element) " +
                "values ( 3, 1 )");
        
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (4, null, null, null, null, 0, null, 'Super', 'User', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, userName, password, enabled) " +
                "values (4, 4, null, null, null, null, 0, 'admin', 'secret', 1)"  );
        jdbcTemplate.execute("insert into SystemUser_Authorities(SystemUser_id, element) " +
                "values ( 4, 0 )");
        
    }
    
    protected void onTearDownInTransaction() throws Exception {
        super.onTearDownInTransaction();
        jdbcTemplate.execute("DELETE FROM SystemUser_Authorities");
        jdbcTemplate.execute("DELETE FROM SystemUser");
        jdbcTemplate.execute("DELETE from Human");
        
        jdbcTemplate.execute("DROP TABLE ACL_ENTRY");
        jdbcTemplate.execute("DROP TABLE ACL_OBJECT_IDENTITY");
        jdbcTemplate.execute("DROP TABLE ACL_CLASS");
        jdbcTemplate.execute("DROP TABLE ACL_SID");
    }
    
    public TestAuthenticationMechanism getAuthenticationMechanism() {
        return this.authenticationMechanism;
    }
    
    public void setAuthenticationMechanism(TestAuthenticationMechanism authenticationMechanism) {
        this.authenticationMechanism = authenticationMechanism;
    }
    
   protected String[] getConfigLocations() {
        return new String[] {"classpath:/spring-test-secured-appcontext-1.xml"};
    }
    


    public ShaPasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(ShaPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public SystemUserDao getSystemUserDao() {
        return systemUserDao;
    }

    public void setSystemUserDao(SystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
    }

    public SystemUserManagementService getSystemUserManagementService() {
        return systemUserManagementService;
    }

    public void setSystemUserManagementService(SystemUserManagementService systemUserManagementService) {
        this.systemUserManagementService = systemUserManagementService;
    }
    
    
    // actual test code!
    public void testCreateNewUser() throws Exception {
        this.authenticationMechanism.logUserIn("hanswurst", "secret");
        this.systemUserManagementService.createNewSystemUser("test1", "password1", "TestName", "Test Given Name", "the Tester");
    }
    
    public void testCreateNewUserAndDelete() throws Exception {
        this.authenticationMechanism.logUserIn("hanswurst", "secret");
        this.systemUserManagementService.createNewSystemUser("test2", "password2", "TestName2", "Test Given Name2", "the Tester2");
        
        this.systemUserManagementService.deleteSystemUser(systemUserDao.findUserByUsername("test2"));
    }

    public void testCreateNewUserCanEditOwnPassword() throws Exception {
        this.authenticationMechanism.logUserIn("hanswurst", "secret");
        this.systemUserManagementService.createNewSystemUser("test2", "password2", "TestName2", "Test Given Name2", "the Tester2");
        
        this.authenticationMechanism.logUserIn("test2", "password2");
        SystemUser test2 = this.systemUserDao.findUserByUsername("test2");
        this.systemUserManagementService.changePassword(test2, "changedPassword");
        
        this.authenticationMechanism.logUserIn("test2", "changedPassword");
    }
    
    public void testCreateNewUserOtherUserCannotEditPassword() throws Exception {
        this.authenticationMechanism.logUserIn("hanswurst", "secret");
        this.systemUserManagementService.createNewSystemUser("test1", "password1", "TestName", "Test Given Name", "the Tester");
        this.systemUserManagementService.createNewSystemUser("test2", "password2", "TestName2", "Test Given Name2", "the Tester2");
        
        this.authenticationMechanism.logUserIn("test2", "password2");
        SystemUser test1 = this.systemUserDao.findUserByUsername("test1");
        
        try {
            this.systemUserManagementService.changePassword(test1, "thisShouldNotWork");
            fail("expected access denied exception");
        }
        catch (AccessDeniedException ade) {
            assertTrue(true);
        }
        
    }
    
    public void testCreateNewUserAdminCanEditPassword() throws Exception {
        this.authenticationMechanism.logUserIn("hanswurst", "secret");
        this.systemUserManagementService.createNewSystemUser("test1", "password1", "TestName", "Test Given Name", "the Tester");
        
        SystemUser test1 = this.systemUserDao.findUserByUsername("test1");
        
        this.systemUserManagementService.changePassword(test1, "aNewPassword");
        
        this.authenticationMechanism.logUserIn("test1", "aNewPassword");
        
    }
    
}
