package org.authorsite.utils.bib.loader.ris;

import java.util.SortedSet;

import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.WorkDates;
import org.authorsite.domain.service.bib.BookService;
import org.authorsite.domain.service.bib.ChapterService;
import org.springframework.transaction.annotation.Transactional;


public class ChapterHandler implements RISEntryHandler {

    private ChapterService chapterService;
    private BookService bookService;

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setChapterService(ChapterService chapterService) {
        this.chapterService = chapterService;
    }
    
    @Transactional
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
        bookBean.setWorkDates(year);
        Collective publisherBean = new Collective();
        publisherBean.setName(publisherName);
        publisherBean.setPlace(publisherPlace);

        bookBean.setPublisher(publisherBean);
        
        //Book authoritativeBook = Bibliography.getInstance().getAuthoritativeBook( bookBean );
        
        Chapter chapterBean = new Chapter();
        chapterBean.addAuthors(authoritativeAuthors);
        chapterBean.addEditors(authoritativeEditors);
        chapterBean.setTitle(chapterTitle);
        chapterBean.setWorkDates(year);
        chapterBean.setPages(pagesBuilder.toString());
        chapterBean.setBook(bookBean);
        
        // Bibliography.getInstance().getAuthoritativeChapter(chapterBean);
    }

}
