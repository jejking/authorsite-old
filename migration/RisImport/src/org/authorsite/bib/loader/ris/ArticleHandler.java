package org.authorsite.bib.loader.ris;

import java.util.SortedSet;

import org.authorsite.bib.Article;
import org.authorsite.bib.Individual;
import org.authorsite.bib.Journal;
import org.authorsite.bib.WorkDates;


public class ArticleHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) throws RISException {
        
        // authors
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // year
        WorkDates years = HandlerHelper.extractYear(entry.getValues("Y1")); 
        
        // title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // volume
        String volume = HandlerHelper.getFirstString(entry.getValues("VL"));
        
        // issue
        String issue = HandlerHelper.getFirstString(entry.getValues("IS"));
            
        // journal
        Journal j = null;
        String journalTitle = HandlerHelper.getFirstString(entry.getValues("JO"));
        if ( journalTitle != null ) {
            Journal jBean = new Journal();
            jBean.setTitle(journalTitle);
            j = Bibliography.getInstance().getAuthoritativeJournal(jBean);
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
        
        Article a = new Article();
        a.addAuthors(authoritativeAuthors);
        a.setYears(years);
        a.setTitle(title);
        a.setVolume(volume);
        a.setIssue(issue);
        a.setJournal(j);
        a.setPages(pagesBuilder.toString());
        
        Bibliography.getInstance().getAuthoritativeArticle(a);
        
    }

}
