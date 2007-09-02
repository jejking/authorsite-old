package org.authorsite.domain.bib;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;

public class ArticleTest extends TestCase {

   
    public void testSimpleEqualsHashCodeCompareTo() {
        Journal j1 = new Journal();
        j1.setId(1);
        j1.setTitle("Journal of Wibble Studies");
        
        Individual jk = new Individual();
        jk.setId(1);
        jk.setName("King");
        jk.setGivenNames("John");
        
        Collective fooBarCollective = new Collective();
        fooBarCollective.setId(2);
        fooBarCollective.setName("Foo Bar Collective");
        
        Article a2 = new Article();
        a2.setId(2);
        a2.setTitle("An Exercise in Testing");
        a2.setWorkDates(new WorkDates(2006, 7, 3));
        a2.setVolume("V");
        a2.setIssue("Special FooBar Issue");
        a2.setJournal(j1);
        a2.addAuthor( jk );
        a2.addAuthor( fooBarCollective );
        
        Article a3 = new Article();
        a3.setTitle("An Exercise in Testing");
        a3.setWorkDates(new WorkDates(2006, 7, 3));
        a3.setVolume("V");
        a3.setIssue("Special FooBar Issue");
        a3.setJournal(j1);
        a3.addAuthor( jk );
        a3.addAuthor( fooBarCollective );
        
        assertEquals(a2, a3);
        assertEquals(a3, a2);
        assertEquals(a2.hashCode(), a3.hashCode());
        assertEquals(0, a2.compareTo(a3));
        assertEquals(0, a3.compareTo(a2));
        
        Article a4 = new Article();
        a4.setTitle("Fu or Foo? That is the question");
        a4.setWorkDates(new WorkDates(2006, 7, 3));
        a4.setVolume("V");
        a4.setIssue("Special FooBar Issue");
        a4.setJournal(j1);
        a4.addAuthor( jk );
        a4.addAuthor( fooBarCollective );
        
        assertFalse(a2.equals(a4));
        assertFalse(a4.equals(a2));
        assertFalse(a2.hashCode() == a4.hashCode());
        assertTrue(a4.compareTo(a2) > 0);
        assertTrue(a2.compareTo(a4) < 0);
        
    }
    
}
