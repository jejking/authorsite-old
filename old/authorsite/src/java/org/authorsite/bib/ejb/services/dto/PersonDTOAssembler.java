/*
 * PersonDTOAssembler.java
 *
 * Created on 20 November 2002, 11:55
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/services/dto/PersonDTOAssembler.java,v 1.1 2003/03/29 12:49:01 jejking Exp $
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
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.dto.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class PersonDTOAssembler {
    
    private PersonLocal myPersonLocal;
    private PersonDTO myPersonDTO;
    
    /** Creates a new instance of PersonDTOAssembler */
    public PersonDTOAssembler(PersonLocal newPersonLocal) {
        myPersonLocal = newPersonLocal;
        myPersonDTO = new PersonDTO(myPersonLocal.getPersonID());
    }
    
    public PersonDTO assembleDTO() {
        myPersonDTO.setMainName(myPersonLocal.getMainName());
        myPersonDTO.setGivenName(myPersonLocal.getGivenName());
        myPersonDTO.setOtherNames(myPersonLocal.getOtherNames());
        myPersonDTO.setPrefix(myPersonLocal.getPrefix());
        myPersonDTO.setSuffix(myPersonLocal.getSuffix());
        myPersonDTO.setGenderCode(myPersonLocal.getGenderCode());
        return myPersonDTO;
    }
}
