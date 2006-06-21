/*
 * ClipboardAddOrgsAction.java
 *
 * Created on 05 February 2003, 20:46
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardAddOrgsAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
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
public class ClipboardAddOrgsAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardAddOrgsAction */
    public ClipboardAddOrgsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // extract variables from ActionForm
        String orgIDStrings[] = ((ClipboardOrgsForm)form).getOrgID();
        
        // now, we need to have all those strings as integers
        int orgIDints[] = new int[orgIDStrings.length];
        for (int i = 0; i < orgIDStrings.length; i++) {
            orgIDints[i] = Integer.parseInt(orgIDStrings[i]);
        }
        
        // get reference to remote interface of PersonAndOrganisationManagementSearchFacade
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PersonAndOrganisationManagementSearchFacadeHome home = (PersonAndOrganisationManagementSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationManagementSearchFacadeEJB", PersonAndOrganisationManagementSearchFacadeHome.class);
        PersonAndOrganisationManagementSearchFacade facade = home.create();
        
        // now, call findOrganisations method
        Collection OrganisationDTOs = facade.findOrganisations(orgIDints);
        
        // these now need to be put into a map
        HashMap OrganisationDTOsMap = new HashMap();
        Iterator orgDTOsIt = OrganisationDTOs.iterator();
        while (orgDTOsIt.hasNext()) {
            OrganisationDTO orgDTO = (OrganisationDTO) orgDTOsIt.next();
            OrganisationDTOsMap.put(orgDTO.getID(), orgDTO);
        }
        
        // ok, we have the DTOs we want (they should have been cached by the EJB container, so we shouldn't have hit the database again)
        // now, do already have a session attribute called "OrgsClipboardMap"??
        HttpSession session = request.getSession();
        HashMap existingOrgsClipboardMap = (HashMap) session.getAttribute("OrgsClipboardMap");
        if (existingOrgsClipboardMap == null) {
            session.setAttribute("OrgsClipboardMap", OrganisationDTOsMap);
        }
        else {
            // already have a clipboard with a map of orgs in the session
            // so add our new ones to it
            existingOrgsClipboardMap.putAll(OrganisationDTOsMap);
        }
        return mapping.findForward("orgsAdded");
    }
    
}
