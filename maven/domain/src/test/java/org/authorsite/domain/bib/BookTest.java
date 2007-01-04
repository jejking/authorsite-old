package org.authorsite.domain.bib;

import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;

import junit.framework.TestCase;


public class BookTest extends TestCase {

    
    
    public void testEqualsHashCodeCompareAllSet() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setName("Bar");
        i2.setGivenNames("Foo");
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setName("Editor");
        i3.setGivenNames("A. N. Other");
        
        Collective c4 = new Collective();
        c4.setId(4);
        c4.setName("Java Publishing Inc");
       
        Book b1 = new Book();
        b1.setId(1);
        b1.addAuthor(i1);
        b1.addAuthor(i2);
        b1.addEditor(i3);
        b1.setPublisher(c4);
        b1.setTitle("Musings on Stuff");
        b1.setYears( new WorkDates(2006));
        
        Book b2 = new Book();
        b2.setId(2);
        b2.addAuthor(i1);
        b2.addAuthor(i2);
        b2.addEditor(i3);
        b2.setPublisher(c4);
        b2.setTitle("Musings on Stuff");
        b2.setYears( new WorkDates(2006));
        
        assertEquals(b1, b2);
        assertEquals(b2, b1);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertEquals(0,b1.compareTo(b2) );
        assertEquals(0, b2.compareTo(b1));
        
        Book b3 = new Book();
        b3.setId(3);
        b3.addAuthor(i3);
        b3.setPublisher(c4);
        b3.setTitle("Musings on Stuff");
        b3.setYears(new WorkDates(2006));
        
        assertFalse(b1.equals(b3));
        assertFalse(b3.equals(b1));
        assertFalse(b1.hashCode() == b3.hashCode());
        assertTrue(b3.compareTo(b1) < 0 );
        assertTrue(b1.compareTo(b3) > 0);

    }
    
}
