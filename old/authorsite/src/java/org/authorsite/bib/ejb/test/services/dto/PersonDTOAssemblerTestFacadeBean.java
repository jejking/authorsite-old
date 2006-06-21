/*
 * PersonDTOAssemblerTestFacadeBean.java
 *
 * Created on 20 November 2002, 14:06
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/services/dto/PersonDTOAssemblerTestFacadeBean.java,v 1.2 2003/03/01 13:35:37 jejking Exp $
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

package org.authorsite.bib.ejb.test.services.dto;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.services.dto.*;
import org.authorsite.bib.ejb.entity.*;

/**
 * Test suite for PersonDTOAssembler
 * @ejb:bean    name="PersonDTOAssemblerTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/PersonDTOAssemblerTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.services.dto.PersonDTOAssemblerTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.services.dto.PersonDTOAssemblerTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PersonDTOAssemblerTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private PersonLocalHome personLocalHome;
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testPersonDTOAssembly() {
        try {
            PersonLocal person = personLocalHome.create(getKeyCandidate(), "testName");
            // test DTO
            PersonDTOAssembler firstAssembler = new PersonDTOAssembler(person);
            PersonDTO firstDTO = firstAssembler.assembleDTO();
            if (!firstDTO.getID().equals(person.getPersonID())) {
                return ("firstDTO's ID not equal to personID");
            }
            if (!firstDTO.getMainName().equals("testName")) {
                return ("firstDTO's ID not equal to \"testName\"");
            }
            if (firstDTO.getGivenName() != null) {
                return ("firstDTO's givenName was not equal to null despite not being set");
            }
            
            // now, set some more attributes on the personBean and build another dto
            person.setGenderCode(1);
            person.setGivenName("testGivenName");
            person.setOtherNames("testOtherNames");
            person.setPrefix("testPrefix");
            person.setSuffix("testSuffix");
            
            PersonDTOAssembler secondAssembler = new PersonDTOAssembler(person);
            PersonDTO secondDTO = secondAssembler.assembleDTO();
            if (!secondDTO.getID().equals(person.getPersonID())) {
                return ("secondDTO's ID not equal to personID");
            }
            if (!secondDTO.getMainName().equals("testName")) {
                return ("secondDTO's ID not equal to \"testName\"");
            }
            if (!secondDTO.getGivenName().equals("testGivenName")) {
                return ("secondDTO's givenName was not equal to \"testGivenName\"");
            }
            if (!secondDTO.getOtherNames().equals("testOtherNames")) {
                return ("secondDTO's otherNames was not equals to \"testOtherNames\"");
            }
            if (!secondDTO.getPrefix().equals("testPrefix")) {
                return ("secondDTO's prefix was not equal to \"testPrefix\"");
            }
            if (!secondDTO.getSuffix().equals("testSuffix")) {
                return ("secondDTO's suffix was not equals to\"testSuffix\"");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        
    }
    
    /** Creates a new instance of PersonDTOAssemblerTestFacadeBean */
    public PersonDTOAssemblerTestFacadeBean() {
        getLocalHomes();
    }
    
    private int getKeyCandidate() {
        Long keyLong = new Long(System.currentTimeMillis());
        return keyLong.intValue();
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyPersonEJB");
            personLocalHome = (PersonLocalHome) obj;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    public void ejbCreate() throws CreateException {
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
