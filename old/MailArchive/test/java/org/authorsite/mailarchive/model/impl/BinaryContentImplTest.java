/*
 * BinaryContentImplTest.java, created on 14-Mar-2004 at 20:16:02
 * 
 * Copyright John King, 2004.
 *
 *  BinaryContentImplTest.java is part of authorsite.org's MailArchive program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.model.impl;

import java.io.*;
import java.util.*;

import org.authorsite.mailarchive.model.*;

import junit.framework.*;

/**
 * Unit tests for non-persistent functionality of BinaryContentImpl.
 * 
 * @author jejking
 * @version $Revision: 1.8 $
 */
public class BinaryContentImplTest extends TestCase {

	private static byte[] jk1Bytes;
	private static byte[] jk2Bytes;

	static {
		try {
			//	load up the two test images as byte arrays
			InputStream jk1 = BinaryContentImplTest.class.getResourceAsStream("jejking1.jpg");
			InputStream jk2 = BinaryContentImplTest.class.getResourceAsStream("jejking2.jpg");
			jk1Bytes = getBytesFromInputStream(jk1);
			jk2Bytes = getBytesFromInputStream(jk2);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Constructor for BinaryContentImplTest.
	 * @param arg0
	 */
	public BinaryContentImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BinaryContentImplTest.class);
	}

	public void testEquals() {
		BinaryContentImpl one = new BinaryContentImpl();
		one.setContent(jk1Bytes);

		BinaryContentImpl two = new BinaryContentImpl();
		two.setContent(jk2Bytes);

		BinaryContentImpl three = new BinaryContentImpl();
		three.setContent((byte[]) jk1Bytes.clone());

		Assert.assertNotSame(one, two);
		Assert.assertEquals(one, one);

		Assert.assertNotSame(two, three);
		Assert.assertEquals(one, three);
	}

	public void testHashCode() {
		// very primitive, make sure hashCode for two different content arrays are different
		BinaryContentImpl one = new BinaryContentImpl();
		one.setContent(jk1Bytes);

		BinaryContentImpl two = new BinaryContentImpl();
		two.setContent(jk2Bytes);

		BinaryContentImpl three = new BinaryContentImpl();
		three.setContent((byte[]) jk1Bytes.clone());
		
		Assert.assertEquals(one.hashCode(), three.hashCode());
		Assert.assertFalse(one.hashCode() == two.hashCode());
		Assert.assertFalse(two.hashCode() == three.hashCode());
		
		// now, test with some byte arrrays that contain the same bytes but in a different order...
		
		BinaryContentImpl four= new BinaryContentImpl();
		BinaryContentImpl five = new BinaryContentImpl();
		byte[] oneTwoThree = {1,2,3};
		byte[] threeTwoOne = {3,2,1};
		four.setContent(oneTwoThree);
		five.setContent(threeTwoOne);
		
		Assert.assertFalse(four.hashCode() == five.hashCode());
		
		// make sure that hashCode changes after we update the byte array
		int originalHash = four.hashCode();
		four.setContent(threeTwoOne);
		Assert.assertFalse(originalHash == four.hashCode());
		
	}

	public void testAddingLanguages() {
		HashSet langSetOne = new HashSet();
		langSetOne.add(Language.EN);
		langSetOne.add(Language.FR);
		
		BinaryContentImpl one = new BinaryContentImpl();
		one.setLanguages(langSetOne);
		
		Set langs = one.getLanguages();
		Assert.assertEquals(2, langs.size());
		Assert.assertTrue(langs.contains(Language.EN));
		Assert.assertTrue(langs.contains(Language.FR));
		
		HashSet langSetTwo = new HashSet();
		langSetTwo.add(Language.ES);
		langSetTwo.add(Language.DE);
		langSetTwo.add(Language.AR);
		one.setLanguages(langSetTwo);
		
		langs = one.getLanguages();
		
		Assert.assertEquals(3, langs.size());
		Assert.assertTrue(langs.contains(Language.ES));
		Assert.assertTrue(langs.contains(Language.DE));
		Assert.assertTrue(langs.contains(Language.AR));
		
		HashSet noLangs = new HashSet();
		noLangs.add("this is not an implementation of Language");
		noLangs.add("and neither is this");
		try {one.setLanguages(noLangs);
			Assert.fail("Expected Illegal Argument Exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
				
		HashSet emptySet = new HashSet();
		one.setLanguages(emptySet);
		langs = one.getLanguages();
		Assert.assertEquals(0, langs.size());
		
		// and try it with a null
		try {
			one.setLanguages(null);
			Assert.fail("Expected Null Pointer Exception");
		}
		catch (NullPointerException npe) {
			Assert.assertTrue(true);
		}
		
		
	}
	/*
	private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		
		// Create the byte array to hold the data... (I know both files are under 10k!)
		byte[] bytes = new byte[10000];

		int bytesRead = is.read(bytes);
	    // Close the input stream and return bytes
		is.close();
		// we won't return a full 10000 element byte array
		int offset = 10000 - bytesRead;
		byte[] bytesToReturn = new byte[offset];
		System.arraycopy(bytes, 0 ,bytesToReturn, 0, 10000 - offset);
		return bytesToReturn;
	}
 */
	private static byte[] getBytesFromInputStream(InputStream is) throws IOException {
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int byteIn = is.read();
		while (byteIn >= 0) {
		    out.write(byteIn);
		    byteIn = is.read();
		}
		is.close();
		return out.toByteArray();
	}

}
