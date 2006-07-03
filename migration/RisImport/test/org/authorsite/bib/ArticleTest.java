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
        a2.setYear(2006);
        a2.setMonth(7);
        a2.setDay(3);
        a2.setVolume("V");
        a2.setIssue("Special FooBar Issue");
        a2.setJournal(j1);
        a2.addAuthor( jk );
        a2.addAuthor( fooBarCollective );
        
        String stmts = a2.toSql();
                
        String expectedSql = "INSERT INTO works (id, created_at, updated_at, type, title, year, month, day, pages, volume, number) VALUES ( 2, NOW(), NOW(), 'Article', 'An Exercise in Testing', 2006, 7, 3, NULL, 'V', 'Special FooBar Issue');" +
                "\n" +
                "INSERT INTO workWorkRelationships ( created_at, updated_at, from_id, to_id, relationship ) VALUES ( NOW(), NOW(), 2, 1, 'Containment');" +
                "\n" +
                "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 1, 2, 'Author');" +
                 "\n" +
                 "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 2, 2, 'Author');";
        assertEquals(expectedSql, stmts);
    }
    
}
