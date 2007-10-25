/**
 * 
 */
package org.authorsite.dao.bib;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.authorsite.dao.AbstractJPATest;
import org.authorsite.dao.CollectiveDao;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.bib.WorkDates;

/**
 * @author jking
 *
 */
public class ThesisDaoJPATest extends AbstractJPATest {

    private ThesisDao thesisDao;
    private IndividualDao individualDao;
    private CollectiveDao collectiveDao;

    /**
     * @param thesisDao the thesisDao to set
     */
    public void setThesisDao(ThesisDao thesisDao) {
        this.thesisDao = thesisDao;
    }
    
    public void setCollectiveDao(CollectiveDao collectiveDao) {
        this.collectiveDao = collectiveDao;
    }

    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }
    
    protected String[] getConfigLocations() {
        return new String[]{"classpath:/spring-test-appcontext-1.xml"};
    }
    
    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        
        // create indivs
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, current_timestamp, 1, current_timestamp, 1, 1, null, 'Bar', 'Foo', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, current_timestamp, 1, current_timestamp, 1, 1, null, 'Wibble', 'Wobble', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, current_timestamp, 1, current_timestamp, 1, 1, null, 'Wurst', 'Hans', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (4, current_timestamp, 1, current_timestamp, 1, 1, null, 'Schmidt', 'Peter', 'Individual')");
        
        // and unis
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (5, current_timestamp, 1, current_timestamp, 1, 1, null, 'Oxford University', null, 'Collective')");

        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (6, current_timestamp, 1, current_timestamp, 1, 1, null, 'Universit\u00e4t Wurststadt', null, 'Collective')");

        
        //      and the theses
        //      M.Phil by foo bar at ou, 1996 
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 1, 1, current_timestamp, current_timestamp, 'Sausages in Germany', " +
                "'1996-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 1, 'M. Phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (1, 'AUTHOR', 1) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (1, 'AWARDING_BODY', 5) ");
        
        
        // D.phil by foo bar at ou, 1999
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 2, 1, current_timestamp, current_timestamp, 'German Sausages', " +
                "'1999-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 2, 'D. Phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'AUTHOR', 1) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'AWARDING_BODY', 5) ");
        
        // d.phil by wibble wobble at ou, 1963
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 3, 1, current_timestamp, current_timestamp, 'Salami and Pork Pies: A Comparative Study', " +
                "'1963-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 3, 'D. Phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (3, 'AUTHOR', 2) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (3, 'AWARDING_BODY', 5) ");
        
        // dr.phil by hans wurst, uni wurststadt 2000
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 4, 1, current_timestamp, current_timestamp, 'Die Currywurst in der Geschichte Berlins', " +
                "'2000-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 4, 'Dr phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'AUTHOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'AWARDING_BODY', 6) ");
        
        //  dr.phil by peter schmidt, uni wurststadt 1920
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 5, 1, current_timestamp, current_timestamp, 'Schrecken und Furcht: Rache des Wurstmarktes', " +
                "'1920-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 5, 'Dr phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (5, 'AUTHOR', 4) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (5, 'AWARDING_BODY', 6) ");
    }
    
    /**
     * 
     */
    public ThesisDaoJPATest() {
        super();
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#countTheses()}.
     */
    public final void testCountTheses() {
        assertEquals(5, this.thesisDao.countTheses());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findAllTheses()}.
     */
    public final void testFindAllTheses() {
        Thesis thesis = this.thesisDao.findById(1);
        assertNotNull(thesis);
        List<Thesis> theses = this.thesisDao.findAllTheses();
        assertEquals(5, theses.size());
        assertTrue(theses.contains(thesis));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findAllTheses(int, int)}.
     */
    public final void testFindAllThesesIntInt() {
        Thesis t1 = this.thesisDao.findById(1);
        Thesis t2 = this.thesisDao.findById(2);
        
        List<Thesis> theses = this.thesisDao.findAllTheses(1, 2);
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t1));
        assertTrue(theses.contains(t2));
        
        Thesis t5 = this.thesisDao.findById(5);
        List<Thesis> theses2 = this.thesisDao.findAllTheses(3, 2);
        assertEquals(1, theses2.size());
        assertTrue(theses2.contains(t5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findById(long)}.
     */
    public final void testFindById() {
        Individual fooBar = this.individualDao.findById(1);
        Collective ou = this.collectiveDao.findById(5);
        Thesis t1 = this.thesisDao.findById(1);
        assertEquals(fooBar, t1.getAuthor());
        assertEquals(ou, t1.getAwardingBody());
        assertEquals("M. Phil", t1.getDegree());
        assertEquals("Sausages in Germany", t1.getTitle());
        WorkDates workDates = new WorkDates(1996);
        assertEquals(workDates, t1.getWorkDates());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesAfterDate(java.util.Date)}.
     */
    public final void testFindThesesAfterDate() {
        Thesis t2 = this.thesisDao.findById(2);
        Thesis t4 = this.thesisDao.findById(4);
        
        GregorianCalendar gcal = new GregorianCalendar(1998, Calendar.JANUARY, 1);
        List<Thesis> theses = this.thesisDao.findThesesAfterDate(gcal.getTime());
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t2));
        assertTrue(theses.contains(t4));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesBeforeDate(java.util.Date)}.
     */
    public final void testFindThesesBeforeDate() {
        // before 1970
        Thesis t3 = this.thesisDao.findById(3);
        Thesis t5 = this.thesisDao.findById(5);
        
        GregorianCalendar gcal = new GregorianCalendar(1970, Calendar.JANUARY, 1);
        List<Thesis> theses = this.thesisDao.findThesesBeforeDate(gcal.getTime());
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t3));
        assertTrue(theses.contains(t5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindThesesBetweenDates() {
        // between 1950 and 1997
        Thesis t1 = this.thesisDao.findById(1);
        Thesis t3 = this.thesisDao.findById(3);
        
        GregorianCalendar gcal1950 = new GregorianCalendar(1950, Calendar.JANUARY, 1);
        GregorianCalendar gcal1997 = new GregorianCalendar(1997, Calendar.JANUARY, 1);
        
        List<Thesis> theses = this.thesisDao.findThesesBetweenDates(gcal1950.getTime(), gcal1997.getTime());
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t1));
        assertTrue(theses.contains(t3));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesByTitle(java.lang.String)}.
     */
    public final void testFindThesesByTitle() {
        Thesis t1 = this.thesisDao.findById(1);
        List<Thesis> theses = this.thesisDao.findThesesByTitle("Sausages in Germany");
        assertEquals(1, theses.size());
        assertEquals(t1, theses.get(0));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesByTitleWildcard(java.lang.String)}.
     */
    public final void testFindThesesByTitleWildcard() {
        Thesis t1 = this.thesisDao.findById(1);
        Thesis t2 = this.thesisDao.findById(2);
        
        List<Thesis> theses = this.thesisDao.findThesesByTitleWildcard("%Sausages%");
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t1));
        assertTrue(theses.contains(t2));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesWithAuthor(org.authorsite.domain.Individual)}.
     */
    public final void testFindThesesWithAuthor() {
        Individual fooBar = this.individualDao.findById(1);
        
        Thesis t1 = this.thesisDao.findById(1);
        Thesis t2 = this.thesisDao.findById(2);
        
        List<Thesis> theses = this.thesisDao.findThesesWithAuthor(fooBar);
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t1));
        assertTrue(theses.contains(t2));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#findThesesWithAwardingBody(org.authorsite.domain.Collective)}.
     */
    public final void testFindThesesWithAwardingBody() {
        Collective ou = this.collectiveDao.findById(6);
        Thesis t4 = this.thesisDao.findById(4);
        Thesis t5 = this.thesisDao.findById(5);
        
        List<Thesis> theses = this.thesisDao.findThesesWithAwardingBody(ou);
        assertEquals(2, theses.size());
        assertTrue(theses.contains(t4));
        assertTrue(theses.contains(t5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#saveThesis(org.authorsite.domain.bib.Thesis)}.
     */
    public final void testSaveThesis() {
        Individual fooBar = this.individualDao.findById(1);
        Individual hansWurst = this.individualDao.findById(3);
        Collective uniWurststadt = this.collectiveDao.findById(6);
        Thesis thesis = new Thesis( "Eine Idee", hansWurst, uniWurststadt, 1998);
        thesis.setCreatedBy(fooBar);
        thesis.setUpdatedBy(fooBar);
        thesis.setDegree("Magister");
        this.thesisDao.saveThesis(thesis);
        
        long id = thesis.getId();
        Thesis loaded = this.thesisDao.findById(id);
        assertNotNull(loaded);
        assertEquals("Eine Idee", loaded.getTitle());
        assertEquals(hansWurst, loaded.getAuthor());
        assertEquals(uniWurststadt, loaded.getAwardingBody());
        WorkDates workDates = new WorkDates(1998);
        assertEquals(workDates, loaded.getWorkDates());
        assertEquals("Magister", thesis.getDegree());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#updateThesis(org.authorsite.domain.bib.Thesis)}.
     */
    public final void testUpdateThesis() {
        Thesis t1 = this.thesisDao.findById(1);
        t1.setTitle("Test Update");
        this.thesisDao.updateThesis(t1);
        
        Thesis t1Loaded = this.thesisDao.findById(1);
        assertEquals("Test Update", t1Loaded.getTitle());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ThesisDaoJPA#deleteThesis(org.authorsite.domain.bib.Thesis)}.
     */
    public final void testDeleteThesis() {
        Thesis t1 = this.thesisDao.findById(1);
        this.thesisDao.deleteThesis(t1);
        Thesis loaded = this.thesisDao.findById(1);
        assertNull(loaded);
    }

}
