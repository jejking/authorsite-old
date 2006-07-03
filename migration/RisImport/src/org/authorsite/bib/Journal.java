package org.authorsite.bib;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Journal extends AbstractWork {

    public Journal(long id) {
        super(id);
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO works (id, created_at, updated_at, type, title) ");
        sb.append("VALUES ( ");
        sb.append(this.getId());
        sb.append(", NOW(), NOW(), 'Journal', ");
        sb.append("'" + super.escapeSingleApostrophes(this.getTitle()) + "'");
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
        if ( obj instanceof Journal ) {
            Journal rhs = (Journal) obj;
            return new EqualsBuilder().append(this.getTitle(), rhs.getTitle()).isEquals();
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getTitle()).toHashCode();
    }
    
    

}
