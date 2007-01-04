package org.authorsite.domain.bib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.authorsite.domain.Collective;
import org.authorsite.domain.Individual;


/**
 * Class describing a thesis or dissertation or equivalent piece
 * of written work submitted for the award of a university (or equivalent)
 * degree.
 * 
 * <p>The class defines the following additional properties:</p>
 * <ul>
 * <li><code>author</code>: the single individual {@link Individual} (student) author of the thesis.</li>
 * <li><code>awardingBody</code>: the single {@link Collective} (university) awarding the degree.</li>
 * <li><code>degree</code>: the degree for which the degree was awarded (PhD, D.Phil, M.A., etc)</li>
 * </ul>
 * 
 * @author jejking
 *
 */
public class Thesis extends AbstractWork implements Comparable {

    private Individual author;
    private Collective awardingBody;
    private String degree;
    
    /**
     * Default constructor.
     */
    public Thesis() {
	super();
    }

    /**
     * Gets author.
     * 
     * @return author
     */
    public Individual getAuthor() {
        return author;
    }

    /**
     * Sets author.
     * 
     * @param author should not be <code>null</code>
     */
    public void setAuthor(Individual author) {
        this.author = author;
    }

    /**
     * Get awarding body.
     * 
     * @return awardingBody
     */
    public Collective getAwardingBody() {
        return awardingBody;
    }

    /**
     * Sets awarding body.
     * 
     * @param awardingBody should not be <code>null</code>
     */
    public void setAwardingBody(Collective awardingBody) {
        this.awardingBody = awardingBody;
    }

    /**
     * Gets degree.
     * 
     * @return degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets degree.
     * 
     * @param degree should not be <code>null</code>
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Thesis ) {
            Thesis rhs = (Thesis) obj;
            return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                      .append(this.author, rhs.author)
                                      .append(this.getWorkDates(), rhs.getWorkDates())
                                      .append(this.degree, rhs.degree)
                                      .append(this.awardingBody, rhs.awardingBody)
                                      .isEquals();
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getTitle()).append(
                this.getAuthor()).append(this.getWorkDates()).append(this.degree).append(
                this.awardingBody).toHashCode();
    }

    @Override
    public String toString() {
        return (this.degree + " thesis by: " + this.getAuthor() + " awarded by " + this.awardingBody + " in " + this.getWorkDates());
    }

    public int compareTo(Object o) {
        Thesis rhs = (Thesis) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.author, rhs.author)
                                     .append(this.getWorkDates(), rhs.getWorkDates())
                                     .append(this.degree, rhs.degree)
                                     .append(this.awardingBody, rhs.awardingBody)
                                     .toComparison();
    }

}
