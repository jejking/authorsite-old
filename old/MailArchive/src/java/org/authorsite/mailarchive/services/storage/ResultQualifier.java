/*
 * ResultQualifier.java, created on 26-Sep-2004 at 09:57:20
 * 
 * Copyright John King, 2004.
 *
 *  ResultQualifier.java is part of authorsite.org's MailArchive program.
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

/**
 * 
 * @author jejking
 * @version $Revision: 1.2 $
 */
public class ResultQualifier {
    
   
    private int maxResults;
    private int firstResult;

    public ResultQualifier() {
        super();
    }
    
    public ResultQualifier(int firstResult, int maxResults) {
        setFirstResult(firstResult);
        setMaxResults(maxResults);
    }
    
    /**
     * @return Returns the firstResult.
     */
    public int getFirstResult() {
        return firstResult;
    }
    
    /**
     * @param firstResult The firstResult to set.
     */
    public void setFirstResult(int firstResult) {
        if (firstResult < 0) { // setting to 0 counts as a reset. note first result in result set is indexed 0
            throw new IllegalArgumentException("Initial number in result set must be greater than 0");
        }
        this.firstResult = firstResult;
    }
    
    /**
     * @return Returns the maxResults.
     */
    public int getMaxResults() {
        return maxResults;
    }
    
    /**
     * @param maxResults The maxResults to set.
     */
    public void setMaxResults(int maxResults) {
        if (maxResults < 0) { // zero resets
            throw new IllegalArgumentException("maxResults must be greater than 0 or 0 to reset");
        }
        this.maxResults = maxResults;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new String ("From: " + firstResult + " Max Results: " + maxResults);
    }
   
}
