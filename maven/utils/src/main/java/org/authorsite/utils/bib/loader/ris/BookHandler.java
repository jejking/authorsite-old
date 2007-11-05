package org.authorsite.utils.bib.loader.ris;

import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.authorsite.dao.CollectiveDao;
import org.authorsite.dao.IndividualDao;
import org.authorsite.dao.bib.BookDao;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.WorkDates;
import org.authorsite.domain.service.bib.BookService;


public class BookHandler implements RISEntryHandler {

    private static final Logger LOGGER = Logger.getLogger(BookHandler.class);
    
    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
    
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
        
        Collective publisher = HandlerHelper.getAuthoritativeCollective(publisherName, placeName);
        
        Book b = new Book();
        b.setTitle(title);
        b.setWorkDates(year);
        b.setPublisher(publisher);
        b.addAuthors(authoritativeAuthors);
        b.addEditors(authoritativeEditors);
        
        //Bibliography.getInstance().getAuthoritativeBook(b);
        
    }

}
