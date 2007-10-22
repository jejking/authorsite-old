package org.authorsite.dao.bib;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.AbstractAuthoredEditedPublishedWork;
import org.authorsite.domain.bib.Chapter;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ChapterDaoJPA implements ChapterDao {

    private EntityManager entityManager;
    
    public ChapterDaoJPA() {
        super();
    }
    
    /**
     * Sets the entity manager reference.
     * 
     * <p>IoC use only.</p>
     * 
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly=true)
    public int countChapters() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("ChapterCount").getSingleResult();
        return n.intValue();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findAllChapters() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllChapters");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findAllChapters(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllChapters");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    public Chapter findById(long id) throws DataAccessException {
        return this.entityManager.find(Chapter.class, new Long(id));
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersByTitleWildcard(String titleWildcard) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersByTitleWildcard");
        q.setParameter("title", titleWildcard);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersInBook(AbstractAuthoredEditedPublishedWork book) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersInBook");
        q.setParameter("book", book);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersWithAuthor(AbstractHuman author) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersWithAuthor");
        q.setParameter("author", author);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersWithAuthorOrEditor");
        q.setParameter("human", human);
        return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersWithEditor(AbstractHuman editor) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ChaptersWithEditor");
        q.setParameter("editor", editor);
        return q.getResultList();
    }

    @Transactional(readOnly=false)
    public void saveChapter(Chapter chapter) throws DataAccessException {
        this.entityManager.persist(chapter);
    }

    @Transactional(readOnly=false)
    public Chapter updateChapter(Chapter chapter) throws DataAccessException {
        return this.entityManager.merge(chapter);
    }

    @Transactional(readOnly=false)
    public void deleteChapter(Chapter chapter) throws DataAccessException {
        this.entityManager.remove(chapter);
    }
}
