/*
 * OrganisationDTOAssemblerTestFacadeBean.java
 *
 * Created on 20 November 2002, 15:31
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/services/dto/OrganisationDTOAssemblerTestFacadeBean.java,v 1.2 2003/03/01 13:35:37 jejking Exp $
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
 * Test suite for OrganisationDTOAssembler
 * @ejb:bean    name="OrganisationDTOAssemblerTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/OrganisationDTOAssemblerTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.services.dto.OrganisationDTOAssemblerTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.services.dto.OrganisationDTOAssemblerTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local" 
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class OrganisationDTOAssemblerTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private OrganisationLocalHome organisationLocalHome;
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testOrganisationDTOAssembly() {
        try {
            OrganisationLocal org = organisationLocalHome.create(getKeyCandidate(), "testOrgName");
            OrganisationDTOAssembler firstAssembler = new OrganisationDTOAssembler(org);
            OrganisationDTO firstDTO = firstAssembler.assembleDTO();
            if (!firstDTO.getID().equals(org.getOrganisationID())) {
                return ("firstDTO id not equal to organisationID");
            }
            if (!firstDTO.getName().equals(org.getName())) {
                return ("firstDTO's name is not equal to \"testOrgName\"");
            }
            
            org.setCity("testCity");
            org.setCountry("testCountry");
            
            OrganisationDTOAssembler secondAssembler = new OrganisationDTOAssembler(org);
            OrganisationDTO secondDTO = secondAssembler.assembleDTO();
            
            if (!secondDTO.getID().equals(org.getOrganisationID())) {
                return ("secondDTO id not equal to organisationID");
            }
            if (!secondDTO.getName().equals(org.getName())) {
                return ("secondDTO's name is not equal to \"testOrgName\"");
            }
            if (!secondDTO.getCity().equals("testCity")) {
                return ("secondDTO's city is not equal to \"testCity\"");
            }
            if (!secondDTO.getCountry().equals("testCountry")) {
                return ("secondDTO's country is not equals to \"testCountry\"");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /** Creates a new instance of OrganisationDTOAssemblerTestFacadeBean */
    public OrganisationDTOAssemblerTestFacadeBean() {
        getLocalHomes();
    }
    
    private int getKeyCandidate() {
        Long keyLong = new Long(System.currentTimeMillis());
        return keyLong.intValue();
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyOrganisationEJB");
            organisationLocalHome = (OrganisationLocalHome) obj;
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
