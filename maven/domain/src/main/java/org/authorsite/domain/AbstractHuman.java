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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Abstract entry superclass for individuals and collectives, grouping some
 * common properties.
 * 
 * @author jejking
 */
@Entity()
@Table(name = "human")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractHuman extends AbstractEntry {

    protected String name;

    protected String nameQualification;

    /**
         * Default constructor.
         */
    public AbstractHuman() {
	super();
    }

    /**
         * Constructs abstract human with name set to that supplied.
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
	if (this.name != null) {
	    return this.name;
	} else {
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
         * Gets name qualification. This is additional data about the name. For
         * individuals, this might be "Jnr", "III". For collectives, it may
         * indicate the type of company - "Ltd", "Pty", "GmbH", etc.
         * 
         * @return name qualification. May be <code>null</code>.
         */
    public String getNameQualification() {
	return this.nameQualification;
    }

    /**
         * Sets name qualification.
         * 
         * @param nameQualification
         *                New name qualification, may be <code>null</code>.
         */
    public void setNameQualification(String nameQualification) {
	this.nameQualification = nameQualification;
    }

}
