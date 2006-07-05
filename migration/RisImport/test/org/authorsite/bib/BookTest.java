package org.authorsite.bib;

import junit.framework.TestCase;


public class BookTest extends TestCase {

    public void testToSqlAllSet() {
        Individual i1 = new Individual(1);
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual(2);
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        Individual i3 = new Individual(3);
        i3.setName("Editor");
        i3.setGivenNames("A. N. Other");
        
        Collective c4 = new Collective(4);
        c4.setName("Java Publishing Inc");
       
        Book b = new Book(1);
        b.addAuthor(i1);
        b.addAuthor(i2);
        b.addEditor(i3);
        b.setPublisher(c4);
        b.setTitle("Musings on Stuff");
        b.setYear(2006);
        
        String expectedSql = "INSERT INTO works (id, created_at, updated_at, type, title, year, volume ) VALUES ( 1, NOW(), NOW(), 'Book', 'Musings on Stuff', 2006, NULL);" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 2, 1, 'Author');" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 1, 1, 'Author');" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 3, 1, 'Editor');" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 4, 1, 'Publisher');";
        assertEquals(expectedSql, b.toSql());
    }
    
    public void testEqualsHashCodeCompareAllSet() {
        Individual i1 = new Individual(1);
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual(2);
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        Individual i3 = new Individual(3);
        i3.setName("Editor");
        i3.setGivenNames("A. N. Other");
        
        Collective c4 = new Collective(4);
        c4.setName("Java Publishing Inc");
       
        Book b1 = new Book(1);
        b1.addAuthor(i1);
        b1.addAuthor(i2);
        b1.addEditor(i3);
        b1.setPublisher(c4);
        b1.setTitle("Musings on Stuff");
        b1.setYear(2006);
        
        Book b2 = new Book(2);
        b2.addAuthor(i1);
        b2.addAuthor(i2);
        b2.addEditor(i3);
        b2.setPublisher(c4);
        b2.setTitle("Musings on Stuff");
        b2.setYear(2006);
        
        assertEquals(b1, b2);
        assertEquals(b2, b1);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertEquals(0,b1.compareTo(b2) );
        assertEquals(0, b2.compareTo(b1));
        
        Book b3 = new Book(3);
        b3.addAuthor(i3);
        b3.setPublisher(c4);
        b3.setTitle("Musings on Stuff");
        b3.setYear(2006);
        
        assertFalse(b1.equals(b3));
        assertFalse(b3.equals(b1));
        assertFalse(b1.hashCode() == b3.hashCode());
        assertTrue(b3.compareTo(b1) < 0 );
        assertTrue(b1.compareTo(b3) > 0);

    }
    
}
