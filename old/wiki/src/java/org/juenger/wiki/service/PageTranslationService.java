package org.juenger.wiki.service;

import org.juenger.wiki.item.Page;


/**
 * Implementations render page content (wiki markup) for external consumption.
 * 
 * @author jking
  */
public interface PageTranslationService {

    /**
     * Translates wiki markup into final HTML for output.
     * 
     * @param page
     * @return page rendered as valid XHTML fragment
     */
    public String pageTextToHTML(Page page);
    
    /**
     * Translates wiki markup into plain text by stripping
     * out wiki markup.
     * 
     * <p>This might be used by a search service in constructing
     * indexes.</p>
     * 
     * @param page
     * @return the page content with markup removed
     */
    public String pageTextToPlainText(Page page);
    
}
