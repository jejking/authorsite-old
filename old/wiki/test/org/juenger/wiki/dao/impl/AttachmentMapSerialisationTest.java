package org.juenger.wiki.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import org.juenger.wiki.dao.impl.AttachmentMap;
import org.juenger.wiki.item.Attachment;

import junit.framework.TestCase;

public class AttachmentMapSerialisationTest extends TestCase {

    public void testSerialisation() throws Exception {
        // create a map with two named pages, each with two attachments, each with two versions...
        
        AttachmentMap map = new AttachmentMap();
        
        Attachment p1AttmtOne = new Attachment("author", "attmtOne", "application/octet-stream", new byte[]{1,2,3});
        map.addNewAttachmentForPage("pageOne", p1AttmtOne);
        Attachment p1AttmtOneV1 = new Attachment(p1AttmtOne, "editor", new byte[]{2,3,4});
        map.addNewAttachmentVersion("pageOne", p1AttmtOneV1);
        
        Attachment p1AttmtTwo = new Attachment("joe", "attmtTwo", "application/octet-stream", new byte[]{3,4,5});
        map.addNewAttachmentForPage("pageOne", p1AttmtTwo);
        Attachment p1AttmtTwoV1 = new Attachment(p1AttmtTwo, "bob", new byte[]{4,5,6});
        map.addNewAttachmentVersion("pageOne", p1AttmtTwoV1);
        
        Attachment p2AttmtThree = new Attachment("pete", "attmtThree", "application/octet-stream", new byte[]{5,6,7});
        map.addNewAttachmentForPage("pageTwo", p2AttmtThree);
        Attachment p2AttmtThreeV1 = new Attachment(p2AttmtThree, "foo", new byte[]{6,7,8});
        map.addNewAttachmentVersion("pageTwo", p2AttmtThreeV1);
        
        Attachment p2AttmtFour = new Attachment("bar", "attmtFour", "application/octet-stream", new byte[]{7,8,9});
        map.addNewAttachmentForPage("pageTwo", p2AttmtFour);
        Attachment p2AttmtFourV1 = new Attachment(p2AttmtFour, "wibble", new byte[]{8,9,10});
        map.addNewAttachmentVersion("pageTwo", p2AttmtFourV1);
        
        ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
        ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);
        ooStream.writeObject(map);
        
        byte[] mapBytes = baoStream.toByteArray();
        
        ByteArrayInputStream baiStream = new ByteArrayInputStream(mapBytes);
        ObjectInputStream oiStream = new ObjectInputStream(baiStream);
        
        AttachmentMap deserialised = (AttachmentMap) oiStream.readObject();
        
        // now check the deserialised version is the same
        assertEquals(2, deserialised.size());
        Map<String, List<Attachment>> pageOneAttmtMap = deserialised.getPageAttachmentMap("pageOne");
        assertEquals(2, pageOneAttmtMap.size());
        List<Attachment> attmtOneVersions = pageOneAttmtMap.get("attmtOne");
        assertEquals(p1AttmtOne, attmtOneVersions.get(0));
        assertEquals(p1AttmtOneV1, attmtOneVersions.get(1));
        List<Attachment> attmtTwoVersions = pageOneAttmtMap.get("attmtTwo");
        assertEquals(p1AttmtTwo, attmtTwoVersions.get(0));
        assertEquals(p1AttmtTwoV1, attmtTwoVersions.get(1));
        
        Map<String, List<Attachment>> pageTwoAttmtMap = deserialised.getPageAttachmentMap("pageTwo");
        assertEquals(2, pageTwoAttmtMap.size());
        List<Attachment> attmtThreeVersions = pageTwoAttmtMap.get("attmtThree");
        assertEquals(p2AttmtThree, attmtThreeVersions.get(0));
        assertEquals(p2AttmtThreeV1, attmtThreeVersions.get(1));
        List<Attachment> attmtFourVersions = pageTwoAttmtMap.get("attmtFour");
        assertEquals(p2AttmtFour, attmtFourVersions.get(0));
        assertEquals(p2AttmtFourV1, attmtFourVersions.get(1));
    }

}
