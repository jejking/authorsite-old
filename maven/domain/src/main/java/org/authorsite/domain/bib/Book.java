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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    /**
         * Generated by Eclipse.
         */
    private static final long serialVersionUID = -1294714725294860372L;

    private Publisher publisher;

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
    @OneToOne(optional=true, fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    public Publisher getPublisher() {
	return this.publisher;
    }

    /**
         * Sets publisher.
         * 
         * @param publisher
         *                may be <code>null</code>
         */
    public void setPublisher(Publisher publisher) {
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
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (obj == this) {
	    return true;
	}
	if (obj instanceof Book) {
	    Book rhs = (Book) obj;
	    if (this.getAuthors().size() == rhs.getAuthors().size() && this.getEditors().size() == rhs.getEditors().size()) {
		return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).append(this.getWorkDates(),
			rhs.getWorkDates()).append(this.getAuthors().toArray(), rhs.getAuthors().toArray()).append(
			this.getEditors().toArray(), rhs.getEditors().toArray()).append(this.publisher, rhs.publisher).isEquals();
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(this.getTitle()).append(this.getWorkDates()).append(this.getAuthors().toArray())
		.append(this.getEditors().toArray()).append(this.publisher).toHashCode();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	for (Author author : this.getAuthors()) {
	    sb.append(author);
	    sb.append(" ");
	}
	sb.append(":");
	sb.append(this.getTitle());
	sb.append(" (");
	sb.append(this.getWorkDates());
	sb.append(") ");
	sb.append("Eds: ");
	for (Editor editor : this.getEditors()) {
	    sb.append(editor);
	    sb.append(" ");
	}
	sb.append(this.publisher);
	return sb.toString();
    }

    public int compareTo(Book book) {
	return new CompareToBuilder().append(this.getTitle(), book.getTitle()).append(this.getWorkDates(),
		book.getWorkDates()).append(this.getAuthors().toArray(), book.getAuthors().toArray()).append(
		this.getEditors().toArray(), book.getEditors().toArray()).append(this.publisher, book.publisher).toComparison();

    }

}
