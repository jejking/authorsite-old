package org.authorsite.utils.bib.loader.ris;

import java.util.SortedSet;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.bib.WorkDates;


public class ThesisHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) throws RISException {
        SortedSet<Individual> authoritativeAuthors = HandlerHelper.getAuthoritativeIndividuals(entry, "A1");
        
        // extract year
        WorkDates year = HandlerHelper.extractYear(entry.getValues("Y1"));
        
        // extract title
        String title = HandlerHelper.getFirstString(entry.getValues("T1"));
        
        // extract awarding body
        Collective awardingBody = new Collective();
        awardingBody.setName(HandlerHelper.getFirstString(entry.getValues("PB")));
        awardingBody.setPlace(HandlerHelper.getFirstString(entry.getValues("CY")));
        
        //Collective awardingBody = Bibliography.getInstance().getAuthoritativeCollective( cBean );
        
        // extract degree
        String degree = HandlerHelper.getFirstString(entry.getValues("M1"));
        
        // assemble the thesis
        Thesis t = new Thesis();
        t.setTitle(title);
        t.setAuthor(authoritativeAuthors.first());
        t.setAwardingBody(awardingBody);
        t.setDegree(degree);
        t.setYears(year);
        
        //Bibliography.getInstance().getAuthoritativeThesis( t );

    }

}
