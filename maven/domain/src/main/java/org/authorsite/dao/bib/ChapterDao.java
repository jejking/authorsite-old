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
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author jejking
 */
public interface ChapterDao {

    public int countChapters() throws DataAccessException;
    
    public Chapter findById(long id) throws DataAccessException;
    
    public void deleteChapter(Chapter Chapter) throws DataAccessException;
    
    public void saveChapter(Chapter Chapter) throws DataAccessException;
    
    public Chapter updateChapter(Chapter Chapter) throws DataAccessException;
    
    public List<Chapter> findAllChapters() throws DataAccessException;
    
    public List<Chapter> findAllChapters(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<Chapter> findChaptersByTitle(String title) throws DataAccessException;
    
    public List<Chapter> findChaptersByTitleWildcard(String titleWildcard) throws DataAccessException;
    
    public List<Chapter> findChaptersAfterDate(Date date) throws DataAccessException;
    
    public List<Chapter> findChaptersBeforeDate(Date date) throws DataAccessException;
    
    public List<Chapter> findChaptersBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
    public List<Chapter> findChaptersWithAuthor(AbstractHuman author) throws DataAccessException;
    
    public List<Chapter> findChaptersWithEditor(AbstractHuman editor) throws DataAccessException;
    
    public List<Chapter> findChaptersWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;
    
    public List<Chapter> findChaptersInBook(Book book) throws DataAccessException;

}
