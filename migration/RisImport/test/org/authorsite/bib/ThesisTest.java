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
    
}
