/*
 * LinkTag.java
 *
 * Created on 19 March 2007, 23:00
 */

package org.authorsite.web.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import org.authorsite.domain.AbstractEntry;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.email.EmailFolder;
import org.authorsite.domain.email.EmailMessage;
import org.authorsite.security.SystemUser;

/**
 * Generated tag handler class.
 * @author  jejking
 * @version
 */

public class LinkTag extends BodyTagSupport {

    /**
     * Initialization of entry property.
     */
    private AbstractEntry entry;
    
    /** Creates new instance of tag handler */
    public LinkTag() {
        super();
    }
    
    /**
     * Setter for the entry attribute.
     */
    public void setEntry(AbstractEntry value) {
        this.entry = value;
    }
    

    public String getUrlForObject(AbstractEntry entry) {
        
        String baseUrl = null;
        String url;
        if (entry instanceof Collective) {
            baseUrl = "/people/collectives";
        }
        else if (entry instanceof Individual) {
            baseUrl = "/people/individuals";
        }
        else if (entry instanceof Article) {
            baseUrl = "/works/articles";
        }
        else if (entry instanceof Book) {
            baseUrl = "/works/books";
        }
        else if (entry instanceof Chapter) {
            baseUrl = "/works/chapters";
        }
        else if (entry instanceof Journal) {
            baseUrl = "/works/journals";
        }
        else if (entry instanceof Thesis) {
            baseUrl = "/works/theses";
        }
        else if (entry instanceof EmailFolder) {
            baseUrl = "/mail/folders";
        }
        else if (entry instanceof EmailMessage) {
            baseUrl = "/mail/messages";
        }
        else if (entry instanceof SystemUser) {
            baseUrl = "/admin/users";
        }
        
        if (entry instanceof EmailFolder) {
            EmailFolder folder = (EmailFolder) entry;
            url = baseUrl + folder.getPath();
        }
        else {
            url = baseUrl + "/" + entry.getId();
        }
        
        if ( pageContext != null ) {
            String contextPath = pageContext.getServletContext().getContextPath();
            return contextPath + url;
        }
        else {
            return url;    
        }
        
        

    }
    
    public String getLinkTextForObject(AbstractEntry entry) {
        
        String text = null;
        if (entry instanceof Collective) {
            Collective c = (Collective) entry;
            text = c.getName();
            if ( c.getPlace() != null )  {
                text = text + ", " + c.getPlace();
            }
        }
        else if (entry instanceof Individual) {
            Individual i = (Individual) entry;
            text = i.getName();
            if (i.getGivenNames() != null) {
                text = text + ", " + i.getGivenNames();
            }
        }
        else if (entry instanceof AbstractWork) {
            AbstractWork work = (AbstractWork) entry;
            text = work.getTitle();
        } 
        else if (entry instanceof EmailFolder) {
            EmailFolder folder = (EmailFolder) entry;
            text = folder.getPath();
        }
        else if (entry instanceof EmailMessage) {
            EmailMessage message = (EmailMessage) entry;
            text = message.getMsgId();
        }
        else if (entry instanceof SystemUser) {
            SystemUser user = (SystemUser) entry;
            text = user.getUserName();
        }
        
        return text;
    }
    
    public int doStartTag() throws JspException, JspException {
        try {
            JspWriter out = pageContext.getOut();
            String url = this.getUrlForObject(entry);
            String linkText = this.getLinkTextForObject(entry);
           /*
            * <a href="/people/individuals/123">John King</a>
            */
            out.print("<a href=\"" + url + "\">" + linkText + "</a>");
        } catch (IOException ex) {
            this.handleBodyContentException(ex);
        }
        
        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * This method is called after the JSP engine finished processing the tag.
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    public int doEndTag() throws JspException, JspException {
      
        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }
    
    /**
     * This method is called after the JSP engine processes the body content of the tag.
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    public int doAfterBody() throws JspException {
        try {
            //
            // This code is generated for tags whose bodyContent is "JSP"
            //
            BodyContent bodyContent = getBodyContent();
            JspWriter out = bodyContent.getEnclosingWriter();

        } catch (Exception ex) {
            handleBodyContentException(ex);
        }
        
        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * Handles exception from processing the body content.
     */
    private void handleBodyContentException(Exception ex) throws JspException {
        // Since the doAfterBody method is guarded, place exception handing code here.
        throw new JspException("error in LinkTag: " + ex);
    }
    
    /**
     * Fill in this method to determine if the rest of the JSP page
     * should be generated after this tag is finished.
     * Called from doEndTag().
     */
    private boolean shouldEvaluateRestOfPageAfterEndTag()  {
        return true;
    }
    
    /**
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body.
     * Use this method to create an iterating tag.
     * Called from doAfterBody().
     */
    private boolean theBodyShouldBeEvaluatedAgain() {
        return false;
    }

    /**
     * Fill in this method to determine if the tag body should be evaluated.
     * Called from doStartTag().
     */
    private boolean theBodyShouldBeEvaluated() {
        return false;
    }

}
