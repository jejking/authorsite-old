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

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.log4j.Logger;

/**
 * Generated tag handler class.
 *
 * @author  jejking
 * @version
 */
public class PagingTag extends SimpleTagSupport {

    private static final Logger LOGGER = Logger.getLogger(PagingTag.class);
    
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
    
    public int getMaxPageNumber() {
        return maxPageNumber;
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
            LOGGER.error("Exception generating paging tag content", e);
        }
        
    }    

    void loadResourceBundle() {
        
        if ( this.locale == null) {
            this.locale = Locale.getDefault();
        }
        
        this.resourceBundle = PropertyResourceBundle.getBundle("org.authorsite.web.resources.standard", this.locale);
        
    }

    void sanitizeInputs() {
        if (this.count < 0 ) {
            this.count = 0;
        }
        
        if (this.pageNumber < 1) {
            this.pageNumber = 1;
        }
        
        if (this.pageSize < 1 || pageSize > 50) {
            this.pageSize = 10;
        }
        
        boolean needExtraPage = (this.count % this.pageSize > 0 ? true : false) | this.count == 0;
        this.maxPageNumber = (this.count / this.pageSize);
        if (needExtraPage)  {
            this.maxPageNumber++;
        }
        
        if ( this.pageNumber > this.maxPageNumber ) {
            this.pageNumber = this.maxPageNumber;
        }
        
    }

    void doFirst(JspWriter out) throws IOException {
        out.print("<div class=\"pager\">");
        out.print("<span id=\"pager.first\">");
        if (this.pageNumber > 1) {
            String url = buildUrl(this.indexUrl, 1, this.pageSize);
            String title = this.resourceBundle.getString("pager.first");
            printLink( out, url, title, " &lt;&lt; " );
        }
        else {
            out.print(" &lt;&lt; ");
        }
        out.print("</span>");
    }

    void doPrevious(JspWriter out) throws IOException{
        out.print("<span id=\"pager.previous\">");
        if (this.pageNumber > 1) {
            String url = buildUrl(this.indexUrl, this.pageNumber - 1, this.pageSize);
            String title = this.resourceBundle.getString("pager.previous");
            printLink( out, url, title, " &lt; " );
        }
        else {
            out.print(" &lt; ");
        }
        out.print("</span>");
    }

    void doNext(JspWriter out) throws IOException{
        out.print("<span id=\"pager.next\">");
        if (this.pageNumber != this.maxPageNumber) {
            String url = buildUrl(this.indexUrl, this.pageNumber + 1, this.pageSize);
            String title = this.resourceBundle.getString("pager.next");
            printLink( out, url, title, " &gt; " );
        }
        else {
            out.print(" &gt; ");
        }
        
        out.print("</span>");
    }

    void doLast(JspWriter out) throws IOException {
        out.print("<span id=\"pager.last\">");
        if (this.pageNumber != this.maxPageNumber) {
            String url = buildUrl(this.indexUrl, this.maxPageNumber, this.pageSize);
            String title = this.resourceBundle.getString("pager.last");
            printLink( out, url, title, " &gt;&gt; " );
            
        }
        else {
            out.print(" &gt;&gt; ");
        }
        
        out.print("</span>");
        // ends pager div
        out.print("</div>");
    }

    void doCentralBlock(JspWriter out) throws IOException {
        CentralBlock centralBlock = new CentralBlock(this.pageNumber, this.maxPageNumber);
        centralBlock.print(out);
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
    
    private void printLink(JspWriter out, String url, String title, String text) throws IOException {
        out.print("<a href=\"" + url + "\" title=\"" + title + "\">");
        out.print(text);
        out.print("</a>");
    }
    
    private class CentralBlock {
        
        private int pageNumber;
        private int maxPageNumber;
        
        private int minBlockPageNumber;
        private int maxBlockPageNumber;
        
        public CentralBlock(int pageNumber, int maxPageNumber) {
            this.pageNumber = pageNumber;
            this.maxPageNumber = maxPageNumber;
        }
        
        public void print(JspWriter out) throws IOException {

            this.minBlockPageNumber = this.pageNumber - 4;
            int distToMax = this.maxPageNumber - this.pageNumber;
            this.minBlockPageNumber += distToMax < 2 ? distToMax : 2;
            if ( this.minBlockPageNumber < 1 )  {
                this.minBlockPageNumber = 1;
            }
            
            this.maxBlockPageNumber = this.pageNumber + 4;
            int distToMin = this.pageNumber - 1;
            this.maxBlockPageNumber -= distToMin < 2 ? distToMin : 2;
            if (this.maxBlockPageNumber > this.maxPageNumber) {
                this.maxBlockPageNumber = this.maxPageNumber;
            }
            
            int i = minBlockPageNumber;
            
            do {
                if ( i == this.pageNumber ) {
                    out.print( " " + i + " " );
                }
                else
                {
                    out.print(" ");
                    String title = PagingTag.this.resourceBundle.getString("pager.pageNumber") + " " + i;
                    String url = buildUrl(PagingTag.this.indexUrl, i, PagingTag.this.pageSize);
                    printLink(out, url, title, Integer.toString(i) );
                    out.print(" ");
                }
                i++;
            }
            while (i <= maxBlockPageNumber);
            
        }

        
    }
}
