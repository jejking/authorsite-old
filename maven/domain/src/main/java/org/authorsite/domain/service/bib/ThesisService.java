/**
 * 
 */
package org.authorsite.domain.service.bib;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
public interface ThesisService {

    @Transactional(readOnly = true)
    public abstract int countTheses() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findAllTheses() throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findAllTheses(int pageNumber, int pageSize) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract Thesis findById(long id) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findThesesAfterDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findThesesBeforeDate(Date date) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findThesesBetweenDates(Date startDate, Date endDate) throws DataAccessException;

    public abstract List<Thesis> findThesesByTitle(String title) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findThesesByTitleWildcard(String titleWildcard) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findThesesWithAuthor(Individual author) throws DataAccessException;

    @Transactional(readOnly = true)
    public abstract List<Thesis> findThesesWithAwardingBody(Collective awardingBody) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void saveThesis(Thesis thesis) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract Thesis updateThesis(Thesis thesis) throws DataAccessException;

    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public abstract void deleteThesis(Thesis thesis) throws DataAccessException;

}