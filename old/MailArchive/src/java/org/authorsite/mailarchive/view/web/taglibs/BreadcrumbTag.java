/*
 * BreadcrumbTag.java, created on 25-Nov-2004 at 22:03:57
 * 
 * Copyright John King, 2004.
 *
 *  BreadcrumbTag.java is part of authorsite.org's MailArchive program.
 *
 *  MailArchive is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  MailArchive is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package org.authorsite.mailarchive.view.web.taglibs;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * Taglib to assemble a breadcrumb trail in the default MailArchive web application.
 *  
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class BreadcrumbTag extends NavigationTag {

    protected void doTagImplementation() throws JspException, IOException {
      
        /*
         * we can now assume that the first element of the array will be the Context. That's not
         * of interest to us here. Nor is the last part - in our standard configuration this will
         * be either foo.jspx for a directly called JSP Document or foo.do (a mapped Struts action). 
         * 
         * Instead, the last part of the breadcrumb trail will be derived from the property matching
         * the key "lastBreadcrumb" found in the request object.
         * 
         * Typically, this would be either set by the JSP (if invoked directly) or by a Struts action.
         */
         
        // build the first part of the bread crumb trail
        if (uriComponents.length == 0 || uriComponents[0].equals("index.jspx")) {
            //  we're at the "archive" level, build just as a string
            out.print("<span class=\"breadcrumb\">");
            out.print(navBundle.getString("archive"));
            out.print("</span>");
        }
        else  {
            // build "archive" as a link (it's basically the root)
            out.print("<a class=\"breadcrumb\" href=\"" + contextPath + "/index.jspx\" >");
            out.print(navBundle.getString("archive"));
            out.print("</a>");
            
            // if we have /context/dir/index.*
            // then we're at the root of /context/dir so we only want a link back to /context/index.jspx
            // not another one to /context/dir/index.jspx + the breadcrumbString in the request.
            int cutOff = 0;
            if (uriComponents[uriComponents.length - 1].startsWith("index") 
                    || uriComponents[uriComponents.length - 1].endsWith("/")) {
                cutOff = 2; 
            }
            else {
                cutOff = 1;
            }
            
            // now go through the rest of the URI components 
            for (int i = 1; i < uriComponents.length - cutOff; i++) {
                //out.print("<!-- i = " + i + " -->");
                out.print(" &gt; ");
                out.print("<a class=\"breadcrumb\" href=\"" + contextPath +"/"); // context
                for (int j = 1; j <= i; j++) {
                    //out.print("<!-- j = " + j + " -->");
                    out.print(uriComponents[j] + "/"); // prints the full path to where we are in the bread crumb
                }
                out.println("index.jspx\">");
                out.println(navBundle.getString(uriComponents[i]));
                out.println("</a>");
            }
                    
        }
        
        // at the end, see if we have a "lastBreadcrumb" in the request. if we do, add it, preceed by a separator
        if (req.getAttribute("lastBreadcrumb") != null) {
            out.println(" &gt; ");
            out.println("<span class=\"breadcrumb\">");
            out.println(navBundle.getString((String) req.getAttribute("lastBreadcrumb")));
            out.println("</span>");
        }
         
    }
}
