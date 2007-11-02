/**
 * 
 */
package org.authorsite.domain.service.bib;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
public interface BookService {

    public abstract int countBooks() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findAllBooks() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findAllBooks(int pageNumber, int pageSize) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksAfterDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksBeforeDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksBetweenDates(Date startDate, Date endDate) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksByTitle(String title) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksByTitleWildcard(String titleWildcard) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksWithAuthor(AbstractHuman author) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksWithEditor(AbstractHuman editor) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Book> findBooksWithPublisher(AbstractHuman publisher) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract Book findById(long id) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void saveBook(Book book) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract Book updateBook(Book book) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void deleteBook(Book book) throws DataAccessException;

}