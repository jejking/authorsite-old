package org.authorsite.bib;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Article extends AbstractWork implements Comparable {

    private SortedSet<AbstractHuman> authors = new TreeSet<AbstractHuman>();
    private Journal journal;
    private String volume;
    private String issue;
    private String pages;
    
    public Article(long id) {
        super(id);
    }
    
    public Article() {
        super();
    }

    
    public Set<AbstractHuman> getAuthors() {
        return authors;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Journal getJournal() {
        return journal;
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
    
    public void addAuthor(AbstractHuman author) {
        this.authors.add(author);
    }
    
    public void addAuthors(Set<? extends AbstractHuman> authors) {
        this.authors.addAll(authors);
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, to_year, month, day, pages, volume, number) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Article', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "', ");
        sb.append(this.getYears().getYear());
        sb.append(", ");
        sb.append( this.getYears().getToYear() == Integer.MIN_VALUE ? "NULL" : this.getYears().getToYear() );
        sb.append(", ");
        sb.append( this.getYears().getMonth() == Integer.MIN_VALUE ? "NULL" : this.getYears().getMonth());
        sb.append(", ");
        sb.append( this.getYears().getDay() == Integer.MIN_VALUE ? "NULL" : this.getYears().getDay() );
        sb.append(", ");
        sb.append(pages == null || pages.length() == 0 ? "NULL" : "'" + super.escapeSingleApostrophes(this.getPages()) + "'");
        sb.append(", ");
        sb.append(volume == null ? "NULL" : "'" + super.escapeSingleApostrophes(this.getVolume()) + "'");
        sb.append(", ");
        sb.append(issue == null ? "NULL" : "'" + super.escapeSingleApostrophes(this.getIssue()) + "'");
        sb.append(");");
        if ( this.journal != null ) {
            sb.append("\n");
            sb.append(super.createSqlForWorkWorkRelationship(this.journal, "Containment"));
        }
        if ( !this.authors.isEmpty() ) {
            sb.append("\n");
            sb.append(super.createSqlForMultipleHumanWorkRelationship(this.getAuthors(), "Author"));
        }
        return sb.toString();
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
                                          .append(this.getYears(), rhs.getYears())
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
                                    .append(this.getYears())
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
        sb.append(getYears());
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
                                     .append(this.getYears(), rhs.getYears())
                                     .append(this.authors.toArray(), rhs.authors.toArray())
                                     .append(this.journal, rhs.journal)
                                     .toComparison();
    }

   

}
 