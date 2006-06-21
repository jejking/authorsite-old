/*
 * ClipboardRemoveOrgsAction.java
 *
 * Created on 05 February 2003, 18:17
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardRemoveOrgsAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
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
/**
 * Removes specified OrganisationDTOs from OrgsClipboardCollectionBean.
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class ClipboardRemoveOrgsAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardRemoveOrgsAction */
    public ClipboardRemoveOrgsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract data from form
        String orgIDStrings[] = ((ClipboardOrgsForm)form).getOrgID();
        
        HttpSession session = request.getSession();
        HashMap existingOrgsClipboardMap = (HashMap) session.getAttribute("OrgsClipboardMap");
        
        // check we've got the OrgsClipboardMap in the session
        if (existingOrgsClipboardMap == null) {
            return mapping.findForward("noOrgsClipboard");
        }
        else {
            // iterate through the orgIDStrings array and remove these from the HashMap
            for (int i = 0; i < orgIDStrings.length; i++) {
                existingOrgsClipboardMap.remove(new Integer(orgIDStrings[i]));
            }
            
            // if the peopleCollection is now of size 0, remove it from the session
            if (existingOrgsClipboardMap.size() == 0) {
                session.removeAttribute("OrgsClipboardMap");
            }
            return mapping.findForward("orgsRemoved");
        }
    }
}