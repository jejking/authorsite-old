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
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.WorkDates;

/**
 * @author jking
 *
 */
public class BookDaoJPATest extends AbstractJPATest {

    private BookDao bookDao;
    private IndividualDao individualDao;
    private CollectiveDao collectiveDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
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
        
        // book 4
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 4, 1, current_timestamp, current_timestamp, 'Buzzing around the Meadow', " +
                "'1927-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into BOOK (id, volume ) " +
                "values ( 4, null)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (4, 'PUBLISHER', 4) ");
        
        // book 5
        jdbcTemplate.execute("insert into WORK (id, version, createdAt, updatedAt," +
                " title, date, toDate, createdBy_id, updatedBy_id) " +
                "values( 5, 1, current_timestamp, current_timestamp, 'Buzzing around the Meadow', " +
                "'1930-01-01', null, 1, 1 )");
        
        jdbcTemplate.execute("insert into BOOK (id, volume ) " +
                "values ( 5, null)" );
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (5, 'AUTHOR', 3) ");
        
        jdbcTemplate.execute("insert into WORK_WORKPRODUCERS( work_id,workproducertype, abstracthuman_id) " +
                "values (5, 'PUBLISHER', 5) ");
    }
    
    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#countBooks()}.
     */
    public final void testCountBooks() {
        assertEquals(5, this.bookDao.countBooks());
    }
    
    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findById(long)}.
     */
    public final void testFindById() {
        Individual hBear = this.individualDao.findById(3);
        Individual hansWurst = this.individualDao.findById(2);
        Individual fooBar = this.individualDao.findById(1);
        
        Collective beehivePublishing = this.collectiveDao.findById(5);
        WorkDates workDates = new WorkDates(1920);
        Book b1 = this.bookDao.findById(1);
        assertEquals("Bees in the Bonnet", b1.getTitle());
        assertEquals( 1, b1.getAuthors().size());
        assertTrue(b1.getAuthors().contains(hBear));
        assertEquals(beehivePublishing, b1.getPublisher());
        assertEquals(workDates, b1.getWorkDates());
        
        Book b3 = this.bookDao.findById(3);
        assertNull(b3.getPublisher());
        assertEquals(1, b3.getWorkProducers().size());
        
        Book b2 = this.bookDao.findById(2);
        assertEquals(2, b2.getAuthors().size());
        assertEquals(1, b2.getEditors().size());
        assertTrue(b2.getEditors().contains(hBear));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findAllBooks()}.
     */
    public final void testFindAllBooks() {
        Book b1 = this.bookDao.findById(1);
        Book b5 = this.bookDao.findById(5);
        List<Book> books = this.bookDao.findAllBooks();
        assertEquals(5, books.size());
        assertTrue( books.contains(b1));
        assertTrue( books.contains(b5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findAllBooks(int, int)}.
     */
    public final void testFindAllBooksIntInt() {
        Book b1 = this.bookDao.findById(1);
        Book b2 = this.bookDao.findById(2);
        Book b3 = this.bookDao.findById(3);
        Book b4 = this.bookDao.findById(4);
        Book b5 = this.bookDao.findById(5);
        
        List<Book> books1 = this.bookDao.findAllBooks(1, 3);
        assertEquals(3, books1.size());
        assertTrue(books1.contains(b1));
        assertTrue(books1.contains(b2));
        assertTrue(books1.contains(b3));
        
        List<Book> books2 = this.bookDao.findAllBooks(2, 3);
        assertEquals(2, books2.size());
        assertTrue(books2.contains(b4));
        assertTrue(books2.contains(b5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksAfterDate(java.util.Date)}.
     */
    public final void testFindBooksAfterDate() {
        // find all after 1922
        Book b3 = this.bookDao.findById(3);
        Book b4 = this.bookDao.findById(4);
        Book b5 = this.bookDao.findById(5);
        
        GregorianCalendar gc = new GregorianCalendar(1922, Calendar.JANUARY, 1);
        List<Book> books = this.bookDao.findBooksAfterDate(gc.getTime());
        
        assertEquals(3, books.size());
        assertTrue(books.contains(b3));
        assertTrue(books.contains(b4));
        assertTrue(books.contains(b5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksBeforeDate(java.util.Date)}.
     */
    public final void testFindBooksBeforeDate() {
        Book b1 = this.bookDao.findById(1);
        // find before 1922 (just book1)
        GregorianCalendar gc = new GregorianCalendar(1922, Calendar.JANUARY, 1);
        List<Book> books = this.bookDao.findBooksBeforeDate(gc.getTime());
        assertEquals(1, books.size());
        assertTrue(books.contains(b1));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindBooksBetweenDates() {
        // between 1924 and 32, finds 4 and 5
        GregorianCalendar gc1924 = new GregorianCalendar(1924, Calendar.JANUARY, 1);
        GregorianCalendar gc1932 = new GregorianCalendar(1932, Calendar.JANUARY, 1);
        
        Book b4 = this.bookDao.findById(4);
        Book b5 = this.bookDao.findById(5);
        
        List<Book> books = this.bookDao.findBooksBetweenDates(gc1924.getTime(), gc1932.getTime());
        assertEquals(2, books.size());
        assertTrue(books.contains(b4));
        assertTrue(books.contains(b5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksByTitle(java.lang.String)}.
     */
    public final void testFindBooksByTitle() {
        Book b1 = this.bookDao.findById(1);
        List<Book> books = this.bookDao.findBooksByTitle("Bees in the Bonnet");
        assertEquals(1, books.size());
        assertTrue(books.contains(b1));
    }
    
    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksByTitle(java.lang.String)}.
     */
    public final void testFindBooksByTitleNotThere() {
        List<Book> books = this.bookDao.findBooksByTitle("Blah");
        assertTrue(books.isEmpty());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksByTitleWildcard(java.lang.String)}.
     */
    public final void testFindBooksByTitleWildcard() {
        Book b1 = this.bookDao.findById(1);
        Book b3 = this.bookDao.findById(3);
        
        List<Book> books = this.bookDao.findBooksByTitleWildcard("%Bee%");
        assertEquals(2, books.size());
        assertTrue(books.contains(b1));
        assertTrue(books.contains(b3));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithAuthor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithAuthor() {
        // books by authored by honey bear, b1 and b5
        Book b1 = this.bookDao.findById(1);
        Book b5 = this.bookDao.findById(5);
        
        Individual honeyBear = this.individualDao.findById(3);
        List<Book> books = this.bookDao.findBooksWithAuthor(honeyBear);
        assertEquals(2, books.size());
        assertTrue(books.contains(b1));
        assertTrue(books.contains(b5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithAuthorOrEditor() {
        Book b1 = this.bookDao.findById(1);
        Book b2 = this.bookDao.findById(2);
        Book b5 = this.bookDao.findById(5);
        
        Individual honeyBear = this.individualDao.findById(3);
        List<Book> books = this.bookDao.findBooksWithAuthorOrEditor(honeyBear);
        assertEquals(3, books.size());
        assertTrue(books.contains(b1));
        assertTrue(books.contains(b2));
        assertTrue(books.contains(b5));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithEditor() {
        Book b2 = this.bookDao.findById(2);
        
        Individual honeyBear = this.individualDao.findById(3);
        List<Book> books = this.bookDao.findBooksWithEditor(honeyBear);
        assertEquals(1, books.size());
        assertTrue(books.contains(b2));
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithPublisher(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithPublisher() {
        Collective fooPress = this.collectiveDao.findById(4);
        Book b4 = this.bookDao.findById(4);
        List<Book> books = this.bookDao.findBooksWithPublisher(fooPress);
        assertEquals(1, books.size());
        assertTrue(books.contains(b4));
    }

    

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#saveBook(org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork)}.
     */
    public final void testSaveBook() {
        Individual fooBar = this.individualDao.findById(1);
        Collective fooPress = this.collectiveDao.findById(4);
        WorkDates workDates = new WorkDates(1999);
        Book book = new Book("My Test Book");
        book.setWorkDates(workDates);
        book.addAuthor(fooBar);
        book.setPublisher(fooPress);
        book.setCreatedBy(fooBar);
        book.setUpdatedBy(fooBar);
        
        this.bookDao.saveBook(book);
        
        long id = book.getId();
        Book loaded = this.bookDao.findById(id);
        assertEquals("My Test Book", loaded.getTitle());
        assertEquals(workDates, loaded.getWorkDates());
        assertEquals(fooPress, loaded.getPublisher());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#updateBook(org.authorsite.domain.bib.Book)}.
     */
    public final void testUpdateBook() {
        Book b3 = bookDao.findById(3);
        Collective fooPress = this.collectiveDao.findById(4);
        b3.setPublisher(fooPress);
        
        this.bookDao.updateBook(b3);
        
        Book loaded = this.bookDao.findById(3);
        assertEquals(fooPress, loaded.getPublisher());
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#deleteBook(org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork)}.
     */
    public final void testDeleteBook() {
        Book b1 = this.bookDao.findById(1);
        this.bookDao.deleteBook(b1);
        Book loaded = this.bookDao.findById(1);
        assertNull(loaded);
    }

}
