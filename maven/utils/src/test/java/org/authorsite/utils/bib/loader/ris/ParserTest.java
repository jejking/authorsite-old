package org.authorsite.utils.bib.loader.ris;

import junit.framework.TestCase;


public class ParserTest extends TestCase {

    public void testLineIsTagged() {
        
        Parser p = new Parser();
        
        assertTrue(p.lineIsTagged("AB  - King, John"));
        assertTrue(p.lineIsTagged("A1  - King, John"));
        assertTrue(p.lineIsTagged("11  - "));
        assertFalse(p.lineIsTagged("a1  - King, John"));
        assertFalse(p.lineIsTagged("A1 - King, John"));
        assertFalse(p.lineIsTagged(" A1  - King, John"));
        assertFalse(p.lineIsTagged("A1"));
        assertFalse(p.lineIsTagged("\nA1  - asdf"));
        assertFalse(p.lineIsTagged("AAA  - King, John"));
        assertFalse(p.lineIsTagged("A  - King, John"));
        assertFalse(p.lineIsTagged("AA -  King, John"));
        assertFalse(p.lineIsTagged(""));
        assertFalse(p.lineIsTagged(null));
    }
    
    public void testProcess() throws Exception {
        Parser p = new Parser();
        p.readFile("biblio.txt"); // expect no exception
    }
    
}
