/*
 * SequenceBlockPrimaryKeyGeneratorTestBean.java
 *
 * Created on 04 November 2002, 15:46
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/services/database/SequenceBlockPrimaryKeyGeneratorTestFacadeBean.java,v 1.2 2003/03/01 13:35:42 jejking Exp $
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

package org.authorsite.bib.ejb.test.services.database;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;

import org.authorsite.bib.ejb.services.database.*;

/**
 * Tests the SequenceBlockPrimaryKeyGenerator by counting up and over the cache limit.
 *
 *
 * @ejb:bean    name="SequenceBlockPrimaryKeyGeneratorTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/SequenceBlockPrimaryKeyGeneratorTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.services.database.SequenceBlockPrimaryKeyGeneratorTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.services.database.SequenceBlockPrimaryKeyGeneratorTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="SequenceBlockPrimaryKeyGeneratorEJB"
 *              ref-name="ejb/MySequenceBlockPrimaryKeyGeneratorEJB"
 *              view-type="local"
 *
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class SequenceBlockPrimaryKeyGeneratorTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private SequenceBlockPrimaryKeyGeneratorLocalHome sequenceBlockPrimaryKeyGeneratorLocalHome;
    
    public void ejbCreate() throws CreateException {
    }
    
    /** Creates a new instance of SequenceBlockPrimaryKeyGeneratorTestBean */
    public SequenceBlockPrimaryKeyGeneratorTestFacadeBean() {
        getLocalHomes();
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MySequenceBlockPrimaryKeyGeneratorEJB");
            sequenceBlockPrimaryKeyGeneratorLocalHome = (SequenceBlockPrimaryKeyGeneratorLocalHome) obj;
        }
        catch (NamingException ne) {
            throw new EJBException(ne);
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce);
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testSequenceGeneration() {
        try {
            HashSet numbersReturned = new HashSet(); // easy to test for uniqueness of numbers. If any attempt to add returns "false", we have a problem
            SequenceBlockPrimaryKeyGeneratorLocal sequenceBlockPrimaryKeyGeneratorLocal = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
            
            for (int i = 0; i <= 1000; i++) {
                Integer returnedInteger = new Integer(sequenceBlockPrimaryKeyGeneratorLocal.getNextSequenceNumber("test"));
                if (numbersReturned.add(returnedInteger)) {
                    continue;
                }
                else {
                    return ("Duplicate integer returned");
                }
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        ctx = sessionContext;
    }
    
}
