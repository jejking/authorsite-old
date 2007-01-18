/*
 * IndividualDaoJPATest.java
 *
 * Created on 04 January 2007, 22:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.dao;

import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.authorsite.domain.Individual;
import org.authorsite.security.SystemUser;
import org.springframework.dao.DataAccessException;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 *
 * @author jejking
 */
public class IndividualDaoJPATest extends AbstractJPATest {

    private IndividualDao individualDao;
    
    /**
     * Creates a new instance of IndividualDaoJPATest
     */
    public IndividualDaoJPATest() {
    }
    
    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }

    protected String[] getConfigLocations() {
        return new String[] {"classpath:/spring-test-appcontext-1.xml"};
    }

    protected void onSetUpInTransaction() throws Exception {

        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, null, null, null, null, 0, null, 'Wurst', 'Hans', 'Individual')");
        jdbcTemplate.execute("insert into SystemUser (id, individual_id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, userName, password, enabled) " +
                "values (1, 1, null, null, null, null, 0, 'hanswurst', 'secret', 1)"  );
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, null, null, null, null, 0, null, 'Sausage', 'Johnny', 'Individual')");
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, null, null, null, null, 0, null, 'Wurst', 'Johannes', 'Individual')");
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (4, null, null, null, null, 0, null, 'Sausage', 'Peter', 'Individual')");
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (5, null, null, null, null, 0, null, 'Saucemaker', 'Paul', 'Individual')");
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (6, null, null, null, null, 0, null, 'Butcher', 'Paul', 'Individual')");
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy, updatedAt, " +
                "updatedBy, version, nameQualification, name, givenNames, DTYPE)" +
                " values (7, null, null, null, null, 0, 'Jnr', 'Wurst', 'Hans', 'Individual')");
        
    }

    public IndividualDao getIndividualDao() {
        return individualDao;
    }
  
    
  public void testFindById() throws Exception {
      Individual i1 = new Individual("Wurst", "Hans");
      Individual loaded = this.individualDao.findById(1);
      assertNotNull(loaded);
      assertEquals(i1, loaded);
  }
  
  
  public void testSaveIndividual() throws Exception {
      
      Individual i1 = new Individual("McTest", "Test");
      
      this.individualDao.save(i1);
      assertTrue(i1.getId() > 0 );
      assertNotNull(i1.getCreatedAt());
      assertNotNull(i1.getUpdatedAt());
      
      // and load
      Individual loaded = this.individualDao.findById(i1.getId());
      assertNotNull(loaded);
      assertEquals("McTest", loaded.getName());
      assertNull(loaded.getNameQualification());
      assertEquals("Test", loaded.getGivenNames());
      
  }
  
  public void testUpdateIndividual() throws Exception {
      
      Individual hansWurst = this.individualDao.findById(1);
      
      hansWurst.setGivenNames("Johnny");
      Individual updated = this.individualDao.update(hansWurst);
      
      Individual johnnyWurst = this.individualDao.findById(1);
      assertEquals("Johnny", johnnyWurst.getGivenNames());
      assertEquals("Wurst", johnnyWurst.getName());
      assertNotNull(johnnyWurst.getUpdatedAt());
     
  }
  
  public void testDeleteIndividual() throws Exception {
      Individual hansWurst = this.individualDao.findById(1);
      SystemUser user = hansWurst.getSystemUser();
      System.out.println(user.getClass());
        this.individualDao.delete(hansWurst);

      Individual missing = this.individualDao.findById(1);
      assertNull(missing);
  }
  
  
  public void testCountIndividuals() throws Exception {
      assertEquals(7, this.individualDao.countIndividuals());
  }
  
  public void testFindByName() throws Exception {
      List<Individual> wuerste = this.individualDao.findIndividualsByName("Wurst");
      assertEquals(3, wuerste.size());
  }
  
  
  public void testFindByNameWildcard() throws Exception {
      List<Individual> saus = this.individualDao.findIndividualsByNameWildcard("Sau%");
      assertEquals(3, saus.size());
  }
  
  
  public void testFindByNameAndGivenNames() throws Exception {
      List<Individual> hansWuerste = this.individualDao.findIndividualsByNameAndGivenNames("Wurst", "Hans");
      assertEquals(2, hansWuerste.size());
  }
  
  public void testFindByNameAndGivenNamesWildcard() throws Exception {
      List<Individual> saups = this.individualDao.findIndividualsByNameAndGivenNamesWildcard("Sau%", "P%");
      assertEquals(2, saups.size());
  }
  
  public void testUserLoad() throws Exception {
      Individual hansWurst = this.individualDao.findById(1);
      assertTrue(hansWurst.isSystemUser());
      
      SystemUser user = hansWurst.getSystemUser();
      assertEquals("hanswurst", user.getUserName());
      
      Individual saucemaker = this.individualDao.findById(5);
      assertFalse(saucemaker.isSystemUser());
  }
          
  
  
}
