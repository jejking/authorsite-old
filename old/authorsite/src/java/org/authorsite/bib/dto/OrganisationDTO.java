/*
 * OrganisationDTO.java
 *
 * Created on 20 November 2002, 09:30
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/OrganisationDTO.java,v 1.2 2003/03/01 13:37:36 jejking Exp $
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
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class OrganisationDTO extends AbstractDTO {
    
    private Integer organisationID;
    private String name;
    private String city;
    private String country;
        
    /** Creates a new instance of OrganisationDTO */
    public OrganisationDTO() {
    }
    
    public OrganisationDTO(Integer newOrganisationID) {
        organisationID = newOrganisationID;
    }
    
    public Integer getID() {
        return organisationID;
    }
    
    public Integer getOrganisationID() {
        return organisationID;
    }
    
    public void setOrganisationID(Integer newOrganisationID) {
        organisationID = newOrganisationID;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String newCity) {
        city = newCity;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String newCountry) {
        country = newCountry;
    }
    
}
