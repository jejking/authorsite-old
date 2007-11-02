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
package org.authorsite.domain.bib;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
@NamedQueries( {
    @NamedQuery(name = "WebResourceCount", query = "select count(wr) from WebResource wr"),
    @NamedQuery(name = "WebResourcesByTitle", query = "select wr from WebResource wr where wr.title = :title"),
    @NamedQuery(name = "WebResourcesByTitleWildcard", query = "select wr from WebResource wr where wr.title like :title"),
    @NamedQuery(name = "WebResourcesByUrlWildcard", query ="select wr from WebResource wr where wr.url like :url"),
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
    @NamedQuery(name = "WebResourcesWithPublisher", query = "select wr from WebResource wr, " + "IN (wr.workProducers) wp "
            + "WHERE " + "wp.abstractHuman = :publisher " + "AND "
            + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.PUBLISHER "),
    @NamedQuery(name = "AllWebResources", query = "select wr from WebResource wr order by wr.id asc"),
    @NamedQuery(name = "WebResourcesBeforeDate", query = "select wr from WebResource wr where wr.workDates.date < :date"),
    @NamedQuery(name = "WebResourcesAfterDate", query = "select wr from WebResource wr " + "where "
            + "wr.workDates.date > :date or wr.workDates.toDate > :date"),
    @NamedQuery(name = "WebResourcesBetweenDates", query = "select wr from WebResource wr " + "where "
            + "(wr.workDates.date >= :startDate OR wr.workDates.toDate >= :startDate) " + "AND "
            + "(wr.workDates.date <= :endDate OR wr.workDates.toDate <= :endDate)") })
public class WebResource extends AbstractAuthoredEditedPublishedWork {

    private static final Logger LOGGER = Logger.getLogger(WebResource.class);
    
    private String url;
    private Date lastChecked;
    private int lastStatusCode;

    public WebResource() {
        super();
    }
    
    public WebResource(String title, String url) {
        super.setTitle(title);
        this.setUrl(url);
    }

    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getLastChecked() {
        return lastChecked;
    }

    public void setLastChecked(Date lastChecked) {
        this.lastChecked = lastChecked;
    }

    public int getLastStatusCode() {
        return lastStatusCode;
    }

    public void setLastStatusCode(int lastStatusCode) {
        this.lastStatusCode = lastStatusCode;
    }

    public String getUrl() {
        return this.url;
    }

    /**
     * @param url must be valid URL (See Javadoc for URL Class.)
     */
    public void setUrl(String url) {
        if ( url.startsWith("www")) {
            this.setUrl("http://" + url);
            return;
        }
        try {
            URL realUrl = new URL( url );
            this.url = url;
        }
        catch (MalformedURLException exception) {
            throw new IllegalArgumentException( url + " is not a valid url");
        }
        
    }
    
    /**
     * @return HTTP status code, or 0 if an error occurs connnecting
     */
    public int checkUrl() 
    {
        int responseCode = 0;
        try {
            URL realUrl = new URL( this.url );
            HttpURLConnection con = (HttpURLConnection) realUrl.openConnection();
            responseCode = con.getResponseCode();
            con.disconnect();
        }
        catch (IOException e) {
            LOGGER.error("Error handling url", e);
        }
        
        this.lastStatusCode = responseCode;
        this.lastChecked = new Date();
        
        return responseCode;
    }

}
