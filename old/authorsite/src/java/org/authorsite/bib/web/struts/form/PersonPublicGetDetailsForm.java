/*
 * PersonPublicGetDetailsForm.java
 *
 * Created on 24 January 2003, 15:51
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/PersonPublicGetDetailsForm.java,v 1.2 2003/03/01 13:32:59 jejking Exp $
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
public class PersonPublicGetDetailsForm extends ActionForm {
    
    private String personID;
    
    /** Creates a new instance of PersonPublicGetDetailsForm */
    public PersonPublicGetDetailsForm() {
    }
    
    public String getPersonID() {
        return personID;
    }
    
    public void setPersonID(String newPersonID) {
        personID = newPersonID;
    }
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        if (!InputChecker.isInteger(personID)) {
            ActionError newError = new ActionError("web.errors.notAnInteger", personID);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        return errors;
    }
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        personID = "";
    }
}
