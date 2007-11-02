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

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.bib.WebResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jking
 *
 */
@Transactional
@Repository
@NamedQueries( {
    @NamedQuery(name = "WebResourceCount", query = "select count(wr) from WebResource wr"),
    @NamedQuery(name = "WebResourcesByTitle", query = "select wr from WebResource wr where wr.title = :title"),
    @NamedQuery(name = "WebResourcesByTitleWildcard", query = "select wr from WebResource wr where wr.title like :title"),

    @NamedQuery(name = "WebResourcesWithAuthor", query = "select wr from WebResource wr, " + "IN (wr.workProducers) wp " + "WHERE "
            + "wp.abstractHuman = :author " + "AND "
            + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR "),
    @NamedQuery(name = "WebResourcesWithEditor", query = "select wr from WebResource wr, " + "IN (wr.workProducers) wp " + "WHERE "
            + "wp.abstractHuman = :editor " + "AND "
            + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR "),
    @NamedQuery(name = "WebResourcesWithAuthorOrEditor", query = "select wr from WebResource wr, " + "IN (wr.workProducers) wp "
            + "WHERE " + "wp.abstractHuman = :human " + "AND "
            + "(wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR " + "OR "
            + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR ) "),
    @NamedQuery(name = "WebResourcesWithPublisher", query = "select WebResource from WebResource wr, " + "IN (wr.workProducers) wp "
            + "WHERE " + "wp.abstractHuman = :publisher " + "AND "
            + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.PUBLISHER "),
    @NamedQuery(name = "AllWebResources", query = "select wr from WebResource wr order by wr.id asc"),
    @NamedQuery(name = "WebResourcesBeforeDate", query = "select wr from WebResource wr where wr.workDates.date < :date"),
    @NamedQuery(name = "WebResourcesAfterDate", query = "select wr from WebResources wr " + "where "
            + "wr.workDates.date > :date or wr.workDates.toDate > :date"),
    @NamedQuery(name = "WebResourcesBetweenDates", query = "select wr from WebResource wr " + "where "
            + "(wr.workDates.date >= :startDate OR wr.workDates.toDate >= :startDate) " + "AND "
            + "(wr.workDates.date <= :endDate OR wr.workDates.toDate <= :endDate)")
} )
public class WebResourceDaoJPA implements WebResourceDao {

    private EntityManager entityManager;
    
    /**
     * Default constructor.
     */
    public WebResourceDaoJPA() {
        super();
    }
    
    /**
     * @param entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#countWebResources()
     */
    @Transactional(readOnly=true)
    public int countWebResources() throws DataAccessException {
        Number n = (Number) this.entityManager.createNamedQuery("WebResourceCount").getSingleResult();
        return n.intValue();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findAllWebResources()
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findAllWebResources() throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllWebResources");
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findAllWebResources(int, int)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findAllWebResources(int pageNumber, int pageSize) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("AllWebResources");
        int startingRow = (pageNumber - 1) * pageSize;
        q.setFirstResult(startingRow);
        q.setMaxResults(pageSize);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findById(long)
     */
    @Transactional(readOnly=true)
    public WebResource findById(long id) throws DataAccessException {
        return this.entityManager.find(WebResource.class, new Long(id));
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesAfterDate(java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesAfterDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesAfterDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesBeforeDate(java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesBeforeDate(Date date) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesBeforeDate");
        q.setParameter("date", date);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesBetweenDates(java.util.Date, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesBetweenDates(Date startDate, Date endDate) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesBetweenDates");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesByDomain(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesByDomain(String domain) throws DataAccessException {
        String urlWildcard = "http://" + domain;
        Query q = this.entityManager.createNamedQuery("WebResourcesByUrlWildcard");
        q.setParameter("url", urlWildcard);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesByTitle(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesByTitle(String title) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesByTitle");
        q.setParameter("title", title);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesByTitleWildcard(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesByTitleWildcard(String titleWildcard) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesByTitleWildcard");
        q.setParameter("title", titleWildcard);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesWithAuthor(org.authorsite.domain.AbstractHuman)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithAuthor(AbstractHuman author) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesWithAuthor");
        q.setParameter("author", author);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesWithAuthorOrEditor(org.authorsite.domain.AbstractHuman)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesWithAuthorOrEditor");
        q.setParameter("human", human);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesWithEditor(org.authorsite.domain.AbstractHuman)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithEditor(AbstractHuman editor) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesWithEditor");
        q.setParameter("editor", editor);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#findWebResourcesWithPublisher(org.authorsite.domain.AbstractHuman)
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly=true)
    public List<WebResource> findWebResourcesWithPublisher(AbstractHuman publisher) throws DataAccessException {
        Query q = this.entityManager.createNamedQuery("WebResourcesWithPublisher");
        q.setParameter("publisher", publisher);
        return q.getResultList();
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#saveWebResource(org.authorsite.domain.bib.WebResource)
     */
    public void saveWebResource(WebResource webResource) throws DataAccessException {
        this.entityManager.persist(webResource);
    }

    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#updateWebResource(org.authorsite.domain.bib.WebResource)
     */
    public WebResource updateWebResource(WebResource webResource) throws DataAccessException {
        return this.entityManager.merge(webResource);
    }
    
    /* (non-Javadoc)
     * @see org.authorsite.dao.bib.WebResourceDao#deleteWebResource(org.authorsite.domain.bib.WebResource)
     */
    public void deleteWebResource(WebResource webResource) throws DataAccessException {
        this.entityManager.remove(webResource);
    }

}
