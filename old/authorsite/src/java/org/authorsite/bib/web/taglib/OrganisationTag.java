/*
 * OrganisationTag.java
 *
 * Created on 17 February 2003, 12:28
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/OrganisationTag.java,v 1.2 2003/03/01 13:31:26 jejking Exp $
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
public class OrganisationTag extends TagSupport {
    
    private BibTagUtils utils;
    
    // tag attributes
    private String detail; // all attributes, or just main name + given name??
    private String beanName; // are we looking for a DTO anywhere except in pageContext.getAttribute("CurrentPersonDTO")??
    private boolean checkBoxFlag;
    
       
    // setters and getters
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String newDetail) {
        detail = newDetail;
    }
    
    public String getBeanName() {
        return beanName;
    }
    
    public void setBeanName(String newBeanName) {
        beanName = newBeanName;
    }
    
    public boolean getCheckBoxFlag() {
        return checkBoxFlag;
    }
    
    public void setCheckBoxFlag(boolean newCheckBoxFlag) {
        checkBoxFlag = newCheckBoxFlag;
    }
    
    /** Creates a new instance of OrganisationTag */
    public OrganisationTag() {
        utils = new BibTagUtils();
    }
    
    public int doStartTag() throws JspException {
        
        String html = null;
        OrganisationDTO dto = null;
        String[] orgIDs = null;
        
        // pick up essential data from the PageContext
        if (beanName != null && !beanName.equals("")) { // i.e. we are not using the default name
            dto = (OrganisationDTO) pageContext.getAttribute(beanName);
        }
        else {
            dto = (OrganisationDTO) pageContext.getAttribute("CurrentOrgDTO");
        }
        
        if (dto == null) {
            dto = (OrganisationDTO) pageContext.findAttribute("CurrentOrgDTO");
        }
        
        // if it's still null, bail out
        if (dto == null) {
            return (SKIP_BODY);
        }
        
        // see if we've got our sorted array of orgIDs in scope
        orgIDs = (String[]) pageContext.getAttribute("OrgIDs");
        
        // delegate generation of content to BibTagUtils
        if (detail != null && !detail.equals("full")) {
            html = utils.renderOrgConcise(pageContext, dto, orgIDs, checkBoxFlag);
        }
        else {
            html = utils.renderOrgFull(pageContext, dto, orgIDs, checkBoxFlag);
        }
        
        try {
            pageContext.getOut().write(html);
        }
        catch (IOException ioe) {
            throw new JspException(ioe);
        }
        
        return (SKIP_BODY);
    }
}
