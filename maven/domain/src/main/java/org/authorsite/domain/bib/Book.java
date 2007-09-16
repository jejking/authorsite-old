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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.authorsite.domain.AbstractHuman;

/**
 * Class representing a printed book.
 * 
 * <p>
 * A book has the following additional properties:
 * </p>
 * <ul>
 * <li><code>authors</code>: a Set of human authors, responsible for
 * creating the content.</li>
 * <li><code>editors</code>: a Set of human editors, responsible for editing
 * and or assembling the content for publication</li>
 * <li><code>publisher</code>: a single human, responsible for publishing
 * the work.</li>.
 * <li><code>volume</code>: a String indicating which volume of a
 * multi-volume set is being referred to.</li>
 * </ul>
 * 
 * @author jejking
 * 
 */
@Entity
public class Book extends AbstractAuthoredEditedWork implements Comparable<Book> {

    private static final long serialVersionUID = 1L;

    private AbstractHuman publisher;

    private String volume;

    /**
     * Default constructor.
     */
    public Book() {
	super();
    }

    /**
         * Gets publisher.
         * 
         * @return publisher, may be <code>null</code>.
         */
    @Transient
    public AbstractHuman getPublisher() {
	return this.publisher;
    }

    /**
         * Sets publisher.
         * 
         * @param publisher
         *                may be <code>null</code>
         */
    public void setPublisher(AbstractHuman publisher) {
	this.publisher = publisher;
    }

    /**
         * Gets volume.
         * 
         * @return volume.
         */
    public String getVolume() {
	return this.volume;
    }

    /**
         * Sets volume.
         * 
         * @param volume
         *                may be <code>null</code>
         */
    public void setVolume(String volume) {
	this.volume = volume;
    }

    
    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(super.toString());
	sb.append(", Publisher: ");
	sb.append(this.publisher);
	return sb.toString();
    }

    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = super.hashCode();
	result = PRIME * result + ((this.publisher == null) ? 0 : this.publisher.hashCode());
	result = PRIME * result + ((this.volume == null) ? 0 : this.volume.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final Book other = (Book) obj;
	if (this.publisher == null) {
	    if (other.publisher != null)
		return false;
	} else if (!this.publisher.equals(other.publisher))
	    return false;
	if (this.volume == null) {
	    if (other.volume != null)
		return false;
	} else if (!this.volume.equals(other.volume))
	    return false;
	return true;
    }

    public int compareTo(Book book) {
	return new CompareToBuilder().append(this.getTitle(), book.getTitle())
		.append(this.getWorkDates(), book.getWorkDates()).append(
			this.getAuthors().toArray(),
			book.getAuthors().toArray()).append(
			this.getEditors().toArray(),
			book.getEditors().toArray()).append(
			this.getPublisher(), book.getPublisher())
		.toComparison();

    }
    
    @Override
    protected boolean areProducersOk() {
        
	// 0-n authors
	
	// 0-n editors
	
	// 0-1 publisher
	
	for ( WorkProducerType workProducerType : WorkProducerType.values()) {
	    if (workProducerType.equals(WorkProducerType.AUTHOR) 
		    || workProducerType.equals(WorkProducerType.EDITOR)) {
		continue;
	    }
	    Set<AbstractHuman> humans = this.getWorkProducersOfType(workProducerType);
	    if ( workProducerType.equals(WorkProducerType.PUBLISHER)) {
		if (humans.size() > 1) {
		    return false;
		}
	    }
	    else {
		if ( !humans.isEmpty()) {
		    return false; // not editor, not author, not publisher. Must be wrong.
		}
	    }
	}
	return true;
    }

}
