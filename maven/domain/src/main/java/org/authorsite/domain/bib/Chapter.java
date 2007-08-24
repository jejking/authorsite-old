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

import javax.persistence.Entity;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.authorsite.domain.AbstractHuman;

/**
 * Class representing a specific chapter in a single {@link Book}.
 * 
 * <p>It defines the following additional properties:</p>
 * <ul>
 * <li><code>book</code>: the book the chapter belongs to.</li>
 * <li><code>pages</code>: a string describing the pages of the book that the chapter is to be found in</li>.
 * <li><code>chapter</code>: a string describing the designation of the chapter (e.g 'VI', '2', etc).</li>
 * </ul>
 * 
 * @author jejking
 */
@Entity
public class Chapter extends AbstractAuthoredEditedWork implements Comparable<Chapter> {

    /**
     * Generated by Eclipse. 
     */
    private static final long serialVersionUID = 3191207264639407756L;

    private Book book;

    private String pages;

    private String chapter;

    /**
     * Default constructor.
     */
    public Chapter() {
	super();
    }

    /**
     * Gets book.
     * 
     * @return book
     */
    public Book getBook() {
	return this.book;
    }

    /**
     * Sets book. 
     * 
     * @param book should not be <code>null</code>
     */
    public void setBook(Book book) {
	this.book = book;
    }

    /**
     * Gets chapter designation.
     * 
     * @return chapter designation.
     */
    public String getChapter() {
	return this.chapter;
    }

    /**
     * Sets chapter designation.
     * 
     * @param chapter
     */
    public void setChapter(String chapter) {
	this.chapter = chapter;
    }

    /**
     * Gets pages numberings.
     * 
     * @return page numberings.
     */
    public String getPages() {
	return this.pages;
    }

    /**
     * Sets page numberings.
     * 
     * @param pages
     */
    public void setPages(String pages) {
	this.pages = pages;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (obj == this) {
	    return true;
	}
	if (obj instanceof Chapter) {
	    Chapter rhs = (Chapter) obj;
	    if (this.authors.size() == rhs.authors.size() && this.editors.size() == rhs.editors.size()) {
		return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).append(this.getWorkDates(),
			rhs.getWorkDates()).append(this.authors.toArray(), rhs.authors.toArray()).append(
			this.editors.toArray(), rhs.editors.toArray()).append(this.book, rhs.book).isEquals();
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(this.getTitle()).append(this.getWorkDates()).append(this.authors.toArray())
		.append(this.editors.toArray()).append(this.book).toHashCode();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	for (AbstractHuman author : this.authors) {
	    sb.append(author);
	    sb.append(" ");
	}
	sb.append(this.getTitle());
	sb.append(" (");
	sb.append(this.getWorkDates());
	sb.append(") ");
	sb.append("Eds: ");
	for (AbstractHuman editor : this.editors) {
	    sb.append(editor);
	    sb.append(" ");
	}
	sb.append(" in book: ");
	sb.append(this.book.toString());
	return sb.toString();
    }

    public int compareTo(Chapter other) {
	return new CompareToBuilder().append(this.getTitle(), other.getTitle()).append(this.getWorkDates(),
		other.getWorkDates()).append(this.authors.toArray(), other.authors.toArray()).append(
		this.editors.toArray(), other.editors.toArray()).append(this.book, other.book).toComparison();
    }

}
