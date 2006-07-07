package org.authorsite.bib.loader.ris;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;
import org.authorsite.bib.Thesis;


public class ThesisHandler implements RISEntryHandler {

    public void handleEntry(RISEntry entry) {
        // extract authors (split into name, given name on comma)
        List<String> authorStrings = entry.getValues("A1");
        SortedSet<Individual> authorBeanSet = new TreeSet<Individual>();
        
        for ( String authorString : authorStrings ) {
            authorBeanSet.addAll(HandlerHelper.getIndividuals(authorString));
        }
        
        SortedSet<Individual> authoritativeAuthors = new TreeSet<Individual>();
        
        for ( Individual individual : authorBeanSet ) {
            authoritativeAuthors.add(Bibliography.getInstance().getAuthoritativeIndividual(individual));
        }
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
