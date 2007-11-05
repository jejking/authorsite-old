/**
 * 
 */
package org.authorsite.domain.service.bib;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Journal;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
public interface JournalService {

    @Transactional(readOnly = true)
    public abstract int countJournals() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findAllJournals() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findAllJournals(int pageNumber, int pageSize) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract Journal findById(long id) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findJournalsAfterDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findJournalsBeforeDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findJournalsBetweenDates(Date startDate, Date endDate) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findJournalsByTitle(String title) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findJournalsByTitleWildcard(String titleWildcard) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Journal> findJournalsWithPublisher(AbstractHuman publisher) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void saveJournal(Journal journal) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract Journal updateJournal(Journal journal) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void deleteJournal(Journal journal) throws DataAccessException;

}