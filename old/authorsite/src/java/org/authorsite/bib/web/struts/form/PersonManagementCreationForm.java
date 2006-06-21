/*
 * PersonManagementCreationForm.java
 *
 * Created on 24 January 2003, 23:46
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/PersonManagementCreationForm.java,v 1.3 2003/03/01 13:32:59 jejking Exp $
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
 * Contains the data submitted from /management/createPerson.jsp
 * @author  jejking 
 * @version $Revision: 1.3 $
 */
public class PersonManagementCreationForm extends ActionForm {
    
    private String prefix;
    private String givenName;
    private String mainName;
    private String otherNames;
    private String suffix;
    private String genderCode;
    
    /** Creates a new instance of PersonManagementCreationForm */
    public PersonManagementCreationForm() {
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String newPrefix) {
        prefix = InputChecker.cleanName(newPrefix.trim());
    }
    
    public String getGivenName() {
        return givenName;
    }
    
    public void setGivenName(String newGivenName) {
        givenName = InputChecker.cleanName(newGivenName.trim());
    }
    
    public String getMainName() {
        return mainName;
    }
    
    public void setMainName(String newMainName) {
        mainName = InputChecker.cleanName(newMainName.trim());
    }
    
    public String getOtherNames() {
        return otherNames;
    }
    
    public void setOtherNames(String newOtherNames) {
        otherNames = InputChecker.cleanName(newOtherNames.trim());
    }
    
    public String getSuffix() {
        return suffix;
    }
    
    public void setSuffix(String newSuffix) {
        suffix = InputChecker.cleanName(newSuffix.trim());
    }
    
    public String getGenderCode() {
        return genderCode;
    }
    
    public void setGenderCode(String newGenderCode) {
        genderCode = newGenderCode;
    }
    
    /**
     * <p>Validates certain minimum semantic validity in the form entries</p>
     * <ul>
     *  <li> there must be a main name submitted </li>
     *  <li> neither the main name nor the given name may be longer than 200 chars </li>
     *  <li> prefix and suffix must not be longer than 100 chars </li>
     *  <li> other names must not be longer than 500 chars </li>
     *  <li> gender code must match 0 = not known, 1 = male, 2 = female, 9 = not specified </li>
     * </ul>
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        MessageResources resources = (MessageResources)req.getAttribute(Action.MESSAGES_KEY);
        String mainNameText = resources.getMessage("web.bibPublic.formlabels.mainName");
        String givenNameText = resources.getMessage("web.bibPublic.formlabels.givenName");
        String otherNamesText = resources.getMessage("web.bibPublic.formlabels.otherNames");
        String prefixText = resources.getMessage("web.bibPublic.formlabels.prefix");
        String suffixText = resources.getMessage("web.bibPublic.formlabels.suffix");
        String genderCodeText = resources.getMessage("web.bibPublic.formlabels.genderCode");
        
        if (mainName == null || mainName.length() == 0) {
            ActionError newError = new ActionError("web.errors.requiredFieldMissing", mainNameText);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        if (mainName.length() > 200) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", mainNameText, "200");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        if (givenName.length() > 200) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", givenNameText, "200");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        if (prefix.length() > 100) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", prefixText, "100");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        if (suffix.length() > 100) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", suffixText, "100");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        if (otherNames.length() > 500) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", otherNamesText, "500");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        if (InputChecker.isInteger(genderCode)) {
            int gc = Integer.parseInt(genderCode);
            
            if (gc != 0 || gc != 1 || gc != 2 || gc != 9) {
                ActionError newError = new ActionError("web.errors.wrongGenderCode", genderCode);
            }
        }
        else {
            ActionError newError = new ActionError("web.errors.notAnInteger", genderCode);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        return errors;
        
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        prefix = "";
        mainName = "";
        givenName = "";
        otherNames = "";
        suffix = "";
        genderCode = "";
    }
    
    
}
