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
package org.authorsite.domain.bib;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.authorsite.domain.AbstractEntry;
import org.authorsite.domain.AbstractHuman;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.IndexColumn;

/**
 * Class representing common features of works to be 
 * captured in the bibliographic sub-system of the authorsite 
 * application.
 * 
 * <p>All works are held to have the following additional
 * properties to {@link AbstractEntry}:</p>
 * <ul>
 * <li><code>Title</code>: the title of the work. This may be "Unknown".</li>
 * <li><code>WorkDates</code>: the dates of the work. See {@link WorkDates}.</li> 
 * </ul>
 * 
 * 
 * @author jejking
 *
 */
@Entity
@Table(name="Work")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class AbstractWork extends AbstractEntry {

    private String title;

    private WorkDates workDates = new WorkDates();
    private Set<WorkProducer> workProducers = new HashSet<WorkProducer>();
    
    protected Map<WorkProducerType, SortedSet<AbstractHuman>> typeHumansMap = new HashMap<WorkProducerType, SortedSet<AbstractHuman>>();

    /**
     * Default constructor.
     */
    public AbstractWork() {
	super();
    }

    /**
     * Gets title.
     * 
     * @return title. If <code>null</code>, returns "Unknown".
     */
    @Column(nullable=false)
    public String getTitle() {
	if (this.title == null) {
	    return "Unknown";
	} else {
	    return this.title;
	}
    }

    /**
     * Sets title.
     * 
     * @param title may be <code>null</code>.
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * Gets work dates.
     * 
     * @return work dates, may be <code>null</code> if not known.
     */
    @Embedded
    public WorkDates getWorkDates() {
	return this.workDates;
    }

    /**
     * Sets work dates.
     * 
     * @param workDates may be <code>null</code>.
     */
    public void setWorkDates(WorkDates workDates) {
	this.workDates = workDates;
    }

    /**
     * @return set of all work producers associated with
     * the work
     */
    @CollectionOfElements
    @IndexColumn(name="producer_idx")
    public Set<WorkProducer> getWorkProducers() {
        return workProducers;
    }

    protected void setWorkProducers(Set<WorkProducer> workProducers) {
        for (WorkProducer workProducer : workProducers) {
            this.addWorkProducer(workProducer);
        }
    }
    
    protected void addWorkProducer(WorkProducer workProducer) {
	this.workProducers.add(workProducer);
	SortedSet<AbstractHuman> humans = this.typeHumansMap.get(workProducer.getWorkProducerType());
	if (humans == null) {
	    humans = new TreeSet<AbstractHuman>();
	    this.typeHumansMap.put(workProducer.getWorkProducerType(), humans);
	}
	humans.add(workProducer.getAbstractHuman());
    }
    
    protected void addWorkProducer(WorkProducerType workProducerType, AbstractHuman abstractHuman) {
	this.addWorkProducer(new WorkProducer(workProducerType, abstractHuman));
    }
    
    protected void removeWorkProducer(WorkProducer workProducer) {
	this.workProducers.remove(workProducer);
	Set<AbstractHuman> humans = this.typeHumansMap.get(workProducer.getWorkProducerType());
	if (humans != null) {
	    humans.remove(workProducer.getAbstractHuman());
	}
    }
    
    protected void removeAllWorkProducersOfType(WorkProducerType workProducerType) {
	for (WorkProducer workProducer : this.workProducers) {
	    if (workProducer.getWorkProducerType().equals(workProducerType)) {
		this.workProducers.remove(workProducer);
	    }
	}
	Set<AbstractHuman> humans = this.typeHumansMap.get(workProducerType);
	if (humans != null) {
	    humans.clear();
	}
    }
    
    protected void removeWorkProducer(WorkProducerType workProducerType, AbstractHuman abstractHuman) {
	this.removeWorkProducer(new WorkProducer(workProducerType, abstractHuman));
    }
    
    @Transient
    protected Set<AbstractHuman> getWorkProducersOfType(WorkProducerType workProducerType) {
	Set<AbstractHuman> humans = this.typeHumansMap.get(workProducerType);
	if (humans == null) {
	    return Collections.emptySet();
	}
	return humans;
    }
    
    
    
    /**
     * Checks that the set of work producers are appropriate for the type.
     *  
     * 
     * @throws IllegalStateException
     */
    @PrePersist
    public void validate() throws IllegalStateException {
	if ( !this.areProducersOk() ) {
	    throw new IllegalStateException("Invalid producers. Cannot save");
	}
    }

    protected abstract boolean areProducersOk();
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Work ");
        sb.append(super.toString());
        sb.append(" Title: ");
        sb.append(this.title);
        sb.append(", Dates: " );
        sb.append(this.workDates);
        sb.append(" ");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((this.title == null) ? 0 : this.title.hashCode());
	result = PRIME * result + ((this.workDates == null) ? 0 : this.workDates.hashCode());
//	result = PRIME * result + ((this.workProducers == null) ? 0 : this.workProducers.hashCode());
	if (this.workProducers != null) {
	    result = PRIME * result + Arrays.deepHashCode(this.workProducers.toArray());
	}
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (!(obj instanceof AbstractWork))
	    return false;
	final AbstractWork other = (AbstractWork) obj;
	if (this.title == null) {
	    if (other.title != null)
		return false;
	} else if (!this.title.equals(other.title))
	    return false;
	if (this.workDates == null) {
	    if (other.workDates != null)
		return false;
	} else if (!this.workDates.equals(other.workDates))
	    return false;
	if (this.workProducers == null) {
	    if (other.workProducers != null)
		return false;
	} else if (!Arrays.deepEquals(this.workProducers.toArray(), other.workProducers.toArray()))
	    return false;
	return true;
    }

    
}
