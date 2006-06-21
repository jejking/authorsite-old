package org.juenger.wiki.service;

import org.juenger.wiki.item.Page;


public interface StatisticsService {

    public int getTotalPageCount();
    
    public int getViews(Page page);
    
}
