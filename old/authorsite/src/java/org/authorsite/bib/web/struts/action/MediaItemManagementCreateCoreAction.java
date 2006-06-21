/**
 * MediaItemManagementCreateCoreAction.java
 *
 * Created 07 February 2003
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/MediaItemManagementCreateCoreAction.java,v 1.4 2003/03/01 13:34:12 jejking Exp $
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
 * @version $Revision: 1.4 $
 */
public class MediaItemManagementCreateCoreAction extends BibAbstractAction {
    
    /** Creates a new instance of MediaItemManagementCreateCoreAction */
    public MediaItemManagementCreateCoreAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract variables from form. NOTE - the source for the form does not exist until the build scripts have run!!
        String title = ((MediaItemManagementCreateCoreForm)form).getTitle();
        String mediaType = ((MediaItemManagementCreateCoreForm)form).getMediaType();
        int year = Integer.parseInt(((MediaItemManagementCreateCoreForm)form).getYear());
        String additionalInfo = ((MediaItemManagementCreateCoreForm)form).getAdditionalInfo();
        String comment = ((MediaItemManagementCreateCoreForm)form).getComment();
        
        // get reference to MediaItemManagementFacade
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        MediaItemManagementFacadeHome home = (MediaItemManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/MediaItemManagementFacadeEJB", MediaItemManagementFacadeHome.class);
        MediaItemManagementFacade facade = home.create();
        
        int newPK = facade.createMediaItem(title, mediaType, year, additionalInfo, comment);
              
        // we need to keep a record of the current active media item in the session, so we'll make a local DTO available
        MediaItemDTO activeMediaItem = createActiveMediaItem(title, mediaType, year, additionalInfo, comment);
        activeMediaItem.setMediaItemID(new Integer(newPK));
        HttpSession session = request.getSession();
        session.setAttribute("ActiveMediaItem", activeMediaItem);
        
        // later on in the process we will also need to process the production relationships and IMRs associated with the item
        // so we'll get them now from the RulesFacade Bean and store them in the session
        
        RulesFacadeHome rfHome = (RulesFacadeHome) ejbHomeFactory.lookupHome("ejb/RulesFacadeEJB", RulesFacadeHome.class);
        RulesFacade rf = rfHome.create();
        
        session.setAttribute("ActiveProductionRelationshipsSet", rf.getProductionRelationships(mediaType));
        session.setAttribute("ActiveIntraMediaRelationshipsMap", rf.getIntraMediaRelationships(mediaType));
        
        // now, we need to look at mediaType and use this to determine the actionForward to return so that 
        // the correct jsp is presented to enter the relevant mediaDetails fields...
        // this depends on correct manipulation of the forward elements in struts-config.xml
        return mapping.findForward(mediaType);
    }
    
    private MediaItemDTO createActiveMediaItem(String title, String mediaType, int yearOfCreation, String additionalInfo, String comment) {
        MediaItemDTO dto = new MediaItemDTO();
        dto.setTitle(title);
        dto.setMediaType(mediaType);
        dto.setYearOfCreation(yearOfCreation);
        dto.setAdditionalInfo(additionalInfo);
        dto.setComment(comment);
        return dto;
    }
}