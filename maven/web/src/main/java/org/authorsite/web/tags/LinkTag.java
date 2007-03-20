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
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.email.EmailFolder;
import org.authorsite.domain.email.EmailMessage;

/**
 * Generated tag handler class.
 * @author  jejking
 * @version
 */

public class LinkTag extends BodyTagSupport {

    /**
     * Initialization of entry property.
     */
    private org.authorsite.domain.AbstractEntry entry;
    
    /** Creates new instance of tag handler */
    public LinkTag() {
        super();
    }
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    /**
     * Method called from doStartTag().
     * Fill in this method to perform other operations from doStartTag().
     *
     */
    private void otherDoStartTagOperations() {
        
        Class clazz = entry.getClass();
        String baseUrl = null;
        if ( Collective.class.isAssignableFrom( clazz )) {
            baseUrl = "/people/collectives";
        }
        else if ( Individual.class.isAssignableFrom(clazz)) {
            baseUrl = "/people/individuals";
        }
        else if ( Article.class.isAssignableFrom(clazz)) {
            baseUrl = "/works/articles";
        }
        else if (Book.class.isAssignableFrom(clazz)) {
            baseUrl = "/works/books";
        }
        else if (Chapter.class.isAssignableFrom(clazz)) {
            baseUrl = "/works/chapters";
        }
        else if (Journal.class.isAssignableFrom(clazz)) {
            baseUrl = "/works/journals";
        }
        else if (Thesis.class.isAssignableFrom(clazz)) {
            baseUrl = "/works/theses";
        }
        else if (EmailFolder.class.isAssignableFrom(clazz)) {
            baseUrl = "/mail/folders";
        }
        else if (EmailMessage.class.isAssignableFrom(clazz)) {
            baseUrl = "/mail/messages";
        }
        
        //   try {
        //       JspWriter out = pageContext.getOut();
        //       out.println("something");
        //   } catch (IOException ex) {
        //       // do something
        //   }
        //
        //
    }
    
    /**
     * Method called from doEndTag()
     * Fill in this method to perform other operations from doEndTag().
     *
     */
    private void otherDoEndTagOperations() {
        //
        // TODO: code that performs other operations in doEndTag
        //       should be placed here.
        //       It will be called after initializing variables,
        //       finding the parent, setting IDREFs, etc, and
        //       before calling shouldEvaluateRestOfPageAfterEndTag().
        //
    }
    
    /**
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     */
    private void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //
        // TODO: insert code to write html before writing the body content.
        // e.g.:
        //
        // out.println("<strong>" + attribute_1 + "</strong>");
        // out.println("   <blockquote>");
        
        //
        // write the body content (after processing by the JSP engine) on the output Writer
        //
        bodyContent.writeOut(out);
        
        //
        // Or else get the body content as a string and process it, e.g.:
        //     String bodyStr = bodyContent.getString();
        //     String result = yourProcessingMethod(bodyStr);
        //     out.println(result);
        //
        
        // TODO: insert code to write html after writing the body content.
        // e.g.:
        //
        // out.println("   </blockquote>");
        
        
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   Tag Handler interface methods.                         ///
    ///                                                          ///
    ///   Do not modify these methods; instead, modify the       ///
    ///   methods that they call.                                ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    /**
     * This method is called when the JSP engine encounters the start tag,
     * after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * @return EVAL_BODY_BUFFERED if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    
    public int doStartTag() throws JspException, JspException {
        
        otherDoStartTagOperations();
        
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
        otherDoEndTagOperations();
        
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
            
            writeTagBodyContent(out, bodyContent);
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
        throw new JspException("error in NewTag: " + ex);
    }
    
    /**
     * Fill in this method to determine if the rest of the JSP page
     * should be generated after this tag is finished.
     * Called from doEndTag().
     */
    private boolean shouldEvaluateRestOfPageAfterEndTag()  {
        //
        // TODO: code that determines whether the rest of the page
        //       should be evaluated after the tag is processed
        //       should be placed here.
        //       Called from the doEndTag() method.
        //
        return true;
    }
    
    /**
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body.
     * Use this method to create an iterating tag.
     * Called from doAfterBody().
     */
    private boolean theBodyShouldBeEvaluatedAgain() {
        //
        // TODO: code that determines whether the tag body should be
        //       evaluated again after processing the tag
        //       should be placed here.
        //       You can use this method to create iterating tags.
        //       Called from the doAfterBody() method.
        //
        return false;
    }

    /**
     * Fill in this method to determine if the tag body should be evaluated.
     * Called from doStartTag().
     */
    private boolean theBodyShouldBeEvaluated() {
        //
        // TODO: code that determines whether the body should be
        //       evaluated should be placed here.
        //       Called from the doStartTag() method.
        //
        return false;
    }

    /**
     * Setter for the entry attribute.
     */
    public void setEntry(org.authorsite.domain.AbstractEntry value) {
        this.entry = value;
    }
    
}
