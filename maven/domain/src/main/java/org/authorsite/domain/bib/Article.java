package org.authorsite.domain.bib;

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
public class Article extends AbstractAuthoredEditedWork implements Comparable {

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
        return issue;
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
        return journal;
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
        return pages;
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
        return volume;
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
        if ( obj == null ) {
            return false;
        }
        if ( obj == this ) {
            return true;
        }
        if ( obj instanceof Article ) {
            Article rhs = (Article) obj;
            if ( this.authors.size() == rhs.authors.size() ) {
                return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                          .append(this.getWorkDates(), rhs.getWorkDates())
                                          .append(this.authors.toArray(), rhs.authors.toArray())
                                          .append(this.journal, rhs.journal)
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
                                    .append(this.journal)
                                    .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (AbstractHuman author : authors ) {
            sb.append(author);
            sb.append(", ");
        }
        sb.append("'");
        sb.append(getTitle());
        sb.append("'(");
        sb.append(getWorkDates());
        sb.append("), in ");
        sb.append(journal);
        sb.append(", Volume:");
        sb.append(volume == null ? "Unspecified" : volume);
        sb.append(" Issue: ");
        sb.append(issue == null ? "Unspecified" : issue);
        sb.append(" pp: ");
        sb.append(pages);
        return sb.toString();
    }

    public int compareTo(Object o) {
        Article rhs = (Article) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.getWorkDates(), rhs.getWorkDates())
                                     .append(this.authors.toArray(), rhs.authors.toArray())
                                     .append(this.journal, rhs.journal)
                                     .toComparison();
    }

   

}
 