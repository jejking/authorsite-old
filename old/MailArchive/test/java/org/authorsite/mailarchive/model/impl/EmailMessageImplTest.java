/*
 * EmailMessageImplTest.java, created on 30-May-2004 at 20:14:58
 * 
 * Copyright John King, 2004.
 *
 *  EmailMessageImplTest.java is part of authorsite.org's MailArchive program.
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

import java.util.*;

import org.authorsite.mailarchive.model.*;

import junit.framework.*;

/**
 * Unit tests for <code>EmailMessageImpl</code>.
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class EmailMessageImplTest extends TestCase {

	public EmailMessageImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(EmailMessageImplTest.class);
	}

	public void testSetTextParts() {

		// create a set of TextContentImpls and that should be ok

		Set set1 = new HashSet();
		TextContentImpl text1 = new TextContentImpl();
		text1.setContent("some text");
		set1.add(text1);
		TextContentImpl text2 = new TextContentImpl();
		text2.setContent("some other text");
		set1.add(text2);

		EmailMessageImpl email1 = new EmailMessageImpl();
		email1.setTextParts(set1);

		Assert.assertEquals(set1, email1.getTextParts());

		// attempt to set null set - should be allowed
		EmailMessageImpl email2 = new EmailMessageImpl();
		try {
			email2.setTextParts(null);
			Assert.fail("Expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

		// test with objects which aren't instances of TextContent
		Set set2 = new HashSet();
		set2.add("this is not a text content instance");
		EmailMessageImpl email3 = new EmailMessageImpl();
		try {
			email3.setTextParts(set2);
			Assert.fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}

	public void testAddTextPart() {
		Set set1 = new HashSet();
		TextContentImpl text1 = new TextContentImpl();
		text1.setContent("this is some content");
		set1.add(text1);

		EmailMessage mail1 = new EmailMessageImpl();
		mail1.setTextParts(set1);

		TextContent text2 = new TextContentImpl();
		mail1.addTextPart(text2);

		Set returnedSet = mail1.getTextParts();
		Iterator it = returnedSet.iterator();
		boolean foundAddedText = false;
		while (it.hasNext()) {
			TextContent text = (TextContent) it.next();
			if (text.equals(text2)) {
				foundAddedText = true;
				break;
			}
		}
		Assert.assertTrue(foundAddedText);
	}

	public void testRemoveTestPart() {
		Set set1 = new HashSet();
		TextContentImpl text1 = new TextContentImpl();
		TextContent text2 = new TextContentImpl();
		text2.setContent("this is also content");
		text1.setContent("this is some content");
		set1.add(text1);
		set1.add(text2);

		EmailMessage mail1 = new EmailMessageImpl();
		mail1.setTextParts(set1);

		mail1.removeTextContent(text2);

		Set returnedSet = mail1.getTextParts();
		Iterator it = returnedSet.iterator();
		boolean foundRemovedText = false;
		while (it.hasNext()) {
			TextContent text = (TextContent) it.next();
			if (text.equals(text2)) {
				foundRemovedText = true;
				break;
			}
		}
		Assert.assertFalse(foundRemovedText);
	}

	public void testSetBinaryParts() {
		//		create a set of TextContentImpls and that should be ok

		Set set1 = new HashSet();
		BinaryContent bin1 = new BinaryContentImpl();
		bin1.setContent(new byte[] { 1, 2, 3, 4 });
		set1.add(bin1);
		BinaryContent bin2 = new BinaryContentImpl();
		bin2.setContent(new byte[] { 5, 6, 7, 8 });
		set1.add(bin2);

		EmailMessageImpl email1 = new EmailMessageImpl();
		email1.setBinaryParts(set1);

		Assert.assertEquals(set1, email1.getBinaryParts());

		// attempt to set null set - should be allowed
		EmailMessageImpl email2 = new EmailMessageImpl();
		try {
			email2.setTextParts(null);
			Assert.fail("Expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

		// test with objects which aren't instances of TextContent
		Set set2 = new HashSet();
		set2.add("this is not a binary content instance");
		EmailMessageImpl email3 = new EmailMessageImpl();
		try {
			email3.setTextParts(set2);
			Assert.fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}

	public void testAddBinaryPart() {
		Set set1 = new HashSet();
		BinaryContent bin1 = new BinaryContentImpl();
		bin1.setContent(new byte[] { 1, 2, 3, 4, 5 });
		set1.add(bin1);

		EmailMessage mail1 = new EmailMessageImpl();
		mail1.setBinaryParts(set1);

		BinaryContent bin2 = new BinaryContentImpl();
		mail1.addBinaryPart(bin2);

		Set returnedSet = mail1.getBinaryParts();
		Iterator it = returnedSet.iterator();
		boolean foundAddedBin = false;
		while (it.hasNext()) {
			BinaryContent bin = (BinaryContent) it.next();
			if (bin.equals(bin2)) {
				foundAddedBin = true;
				break;
			}
		}
		Assert.assertTrue(foundAddedBin);
	}

	public void testRemoveBinaryPart() {
		Set set1 = new HashSet();
		BinaryContent bin1 = new BinaryContentImpl();
		BinaryContent bin2 = new BinaryContentImpl();
		bin1.setContent(new byte[] { 1, 2, 3, 4, 5 });
		bin2.setContent(new byte[] { 6, 7, 8, 9, 10 });
		set1.add(bin1);
		set1.add(bin2);
		EmailMessage mail1 = new EmailMessageImpl();
		mail1.setBinaryParts(set1);

		mail1.removeBinaryPart(bin2);

		Set returnedSet = mail1.getBinaryParts();
		Iterator it = returnedSet.iterator();
		boolean foundRemovedBin = false;
		while (it.hasNext()) {
			BinaryContent bin = (BinaryContent) it.next();
			if (bin.equals(bin2)) {
				foundRemovedBin = true;
				break;
			}
		}
		Assert.assertFalse(foundRemovedBin);
	}

	public void testAddMessageEmailAddress() {
		EmailMessage mail = new EmailMessageImpl();
		EmailAddress address = new EmailAddressImpl();
		address.setAddress("test@test.com");
		MessageEmailAddress test1 = new MessageEmailAddressImpl();
		test1.setEmailAddress(address);
		test1.setRole(EmailAddressRole.FROM);
		mail.addMessageEmailAddress(test1);

		Set meaSet1 = mail.getMessageEmailAddresses();
		boolean foundMea = false;
		Iterator it = meaSet1.iterator();
		while (it.hasNext()) {
			MessageEmailAddress mea = (MessageEmailAddress) it.next();
			if (mea.equals(test1)) {
				foundMea = true;
				break;
			}
		}
		Assert.assertTrue(foundMea);

		// check that we can't add duff Meas...
		MessageEmailAddress test2 = new MessageEmailAddressImpl(); // no address or role set
		try {
			mail.addMessageEmailAddress(test2);
			Assert.fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

		MessageEmailAddress test3 = new MessageEmailAddressImpl(); // no role set
		EmailAddress address2 = new EmailAddressImpl();
		test3.setEmailAddress(address2);
		try {
			mail.addMessageEmailAddress(test3);
			Assert.fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

		MessageEmailAddress test4 = new MessageEmailAddressImpl(); // to address set
		test4.setRole(EmailAddressRole.BCC);
		try {
			mail.addMessageEmailAddress(test3);
			Assert.fail("expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}

	public void testGetMessageEmailAddressesInRole() {
		EmailMessage mail = new EmailMessageImpl();
		// create some MEAs - TO, FROM
		MessageEmailAddress to1 = new MessageEmailAddressImpl();
		EmailAddress testAddr1 = new EmailAddressImpl();
		testAddr1.setAddress("test1@test.com");
		to1.setEmailAddress(testAddr1);
		to1.setRole(EmailAddressRole.TO);

		MessageEmailAddress to2 = new MessageEmailAddressImpl();
		EmailAddress testAddr2 = new EmailAddressImpl();
		testAddr2.setAddress("test2@test.com");
		to2.setEmailAddress(testAddr2);
		to2.setRole(EmailAddressRole.TO);

		MessageEmailAddress from1 = new MessageEmailAddressImpl();
		EmailAddress fromAddr1 = new EmailAddressImpl();
		testAddr1.setAddress("test3@test.com");
		from1.setRole(EmailAddressRole.FROM);
		from1.setEmailAddress(fromAddr1);

		MessageEmailAddress from2 = new MessageEmailAddressImpl();
		EmailAddress fromAddr2 = new EmailAddressImpl();
		testAddr2.setAddress("test4@test.com");
		from2.setRole(EmailAddressRole.FROM);
		from2.setEmailAddress(fromAddr2);

		Set meaSet = new HashSet();
		meaSet.add(to1);
		meaSet.add(to2);
		meaSet.add(from1);
		meaSet.add(from2);

		mail.setMessageEmailAddresses(meaSet);

		Set toSet = mail.getMessageEmailAddressesInRole(EmailAddressRole.TO);
		Assert.assertEquals(2, toSet.size());

		boolean foundTo1 = false;
		boolean foundTo2 = false;

		Iterator toIt = toSet.iterator();
		while (toIt.hasNext()) {
			MessageEmailAddress mea = (MessageEmailAddress) toIt.next();
			Assert.assertEquals(EmailAddressRole.TO, mea.getRole());
			if (mea.equals(to1)) {
				foundTo1 = true;
			}
			if (mea.equals(to2)) {
				foundTo2 = true;
			}
		}

		Assert.assertTrue(foundTo1);
		Assert.assertTrue(foundTo2);

	}

	public void testSetMessageEmailAddresses() {
		EmailAddress testAddr1 = new EmailAddressImpl();
		testAddr1.setAddress("test1@test.com");

		// double check that an email address can send mail to itself
		EmailMessage mail2 = new EmailMessageImpl();
		MessageEmailAddress toSelf = new MessageEmailAddressImpl();
		toSelf.setEmailAddress(testAddr1);
		toSelf.setRole(EmailAddressRole.TO);

		MessageEmailAddress fromSelf = new MessageEmailAddressImpl();
		fromSelf.setEmailAddress(testAddr1);
		fromSelf.setRole(EmailAddressRole.FROM);

		Set meaSet = new HashSet();
		meaSet.add(toSelf);
		meaSet.add(fromSelf);

		mail2.setMessageEmailAddresses(meaSet);

		// test with a set containing a MEA with null emailAddress
		MessageEmailAddress nullAddr = new MessageEmailAddressImpl();
		nullAddr.setRole(EmailAddressRole.TO);
		meaSet = new HashSet();
		meaSet.add(nullAddr);
		try {
			mail2.setMessageEmailAddresses(meaSet);
			Assert.fail("Expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

		// test with a set containing a MEA with null role
		MessageEmailAddress nullRole = new MessageEmailAddressImpl();
		nullRole.setEmailAddress(testAddr1);
		meaSet = new HashSet();
		meaSet.add(nullRole);
		try {
			mail2.setMessageEmailAddresses(meaSet);
			Assert.fail("Expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}

		// test with a set containging object which isn't an MEA
		meaSet = new HashSet();
		meaSet.add("this isn't a MessageEmailAddress");
		try {
			mail2.setMessageEmailAddresses(meaSet);
			Assert.fail("Expected IllegalArgumentException");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}

}
