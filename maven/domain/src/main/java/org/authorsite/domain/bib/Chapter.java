package org.authorsite.domain.bib;

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
public class Chapter extends AbstractAuthoredEditedWork implements Comparable {

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
        return book;
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
        return chapter;
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
        return pages;
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
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Chapter ) {
            Chapter rhs = (Chapter) obj;
            if ( this.authors.size() == rhs.authors.size() && this.editors.size() == rhs.editors.size() ) {
                return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                          .append(this.getWorkDates(), rhs.getWorkDates())
                                          .append(this.authors.toArray(), rhs.authors.toArray())
                                          .append(this.editors.toArray(), rhs.editors.toArray())
                                          .append(this.book, rhs.book)
                                          .isEquals();
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getTitle())
                                     .append(this.getWorkDates())
                                     .append(this.authors.toArray())
                                     .append(this.editors.toArray())
                                     .append(this.book)
                                     .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for ( AbstractHuman author : authors ) {
            sb.append(author);
            sb.append(" ");
        }
        sb.append(this.getTitle());
        sb.append(" (");
        sb.append(this.getWorkDates());
        sb.append(") ");
        sb.append("Eds: ");
        for (AbstractHuman editor : editors ) {
            sb.append(editor);
            sb.append(" ");
        }
        sb.append(" in book: ");
        sb.append(book.toString());
        return sb.toString();
    }

    public int compareTo(Object o) {
        Chapter rhs = (Chapter) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.getWorkDates(), rhs.getWorkDates())
                                     .append(this.authors.toArray(), rhs.authors.toArray())
                                     .append(this.editors.toArray(), rhs.editors.toArray())
                                     .append(this.book, rhs.book)
                                     .toComparison();
    }

    
    
    

}
