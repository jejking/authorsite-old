/*
 * MediaProductionRelationshipDTOAssemblerTestFacadeBean.java
 *
 * Created on 20 November 2002, 16:34
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/services/dto/MediaProductionRelationshipDTOAssemblerTestFacadeBean.java,v 1.2 2003/03/01 13:35:37 jejking Exp $
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
 * @ejb:bean    name="MediaProductionRelationshipDTOAssemblerTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/MediaProductionRelationshipDTOAssemblerTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.services.dto.MediaProductionRelationshipDTOAssemblerTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.services.dto.MediaProductionRelationshipDTOAssemblerTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"  
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="MediaProductionRelationshipEJB"
 *              ref-name="ejb/MyMediaProductionRelationshipEJB"
 *              view-type="local" 
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaProductionRelationshipDTOAssemblerTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private OrganisationLocalHome organisationLocalHome;
    private PersonLocalHome personLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testMediaProductionRelationshipDTOAssembly() {
        try {
            // first, lets create two people who will be authors
            PersonLocal person1 = personLocalHome.create(getKeyCandidate(), "testPerson1");
            PersonLocal person2 = personLocalHome.create(getKeyCandidate(), "testPerson2");
            // and an organisation which will also be an author, somehow... Never mind, it's a test
            OrganisationLocal org1 = organisationLocalHome.create(getKeyCandidate(), "org1");
            
            MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.create(getKeyCandidate(), "author");
            Set prodRelPeople = prodRel.getPeople();
            prodRelPeople.add(person1);
            prodRelPeople.add(person2);
            Set prodRelOrgs = prodRel.getOrganisations();
            prodRelOrgs.add(org1);
            
            // now make a corresponding DTO
            MediaProductionRelationshipDTOAssembler assembler = new MediaProductionRelationshipDTOAssembler(prodRel);
            MediaProductionRelationshipDTO dto = assembler.assembleDTO();
            
            // check the dto has the correct attributes
            if (!dto.getID().equals(prodRel.getMediaProductionRelationshipID())) {
                return ("dto id not equal to mediaProductionRelationshipID");
            }
            if (!dto.getRelationshipType().equals("author")) {
                return ("dto's relationshipType is not equal to \"author\"");
            }
            Set dtoPeople = dto.getPeople();
            // check the right sum is there
            if (dtoPeople.size() != 2) {
                return ("dto's set of people was wrong size, namely " + dtoPeople.size());
            }
            boolean foundPerson1 = false;
            boolean foundPerson2 = false;
            
            // now, check the right personDTOs are there
            Iterator dtoPeopleIt = dtoPeople.iterator();
            while (dtoPeopleIt.hasNext()) {
                PersonDTO currentPersonDTO = (PersonDTO) dtoPeopleIt.next();
                if (currentPersonDTO.getID().equals(person1.getPersonID())) {
                    foundPerson1 = true;
                }
                else if (currentPersonDTO.getID().equals(person2.getPersonID())) {
                    foundPerson2 = true;
                }
            }
            if (!foundPerson1) {
                return ("failed to retrieve person1 from dto");
            }
            if (!foundPerson2) {
                return ("failed to retrieve person2 from dto");
            }
            
            // now, see if we can correctly extract org1 from the dto
            Set dtoOrgs = dto.getOrganisations();
            if (dtoOrgs.size() != 1) {
                return ("dto's set of organisations was wrong size, namely " + dtoOrgs.size());
            }
            Iterator dtoOrgsIt = dtoOrgs.iterator();
            while (dtoOrgsIt.hasNext()) {
                OrganisationDTO orgDTO = (OrganisationDTO) dtoOrgsIt.next();
                if (!orgDTO.getID().equals(org1.getOrganisationID())) {
                    return ("failed to retrieve org1 from dto");
                }
            }
            // ok, all tests have passed by now. Correct id, type, people and orgs
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
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
            
            Object obj2 = context.lookup("java:comp/env/ejb/MyPersonEJB");
            personLocalHome = (PersonLocalHome) obj2;
            
            Object obj3 = context.lookup("java:comp/env/ejb/MyMediaProductionRelationshipEJB");
            mediaProductionRelationshipLocalHome = (MediaProductionRelationshipLocalHome) obj3;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    /** Creates a new instance of MediaProductionRelationshipDTOAssemblerTestFacadeBean */
    public MediaProductionRelationshipDTOAssemblerTestFacadeBean() {
        getLocalHomes();
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
