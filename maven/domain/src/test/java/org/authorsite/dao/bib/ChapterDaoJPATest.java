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
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;

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
        assertEquals(5, this.chapterDao.countChapters());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findAllChapters()}.
     */
    public final void testFindAllChapters() {
        Chapter chapter5 = this.chapterDao.findById(5);
        Chapter chapter8 = this.chapterDao.findById(8);
        
        List<Chapter> chapters = this.chapterDao.findAllChapters();
        assertEquals(5, chapters.size());
        assertTrue(chapters.contains(chapter5));
        assertTrue(chapters.contains(chapter8));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findAllChapters(int, int)}.
     */
    public final void testFindAllChaptersIntInt() {
        Chapter chapter4 = this.chapterDao.findById(4);
        Chapter chapter8 = this.chapterDao.findById(8);
        
        List<Chapter> chapList1 = this.chapterDao.findAllChapters(1, 2);
        assertEquals(2, chapList1.size());
        assertTrue(chapList1.contains(chapter4));
        assertFalse(chapList1.contains(chapter8));
        
        List<Chapter> chapList2 = this.chapterDao.findAllChapters(3, 2);
        assertEquals(1, chapList2.size());
        assertTrue(chapList2.contains(chapter8));
       
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findById(long)}.
     */
    public final void testFindById() {
        Individual fooBar = this.individualDao.findById(1);
        Book beesInTheBonnet = this.bookDao.findById(1);
        
        Chapter chapter4 = this.chapterDao.findById(4);
        assertTrue(chapter4.getAuthors().contains(fooBar));
        assertEquals(beesInTheBonnet, chapter4.getBook());
        assertEquals("11-20", chapter4.getPages());
        assertEquals("2", chapter4.getChapter());
        
        Chapter chapter6 = this.chapterDao.findById(6);
        assertEquals(3, chapter6.getWorkProducers().size());
        assertTrue(chapter6.getEditors().contains(fooBar));
        
        Chapter notThere = this.chapterDao.findById(666);
        assertNull(notThere);
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersAfterDate(java.util.Date)}.
     */
    public final void testFindChaptersAfterDate() {
        GregorianCalendar gc = new GregorianCalendar( 1922, Calendar.JANUARY, 1);
        Chapter chapter8 = this.chapterDao.findById(8);
        List<Chapter> chapters = this.chapterDao.findChaptersAfterDate(gc.getTime());
        assertEquals(1, chapters.size());
        assertTrue(chapters.contains(chapter8));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersBeforeDate(java.util.Date)}.
     */
    public final void testFindChaptersBeforeDate() {
        GregorianCalendar gc = new GregorianCalendar( 1922, Calendar.JANUARY, 1);
        Chapter chapter4 = this.chapterDao.findById(4);
        Chapter chapter5 = this.chapterDao.findById(5);
        List<Chapter> chapters = this.chapterDao.findChaptersBeforeDate(gc.getTime());
        assertEquals(2, chapters.size());
        assertTrue(chapters.contains(chapter4));
        assertTrue(chapters.contains(chapter5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindChaptersBetweenDates() {
        GregorianCalendar gcStart = new GregorianCalendar( 1921, Calendar.JANUARY, 1);
        GregorianCalendar gcEnd = new GregorianCalendar(1922, Calendar.DECEMBER, 1);
        
        Chapter chapter6 = this.chapterDao.findById(6);
        Chapter chapter7 = this.chapterDao.findById(7);
        
        List<Chapter> chapters = this.chapterDao.findChaptersBetweenDates(gcStart.getTime(), gcEnd.getTime());
        assertEquals(2, chapters.size());
        assertTrue(chapters.contains(chapter6));
        assertTrue(chapters.contains(chapter7));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersByTitle(java.lang.String)}.
     */
    public final void testFindChaptersByTitle() {
        Chapter chapter4 = this.chapterDao.findById(4);
        List<Chapter> chapters = this.chapterDao.findChaptersByTitle("Bees and Wasps");
        assertEquals(1, chapters.size());
        assertTrue(chapters.contains(chapter4));
        
        List<Chapter> notFound = this.chapterDao.findChaptersByTitle("Blah Wibble Foo");
        assertTrue(notFound.isEmpty());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersByTitleWildcard(java.lang.String)}.
     */
    public final void testFindChaptersByTitleWildcard() {
        Chapter chapter7  = this.chapterDao.findById(7);
        Chapter chapter8  = this.chapterDao.findById(8);
        List<Chapter> chapters = this.chapterDao.findChaptersByTitleWildcard("%Meadow%");
        assertEquals(2, chapters.size());
        assertTrue(chapters.contains(chapter7));
        assertTrue(chapters.contains(chapter8));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersInBook(Book)}.
     */
    public final void testFindChaptersInBook() {
        Book book1 = this.bookDao.findById(1);
        Chapter chapter4 = this.chapterDao.findById(4);
        Chapter chapter5 = this.chapterDao.findById(5);
        
        List<Chapter> chapters = this.chapterDao.findChaptersInBook(book1);
        assertEquals(2, chapters.size());
        assertTrue(chapters.contains(chapter4));
        assertTrue(chapters.contains(chapter5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersWithAuthor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindChaptersWithAuthor() {
        Individual honeyBear = this.individualDao.findById(3);
        Chapter chapter5 = this.chapterDao.findById(5);
        Chapter chapter6 = this.chapterDao.findById(6);
        
        List<Chapter> chapters = this.chapterDao.findChaptersWithAuthor(honeyBear);
        assertEquals(2, chapters.size());
        assertTrue(chapters.contains(chapter5));
        assertTrue(chapters.contains(chapter6));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindChaptersWithAuthorOrEditor() {
        Individual fooBar = this.individualDao.findById(1);
        List<Chapter> chList = this.chapterDao.findChaptersWithAuthorOrEditor(fooBar);
        assertEquals(4, chList.size());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#findChaptersWithEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindChaptersWithEditor() {
        Individual fooBar = this.individualDao.findById(1);
        Chapter chapter6 = this.chapterDao.findById(6);
        List<Chapter> chapters = this.chapterDao.findChaptersWithEditor(fooBar);
        assertEquals(1, chapters.size());
        assertTrue(chapters.contains(chapter6));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#saveChapter(org.authorsite.domain.bib.Chapter)}.
     */
    public final void testSaveChapter() {
        Individual fooBar = this.individualDao.findById(1);
        Individual hansWurst = this.individualDao.findById(2);
        
        Book book1 = this.bookDao.findById(1);
        
        Chapter chapter = new Chapter("My Test Chapter", book1);
        chapter.setPages("40-42");
        chapter.setChapter("5");
        chapter.setCreatedBy(fooBar);
        chapter.setUpdatedBy(fooBar);
        chapter.addAuthor(hansWurst);
        
        this.chapterDao.saveChapter(chapter);
        
        Chapter loaded = this.chapterDao.findById(chapter.getId());
        assertTrue(loaded.getAuthors().contains(hansWurst));
        assertEquals(book1, loaded.getBook());
        assertEquals("40-42", chapter.getPages());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#updateChapter(org.authorsite.domain.bib.Chapter)}.
     */
    public final void testUpdateChapter() {
        Chapter chapter4 = this.chapterDao.findById(4);
        chapter4.setChapter("23");
        chapter4.setPages("22-40");
        this.chapterDao.updateChapter(chapter4);
        
        Chapter loaded = this.chapterDao.findById(4);
        assertEquals("23", loaded.getChapter());
        assertEquals("22-40", loaded.getPages());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.ChapterDaoJPA#deleteChapter(org.authorsite.domain.bib.Chapter)}.
     */
    public final void testDeleteChapter() {
        Chapter chapter4 = this.chapterDao.findById(4);
        this.chapterDao.deleteChapter(chapter4);
        
        Chapter loaded = this.chapterDao.findById(4);
        assertNull(loaded);
    }

}
