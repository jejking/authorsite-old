/*
 * LanguagesForm.java
 *
 * Created on 13 February 2003, 16:05
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/LanguagesForm.java,v 1.3 2003/03/01 13:32:59 jejking Exp $
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
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class LanguagesForm extends ActionForm {
    
    private String[] languages;
    
    // this will be extracted so that the Action classes which use this form know
    // which ActionForward to return so that the workflow is executed properly
    private String returnTarget;
    
    /** Creates a new instance of LanguagesForm */
    public LanguagesForm() {
    }
    
    public String[] getLanguages() {
        return languages;
    }
    
    public void setLanguages(String[] newLanguages) {
        languages = newLanguages;
    }
    
    public String getReturnTarget() {
        return returnTarget;
    }
    
    public void setReturnTarget(String newReturnTarget) {
        returnTarget = newReturnTarget;
    }
    
    /**
     * ensures that everything submitted in the string array is actually a valid language id
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        // don't need to validate returnTarget. The action will return to editing_context if that is its value, otherwise will ignore this value
        
        // validate that we have got legitimate ISO 639-2 values
        //  get hold of the servlet context and the lists with the languages in
        ServletContext context = req.getSession().getServletContext();
        List languagesOneList = (List) context.getAttribute("languagesOneList");
        List languagesTwoList = (List) context.getAttribute("languagesTwoList");
        List languagesThreeList = (List) context.getAttribute("languagesThreeList");
                
        // now, iterate through the array of strings submitted and check that the string is in one of the lists
        for (int i = 0; i < languages.length; i++) {
            if (!languagesOneList.contains(languages[i]) && !languagesTwoList.contains(languages[i]) && !languagesThreeList.contains(languages[i])) {
                // oh dear. someone has added a non-existent iso639 key. this is bad
                ActionError newError = new ActionError("web.errors.nonExistentLanguageCode", languages[i]);
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
       languages = new String[0];
       returnTarget = "";
    }
    
}
