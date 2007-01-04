package org.authorsite.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


/**
 * Abstract entry superclass for individuals and collectives,
 * grouping some common properties.
 * 
 * @author jejking
 *
 */
@Entity()
@Table(name="Human")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class AbstractHuman extends AbstractEntry {

    private String name;
    private String nameQualification;
    
    /**
     * Default constructor.
     */
    public AbstractHuman() {
        super();
    }
    
    /**
     * Constructs abstract human with name set
     * to that supplied.
     *
     * @param name
     */
    public AbstractHuman(String name) {
        this.setName(name);
    }

    /**
     * Returns name.
     * 
     * @return name, if set. If <code>null</code> returns "Unknown".
     */
     public String getName() {
        if ( name != null ) {
            return name;
        }
        else {
            return "Unknown";
        }
    }

    
    /**
     * Sets name. This may not be persisted as <code>null</code>.
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * Gets name qualification. This is additional data about
     * the name. For individuals, this might be "Jnr", "III".
     * For collectives, it may indicate the type of company -
     * "Ltd", "Pty", "GmbH", etc.
     * 
     * @return name qualification. May be <code>null</code>.
     */
    public String getNameQualification() {
        return nameQualification;
    }

    
    /**
     * Sets name qualification.
     * 
     * @param nameQualification New name qualification, may be <code>null</code>.
     */
    public void setNameQualification(String nameQualification) {
        this.nameQualification = nameQualification;
    }

        
    
}
