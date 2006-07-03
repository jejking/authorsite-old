package org.authorsite.bib;

import junit.framework.TestCase;


public class CollectiveTest extends TestCase {

    public void testToSqlAllSet() throws Exception {
        Collective c = new Collective(1);
        c.setName("Test1");
        c.setPlace("Testville");
        c.setNameQualification("The one in the tests");
        
        String stmt = c.toSql();
        assertEquals("INSERT INTO humans (id, created_at, updated_at, type, name, nameQualification, place ) VALUES ( 1, NOW(), NOW(), 'Collective', 'Test1', 'The one in the tests', 'Testville');", stmt);
    }
    
    public void testToSqlJustName() throws Exception {
        Collective c = new Collective(2);
        c.setName("Test1");
        
        String stmt = c.toSql();
        assertEquals("INSERT INTO humans (id, created_at, updated_at, type, name, nameQualification, place ) VALUES ( 2, NOW(), NOW(), 'Collective', 'Test1', NULL, NULL);", stmt);
    }
    
    public void testToSqlNameNotSet() throws Exception {
        Collective c = new Collective(3);
        String stmt = c.toSql();
        assertEquals("INSERT INTO humans (id, created_at, updated_at, type, name, nameQualification, place ) VALUES ( 3, NOW(), NOW(), 'Collective', 'Unknown', NULL, NULL);", stmt);
    }
    
    public void testToSqlNamePlace() throws Exception {
        Collective c = new Collective(4);
        c.setName("Test1");
        c.setPlace("Testville");
        String stmt = c.toSql();
        assertEquals("INSERT INTO humans (id, created_at, updated_at, type, name, nameQualification, place ) VALUES ( 4, NOW(), NOW(), 'Collective', 'Test1', NULL, 'Testville');", stmt);
    }
    
    public void testToSqlNameNameQualification() throws Exception {
        Collective c = new Collective(5);
        c.setName("Test1");
        c.setNameQualification("The one in the tests");
        
        String stmt = c.toSql();
        assertEquals("INSERT INTO humans (id, created_at, updated_at, type, name, nameQualification, place ) VALUES ( 5, NOW(), NOW(), 'Collective', 'Test1', 'The one in the tests', NULL);", stmt);
        
    }
    
    public void testEqualsHashCodeAllSet() throws Exception {
        Collective c1 = new Collective(1);
        c1.setName("Test1");
        c1.setPlace("Testville");
        c1.setNameQualification("The one in the tests");
        
        Collective c2 = new Collective(2);
        c2.setName("Test1");
        c2.setPlace("Testville");
        c2.setNameQualification("The one in the tests");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        Collective c3 = new Collective(3);
        c3.setName("Test1");
        c3.setPlace("Testville");
        c3.setNameQualification("A different one from the tests");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
    }
    
    public void testEqualsHashCodeNameOnly() {
        Collective c1 = new Collective(1);
        c1.setName("Test1");
        
        Collective c2 = new Collective(2);
        c2.setName("Test1");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        Collective c3 = new Collective(3);
        c3.setName("Test2");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
    }
    
    public void testEqualsHashCodeNamePlace() {
        Collective c1 = new Collective(1);
        c1.setName("Test1");
        c1.setPlace("Testville");
        
        Collective c2 = new Collective(2);
        c2.setName("Test1");
        c2.setPlace("Testville");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        Collective c3 = new Collective(3);
        c3.setName("Test1");
        c3.setPlace("Teststadt");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
    }
    
    public void testEqualsHashCodePlaceNameQual() {
        Collective c1 = new Collective(1);
        c1.setPlace("Testville");
        c1.setNameQualification("The one in the tests");
        
        Collective c2 = new Collective(2);
        c2.setPlace("Testville");
        c2.setNameQualification("The one in the tests");
        
        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        
        assertEquals(c1.hashCode(), c2.hashCode());
        
        Collective c3 = new Collective(3);
        c3.setPlace("Testville");
        c3.setNameQualification("A different one from the tests");
        
        assertFalse(c2.equals(c3));
        assertFalse(c3.equals(c2));
        assertFalse(c1.hashCode() == c3.hashCode() );
    }
    
}
