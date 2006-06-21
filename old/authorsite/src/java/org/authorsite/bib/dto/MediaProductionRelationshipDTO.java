/*
 * MediaProductionRelationshipDTO.java
 *
 * Created on 20 November 2002, 09:29
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/MediaProductionRelationshipDTO.java,v 1.2 2003/03/01 13:37:36 jejking Exp $
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

package org.authorsite.bib.dto;
import java.io.*;
import java.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaProductionRelationshipDTO extends AbstractDTO {
    
    private Integer mediaProductionRelationshipID;
    private String relationshipType;
    private Set people = new HashSet();
    private Set organisations = new HashSet();
    
    /** Creates a new instance of MediaProductionRelationshipDTO */
    public MediaProductionRelationshipDTO() {
    }
    
    public MediaProductionRelationshipDTO(Integer newMediaProductionRelationshipID) {
        setMediaProductionRelationshipID(newMediaProductionRelationshipID);
    }
    
    public Integer getID() {
        return mediaProductionRelationshipID;
    }
    
    public Integer getMediaProductionRelationshipID() {
        return mediaProductionRelationshipID;
    }
    
    public void setMediaProductionRelationshipID(Integer newMediaProductionRelationshipID) {
        mediaProductionRelationshipID = newMediaProductionRelationshipID;
    }
    
    public String getRelationshipType() {
        return relationshipType;
    }
    
    public void setRelationshipType(String newRelationshipType) {
        relationshipType = newRelationshipType;
    }
    
    public Set getPeople() {
        return people;
    }
    
    public void setPeople(Set newPeople) {
        // check we've got exclusively PersonDTO objects here
        Iterator it = newPeople.iterator();
        try {
            while (it.hasNext()) {
                PersonDTO currentPerson =  (PersonDTO) it.next();
            }
            people = newPeople;
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("set newPeople may only contain PersonDTO objects");
        }
    }
    
    public Set getOrganisations() {
        return organisations;
    }
    
    public void setOrganisations (Set newOrganisations) {
        Iterator it = newOrganisations.iterator();
        try {
            while (it.hasNext()) {
                OrganisationDTO currentOrg = (OrganisationDTO) it.next();
            }
            organisations = newOrganisations;
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("set newOrganisations may only contain OrganisationDTO objects");
        }
    }
        
}