/*
 * PublishMediaItemAction.java
 *
 * Created on 27 February 2003, 17:57
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/PublishMediaItemAction.java,v 1.1 2003/03/01 13:33:55 jejking Exp $
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
import org.authorsite.bib.exceptions.*;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.EJBHomeFactory;
/**
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class PublishMediaItemAction extends BibAbstractAction {
    
    /** Creates a new instance of PublishMediaItemAction */
    public PublishMediaItemAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // all we need to do is grab the ActiveMediaItem from the session
        HttpSession session = request.getSession();
        MediaItemDTO activeDTO= (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        
        if (activeDTO == null) {
            return mapping.findForward("publicationFailed");
        }
        
        int activeID = activeDTO.getID().intValue();
        
        // get reference to MediaItemManagementFacade
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        MediaItemManagementFacadeHome home = (MediaItemManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/MediaItemManagementFacadeEJB", MediaItemManagementFacadeHome.class);
        MediaItemManagementFacade facade = home.create();
        
        try {
            facade.publishMediaItem(activeID);
        }
        catch (InsufficientDetailException ide) {
            ActionErrors errors = new ActionErrors();
            ActionError newError = new ActionError(ide.getMessage());
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            saveErrors(request, errors);
            return mapping.findForward("publicationFailed");
        }
        return mapping.findForward("itemPublished");
        
    }
    
}
