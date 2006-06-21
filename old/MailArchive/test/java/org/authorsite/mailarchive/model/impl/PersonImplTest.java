/*
 * PersonImplTest.java, created on 17-Mar-2004 at 20:50:21
 * 
 * Copyright John King, 2004.
 *
 *  PersonImplTest.java is part of authorsite.org's MailArchive program.
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
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class PersonImplTest extends TestCase {

	/**
	 * Constructor for PersonImplTest.
	 * @param arg0
	 */
	public PersonImplTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(PersonImplTest.class);
	}

	final public void testSetGenderCode() {
		// only valid gender codes are 0, 1, 2, and 9 in this implementation
		// any other values should default to 0
		Person bob = new PersonImpl();
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(1);
		Assert.assertEquals(1, bob.getGenderCode());

		bob.setGenderCode(2); // oops, sex change for bob...
		Assert.assertEquals(2, bob.getGenderCode());

		bob.setGenderCode(9);
		Assert.assertEquals(9, bob.getGenderCode());

		bob.setGenderCode(-1);
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(0);
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(3);
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(4);
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(5);
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(8);
		Assert.assertEquals(0, bob.getGenderCode());

		bob.setGenderCode(10);
		Assert.assertEquals(0, bob.getGenderCode());
	}

	final public void testSetDateOfBirth() {
		Person bob = new PersonImpl();
		Calendar cal1971 = new GregorianCalendar(1971, Calendar.NOVEMBER, 10); // not that that's anyone's b'day ;-)
		Date date1971 = cal1971.getTime();
		
		Calendar cal1900 = new GregorianCalendar(1900, Calendar.JANUARY, 1);
		Date date1900 = cal1900.getTime();
		
		Calendar cal1945 = new GregorianCalendar(1945, Calendar.AUGUST, 14);
		Date date1945 = cal1945.getTime();
		
		bob.setDateOfDeath(date1945);
		bob.setDateOfBirth(date1900); // should be absolutely fine
		Assert.assertEquals(date1900, bob.getDateOfBirth());
		
		
		try {
			bob.setDateOfBirth(date1971); // bob born after bob died!
			Assert.fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
			
		}
	}

	final public void testSetDateOfDeath() {
		Person bob = new PersonImpl();
		Calendar cal1971 = new GregorianCalendar(1971, Calendar.NOVEMBER, 10);
		Date date1971 = cal1971.getTime();
		
		Calendar cal1900 = new GregorianCalendar(1900, Calendar.JANUARY, 1);
		Date date1900 = cal1900.getTime();
		
		Calendar cal1945 = new GregorianCalendar(1945, Calendar.AUGUST, 14);
		Date date1945 = cal1945.getTime();
		
		bob.setDateOfBirth(date1945);
		bob.setDateOfDeath(date1971);
		Assert.assertEquals(date1971, bob.getDateOfDeath());
		Assert.assertFalse(date1971 == bob.getDateOfDeath());
		
		try {
			bob.setDateOfDeath(date1900); // must be born before bob can die
			Assert.fail("expected illegal argument exception");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertTrue(true);
		}
	}

}
