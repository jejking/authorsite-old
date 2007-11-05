/**
 * 
 */
package org.authorsite.domain.service.bib;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
public interface ArticleService {

    @Transactional(readOnly = true)
    public abstract int countArticles() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findAllArticles() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findAllArticles(int pageNumber, int pageSize) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesAfterDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesBeforeDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesBetweenDates(Date startDate, Date endDate) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesByTitle(String title) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesByTitleWildcard(String titleWildcard) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesInJournal(Journal journal) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesWithAuthor(AbstractHuman author) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Article> findArticlesWithEditor(AbstractHuman editor) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract Article findById(long id) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void saveArticle(Article article) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract Article updateArticle(Article article) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void deleteArticle(Article article) throws DataAccessException;

}