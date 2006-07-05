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
    
    public void testEqualsHashCodeCompareTo() {
        Individual i1 = new Individual();
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        Collective c = new Collective();
        c.setName("Foo Publishing Co");
        c.setPlace("here");
        
        Book b = new Book();
        b.setTitle("Musings on Stuff");
        b.setYear(2006);
        b.setPublisher(c);
        b.addEditor(i1);
        
        Chapter ch1 = new Chapter();
        ch1.setBook(b);
        ch1.addAuthor(i2);
        ch1.addAuthor(i1);
        ch1.setYear(2006);
        ch1.setTitle("Introduction to Stuff");
        
        Chapter ch2 = new Chapter();
        ch2.setBook(b);
        ch2.setYear(2006);
        ch2.addAuthor(i1);
        ch2.addAuthor(i2);
        ch2.setTitle("Introduction to Stuff");
        
        assertEquals(ch2, ch1);
        assertEquals(ch1, ch2);
        assertEquals(ch1.hashCode(), ch2.hashCode());
        assertEquals(0, ch1.compareTo(ch2));
        assertEquals(0, ch2.compareTo(ch1));
        
        Chapter ch3 = new Chapter();
        ch3.setBook(b);
        ch3.addAuthor(i1);
        ch3.setYear(2006);
        ch3.setTitle("Introduction to Stuff");
        
        assertFalse(ch1.equals(ch3));
        assertFalse(ch3.equals(ch1));
        assertFalse(ch1.hashCode() == ch3.hashCode());
        
        // ch3 one fewer authors, should sort before ch1
        assertTrue(ch1.compareTo(ch3) > 0);
        assertTrue(ch3.compareTo(ch1) < 0);
        
    }
    
}
