/*
 * MediaProductionRelationshipDTOAssembler.java
 *
 * Created on 20 November 2002, 11:54
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/services/dto/MediaProductionRelationshipDTOAssembler.java,v 1.1 2003/03/29 12:49:01 jejking Exp $
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

package org.authorsite.bib.ejb.services.dto;
import java.util.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.dto.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class MediaProductionRelationshipDTOAssembler {
    
    private MediaProductionRelationshipLocal myMediaProductionRelationshipLocal;
    private MediaProductionRelationshipDTO myMediaProductionRelationshipDTO;
    
    /** Creates a new instance of MediaProductionRelationshipDTOAssembler */
    public MediaProductionRelationshipDTOAssembler(MediaProductionRelationshipLocal newMediaProductionRelationshipLocal) {
        myMediaProductionRelationshipLocal = newMediaProductionRelationshipLocal;
        myMediaProductionRelationshipDTO = new MediaProductionRelationshipDTO(myMediaProductionRelationshipLocal.getMediaProductionRelationshipID());
    }
    
    public MediaProductionRelationshipDTO assembleDTO() {
        // set up core data
        myMediaProductionRelationshipDTO.setRelationshipType(myMediaProductionRelationshipLocal.getRelationshipType());
        
        // set up people
        Set people = myMediaProductionRelationshipLocal.getPeople();
        Set myPeopleDTOs = new HashSet();
        Iterator peopleIt = people.iterator();
        while (peopleIt.hasNext()) {
            PersonLocal currentPerson = (PersonLocal) peopleIt.next();
            myPeopleDTOs.add(new PersonDTOAssembler(currentPerson).assembleDTO());
        }
        myMediaProductionRelationshipDTO.setPeople(myPeopleDTOs);
        
        // set up organisations
        Set organisations = myMediaProductionRelationshipLocal.getOrganisations();
        Set myOrganisationDTOs = new HashSet();
        Iterator organisationsIt = organisations.iterator();
        while (organisationsIt.hasNext()) {
            OrganisationLocal currentOrg = (OrganisationLocal) organisationsIt.next();
            myOrganisationDTOs.add(new OrganisationDTOAssembler(currentOrg).assembleDTO());
        }
        myMediaProductionRelationshipDTO.setOrganisations(myOrganisationDTOs);
        
        return myMediaProductionRelationshipDTO;
    }
}
