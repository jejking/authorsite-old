package org.authorsite.email;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class MessagePartContainer extends AbstractEmailPart {

	private List<AbstractEmailPart> children = new ArrayList<AbstractEmailPart>();
	
	public MessagePartContainer() {
		super();
	}
	
	public void addChildPart(AbstractEmailPart part) {
		assert part != null;
		if ( ! children.contains(part)) {
			children.add(part);
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(children.toArray()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final MessagePartContainer other = (MessagePartContainer) obj;
		return new EqualsBuilder().append(this.children.toArray(), other.children.toArray()).isEquals();
	}

	
}
