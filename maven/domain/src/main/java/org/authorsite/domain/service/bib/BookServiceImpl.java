/**
 * 
 */
package org.authorsite.domain.service.bib;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.apache.log4j.Logger;
import org.authorsite.dao.bib.BookDao;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
public class BookServiceImpl implements BookService {

    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    
    private BookDao bookDao;

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#countBooks()
     */
    public int countBooks() throws DataAccessException {
        return this.bookDao.countBooks();
    }
    
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findAllBooks()
     */
    @Transactional(readOnly = true)
    public List<Book> findAllBooks() throws DataAccessException {
        return this.bookDao.findAllBooks();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findAllBooks(int, int)
     */
    @Transactional(readOnly = true)
    public List<Book> findAllBooks(int pageNumber, int pageSize) throws DataAccessException {
        return this.bookDao.findAllBooks(pageNumber, pageSize);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksAfterDate(java.util.Date)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksAfterDate(Date date) throws DataAccessException {
        return this.bookDao.findBooksAfterDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksBeforeDate(java.util.Date)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksBeforeDate(Date date) throws DataAccessException {
        return this.bookDao.findBooksBeforeDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksBetweenDates(java.util.Date, java.util.Date)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.bookDao.findBooksBetweenDates(startDate, endDate);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksByTitle(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksByTitle(String title) throws DataAccessException {
        return this.bookDao.findBooksByTitle(title);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksByTitleWildcard(java.lang.String)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.bookDao.findBooksByTitleWildcard(titleWildcard);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksWithAuthor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksWithAuthor(AbstractHuman author) throws DataAccessException {
        return this.bookDao.findBooksWithAuthor(author);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        return this.bookDao.findBooksWithAuthorOrEditor(human);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksWithEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksWithEditor(AbstractHuman editor) throws DataAccessException {
        return this.bookDao.findBooksWithEditor(editor);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findBooksWithPublisher(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly = true)
    public List<Book> findBooksWithPublisher(AbstractHuman publisher) throws DataAccessException {
        return this.bookDao.findBooksWithPublisher(publisher);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#findById(long)
     */
    @Transactional(readOnly = true)
    public Book findById(long id) throws DataAccessException {
        return this.bookDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#saveBook(org.authorsite.domain.bib.Book)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveBook(Book book) throws DataAccessException {
        this.bookDao.saveBook(book);
        LOGGER.info("Book saved" + book);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#updateBook(org.authorsite.domain.bib.Book)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Book updateBook(Book book) throws DataAccessException {
        LOGGER.info("Book updated" + book);
        return this.bookDao.updateBook(book);
    }
    
    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.BookService#deleteBook(org.authorsite.domain.bib.Book)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteBook(Book book) throws DataAccessException {
        this.bookDao.deleteBook(book);
        LOGGER.info("Book deleted" +  book);
    }
}
