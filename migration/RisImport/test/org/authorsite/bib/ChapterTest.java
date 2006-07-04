package org.authorsite.bib;

import junit.framework.TestCase;


public class ChapterTest extends TestCase {

    public void testToSql() {
        Individual i1 = new Individual(1);
        
        Book b1 = new Book(1);
        b1.setYear(2006);
        Chapter c1 = new Chapter(2);
        c1.setTitle("Test Chapter One");
        c1.setChapter("1");
        c1.setPages("10-20");
        c1.addAuthor(i1);
        c1.setBook(b1);        
        
        
        String expectedStmts = "INSERT INTO works (id, created_at, updated_at, type, title, year, pages, chapter ) VALUES ( 2, NOW(), NOW(), 'Chapter', 'Test Chapter One', 2006, '10-20', '1');" +
                                "\n" +
                                "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 1, 2, 'Author');" +
                                "\n" +
                                "INSERT INTO workWorkRelationships ( created_at, updated_at, from_id, to_id, relationship ) VALUES ( NOW(), NOW(), 2, 1, 'Containment');";
        assertEquals(expectedStmts, c1.toSql());
    }
    
}
