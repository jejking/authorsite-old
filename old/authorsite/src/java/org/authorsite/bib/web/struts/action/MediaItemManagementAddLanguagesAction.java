/*
 * MediaItemManagementAddLanguages.java
 *
 * Created on 14 February 2003, 11:40
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/action/MediaItemManagementAddLanguagesAction.java,v 1.3 2003/03/01 13:34:12 jejking Exp $
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
import org.authorsite.bib.web.util.*;

/**
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class MediaItemManagementAddLanguagesAction extends BibAbstractAction {
    
    /** Creates a new instance of MediaItemManagementAddLanguages */
    public MediaItemManagementAddLanguagesAction() {
    }
    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String languages[] = ((LanguagesForm)form).getLanguages();
        // for the time being just print them to standard out
        
        // take the languages from the array and put them in a Set
        HashSet languageSet = new HashSet();
        HashSet languageDTOSet = new HashSet(); // so we can add them to the active media item
                
        for (int i = 0; i < languages.length; i++) {
            languageSet.add(languages[i]);
            languageDTOSet.add(new LanguageDTO(languages[i]));
        }
        
        // now, get hold of a reference to MediaItemManagementFacade EJB and tell it to add the languages
        // to the  current ActiveMediaItem
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        MediaItemManagementFacadeHome home = (MediaItemManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/MediaItemManagementFacadeEJB", MediaItemManagementFacadeHome.class);
        MediaItemManagementFacade facade = home.create();
        
        HttpSession session = request.getSession();
        MediaItemDTO activeMediaItem = (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        int pk = activeMediaItem.getMediaItemID().intValue();
        
        facade.addMultipleLanguagesToMediaItem(pk, languageSet);
        
        // add the language DTOs to the ActiveMediaItem
        activeMediaItem.setLanguages(languageDTOSet);
        
        return mapping.findForward("languagesAdded");
    }
}
