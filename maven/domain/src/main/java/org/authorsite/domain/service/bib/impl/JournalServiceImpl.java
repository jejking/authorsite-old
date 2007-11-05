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
import org.authorsite.dao.bib.JournalDao;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.service.bib.JournalService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
public class JournalServiceImpl implements JournalService {

    private JournalDao journalDao;
    
    /**
     * 
     */
    public JournalServiceImpl() {
        super();
    }
    
    public void setJournalDao(JournalDao journalDao) {
        this.journalDao = journalDao;
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#countJournals()
     */
    @Transactional(readOnly=true)
    public int countJournals() throws DataAccessException {
        return this.journalDao.countJournals();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findAllJournals()
     */
    @Transactional(readOnly=true)
    public List<Journal> findAllJournals() throws DataAccessException {
        return this.journalDao.findAllJournals();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findAllJournals(int, int)
     */
    @Transactional(readOnly=true)
    public List<Journal> findAllJournals(int pageNumber, int pageSize) throws DataAccessException {
        return this.journalDao.findAllJournals(pageNumber, pageSize);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findById(long)
     */
    @Transactional(readOnly=true)
    public Journal findById(long id) throws DataAccessException {
        return this.journalDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findJournalsAfterDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Journal> findJournalsAfterDate(Date date) throws DataAccessException {
        return this.journalDao.findJournalsAfterDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findJournalsBeforeDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Journal> findJournalsBeforeDate(Date date) throws DataAccessException {
        return this.journalDao.findJournalsBeforeDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findJournalsBetweenDates(java.util.Date, java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Journal> findJournalsBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.journalDao.findJournalsBetweenDates(startDate, endDate);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findJournalsByTitle(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Journal> findJournalsByTitle(String title) throws DataAccessException {
        return this.journalDao.findJournalsByTitle(title);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findJournalsByTitleWildcard(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Journal> findJournalsByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.journalDao.findJournalsByTitleWildcard(titleWildcard);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#findJournalsWithPublisher(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<Journal> findJournalsWithPublisher(AbstractHuman publisher) throws DataAccessException {
        return this.journalDao.findJournalsWithPublisher(publisher);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#saveJournal(org.authorsite.domain.bib.Journal)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveJournal(Journal journal) throws DataAccessException {
        this.journalDao.saveJournal(journal);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#updateJournal(org.authorsite.domain.bib.Journal)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Journal updateJournal(Journal journal) throws DataAccessException {
        return this.journalDao.updateJournal(journal);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.JournalService#deleteJournal(org.authorsite.domain.bib.Journal)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteJournal(Journal journal) throws DataAccessException {
        this.journalDao.deleteJournal(journal);
    }
}
