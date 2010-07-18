/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package org.authorsite.simplemailarchive.domain;

import org.junit.Assert;
import org.junit.Test;

public class SimpleEmailAddressTest {

	@Test
	public void testRegularConstruction() {
		SimpleEmailAddress addr1 = new SimpleEmailAddress("foo@bar.com", "Foo Bar");
		Assert.assertEquals("foo@bar.com", addr1.getAddress());
		Assert.assertEquals("Foo Bar", addr1.getName());
		
		SimpleEmailAddress addr2 = new SimpleEmailAddress("foo@bar.com", null);
		Assert.assertEquals("foo@bar.com", addr2.getAddress());
		Assert.assertNull(addr2.getName());
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectNullAddress() {
		@SuppressWarnings("unused")
		SimpleEmailAddress addr1 = new SimpleEmailAddress(null, "Foo Bar");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectEmptyAddress() {
		@SuppressWarnings("unused")
		SimpleEmailAddress addr1 = new SimpleEmailAddress("   ", "Foo Bar");
	}
	
}
