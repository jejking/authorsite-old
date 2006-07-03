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
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 1, 1, 'Author');" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 2, 1, 'Author');" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 3, 1, 'Editor');" +
                            "\n" +
                            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 4, 1, 'Publisher');";
        assertEquals(expectedSql, b.toSql());
    }
    
}
