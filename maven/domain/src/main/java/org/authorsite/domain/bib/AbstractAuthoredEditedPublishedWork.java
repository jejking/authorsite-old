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

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.authorsite.domain.AbstractHuman;

@Entity
public abstract class AbstractAuthoredEditedPublishedWork extends AbstractAuthoredEditedWork {

    protected AbstractHuman publisher;

    
    /**
     * @return publisher, maybe <code>null</code>
     */
    @Transient
    public AbstractHuman getPublisher() {
    if (this.publisher == null) {
        Set<AbstractHuman> publishers = super.getWorkProducersOfType(WorkProducerType.PUBLISHER);
        if (!publishers.isEmpty()) {
    	this.publisher = publishers.iterator().next();
        }
    }
        return this.publisher;
    }

    /**
     * Sets the publisher. If one is already set, this is cleared and
     * the new one set.
     * 
     * @param publisher
     */
    public void setPublisher(AbstractHuman publisher) {
        if (!super.getWorkProducersOfType(WorkProducerType.PUBLISHER).isEmpty()) {
            super.removeAllWorkProducersOfType(WorkProducerType.PUBLISHER);
        }
        super.addWorkProducer(WorkProducerType.PUBLISHER, publisher);
        this.publisher = publisher;
    }

    @Override
    protected boolean areProducersOk() {

        // 0-n authors

        // 0-n editors

        // 0-1 publisher

        for (WorkProducerType workProducerType : WorkProducerType.values()) {
            if (workProducerType.equals(WorkProducerType.AUTHOR) || workProducerType.equals(WorkProducerType.EDITOR)) {
                continue;
            }
            Set<AbstractHuman> humans = this.getWorkProducersOfType(workProducerType);
            if (workProducerType.equals(WorkProducerType.PUBLISHER)) {
                if (humans.size() > 1) {
                    return false;
                }
            } else {
                if (!humans.isEmpty()) {
                    return false; // not editor, not author, not publisher.
                                    // Must be wrong.
                }
            }
        }
        return true;
    }

}
