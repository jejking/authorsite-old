/*
 * PersonPublicGetDetailsAction.java
 *
 * Created on 24 January 2003, 16:14
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/PersonPublicGetDetailsAction.java,v 1.3 2003/03/01 13:34:13 jejking Exp $
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
public class PersonPublicGetDetailsAction extends BibAbstractAction {
    
    /** Creates a new instance of PersonPublicGetDetailsAction */
    public PersonPublicGetDetailsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int personID = Integer.parseInt(((PersonPublicGetDetailsForm)form).getPersonID());
        
        // get reference to PersonRemote interface
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PersonAndOrganisationPublicSearchFacadeHome home = (PersonAndOrganisationPublicSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationPublicSearchFacadeEJB",
                    PersonAndOrganisationPublicSearchFacadeHome.class);
        PersonAndOrganisationPublicSearchFacade facade = home.create();
        
        PersonDTO foundPerson = facade.findPersonByPersonID(personID);
        
        if (foundPerson == null) {
            return mapping.findForward("nothingFound");
        }
        
        request.setAttribute("CurrentPersonDTO", foundPerson);
        return mapping.findForward("gotDetails");
    }
    
}
