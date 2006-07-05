package org.authorsite.bib;

import junit.framework.TestCase;


public class ThesisTest extends TestCase {

    public void testToSql() {
        Individual i = new Individual(1);
        i.setName("King");
        i.setGivenNames("John");
        
        Collective ou = new Collective(2);
        ou.setName("Oxford University");
        
        Thesis t = new Thesis(1);
        t.setTitle("Writing and Rewriting the First World War");
        t.setAuthor(i);
        t.setAwardingBody(ou);
        t.setYear(1999);
        t.setDegree("D. Phil");
        
        String expectedStmts = "INSERT INTO works (id, created_at, updated_at, type, title, year, degree ) VALUES ( 1, NOW(), NOW(), 'Thesis', 'Writing and Rewriting the First World War', 1999, 'D. Phil');" + 
            "\n" + 
            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 1, 1, 'Author');" +
            "\n" +
            "INSERT INTO humanWorkRelationships ( created_at, updated_at, humans_id, works_id, relationship ) VALUES ( NOW(), NOW(), 2, 1, 'AwardingBody');";
        
        assertEquals(expectedStmts, t.toSql());
    }
    
    public void testEqualsHashCodeComparable() {
        Individual i = new Individual();
        i.setName("King");
        i.setGivenNames("John");
        
        Collective ou = new Collective();
        ou.setName("Oxford University");
        ou.setPlace("Oxford");
        
        Thesis t1 = new Thesis();
        t1.setTitle("Writing and Rewriting the First World War");
        t1.setAuthor(i);
        t1.setAwardingBody(ou);
        t1.setYear(1999);
        t1.setDegree("D. Phil");
        
        Thesis t2 = new Thesis();
        t2.setTitle("Writing and Rewriting the First World War");
        t2.setAuthor(i);
        t2.setAwardingBody(ou);
        t2.setYear(1999);
        t2.setDegree("D. Phil");
        
        assertEquals(t1, t2);
        assertEquals(t2, t1);
        
        assertEquals(t1.hashCode(), t2.hashCode());
        assertEquals(0, t1.compareTo(t2));
        assertEquals(0, t2.compareTo(t1));
        
        Thesis t3 = new Thesis();
        t3.setTitle("The one I really wanted to write");
        t3.setAuthor(i);
        t3.setAwardingBody(ou);
        t3.setYear(1999);
        t3.setDegree("D. Phil");
        
        assertFalse(t1.equals(t3));
        assertFalse(t3.equals(t1));
        
        assertTrue(t1.compareTo(t3) > 0);
        assertTrue(t3.compareTo(t1) < 0);
    }
    
}
