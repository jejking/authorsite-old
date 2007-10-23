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
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.WorkDates;

/**
 * @author jking
 *
 */
public class JournalDaoJPATest extends AbstractJPATest {

    private JournalDao journalDao;
    private IndividualDao individualDao;
    private CollectiveDao collectiveDao;

    /**
     * 
     */
    public JournalDaoJPATest() {
        super();
    }
    
    public void setJournalDao(JournalDao journalDao) {
        this.journalDao = journalDao;
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
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (0, current_timestamp, 0, current_timestamp, 0, 1, null, 'Bob', 'Smith', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, current_timestamp, 0, current_timestamp, 0, 1, null, 'Institute of Foo', null, 'Collective')");

        // journal 1, "Wurststudien", no publisher, dates 1920-1927
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 1, 1, current_timestamp, current_timestamp, 'Wurststudien', " +
                "'1920-01-01', '1927-01-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (1)");
        
        // journal 2, "Zeitschrift des Wurstvereins", no publisher, no dates
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 2, 1, current_timestamp, current_timestamp, 'Zeitschrift des Wurstvereins', " +
                "null, null, 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (2)");
    
        // journal 3, "Salami Notes", published by Institute of Foo, no dates
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 3, 1, current_timestamp, current_timestamp, 'Salami Notes', " +
                "null, null, 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (3)");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (3, 'PUBLISHER', 1) ");
        
        // journal 4, "Sausage Journal", dates 1925-33
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 4, 1, current_timestamp, current_timestamp, 'Sausage Journal', " +
                "'1925-01-01', '1933-01-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (4)");
        
        // journal 5, "Salami Notes",  dates 1930-45
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 5, 1, current_timestamp, current_timestamp, 'Pies and Sausage Notes', " +
                "'1930-01-01', '1945-01-01', 0, 0 )");
    
        jdbcTemplate.execute("insert into JOURNAL (id) VALUES (5)");
        
        // and a thesis
        //      create d.phil thesis by hans wurst from oxford university
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 6, 1, current_timestamp, current_timestamp, 'Die Wurst in der Geschichte Deutschlands', " +
                "'2001-01-01', '2004-01-01', 0, 0 )");
        
        jdbcTemplate.execute("insert into THESIS (id, degree ) " +
                "values ( 6, 'D. Phil')" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (6, 'AUTHOR', 0) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (6, 'AWARDING_BODY', 1) ");
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#countJournals()}.
     */
    public final void testCountJournals() {
        assertEquals(5, this.journalDao.countJournals());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findAllJournals()}.
     */
    public final void testFindAllJournals() {
        Journal j2 = this.journalDao.findById(2);
        assertNotNull(j2);
        List<Journal> journals = this.journalDao.findAllJournals();
        assertEquals(5, journals.size() );
        assertTrue(journals.contains(j2));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findAllJournals(int, int)}.
     */
    public final void testFindAllJournalsIntInt() {
        Journal j1 = this.journalDao.findById(1);
        Journal j2 = this.journalDao.findById(2);
        
        List<Journal> journals = this.journalDao.findAllJournals(1, 2);
        assertEquals(2, journals.size());
        assertTrue(journals.contains(j1));
        assertTrue(journals.contains(j2));
        
        List<Journal> journals3 = this.journalDao.findAllJournals(3, 2);
        Journal j5 = this.journalDao.findById(5);
        assertEquals(1, journals3.size());
        assertTrue(journals3.contains(j5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findById(long)}.
     */
    public final void testFindById() {
        Journal j = this.journalDao.findById(1);
        assertNotNull(j);
        assertEquals(1, j.getId());
        assertEquals("Wurststudien", j.getTitle());
        WorkDates workDates = new WorkDates(1920, 1927);
        assertEquals(workDates, j.getWorkDates());
        assertTrue(j.getWorkProducers().isEmpty());
    }
    
    public final void testFindByIdNotThere() {
        Journal notThere = this.journalDao.findById(666);
        assertNull(notThere);
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findJournalsAfterDate(java.util.Date)}.
     */
    public final void testFindJournalsAfterDate() {
        GregorianCalendar cal = new GregorianCalendar(1929, Calendar.JANUARY, 1);
        List<Journal> journals = this.journalDao.findJournalsAfterDate(cal.getTime());
        //expect 4 + 5
        assertEquals(2, journals.size());
        Journal j4 = this.journalDao.findById(4);
        Journal j5 = this.journalDao.findById(5);
        assertTrue(journals.contains(j4));
        assertTrue(journals.contains(j5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findJournalsBeforeDate(java.util.Date)}.
     */
    public final void testFindJournalsBeforeDate() {
        GregorianCalendar cal = new GregorianCalendar(1929, Calendar.JANUARY, 1);
        List<Journal> journals = this.journalDao.findJournalsBeforeDate(cal.getTime());
        // expect j1
        assertEquals(2, journals.size());
        Journal j1 = this.journalDao.findById(1);
        assertTrue(journals.contains(j1));
        Journal j4 = this.journalDao.findById(4);
        assertTrue(journals.contains(j4));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findJournalsBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindJournalsBetweenDates() {
        GregorianCalendar cal1918 = new GregorianCalendar(1918, Calendar.JANUARY, 1);
        GregorianCalendar cal1926 = new GregorianCalendar(1926, Calendar.JANUARY, 1);
        
        List<Journal> journals = this.journalDao.findJournalsBetweenDates(cal1918.getTime(), cal1926.getTime());
        assertEquals(2, journals.size());
        
        Journal j1 = this.journalDao.findById(1);
        Journal j4 = this.journalDao.findById(4);
        assertTrue(journals.contains(j1));
        assertTrue(journals.contains(j4));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findJournalsByTitle(java.lang.String)}.
     */
    public final void testFindJournalsByTitle() {
        List<Journal> journals = this.journalDao.findJournalsByTitle("Zeitschrift des Wurstvereins");
        assertEquals(1, journals.size());
        Journal j2 = this.journalDao.findById(2);
        assertEquals(j2, journals.get(0));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findJournalsByTitleWildcard(java.lang.String)}.
     */
    public final void testFindJournalsByTitleWildcard() {
        List<Journal> journals = this.journalDao.findJournalsByTitleWildcard("%Wurst%");
        assertEquals(2, journals.size());
        Journal j1 = this.journalDao.findById(1);
        Journal j2 = this.journalDao.findById(2);
        
        assertTrue(journals.contains(j1));
        assertTrue(journals.contains(j2));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#findJournalsWithPublisher(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindJournalsWithPublisher() {
        Collective fooInstitute = this.collectiveDao.findById(1);
        List<Journal> journals = this.journalDao.findJournalsWithPublisher(fooInstitute);
        assertEquals(1, journals.size());
        
        Journal j3 = this.journalDao.findById(3);
        assertTrue(journals.contains(j3));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#saveJournal(org.authorsite.domain.bib.Journal)}.
     */
    public final void testSaveJournal() {
        Individual bob = this.individualDao.findById(0);
        Journal j = new Journal("Butcher's Weekly");
        j.setCreatedBy(bob);
        j.setUpdatedBy(bob);
        this.journalDao.saveJournal(j);
        
        long id = j.getId();
        Journal loaded = this.journalDao.findById(id);
        assertNotNull(loaded);
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#updateJournal(org.authorsite.domain.bib.Journal)}.
     */
    public final void testUpdateJournal() {
        Journal wurstStudien = this.journalDao.findById(1);
        Collective fooInstitute = this.collectiveDao.findById(1);
        
        wurstStudien.setPublisher(fooInstitute);
        wurstStudien.setTitle("Wurststudien I");
        
        this.journalDao.saveJournal(wurstStudien);
        
        Journal loaded = this.journalDao.findById(1);
        assertEquals(fooInstitute, loaded.getPublisher());
        assertEquals("Wurststudien I", loaded.getTitle());
        
        List<Journal> oldTitle = this.journalDao.findJournalsByTitle("Wurststudien");
        assertTrue(oldTitle.isEmpty());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.JournalDaoJPA#deleteJournal(org.authorsite.domain.bib.Journal)}.
     */
    public final void testDeleteJournal() {
        Journal wurstStudien = this.journalDao.findById(1);
        this.journalDao.deleteJournal(wurstStudien);
        
        Journal loaded = this.journalDao.findById(1);
        assertNull(loaded);
    }

}
