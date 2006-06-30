package org.authorsite.bib;

import junit.framework.TestCase;


public class PersonTest extends TestCase {

    public void testToSql() throws Exception {
        Person p = new Person(1);
        p.setName("King");
        p.setGivenNames("John");
        p.setNameQualification("the one writing this code");
        
        String sqlStmt = p.toSql();
        System.err.println(sqlStmt);
        assertEquals("INSERT INTO HUMANS (id, created_at, updated_at, type, name, givenNames, nameQualification ) VALUES ( 1, NOW(), NOW(), 'Individual', 'King', 'John', 'the one writing this code');", sqlStmt);
    }
    
    public void testToSqlApostrophes() throws Exception {
        Person p = new Person(2);
        p.setName("O'Reilly");
        p.setGivenNames("O'Gorman");
        p.setNameQualification("The third one o' them");
        
        String sqlStmt = p.toSql();
        System.err.println(sqlStmt);
        assertEquals("INSERT INTO HUMANS (id, created_at, updated_at, type, name, givenNames, nameQualification ) VALUES ( 2, NOW(), NOW(), 'Individual', 'O\\'Reilly', 'O\\'Gorman', 'The third one o\\' them');", sqlStmt);
    }
    
    public void testToSqlGivenNamesNull() throws Exception {
        Person p = new Person(3);
        p.setName("King");
        p.setNameQualification("the one writing this code");
        
        String sqlStmt = p.toSql();
        System.err.println(sqlStmt);
        assertEquals("INSERT INTO HUMANS (id, created_at, updated_at, type, name, givenNames, nameQualification ) VALUES ( 3, NOW(), NOW(), 'Individual', 'King', NULL, 'the one writing this code');", sqlStmt);
    }
    
    public void testToSqlNameQualificationNull() throws Exception {
        Person p = new Person(4);
        p.setName("King");
        p.setGivenNames("John");
        
        String sqlStmt = p.toSql();
        System.err.println(sqlStmt);
        assertEquals("INSERT INTO HUMANS (id, created_at, updated_at, type, name, givenNames, nameQualification ) VALUES ( 4, NOW(), NOW(), 'Individual', 'King', 'John', NULL);", sqlStmt);
        
    }
    
    public void testToSqlOnlyName() throws Exception {
        Person p = new Person(5);
        p.setName("King");
        
        String sqlStmt = p.toSql();
        System.err.println(sqlStmt);
        assertEquals("INSERT INTO HUMANS (id, created_at, updated_at, type, name, givenNames, nameQualification ) VALUES ( 5, NOW(), NOW(), 'Individual', 'King', NULL, NULL);", sqlStmt);
    }
    
    public void testToSqlNoName() throws Exception {
        Person p = new Person(6);
        String sqlStmt = p.toSql();
        System.err.println(sqlStmt);
        assertEquals("INSERT INTO HUMANS (id, created_at, updated_at, type, name, givenNames, nameQualification ) VALUES ( 6, NOW(), NOW(), 'Individual', 'Unknown', NULL, NULL);", sqlStmt);
    }
}
