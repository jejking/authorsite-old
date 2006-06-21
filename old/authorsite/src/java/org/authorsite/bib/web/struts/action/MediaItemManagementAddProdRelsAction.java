/*
 * MediaItemManagementAddProdRelsAction.java
 *
 * Created on 15 February 2003, 22:56
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/MediaItemManagementAddProdRelsAction.java,v 1.3 2003/03/29 12:59:03 jejking Exp $
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
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.EJBHomeFactory;

/**
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class MediaItemManagementAddProdRelsAction extends BibAbstractAction {
    
    private final int PEOPLE = 1;
    private final int ORGS = 2;
    
    /** Creates a new instance of MediaItemManagementAddProdRelsAction */
    public MediaItemManagementAddProdRelsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        int prodRelPK = 0;
        MediaProductionRelationshipDTO mprDTO = null;
        
        // extract values from the form, convert arrays to Sets of Integer objects
        String[] personID = ((ProdRelsForm)form).getPersonID();
        String[] orgID = ((ProdRelsForm)form).getOrgID();
        String productionRelationship = ((ProdRelsForm)form).getProductionRelationship();
        boolean finishedFlag = ((ProdRelsForm)form).getFinishedFlag();
        System.out.println("obtained variables from form");
        
        // get reference to MediaItemManagementFacade
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        MediaItemManagementFacadeHome home = (MediaItemManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/MediaItemManagementFacadeEJB", MediaItemManagementFacadeHome.class);
        MediaItemManagementFacade facade = home.create();
        System.out.println("obtained ejb ref");
        
        // get reference to the ActiveMediaItem. We keep track of the state of the item we are editing here
        HttpSession session = request.getSession();
        MediaItemDTO activeMediaItem = (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        System.out.println("got active media item");
        
        // check to see if we know that the production relationship submitted exists already for the active media item
        Set activeMediaItemProdRels = activeMediaItem.getMediaProductionRelationships();
        if (activeMediaItemProdRels == null) {
            activeMediaItemProdRels = new HashSet();
        }
        
        // if the item has NO production relationships, create a new one and add everything in personID and orgID to it
        if (activeMediaItemProdRels.size() == 0) {
            System.out.println("there are no production relationships for the active media item dto");
            prodRelPK = createNewProductionRelationship(facade, activeMediaItem.getID().intValue(),  productionRelationship);
            mprDTO = new MediaProductionRelationshipDTO(new Integer(prodRelPK));
            mprDTO.setRelationshipType(productionRelationship);
            System.out.println("created new production relationship, " + prodRelPK);
        }
        else { // the item has SOME production relationships. does it have the one specified ??
            boolean relationshipExists = false;
            System.out.println("this media item dto does apparently have some production relationships");
            Iterator it = activeMediaItemProdRels.iterator();
            while (it.hasNext()) {
                mprDTO = (MediaProductionRelationshipDTO) it.next();
                if (productionRelationship.equals(mprDTO.getRelationshipType())) {
                    relationshipExists = true;
                    System.out.println("This media item dto has the production relationship submitted in the form");
                    break;
                }
            }
            
            if (!relationshipExists) { // we didn't find it. make a new one.
                System.out.println("Although there are some prod rels, there isnt this one. Making it");
                prodRelPK = createNewProductionRelationship(facade, activeMediaItem.getID().intValue(),  productionRelationship);
                mprDTO = new MediaProductionRelationshipDTO(new Integer(prodRelPK));
                mprDTO.setRelationshipType(productionRelationship);
                System.out.println("Created new production relationship," + prodRelPK);
            }
            else { // we've got it, get its PK
                prodRelPK = mprDTO.getID().intValue();
                System.out.println("We found the prod rel already existing. Its PK is " + prodRelPK);
            }
        }
        
        
        if (personID.length > 0) {
                System.out.println("about to call updateEJBWithPeople");
                updateEJBWithPeople(facade, prodRelPK, removeDuplicates(personID, mprDTO.getPeople()));
                System.out.println("about to call updateActiveMediaItemWithPeople");
                updateActiveMediaItemWithPeople(mprDTO, personID, session);   
            }
        
        if (orgID.length > 0) {
            System.out.println("about to call updateEJBWithOrgs");
                updateEJBWithOrgs(facade, prodRelPK, removeDuplicates(orgID, mprDTO.getOrganisations()));
                System.out.println("About to call updateActiveMEdiaItemWithORgs");
                updateActiveMediaItemWithOrgs(mprDTO, orgID, session);
        }
        
        System.out.println("about to add MediaItemProductionRelationshipDTO to activeMediaItemProdRels");
        activeMediaItemProdRels.add(mprDTO);
        System.out.println("about to add activeMediaItemProdRels to active media item");
        activeMediaItem.setMediaProductionRelationships(activeMediaItemProdRels);
    
        // ok, by now we have set everything up on the EJB side and syncced it the ActiveMediaItem
        if (finishedFlag) {
            return mapping.findForward("addIMRs");
        }
        else {
            return mapping.findForward("addMoreProdRels");
        }
    }
    
    /*
     * BUNCH OF UTILITY METHODS
     */ 
    
    private int createNewProductionRelationship(MediaItemManagementFacade facade, int activeMediaItemPK, String productionRelationship) throws Exception {
        return facade.addMediaProductionRelationship(activeMediaItemPK, productionRelationship);
    }
    
    private void updateEJBWithPeople(MediaItemManagementFacade facade, int prodRelPK, String[] personID) throws Exception {
        
        for (int i = 0; i < personID.length; i++) {
            
            facade.addPersonToProductionRelationship(prodRelPK, Integer.parseInt(personID[i]));
        }
    }
    
    private void updateEJBWithOrgs(MediaItemManagementFacade facade, int prodRelPK, String[] orgID) throws Exception {
        
        for (int i = 0; i < orgID.length; i++) {
            facade.addOrganisationToProductionRelationship(prodRelPK, Integer.parseInt(orgID[i]));
        }
    }
        
    private void updateActiveMediaItemWithPeople(MediaProductionRelationshipDTO dto, String[] personID, HttpSession session) {
        HashMap peopleClipboardMap = (HashMap) session.getAttribute("PeopleClipboardMap");
        
        // no point trying to get things if there aren't any people on the clipboard..
        if (peopleClipboardMap == null) {
            return;
        }
        // iterate through the set. this gives Integer keys which we can use to get the PersonDTO references. add these to the
        // dto, which is the one that belongs to the ActiveMediaItem
        Set dtoPeople = dto.getPeople();
        for (int i = 0; i < personID.length; i++) {
            PersonDTO personDTO = (PersonDTO) peopleClipboardMap.get(new Integer(personID[i]));
            dtoPeople.add(personDTO);
            System.out.println("added personDTO to active media item: " + personDTO.getMainName());
        }
    }
    
    private void updateActiveMediaItemWithOrgs(MediaProductionRelationshipDTO dto, String[] orgID, HttpSession session) {
        
        HashMap orgsClipboardMap = (HashMap) session.getAttribute("OrgsClipboardMap");
        
        if (orgsClipboardMap == null) {
            return;
        }
        
        Set dtoOrgs = dto.getOrganisations();
        for (int i = 0; i < orgID.length; i++) {
            OrganisationDTO orgDTO = (OrganisationDTO) orgsClipboardMap.get(new Integer(orgID[i]));
            dtoOrgs.add(orgDTO);
        }
    }
    
    private String[] removeDuplicates(String[] array, Set existing) {
        HashSet noDuplicates = new HashSet(Arrays.asList(array));
        Iterator existingIt = existing.iterator();
        while (existingIt.hasNext()) {
            AbstractDTO dto = (AbstractDTO) existingIt.next();
            if (noDuplicates.contains(dto.getID().toString())) {
                noDuplicates.remove(dto.getID().toString());
            }
        }
        System.out.println("removed duplicates");
        return (String[]) noDuplicates.toArray(new String[noDuplicates.size()]);
    }
}
