package org.authorsite.domain.email;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A class holding multiple {@link AbstractEmailPart} instances.
 * 
 * <p>This class is our equivalent of the multipart MIME message 
 * classes in Java Mail. It can thus be used to construct an email
 * message in textual form with one or more attachments. It could
 * be used to construct different versions of the same mail in 
 * different formats - plain text or HTML.</p>
 * 
 * <p>Because the class itself extends {@link AbstractEmailPart},
 * it can be used to nest instances within one another and thus create
 * trees of containers if required.</p> 
 * 
 * @author jejking
 *
 */
public class MessagePartContainer extends AbstractEmailPart {

    private List<AbstractEmailPart> children = new ArrayList<AbstractEmailPart>();

    /**
     * Default constructor.
     */
    public MessagePartContainer() {
	super();
    }

    /**
     * Adds part.
     * 
     * @param part may not be <code>null</code>.
     * @throws IllegalArgumentException if param is <code>null</code>.
     */
    public void addChildPart(AbstractEmailPart part) {
	if ( part == null ) {
	    throw new IllegalArgumentException("part parameter cannot be null");
	}
	if (!children.contains(part)) {
	    children.add(part);
	}
    }
    
    /**
     * Gets children.
     * 
     * @return children.
     */
    public List<AbstractEmailPart> getChildren() {
	return children;
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
	return new EqualsBuilder().append(this.children.toArray(),
		other.children.toArray()).isEquals();
    }

    @Override
    public String toString() {
	StringBuilder buffer = new StringBuilder();
	for (AbstractEmailPart part : children) {
	    buffer.append(part);
	}
	return buffer.toString();
    }

    
}
