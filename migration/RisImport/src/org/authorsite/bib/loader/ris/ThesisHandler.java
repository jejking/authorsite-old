package org.authorsite.bib.loader.ris;

import java.util.SortedSet;
import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;
import org.authorsite.bib.Thesis;


public class ThesisHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) {
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry);
        
        // extract year
        int year = HandlerHelper.extractYear(entry.getValues("Y1"));
        
        // extract title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // extract awarding body
        Collective cBean = new Collective();
        cBean.setName(HandlerHelper.getFirstString(entry.getValues("PB")));
        
        Collective awardingBody = Bibliography.getInstance().getAuthoritativeCollective( cBean );
        
        // extract degree
        String degree = HandlerHelper.getFirstString(entry.getValues("M1"));
        
        // assemble the thesis
        Thesis t = new Thesis();
        t.setTitle(title);
        t.setAuthor(authoritativeAuthors.first());
        t.setAwardingBody(awardingBody);
        t.setDegree(degree);
        t.setYear(year);
        
        Bibliography.getInstance().getAuthoritativeThesis( t );

    }

}
