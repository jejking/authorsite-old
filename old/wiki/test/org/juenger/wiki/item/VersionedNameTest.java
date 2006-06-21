package org.juenger.wiki.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.juenger.wiki.item.VersionedName;

import junit.framework.TestCase;

public class VersionedNameTest extends TestCase {

	public static void main(String[] args) {
	}
	
	public void testSerialisation() throws Exception {
		
		VersionedName name1 = new VersionedName("name", 1);
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream();
		ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);
		
		ooStream.writeObject(name1);
		byte[] name1Bytes = baoStream.toByteArray();
		
		ByteArrayInputStream baiStream = new ByteArrayInputStream(name1Bytes);
		ObjectInputStream oiStream = new ObjectInputStream(baiStream);
		VersionedName name1Deserialized = (VersionedName) oiStream.readObject();
		
		assertEquals(name1, name1Deserialized);
		assertEquals(name1.hashCode(), name1Deserialized.hashCode());
		assertEquals(name1.toString(), name1Deserialized.toString());
	}
    
    public void testIsNextVersion() {
        VersionedName name1 = new VersionedName("name", 1);
        VersionedName name2 = new VersionedName("name", 2);
        VersionedName name3 = new VersionedName("name", 3);
        VersionedName version4 = new VersionedName("version", 4);
        
        assertTrue(name1.isNextVersion(name2));
        assertTrue(name2.isNextVersion(name3));
        
        assertFalse(name1.isNextVersion(name3));
        assertFalse(name3.isNextVersion(name1));
        assertFalse(name3.isNextVersion(version4));
        
    }
    
    public void testIsPreviousVersion() {
        VersionedName name1 = new VersionedName("name", 1);
        VersionedName name2 = new VersionedName("name", 2);
        VersionedName name3 = new VersionedName("name", 3);
        VersionedName version4 = new VersionedName("version", 4);
        
        assertTrue(name2.isPreviousVersion(name1));
        assertTrue(name3.isPreviousVersion(name2));
        
        assertFalse(name3.isPreviousVersion(name1));
        assertFalse(name1.isPreviousVersion(name3));
        assertFalse(version4.isPreviousVersion(name3));
    }

}
