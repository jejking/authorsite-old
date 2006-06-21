package org.juenger.wiki.service;

import java.util.List;

import org.juenger.wiki.item.Page;


public interface SearchService {

    public List<Page> findPages(String query);
    
    public List<Page> findPages(String query, boolean searchOldVersions);
    
}
