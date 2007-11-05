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
package org.authorsite.domain.service.bib.impl;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.apache.log4j.Logger;
import org.authorsite.dao.bib.ChapterDao;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.service.bib.ChapterService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
public class ChapterServiceImpl implements ChapterService {

    private ChapterDao chapterDao;
    
    /**
     * 
     */
    public ChapterServiceImpl() {
        super();
    }

    public void setChapterDao(ChapterDao chapterDao) {
        this.chapterDao = chapterDao;
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#countChapters()
     */
    @Transactional(readOnly=true)
    public int countChapters() throws DataAccessException {
        return this.chapterDao.countChapters();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findAllChapters()
     */
    @Transactional(readOnly=true)
    public List<Chapter> findAllChapters() throws DataAccessException {
        return this.chapterDao.findAllChapters();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findAllChapters(int, int)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findAllChapters(int pageNumber, int pageSize) throws DataAccessException {
        return this.chapterDao.findAllChapters(pageNumber, pageSize);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findById(long)
     */
    @Transactional(readOnly=true)
    public Chapter findById(long id) throws DataAccessException {
        return this.chapterDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersAfterDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersAfterDate(Date date) throws DataAccessException {
        return this.chapterDao.findChaptersAfterDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersBeforeDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersBeforeDate(Date date) throws DataAccessException {
        return this.chapterDao.findChaptersBeforeDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersBetweenDates(java.util.Date, java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.chapterDao.findChaptersBetweenDates(startDate, endDate);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersByTitle(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersByTitle(String title) throws DataAccessException {
        return this.chapterDao.findChaptersByTitle(title);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersByTitleWildcard(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.chapterDao.findChaptersByTitleWildcard(titleWildcard);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersInBook(org.authorsite.domain.bib.Book)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersInBook(Book book) throws DataAccessException {
        return this.chapterDao.findChaptersInBook(book);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersWithAuthor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersWithAuthor(AbstractHuman author) throws DataAccessException {
        return this.chapterDao.findChaptersWithAuthor(author);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        return this.chapterDao.findChaptersWithAuthorOrEditor(human);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#findChaptersWithEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Chapter> findChaptersWithEditor(AbstractHuman editor) throws DataAccessException {
        return this.chapterDao.findChaptersWithEditor(editor);
    }
    
    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#saveChapter(org.authorsite.domain.bib.Chapter)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveChapter(Chapter chapter) throws DataAccessException {
        this.chapterDao.saveChapter(chapter);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#updateChapter(org.authorsite.domain.bib.Chapter)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Chapter updateChapter(Chapter chapter) throws DataAccessException {
        return this.chapterDao.updateChapter(chapter);
    }
    
    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ChapterService#deleteChapter(org.authorsite.domain.bib.Chapter)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteChapter(Chapter chapter) throws DataAccessException {
        this.chapterDao.deleteChapter(chapter);
    }

}
