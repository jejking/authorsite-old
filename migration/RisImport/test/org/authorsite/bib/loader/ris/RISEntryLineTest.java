package org.authorsite.bib.loader.ris;

import junit.framework.TestCase;

public class RISEntryLineTest extends TestCase {

    public void testConstructor()
    {
        RISEntryLine l = new RISEntryLine("A1  - King, John");
        assertEquals("A1", l.getKey());
        assertEquals("King, John", l.getValue());
    }
    
}
