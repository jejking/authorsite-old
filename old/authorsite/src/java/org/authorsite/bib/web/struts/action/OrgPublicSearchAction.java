/*
 * OrgPublicSearchAction.java
 *
 * Created on 28 January 2003, 16:27
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/OrgPublicSearchAction.java,v 1.2 2003/03/01 13:34:12 jejking Exp $
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
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class OrgPublicSearchAction extends BibAbstractAction {
    
    /** Creates a new instance of OrgPublicSearchAction */
    public OrgPublicSearchAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract variables from ActionForm
        String name = ((OrgPublicSearchForm)form).getName();
        String city = ((OrgPublicSearchForm)form).getCity();
        String country = ((OrgPublicSearchForm)form).getCountry();
        boolean likeFlag = ((OrgPublicSearchForm)form).getLikeFlag();
        
        boolean nameSet = false;
        boolean citySet = false;
        boolean countrySet = false;
        
        if (name.length() > 0) {
            nameSet = true;
            System.out.println("setting nameSet to true");
        }
        
        if (city.length() > 0) {
            citySet = true;
            System.out.println("setting citySet to true");
        }
        
        if (country.length() > 0) {
            countrySet = true;
            System.out.println("Setting countrySet to true");
        }
        
        Collection foundOrgs = null;
        
        // get ref to remote EJB interface
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PersonAndOrganisationPublicSearchFacadeHome home = (PersonAndOrganisationPublicSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationPublicSearchFacadeEJB",
                    PersonAndOrganisationPublicSearchFacadeHome.class);
        PersonAndOrganisationPublicSearchFacade facade = home.create();
        
        if (likeFlag) {
            // if only name is filled in...
            boolean x = nameSet && (!citySet && !countrySet);
            System.out.println(x);
            if (nameSet && (!citySet && !countrySet)) {
                System.out.println("calling findOrganisationLikeName() " + name);
                foundOrgs = facade.findOrganisationLikeName(name);
            }
            // name and city
            else if (!countrySet) {
                System.out.println("calling findOrganisationByLikeNameAndCity() " + name + " " + city);
                foundOrgs = facade.findOrganisationByLikeNameAndCity(name, city);
            }
            // name and country
            else if (!citySet) {
                System.out.println("calling findOrganisationByLikeNameAndCountry() " + name + " " + country);
                foundOrgs = facade.findOrganisationByLikeNameAndCountry(name, country);
            }
            else {
                System.out.println("calling findOrganisationByLikeNameCityCountry() " + name + " " + city + " " + country);
                foundOrgs = facade.findOrganisationByLikeNameCityCountry(name, city, country);
            }
        }
        // like flag not set.
        else {
            // if only name is filled in...
            if (nameSet && (!citySet && !countrySet)) {
                System.out.println("calling findOrganisationByName() " + name);
                foundOrgs = facade.findOrganisationByName(name);
            }
            // name and city
            else if (!countrySet) {
                System.out.println("calling findOrganisationByNameAndCity() " + name + " " + city);
                foundOrgs = facade.findOrganisationByNameAndCity(name, city);
            }
            // name and country
            else if (!citySet) {
                System.out.println("calling findOrganisationByNameAndCountry() " + name + " " + country);
                foundOrgs = facade.findOrganisationByNameAndCountry(name, country);
            }
            else {
                System.out.println("calling findOrganisationByNameAndCountry() " + name + " " + city + " " + country);
                foundOrgs = facade.findOrganisationByNameCityCountry(name, city, country);
            }
        }
        
        // now, have we got anything back from the EJB?
        if (foundOrgs.size() == 0) {
            ActionErrors errors = new ActionErrors();
            ActionError error = new ActionError("web.errors.orgSearch.nothingFound");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
            saveErrors(request, errors);
            return mapping.findForward("nothingFound");
        }
        // else, send user to results display form, forwarding the Collection of DTOs
        else {
            CollectionBean cb = new CollectionBean();
            cb.setCollection(foundOrgs);
            request.setAttribute("OrgsFoundCollectionBean", cb);
            return mapping.findForward("orgsFound");
        }
        
    }
    
}
