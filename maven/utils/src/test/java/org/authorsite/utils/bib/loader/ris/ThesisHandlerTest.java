package org.authorsite.utils.bib.loader.ris;

import org.authorsite.bib.Collective;
import org.authorsite.bib.Individual;
import org.authorsite.bib.Thesis;
import org.authorsite.bib.WorkDates;

import junit.framework.TestCase;

public class ThesisHandlerTest extends TestCase {

    
    
    @Override
    protected void setUp() throws Exception {
        Bibliography.getInstance().clearIndividuals();
        Bibliography.getInstance().clearCollectives();
        Bibliography.getInstance().clearTheses();
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        Bibliography.getInstance().clearIndividuals();
        Bibliography.getInstance().clearCollectives();
        Bibliography.getInstance().clearTheses();
        super.tearDown();
    }

    public void testHandleEntry() throws Exception {
        
        RISEntry thesisEntry = new RISEntry();
        thesisEntry.addEntryLine(new RISEntryLine("TY", "THES"));
        thesisEntry.addEntryLine(new RISEntryLine("A1", "King, John"));
        thesisEntry.addEntryLine(new RISEntryLine("T1", "Writing and Rewriting the First World War"));
        thesisEntry.addEntryLine(new RISEntryLine("Y1", "1999"));
        thesisEntry.addEntryLine(new RISEntryLine("PB", "Oxford University"));
        thesisEntry.addEntryLine(new RISEntryLine("M1", "D.Phil"));
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");
        
        Collective ou = new Collective();
        ou.setName("Oxford University");
        
        Thesis t = new Thesis();
        t.setAuthor(jk);
        t.setAwardingBody(ou);
        t.setDegree("D.Phil");
        t.setYears(new WorkDates(1999));
        t.setTitle("Writing and Rewriting the First World War");
        
        ThesisHandler handler = new ThesisHandler();
        handler.handleEntry(thesisEntry);
        
        // we now expect to see jk, ou and thesis in Bibliography
        assertTrue(Bibliography.getInstance().getIndividuals().containsKey(jk));
        assertTrue(Bibliography.getInstance().getCollectives().containsKey(ou));
        assertTrue(Bibliography.getInstance().getTheses().containsKey(t));
        
        Thesis at = Bibliography.getInstance().getAuthoritativeThesis(t);
        assertNotSame(t, at); // should return the one created by the handling...
        assertTrue(at.getId() > 0);
        assertTrue(at.getAuthor().getId() > 0);
        assertNotSame(jk, at.getAuthor());
        assertTrue(at.getAwardingBody().getId() > 0);
        assertNotSame(ou, at.getAwardingBody());
        
    }
    
    public void testHandleTwoEntriesSameUniversity() throws Exception {
        RISEntry jkThesisEntry = new RISEntry();
        jkThesisEntry.addEntryLine(new RISEntryLine("TY", "THES"));
        jkThesisEntry.addEntryLine(new RISEntryLine("A1", "King, John"));
        jkThesisEntry.addEntryLine(new RISEntryLine("T1", "Writing and Rewriting the First World War"));
        jkThesisEntry.addEntryLine(new RISEntryLine("Y1", "1999"));
        jkThesisEntry.addEntryLine(new RISEntryLine("PB", "Oxford University"));
        jkThesisEntry.addEntryLine(new RISEntryLine("M1", "D.Phil"));
        
        RISEntry woodsThesisEntry = new RISEntry();
        woodsThesisEntry.addEntryLine(new RISEntryLine("TY", "THES"));
        woodsThesisEntry.addEntryLine(new RISEntryLine("A1", "Woods, Roger"));
        woodsThesisEntry.addEntryLine(new RISEntryLine("T1", "Ernst J\u00fcnger and the Nature of Political Commitment"));
        woodsThesisEntry.addEntryLine(new RISEntryLine("Y1", "1981"));
        woodsThesisEntry.addEntryLine(new RISEntryLine("PB", "Oxford University"));
        woodsThesisEntry.addEntryLine(new RISEntryLine("M1", "D.Phil"));
        
        RISEntry jkMastersThesisEntry = new RISEntry();
        jkMastersThesisEntry.addEntryLine(new RISEntryLine("TY", "THES"));
        jkMastersThesisEntry.addEntryLine(new RISEntryLine("A1", "King, John"));
        jkMastersThesisEntry.addEntryLine(new RISEntryLine("T1", "History and Time: A Critical Survey of the Secondary Literature on Ernst J\u00fcnger."));
        jkMastersThesisEntry.addEntryLine(new RISEntryLine("Y1", "1995"));
        jkMastersThesisEntry.addEntryLine(new RISEntryLine("PB", "Oxford University"));
        jkMastersThesisEntry.addEntryLine(new RISEntryLine("M1", "M.St.")); // :)
        
        Individual jk = new Individual();
        jk.setName("King");
        jk.setGivenNames("John");

        Individual rw = new Individual();
        rw.setName("Woods");
        rw.setGivenNames("Roger");
        
        Collective ou = new Collective();
        ou.setName("Oxford University");
        
        Thesis jkDphil = new Thesis();
        jkDphil.setAuthor(jk);
        jkDphil.setAwardingBody(ou);
        jkDphil.setDegree("D.Phil");
        jkDphil.setYears(new WorkDates(1999));
        jkDphil.setTitle("Writing and Rewriting the First World War");
        
        Thesis rwDphil = new Thesis();
        rwDphil.setAuthor(rw);
        rwDphil.setAwardingBody(ou);
        rwDphil.setDegree("D.Phil");
        rwDphil.setYears(new WorkDates(1981));
        rwDphil.setTitle("Ernst J\u00fcnger and the Nature of Political Commitment");
        
        Thesis jkMst = new Thesis();
        jkMst.setAuthor(jk);
        jkMst.setAwardingBody(ou);
        jkMst.setDegree("M.St.");
        jkMst.setYears(new WorkDates(1995));
        jkMst.setTitle("History and Time: A Critical Survey of the Secondary Literature on Ernst J\u00fcnger.");
        
        ThesisHandler handler = new ThesisHandler();
        handler.handleEntry(jkMastersThesisEntry);
        handler.handleEntry(jkThesisEntry);
        handler.handleEntry(woodsThesisEntry);
        
        assertTrue(Bibliography.getInstance().getIndividuals().containsKey(jk));
        assertTrue(Bibliography.getInstance().getIndividuals().containsKey(rw));
        
        Thesis aJkDphil = Bibliography.getInstance().getAuthoritativeThesis(jkDphil);
        Thesis aRwDphil = Bibliography.getInstance().getAuthoritativeThesis(rwDphil);
        Thesis aJkMst = Bibliography.getInstance().getAuthoritativeThesis(jkMst);
        
        assertSame(aJkDphil.getAuthor(), aJkMst.getAuthor());
        assertSame(aJkDphil.getAwardingBody(), aRwDphil.getAwardingBody());
        assertSame(aJkDphil.getAwardingBody(), aJkMst.getAwardingBody());
        
    }
    
}
