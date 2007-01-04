package org.authorsite.domain.bib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.authorsite.domain.AbstractHuman;

/**
 * Class representing a printed book.
 * 
 * <p>A book has the following additional properties:</p>
 * <ul>
 * <li><code>authors</code>: a Set of human authors, responsible for creating the content.</li>
 * <li><code>editors</code>: a Set of human editors, responsible for editing and or assembling the content for publication</li>
 * <li><code>publisher</code>: a single human, responsible for publishing the work.</li>.
 * <li><code>volume</code>: a String indicating which volume of a multi-volume set is being referred to.</li> 
 * </ul>
 * 
 * @author jejking
 *
 */
public class Book extends AbstractAuthoredEditedWork implements Comparable {

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
    public AbstractHuman getPublisher() {
        return publisher;
    }

    /**
     * Sets publisher.
     * 
     * @param publisher may be <code>null</code>
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
        return volume;
    }

    /**
     * Sets volume.
     * 
     * @param volume may be <code>null</code>
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Book ) {
            Book rhs = (Book) obj;
            if ( this.authors.size() == rhs.authors.size() && this.editors.size() == rhs.editors.size() ) {
                return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                          .append(this.getWorkDates(), rhs.getWorkDates())
                                          .append(this.authors.toArray(), rhs.authors.toArray())
                                          .append(this.editors.toArray(), rhs.editors.toArray())
                                          .append(this.publisher, rhs.publisher)
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
                                    .append(this.publisher)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractHuman author: this.authors) {
            sb.append(author);
            sb.append(" ");
        }
        sb.append(":");
        sb.append(this.getTitle());
        sb.append(" (");
        sb.append(this.getWorkDates());
        sb.append(") ");
        sb.append("Eds: ");
        for ( AbstractHuman editor : this.editors ) {
            sb.append(editor);
            sb.append(" ");
        }
        sb.append(this.publisher);
        return sb.toString();
    }

    public int compareTo(Object o) {
        Book rhs = (Book) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.getWorkDates(), rhs.getWorkDates())
                                     .append(this.authors.toArray(), rhs.authors.toArray())
                                     .append(this.editors.toArray(), rhs.editors.toArray())
                                     .append(this.publisher, rhs.publisher)
                                     .toComparison();
                                     
         
    }


}
