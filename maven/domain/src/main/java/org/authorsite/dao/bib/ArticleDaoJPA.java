package org.authorsite.dao.bib;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ArticleDaoJPA implements ArticleDao {

    private EntityManager entityManager;
    
    /**
     * Default constructor.
     */
    public ArticleDaoJPA() {
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
    public int countArticles() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("ArticleCount").getSingleResult();
        return n.intValue();
    }

    
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findAllArticles() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllArticles");
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findAllArticles(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllArticles");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesByTitleWildcard(String titleWildcard) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesByTitleWildcard");
        q.setParameter("title", titleWildcard);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesInJournal(Journal journal) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesInJournal");
        q.setParameter("journal", journal);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesWithAuthor(AbstractHuman author) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesWithAuthor");
        q.setParameter("author", author);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesWithAuthorOrEditor");
        q.setParameter("human", human);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<Article> findArticlesWithEditor(AbstractHuman editor) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("ArticlesWithEditor");
        q.setParameter("editor", editor);
        return q.getResultList();
    }

    @Transactional(readOnly=true)
    public Article findById(long id) throws DataAccessException {
        return this.entityManager.find(Article.class, new Long(id));
    }

    @Transactional(readOnly=false)
    public void saveArticle(Article article) throws DataAccessException {
        this.entityManager.persist(article);
    }

    @Transactional(readOnly=false)
    public Article updateArticle(Article article) throws DataAccessException {
        return this.entityManager.merge(article);
    }

    @Transactional(readOnly=false)
    public void deleteArticle(Article article) throws DataAccessException {
        this.entityManager.remove(article);
    }

}
