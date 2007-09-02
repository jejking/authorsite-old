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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.authorsite.domain.AbstractHuman;

/**
 *
 * @author jejking
 */
@Entity()
@Table(name = "Work_Human")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class WorkProducer {

    private long id;
    private AbstractWork abstractWork;
    private AbstractHuman abstractHuman;

    public WorkProducer() {
        super();
    }

    public WorkProducer(AbstractWork abstractWork, AbstractHuman abstractHuman) {
        this.abstractWork = abstractWork;
        this.abstractHuman = abstractHuman;
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

    public void setId(long id) {
        this.id = id;
    }
    
    @Transient
    public abstract String getProducerType();
    
    @OneToOne(optional=false)
    public AbstractHuman getAbstractHuman() {
        return abstractHuman;
    }

    public void setAbstractHuman(AbstractHuman abstractHuman) {
        this.abstractHuman = abstractHuman;
    }

    @OneToOne(optional=false)
    public AbstractWork getAbstractWork() {
        return abstractWork;
    }

    public void setAbstractWork(AbstractWork abstractWork) {
        this.abstractWork = abstractWork;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorkProducer other = (WorkProducer) obj;
        if (this.abstractWork != other.abstractWork && (this.abstractWork == null || !this.abstractWork.equals(other.abstractWork))) {
            return false;
        }
        if (this.abstractHuman != other.abstractHuman && (this.abstractHuman == null || !this.abstractHuman.equals(other.abstractHuman))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.abstractWork != null ? this.abstractWork.hashCode() : 0);
        hash = 67 * hash + (this.abstractHuman != null ? this.abstractHuman.hashCode() : 0);
        return hash;
    }
   
    
}
