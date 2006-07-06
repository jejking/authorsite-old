package org.authorsite.bib;

import org.authorsite.bib.loader.ris.Bibliography;

import junit.framework.TestCase;

public class BibliographyTest extends TestCase {

    public void testGetAuthoritativeIndividual() {
        Individual i1 = new Individual();
        i1.setName("King");
        i1.setGivenNames("John");
        
        int nextId = Bibliography.getInstance().getNextHumanId();
        Individual ai1 = Bibliography.getInstance().getAuthoritativeIndividual(i1);
        assertEquals(nextId + 1, ai1.getId());
        assertSame(i1, ai1);
        
        Individual i2 = new Individual();
        i2.setName("King");
        i2.setGivenNames("John");
        
        Individual ai2 = Bibliography.getInstance().getAuthoritativeIndividual(i2);
        assertSame(i1, ai2);
        assertEquals(nextId + 1, ai2.getId());
    }
    
    public void testGetAuthoritativeCollective() { 
        fail("test not built yet");
    }
    
    public void testGetAuthoritativeThesis() {
        fail("test not buildt yet");
    }
}
