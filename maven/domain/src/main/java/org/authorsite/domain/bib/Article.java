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
 * Class describing an article published in a {@link Journal}.
 *
 * <p>The following additional properties are defined:</p>
 * 
 * <ul>
 * <li><code>Journal</code>: the journal the article is published in.</li>
 * <li><code>Volume</code>: string indicating the volume of the journal the article is published in.</li>
 * <li><code>issue</code>: string indicating the issue of the journal the article is published in.</li>
 * <li><code>pages</code>: string designating the pages of the issue/volume of the journal in which the article is printed.</li>.
 * </ul>
 * 
 * @author jejking
 */
@Entity
public class Article extends AbstractAuthoredEditedWork implements Comparable<Article> {

    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = -3678362334392909452L;

    private Journal journal;

    private String volume;

    private String issue;

    private String pages;

    /**
     * Default constructor. 
     */
    public Article() {
	super();
    }

    /**
     * Gets issue.
     * 
     * @return issue.
     */
    public String getIssue() {
	return this.issue;
    }

    /**
     * Sets issue.
     * 
     * @param issue
     */
    public void setIssue(String issue) {
	this.issue = issue;
    }

    /**
     * Gets journal.
     * 
     * @return journal.
     */
    public Journal getJournal() {
	return this.journal;
    }

    /**
     * Sets journal.
     * 
     * @param journal should not be <code>null</code>.
     */
    public void setJournal(Journal journal) {
	this.journal = journal;
    }

    /**
     * Gets pages.
     * 
     * @return pages
     */
    public String getPages() {
	return this.pages;
    }

    /**
     * Sets pages.
     * 
     * @param pages should not be <code>null</code>-
     */
    public void setPages(String pages) {
	this.pages = pages;
    }

    /**
     * Gets volume.
     * 
     * @return volume
     */
    public String getVolume() {
	return this.volume;
    }

    /**
     * Sets volume.
     * 
     * @param volume should not be <code>null</code>.
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
	if (obj instanceof Article) {
	    Article rhs = (Article) obj;
	    if (this.authors.size() == rhs.authors.size()) {
		return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).append(this.getWorkDates(),
			rhs.getWorkDates()).append(this.authors.toArray(), rhs.authors.toArray()).append(this.journal,
			rhs.journal).isEquals();
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
		.append(this.journal).toHashCode();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	for (AbstractHuman author : this.authors) {
	    sb.append(author);
	    sb.append(", ");
	}
	sb.append("'");
	sb.append(getTitle());
	sb.append("'(");
	sb.append(getWorkDates());
	sb.append("), in ");
	sb.append(this.journal);
	sb.append(", Volume:");
	sb.append(this.volume == null ? "Unspecified" : this.volume);
	sb.append(" Issue: ");
	sb.append(this.issue == null ? "Unspecified" : this.issue);
	sb.append(" pp: ");
	sb.append(this.pages);
	return sb.toString();
    }

    public int compareTo(Article rhs) {
	return new CompareToBuilder().append(this.getTitle(), rhs.getTitle()).append(this.getWorkDates(),
		rhs.getWorkDates()).append(this.authors.toArray(), rhs.authors.toArray()).append(this.journal,
		rhs.journal).toComparison();
    }

}
