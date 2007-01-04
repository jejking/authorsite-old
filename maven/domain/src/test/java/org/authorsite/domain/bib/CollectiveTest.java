package org.authorsite.domain.bib;

import org.authorsite.domain.Collective;

import junit.framework.TestCase;


public class CollectiveTest extends TestCase {

    public void testEqualsHashCodeAllSet() throws Exception {
        Collective c1 = new Collective();
        c1.setId(1);
        c1.setName("Test1");
        c1.setPlace("Testville");
        c1.setNameQualification("The one in the tests");
        
        Collective c2 = new Collective();
        c2.setId(2);
        c2.setName("Test1");
        c2.setPlace("Testville");
        c2.setNameQualification("The one in the tests");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        assertEquals(0, c1.compareTo(c2));
        assertEquals(0, c2.compareTo(c1));
        
        Collective c3 = new Collective();
        c3.setId(3);
        c3.setName("Test1");
        c3.setPlace("Testville");
        c3.setNameQualification("A different one from the tests");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
        
        assertTrue(c1.compareTo(c3) > 0);
        assertTrue(c3.compareTo(c1) < 0);
    }
    
    public void testEqualsHashCodeNameOnly() {
        Collective c1 = new Collective();
        c1.setId(1);
        c1.setName("Test1");
        
        Collective c2 = new Collective();
        c2.setId(2);
        c2.setName("Test1");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        assertEquals(0, c1.compareTo(c2));
        assertEquals(0, c2.compareTo(c1));
        
        Collective c3 = new Collective();
        c3.setId(3);
        c3.setName("Test2");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
        
        assertTrue(c1.compareTo(c3) < 0);
        assertTrue(c3.compareTo(c1) > 0);
    }
    
    public void testEqualsHashCodeNamePlace() {
        Collective c1 = new Collective();
        c1.setId(1);
        c1.setName("Test1");
        c1.setPlace("Testville");
        
        Collective c2 = new Collective();
        c2.setId(2);
        c2.setName("Test1");
        c2.setPlace("Testville");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        assertEquals(0, c1.compareTo(c2));
        assertEquals(0, c2.compareTo(c1));
        
        Collective c3 = new Collective();
        c3.setId(3);
        c3.setName("Test1");
        c3.setPlace("Teststadt");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
        
        assertTrue(c1.compareTo(c3) > 0);
        assertTrue(c3.compareTo(c1) < 0);
    }
    
    public void testEqualsHashCodePlaceNameQual() {
        Collective c1 = new Collective();
        c1.setId(1);
        c1.setPlace("Testville");
        c1.setNameQualification("The one in the tests");
        
        Collective c2 = new Collective();
        c2.setId(2);
        c2.setPlace("Testville");
        c2.setNameQualification("The one in the tests");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        assertEquals(0, c1.compareTo(c2));
        assertEquals(0, c2.compareTo(c1));
        
        Collective c3 = new Collective();
        c3.setId(3);
        c3.setPlace("Testville");
        c3.setNameQualification("A different one from the tests");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
        
        assertTrue(c1.compareTo(c3) > 0);
        assertTrue(c3.compareTo(c1) < 0);
    }
    
}
