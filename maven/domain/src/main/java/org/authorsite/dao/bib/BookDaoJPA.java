package org.authorsite.dao.bib;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Book;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class BookDaoJPA implements BookDao {

    private EntityManager entityManager;
    
    public BookDaoJPA() {
        super();
    }
    
    /**
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly=true)
    public int countBooks() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("BookCount").getSingleResult();
        return n.intValue();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findAllBooks() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllBooks");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findAllBooks(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllBooks");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksByTitleWildcard(String titleWildcard) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksByTitleWildcard");
        q.setParameter("title", titleWildcard);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksWithAuthor(AbstractHuman author) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksWithAuthor");
        q.setParameter("author", author);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksWithAuthorOrEditor");
        q.setParameter("human", human);
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksWithEditor(AbstractHuman editor) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksWithEditor");
        q.setParameter("editor", editor);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Book> findBooksWithPublisher(AbstractHuman publisher) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("BooksWithPublisher");
        q.setParameter("publisher", publisher);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    public Book findById(long id) throws DataAccessException {
        return this.entityManager.find(Book.class, new Long(id));
    }

    @Transactional(readOnly=false)
    public void saveBook(Book book) throws DataAccessException {
        this.entityManager.persist(book);
    }

    @Transactional(readOnly=false)
    public Book updateBook(Book book) throws DataAccessException {
        return this.entityManager.merge(book);
    }

    @Transactional(readOnly=false)
    public void deleteBook(Book book) throws DataAccessException {
        this.entityManager.remove(book);
    }

}
