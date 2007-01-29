/*
 * MethodAccessSecurityTest.java
 *
 * Created on 21 January 2007, 20:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.DisabledException;
import org.acegisecurity.providers.encoding.ShaPasswordEncoder;
import org.authorsite.dao.AbstractJPATest;
import org.authorsite.security.test.TestSecured;
import org.authorsite.security.test.TestSecuredImpl;
import org.authorsite.security.test.UsernameCollector;

/**
 *
 * @author jejking
 */
public class MethodAccessSecurityTest extends AbstractJPATest {
    
    private TestAuthenticationMechanism authenticationMechanism;
    private ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
    private TestSecured testSecured;
    
    static {
        
    }
    /**
     * Creates a new instance of MethodAccessSecurityTest
     */
    public MethodAccessSecurityTest() {
        super();
    }
    
    protected void onSetUpInTransaction() throws Exception {

        String hansWurstPassword = passwordEncoder.encodePassword("secret", "hanswurst");
        String johnnySausagePassword = passwordEncoder.encodePassword("porkscratchings", "johnnysausage");
        String johanWurstPassword = passwordEncoder.encodePassword("foobar", "johanwurst");
        
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
    
    public TestAuthenticationMechanism getAuthenticationMechanism() {
        return this.authenticationMechanism;
    }
    
    public void setAuthenticationMechanism(TestAuthenticationMechanism authenticationMechanism) {
        this.authenticationMechanism = authenticationMechanism;
    }
    
    public TestSecured getTestSecured() {
        return this.testSecured;
    }
    
    public void setTestSecured(TestSecured testSecured) {
        this.testSecured = testSecured;
    }
    
    
   protected String[] getConfigLocations() {
        return new String[] {"classpath:/spring-test-secured-appcontext-1.xml"};
    }
    
    public void testSuccessfulLogin() throws Exception {
        authenticationMechanism.logUserIn("hanswurst", "secret");
        UsernameCollector collector = new UsernameCollector();
        this.testSecured.noAccessControl(collector);
        assertEquals("hanswurst", collector.getUsername());
    }
    
    public void testFailedLoginWrongPassword() throws Exception {
        try {
            authenticationMechanism.logUserIn("hanswurst", "thewrongpassword");
            fail("expected bad credentials exception");
        }
        catch (BadCredentialsException bce) {
            assertTrue(true);
        }
    }
    
    public void testFailedLoginNosuchUser() throws Exception {
        try {
            authenticationMechanism.logUserIn("nonexistent", "fictional");
            fail("expected bad credentials exception");
        }
        catch (BadCredentialsException bce) {
            assertTrue(true);
        }
    }
    
    public void testFailedLoginUserDisabled() throws Exception {
        try {
            authenticationMechanism.logUserIn("johnnysausage", "porkscratchings");
            fail("expected DisabledException");
        }
        catch (DisabledException de) {
            assertTrue(true);
        }
    }
    
    public void testEditorCanAccessUnsecuredMethod() throws Exception {
        authenticationMechanism.logUserIn("johanwurst", "foobar");
        UsernameCollector collector = new UsernameCollector();
        this.testSecured.noAccessControl(collector);
        assertEquals("johanwurst", collector.getUsername());
    }
    
    public void testEditorCanAccessEditorOnlyMethod() throws Exception {
        authenticationMechanism.logUserIn("johanwurst", "foobar");
        UsernameCollector collector = new UsernameCollector();
        this.testSecured.editorOnly(collector);
        assertEquals("johanwurst", collector.getUsername());
    }
    
    public void testEditorCanAccessEditorOrAdminMethod() throws Exception {
        authenticationMechanism.logUserIn("johanwurst", "foobar");
        UsernameCollector collector = new UsernameCollector();
        this.testSecured.editorOrAdmin(collector);
        assertEquals("johanwurst", collector.getUsername());
    }
    
    public void testAdminCannotAccessEdtiorOnlyMethod() throws Exception {
        authenticationMechanism.logUserIn("hanswurst", "secret");
        UsernameCollector collector = new UsernameCollector();
        try {
            this.testSecured.editorOnly(collector); // should fail hanswurst is ADMIN, not EDITOR
            fail("expected exception");
        }
        catch (AccessDeniedException ade) {
            assertTrue(true);
        }
        
    }
    
    public void testLoginRequiredToAccessEditorOnlyMethod() throws Exception {
        UsernameCollector collector = new UsernameCollector();
        try {
            this.testSecured.editorOnly(collector);
            fail("expected exception");
        }
        catch (AccessDeniedException ade) {
            assertTrue(true);
        }
    }
    
    public void testEditorCannotAccessAdminOnlyMethod() throws Exception {
        authenticationMechanism.logUserIn("johanwurst", "foobar");
        UsernameCollector collector = new UsernameCollector();
        try {
            this.testSecured.adminOnly(collector);
            fail("expected exception");
        }
        catch (AccessDeniedException ade) {
            assertTrue(true);
        }
    }
    
    public void testAdminCanAccessAdminOnlyMethod() throws Exception  {
        authenticationMechanism.logUserIn("hanswurst", "secret");
        UsernameCollector collector = new UsernameCollector();
        this.testSecured.adminOnly(collector);
        assertEquals("hanswurst", collector.getUsername());
    }
    
    public void testAdminCanAccessEditorOrAdminMethod() throws Exception {
        authenticationMechanism.logUserIn("hanswurst", "secret");
        UsernameCollector collector = new UsernameCollector();
        this.testSecured.editorOrAdmin(collector);
        assertEquals("hanswurst", collector.getUsername());
    }
}
