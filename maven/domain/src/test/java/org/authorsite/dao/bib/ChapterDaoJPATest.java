/**
 * 
 */
package org.authorsite.dao.bib;

import org.authorsite.dao.AbstractJPATest;
import org.authorsite.dao.CollectiveDao;
import org.authorsite.dao.IndividualDao;

/**
 * @author jking
 *
 */
public class ChapterDaoJPATest extends AbstractJPATest {

    private BookDao bookDao;
    private IndividualDao individualDao;
    private CollectiveDao collectiveDao;
    private ChapterDao chapterDao;

    /**
     * 
     */
    public ChapterDaoJPATest() {
        super();
    }
    
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void setCollectiveDao(CollectiveDao collectiveDao) {
        this.collectiveDao = collectiveDao;
    }

    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }
    
    /**
     * @param chapterDao the chapterDao to set
     */
    public void setChapterDao(ChapterDao chapterDao) {
        this.chapterDao = chapterDao;
    }
    
    protected String[] getConfigLocations() {
        return new String[]{"classpath:/spring-test-appcontext-1.xml"};
    }

    /* (non-Javadoc)
     * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpInTransaction()
     */
    @Override
    protected void onSetUpInTransaction() throws Exception {
        super.onSetUpInTransaction();
        // create indivs (authors, editors, publishers)
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (1, current_timestamp, 1, current_timestamp, 1, 1, null, 'Bar', 'Foo', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (2, current_timestamp, 1, current_timestamp, 1, 1, null, 'Wurst', 'Hans', 'Individual')");
        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (3, current_timestamp, 1, current_timestamp, 1, 1, null, 'Bear', 'Honey', 'Individual')");
        
        // create collectives (publishers)
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (4, current_timestamp, 1, current_timestamp, 1, 1, null, 'Foo Press', null, 'Collective')");

        
        jdbcTemplate.execute("insert into Human " +
                "(id, createdAt, createdBy_id, updatedAt, " +
                "updatedBy_id, version, nameQualification, name, givenNames, DTYPE)" +
                " values (5, current_timestamp, 1, current_timestamp, 1, 1, null, 'Beehive Publishing, Inc', null, 'Collective')");
        
        // book 1
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 1, 1, current_timestamp, current_timestamp, 'Bees in the Bonnet', " +
                "'1920-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into BOOK (id, volume ) " +
                "values ( 1, null)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (1, 'AUTHOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (1, 'PUBLISHER', 5) ");
        
        // book 2
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 2, 1, current_timestamp, current_timestamp, 'A Guide to making Honey', " +
                "'1922-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into BOOK (id, volume ) " +
                "values ( 2, null)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'AUTHOR', 1) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'AUTHOR', 2) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'EDITOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (2, 'PUBLISHER', 5) ");
        
        // book 3
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 3, 1, current_timestamp, current_timestamp, 'Bees and Birds', " +
                "'1923-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into BOOK (id, volume ) " +
                "values ( 3, null)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (3, 'AUTHOR', 1) ");
        
        // chapter 2 in book 1 (id 4)
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 4, 1, current_timestamp, current_timestamp, 'Bees and Wasps', " +
                "'1920-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into CHAPTER (id, pages, chapter, book_id ) " +
                "values ( 4, '11-20', '2', 1)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'AUTHOR', 1) ");
        
        // chapter 4 in book 1 (id 5)
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 5, 1, current_timestamp, current_timestamp, 'Fighting for Clover', " +
                "'1920-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into CHAPTER (id, pages, chapter, book_id ) " +
                "values ( 5, '30-50', '4', 1)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (5, 'AUTHOR', 3) ");
        
        // chapter V in book 2 (id 6)
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 6, 1, current_timestamp, current_timestamp, 'Wax and Combs', " +
                "'1922-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into CHAPTER (id, pages, chapter, book_id ) " +
                "values ( 6, '30-48', 'V', 2)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (6, 'AUTHOR', 2) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (6, 'AUTHOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (6, 'EDITOR', 1) ");
        
        // chapter VI in book 2 (id 7)
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 7, 1, current_timestamp, current_timestamp, 'Retaining that Meadow Flavour', " +
                "'1922-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into CHAPTER (id, pages, chapter, book_id ) " +
                "values ( 7, '49-61', 'VI', 2)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (7, 'AUTHOR', 1) ");
        
        // introduction in book 3 (id 8)
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 8, 1, current_timestamp, current_timestamp, 'Swallows chasing Bees: Meadow Scenes', " +
                "'1923-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into CHAPTER (id, pages, chapter, book_id ) " +
                "values ( 8, 'i-v', 'intro', 3)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (8, 'AUTHOR', 1) ");
        
    }
    
    
    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#countChapters()}.
     */
    public final void testCountChapters() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findAllChapters()}.
     */
    public final void testFindAllChapters() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findAllChapters(int, int)}.
     */
    public final void testFindAllChaptersIntInt() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findById(long)}.
     */
    public final void testFindById() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersAfterDate(java.util.Date)}.
     */
    public final void testFindChaptersAfterDate() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersBeforeDate(java.util.Date)}.
     */
    public final void testFindChaptersBeforeDate() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindChaptersBetweenDates() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersByTitle(java.lang.String)}.
     */
    public final void testFindChaptersByTitle() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersByTitleWildcard(java.lang.String)}.
     */
    public final void testFindChaptersByTitleWildcard() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersInBook(org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork)}.
     */
    public final void testFindChaptersInBook() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersWithAuthor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindChaptersWithAuthor() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindChaptersWithAuthorOrEditor() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersWithEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindChaptersWithEditor() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#saveChapter(org.authorsite.domain.bib.Chapter)}.
     */
    public final void testSaveChapter() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#updateChapter(org.authorsite.domain.bib.Chapter)}.
     */
    public final void testUpdateChapter() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#deleteChapter(org.authorsite.domain.bib.Chapter)}.
     */
    public final void testDeleteChapter() {
        fail("Not yet implemented"); // TODO
    }

}
