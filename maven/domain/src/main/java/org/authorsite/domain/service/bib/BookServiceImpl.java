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
public class BookServiceImpl {

    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class);
    
    private BookDao bookDao;

    public int countBooks() throws DataAccessException {
        return this.bookDao.countBooks();
    }
    
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBooks() throws DataAccessException {
        return this.bookDao.findAllBooks();
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBooks(int pageNumber, int pageSize) throws DataAccessException {
        return this.bookDao.findAllBooks(pageNumber, pageSize);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksAfterDate(Date date) throws DataAccessException {
        return this.bookDao.findBooksAfterDate(date);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksBeforeDate(Date date) throws DataAccessException {
        return this.bookDao.findBooksBeforeDate(date);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.bookDao.findBooksBetweenDates(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByTitle(String title) throws DataAccessException {
        return this.bookDao.findBooksByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.bookDao.findBooksByTitleWildcard(titleWildcard);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksWithAuthor(AbstractHuman author) throws DataAccessException {
        return this.bookDao.findBooksWithAuthor(author);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        return this.bookDao.findBooksWithAuthorOrEditor(human);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksWithEditor(AbstractHuman editor) throws DataAccessException {
        return this.bookDao.findBooksWithEditor(editor);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksWithPublisher(AbstractHuman publisher) throws DataAccessException {
        return this.bookDao.findBooksWithPublisher(publisher);
    }

    @Transactional(readOnly = true)
    public Book findById(long id) throws DataAccessException {
        return this.bookDao.findById(id);
    }

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveBook(Book book) throws DataAccessException {
        this.bookDao.saveBook(book);
        LOGGER.info("Book saved" + book);
    }

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Book updateBook(Book book) throws DataAccessException {
        LOGGER.info("Book updated" + book);
        return this.bookDao.updateBook(book);
    }
    
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteBook(Book book) throws DataAccessException {
        this.bookDao.deleteBook(book);
        LOGGER.info("Book deleted" +  book);
    }
}
