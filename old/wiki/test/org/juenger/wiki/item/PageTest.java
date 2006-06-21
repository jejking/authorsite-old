package org.juenger.wiki.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.juenger.wiki.item.Attachment;
import org.juenger.wiki.item.Page;
import org.juenger.wiki.item.VersionedName;

import junit.framework.TestCase;

public class PageTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PageTest.class);
	}

	public PageTest(String arg0) {
		super(arg0);
	}
	
	// test constructors
    
    public void testPageTestStringStringString() throws Exception {
        Page page = new Page("testAuthor", "testName", "testText");
        assertEquals("testAuthor", page.getOriginalAuthor());
        assertEquals("testAuthor", page.getVersionEditor());
        assertEquals("testName", page.getVersionedName().getName());
        assertEquals("testText", page.getText());
        
        // test we have an empty map of attachments
        assertTrue(page.getAttachmentVersionedNames().isEmpty());
        // and we can't modify it
        Attachment attachment = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
        
        try {
            page.getAttachmentVersionedNames().add(new VersionedName("key", 0));
            fail("expected unsupported operation exception");
        }
        catch (UnsupportedOperationException uoe) {
            assertTrue(true);
        }
        
    }
    
    public void testPageTestStringStringStringNull() throws Exception {
        try {
            Page page = new Page("testAuthor", "testName", null);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("text parameter is null", iae.getMessage());
        }
    }
    
    public void testPageStringStringStringEmpty() throws Exception {
        try {
            Page page = new Page("testAuthor", "testName", " ");
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("text parameter is empty string", iae.getMessage());
        }
    }
    
    public void testPageOriginalStringAttachments() throws Exception {
        Page originalPage = new Page("testAuthor", "testName", "testText");
        Set<VersionedName> attachments = new HashSet<VersionedName>();
        VersionedName test0 = new VersionedName("test",0); 
        attachments.add(test0);
        
        Page newVersion = new Page(originalPage, "testEditor", attachments);
        
        assertEquals("testText", newVersion.getText());
        assertEquals(1, newVersion.getVersionedName().getVersion());
        
        Set<VersionedName> retrievedAttachments = newVersion.getAttachmentVersionedNames();
        assertNotSame(attachments, retrievedAttachments);
        try {
            retrievedAttachments.add(new VersionedName("test2", 0));
            fail("expected unsupported operation exception");
        }
        catch (UnsupportedOperationException uoe) {
            assertTrue(true);
        }
        assertEquals(1, retrievedAttachments.size());
        boolean foundVersionedName = false;
        Iterator retrievedAttachmentsIt = retrievedAttachments.iterator();
        VersionedName retrievedName = (VersionedName) retrievedAttachmentsIt.next();
        assertNotNull(retrievedName);
        assertEquals(test0, retrievedName);
    }
    
    public void testPageOriginalStringAttachmentsNull() {
        Page originalPage = new Page("testAuthor", "testName", "testText");
        Set<VersionedName> attachments = null;
        try {
            Page newVersion = new Page(originalPage, "testEditor", attachments);
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("new attachment set is null", iae.getMessage());
        }
    }
    
    public void testPageOriginalStringStringSameText() {
        Page originalPage = new Page("testAuthor", "testName", "testText");
        try {
            Page newVersion = new Page(originalPage, "testEditor", "testText");
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertEquals("new text is identical with old text", iae.getMessage());
        }
    }
    
    public void testEquals() {
    	// equals checks name, version, text
    	Page pageOne = new Page("testAuthor", "testName", "testText");
    	Page pageTwo = new Page("testAuthor", "testName", "testText");
    	
    	assertEquals(pageOne, pageTwo);
    	assertTrue(pageOne.equals(pageTwo));
    	assertTrue(pageTwo.equals(pageOne));
    	
    	// object equals with itself
    	assertTrue(pageOne.equals(pageOne));
    	
    	// check on different names
    	Page pageThree = new Page("testAuthor", "testThree", "testText");
    	Page pageFour = new Page("testAuthor", "testFour", "testText");
    	
    	assertFalse(pageThree.equals(pageFour));
    	assertFalse(pageFour.equals(pageThree));
    	
    	// check on different versions
    	Page pageFourVersionTwo = new Page(pageFour, "testEditor", new HashSet<VersionedName>());
    	assertFalse(pageFour.equals(pageFourVersionTwo));
    	assertFalse(pageFourVersionTwo.equals(pageFour));

    	
    	// and a completely different object
    	assertFalse(pageFour.equals("a string"));
    	
    	// and null
    	assertFalse(pageFour.equals(null));
    }
    
    public void testHashCode() {
    	Page pageOne = new Page("testAuthor", "testName", "testText");
    	Page pageTwo = new Page("testAuthor", "testName", "testText");
    	
    	assertEquals(pageOne.hashCode(), pageTwo.hashCode());
    	
    	// non equals objects should produce different hash codes
    	
    	//    	 check on different names
    	Page pageThree = new Page("testAuthor", "testThree", "testText");
    	Page pageFour = new Page("testAuthor", "testFour", "testText");
    	
    	assertFalse(pageThree.hashCode() == pageFour.hashCode());
    	
    	// check on different versions
    	Page pageFourVersionTwo = new Page(pageFour, "testEditor", new HashSet<VersionedName>());
    	assertFalse(pageFour.hashCode() == pageFourVersionTwo.hashCode());
    }
    
    public void testCompareTo() {
    	
    	// test consistent with equals
    	Page pageOne = new Page("testAuthor", "testName", "testText");
    	Page pageTwo = new Page("testAuthor", "testName", "testText");
    	assertEquals(0, pageOne.compareTo(pageTwo));
    	assertEquals(0, pageTwo.compareTo(pageOne));
    	
    	// test sorting on name
    	Page pageA = new Page("testAuthor", "a", "testText");
    	Page pageB = new Page("testAuthor", "b", "textText");
    	assertTrue(pageA.compareTo(pageB) < 0);
    	assertTrue(pageB.compareTo(pageA) > 0);
    	
    	// and now on version
    	Page pageFour = new Page("testAuthor", "testFour", "testText");
    	Page pageFourVersionTwo = new Page(pageFour, "testEditor", new HashSet<VersionedName>());
    	assertTrue(pageFour.compareTo(pageFourVersionTwo) < 0);
    	assertTrue(pageFourVersionTwo.compareTo(pageFour) > 0);
    	
    	// and finally on text
    	Page pageFive = new Page("testAuthor", "testName", "different text");
    	assertTrue(pageFive.compareTo(pageOne) == 0);
    	assertTrue(pageOne.compareTo(pageFive) == 0);
    	
    	// null must throw null pointer
    	try {
    		pageFive.compareTo(null);
    		fail("expected null pointer exception");
    	}
    	catch (NullPointerException npe) {
    		assertEquals("cannot compare to null", npe.getMessage());
    	}
    	
    	// and a non Page object should throw class cast exception
    	try {
    		pageFive.compareTo("a string");
    		fail("expected class cast exception");
    	}
    	catch (ClassCastException cce) {
    		assertEquals("cannot compare instance of java.lang.String to Page", cce.getMessage());
    	}
    }
    
    public void testSerialization() throws Exception {
        Page pageOne = new Page("testAuthor", "testName", "testText");
        // add a versionedName
        VersionedName attmt1 = new VersionedName("attmt1.txt", 0);
        Set<VersionedName> attmts = new HashSet<VersionedName>();
        attmts.add(attmt1);
        Page pageOneb = new Page(pageOne, "testEditor", attmts); // this is what we'll serialize
        
        ByteArrayOutputStream baOs = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baOs);
        oos.writeObject(pageOneb);
        
        byte[] pageOnebBytes = baOs.toByteArray();
        
        ByteArrayInputStream baIs = new ByteArrayInputStream(pageOnebBytes);
        ObjectInputStream ois = new ObjectInputStream(baIs);
        
        Page deserialized = (Page) ois.readObject();
        
        assertEquals(pageOneb, deserialized);
        assertEquals(pageOneb.hashCode(), deserialized.hashCode());
        assertEquals(pageOneb.toString(), deserialized.toString());
    }
    

}
