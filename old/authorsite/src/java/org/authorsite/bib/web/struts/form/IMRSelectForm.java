/*
 * IMRSelectForm.java
 *
 * Created on 26 February 2003, 12:34
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/IMRSelectForm.java,v 1.1 2003/03/01 13:32:34 jejking Exp $
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

package org.authorsite.bib.web.struts.form;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.authorsite.bib.web.util.*;
import org.authorsite.bib.web.struts.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class IMRSelectForm extends ActionForm {
    
    private String intraMediaRelationship;
    
    private String[] mediaItemID;
    
    public String getIntraMediaRelationship() {
        return intraMediaRelationship;
    }
    
    public void setIntraMediaRelationship(String newIntraMediaRelationship) {
        intraMediaRelationship = newIntraMediaRelationship;
    }
    
    public String[] getMediaItemID() {
        return mediaItemID;
    }
    
    public void setMediaItemID(String[] newMediaItemID) {
        mediaItemID = newMediaItemID;
    }
    
    /**
     * Checks that there is an ActiveMediaItem and that the submitted 
     * string matches the current ActiveIntraMediaRelationshipsMap
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        
        System.out.println("validate method of IMRSelectForm called");
        
        ActionErrors errors = new ActionErrors();
        HttpSession session = req.getSession();
        if (session.getAttribute("ActiveMediaItem") == null) {
            ActionError newError = new ActionError("web.errors.missingActiveMediaItem");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        
        HashMap map = (HashMap) session.getAttribute("ActiveIntraMediaRelationshipsMap");
        Set mapKeys = map.keySet();
        if (!mapKeys.contains(intraMediaRelationship)) {
            ActionError newError = new ActionError("web.errors.imrNotPermitted", intraMediaRelationship);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
                
        // check that mediaItemID only contains integers
        for (int i = 0; i < mediaItemID.length; i++) {
            if (!InputChecker.isInteger(mediaItemID[i])) {
                ActionError newError = new ActionError("web.errors.notAnInteger", mediaItemID[i]);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        return errors;
    }
    
    /** Creates a new instance of IMRSelectForm */
    public IMRSelectForm() {
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        intraMediaRelationship = "";
        mediaItemID = new String[0];
    }
}
