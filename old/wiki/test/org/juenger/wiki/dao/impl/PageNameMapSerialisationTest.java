package org.juenger.wiki.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.juenger.wiki.dao.impl.PageNameMap;
import org.juenger.wiki.item.Page;

import junit.framework.TestCase;

public class PageNameMapSerialisationTest extends TestCase {
    
    public void testSerialisation() throws Exception {
        
        // create a map with two named pages, one with two versions, one with three
        PageNameMap pnMap = new PageNameMap();
        
        Page pageOneV0 = new Page("author", "pageOne", "text");
        pnMap.addNewPage(pageOneV0);
        Page pageOneV1 = new Page(pageOneV0, "editor", "newText1");
        pnMap.addNewPageVersion(pageOneV1);
        
        Page pageTwoV0 = new Page("joe bloggs", "pageTwo", "text0");
        pnMap.addNewPage(pageTwoV0);
        Page pageTwoV1 = new Page(pageTwoV0, "editor", "text1");
        pnMap.addNewPageVersion(pageTwoV1);
        Page pageTwoV2 = new Page(pageTwoV1, "another editor", "text2");
        pnMap.addNewPageVersion(pageTwoV2);
            
        // serialise it to a byte array
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
        ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);
        ooStream.writeObject(pnMap);
        byte[] pnMapBytes = baoStream.toByteArray();
        
        // deserialise it from the byte array
        ByteArrayInputStream baiStream = new ByteArrayInputStream(pnMapBytes);
        ObjectInputStream oiStream = new ObjectInputStream(baiStream);
        PageNameMap deserialised = (PageNameMap) oiStream.readObject();
        
        // check that the objects are equal
        assertEquals(2, deserialised.size());
        List<Page> pageOneVersions = deserialised.getPageVersionsList("pageOne");
        assertEquals(2, pageOneVersions.size());
        assertEquals(pageOneV0, pageOneVersions.get(0));
        assertEquals(pageOneV1, pageOneVersions.get(1));
        List<Page> pageTwoVersions = deserialised.getPageVersionsList("pageTwo");
        assertEquals(3, pageTwoVersions.size());
        assertEquals(pageTwoV0, pageTwoVersions.get(0));
        assertEquals(pageTwoV1, pageTwoVersions.get(1));
        assertEquals(pageTwoV2, pageTwoVersions.get(2));
    }
    
}
