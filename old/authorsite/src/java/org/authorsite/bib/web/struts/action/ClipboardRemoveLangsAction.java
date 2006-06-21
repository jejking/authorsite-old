/*
 * ClipboardRemoveLangsAction.java
 *
 * Created on 17 February 2003, 22:11
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/ClipboardRemoveLangsAction.java,v 1.2 2003/03/01 13:34:12 jejking Exp $
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
 * @version $Revision: 1.2 $
 */
public class ClipboardRemoveLangsAction extends BibAbstractAction {
    
    /** Creates a new instance of ClipboardRemoveLangsAction */
    public ClipboardRemoveLangsAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String[] languages = ((LanguagesForm)form).getLanguages();
                
        HashSet newLanguageSet = new HashSet(Arrays.asList(languages));
        
        HttpSession session = request.getSession();
        HashSet languagesClipboardSet = (HashSet) session.getAttribute("LanguagesClipboardSet");
        
        if (languagesClipboardSet == null) { // if there is no languages clipboard in the session, create one and set its collection to the new array list of langs
             return mapping.findForward("langsRemoved");
        }
        else { // there is a languages clipboard, add the array list of langs to its collection
            languagesClipboardSet.removeAll(newLanguageSet);
        }
            
        if (languagesClipboardSet.size() == 0) {
            request.removeAttribute("LanguagesClipboardSet"); // it's empty, so remove it
        }
        
        return mapping.findForward("langsRemoved");
    }
    
}
