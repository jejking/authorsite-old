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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.CompareToBuilder;

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
@NamedQueries( {
        @NamedQuery(name = "BookCount", query = "select count(b) from Book b"),
        @NamedQuery(name = "BooksByTitle", query = "select b from Book b where b.title = :title"),
        @NamedQuery(name = "BooksByTitleWildcard", query = "select b from Book b where b.title like :title"),

        @NamedQuery(name = "BooksWithAuthor", query = "select b from Book b, " + "IN (b.workProducers) wp " + "WHERE "
                + "wp.abstractHuman = :author " + "AND "
                + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR "),
        @NamedQuery(name = "BooksWithEditor", query = "select b from Book b, " + "IN (b.workProducers) wp " + "WHERE "
                + "wp.abstractHuman = :editor " + "AND "
                + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR "),
        @NamedQuery(name = "BooksWithAuthorOrEditor", query = "select b from Book b, " + "IN (b.workProducers) wp "
                + "WHERE " + "wp.abstractHuman = :human " + "AND "
                + "(wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR " + "OR "
                + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR ) "),
        @NamedQuery(name = "BooksWithPublisher", query = "select b from Book b, " + "IN (b.workProducers) wp "
                + "WHERE " + "wp.abstractHuman = :publisher " + "AND "
                + "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.PUBLISHER "),
        @NamedQuery(name = "AllBooks", query = "select b from Book b order by b.id asc"),
        @NamedQuery(name = "BooksBeforeDate", query = "select b from Book b where b.workDates.date < :date"),
        @NamedQuery(name = "BooksAfterDate", query = "select b from Book b " + "where "
                + "b.workDates.date > :date or b.workDates.toDate > :date"),
        @NamedQuery(name = "BooksBetweenDates", query = "select b from Book b " + "where "
                + "(b.workDates.date >= :startDate OR b.workDates.toDate >= :startDate) " + "AND "
                + "(b.workDates.date <= :endDate OR b.workDates.toDate <= :endDate)") })
public class Book extends AbstractAuthoredEditedPublishedWork implements Comparable<Book> {

    private static final long serialVersionUID = 1L;

    private String volume;

    /**
     * Default constructor.
     */
    public Book() {
        super();
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
        sb.append(this.getPublisher());
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((this.getPublisher() == null) ? 0 : this.getPublisher().hashCode());
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
        if (this.getPublisher() == null) {
            if (other.getPublisher() != null)
                return false;
        } else if (!this.getPublisher().equals(other.getPublisher()))
            return false;
        if (this.volume == null) {
            if (other.volume != null)
                return false;
        } else if (!this.volume.equals(other.volume))
            return false;
        return true;
    }

    public int compareTo(Book book) {
        return new CompareToBuilder().append(this.getTitle(), book.getTitle()).append(this.getWorkDates(),
                book.getWorkDates()).append(this.getAuthors().toArray(), book.getAuthors().toArray()).append(
                this.getEditors().toArray(), book.getEditors().toArray()).append(this.getPublisher(),
                book.getPublisher()).toComparison();

    }

}
