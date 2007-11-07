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

import junit.framework.TestCase;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;
import org.authorsite.domain.bib.Thesis;
import org.authorsite.domain.bib.WorkDates;

public class ThesisHandlerTest extends TestCase {

   
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
        t.setWorkDates(new WorkDates(1999));
        t.setTitle("Writing and Rewriting the First World War");
        
        ThesisHandler handler = new ThesisHandler();
        Thesis assembled = (Thesis) handler.buildWorkFromEntry(thesisEntry);
        assertEquals(t, assembled);
        
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
        jkDphil.setWorkDates(new WorkDates(1999));
        jkDphil.setTitle("Writing and Rewriting the First World War");
        
        Thesis rwDphil = new Thesis();
        rwDphil.setAuthor(rw);
        rwDphil.setAwardingBody(ou);
        rwDphil.setDegree("D.Phil");
        rwDphil.setWorkDates(new WorkDates(1981));
        rwDphil.setTitle("Ernst J\u00fcnger and the Nature of Political Commitment");
        
        Thesis jkMst = new Thesis();
        jkMst.setAuthor(jk);
        jkMst.setAwardingBody(ou);
        jkMst.setDegree("M.St.");
        jkMst.setWorkDates(new WorkDates(1995));
        jkMst.setTitle("History and Time: A Critical Survey of the Secondary Literature on Ernst J\u00fcnger.");
        
        ThesisHandler handler = new ThesisHandler();
        Thesis assembled1 = (Thesis) handler.buildWorkFromEntry(jkMastersThesisEntry);
        Thesis assembled2 = (Thesis) handler.buildWorkFromEntry(jkThesisEntry);
        Thesis assembled3 = (Thesis) handler.buildWorkFromEntry(woodsThesisEntry);
        
        assertEquals( jkMst, assembled1);
        assertEquals( jkDphil, assembled2);
        assertEquals( rwDphil, assembled3);
        assertEquals( assembled1.getAwardingBody(), assembled3.getAwardingBody());
        
    }
    
}
