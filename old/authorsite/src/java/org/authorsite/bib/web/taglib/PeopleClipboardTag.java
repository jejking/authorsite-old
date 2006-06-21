/*
 * PeopleClipboardTag.java
 *
 * Created on 16 February 2003, 19:34
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/PeopleClipboardTag.java,v 1.2 2003/03/01 13:31:26 jejking Exp $
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

package org.authorsite.bib.web.taglib;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.authorsite.bib.dto.*;
import org.apache.commons.beanutils.*;
import org.apache.struts.Globals;
import org.apache.struts.util.*;
/**
 * <p>
 * The <code>PeopleClipboardTag</code> is a simple iterator that iterates through
 * the "PeopleClipboardMap" in a user's session, making the current <code>PersonDTO</code>
 * available in the PageContext for processing by the <code>PersonTag</code> under the
 * "CurrentPersonDTO" attribute.
 * </p>
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PeopleClipboardTag extends BodyTagSupport {
    
    private Iterator pcmIt;
    
    private boolean useFormBean;
    
    private String formBeanName;
        
    public String getFormBeanName() {
        return formBeanName;
    }
    
    public void setFormBeanName(String newFormBeanName) {
        formBeanName = newFormBeanName;
        if (formBeanName != null && !formBeanName.equals("")) {
            useFormBean = true;
        }
        else {
            useFormBean = false;
        }
    }
    
    /** Creates a new instance of PeopleClipboardTag */
    public PeopleClipboardTag() {
    }
    
    public int doStartTag() throws JspException {
        try {
            String[] personID = null;
            // we need to see if there is a "PeopleClipboardMap" attribute in session scope
            HttpSession session = pageContext.getSession();
            HashMap peopleClipboardMap = (HashMap) session.getAttribute("PeopleClipboardMap");
            if (peopleClipboardMap == null) {
                return (SKIP_BODY);
            }
            
            // are we using a FormBean with a bunch of PersonID strings in it??
            if (useFormBean) {
                personID = (String[]) BeanUtils.getArrayProperty(pageContext.findAttribute(formBeanName), "personID");
                Arrays.sort(personID);
                // having sorted the array, the personTags can use if more efficiently. put it in the page context for them to use
                pageContext.setAttribute("PersonIDs", personID);
            }
            
            // ok, now get an iterator for the Map
            pcmIt = peopleClipboardMap.values().iterator();
            
            // now, anything left in the iterator??
            if (pcmIt.hasNext()) {
                // set the DTO to be stored in the PageContext under "CurrentPersonDTO"
                pageContext.setAttribute("CurrentPersonDTO", pcmIt.next());
                
                return (EVAL_BODY_BUFFERED); // JSP 1.2
            }
            else {
                return (SKIP_BODY);
            }
        }
        catch (Exception e) {
            throw new JspException(e);
        }
    }
    
    public int doAfterBody() throws JspException {
         
        BodyContent bc = getBodyContent();
        if (bc != null) { // erm, copied off Struts iterate tag
            ResponseUtils.writePrevious(pageContext, bc.getString());
            bc.clearBody();
        }
        
        // do we have anything left in the iterator??
        if (pcmIt.hasNext()) {
            pageContext.setAttribute("CurrentPersonDTO", pcmIt.next());
            return (EVAL_BODY_AGAIN);
        }
        else {
            return (SKIP_BODY);
        }
    }
     
    public int doEndTag() throws JspException {
        // Continue processing this page
        return (EVAL_PAGE);
    }
    
    public void release() {
        pcmIt = null;
        formBeanName = null;
    }
}