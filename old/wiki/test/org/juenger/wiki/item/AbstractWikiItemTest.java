package org.juenger.wiki.item;

import java.util.Date;

import org.juenger.wiki.item.AbstractWikiItem;

import junit.framework.TestCase;

/**
 * Tests constructors of {@link AbstractWikiItem} and other methods.
 * 
 * @author jking
 *
 */
public class AbstractWikiItemTest extends TestCase {

	public static void main(String[] args) {
	}

	public AbstractWikiItemTest(String arg0) {
		super(arg0);
	}

	/*
	 * Test method for 'org.juenger.wiki.AbstractWikiItem.AbstractWikiItem(String, String)'
	 */
	public void testAbstractWikiItemStringStringValid() {
		TestWikiItem item = new TestWikiItem("testAuthor", "testName");
		assertEquals("testAuthor", item.getOriginalAuthor());
		assertEquals("testAuthor", item.getVersionEditor());
		assertEquals("testName", item.getVersionedName().getName());
		assertEquals(0, item.getVersionedName().getVersion());
		assertEquals(item.getCreationDate(),item.getVersionDate());
	}
	
	public void testAbstractWikiItemStringNullStringValid() {
		String testAuthorName = null;
		try {
			TestWikiItem item = new TestWikiItem(testAuthorName, "testName");
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("author parameter is null", iae.getMessage());
		}
	}
	
	public void testAbstractWikiItemStringEmptyStringValid() {
		try {
			TestWikiItem item = new TestWikiItem(" ", "testName");
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("author parameter is empty string", iae.getMessage());
		}
	}

	public void testAbstractWikiItemStringValidStringNull() {
		try {
			TestWikiItem item = new TestWikiItem("testAuthor", null);
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("name parameter is null", iae.getMessage());
		}
	}
	
	public void testAbstractWikiItemStringValidStringEmpty() {
		try {
			TestWikiItem item = new TestWikiItem("testAuthor", " ");
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("name parameter is empty string", iae.getMessage());
		}
	}
	
	public void testAbstractWikiItemAbstractWikiItemStringValid() throws Exception {
		TestWikiItem originalItem = new TestWikiItem("testAuthor", "testName");
		Thread.sleep(10); // artifial pause, to make the dates different!
		TestWikiItem revisedItem = new TestWikiItem(originalItem, "testEditor");
		assertEquals("testAuthor", revisedItem.getOriginalAuthor());
		assertEquals("testEditor", revisedItem.getVersionEditor());
		assertEquals(0, originalItem.getVersionedName().getVersion());
		assertEquals(1, revisedItem.getVersionedName().getVersion());
		assertEquals("testName", revisedItem.getVersionedName().getName());
		assertTrue(revisedItem.getVersionDate().after(revisedItem.getCreationDate()));
		assertEquals(originalItem.getCreationDate(), revisedItem.getCreationDate());
	}
	
	public void testAbstractWikiItemAbstractWikiItemNullStringValid() throws Exception {
		AbstractWikiItem originalItem = null;
		try {
			TestWikiItem item = new TestWikiItem(originalItem, "testEditor");
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("original item is null", iae.getMessage());
		}
	}
	
	public void testAbstractWikiItemValidStringNull() throws Exception {
		TestWikiItem originalItem = new TestWikiItem("testAuthor", "testName");
		try {
			TestWikiItem item = new TestWikiItem(originalItem, null);
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("editedBy is null", iae.getMessage());
		}
	}
	
	public void testAbstractWikiItemValidStringEmpty() throws Exception {
		TestWikiItem originalItem = new TestWikiItem("testAuthor", "testName");
		try {
			TestWikiItem item = new TestWikiItem(originalItem, " ");
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("editedBy is empty string", iae.getMessage());
		}
	}
	
    public void testDateCloningOnGetCreatedDate() throws Exception {
        TestWikiItem originalItem = new TestWikiItem("testAuthor", "testName");
        
        Date createdDate = originalItem.getCreationDate();
        createdDate.setTime(1); // journeying back in time :)
        
        Date createdDate2 = originalItem.getCreationDate();
        assertNotSame(createdDate, createdDate2);
        
        assertFalse(1 == createdDate2.getTime());
        
    }
    
    public void testDateCloningOnGetLastChangeDate() throws Exception {
        TestWikiItem originalItem = new TestWikiItem("testAuthor", "testName");
        
        Date lastChangedDate = originalItem.getVersionDate();
        lastChangedDate.setTime(1); // journeying back in time :)
        
        Date lastChangeDate2 = originalItem.getVersionDate();
        assertNotSame(lastChangedDate, lastChangeDate2);
        
        assertFalse(1 == lastChangeDate2.getTime());
    }
	
	private class TestWikiItem extends AbstractWikiItem {

		protected TestWikiItem(AbstractWikiItem originalItem, String editedBy) {
			super(originalItem, editedBy);
			// TODO Auto-generated constructor stub
		}

		public TestWikiItem(String author, String name) {
			super(author, name);
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getSize() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
		
	}
}
