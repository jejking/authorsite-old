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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Entity
public class Thesis extends AbstractWork implements Comparable<Thesis> {

    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = -6310817720547175025L;

    private Author author;

    private AwardingBody awardingBody;

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
    @OneToOne(optional=false, fetch=FetchType.EAGER)
    public Author getAuthor() {
	return this.author;
    }

    /**
     * Sets author.
     * 
     * @param author should not be <code>null</code>
     */
    public void setAuthor(Author author) {
	this.author = author;
    }

    /**
     * Get awarding body.
     * 
     * @return awardingBody
     */
    @OneToOne(optional=false, fetch=FetchType.EAGER, cascade={CascadeType.ALL})
    public AwardingBody getAwardingBody() {
	return this.awardingBody;
    }

    /**
     * Sets awarding body.
     * 
     * @param awardingBody should not be <code>null</code>
     */
    public void setAwardingBody(AwardingBody awardingBody) {
	this.awardingBody = awardingBody;
    }

    /**
     * Gets degree.
     * 
     * @return degree
     */
    public String getDegree() {
	return this.degree;
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
	if (obj == null) {
	    return false;
	}
	if (obj == this) {
	    return true;
	}
	if (obj instanceof Thesis) {
	    Thesis rhs = (Thesis) obj;
	    return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).append(this.author, rhs.author).append(
		    this.getWorkDates(), rhs.getWorkDates()).append(this.degree, rhs.degree).append(this.awardingBody,
		    rhs.awardingBody).isEquals();
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(this.getTitle()).append(this.getAuthor()).append(this.getWorkDates())
		.append(this.degree).append(this.awardingBody).toHashCode();
    }

    @Override
    public String toString() {
	return (this.degree + " thesis by: " + this.getAuthor() + " awarded by " + this.awardingBody + " in " + this
		.getWorkDates());
    }

    public int compareTo(Thesis thesis) {
	return new CompareToBuilder().append(this.getTitle(), thesis.getTitle()).append(this.author, thesis.author)
		.append(this.getWorkDates(), thesis.getWorkDates()).append(this.degree, thesis.degree).append(
			this.awardingBody, thesis.awardingBody).toComparison();
    }

}
