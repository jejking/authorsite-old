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
import org.authorsite.domain.bib.WebResource;
import org.springframework.dao.DataAccessException;

/**
 * @author jking
 *
 */
public interface WebResourceDao {

    public int countWebResources() throws DataAccessException;
    
    public WebResource findById(long id) throws DataAccessException;
    
    public void deleteWebResource(WebResource WebResource) throws DataAccessException;
    
    public void saveWebResource(WebResource WebResource) throws DataAccessException;
    
    public WebResource updateWebResource(WebResource WebResource) throws DataAccessException;
    
    public List<WebResource> findAllWebResources() throws DataAccessException;
    
    public List<WebResource> findAllWebResources(int pageNumber, int pageSize) throws DataAccessException;
    
    public List<WebResource> findWebResourcesByTitle(String title) throws DataAccessException;
    
    public List<WebResource> findWebResourcesByTitleWildcard(String titleWildcard) throws DataAccessException;
    
    public List<WebResource> findWebResourcesByDomain(String domain) throws DataAccessException;
    
    public List<WebResource> findWebResourcesAfterDate(Date date) throws DataAccessException;
    
    public List<WebResource> findWebResourcesBeforeDate(Date date) throws DataAccessException;
    
    public List<WebResource> findWebResourcesBetweenDates(Date startDate, Date endDate) throws DataAccessException;
    
    public List<WebResource> findWebResourcesWithAuthor(AbstractHuman author) throws DataAccessException;
    
    public List<WebResource> findWebResourcesWithEditor(AbstractHuman editor) throws DataAccessException;
    
    public List<WebResource> findWebResourcesWithAuthorOrEditor(AbstractHuman human) throws DataAccessException;
    
    public List<WebResource> findWebResourcesWithPublisher(AbstractHuman publisher) throws DataAccessException;
    
}
