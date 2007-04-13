/*
 * SystemUserDaoJPATest.java
 *
 * Created on 18 January 2007, 22:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.dao;

import java.util.List;
import java.util.Set;
import org.authorsite.domain.Individual;
import org.authorsite.security.Authority;
import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public class SystemUserDaoJPATest extends AbstractJPATest {
    
    /** Creates a new instance of SystemUserDaoJPATest */
    public SystemUserDaoJPATest() {
    }
    
    private SystemUserDao systemUserDao;
    private IndividualDao individualDao;
    
    public void setSystemUserDao(SystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
    }
    
    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }

    protected String[] getConfigLocations() {
        return new String[] {"classpath:/spring-test-appcontext-1.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {

        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, null, null, null, null, 0, null, 'Wurst', 'Hans', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, userName, password, enabled) " +
                "values (1, 1, null, null, null, null, 0, 'hanswurst', 'secret', 1)"  );
        
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, null, null, null, null, 0, null, 'Sausage', 'Johnny', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, userName, password, enabled) " +
                "values (2, 2, null, null, null, null, 0, 'johnnysausage', 'secret', 1)"  );
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, null, null, null, null, 0, null, 'Wurst', 'Johannes', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (4, null, null, null, null, 0, null, 'Super', 'User', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, userName, password, enabled) " +
                "values (4, 4, null, null, null, null, 0, 'admin', 'secret', 1)"  );
        jdbcTemplate.execute("insert into SystemUser_Authorities(SystemUser_id, element) " +
                "values ( 4, 0 )");
        
    }

    public SystemUserDao getSystemUserDao() {
        return this.systemUserDao;
    }
    
    public IndividualDao getIndividualDao() {
        return this.individualDao;
    }
    
    
    public void testFindById() throws Exception {
        SystemUser su = systemUserDao.findById(1);
        assertNotNull(su);
        
        assertEquals("hanswurst", su.getUserName());
        assertTrue(su.isEnabled());
        Individual i = su.getIndividual();
        assertNotNull(i);
        assertEquals("Wurst", i.getName());
        assertTrue(i.isSystemUser());
    }
    
    public void testCountUsers() throws Exception {
        int count = systemUserDao.countUsers();
        assertEquals(3, count);
    }
    
    public void testDelete() throws Exception {
        SystemUser hw = systemUserDao.findById(1);
        systemUserDao.delete(hw);
        
        SystemUser nullHw = systemUserDao.findById(1);
        assertNull(nullHw);
        
        Individual hansWurst = individualDao.findById(1);
        assertNotNull(hansWurst);

    }
    
    public void testCreateSystemUserWithExistingIndividual() throws Exception {
        Individual johannesWurst = individualDao.findById(3);
        SystemUser jwUser = new SystemUser();
        jwUser.setIndividual(johannesWurst);
        jwUser.setUserName("johanneswurst");
        jwUser.setPassword("salami");
        
        systemUserDao.save(jwUser);
        long id = jwUser.getId();
        assertTrue(id > 0);
        
        SystemUser loaded = systemUserDao.findById(id);
        assertNotNull(loaded);
        assertTrue(loaded.getIndividual().equals(johannesWurst));
    }
    
    public void testCreateSystemUserNewIndividual() throws Exception {
        Individual test = new Individual("Testus", "McTest");
        SystemUser testUser = new SystemUser(test, "testuser", "testpassword");
        
        try {
            systemUserDao.save(testUser);
            fail("expected data access exception");
        }
        catch (DataAccessException dae) {
            assertTrue(true);
        }

    }
    
    public void testCreateSystemUserDuplicateUserName() throws Exception {
        Individual johannesWurst = individualDao.findById(3);
        SystemUser jwUser = new SystemUser();
        jwUser.setIndividual(johannesWurst);
        jwUser.setUserName("hanswurst");
        jwUser.setPassword("salami");
        
        try {
            systemUserDao.save(jwUser);
            fail("expected data access exception");
        }
        catch (DataAccessException dae) {
            assertTrue(true);
        }
    }
    
    public void testCreateSystemUserNullUserName() throws Exception {
        Individual johannesWurst = individualDao.findById(3);
        SystemUser jwUser = new SystemUser();
        jwUser.setIndividual(johannesWurst);
        jwUser.setUserName(null);
        jwUser.setPassword("salami");
        
        try {
            systemUserDao.save(jwUser);
            fail("expected data access exception");
        }
        catch (DataAccessException dae) {
            assertTrue(true);
        }
    }
    
    public void testCreateSystemUserNullPassword() throws Exception {
        Individual johannesWurst = individualDao.findById(3);
        SystemUser jwUser = new SystemUser();
        jwUser.setIndividual(johannesWurst);
        jwUser.setUserName("johanneswurst");
        jwUser.setPassword(null);
        
        try {
            systemUserDao.save(jwUser);
            fail("expected data access exception");
        }
        catch (DataAccessException dae) {
            assertTrue(true);
        }
    }
    
    public void testUpdateSystemUser() throws Exception {
        SystemUser hw = systemUserDao.findById(1);
        hw.setPassword("notSoSecret");
        systemUserDao.update(hw);
        
        SystemUser loaded = systemUserDao.findById(1);
        assertEquals("notSoSecret", loaded.getPassword());
    }
    /*
    public void testDeleteIndividualWhoIsUser() {
        Individual hansWurst = individualDao.findById(1);
        try {
            individualDao.delete(hansWurst);
            fail("expected data access exception");
        }
        catch (DataAccessException dae) {
            assertTrue(true);
        }
    }
     **/
    
    public void testFindUserByUserName() throws Exception {
        SystemUser hw = systemUserDao.findUserByUsername("hanswurst");
        assertNotNull(hw);
        
        SystemUser fictional = systemUserDao.findUserByUsername("fictional");
        assertNull(fictional);
    }
    
    public void testCreateSystemUserWithAuthorities() throws Exception {
        Individual johannesWurst = individualDao.findById(3);
        SystemUser jwUser = new SystemUser();
        jwUser.setIndividual(johannesWurst);
        jwUser.setUserName("johanneswurst");
        jwUser.setPassword("salami");
        
        jwUser.getAuthorities().add(Authority.ADMIN);
        systemUserDao.save(jwUser);
    }
    
    public void testLoadSystemUserWithAuthorities() throws Exception {
        SystemUser admin = systemUserDao.findById(4);
        Set<Authority> adminPerms = admin.getAuthorities();
        assertNotNull(adminPerms);
        assertTrue(adminPerms.contains(Authority.ADMIN));
    }
    
    public void testFindAll() throws Exception {
        List<SystemUser> all = this.systemUserDao.findAllSystemUsers();
        assertEquals(3, all.size());
    }
    
    public void testFindAllPage() throws Exception {
        List<SystemUser> page1 = this.systemUserDao.findAllSystemUsers(1, 10);
        assertEquals(3, page1.size());
        List<SystemUser> page3 = this.systemUserDao.findAllSystemUsers(3, 10);
        assertTrue(page3.isEmpty());
    }
}
