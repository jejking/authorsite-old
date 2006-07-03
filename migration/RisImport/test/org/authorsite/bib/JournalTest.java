package org.authorsite.bib;

import junit.framework.TestCase;

public class JournalTest extends TestCase {

    public void testToSql() {
        Journal j = new Journal(1);
        j.setTitle("Journal of Java Studies");
        
        System.err.println(j.toSql());
    }
    
}
