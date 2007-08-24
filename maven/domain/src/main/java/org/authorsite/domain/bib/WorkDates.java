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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Helper class representing the dates of a given {@link AbstractWork}.
 * 
 * <p>It defines two properties:</p>
 * <ul>
 * <li><code>date</code>: the first date associated with the work.</li>
 * <li><code>toDate</code>: an optional second date associated with the work. If set,
 * this must be after the first <code>date</code>.</li>
 * </ul>
 * 
 * <p>Note, this class is not currently very sophisticated about handling
 * time zones - i.e. it doesn't. All dates are simply in the default 
 * time zone of the JVM for the present.</p>
 * 
 * @author jejking
 *
 */
@Embeddable
public class WorkDates implements Comparable<WorkDates>, Serializable {

    /**
     * Generated by Eclipse.
     */
    private static final long serialVersionUID = 5041156081178942983L;

    private Date date;

    private Date toDate;

    /**
     * Default constructor.
     */
    public WorkDates() {
	super();
    }

    /**
     * Creates object set to midnight on 1st January of the 
     * year of the Western calendar specified as the argument.
     * 
     * @param year
     */
    public WorkDates(int year) {
	this(year, 1, 1);
    }
    
    public WorkDates(int fromYear, int toYear) {
        this(fromYear);
        this.toDate = this.buildDate(toYear, 1, 1);
    }

    /**
     * Creates object, setting values of date property
     * to date with year, month, day set to the values
     * below. These must be feasible dates. The time
     * is set to midnight precisely.
     * 
     * @param year 
     * @param month 
     * @param day 
     */
    public WorkDates(int year, int month, int day) {
	this.date = this.buildDate(year, month, day);
    }

    private Date buildDate(int year, int month, int day) {
        GregorianCalendar gc = new GregorianCalendar();
	gc.set(Calendar.YEAR, year);
	// month
	gc.set(Calendar.MONTH, month);
	// day
	gc.set(Calendar.DAY_OF_MONTH, day);
	// midnight
	gc.set(Calendar.HOUR_OF_DAY, 0);
	gc.set(Calendar.MINUTE, 0);
	gc.set(Calendar.SECOND, 0);
	gc.set(Calendar.MILLISECOND, 0);
        return gc.getTime();
    }
    
    /**
     * Returns date.
     * 
     * @return copy of date, may be <code>null</null>
     */
    @Temporal(value = TemporalType.DATE)
    public Date getDate() {
	if (this.date != null) {
	    return (Date) this.date.clone();
	} else {
	    return null;
	}
    }

    /**
     * Sets date. If property <code>toDate</code>
     * is set, checks that the date parameter is 
     * <em>before</em> the <code>toDate</code> property. 
     * 
     * <p>If 
     * it is not, throws an <code>IllegalArgumentException</code>. If the 
     * parameter is <code>null</code> and the <code>toDate</code>
     * property is set, also throws an IllegalArgumentException.
     * </p>
     * 
     * <p>If param is <code>null</code>, then property is set to <code>null</code>.
     * Otherwise, property is set to a copy of the parameter passed in.</p>
     * 
     * @param date
     * @throws IllegalArgumentException
     */
    public void setDate(Date date) {
	if (date == null && this.toDate != null) {
	    throw new IllegalArgumentException("Date param is null, but toDate param is set");
	}
	if (date != null && this.toDate != null && date.after(this.toDate)) {
	    throw new IllegalArgumentException("Date param (" + date.toString() + ") is after the toDate property ("
		    + this.toDate.toString() + "), which is illogical");
	}
	if (date == null) {
	    this.date = null;
	} else {
	    this.date = (Date) date.clone();
	    ;
	}
    }

    /**
     * Returns toDate property. 
     * 
     * @return to date property. If this is <code>null</code>, returns <code>null</code>,
     * else return a copy of the property.
     */
    @Temporal(value = TemporalType.DATE)
    public Date getToDate() {
	if (this.toDate == null) {
	    return null;
	} else {
	    return (Date) this.date.clone();
	}
    }

    /**
     * Sets toDate property.
     * 
     * <p>If property <code>date</code> is set, checks that the parameter
     * is <em>after</em> the <code>date</code> property. If it is not,
     * throws an <code>IllegalArgumentException</code>.</p>
     * 
     * <p>If the param is <code>null</code>, the property is also set to
     * <code>null</code>. Otherwise, it is set to a copy of the parameter.</p>
     * 
     * @param toDate
     * @throws IllegalArgumentException
     */
    public void setToDate(Date toDate) {

	if (this.date != null && toDate != null && toDate.before(this.date)) {
	    throw new IllegalArgumentException("ToDate param (" + toDate.toString() + ") is before the date property ("
		    + this.date.toString() + "), which is illogical.");
	}

	if (toDate == null) {
	    this.toDate = null;
	} else {
	    this.toDate = (Date) toDate.clone();
	}

    }

    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((this.date == null) ? 0 : this.date.hashCode());
	result = PRIME * result + ((this.toDate == null) ? 0 : this.toDate.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final WorkDates other = (WorkDates) obj;
	if (this.date == null) {
	    if (other.date != null)
		return false;
	} else if (!this.date.equals(other.date))
	    return false;
	if (this.toDate == null) {
	    if (other.toDate != null)
		return false;
	} else if (!this.toDate.equals(other.toDate))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append(this.date);
	if (this.toDate != null) {
	    sb.append("-");
	    sb.append(this.toDate);
	}
	return sb.toString();
    }

    public int compareTo(WorkDates workDates) {
	return new CompareToBuilder().append(this.getDate(), workDates.getDate()).append(this.getToDate(),
		workDates.getToDate()).toComparison();

    }

}
