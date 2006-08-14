package org.authorsite.email;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public final class EmailAddressingContainer {

	private final SortedSet<EmailAddressing> addressings = new TreeSet<EmailAddressing>();
	private EmailMessage message;
	
	public EmailMessage getMessage() {
		return message;
	}

	public void setMessage(EmailMessage message) {
		this.message = message;
	}

	public void add(EmailAddressing addressing) {
		assert addressing != null;
		addressings.add(addressing);
	}
	
	public Set<EmailAddressing> getAddressings() {
		return addressings;
	}
	
	public Set<EmailAddressing> getAddressingsByType(EmailAddressingType type) {
		HashSet<EmailAddressing> subset = new HashSet<EmailAddressing>();
		for ( EmailAddressing addressing : addressings ) {
			if ( addressing.getType().equals(type)) {
				subset.add(addressing);
			}
		}
		return subset;
	}

	

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(addressings.toArray()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EmailAddressingContainer other = (EmailAddressingContainer) obj;
		return new EqualsBuilder().append(this.addressings.toArray(), other.addressings.toArray()).isEquals();
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		for ( EmailAddressing addressing : addressings ) {
			buffer.append(addressing);
			buffer.append("\n");
		}
		return buffer.toString();
	}
	
	

}
