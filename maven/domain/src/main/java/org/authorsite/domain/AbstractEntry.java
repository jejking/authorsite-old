package org.authorsite.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
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
    public Individual getUpdatedBy() {
        return this.updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Individual updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    

}
