/*
 * ClearClipboardAction.java
 *
 * Created on 05 February 2003, 19:30
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClearClipboardAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
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
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class ClearClipboardAction extends BibAbstractAction {
    
    /** Creates a new instance of ClearClipboardAction */
    public ClearClipboardAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // extract data from form
        String clipboard = ((ClipboardClearForm)form).getClipboard();
        
        HttpSession session = request.getSession();
        
        // bit of a crude if, else sequence... ho hum
        if (clipboard.equals("people")) {
            removePeopleClipboard(session);
        }
        else if (clipboard.equals("orgs")) {
            removeOrgsClipboard(session);
        }
        else if (clipboard.equals("mediaItems")) {
            removeMediaItemsClipboard(session);
        }
        else if (clipboard.equals("languages")) {
            removeLanguagesClipboard(session);
        }
        else { // i.e. "all"
            removePeopleClipboard(session);
            removeOrgsClipboard(session);
            removeMediaItemsClipboard(session);
            removeLanguagesClipboard(session);
        }
        return mapping.findForward("clipboardCleared");
    }
    
    private void removePeopleClipboard(HttpSession session) {
        if (session.getAttribute("PeopleClipboardMap") != null) {
            session.removeAttribute("PeopleClipboardMap");
        }
    }
    
    private void removeOrgsClipboard(HttpSession session) {
        if (session.getAttribute("OrgsClipboardMap") != null) {
            session.removeAttribute("OrgsClipboardMap");
        }
    }
    
    private void removeMediaItemsClipboard(HttpSession session) {
        if (session.getAttribute("MediaItemsClipboardMap") != null) {
            session.removeAttribute("MediaItemsClipboardMap");
        }
    }
    
    private void removeLanguagesClipboard(HttpSession session) {
        if (session.getAttribute("LanguagesClipboardSet") != null) {
            session.removeAttribute("LanguagesClipboardSet");
        }
    }
}
