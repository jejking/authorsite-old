/*
 * ClipboardOrgsForm.java
 *
 * Created on 05 February 2003, 20:47
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/ClipboardOrgsForm.java,v 1.2 2003/03/01 13:32:59 jejking Exp $
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
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.authorsite.bib.web.util.*;

/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class ClipboardOrgsForm extends ActionForm {
    
    // array to hold all the orgIDs that are checked in /management/clipboard/orgSearchResults
    private String orgID[];
    
    /** Creates a new instance of ClipboardOrgsForm */
    public ClipboardOrgsForm() {
    }
    
    public String[] getOrgID() {
        return orgID;
    }
    
    public void setOrgID(String newOrgID[]) {
        orgID = newOrgID;
    }
    
    /**
     * ensures that everything submitted in the string array is actually a valid integer.
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        // check values have actually been submitted..
        if (orgID.length == 0) {
            ActionError newError = new ActionError("web.errors.nothingSelected");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        
        // check everything's an integer
        for (int i = 0; i < orgID.length; i++ ) {
            if (!InputChecker.isInteger(orgID[i])) {
                // someone's sent in something they shouldn't have
                ActionError newError = new ActionError("web.errors.notAnInteger", orgID[i]);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        return errors;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
       // doc says we've got to set the length to 0 in the reset method... so here goes
       orgID = new String[0];
    }
    
    
    
}
