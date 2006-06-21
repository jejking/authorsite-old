/*
 * OrgsClipboardTag.java
 *
 * Created on 17 February 2003, 12:27
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/OrganisationsClipboardTag.java,v 1.2 2003/03/01 13:31:26 jejking Exp $
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
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class OrganisationsClipboardTag extends BodyTagSupport {
    
    private Iterator ocmIt;
    
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
    
    /** Creates a new instance of OrgsClipboardTag */
    public OrganisationsClipboardTag() {
    }
    
    public int doStartTag() throws JspException {
        try {
            String[] orgID = null;
            // we need to see if there is an "OrgsClipboardMap" attribute in session scope
            HttpSession session = pageContext.getSession();
            HashMap orgsClipboardMap = (HashMap) session.getAttribute("OrgsClipboardMap");
            if (orgsClipboardMap == null) {
                return (SKIP_BODY);
            }
            
            // are we using a FormBean with a bunch of orgID strings in it??
            if (useFormBean) {
                orgID = (String[]) BeanUtils.getArrayProperty(pageContext.findAttribute(formBeanName), "orgID");
                Arrays.sort(orgID);
                pageContext.setAttribute("OrgIDs", orgID);
            }
            
            // ok, now get an iterator for the Map
            ocmIt = orgsClipboardMap.values().iterator();
            
            // now, anything left in the iterator??
            if (ocmIt.hasNext()) {
                // set the DTO to be stored in the PageContext under "CurrentOrgDTO"
                pageContext.setAttribute("CurrentOrgDTO", ocmIt.next());
                
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
        if (ocmIt.hasNext()) {
            pageContext.setAttribute("CurrentOrgDTO", ocmIt.next());
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
        ocmIt = null;
        formBeanName = null;
    }
    
}
