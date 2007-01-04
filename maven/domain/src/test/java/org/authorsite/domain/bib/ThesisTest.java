package org.authorsite.domain.bib;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;


public class ThesisTest extends TestCase {

    
    public void testEqualsHashCodeComparable() {
        Individual i = new Individual();
        i.setName("King");
        i.setGivenNames("John");
        
        Collective ou = new Collective();
        ou.setName("Oxford University");
        ou.setPlace("Oxford");
        
        Thesis t1 = new Thesis();
        t1.setTitle("Writing and Rewriting the First World War");
        t1.setAuthor(i);
        t1.setAwardingBody(ou);
        t1.setYears(new WorkDates(1999));
        t1.setDegree("D. Phil");
        
        Thesis t2 = new Thesis();
        t2.setTitle("Writing and Rewriting the First World War");
        t2.setAuthor(i);
        t2.setAwardingBody(ou);
        t2.setYears(new WorkDates(1999));
        t2.setDegree("D. Phil");
        
        assertEquals(t1, t2);
        assertEquals(t2, t1);
        
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(0, t1.compareTo(t2));
        assertEquals(0, t2.compareTo(t1));
        
        Thesis t3 = new Thesis();
        t3.setTitle("The one I really wanted to write");
        t3.setAuthor(i);
        t3.setAwardingBody(ou);
        t3.setYears(new WorkDates(1999));
        t3.setDegree("D. Phil");
        
        assertFalse(t1.equals(t3));
        assertFalse(t3.equals(t1));
        
        assertTrue(t1.compareTo(t3) > 0);
        assertTrue(t3.compareTo(t1) < 0);
    }
    
}
