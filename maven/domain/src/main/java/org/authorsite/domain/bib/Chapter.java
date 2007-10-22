/**
 * This file is part of the getAuthors()ite application.
 *
 * The getAuthors()ite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The getAuthors()ite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the getAuthors()ite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */
package org.authorsite.domain.bib;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang.builder.CompareToBuilder;
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
@NamedQueries( {
    @NamedQuery(name = "ChapterCount", query = "select count(ch) from Chapter ch"),
    @NamedQuery(name = "ChaptersByTitle", query = "select ch from Chapter ch where ch.title = :title"),
    @NamedQuery(name = "ChaptersByTitleWildcard", query = "select ch from Chapter ch where ch.title like :title"),
        @NamedQuery(name= "ChaptersWithAuthor", query="select ch from Chapter ch, " +
                                                                   "IN (ch.workProducers) wp " +
                                                                   "WHERE " +
                                                                   "wp.abstractHuman = :author " +
                                                                   "AND " +
                                                                   "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR "),
       @NamedQuery(name= "ChaptersWithEditor", query="select ch from Chapter ch, " +
                                                                   "IN (ch.workProducers) wp " +
                                                                   "WHERE " +
                                                                   "wp.abstractHuman = :editor " +
                                                                   "AND " +
                                                                   "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR "),
       @NamedQuery(name= "ChaptersWithAuthorOrEditor", query="select ch from Chapter ch, " +
                                                                   "IN (ch.workProducers) wp " +
                                                                   "WHERE " +
                                                                   "wp.abstractHuman = :human " +
                                                                   "AND " +
                                                                   "(wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR " +
                                                                   "OR " +
                                                                   "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR ) "),
        @NamedQuery(name = "AllChapters", query = "select ch from Chapter ch order by ch.id asc"),
        @NamedQuery(name = "ChaptersBeforeDate", query="select ch from Chapter ch where ch.workDates.date < :date"),
        @NamedQuery(name = "ChaptersAfterDate", query="select ch from Chapter ch " +
                                                            "where " +
                                                            "ch.workDates.date > :date or ch.workDates.toDate > :date"),
        @NamedQuery(name = "ChaptersBetweenDates", query = "select ch from Chapter ch " +
                                                                  "where " +
                                                                  "(ch.workDates.date >= :startDate OR ch.workDates.toDate >= :startDate) " +
                                                                  "AND " +
                                                                  "(ch.workDates.date <= :endDate OR ch.workDates.toDate <= :endDate)"),
        @NamedQuery(name = "ChaptersInBook", query = "select ch from Chapter ch where ch.book = :book order by ch.id asc")}
)
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
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
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
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Chapter: " );
	sb.append(super.toString());
	sb.append(", in book: ");
	sb.append(this.book.toString());
	sb.append(", pp: ");
	sb.append(this.pages);
	sb.append(", ch. ");
	sb.append(this.chapter);
	return sb.toString();
    }

    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = super.hashCode();
	result = PRIME * result + ((this.book == null) ? 0 : this.book.hashCode());
	result = PRIME * result + ((this.chapter == null) ? 0 : this.chapter.hashCode());
	result = PRIME * result + ((this.pages == null) ? 0 : this.pages.hashCode());
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
	if (! (obj instanceof Chapter))
	    return false;
	final Chapter other = (Chapter) obj;
	if (this.book == null) {
	    if (other.book != null)
		return false;
	} else if (!this.book.equals(other.book))
	    return false;
	if (this.chapter == null) {
	    if (other.chapter != null)
		return false;
	} else if (!this.chapter.equals(other.chapter))
	    return false;
	if (this.pages == null) {
	    if (other.pages != null)
		return false;
	} else if (!this.pages.equals(other.pages))
	    return false;
	return true;
    }

    public int compareTo(Chapter other) {
	return new CompareToBuilder().append(this.getTitle(), other.getTitle()).append(this.getWorkDates(),
		other.getWorkDates()).append(this.getAuthors().toArray(), other.getAuthors().toArray()).append(
		this.getEditors().toArray(), other.getEditors().toArray()).append(this.book, other.book).toComparison();
    }
    
    @Override
    protected boolean areProducersOk() {
        // may have 0-n author(may be anonymous!)
	
	// may have 0-n editors
	
	// no other production rels.
	for (WorkProducerType workProducerType : WorkProducerType.values()) {
	    if ( workProducerType.equals(WorkProducerType.AUTHOR)
		    || workProducerType.equals(WorkProducerType.EDITOR)) {
		continue;
	    }
	    Set<AbstractHuman> humans = this.typeHumansMap.get(workProducerType);
	    if (humans == null || humans.isEmpty()) {
		continue;
	    }
	    else {
		return false;
	    }
	}
	return true;
    }

}
