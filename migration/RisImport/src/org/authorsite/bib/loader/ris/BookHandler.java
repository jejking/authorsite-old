package org.authorsite.bib.loader.ris;

import java.util.SortedSet;

import org.authorsite.bib.Book;
import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;


public class BookHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) {
        
        // authors
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // editors
        SortedSet<Individual> authoritativeEditors = HandlerHelper.getAuthoritativeIndividuals(entry, "E1");
        
        // year
        int year = HandlerHelper.extractYear(entry.getValues("Y1"));
        
        // title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // publisher name
        String publisherName = HandlerHelper.getFirstString(entry.getValues("PB"));
        
        // place
        String placeName = HandlerHelper.getFirstString(entry.getValues("CY"));
        
        
        Collective publisherBean = new Collective();
        publisherBean.setName(publisherName);
        publisherBean.setPlace(placeName);
        
        Collective publisher = Bibliography.getInstance().getAuthoritativeCollective(publisherBean);
        
        Book b = new Book();
        b.setTitle(title);
        b.setYear(year);
        b.setPublisher(publisher);
        b.addAuthors(authoritativeAuthors);
        b.addEditors(authoritativeEditors);
        
        Bibliography.getInstance().getAuthoritativeBook(b);
        System.out.println(b);
    }

}
