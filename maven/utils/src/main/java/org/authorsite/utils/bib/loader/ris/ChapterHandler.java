/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.utils.bib.loader.ris;

import java.util.SortedSet;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.Book;
import org.authorsite.domain.bib.Chapter;
import org.authorsite.domain.bib.WorkDates;


public class ChapterHandler implements RISEntryHandler {

    public AbstractWork buildWorkFromEntry(RISEntry entry) throws RISException {
        // authors
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // editors
        SortedSet<Individual> authoritativeEditors = HandlerHelper.getAuthoritativeIndividuals(entry, "E1");
        
        // year
        WorkDates workDates = HandlerHelper.extractYear(entry.getValues("Y1"));
        
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
        
        Book book = new Book();
        book.addAuthors(bookAuthoritativeAuthors);
        book.addEditors(bookAuthoritativeEditors);
        book.setTitle(bookTitle);
        book.setWorkDates(workDates);
        
        Collective publisherBean = new Collective();
        publisherBean.setName(publisherName);
        publisherBean.setPlace(publisherPlace);

        book.setPublisher(publisherBean);
        
        // and now the chapter
        
        Chapter chapter = new Chapter();
        chapter.addAuthors(authoritativeAuthors);
        chapter.addEditors(authoritativeEditors);
        chapter.setTitle(chapterTitle);
        chapter.setWorkDates(workDates);
        chapter.setPages(pagesBuilder.toString());
        chapter.setBook(book);
        
        return chapter;
    }

}
