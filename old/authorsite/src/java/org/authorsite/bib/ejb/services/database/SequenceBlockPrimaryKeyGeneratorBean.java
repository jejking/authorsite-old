/*
 * SequenceBlockPrimaryKeyGenerator.java
 *
 * Created on 04 November 2002, 14:03
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/services/database/SequenceBlockPrimaryKeyGeneratorBean.java,v 1.1 2003/03/29 12:48:19 jejking Exp $
 *
 * Copyright (C) 2002  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.ejb.services.database;
import javax.ejb.*;
import javax.naming.*;
import java.util.*;
import org.authorsite.bib.ejb.entity.*;

/**
 * Stateless Session bean which accesses the Sequence entityEJB to grab blocks of 100 possible keys,
 * caches them and hands them out on request from clients for use in creating other entity EJBs. This
 * approach should be both reliable and scalable.
 *
 * The code is essentially derived from Floyd Marinescu's "EJB Design Patterns" book (Wiley, 2002) and
 * the SequenceSessionBean.java code, but modified to suit our build process, coding conventions and package
 * layout.
 *
 * @ejb:bean    name="SequenceBlockPrimaryKeyGeneratorEJB"
 *              type="Stateless"
 *              local-jndi-name="ejb/SequenceBlockPrimaryKeyGeneratorLocalEJB"
 *              view-type="local"
 *
 * @ejb:interface   local-class="org.authorsite.bib.ejb.services.database.SequenceBlockPrimaryKeyGeneratorLocal"
 *                  generate="local"
 * @ejb:home        local-class="org.authorsite.bib.ejb.services.database.SequenceBlockPrimaryKeyGeneratorLocalHome"
 *                  generate="local"
 *
 * @ejb:ejb-ref ejb-name="SequenceEJB"
 *              ref-name="ejb/MySequenceEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class SequenceBlockPrimaryKeyGeneratorBean implements SessionBean {
    
    private class Entry {
        SequenceLocal sequenceLocal;
        int last;
    }
    
    private SessionContext ctx;
    private Hashtable entries = new Hashtable();
    private int blockSize = 100;
    private int retryCount = 3;
    private SequenceLocalHome sequenceLocalHome;
    
    public void ejbCreate() throws CreateException {
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MySequenceEJB");
            sequenceLocalHome = (SequenceLocalHome) obj;
        }
        catch (NamingException ne) {
            throw new EJBException (ne);
        }
        catch (ClassCastException cce) {
            throw new EJBException (cce);
        }
    }
    
    /** Creates a new instance of SequenceBlockPrimaryKeyGenerator */
    public SequenceBlockPrimaryKeyGeneratorBean() {
        getLocalHomes();
    }
    
    /** 
     * @ejb:interface-method view-type="local"
     * 
     * returns the next number in the sequence associated with the sequence named
     */
    public int getNextSequenceNumber(String name) {
        try {
            Entry entry = (Entry) entries.get(name);
            
            if (entry == null) {
                // add an entry to the sequence table
                entry = new Entry();
                try {
                    entry.sequenceLocal = sequenceLocalHome.findByPrimaryKey(name);
                }
                catch (javax.ejb.FinderException fe) {
                    entry.sequenceLocal = sequenceLocalHome.create(name);
                }
                entries.put(name, entry);
            }
            if (entry.last % blockSize == 0) { // i.e. we've counted up through our remaining known valid keys
                for (int retry = 0; true; retry++) {
                    try {
                        entry.last = entry.sequenceLocal.getValueAfterIncrementingBy(blockSize);
                        break;
                    }
                    catch (javax.ejb.TransactionRolledbackLocalException trle) {
                        if (retry < retryCount) {
                            // we hit a concurrency exception, so try again...
                            continue;
                        }
                        else {
                            // we tried too many times, so fail...
                            throw new javax.ejb.EJBException(trle);
                        }
                    }
                }
            }
            
            return entry.last++;
        }
        catch (javax.ejb.CreateException ce) {
            throw new javax.ejb.EJBException(ce);
        }
    }
    
    
    public void ejbActivate() throws javax.ejb.EJBException {
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        ctx = sessionContext;
    }
}
