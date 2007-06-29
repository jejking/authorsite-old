package org.authorsite.utils.bib.loader.ris;

import java.util.SortedSet;

import org.authorsite.domain.bib.Book;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.WorkDates;


public class BookHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) throws RISException {
        
        // authors
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // editors
        SortedSet<Individual> authoritativeEditors = HandlerHelper.getAuthoritativeIndividuals(entry, "E1");
        
        // year
        WorkDates year = HandlerHelper.extractYear(entry.getValues("Y1"));
        
        // title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // publisher name
        String publisherName = HandlerHelper.getFirstString(entry.getValues("PB"));
        
        // place
        String placeName = HandlerHelper.getFirstString(entry.getValues("CY"));
        
        
        Collective publisherBean = new Collective();
        publisherBean.setName(publisherName);
        publisherBean.setPlace(placeName);
        
        //Collective publisher = Bibliography.getInstance().getAuthoritativeCollective(publisherBean);
        
        Book b = new Book();
        b.setTitle(title);
        b.setYears(year);
        b.setPublisher(publisherBean);
        b.addAuthors(authoritativeAuthors);
        b.addEditors(authoritativeEditors);
        
        //Bibliography.getInstance().getAuthoritativeBook(b);
        System.out.println(b);
    }

}
