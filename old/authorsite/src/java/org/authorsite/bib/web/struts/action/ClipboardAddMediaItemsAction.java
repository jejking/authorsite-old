/*
 * ClipboardAddMediaItemsAction.java
 *
 * Created on 25 February 2003, 16:38
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardAddMediaItemsAction.java,v 1.1 2003/03/01 13:33:55 jejking Exp $
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
 * @version $Revision: 1.1 $
 */
public class ClipboardAddMediaItemsAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardAddMediaItemsAction */
    public ClipboardAddMediaItemsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract variables from ActionForm
        String mediaItemIDStrings[] = ((MediaItemsForm)form).getMediaItemID();
        
        // now, we need to have all those strings as integers
        int mediaItemIDints[] = new int[mediaItemIDStrings.length];
        for (int i = 0; i < mediaItemIDStrings.length; i++) {
            mediaItemIDints[i] = Integer.parseInt(mediaItemIDStrings[i]);
        }
        
        // get reference to remote interface of UnpublishedMediaItemSearchFacadeEJB
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        UnpublishedMediaItemSearchFacadeHome home = (UnpublishedMediaItemSearchFacadeHome) ejbHomeFactory.lookupHome("ejb/UnpublishedMediaItemSearchFacadeEJB", UnpublishedMediaItemSearchFacadeHome.class);
        UnpublishedMediaItemSearchFacade facade = home.create();
        
        // now, call findOrganisations method
        Collection MediaItemDTOs = facade.findMediaItemsByMediaItemID(mediaItemIDints);
        
        // these now need to be put into a map
        HashMap MediaItemDTOsMap = new HashMap();
        Iterator mediaItemDTOsIt = MediaItemDTOs.iterator();
        while (mediaItemDTOsIt.hasNext()) {
            MediaItemDTO mediaItemDTO = (MediaItemDTO) mediaItemDTOsIt.next();
            MediaItemDTOsMap.put(mediaItemDTO.getMediaItemID(), mediaItemDTO);
        }
        
        // ok, we have the DTOs we want (they should have been cached by the EJB container, so we shouldn't have hit the database again)
        // now, do already have a session attribute called "MediaItemsClipboardMap"??
        HttpSession session = request.getSession();
        HashMap existingMediaItemsClipboardMap = (HashMap) session.getAttribute("MediaItemsClipboardMap");
        if (existingMediaItemsClipboardMap == null) {
            session.setAttribute("MediaItemsClipboardMap", MediaItemDTOsMap);
        }
        else {
            // already have a clipboard with a map of orgs in the session
            // so add our new ones to it
            existingMediaItemsClipboardMap.putAll(MediaItemDTOsMap);
        }
        return mapping.findForward("mediaItemsAdded");
        
    }
}
