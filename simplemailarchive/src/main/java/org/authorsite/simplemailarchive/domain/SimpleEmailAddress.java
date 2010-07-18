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

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

/**
 * Simple representation of an email address.
 *
 * @author jejking
 */
@Embeddable
public class SimpleEmailAddress {

	@Column(nullable = false, name = "email_address")
	private String address;
	@Column(nullable = true, name = "email_name")
	private String name;
	
	/**
	 * For JPA.
	 */
	@SuppressWarnings("unused")
	private SimpleEmailAddress() {
		super();
	}
	
	
	/**
	 * Constructor.
	 * @param address may not be <code>null</code> and must be of length > 0 when trimmed.
	 * @param name may be <code>null</code>, may be empty string
	 * @throws IllegalArgumentException if constraints not met.
	 */
	public SimpleEmailAddress(String address, String name) {
		super();
		Assert.isTrue(address != null && address.trim().length() > 0);
		this.address = address;
		this.name = name;
	}



	public String getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimpleEmailAddress))
			return false;
		SimpleEmailAddress other = (SimpleEmailAddress) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "SimpleEmailAddress [address=" + address + ", name=" + name
				+ "]";
	}
	
	
}
