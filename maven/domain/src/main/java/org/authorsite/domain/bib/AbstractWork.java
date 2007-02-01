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
public abstract class AbstractWork extends AbstractEntry {

    private String title;

    private WorkDates workDates = new WorkDates();

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
    public WorkDates getWorkDates() {
	return this.workDates;
    }

    /**
     * Sets work dates.
     * 
     * @param workDates may be <code>null</code>.
     */
    public void setYears(WorkDates workDates) {
	this.workDates = workDates;
    }

}
