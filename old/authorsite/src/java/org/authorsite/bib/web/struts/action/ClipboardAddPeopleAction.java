/*
 * ClipboardAddPeopleAction.java
 *
 * Created on 04 February 2003, 21:09
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardAddPeopleAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
 *
 * Copyright (C) 2003  John King
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

package org.authorsite.bib.web.struts.action;
import java.util.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.EJBHomeFactory;
/**
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class ClipboardAddPeopleAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardAddPeopleAction */
    public ClipboardAddPeopleAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // extract variables from ActionForm
        String personIDStrings[] = ((ClipboardAddPeopleForm)form).getPersonID();
        
        // now, we need to have all those strings as integers
        int personIDints[] = new int[personIDStrings.length];
        for (int i = 0; i < personIDStrings.length; i++) {
            personIDints[i] = Integer.parseInt(personIDStrings[i]);
        }
        
        // get reference to remote interface of PersonAndOrganisationManagementSearchFacade
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PersonAndOrganisationManagementSearchFacadeHome home = (PersonAndOrganisationManagementSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationManagementSearchFacadeEJB", PersonAndOrganisationManagementSearchFacadeHome.class);
        PersonAndOrganisationManagementSearchFacade facade = home.create();
        
        Collection peopleDTOs = facade.findPeople(personIDints);
        
        // these now need to be put into a map
        HashMap peopleDTOsMap = new HashMap();
        Iterator peopleDTOsIt = peopleDTOs.iterator();
        while (peopleDTOsIt.hasNext()) {
            PersonDTO personDTO = (PersonDTO) peopleDTOsIt.next();
            peopleDTOsMap.put(personDTO.getID(), personDTO);
        }
        
        // ok, we have the DTOs we want (they should have been cached by the EJB container, so we shouldn't have hit the database again)
        // now, do already have a session attribute called "PeopleClipboardMap"??
        HttpSession session = request.getSession();
        HashMap existingPeopleClipboardMap = (HashMap) session.getAttribute("PeopleClipboardMap");
        if (existingPeopleClipboardMap == null) {
            session.setAttribute("PeopleClipboardMap", peopleDTOsMap);
        }
        else {
            // already have a clipboard with a map of orgs in the session
            // so add our new ones to it
            existingPeopleClipboardMap.putAll(peopleDTOsMap);
        }
        return mapping.findForward("peopleAdded");
    }
    
}