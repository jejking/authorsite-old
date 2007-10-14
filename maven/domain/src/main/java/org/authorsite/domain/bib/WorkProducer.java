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

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.authorsite.domain.AbstractHuman;

/**
 *
 * @author jejking
 */
@Embeddable()
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class WorkProducer {

    private WorkProducerType workProducerType;
    private AbstractHuman abstractHuman;

    /**
     * Default constructor. In general, new instances should be created
     * from the works API.
     */
    public WorkProducer() {
        super();
    }
    
    /**
     * @param workProducerType
     * @param abstractHuman
     */
    public WorkProducer(WorkProducerType workProducerType, AbstractHuman abstractHuman) {
	super();
	this.workProducerType = workProducerType;
	this.abstractHuman = abstractHuman;
    }


    @ManyToOne(optional=false, fetch=FetchType.EAGER)
    public AbstractHuman getAbstractHuman() {
        return abstractHuman;
    }

    protected void setAbstractHuman(AbstractHuman abstractHuman) {
        this.abstractHuman = abstractHuman;
    }
    
    /**
     * @return work producer type
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    public WorkProducerType getWorkProducerType() {
	return this.workProducerType;
    }
    
    protected void setWorkProducerType(WorkProducerType workProducerType) {
	this.workProducerType = workProducerType;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((this.abstractHuman == null) ? 0 : this.abstractHuman.hashCode());
	result = PRIME * result + ((this.workProducerType == null) ? 0 : this.workProducerType.hashCode());
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
	if (getClass() != obj.getClass())
	    return false;
	final WorkProducer other = (WorkProducer) obj;
	if (this.abstractHuman == null) {
	    if (other.abstractHuman != null)
		return false;
	} else if (!this.abstractHuman.equals(other.abstractHuman))
	    return false;
	if (this.workProducerType == null) {
	    if (other.workProducerType != null)
		return false;
	} else if (!this.workProducerType.equals(other.workProducerType))
	    return false;
	return true;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Producer Type: ");
        sb.append(this.workProducerType);
        sb.append(", Human: ");
        sb.append(this.abstractHuman);
        return sb.toString();
    }
    
}
