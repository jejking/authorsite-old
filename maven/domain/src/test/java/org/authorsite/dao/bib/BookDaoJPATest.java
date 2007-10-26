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

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#countBooks()}.
     */
    public final void testCountBooks() {
        fail("Not yet implemented"); // TODO
    }
    
    /* (non-Javadoc)
     * @see org.springframework.test.AbstractTransactionalSpringContextTests#onSetUpInTransaction()
     */
    @Override
    protected void onSetUpInTransaction() throws Exception {
     
        super.onSetUpInTransaction();
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findAllBooks()}.
     */
    public final void testFindAllBooks() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findAllBooks(int, int)}.
     */
    public final void testFindAllBooksIntInt() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksAfterDate(java.util.Date)}.
     */
    public final void testFindBooksAfterDate() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksBeforeDate(java.util.Date)}.
     */
    public final void testFindBooksBeforeDate() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksBetweenDates(java.util.Date, java.util.Date)}.
     */
    public final void testFindBooksBetweenDates() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksByTitle(java.lang.String)}.
     */
    public final void testFindBooksByTitle() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksByTitleWildcard(java.lang.String)}.
     */
    public final void testFindBooksByTitleWildcard() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithAuthor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithAuthor() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithAuthorOrEditor() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithEditor(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithEditor() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findBooksWithPublisher(org.authorsite.domain.AbstractHuman)}.
     */
    public final void testFindBooksWithPublisher() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#findById(long)}.
     */
    public final void testFindById() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#saveBook(org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork)}.
     */
    public final void testSaveBook() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#updateBook(org.authorsite.domain.bib.Book)}.
     */
    public final void testUpdateBook() {
        fail("Not yet implemented"); // TODO
    }

    /**
     * Test method for {@link org.authorsite.dao.bib.BookDaoJPA#deleteBook(org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork)}.
     */
    public final void testDeleteBook() {
        fail("Not yet implemented"); // TODO
    }

}
