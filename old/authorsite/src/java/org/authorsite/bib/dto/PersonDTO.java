/*
 * PersonDTO.java
 *
 * Created on 20 November 2002, 09:31
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/PersonDTO.java,v 1.2 2003/03/01 13:37:36 jejking Exp $
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
public class PersonDTO extends AbstractDTO {
    
    private Integer personID;
    private String mainName;
    private String givenName;
    private String otherNames;
    private String prefix;
    private String suffix;
    private int genderCode;
    
    /** Creates a new instance of PersonDTO */
    public PersonDTO() {
    }
    
    public PersonDTO(Integer newPersonID) {
        personID = newPersonID;
    }
    
    public Integer getID() {
        return personID;
    }
    
    public Integer getPersonID() {
        return personID;
    }
    
    public void setPersonID(Integer newPersonID) {
        personID = newPersonID;
    }
    
    public String getMainName() {
        return mainName;
    }
    
    public void setMainName(String newMainName) {
        mainName = newMainName;
    }
    
    public String getGivenName() {
        return givenName;
    }
    
    public void setGivenName(String newGivenName) {
        givenName = newGivenName;
    }
    
    public String getOtherNames() {
        return otherNames;
    }
    
    public void setOtherNames(String newOtherNames) {
        otherNames = newOtherNames;
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String newPrefix) {
        prefix = newPrefix;
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String newSuffix) {
        suffix = newSuffix;
    }
    
    public int getGenderCode() {
        return genderCode;
    }
    
    public void setGenderCode(int newGenderCode) {
        genderCode = newGenderCode;
    }
    
}
