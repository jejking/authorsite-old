/*
 * ClipboardAddLangsAction.java
 *
 * Created on 14 February 2003, 11:39
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardAddLangsAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
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
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;

/**
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class ClipboardAddLangsAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardAddLangsAction */
    public ClipboardAddLangsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] languages = ((LanguagesForm)form).getLanguages();
        String returnTarget = ((LanguagesForm)form).getReturnTarget();
        
        HashSet newLanguageSet = new HashSet(Arrays.asList(languages));
        
        HttpSession session = request.getSession();
        HashSet languagesClipboardSet = (HashSet) session.getAttribute("LanguagesClipboardSet");
        
        if (languagesClipboardSet == null) { // if there is no languages clipboard in the session, create one and set its collection to the new array list of langs
             session.setAttribute("LanguagesClipboardSet", newLanguageSet);
        }
        else { // there is a languages clipboard, add the array list of langs to its collection
            languagesClipboardSet.addAll(newLanguageSet);
        }
            
        // the languages clipboard has now either been created or updated. Where do we want to go today? The returnTarget will tell us...
        if (returnTarget.equals("editing_context")) {
            return mapping.findForward("langsAdded_ItemEditingContext");
        }
        else {
            return mapping.findForward("langsAdded_ClipboardContext");
        }
    }
    
}
