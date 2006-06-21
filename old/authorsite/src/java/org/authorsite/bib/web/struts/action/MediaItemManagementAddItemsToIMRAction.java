/*
 * MediaItemManagementAddItemsToIMRAction.java
 *
 * Created on 27 February 2003, 10:45 
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/MediaItemManagementAddItemsToIMRAction.java,v 1.2 2003/03/29 12:58:17 jejking Exp $
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
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemManagementAddItemsToIMRAction extends BibAbstractAction {
    
    /** Creates a new instance of MediaItemManagementAddItemsToIMRAction */
    public MediaItemManagementAddItemsToIMRAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // obtain variables from form
        String intraMediaRelationship = ((IMRSelectForm)form).getIntraMediaRelationship();
        String[] mediaItemIDStrings = ((IMRSelectForm)form).getMediaItemID();
        
        // get the ID of the current Active Media Item
        HttpSession session = request.getSession();
        MediaItemDTO activeDTO = (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        int fromID = activeDTO.getID().intValue();
        
        System.out.println("got active media item");
        Map dtoChildren = activeDTO.getChildren();
        // get the List of child IMRs of the submitted type that are already attached to the ActiveMediaItem
        List childrenOfType = (List) dtoChildren.get(intraMediaRelationship);
        int[] childrenIDs = new int[0];
        // from these children, we will need the IDs. For speed, we'll make a sorted array of ints
        if (childrenOfType != null) {
            System.out.println("we have children of type " + intraMediaRelationship);
            Iterator childrenOfTypeIt = childrenOfType.iterator();
            childrenIDs = new int[childrenOfType.size()];
            int i = 0;
            while (childrenOfTypeIt.hasNext()) {
                MediaItemDTO currentDTO = (MediaItemDTO) childrenOfTypeIt.next();
                childrenIDs[i] = currentDTO.getID().intValue();
                i++;
            }
            Arrays.sort(childrenIDs); // ok, that's sorted
        }
        else {
            childrenOfType = new ArrayList();
            System.out.println("made new array list for children of type, added under: " + intraMediaRelationship);
        }
        
        // get the MediaItemsClipboardMap from the session. we use references from this to update the ActiveMediaItemDTO
        HashMap mediaItemsClipboardMap = (HashMap) session.getAttribute("MediaItemsClipboardMap");
        System.out.println("got clipboard media items");
        // get ref to MediaItemManagementFacade EJB
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        MediaItemManagementFacadeHome home = (MediaItemManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/MediaItemManagementFacadeEJB", MediaItemManagementFacadeHome.class);
        MediaItemManagementFacade facade = home.create();
        System.out.println("got ejb ref");
        // now, iterate through the array of mediaItemIDs.
        for (int j = 0; j < mediaItemIDStrings.length; j++) {
            
            int currentID = Integer.parseInt(mediaItemIDStrings[j]);
            
            // check we don't already have a similar IMR, if we do, skip it and continue the iteration
            if (Arrays.binarySearch(childrenIDs, currentID) > -1) {
                continue;
            }
            
            // if we don't, attempt to create it on the facade
            facade.createIntraMediaRelationship(fromID, intraMediaRelationship, currentID);
            System.out.println("created IMR in EJBs");
            // having created it, update the active media item's list of children of this IMR type with it
            childrenOfType.add(mediaItemsClipboardMap.get(new Integer(currentID)));
            System.out.println("created IMR in active media item");
        }
        
        // if we did not have a list of child IMRs of type at the start (i.e. childrenIDs was left as a zero element array)
        // then we need to add the ArrayList we built up whilst iterating above to the children Map of the Active Media Item
        if (childrenIDs.length == 0) {
            dtoChildren.put(intraMediaRelationship, childrenOfType);
        }
        
        Set dtoChildrenKeys = dtoChildren.keySet();
        Iterator dtoChildrenKeysIt = dtoChildrenKeys.iterator();
        while (dtoChildrenKeysIt.hasNext()) {
            System.out.println("Dto has child key: " + dtoChildrenKeysIt.next());
        }
        
        // by now the EJBs should be updated and the ActiveMediaItem synced
        return mapping.findForward("itemsAddedToIMR");
        
    }
}
