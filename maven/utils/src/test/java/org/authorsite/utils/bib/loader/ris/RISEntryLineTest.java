package org.authorsite.utils.bib.loader.ris;

import junit.framework.TestCase;

public class RISEntryLineTest extends TestCase {

    public void testConstructor() throws Exception
    {
        RISEntryLine l = new RISEntryLine("A1  - King, John");
        assertEquals("A1", l.getKey());
        assertEquals("King, John", l.getValue());
    }
    
    public void testKeyValueConstructor() throws Exception
    {
        RISEntryLine l = new RISEntryLine("A1", "King, John");
        assertEquals("A1", l.getKey());
        assertEquals("King, John", l.getValue());
        
        RISEntryLine l2 = new RISEntryLine("BB", "King, John");
        assertEquals("BB", l2.getKey());
        
        try {
            RISEntryLine l3 = new RISEntryLine("a2", "King, John");
            fail("expected exception");
        }
        catch (RISException e)
        {
            assertTrue(true);
        }
        
        try {
            RISEntryLine l4 = new RISEntryLine("AA2", "King, John");
            fail("expected exception");
        }
        catch (RISException e )
        {
            assertTrue(true);
        }
        
        try {
            RISEntryLine l5 = new RISEntryLine(" AA", "King, John");
            fail("expected exception");
        }
        catch (RISException e)
        {
            assertTrue(true);
        }
        
        try {
            RISEntryLine l4 = new RISEntryLine("A", "King, John");
            fail("expected exception");
        }
        catch (RISException e )
        {
            assertTrue(true);
        }
        
    }
    
    public void testToString() throws Exception {
        RISEntryLine l = new RISEntryLine("A1  - King, John");
        assertEquals(l.toString() , "A1  - King, John");
    }
    
}
