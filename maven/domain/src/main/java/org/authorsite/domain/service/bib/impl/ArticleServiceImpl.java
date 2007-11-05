/**
 * 
 */
package org.authorsite.domain.service.bib.impl;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.dao.bib.ArticleDao;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.service.bib.ArticleService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;
    
    /**
     * 
     */
    public ArticleServiceImpl() {
        super();
    }
    
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }



    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#countArticles()
     */
    @Transactional(readOnly=true)
    public int countArticles() throws DataAccessException {
        return this.articleDao.countArticles();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findAllArticles()
     */
    @Transactional(readOnly=true)
    public List<Article> findAllArticles() throws DataAccessException {
        return this.articleDao.findAllArticles();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findAllArticles(int, int)
     */
    @Transactional(readOnly=true)
    public List<Article> findAllArticles(int pageNumber, int pageSize) throws DataAccessException {
        return this.articleDao.findAllArticles(pageNumber, pageSize);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesAfterDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesAfterDate(Date date) throws DataAccessException {
        return this.articleDao.findArticlesAfterDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesBeforeDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesBeforeDate(Date date) throws DataAccessException {
        return this.articleDao.findArticlesBeforeDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesBetweenDates(java.util.Date, java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.articleDao.findArticlesBetweenDates(startDate, endDate);
    }
    
    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesByTitle(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesByTitle(String title) throws DataAccessException {
        return this.articleDao.findArticlesByTitle(title);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesByTitleWildcard(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.articleDao.findArticlesByTitleWildcard(titleWildcard);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesInJournal(org.authorsite.domain.bib.Journal)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesInJournal(Journal journal) throws DataAccessException {
        return this.articleDao.findArticlesInJournal(journal);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesWithAuthor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesWithAuthor(AbstractHuman author) throws DataAccessException {
        return this.articleDao.findArticlesWithAuthor(author);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        return this.articleDao.findArticlesWithAuthorOrEditor(human);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findArticlesWithEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Article> findArticlesWithEditor(AbstractHuman editor) throws DataAccessException {
        return this.articleDao.findArticlesWithEditor(editor);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#findById(long)
     */
    @Transactional(readOnly=true)
    public Article findById(long id) throws DataAccessException {
        return this.articleDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#saveArticle(org.authorsite.domain.bib.Article)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveArticle(Article article) throws DataAccessException {
        this.articleDao.saveArticle(article);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#updateArticle(org.authorsite.domain.bib.Article)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Article updateArticle(Article article) throws DataAccessException {
        return this.articleDao.updateArticle(article);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ArticleService#deleteArticle(org.authorsite.domain.bib.Article)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteArticle(Article article) throws DataAccessException {
        this.articleDao.deleteArticle(article);
    }
}
