package org.authorsite.domain.dao;

import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 *
 * @author jejking
 */
public class CollectiveDaoJPATest extends AbstractJpaTests {
    
    /** Creates a new instance of CollectiveDaoJPATest */
    public CollectiveDaoJPATest() {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.WARN);
        Logger.getLogger("org.springframework").setLevel(Level.ERROR);
        Logger.getLogger("org.hibernate").setLevel(Level.ERROR);
        Logger.getLogger("org.authorsite").setLevel(Level.DEBUG);
    }
    
  private CollectiveDao collectiveDao;

  public void setCollectiveDao(CollectiveDao collectiveDao) {
    this.collectiveDao = collectiveDao;
  }

  protected String[] getConfigLocations() {
    return new String[] {"classpath:/spring-text-appcontext-1.xml"};
  }

  protected void onSetUpInTransaction() throws Exception {
    System.out.println("On setup in transaction");
    
    jdbcTemplate.execute("insert into Human " +
            "(id, createdAt, createdBy, updatedAt, " +
            "updatedBy, version, nameQualification, name, place, DTYPE)" +
            " values (1, null, null, null, null, 0, null, 'Sausage', 'Wurstland', 'Collective')");
    jdbcTemplate.execute("insert into Human " +
            "(id, createdAt, createdBy, updatedAt, " +
            "updatedBy, version, nameQualification, name, place, DTYPE)" +
            " values (2, null, null, null, null, 0, null, 'Mortadella', 'Torino', 'Collective')");
    jdbcTemplate.execute("insert into Human " +
            "(id, createdAt, createdBy, updatedAt, " +
            "updatedBy, version, nameQualification, name, place, DTYPE)" +
            " values (3, null, null, null, null, 0, null, 'Salami', 'Hamburg', 'Collective')");
    jdbcTemplate.execute("insert into Human " +
            "(id, createdAt, createdBy, updatedAt, " +
            "updatedBy, version, nameQualification, name, place, DTYPE)" +
            " values (4, null, null, null, null, 0, null, 'Sausage', 'Hamburg', 'Collective')");
    jdbcTemplate.execute("insert into Human " +
            "(id, createdAt, createdBy, updatedAt, " +
            "updatedBy, version, nameQualification, name, place, DTYPE)" +
            " values (5, null, null, null, null, 0, null, 'Sauces Are Us', 'Hampstead', 'Collective')");
    jdbcTemplate.execute("insert into Human " +
            "(id, createdAt, createdBy, updatedAt, " +
            "updatedBy, version, nameQualification, name, place, DTYPE)" +
            " values (6, null, null, null, null, 0, null, 'Franzbrötchen', 'Hamburg', 'Collective')");
  }

  public CollectiveDao getCollectiveDao() {
      return collectiveDao;
  }
  

  
  
  public void testSaveCollective() throws Exception {
      Collective c = new Collective("Foo Inc", "Barsville");
      this.collectiveDao.save(c);
      assertTrue(c.getId() > 0 );
      
      // and load
      Collective loaded = this.collectiveDao.findById(c.getId());
      assertNotNull(loaded);
      assertEquals("Foo Inc", loaded.getName());
      assertNull(loaded.getNameQualification());
      assertEquals("Barsville", loaded.getPlace());
      
  }
  
  public void testUpdateCollective() throws Exception {
      Collective c = new Collective("Foo Inc", "Barsville");
      this.collectiveDao.save(c);
      long id = c.getId();
      System.out.println(c.getVersion());
      
      c.setName("Foo Ltd");
      Collective updated = this.collectiveDao.update(c);
      
      Collective loaded = this.collectiveDao.findById(id);
      assertEquals("Foo Ltd", loaded.getName());
      assertEquals(updated, loaded);
      System.out.println(loaded.getVersion());
     
  }
  
  public void testDeleteCollective() throws Exception {
      Collective sausage = this.collectiveDao.findById(1);
      assertEquals("Sausage", sausage.getName());
      this.collectiveDao.delete(sausage);
      
      Collective loaded = this.collectiveDao.findById(1);
      assertNull(loaded); // should have been deleted
  }
  
  public void testCountCollectives() throws Exception {
      assertEquals(6, this.collectiveDao.countCollectives());
  }
  
  public void testFindByName() throws Exception {
      List<Collective> sausages = this.collectiveDao.findCollectivesByName("Sausage");
      assertEquals(2, sausages.size());
  }
  
  public void testFindByNameWildcard() throws Exception {
      List<Collective> saus = this.collectiveDao.findCollectivesByNameWildcard("Sau%");
      assertEquals(3, saus.size());
  }
  
  public void testFindByPlace() throws Exception {
      List<Collective> hamburgers = this.collectiveDao.findCollectivesByPlace("Hamburg");
      assertEquals(3, hamburgers.size());
  }
  
  public void testFindByPlaceWildcard() throws Exception {
      List<Collective> hamburgers = this.collectiveDao.findCollectivesByPlaceWildcard("Ham%");
      assertEquals(4, hamburgers.size());
  }
  
    
}
