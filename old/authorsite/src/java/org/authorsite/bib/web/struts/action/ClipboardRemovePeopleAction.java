/*
 * ClipboardRemovePeopleAction.java
 *
 * Created on 05 February 2003, 18:17
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardRemovePeopleAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
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
 * Removes specified PeopleClipboardCollectionDTOs from PeopleClipboardCollectionBean.
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class ClipboardRemovePeopleAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardRemovePeopleAction */
    public ClipboardRemovePeopleAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract data from form
        String personIDStrings[] = ((ClipboardAddPeopleForm)form).getPersonID();
      
        HttpSession session = request.getSession();
        HashMap existingPeopleClipboardMap = (HashMap) session.getAttribute("PeopleClipboardMap");
        
        // check we've got the PeopleClipboardMap in the session
        if (existingPeopleClipboardMap == null) {
            return mapping.findForward("noPeopleClipboard");
        }
        else {
            // iterate through the orgIDStrings array and remove these from the HashMap
            for (int i = 0; i < personIDStrings.length; i++) {
                existingPeopleClipboardMap.remove(new Integer(personIDStrings[i]));
            }
            
            // if the peopleCollection is now of size 0, remove it from the session
            if (existingPeopleClipboardMap.size() == 0) {
                session.removeAttribute("PeopleClipboardMap");
            }
            return mapping.findForward("peopleRemoved");
        }
    }
}
