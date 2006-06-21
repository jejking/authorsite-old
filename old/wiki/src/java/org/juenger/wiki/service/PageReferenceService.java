package org.juenger.wiki.service;

import java.util.List;

import org.juenger.wiki.item.Page;


public interface PageReferenceService {

    public List<String> getUndefinedPages();
    
    public List<Page> getReferringPages(Page page);
    
}
