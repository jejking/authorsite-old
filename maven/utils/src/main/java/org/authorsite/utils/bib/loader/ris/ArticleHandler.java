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

import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.domain.bib.Article;
import org.authorsite.domain.bib.Journal;
import org.authorsite.domain.bib.WorkDates;


public class ArticleHandler implements RISEntryHandler {

    
    public AbstractWork buildWorkFromEntry(RISEntry entry) throws RISException {
        
        // authors
        SortedSet<Individual> authors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // year
        WorkDates years = HandlerHelper.extractYear(entry.getValues("Y1")); 
        
        // title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // volume
        String volume = HandlerHelper.getFirstString(entry.getValues("VL"));
        
        // issue
        String issue = HandlerHelper.getFirstString(entry.getValues("IS"));
            
        // journal
        Journal journal = null;
        String journalTitle = HandlerHelper.getFirstString(entry.getValues("JO"));
        if ( journalTitle != null ) {
            journal = new Journal();
            journal.setTitle(journalTitle);
        }
        
        // pages... (papyrus puts thems all as SP, but try and handle EP too
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
        
        Article article = new Article();
        article.addAuthors(authors);
        article.setWorkDates(years);
        article.setTitle(title);
        article.setVolume(volume);
        article.setIssue(issue);
        article.setJournal(journal);
        article.setPages(pagesBuilder.toString());
        
        return article;
        
    }

}
