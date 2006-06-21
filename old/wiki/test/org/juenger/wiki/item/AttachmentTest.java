package org.juenger.wiki.item;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.juenger.wiki.item.Attachment;

import junit.framework.TestCase;

public class AttachmentTest extends TestCase {

	public static void main(String[] args) {
	}

	public AttachmentTest(String arg0) {
		super(arg0);
	}
	
	// constructors
	public void testAttachmentStringStringStringByteArray() {
		Attachment atmt = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		assertEquals("testAuthor", atmt.getOriginalAuthor());
		assertEquals("testAuthor", atmt.getVersionEditor());
		assertEquals(atmt.getCreationDate(), atmt.getVersionDate());
		assertEquals("application/octet-stream", atmt.getMimeType());
	}
	
	public void testAttachmentStringStringStringNullByteArray() {
		try {
			Attachment atmt = new Attachment("testAuthor", "testName", null, new byte[]{1,2,3});
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("mimeType is null", iae.getMessage());
		}
	}
	
	public void testAttachmentStringStringStringEmptyByteArray() {
		try {
			Attachment atmt = new Attachment("testAuthor", "testName", "\t", new byte[]{1,2,3});
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("mimeType is empty string", iae.getMessage());
		}
	}
	
	public void testAttachmentStringStringStringByteArrayNull() {
		try {
			Attachment atmt = new Attachment("testAuthor", "testName", "application/octet-stream", null);
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("content is null", iae.getMessage());
		}
	}
	
	public void testAttachmentsStringStringStringByteArrayZeroLength() {
		try {
			Attachment atmt = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[0]);
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("content is zero length byte array", iae.getMessage());
		}
	}
	
	// getting byte stream
	public void testGetByteStream() throws IOException {
		Attachment atmt = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		InputStream byteStream = atmt.getContentAsStream();
		
		byte[] retrievedBytes = new byte[3];
		byteStream.read(retrievedBytes);
		
		assertEquals(1, retrievedBytes[0]);
		assertEquals(2, retrievedBytes[1]);
		assertEquals(3, retrievedBytes[2]);
	}
	
	
	// encapsulation of byte array
	public void testByteArrayNotModifiableFromOutside() throws Exception {
		byte[] originalBytes = new byte[]{1,2,3};
		Attachment atmt = new Attachment("testAuthor", "testName", "application/octet-stream", originalBytes);
		// swap original around
		originalBytes[2] = 1;
		originalBytes[0] = 3;
		
		InputStream byteStream = atmt.getContentAsStream();
		byte[] retrievedBytes = new byte[3];
		byteStream.read(retrievedBytes);
		
		assertEquals(1, retrievedBytes[0]);
		assertEquals(2, retrievedBytes[1]);
		assertEquals(3, retrievedBytes[2]);
	}
	
	// constructor which produces new version
	public void testAttachmentAttachmentStringByteArray() throws Exception {
		Attachment original = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		byte[] nextVersionOriginalBytes = new byte[]{3,4,5};
		Attachment nextVersion = new Attachment(original, "testEditor", nextVersionOriginalBytes);
		assertEquals(1, nextVersion.getVersionedName().getVersion());
		
		byte[] nextVersionContent = new byte[3];
		InputStream byteStream = nextVersion.getContentAsStream();
		byteStream.read(nextVersionContent);
		assertEquals(3, nextVersionContent[0]);
		assertEquals(4, nextVersionContent[1]);
		assertEquals(5, nextVersionContent[2]);
		byteStream.close();
		
		// check we're safe against manipulation or next version byte array
		nextVersionOriginalBytes[0] = 6;
		nextVersionOriginalBytes[1] = 7;
		nextVersionOriginalBytes[2] = 8;
		
		byte[] nextVersionContent2 = new byte[3];
		InputStream byteStream2 = nextVersion.getContentAsStream();
		byteStream2.read(nextVersionContent2);
		assertEquals(3, nextVersionContent2[0]);
		assertEquals(4, nextVersionContent2[1]);
		assertEquals(5, nextVersionContent2[2]);
		byteStream2.close();
	}
	
	public void testAttachmentAttachmentStringByteArrayNull() {
		Attachment original = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		try {
			Attachment atmt = new Attachment(original, "testEditor", null);
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("content is null", iae.getMessage());
		}
	}
	
	public void testAttachmentAttachmentStringByteArrayZeroLength() {
		Attachment original = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		try {
			Attachment atmt = new Attachment(original, "testEditor", new byte[0]);
			fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			assertEquals("content is zero length byte array", iae.getMessage());
		}
	}
	
	// equals - combination of name and version
	public void testEquals() {
		// try two identical objects
		Attachment one = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		Attachment two = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		assertTrue(one.equals(two));
		assertTrue(two.equals(one));
		
		// an object must be equals to itself
		assertTrue(one.equals(one));
		
		// null is not equal
		assertFalse(one.equals(null));
		
		// not equal to another object type
		assertFalse(one.equals("a string object"));
		
		// objects with different names are not equal
		Attachment three = new Attachment("testAuthor", "three is not the same as testName", "application/octet-stream", new byte[]{1,2,3});
		assertFalse(one.equals(three));
		assertFalse(three.equals(one));
		
		// objects with different versions are not equal
		Attachment threeV1 = new Attachment(three, "testEditor", new byte[]{4,5,6});
		assertFalse(three.equals(threeV1));
		assertFalse(threeV1.equals(three));
	}
	
	// hash code - calculated from name and version
	public void testHashCode() {
		// two equals objects must have same hash code
		Attachment one = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		Attachment two = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		assertEquals(one.hashCode(), two.hashCode());
		
		// objects with different names should have different hash codes
		Attachment three = new Attachment("testAuthor", "three is not the same as testName", "application/octet-stream", new byte[]{1,2,3});
		assertFalse(one.hashCode() == three.hashCode());
		
		// and objects with different versions should have different hash codes
		Attachment threeV1 = new Attachment(three, "testEditor", new byte[]{4,5,6});
		assertFalse(three.hashCode() == threeV1.hashCode());
	}
	
	// compare to - sorts on name, then version number
	public void testCompareTo() {
		// equals should give us 0
		Attachment one = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		Attachment two = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		assertEquals(0, one.compareTo(two));
		assertEquals(0, two.compareTo(one));
		
		assertEquals(0, one.compareTo(one));
		
		Attachment a = new Attachment("testAuthor", "a", "application/octet-stream", new byte[]{1,2,3});
		Attachment b = new Attachment("testAuthor", "b", "application/octet-stream", new byte[]{1,2,3});
		
		assertTrue(a.compareTo(b) < 0);
		assertTrue(b.compareTo(a) > 0);
		
		// and a second version should be sorted after the initial version
		Attachment b2 = new Attachment(b, "testEditor", new byte[]{4,5,6});
		assertTrue(b.compareTo(b2) < 0);
		assertTrue(b2.compareTo(b) > 0);
		
		// and a should come before b2 by virtual of its name
		assertTrue(a.compareTo(b2) < 0);
		assertTrue(b2.compareTo(a) > 0);
		
		// null must throw a null pointer
		try {
			b2.compareTo(null);
			fail("expected null pointer exception");
		}
		catch (NullPointerException npe) {
			assertTrue(true);
		}
		
		try {
			b2.compareTo("a string");
			fail("expected class cast exception");
		}
		catch (ClassCastException cce) {
			assertEquals("cannot compare instance of java.lang.String to Attachment",
					cce.getMessage());
		}
	}
	
	public void testSerialization() throws Exception {
		Attachment one = new Attachment("testAuthor", "testName", "application/octet-stream", new byte[]{1,2,3});
		
		ByteArrayOutputStream baOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutputStream = new ObjectOutputStream(baOutputStream);
		objOutputStream.writeObject(one);
		
		byte[] oneSerialized = baOutputStream.toByteArray();
		
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(oneSerialized);
		ObjectInputStream objInputStream = new ObjectInputStream(baInputStream);
		
		Attachment oneDeserialized = (Attachment) objInputStream.readObject();
		assertEquals(one, oneDeserialized);
		assertEquals(one.hashCode(), oneDeserialized.hashCode());
		assertEquals(one.toString(), oneDeserialized.toString());
		
	}

}
