/*
 * HiddenIDField.java
 *
 * Created on 25 February 2003, 22:19
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/HiddenIDTag.java,v 1.1 2003/03/01 13:31:12 jejking Exp $
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
/**
 * Sets a hidden form field to the value and property specified.
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class HiddenIDTag extends TagSupport {
    
    // attributes
    private String beanName;
    private String property;
    
       
    public String getBeanName() {
        return beanName;
    }
    
    public void setBeanName(String newBeanName) {
        beanName = newBeanName;
    }
    
    public String getProperty() {
        return property;
    }
    
    public void setProperty(String newProperty) {
        property = newProperty;
    }
    
    /** Creates a new instance of HiddenIDField */
    public HiddenIDTag() {
    }
    
    public int doStartTag() throws JspException {
        System.out.println("HiddenIDTag - doStartTag()");
        AbstractDTO dto = (AbstractDTO) pageContext.findAttribute(beanName);
        if (dto == null) {
            System.out.println("HiddenIDTag - dto is null, exiting");
            return(SKIP_BODY);
        }
        StringBuffer html = new StringBuffer();
        html.append("<input type=\"hidden\" name=\"");
        html.append(property);
        html.append("\" value=\"");
        html.append(dto.getID());
        html.append("\"/>");
        
        // get hold of JspWriter and write the html
        try {
            pageContext.getOut().write(html.toString());
        }
        catch (IOException ioe) {
            throw new JspException (ioe);
        }
        System.out.println("HiddenIDTag - build html..." + html.toString());
        return (SKIP_BODY);
        
    }
    
}
