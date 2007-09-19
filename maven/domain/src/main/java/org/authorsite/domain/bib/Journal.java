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
package org.authorsite.domain.bib;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.authorsite.domain.AbstractHuman;
import org.authorsite.domain.Individual;

/**
 * Class describing a journal as a whole series of regular
 * publications. It does not describe individual issues or volumes
 * or a journal.
 * 
 * <p>It may also be used to describe newspapers.</p>. 
 * 
 * @author jejking
 */
@Entity
public class Journal extends AbstractWork implements Comparable<Journal> {

    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = 6400922520162391883L;
    
    private AbstractHuman publisher;

    /**
     * Default constructor.
     */
    public Journal() {
	super();
    }

    /**
     * Creates instance with the given title.
     * 
     * @param title
     */
    public Journal(String title) {
	super.setTitle(title);
    }

    /**
     * Creates instance with given title and publisher.
     * 
     * @param title
     * @param publisher
     */
    public Journal(String title, AbstractHuman publisher) {
	this(title);
	this.setPublisher(publisher);
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (obj == this) {
	    return true;
	}
	if (obj instanceof Journal) {
	    Journal rhs = (Journal) obj;
	    return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).isEquals();
	} else {
	    return false;
	}
    }

    @Transient
    public AbstractHuman getPublisher() {
	if (this.publisher == null) {
	    Set<AbstractHuman> publishers = super.getWorkProducersOfType(WorkProducerType.PUBLISHER);
	    if (!publishers.isEmpty()) {
		this.publisher = publishers.iterator().next();
	    }
	}
        return publisher;
    }

    /**
     * Sets the publisher. If one is already set, this is cleared and
     * the new one set.
     * 
     * @param publisher
     */
    public void setPublisher(AbstractHuman publisher) {
	if (!super.getWorkProducersOfType(WorkProducerType.PUBLISHER).isEmpty()) 
	{
	    super.removeAllWorkProducersOfType(WorkProducerType.PUBLISHER);
	}
	super.addWorkProducer(WorkProducerType.PUBLISHER, publisher);
        this.publisher = publisher;
    }
    
    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(this.getTitle()).toHashCode();
    }

    public int compareTo(Journal journal) {
	return new CompareToBuilder().append(this.getTitle(), journal.getTitle()).toComparison();
    }

    @Override
    public String toString() {
	return "Journal: " + this.getTitle();
    }
    
    @Override
    protected boolean areProducersOk() {
        // one optional publisher (for now!)
	if (this.typeHumansMap.size() > 1) {
	    return false;
	}
	Set<AbstractHuman> publishers = super.getWorkProducersOfType(WorkProducerType.PUBLISHER);
	if (publishers.size() > 1) {
	    return false;
	}
	return true;
    }

}
