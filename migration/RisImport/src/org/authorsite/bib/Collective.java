package org.authorsite.bib;

import javax.naming.ldap.HasControls;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class Collective extends AbstractHuman {

    private String place;
    
    public Collective(long id) {
        super(id);
    }

    
    public String getPlace() {
        return place;
    }

    
    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO humans ");
        sb.append("(id, created_at, updated_at, type, name, nameQualification, place ) ");
        sb.append("VALUES ");
        sb.append("( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Collective', ");
        if ( this.getName() == null ) {
            sb.append("'Unknown', ");
        }
        else {
            sb.append("'" + super.escapeSingleApostrophes(this.getName()) + "', ");
        }
        
        if ( this.getNameQualification() == null ) {
            sb.append("NULL, ");
        }
        else {
            sb.append("'" + super.escapeSingleApostrophes(this.getNameQualification()) + "', ");
        }
        
        if ( this.place != null ) {
            sb.append("'" + place + "'");
        }
        else {
            sb.append("NULL");
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
        if ( obj instanceof Collective ) {
            Collective rhs = (Collective) obj;
            return new EqualsBuilder().append(this.getName(), rhs.getName()).append(this.getPlace(), rhs.getPlace())
            .append(this.getNameQualification(), rhs.getNameQualification()).isEquals();
        }
        else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getName()).append(this.place).append(this.getNameQualification()).toHashCode();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        if ( place != null ) {
            sb.append(", ");
            sb.append( this.place );
        }
        if ( this.getNameQualification() != null ) {
            sb.append(" (");
            sb.append(this.getNameQualification());
            sb.append(")");
        }
        return sb.toString();
    }
    
    

}
