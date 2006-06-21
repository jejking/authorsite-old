/*
 * PersonPublicSearchActionForm.java
 *
 * Created on 06 January 2003, 11:52
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/PersonPublicSearchForm.java,v 1.4 2003/03/29 13:04:39 jejking Exp $
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
 * Contains the data submitted from personSearch.jsp
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class PersonPublicSearchForm extends ActionForm {
    
    private String mainName = null;
    private String givenName = null;
    private boolean likeFlag = false;
    
    /** Creates a new instance of PersonPublicSearchActionForm */
    public PersonPublicSearchForm() {
    }
    
    public String getMainName() {
        return mainName;
    }
    
    public void setMainName(String newMainName) {
        mainName = InputChecker.cleanName(newMainName.trim());
    }
    
    public String getGivenName() {
        return givenName;
    }
    
    public void setGivenName(String newGivenName) {
        givenName = InputChecker.cleanName(newGivenName.trim());
    }
    
    public boolean getLikeFlag() {
        return likeFlag;
    }
    
    public void setLikeFlag(boolean newLikeFlag) {
        likeFlag = newLikeFlag;
    }
    
    /**
     * <p>Validates certain minimum semantic validity in the form entries</p>
     * <ul>
     *  <li> there must be a main name submitted </li>
     *  <li> neither the main name nor the given name may be longer than 200 chars </li>
     * </ul>
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        MessageResources resources = (MessageResources)req.getAttribute(Action.MESSAGES_KEY);
        String mainNameText = resources.getMessage("web.bibPublic.formlabels.mainName");
        String givenNameText = resources.getMessage("web.bibPublic.formlabels.givenName");
        
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
        
        return errors;
        
    }
    
    private void printBytes(byte[] bytes, String name) {
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
        System.out.print(" " + name + "\n");
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        mainName = "";
        givenName = "";
        likeFlag = false;
    }
    
}
