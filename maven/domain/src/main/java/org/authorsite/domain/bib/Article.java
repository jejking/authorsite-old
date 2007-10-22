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

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

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
@NamedQueries( {
    @NamedQuery(name = "ArticleCount", query = "select count(a) from Article a"),
    @NamedQuery(name = "ArticlesByTitle", query = "select a from Article a where a.title = :title"),
    @NamedQuery(name = "ArticlesByTitleWildcard", query = "select a from Article a where a.title like :title"),

        @NamedQuery(name= "ArticlesWithAuthor", query="select a from Article a, " +
                                                                   "IN (a.workProducers) wp " +
                                                                   "WHERE " +
                                                                   "wp.abstractHuman = :author " +
                                                                   "AND " +
                                                                   "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR "),
       @NamedQuery(name= "ArticlesWithEditor", query="select a from Article a, " +
                                                                   "IN (a.workProducers) wp " +
                                                                   "WHERE " +
                                                                   "wp.abstractHuman = :editor " +
                                                                   "AND " +
                                                                   "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR "),
       @NamedQuery(name= "ArticlesWithAuthorOrEditor", query="select a from Article a, " +
                                                                   "IN (a.workProducers) wp " +
                                                                   "WHERE " +
                                                                   "wp.abstractHuman = :human " +
                                                                   "AND " +
                                                                   "(wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.AUTHOR " +
                                                                   "OR " +
                                                                   "wp.workProducerType = org.authorsite.domain.bib.WorkProducerType.EDITOR ) "),
        @NamedQuery(name = "AllArticles", query = "select a from Article a order by a.id asc"),
        @NamedQuery(name = "ArticlesBeforeDate", query="select a from Article a where a.workDates.date < :date"),
        @NamedQuery(name = "ArticlesAfterDate", query="select a from Article a " +
                                                            "where " +
                                                            "a.workDates.date > :date or a.workDates.toDate > :date"),
        @NamedQuery(name = "ArticlesBetweenDates", query = "select a from Article a " +
                                                                  "where " +
                                                                  "(a.workDates.date >= :startDate OR a.workDates.toDate >= :startDate) " +
                                                                  "AND " +
                                                                  "(a.workDates.date <= :endDate OR a.workDates.toDate <= :endDate)"),
        @NamedQuery(name = "ArticlesInJournal", query = "select a from Article a where a.journal = :journal order by a.id asc")}
)
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
    @ManyToOne(optional=false, fetch=FetchType.EAGER)
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

    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = super.hashCode();
	result = PRIME * result + ((this.issue == null) ? 0 : this.issue.hashCode());
	result = PRIME * result + ((this.journal == null) ? 0 : this.journal.hashCode());
	result = PRIME * result + ((this.pages == null) ? 0 : this.pages.hashCode());
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
	final Article other = (Article) obj;
	if (this.issue == null) {
	    if (other.issue != null)
		return false;
	} else if (!this.issue.equals(other.issue))
	    return false;
	if (this.journal == null) {
	    if (other.journal != null)
		return false;
	} else if (!this.journal.equals(other.journal))
	    return false;
	if (this.pages == null) {
	    if (other.pages != null)
		return false;
	} else if (!this.pages.equals(other.pages))
	    return false;
	if (this.volume == null) {
	    if (other.volume != null)
		return false;
	} else if (!this.volume.equals(other.volume))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("Article:" );
	sb.append(super.toString());
	sb.append(" in ");
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
		rhs.getWorkDates()).append(this.getAuthors().toArray(), rhs.getAuthors().toArray()).append(this.journal,
		rhs.journal).toComparison();
    }
    
    @Override
    protected boolean areProducersOk() {
        
	// 0-n authors
	
	// 0-n editors
	
	for (WorkProducerType workProducerType : WorkProducerType.values()) {
	    if (workProducerType.equals(WorkProducerType.AUTHOR) 
		|| workProducerType.equals(WorkProducerType.EDITOR)) {
		continue;
	    }
	    Set<AbstractHuman> humans = this.getWorkProducersOfType(workProducerType);
	    if (!humans.isEmpty()) {
		return false;
	    }
	}
	return true;
    }

}
