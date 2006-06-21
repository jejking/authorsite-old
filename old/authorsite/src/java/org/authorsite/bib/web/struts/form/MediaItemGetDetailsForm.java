/*
 * MediaItemGetDetailsForm.java
 *
 * Created on 23 February 2003, 16:43
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/MediaItemGetDetailsForm.java,v 1.2 2003/03/01 13:32:59 jejking Exp $
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
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.authorsite.bib.web.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemGetDetailsForm extends ActionForm {
    
    private String mediaItemID;
    
    public String getMediaItemID() {
        return mediaItemID;
    }
    
    public void setMediaItemID(String newMediaItemID) {
        mediaItemID = newMediaItemID;
    }
            
    /** Creates a new instance of MediaItemGetDetailsForm */
    public MediaItemGetDetailsForm() {
    }
    
    /**
     * Simply checks that the submitted mediaItemID parameter is a valid integer
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        
        ActionErrors errors = new ActionErrors();
        
        if (!InputChecker.isInteger(mediaItemID)) {
            ActionError error = new ActionError("web.errors.notAnInteger", "mediaItemID");
            errors.add(ActionErrors.GLOBAL_ERROR, error);
        }
        return errors;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        mediaItemID = "";
    }
    
}
