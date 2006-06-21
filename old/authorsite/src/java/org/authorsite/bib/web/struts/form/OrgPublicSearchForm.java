/*
 * OrgPublicSearchForm.java
 *
 * Created on 28 January 2003, 15:42
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/OrgPublicSearchForm.java,v 1.3 2003/03/01 13:32:59 jejking Exp $
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
 * @version $Revision: 1.3 $
 */
public class OrgPublicSearchForm extends ActionForm {
    
    private String name;
    private String city;
    private String country;
    boolean likeFlag = false;
    
    /** Creates a new instance of OrgPublicSearchForm */
    public OrgPublicSearchForm() {
    }
    
    // getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = InputChecker.cleanName(newName.trim());
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String newCity) {
        city = InputChecker.cleanName(newCity.trim());
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String newCountry) {
        country = InputChecker.cleanName(newCountry.trim());
    }
    
    public boolean getLikeFlag() {
        return likeFlag;
    }
    
    public void setLikeFlag(boolean newLikeFlag) {
        likeFlag = newLikeFlag;
    }
    
    /**
     * <p>Validates input according to the following criteria:</p>
     * <ul>
     * <li>At least one of the fields must be filled in for the action to process the submission</li>
     * <li>Name be no longer than 200 characters</li>
     * <li>City must be no longer than 100 characters</li>
     * <li>Country must likewise be no longer than 100 characters</li>
     * </ul>
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        MessageResources resources = (MessageResources)req.getAttribute(Action.MESSAGES_KEY);
        String orgNameText = resources.getMessage("web.bibPublic.formlabels.orgName");
        String cityText = resources.getMessage("web.bibPublic.formlabels.cityName");
        String countryText = resources.getMessage("web.bibPublic.formlabels.countryName");
        
        // validate org name
        if (name == null || name.length() == 0) {
            ActionError newError = new ActionError("web.errors.requiredFieldMissing", orgNameText);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
                
        if (name.length() > 200) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", orgNameText, "200");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        // validate city name
        if (city.length() > 100) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", cityText, "100");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        // validate country name
        if (country.length() > 100) {
            ActionError newError = new ActionError("web.errors.fieldTooLong", countryText, "100");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        return errors;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        name = "";
        city = "";
        country = "";
        likeFlag = false;
    }
}
