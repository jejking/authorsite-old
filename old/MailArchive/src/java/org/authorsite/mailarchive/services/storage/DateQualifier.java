/*
 * MessageLocatorQualifier.java, created on 19-Sep-2004 at 15:51:23
 * 
 * Copyright John King, 2004.
 *
 *  MessageLocatorQualifier.java is part of authorsite.org's MailArchive program.
 *
 *  MailArchive is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  MailArchive is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.services.storage;

import java.util.*;

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class DateQualifier {

    private Date fromDate;
    private Date toDate;
    
    
    public DateQualifier() {
        super();
    }
    
    public DateQualifier(Date fromDate, Date toDate) {
        if (fromDate != null) {
            setFromDate(fromDate);
        }
        if (toDate != null) {
            setToDate(toDate);
        }
    }
    
    
    /**
     * @return Returns the fromDate.
     */
    public Date getFromDate() {
        return fromDate;
    }
    
    /**
     * @param fromDate The fromDate to set.
     */
    public void setFromDate(Date fromDate) {
        if (toDate != null && fromDate != null && fromDate.after(toDate)) {
            throw new IllegalArgumentException("From Date must be before to date");
        }
        this.fromDate = fromDate;
    }
    
    /**
     * @return Returns the toDate.
     */
    public Date getToDate() {
        return toDate;
    }
    
    /**
     * @param toDate The toDate to set.
     */
    public void setToDate(Date toDate) {
        if (fromDate != null && toDate != null && toDate.before(fromDate)) {
            throw new IllegalArgumentException("To date must be after from date");
        }
        this.toDate = toDate;
    }
    
    public String toString() {
        return ("From: " + this.getFromDate() + " To: " + this.getToDate());
    }
    
    
}
