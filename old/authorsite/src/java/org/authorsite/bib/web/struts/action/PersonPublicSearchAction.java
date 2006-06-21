/*
 * PersonPublicSearchAction.java
 *
 * Created on 08 January 2003, 23:59
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/PersonPublicSearchAction.java,v 1.2 2003/03/01 13:34:13 jejking Exp $
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
import org.authorsite.bib.web.util.EJBHomeFactory;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
/**
 * Executes publicly available searches for people.
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PersonPublicSearchAction extends BibAbstractAction {
    
    /** Creates a new instance of PersonPublicSearchAction */
    public PersonPublicSearchAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        System.out.println("PersonPublicSearchAction method execute has been called");
        
        // extract variables from ActionForm
        String mainName = ((PersonPublicSearchForm)form).getMainName();
        String givenName = ((PersonPublicSearchForm)form).getGivenName();
        boolean likeFlag = ((PersonPublicSearchForm)form).getLikeFlag();
        
        Collection foundPeople = null;
        
        // get reference to PersonRemote interface
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PersonAndOrganisationPublicSearchFacadeHome home = (PersonAndOrganisationPublicSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationPublicSearchFacadeEJB",
                    PersonAndOrganisationPublicSearchFacadeHome.class);
        PersonAndOrganisationPublicSearchFacade facade = home.create();
        
        // call appropriate finder method on remote interface
        // - if likeFlag is set...
        if (likeFlag) {
            // if we only have main name
            if (givenName == null || givenName.length() == 0) {
                System.out.println("calling findPersonLikeMainName");
                foundPeople = facade.findPersonLikeMainName(mainName);
            }
            else {
                System.out.println("calling findPersonLikeMainNameAndGivenName");
                foundPeople = facade.findPersonLikeMainNameAndGivenName(mainName, givenName);
            }
        }
        else {
            if (givenName == null || givenName.length() == 0) {
                System.out.println("calling findPersonByMainName");
                foundPeople = facade.findPersonByMainName(mainName);
            }
            else {
                System.out.println("calling findPersonByMainNameAndGivenName");
                foundPeople = facade.findPersonByMainNameAndGivenName(mainName, givenName);
            }
        }
        
        // if nothing found, return user to search form
        if (foundPeople.size() == 0) {
            System.out.println("apparently we found nothing");
            ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("web.errors.personSearch.nooneFound");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return mapping.findForward("nothingFound");
        }
        // else, send user to results display form, forwarding the Collection of DTOs
        else {
           
            System.out.println("we found a collection with " + foundPeople.size() + " people");
            CollectionBean cb = new CollectionBean();
            cb.setCollection(foundPeople);
            request.setAttribute("PeopleFoundCollectionBean", cb);
            return mapping.findForward("peopleFound");
        }
    }
}