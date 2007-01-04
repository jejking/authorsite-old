package org.authorsite.domain.bib;

import org.authorsite.domain.Individual;

import junit.framework.TestCase;


public class IndividualTest extends TestCase {

    
    
    public void testEqualsAllSet() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setName("King");
        i1.setGivenNames("John");
        i1.setNameQualification("the one writing this code");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setName("King");
        i2.setGivenNames("John");
        i2.setNameQualification("the one writing this code");
        
        assertEquals(i1, i1);
        assertEquals(i1, i2);
        assertEquals(i2, i1);
        
        assertEquals(i1.hashCode(), i2.hashCode());
        
        assertFalse(i1.equals(null));
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setName("King");
        i3.setGivenNames("John");
        i3.setNameQualification("Not the dodgy medieval king");
        
        assertFalse(i3.equals(i2));
        assertFalse(i2.equals(i3));
        
        assertFalse(i2.hashCode() == i3.hashCode());
    }
    
    public void testEqualsJustName() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setName("King");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setName("King");
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setName("Foo");
        
        assertEquals(i1, i1);
        assertEquals(i1, i2);
        assertEquals(i2, i1);
        
        assertFalse(i1.equals(i3));
        assertFalse(i3.equals(i1));
        
        assertTrue( i1.hashCode() == i2.hashCode());
        assertFalse( i1.hashCode() == i3.hashCode() );
       
    }
    
    public void testEqualsNameAndGivenNames() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setName("King");
        i2.setGivenNames("John");
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setName("Foo");
        i3.setGivenNames("Bar");
        
        assertEquals(i1, i1);
        assertEquals(i1, i2);
        assertEquals(i2, i1);
        
        assertFalse(i1.equals(i3));
        assertFalse(i3.equals(i1));
        
        assertTrue( i1.hashCode() == i2.hashCode());
        assertFalse( i1.hashCode() == i3.hashCode() );
    }
    
    public void testEqualsNameAndNameQualification() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setName("King");
        i1.setNameQualification("the one writing this code");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setName("King");
        i2.setNameQualification("the one writing this code");
        
        assertEquals(i1, i1);
        assertEquals(i1, i2);
        assertEquals(i2, i1);
        
        assertEquals(i1.hashCode(), i2.hashCode());
        
        assertFalse(i1.equals(null));
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setName("King");
        i3.setNameQualification("Not the dodgy medieval king");
        
        assertFalse(i3.equals(i2));
        assertFalse(i2.equals(i3));
        
        assertFalse(i2.hashCode() == i3.hashCode());
    }
    
    public void testEqualsGivenNamesAndNameQualification() {
        Individual i1 = new Individual();
        i1.setId(1);
        i1.setGivenNames("John");
        i1.setNameQualification("the one writing this code");
        
        Individual i2 = new Individual();
        i2.setId(2);
        i2.setGivenNames("John");
        i2.setNameQualification("the one writing this code");
        
        assertEquals(i1, i1);
        assertEquals(i1, i2);
        assertEquals(i2, i1);
        
        assertEquals(i1.hashCode(), i2.hashCode());
        
        assertFalse(i1.equals(null));
        
        Individual i3 = new Individual();
        i3.setId(3);
        i3.setGivenNames("John");
        i3.setNameQualification("Not the dodgy medieval king");
        
        assertFalse(i3.equals(i2));
        assertFalse(i2.equals(i3));
        
        assertFalse(i2.hashCode() == i3.hashCode());
    }
    
    public void testCompareTo() {
        Individual i1 = new Individual();
        i1.setName("King");
        i1.setGivenNames("John");
        
        Individual i2 = new Individual();
        i2.setName("King");
        i2.setGivenNames("John");
        
        assertEquals(0, i1.compareTo(i2));
        
        Individual i3 = new Individual();
        i3.setName("King");
        i3.setGivenNames("Adam");
        
        assertTrue(i1.compareTo(i3) > 0 );
        assertTrue(i3.compareTo(i1) < 0);
        
        Individual i4 = new Individual();
        i4.setName("Smith");
        i4.setGivenNames("John");
        
        assertTrue(i1.compareTo(i4) < 0);
        assertTrue(i4.compareTo(i1) > 0);
    }
}
