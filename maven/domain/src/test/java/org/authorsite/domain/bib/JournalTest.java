package org.authorsite.domain.bib;

import junit.framework.TestCase;

public class JournalTest extends TestCase {

    
    
    public void testEqualsHashCodeCompare() {
        Journal j1 = new Journal();
        j1.setTitle("Journal of Java Studies");
        
        Journal j2 = new Journal();
        j2.setTitle("Journal of Java Studies");
        
        assertEquals(j1, j2);
        assertEquals(j2, j1);
        assertEquals(j1.hashCode(), j2.hashCode());
        assertEquals(0, j1.compareTo(j2));
        assertEquals(0, j2.compareTo(j1));
        
        Journal j3 = new Journal();
        j3.setTitle("Java Studies");
        
        assertFalse(j1.equals(j3));
        assertFalse(j3.equals(j1));
        assertFalse(j1.hashCode() == j3.hashCode());
        assertTrue(j1.compareTo(j3) > 0);
        assertTrue(j3.compareTo(j1) < 0);
    }
    
}
