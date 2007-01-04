package org.authorsite.utils.bib.loader.ris;

import java.util.SortedSet;

import org.authorsite.bib.Book;
import org.authorsite.bib.Chapter;
import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;
import org.authorsite.bib.WorkDates;


public class ChapterHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) throws RISException {
        // authors
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // editors
        SortedSet<Individual> authoritativeEditors = HandlerHelper.getAuthoritativeIndividuals(entry, "E1");
        
        // year
        WorkDates year = HandlerHelper.extractYear(entry.getValues("Y1"));
        
        // title
        String chapterTitle = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // pages
        String sp = HandlerHelper.getFirstString(entry.getValues("SP"));
        String ep = HandlerHelper.getFirstString(entry.getValues("EP"));
        
        StringBuilder pagesBuilder = new StringBuilder();
        if ( sp != null ) {
            pagesBuilder.append(sp);
        }
        if ( ep != null ) {
            pagesBuilder.append("-");
            pagesBuilder.append(ep);
        }
        
        // -- book
        
        // book authors
        SortedSet<Individual> bookAuthoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A2");
        
        // book editors
        SortedSet<Individual> bookAuthoritativeEditors = HandlerHelper.getAuthoritativeIndividuals(entry, "E2");
        
        // book year is obviously same as chapter year
        
        // book title
        String bookTitle = HandlerHelper.getFirstString(entry.getValues("T2"));
        
        // book publisher
        String publisherName = HandlerHelper.getFirstString(entry.getValues("PB"));
        
        // book publisher place
        String publisherPlace = HandlerHelper.getFirstString(entry.getValues("CY"));
        
        Book bookBean = new Book();
        bookBean.addAuthors(bookAuthoritativeAuthors);
        bookBean.addEditors(bookAuthoritativeEditors);
        bookBean.setTitle(bookTitle);
        bookBean.setYears(year);
        Collective publisherBean = new Collective();
        publisherBean.setName(publisherName);
        publisherBean.setPlace(publisherPlace);
        Collective authoritativePublisher = Bibliography.getInstance().getAuthoritativeCollective(publisherBean);
        bookBean.setPublisher(authoritativePublisher);
        
        Book authoritativeBook = Bibliography.getInstance().getAuthoritativeBook( bookBean );
        
        Chapter chapterBean = new Chapter();
        chapterBean.addAuthors(authoritativeAuthors);
        chapterBean.addEditors(authoritativeEditors);
        chapterBean.setTitle(chapterTitle);
        chapterBean.setYears(year);
        chapterBean.setPages(pagesBuilder.toString());
        chapterBean.setBook(authoritativeBook);
        
        Bibliography.getInstance().getAuthoritativeChapter(chapterBean);
    }

}
