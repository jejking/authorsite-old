package org.authorsite.domain.bib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Class describing a journal as a whole series of regular
 * publications. It does not describe individual issues or volumes
 * or a journal.
 * 
 * <p>It may also be used to describe newspapers.</p>. 
 * 
 * @author jejking
 */
public class Journal extends AbstractWork implements Comparable {

    /**
     * Default constructor.
     */
    public Journal() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Journal ) {
            Journal rhs = (Journal) obj;
            return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).isEquals();
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getTitle()).toHashCode();
    }

    public int compareTo(Object o) {
        Journal rhs = (Journal) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle()).toComparison();
    }
    
    @Override
    public String toString() {
        return this.getTitle();
    }

}
