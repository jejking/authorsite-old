/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.dao.bib;

import java.util.Date;
import java.util.List;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface ArticleDao {
    
    public int countArticles() throws DataAccessException;
    
    public Article findById(long id) throws DataAccessException;
    
    public void deleteArticle(Article article) throws DataAccessException;
    
    public void saveArticle(Article article) throws DataAccessException;
    
    public Article updateArticle(Article article) throws DataAccessException;
    
    public List<Article> findAllArticles() throws DataAccessException;
    
    public List<Article> findAllArticles(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<Article> findArticlesByTitle(String title) throws DataAccessException;
    
    public List<Article> findArticlesByTitleWildcard(String titleWildcard) throws DataAccessException;
    
    public List<Article> findArticlesAfterDate(Date date) throws DataAccessException;
    
    public List<Article> findArticlesBeforeDate(Date date) throws DataAccessException;
    
    public List<Article> findArticlesBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
    public List<Article> findArticlesWithAuthor(AbstractHuman author) throws DataAccessException;
    
    public List<Article> findArticlesWithEditor(AbstractHuman editor) throws DataAccessException;
    
    public List<Article> findArticlesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;
    
    public List<Article> findArticlesInJournal(Journal journal) throws DataAccessException;

}
