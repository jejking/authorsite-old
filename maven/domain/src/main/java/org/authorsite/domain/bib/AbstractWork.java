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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.authorsite.domain.AbstractEntry;

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
    
    protected Set<WorkProducer> workProducers = new HashSet<WorkProducer>();

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

    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="abstractWork")
    public Set<WorkProducer> getWorkProducers() {
        return workProducers;
    }

    public void setWorkProducers(Set<WorkProducer> workProducers) {
        this.workProducers = workProducers;
    }
    
    protected Set<WorkProducer> getWorkProducersOfType(String type) {
       Set<WorkProducer> filteredSet = new HashSet<WorkProducer>();
       for (WorkProducer workProducer : workProducers) {
           if(workProducer.getProducerType().equals(type)) {
               filteredSet.add(workProducer);
           }
       }
       return filteredSet;
    }

}
