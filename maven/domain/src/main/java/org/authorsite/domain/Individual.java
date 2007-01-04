package org.authorsite.domain;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Class representing an individual human being, derived from
 * {@link AbstractHuman}. The class defines the additional property
 * <code>GivenNames</code> for first names.
 * 
 * <p>The class is relatively simplistic in its modelling - it does
 * not take account of the fact that names may change over the course
 * of someone's life. A further limitation is that it does not
 * capture birth or death events.</p>.
 * 
 * @author jejking
 *
 */
@Entity()
@NamedQueries(
    {
    @NamedQuery(name="IndividualCount",query="select count(i) from Individual i"),
    @NamedQuery(name="IndividualsByName", query="select i from Individual i where i.name = :individualName"),
    @NamedQuery(name="IndividualsByNameWildcard", query="select i from Individual i where i.name like :individualName"),
    @NamedQuery(name="IndividualsByNameAndGivenNames", query="select i from Individual i where i.name = :individualName and i.givenNames = :givenNames"),
    @NamedQuery(name="IndividualsByNameAndGivenNamesWildcard", query="select i from Individual i where i.name like :individualName and i.givenNames like :givenNames")
    }
)
public class Individual extends AbstractHuman implements Comparable {

    private String givenNames;
    
    
    /**
     * Default constructor.
     */
    public Individual() {
        super();
    }
    
    public Individual(String name, String givenNames) {
        super(name);
        setGivenNames(givenNames);
    }


    /**
     * Gets given names property.
     * 
     * @return given names. May be <code>null</code>.
     */
    public String getGivenNames() {
        return givenNames;
    }

    /**
     * Sets given names property.
     * 
     * @param givenNames may be <code>null</code>.
     */
    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
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
            return new EqualsBuilder().append( this.getName(), rhs.getName())
                                     .append( this.givenNames, rhs.givenNames)
                                     .append( this.getNameQualification(), rhs.getNameQualification() )
                                     .isEquals();
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


    public int compareTo(Object o) {
        if ( o instanceof AbstractHuman && o instanceof Collective ) {
            return 1;
        }
        Individual rhs = (Individual) o;
        return new CompareToBuilder().append(this.getName(), rhs.getName())
                                     .append(this.givenNames, rhs.givenNames)
                                     .append(this.getNameQualification(), rhs.getNameQualification())
                                     .toComparison();
                                     
    }
    
    

}
