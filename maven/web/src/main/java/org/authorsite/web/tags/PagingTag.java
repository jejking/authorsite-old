/*
 * PagingTag.java
 *
 * Created on 28 May 2007, 16:20
 */

package org.authorsite.web.tags;

import java.io.IOException;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Generated tag handler class.
 * @author  jejking
 * @version
 */

public class PagingTag extends SimpleTagSupport {

    private int pageNumber;
    private int pageSize;
    private int count;
    private Locale locale;
    private String indexUrl;
    
    private ResourceBundle resourceBundle;

    private int maxPageNumber;
    
    /** Creates new instance of tag handler */
    public PagingTag() {
        super();
    }
    
    /*
     * Bean accessors.
     */
    
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getIndexUrl() {
        return indexUrl;
    }

    public void setIndexUrl(String indexUrl) {
        this.indexUrl = indexUrl;
    }
    
    public void doTag() {
        try {
            loadResourceBundle();
            sanitizeInputs();
            JspWriter out = this.getJspContext().getOut();

            doFirst(out);
            doPrevious(out);

            doCentralBlock(out);
            
            doNext(out);
            doLast(out);
        }
        catch (Exception e) {
            
        }
        
    }    

    
    

    private void loadResourceBundle() {
        
        if ( this.locale == null) {
            this.locale = Locale.getDefault();
        }
        
        this.resourceBundle = PropertyResourceBundle.getBundle("org.authorsite.web.resources.standard", this.locale);
        
    }

    private void sanitizeInputs() {
        if (this.count < 0 ) {
            this.count = 0;
        }
        
        if (this.pageNumber < 0) {
            this.pageNumber = 1;
        }
        
        if (this.pageSize < 0 || pageSize > 50) {
            this.pageSize = 10;
        }
        
        this.maxPageNumber = (this.count / this.pageSize) + 1;
        
        if ( this.pageNumber > this.maxPageNumber ) {
            this.pageNumber = this.maxPageNumber;
        }
            
        
    }

    private void doFirst(JspWriter out) throws IOException {
        out.write("<div class=\"pager\">");
        out.write("<span id=\"pager.first\">");
        if (this.pageNumber > 1) {
            String url = buildUrl(this.indexUrl, 1, this.pageSize);
            String title = this.resourceBundle.getString("pager.first");
            writeLink( out, url, title, "<<" );
        }
        else {
            out.write(" << ");
        }
        out.write("</span>");
    }

    private void doPrevious(JspWriter out) throws IOException{
        out.write("<span id=\"pager.previous\">");
        if (this.pageNumber > 1) {
            String url = buildUrl(this.indexUrl, this.pageNumber - 1, this.pageSize);
            String title = this.resourceBundle.getString("pager.previous");
            writeLink( out, url, title, "<" );
        }
        else {
            out.write(" < ");
        }
        out.write("</span>");
    }

    private void doNext(JspWriter out) throws IOException{
        out.write("<span id=\"pager.next\">");
        if (this.pageNumber != this.maxPageNumber) {
            String url = buildUrl(this.indexUrl, this.pageNumber + 1, this.pageSize);
            String title = this.resourceBundle.getString("pager.next");
            writeLink( out, url, title, ">" );
        }
        else {
            out.write(" > ");
        }
        
        out.write("</span>");
    }

    private void doLast(JspWriter out) throws IOException {
        out.write("<span id=\"pager.last\">");
        if (this.pageNumber != this.maxPageNumber) {
            String url = buildUrl(this.indexUrl, this.maxPageNumber, this.pageSize);
            String title = this.resourceBundle.getString("pager.last");
            writeLink( out, url, title, ">>" );
            
        }
        else {
            out.write(" >> ");
        }
        
        out.write("</span>");
        // ends pager div
        out.write("</div>");
    }

    private String buildUrl(String indexUrl, int pageNumber, int pageSize) {
        StringBuilder sb = new StringBuilder(indexUrl);
        sb.append("?");
        sb.append("pageNumber=");
        sb.append(pageNumber);
        sb.append("&");
        sb.append("pageSize=");
        sb.append(pageSize);
        return sb.toString();
    }

    private void doCentralBlock(JspWriter out) throws IOException {
        
        /* 
         * we need to work out how
         * many page blocks fit between
         * the first and the max
         *
         * of these we can have at most 5
         *
         * the active page should not be an active link
         *
         */
         
        int minCentralPageNumber = this.pageNumber - 2;
        int maxCentralPageNumber = this.pageNumber + 2;
        
        if ( minCentralPageNumber < 1 ) {
            minCentralPageNumber = 1;
            maxCentralPageNumber = 5;
        }
        
        if (maxCentralPageNumber > this.maxPageNumber ) {
            maxCentralPageNumber = this.maxPageNumber;
        }
        
        
        // iterate from min to max, don't write link for current page
        
        for ( int i = minCentralPageNumber; i <= maxCentralPageNumber; i++ ) {
            if ( i == this.pageNumber ) {
                out.write( " " + i + " " );
            }
            else
            {
                String title = this.resourceBundle.getString("pager.pageNumber") + " " + i;
                writeLink(out, this.indexUrl, title, Integer.toString(i) );
            }
        }
    }

    private void writeLink(JspWriter out, String url, String title, String text) throws IOException {
        out.write("<a href=\"" + url + "\" title=\"" + title + "\">");
        out.write(text);
        out.write("</a>");
    }
    
    
}


