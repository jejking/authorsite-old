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
        Collective c1 = new Collective();
        c1.setName("Foo Inc");
        c1.setPlace("Earth");
        
        int nextId = Bibliography.getInstance().getNextHumanId();
        Collective ac1 = Bibliography.getInstance().getAuthoritativeCollective( c1 ); 
        assertEquals(nextId + 1, ac1.getId());
        assertSame(c1, ac1);
        
        Collective c2 = new Collective();
        c2.setName("Foo Inc");
        c2.setPlace("Earth");
        
        Collective ac2 = Bibliography.getInstance().getAuthoritativeCollective( c2 );
        assertSame(c1, ac2);
        assertEquals(nextId + 1, ac2.getId());
        
    }
    
    public void testGetAuthoritativeThesis() {
        Individual i1 = new Individual();
        i1.setName("King");
        i1.setGivenNames("John");
        
        Collective ou = new Collective();
        ou.setName("Oxford University");
        ou.setPlace("Oxford");
        
        Thesis t1 = new Thesis();
        t1.setTitle("Writing and Rewriting the First World War");
        t1.setYear(1999);
        t1.setAuthor(i1);
        t1.setAwardingBody(ou);
        t1.setDegree("D.Phil");
        
        int nextId = Bibliography.getNextWorkId();
        
        Thesis at1 = Bibliography.getInstance().getAuthoritativeThesis( t1 );
        assertEquals(nextId + 1, at1.getId());
        assertSame(t1, at1);
        
        Thesis t2 = new Thesis();
        t2.setTitle("Writing and Rewriting the First World War");
        t2.setYear(1999);
        t2.setAuthor(i1);
        t2.setAwardingBody(ou);
        t2.setDegree("D.Phil");
        
        Thesis at2 = Bibliography.getInstance().getAuthoritativeThesis( t2 );
        assertSame(t1, at2);
        assertEquals(nextId + 1, at2.getId());
                
        
        
    }
}
