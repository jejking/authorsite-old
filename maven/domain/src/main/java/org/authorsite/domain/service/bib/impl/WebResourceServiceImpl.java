/**
 * 
 */
package org.authorsite.domain.service.bib.impl;

import java.util.Date;
import java.util.List;

import org.acegisecurity.annotation.Secured;
import org.apache.log4j.Logger;
import org.authorsite.dao.bib.WebResourceDao;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.WebResource;
import org.authorsite.domain.service.bib.WebResourceService;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
public class WebResourceServiceImpl implements WebResourceService {

    private static final Logger LOGGER = Logger.getLogger(WebResourceServiceImpl.class);
    
    private WebResourceDao webResourceDao;
    
    /**
     * 
     */
    public WebResourceServiceImpl() {
        super();
    }

    public void setWebResourceDao(WebResourceDao webResourceDao) {
        this.webResourceDao = webResourceDao;
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#countWebResources()
     */
    @Transactional(readOnly=true)
    public int countWebResources() throws DataAccessException {
        return this.webResourceDao.countWebResources();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findAllWebResources()
     */
    @Transactional(readOnly=true)
    public List<WebResource> findAllWebResources() throws DataAccessException {
        return this.webResourceDao.findAllWebResources();
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findAllWebResources(int, int)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findAllWebResources(int pageNumber, int pageSize) throws DataAccessException {
        return this.webResourceDao.findAllWebResources(pageNumber, pageSize);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findById(long)
     */
    @Transactional(readOnly=true)
    public WebResource findById(long id) throws DataAccessException {
        return this.webResourceDao.findById(id);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesAfterDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesAfterDate(Date date) throws DataAccessException {
        return this.webResourceDao.findWebResourcesAfterDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesBeforeDate(java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesBeforeDate(Date date) throws DataAccessException {
        return this.webResourceDao.findWebResourcesBeforeDate(date);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesBetweenDates(java.util.Date, java.util.Date)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        return this.webResourceDao.findWebResourcesBetweenDates(startDate, endDate);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesByDomain(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesByDomain(String domain) throws DataAccessException {
        return this.webResourceDao.findWebResourcesByDomain(domain);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesByTitle(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesByTitle(String title) throws DataAccessException {
        return this.webResourceDao.findWebResourcesByTitle(title);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesByTitleWildcard(java.lang.String)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesByTitleWildcard(String titleWildcard) throws DataAccessException {
        return this.webResourceDao.findWebResourcesByTitleWildcard(titleWildcard);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesWithAuthor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithAuthor(AbstractHuman author) throws DataAccessException {
        return this.webResourceDao.findWebResourcesWithAuthor(author);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        return this.webResourceDao.findWebResourcesWithAuthorOrEditor(human);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesWithEditor(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithEditor(AbstractHuman editor) throws DataAccessException {
        return this.webResourceDao.findWebResourcesWithEditor(editor);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#findWebResourcesWithPublisher(org.authorsite.domain.AbstractHuman)
     */
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithPublisher(AbstractHuman publisher) throws DataAccessException {
        return this.webResourceDao.findWebResourcesWithPublisher(publisher);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#saveWebResource(org.authorsite.domain.bib.WebResource)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void saveWebResource(WebResource WebResource) throws DataAccessException {
        this.webResourceDao.saveWebResource(WebResource);
    }

    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#updateWebResource(org.authorsite.domain.bib.WebResource)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public WebResource updateWebResource(WebResource WebResource) throws DataAccessException {
        return this.webResourceDao.updateWebResource(WebResource);
    }
    
    /* (non-Javadoc)
     * @see org.authorsite.domain.service.bib.WebResourceService#deleteWebResource(org.authorsite.domain.bib.WebResource)
     */
    @Secured( { "ROLE_ADMINISTRATOR", "ROLE_EDITOR" })
    public void deleteWebResource(WebResource WebResource) throws DataAccessException {
        this.webResourceDao.deleteWebResource(WebResource);
    }

}
