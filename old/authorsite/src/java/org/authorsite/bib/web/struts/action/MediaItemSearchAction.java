/*
 * MediaItemManagementSearchAction.java
 *
 * Created on 24 February 2003, 23:00
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/MediaItemSearchAction.java,v 1.1 2003/03/01 13:33:55 jejking Exp $
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
import java.lang.reflect.*;
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
 * @version $Revision: 1.1 $
 */
public class MediaItemSearchAction extends BibAbstractAction {
    
    /** Creates a new instance of MediaItemManagementSearchAction */
    public MediaItemSearchAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract values from the form 
        String personID = ((MediaItemSearchForm)form).getPersonID();
        String organisationID = ((MediaItemSearchForm)form).getOrganisationID();
        String productionRelationship = ((MediaItemSearchForm)form).getProductionRelationship();
        String title = ((MediaItemSearchForm)form).getTitle();
        boolean titleLikeFlag = ((MediaItemSearchForm)form).getTitleLikeFlag();
        String mediaType = ((MediaItemSearchForm)form).getMediaType();
        String iso639 = ((MediaItemSearchForm)form).getIso639();
        String year = ((MediaItemSearchForm)form).getYear();
        String secondYear = ((MediaItemSearchForm)form).getSecondYear();
        String yearOperation = ((MediaItemSearchForm)form).getYearOperation();
        boolean searchUnpublishedFlag = ((MediaItemSearchForm)form).getSearchUnpublishedFlag();
        
        // this is the "clever" bit. We look at which fields have been set and construct a method name in a string buffer,
        // adding method arguments as we go. This only works because of the systematic naming convention in 
        // PublishedMediaItemSearchFacadeBean. Although we have a slight runtime overhead from the reflection, it is much
        // more convenient to code than lots and lots of if/else blocks.
        StringBuffer methodToCall = new StringBuffer();
        methodToCall.append("findBy");
        ArrayList args = new ArrayList();
        ArrayList argClasses = new ArrayList(); // we use this to find the method on the facade using Class.getMethod( ... )
        
        if (personID != null && !personID.equals("")) {
            methodToCall.append("Person");
            args.add(new Integer(personID));
            argClasses.add(int.class);
        }
        
        if (organisationID != null && !organisationID.equals("")) {
            methodToCall.append("Organisation");
            args.add(new Integer(organisationID));
            argClasses.add(int.class);
        }
        
        if (productionRelationship != null && !productionRelationship.equals("")) {
            methodToCall.append("Relationship");
            args.add(productionRelationship);
            argClasses.add(String.class);
        }
        
        if (title != null && !title.equals("")) {
            if (!titleLikeFlag) {
                methodToCall.append("Title");
            }
            else {
                methodToCall.append("LikeTitle");
            }
            args.add(title);
            argClasses.add(String.class);
        }
        
        if (mediaType != null && !mediaType.equals("")) {
            methodToCall.append("Type");
            args.add(mediaType);
            argClasses.add(String.class);
        }
        
        if (iso639 != null && !iso639.equals("")) {
            methodToCall.append("Language");
            args.add(iso639);
            argClasses.add(String.class);
        }
        
        if (year != null && !year.equals("")) {
            // now, what year operation do we want to carry out?
            if (yearOperation != null && !yearOperation.equals("")) {
                methodToCall.append(yearOperation); // it's cunningly already in the correct wording
                args.add(new Integer(year));
                argClasses.add(int.class);
                if (yearOperation.equals("BetweenYears")) {
                    args.add(new Integer(secondYear));
                    argClasses.add(int.class);
                }
            }
        }
        
        // get a reference to the published media item search facade EJB
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        PublishedMediaItemSearchFacadeHome home = (PublishedMediaItemSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PublishedMediaItemSearchFacadeEJB", PublishedMediaItemSearchFacadeHome.class);
        PublishedMediaItemSearchFacade facade = home.create();
        
        System.out.println("Constructed method: " + methodToCall.toString());
        Iterator argsIt = args.iterator();
        while (argsIt.hasNext()) {
            System.out.println("Arg: " + argsIt.next().toString());
        }
        Iterator argClassesIt = argClasses.iterator();
        while (argClassesIt.hasNext()) {
            System.out.println("Arg Class: " + argClassesIt.next().toString());
        }
        
        Class facadeClass = facade.getClass();
        Method finderMethod = facadeClass.getDeclaredMethod(methodToCall.toString(), (Class[]) argClasses.toArray(new Class[argClasses.size()]));
        Collection foundDTOs = (Collection) finderMethod.invoke(facade, args.toArray());
        
        // now, do we have also to look for unpublished items?? If so, call same method on the Unpublished.. facade and add the 
        // results
        if (searchUnpublishedFlag) {
            // get EJB ref for UnpublishedMediaItemSearchFacade
            UnpublishedMediaItemSearchFacadeHome uHome = (UnpublishedMediaItemSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/UnpublishedMediaItemSearchFacadeEJB", UnpublishedMediaItemSearchFacadeHome.class);
            UnpublishedMediaItemSearchFacade uFacade = uHome.create();
            Class uFacadeClass = uFacade.getClass();
            Method uFinderMethod = uFacadeClass.getDeclaredMethod(methodToCall.toString(), (Class[]) argClasses.toArray(new Class[argClasses.size()]));
            Collection uFoundDTOs = (Collection) uFinderMethod.invoke(uFacade, args.toArray());        
            foundDTOs.addAll(uFoundDTOs);
        }
        
        request.setAttribute("mediaItemsCollection", foundDTOs);
                
        if (foundDTOs.size() == 0) {
            
            // finally, if nothing is found, we will need to reset the "CurrentOrgDTO" or "CurrentPersonDTO"
            // as a request attribute if we are forwarding back to the orgDetailsResults.jsp or personDetailsResults.jsp
            
            if ((personID != null && !personID.equals("")) || (organisationID != null && !organisationID.equals(""))) {
                 PersonAndOrganisationPublicSearchFacadeHome poHome = (PersonAndOrganisationPublicSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/PersonAndOrganisationPublicSearchFacadeEJB",
                    PersonAndOrganisationPublicSearchFacadeHome.class);
                PersonAndOrganisationPublicSearchFacade poFacade = poHome.create();
                
                // person??
                if (personID != null && !personID.equals("")) {
                    PersonDTO personDTO = poFacade.findPersonByPersonID(Integer.parseInt(personID));
                    request.setAttribute("CurrentPersonDTO", personDTO);
                }
                else if (organisationID != null && !organisationID.equals("")) {
                    OrganisationDTO orgDTO = poFacade.findOrganisationByOrganisationID(Integer.parseInt(organisationID));
                    request.setAttribute("CurrentOrgDTO", orgDTO);
                }
            }
            return mapping.findForward("nothingFound");
        }
        else {
            return mapping.findForward("itemsFound");
        }
    }
    
}
