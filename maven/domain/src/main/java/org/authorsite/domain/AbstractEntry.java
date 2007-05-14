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
package org.authorsite.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Super class of all domain objects - simply provides a <code>long</code>
 * field for use as an identifier.
 * 
 * @author jejking
 */
@MappedSuperclass
public abstract class AbstractEntry implements Serializable {

    private long id;

    private int version;

    private Date createdAt;

    private Date updatedAt;

    private Individual createdBy;

    private Individual updatedBy;

    /**
     * Default constructor.
     */
    public AbstractEntry() {
        super();
    }

    /**
     * Gets id.
     * 
     * @return id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return this.id;
    }

    /**
     *Gets version.
     *
     * @return version
     */
    @Version
    public int getVersion() {
        return this.version;
    }

    /**
     * Sets the version for optimistic locking! Do not 
     * call this method explicitly!
     *
     * @param i the version
     */
    protected void setVersion(int i) {
        this.version = i;
    }

    /**
     * Sets id.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the createdAt
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getCreatedAt() {
        return this.createdAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return the createdBy
     */
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    public Individual getCreatedBy() {
        return this.createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Individual createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedAt
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return the updatedBy
     */
    @ManyToOne(optional = false, fetch=FetchType.LAZY)
    public Individual getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Individual updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((this.createdAt == null) ? 0 : this.createdAt.hashCode());
        result = PRIME * result + ((this.createdBy == null) ? 0 : this.createdBy.hashCode());
        result = PRIME * result + (int) (this.id ^ (this.id >>> 32));
        result = PRIME * result + ((this.updatedAt == null) ? 0 : this.updatedAt.hashCode());
        result = PRIME * result + ((this.updatedBy == null) ? 0 : this.updatedBy.hashCode());
        result = PRIME * result + this.version;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if ( ! ( obj instanceof AbstractEntry )) {
            return false;
        }
        final AbstractEntry other = (AbstractEntry) obj;
        if (this.createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!this.createdAt.equals(other.createdAt))
            return false;
        if (this.createdBy == null) {
            if (other.createdBy != null)
                return false;
        } else if (!this.createdBy.equals(other.createdBy))
            return false;
        if (this.id != other.id)
            return false;
        if (this.updatedAt == null) {
            if (other.updatedAt != null)
                return false;
        } else if (!this.updatedAt.equals(other.updatedAt))
            return false;
        if (this.updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        } else if (!this.updatedBy.equals(other.updatedBy))
            return false;
        if (this.version != other.version)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("id: " +id );
        sb.append(", version: " + this.version);
        if (this.createdBy != null)
        {
            sb.append(", created by: " + this.createdBy.getId());
        }
        
        sb.append(", created at: " + this.createdAt);
        if ( this.updatedBy != null )
        {
            sb.append(", updated by: " + this.updatedBy.getId());
        }
        sb.append(", updated at: " + this.updatedAt);
        return sb.toString();
    }
    
}
