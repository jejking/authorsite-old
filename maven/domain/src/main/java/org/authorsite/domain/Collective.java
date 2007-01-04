package org.authorsite.domain;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Class representing any grouping of human beings, derived from
 * {@link AbstractHuman}. The class defines the additional property
 * <code>Place</code> to indicate where the collective is or was 
 * located. This is of primary use when indicating where, say, a 
 * publishing house is located.
 * 
 * <p>The property is a simple string and may hold more than 
 * one place term separated by commas. The property should use
 * the local term for the place at the time - e.g. München for Munich and 
 * Roma for Rome. When a place has more than one local name, such
 * as in bilingual localities, it is up to the user to pick
 * the one which seems most appropriate!</p>
 * 
 * @author jejking
 *
 */
@Entity()
@NamedQueries(
    {
    @NamedQuery(name="CollectiveCount",query="select count(c) from Collective c"),
    @NamedQuery(name="CollectivesByName", query="select c from Collective c where c.name = :collectiveName"),
    @NamedQuery(name="CollectivesByNameWildcard", query="select c from Collective c where c.name like :collectiveName"),
    @NamedQuery(name="CollectivesByPlace", query="select c from Collective c where c.place = :placeName"),
    @NamedQuery(name="CollectivesByPlaceWildcard", query="select c from Collective c where c.place like :placeName")
    }
)
public class Collective extends AbstractHuman implements Comparable {

    private String place;

    /**
     * Default constructor.
     */
    public Collective() {
	super();
    }

    
    /**
     * Constructs collective with the given name.
     *
     * @param name name of the collective
     */
    public Collective(String name) {
        super(name);
    }
    
    /**
     * Constructs collective with the supplied
     * name and place parameters.
     *
     *
     * @param name
     * @param place
     */
    public Collective(String name, String place) {
        this(name);
        this.setPlace(place);
    }
    
    /**
     * Constructs collective with the supplied name,
     * name qualification and place parameters.
     *
     * @param name
     * @param nameQualification
     * @param place
     */
    public Collective(String name, String nameQualification, String place) {
        this(name);
        this.setNameQualification(nameQualification);
        this.setPlace(place);
    }
    

    /**
     * Gets place name.
     * 
     * @return place. May be <code>null</code>.
     */
    public String getPlace() {
	return place;
    }

    /**
     * Sets place name.
     * 
     * @param place may be <code>null</code>.
     */
    public void setPlace(String place) {
	this.place = place;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (obj == this) {
	    return true;
	}
	if (obj instanceof Collective) {
	    Collective rhs = (Collective) obj;
	    return new EqualsBuilder().append(this.getName(), rhs.getName())
		    .append(this.getPlace(), rhs.getPlace()).append(
			    this.getNameQualification(),
			    rhs.getNameQualification()).isEquals();
	} else {
	    return false;
	}
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(this.getName()).append(this.place)
		.append(this.getNameQualification()).toHashCode();
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(this.getName());
	if (place != null) {
	    sb.append(", ");
	    sb.append(this.place);
	}
	if (this.getNameQualification() != null) {
	    sb.append(" (");
	    sb.append(this.getNameQualification());
	    sb.append(")");
	}
	return sb.toString();
    }

    public int compareTo(Object o) {
	if (o instanceof AbstractHuman && o instanceof Individual) {
	    return -1;
	}
	Collective rhs = (Collective) o;
	return new CompareToBuilder()
		.append(this.getName(), rhs.getName())
		.append(this.getPlace(), rhs.getPlace())
		.append(this.getNameQualification(), rhs.getNameQualification())
		.toComparison();
    }

}
