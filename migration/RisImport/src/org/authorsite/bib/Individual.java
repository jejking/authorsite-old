package org.authorsite.bib;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Individual extends AbstractHuman {

    private String givenNames;
    
    public Individual(long id) {
        super(id);
    }

    
    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    @Override
    public String toSql() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO HUMANS ");
        sb.append("(id, created_at, updated_at, type, name, givenNames, nameQualification ) ");
        sb.append("VALUES ");
        sb.append("( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Individual', ");
        if ( this.getName() == null ) {
            sb.append("'Unknown', ");
        }
        else {
            sb.append("'" + super.escapeSingleApostrophes(this.getName()) + "', ");
        }
        if ( this.givenNames == null) {
            sb.append("NULL, ");
        }
        else {
            sb.append( "'" + super.escapeSingleApostrophes(this.givenNames) + "', ");
        }
        if ( this.getNameQualification() == null ) {
            sb.append("NULL");
        }
        else {
            sb.append("'" + super.escapeSingleApostrophes(this.getNameQualification()) + "'");
        }
        sb.append(");");
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
        if ( obj instanceof Individual ) {
            Individual rhs = (Individual) obj;
            return new EqualsBuilder().append( this.getName(), rhs.getName()).append( this.givenNames, rhs.givenNames)
            .append( this.getNameQualification(), rhs.getNameQualification() ).isEquals();
        }
        else {
            return false;
        }
        
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getName()).append(
                this.givenNames).append(this.getNameQualification()).toHashCode();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        if ( this.givenNames != null ) {
            sb.append(", ");
            sb.append(this.givenNames);
        }
        if ( this.getNameQualification() != null ) {
            sb.append( ": ");
            sb.append( this.getNameQualification() );
        }
        return sb.toString();
    }
    
    

}
