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
import org.authorsite.dao.bib.ThesisDao;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.service.bib.ThesisService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
public class ThesisServiceImpl implements ThesisService {

    private ThesisDao thesisDao;
    
    /**
     * Default constructor
     */
    public ThesisServiceImpl() {
        super();
    }

    public void setThesisDao(ThesisDao thesisDao) {
        this.thesisDao = thesisDao;
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#countTheses()
     */
    @Transactional(readOnly=true)
    public int countTheses() throws DataAccessException {
        return this.thesisDao.countTheses();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findAllTheses()
     */
    @Transactional(readOnly=true)
    public List<Thesis> findAllTheses() throws DataAccessException {
        return this.thesisDao.findAllTheses();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findAllTheses(int, int)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findAllTheses(int pageNumber, int pageSize) throws DataAccessException {
        return this.thesisDao.findAllTheses(pageNumber, pageSize);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findById(long)
     */
    @Transactional(readOnly=true)
    public Thesis findById(long id) throws DataAccessException {
        return this.thesisDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesAfterDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findThesesAfterDate(Date date) throws DataAccessException {
        return this.thesisDao.findThesesAfterDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesBeforeDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findThesesBeforeDate(Date date) throws DataAccessException {
        return this.thesisDao.findThesesBeforeDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesBetweenDates(java.util.Date, java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findThesesBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.thesisDao.findThesesBetweenDates(startDate, endDate);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesByTitle(java.lang.String)
     */
    public List<Thesis> findThesesByTitle(String title) throws DataAccessException {
        return this.thesisDao.findThesesByTitle(title);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesByTitleWildcard(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findThesesByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.thesisDao.findThesesByTitleWildcard(titleWildcard);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesWithAuthor(org.authorsite.domain.Individual)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findThesesWithAuthor(Individual author) throws DataAccessException {
        return this.thesisDao.findThesesWithAuthor(author);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#findThesesWithAwardingBody(org.authorsite.domain.Collective)
     */
    @Transactional(readOnly=true)
    public List<Thesis> findThesesWithAwardingBody(Collective awardingBody) throws DataAccessException {
        return this.thesisDao.findThesesWithAwardingBody(awardingBody);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#saveThesis(org.authorsite.domain.bib.Thesis)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveThesis(Thesis thesis) throws DataAccessException {
        this.thesisDao.saveThesis(thesis);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#updateThesis(org.authorsite.domain.bib.Thesis)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public Thesis updateThesis(Thesis thesis) throws DataAccessException {
        return this.thesisDao.updateThesis(thesis);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.ThesisService#deleteThesis(org.authorsite.domain.bib.Thesis)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteThesis(Thesis thesis) throws DataAccessException {
        this.thesisDao.deleteThesis(thesis);
    }
    
}
