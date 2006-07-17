package org.authorsite.bib;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Thesis extends AbstractWork implements Comparable {

    private Individual author;
    private Collective awardingBody;
    private String degree;
    
    public Thesis(long id) {
        super(id);
    }
    
    public Thesis() {
    }

    public Individual getAuthor() {
        return author;
    }

    public void setAuthor(Individual author) {
        this.author = author;
    }

    public Collective getAwardingBody() {
        return awardingBody;
    }

    public void setAwardingBody(Collective awardingBody) {
        this.awardingBody = awardingBody;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title, year, toYear, degree ) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Thesis', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "', ");
        sb.append(this.getYears().getYear());
        sb.append(", ");
        sb.append(this.getYears().getToYear() == Integer.MIN_VALUE ? "NULL" : this.getYears().getToYear());
        sb.append(", '");
        sb.append( degree == null ? "Unknown" :  super.escapeSingleApostrophes(this.degree) );
        sb.append("');");
        
        if ( this.author != null ) {
            sb.append("\n");
            sb.append(super.createSqlForSingleHumanWorkRelationship(this.author, "Author"));
        }
        if ( this.awardingBody != null ) {
            sb.append("\n");
            sb.append(super.createSqlForSingleHumanWorkRelationship(this.awardingBody, "AwardingBody"));
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
        if ( obj instanceof Thesis ) {
            Thesis rhs = (Thesis) obj;
            return new EqualsBuilder().append(this.getTitle(), rhs.getTitle())
                                      .append(this.author, rhs.author)
                                      .append(this.getYears(), rhs.getYears())
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
                this.getAuthor()).append(this.getYears()).append(this.degree).append(
                this.awardingBody).toHashCode();
    }

    @Override
    public String toString() {
        return (this.degree + " thesis by: " + this.getAuthor() + " awarded by " + this.awardingBody + " in " + this.getYears());
    }

    public int compareTo(Object o) {
        Thesis rhs = (Thesis) o;
        return new CompareToBuilder().append(this.getTitle(), rhs.getTitle())
                                     .append(this.author, rhs.author)
                                     .append(this.getYears(), rhs.getYears())
                                     .append(this.degree, rhs.degree)
                                     .append(this.awardingBody, rhs.awardingBody)
                                     .toComparison();
    }

}
