package org.authorsite.bib;

import junit.framework.TestCase;

public class ArticleTest extends TestCase {

    public void testToSqlAllSet() {
        Journal j1 = new Journal(1);
        j1.setTitle("Journal of Wibble Studies");
        
        Individual jk = new Individual(1);
        jk.setName("King");
        jk.setGivenNames("John");
        
        Collective fooBarCollective = new Collective(2);
        fooBarCollective.setName("Foo Bar Collective");
        
        Article a2 = new Article(2);
        a2.setTitle("An Exercise in Testing");
        a2.setYears(new WorkDates(2006));
        a2.getYears().setMonth(7);
        a2.getYears().setDay(3);
        a2.setVolume("V");
        a2.setIssue("Special FooBar Issue");
        a2.setJournal(j1);
        a2.addAuthor( jk );
        a2.addAuthor( fooBarCollective );
        
        String stmts = a2.toSql();
                
        String expectedSql = "INSERT INTO works (id, created_at, updated_at, type, title, year, toYear, month, day, pages, volume, number) VALUES ( 2, NOW(), NOW(), 'Article', 'An Exercise in Testing', 2006, NULL, 7, 3, NULL, 'V', 'Special FooBar Issue');" +
                "\n" +
                "INSERT INTO workWorkRelationships ( created_at, updated_at, from_id, to_id, relationship ) VALUES ( NOW(), NOW(), 2, 1, 'Containment');" +
                "\n" +
                 "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 2, 2, 'Author');" +
                 "\n" +
                 "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 1, 2, 'Author');" ;
        assertEquals(expectedSql, stmts);
    }
    
    public void testSimpleEqualsHashCodeCompareTo() {
        Journal j1 = new Journal(1);
        j1.setTitle("Journal of Wibble Studies");
        
        Individual jk = new Individual(1);
        jk.setName("King");
        jk.setGivenNames("John");
        
        Collective fooBarCollective = new Collective(2);
        fooBarCollective.setName("Foo Bar Collective");
        
        Article a2 = new Article(2);
        a2.setTitle("An Exercise in Testing");
        a2.setYears(new WorkDates(2006));
        a2.getYears().setMonth(7);
        a2.getYears().setDay(3);
        a2.setVolume("V");
        a2.setIssue("Special FooBar Issue");
        a2.setJournal(j1);
        a2.addAuthor( jk );
        a2.addAuthor( fooBarCollective );
        
        Article a3 = new Article();
        a3.setTitle("An Exercise in Testing");
        a3.setYears(new WorkDates(2006));
        a3.getYears().setMonth(7);
        a3.getYears().setDay(3);
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
        a4.setYears(new WorkDates(2006));
        a4.getYears().setMonth(7);
        a4.getYears().setDay(3);
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
