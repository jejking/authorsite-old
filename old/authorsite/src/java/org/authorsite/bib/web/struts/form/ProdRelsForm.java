/*
 * ProdRelsForm.java
 *
 * Created on 15 February 2003, 22:33
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/ProdRelsForm.java,v 1.2 2003/03/01 13:32:59 jejking Exp $
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
public class ProdRelsForm extends ActionForm {
    
    private String productionRelationship;
    private boolean finishedFlag;
    private String[] personID = new String[0];
    private String[] orgID = new String[0];
    
    /** Creates a new instance of ProdRelsForm */
    public ProdRelsForm() {
    }
    
    // getters and setters
    public String getProductionRelationship() {
        return productionRelationship;
    }
    
    public void setProductionRelationship(String newProductionRelationship) {
        productionRelationship = newProductionRelationship;
    }
    
    public boolean getFinishedFlag() {
        return finishedFlag;
    }
    
    public void setFinishedFlag(boolean newFinishedFlag) {
        finishedFlag = newFinishedFlag;
    }
    
    public String[] getPersonID() {
        return personID;
    }
    
    public void setPersonID(String[] newPersonID) {
        personID = newPersonID;
    }
    
    public String[] getOrgID() {
        return orgID;
    }
    
    public void setOrgID(String[] newOrgID) {
        orgID = newOrgID;
    }
    
    /**
     * <p>
     * Performs various checks before we proceed to the Action:
     * </p>
     * <ul>
     * <li>a valid production relationship has been set that is permitted for the ActiveMediaItem</li>
     * <li>at least one person or organisation has been submitted</li>
     * <li>all refs passed in are valid integers</li>
     * </ul>
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        // check we have a valid production relationship
        HttpSession session = req.getSession();
        
        // check there's an active media item
        Object activeMediaItem = session.getAttribute("ActiveMediaItem");
        if (activeMediaItem == null) {
            ActionError newError = new ActionError("web.errors.noActiveMediaItem");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }    
        
        if (productionRelationship == null || productionRelationship.equals("")) {
            ActionError newError = new ActionError("web.errors.requiredFieldMissing", "productionRelationship");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        
        Set ActiveProductionRelationshipsSet = (Set) session.getAttribute("ActiveProductionRelationshipsSet");
        if (ActiveProductionRelationshipsSet == null) {
            ActionError newError = new ActionError("web.errors.noActiveProductionRelationships");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        if (!ActiveProductionRelationshipsSet.contains(productionRelationship)) {
            ActionError newError = new ActionError("web.errors.invalidProductionRelationship", productionRelationship);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        
        // check we have at least one person or organisation selected
        if (personID.length + orgID.length == 0) {
            ActionError newError = new ActionError("web.errors.noProductionRelationshipsDefined");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        
        if (orgID.length > 0) {
            // check that everything that has been submitted is at least a valid integer
            for (int i = 0; i < orgID.length; i++ ) {
                if (!InputChecker.isInteger(orgID[i])) {
                    // someone's sent in something they shouldn't have
                    ActionError newError = new ActionError("web.errors.notAnInteger", orgID[i]);
                    errors.add(ActionErrors.GLOBAL_ERROR, newError);
                }
            }
        }
        
        if (personID.length > 0) {
            for (int i = 0; i < personID.length; i++ ) {
                if (!InputChecker.isInteger(personID[i])) {
                    // someone's sent in something they shouldn't have
                    ActionError newError = new ActionError("web.errors.notAnInteger", personID[i]);
                    errors.add(ActionErrors.GLOBAL_ERROR, newError);
                }
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
       personID = new String[0];
       finishedFlag = false;
       productionRelationship = "";
    }
    
        
    
    
}
