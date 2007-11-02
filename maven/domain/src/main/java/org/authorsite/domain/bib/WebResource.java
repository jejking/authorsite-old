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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

@Entity
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
        
        return responseCode;
    }

}
