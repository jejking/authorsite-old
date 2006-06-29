package org.authorsite.bib.loader.ris;

import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class RISEntryTest extends TestCase {

    public void testAddEntryLine() throws Exception {
        
        RISEntry entry = new RISEntry();
        entry.addEntryLine(new RISEntryLine("A1", "King, John"));
        entry.addEntryLine(new RISEntryLine("A1", "Bar, Foo"));
        entry.addEntryLine(new RISEntryLine("TY", "BOOK"));
        
        List<String> a1s = entry.getValues("A1");
        assertNotNull(a1s);
        assertEquals(2, a1s.size());
        assertEquals("King, John", a1s.get(0));
        assertEquals("Bar, Foo", a1s.get(1));
        
        List<String> ty = entry.getValues("TY");
        assertNotNull(ty);
        assertEquals(1, ty.size());
        assertEquals("BOOK", ty.get(0));
        
        List<String> blah = entry.getValues("BLAH");
        assertEquals(blah, Collections.EMPTY_LIST);
    }
    
}
