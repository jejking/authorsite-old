/*
 * EmailAddressImplTest.java, created on 14-Mar-2004 at 22:29:54
 * 
 * Copyright John King, 2004.
 *
 *  EmailAddressImplTest.java is part of authorsite.org's MailArchive program.
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


import junit.framework.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class EmailAddressImplTest extends TestCase {

	/**
	 * Constructor for EmailAddressImplTest.
	 * @param arg0
	 */
	public EmailAddressImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(EmailAddressImplTest.class);
	}

	public void testEquals() {
		// equality test is on the address only, person info and person are irrelevant
		EmailAddressImpl one = new EmailAddressImpl();
		one.setAddress("example@example.com");
		one.setPersonalName("Mr Example");

		EmailAddressImpl two = new EmailAddressImpl();
		two.setAddress("example@example.com");
		two.setPersonalName("Not Mr Example");

		// one should be equal to two
		Assert.assertTrue(one.equals(two));

		// try with identical names, but different addresses
		EmailAddressImpl three = new EmailAddressImpl();
		three.setAddress("three@example.com");
		three.setPersonalName("Mr Example");
		EmailAddressImpl four = new EmailAddressImpl();
		four.setAddress("four@example.com");
		four.setPersonalName("Mr Example");

		// three should not be equal to four
		Assert.assertFalse(three.equals(four));
	}

	public void testHashCode() {
		// hashCode is linked to Address String
		EmailAddressImpl one = new EmailAddressImpl();
		one.setAddress("one@example.com");

		EmailAddressImpl two = new EmailAddressImpl();
		StringBuffer sb = new StringBuffer();
		sb.append("one");
		sb.append("@example.com");
		two.setAddress(sb.toString());

		Assert.assertEquals(one.hashCode(), two.hashCode());

		// hashCodes of different addresses should be different
		EmailAddressImpl three = new EmailAddressImpl();
		three.setAddress("three@example.com");

		Assert.assertFalse(one.hashCode() == three.hashCode());
	}

	public void testSetAddress() {
		EmailAddressImpl addr = new EmailAddressImpl();
		try {
			addr.setAddress("jejking@sourceforge.net"); // should be ok
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("an.example@sourceforge.net"); // should be ok
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("example@localhost"); // should be ok, but no domain, not internet routable
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("example@host.subdomain.domain"); // should be ok, but no domain, not internet routable
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("example_with_underscores@host.domain"); // should be ok, but no domain, not internet routable
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("example-with-hyphen@host.domain"); // should be ok, but no domain, not internet routable
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("CAPITALS@localhost.domain"); // should be ok, but no domain, not internet routable
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("user@domain.trailing.dot.");
			Assert.fail("expected failure");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
		try {
			addr.setAddress("user@domain-with-hyphen.com");
			Assert.assertTrue(true);
		}
		catch (IllegalArgumentException iae) {
			Assert.fail("iae with valid email pattern");
		}
		try {
			addr.setAddress("user@domain_with_underscore.com");
			Assert.fail("expected failure");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
		try {
			addr.setAddress("user@domain..with..toomanydots.com");
			Assert.fail("expected failure");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
		try {
			addr.setAddress("user"); // without at or domain
			Assert.fail("expected failure");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}


}