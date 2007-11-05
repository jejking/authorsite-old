/**
 * 
 */
package org.authorsite.domain.service.bib;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
public interface ChapterService {

    @Transactional(readOnly = true)
    public abstract int countChapters() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findAllChapters() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findAllChapters(int pageNumber, int pageSize) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract Chapter findById(long id) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersAfterDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersBeforeDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersBetweenDates(Date startDate, Date endDate) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersByTitle(String title) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersByTitleWildcard(String titleWildcard) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersInBook(Book book) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersWithAuthor(AbstractHuman author) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Chapter> findChaptersWithEditor(AbstractHuman editor) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void saveChapter(Chapter chapter) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract Chapter updateChapter(Chapter chapter) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void deleteChapter(Chapter chapter) throws DataAccessException;

}