/*
 * SidenavTag.java, created on 28-Nov-2004 at 11:57:48
 * 
 * Copyright John King, 2004.
 *
 *  SidenavTag.java is part of authorsite.org's MailArchive program.
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
 * Custom tag to build the sidenav <code>div</code> in the default MailArchive web application.
 * 
 * @author jejking
 * @version $Revision: 1.4 $
 */
public class SidenavTag extends NavigationTag {
    
    /**
     * @see org.authorsite.mailarchive.view.web.taglibs.NavigationTag#doTagImplementation()
     */
    protected void doTagImplementation() throws JspException, IOException {
        /*
         * Basic logic is quite simple:
         * 		- if we are right at the top, just display /admin/ and /browse/
         * 		and none of the sub-options
         * 
         *      - if we are in the /browse/ part of the app,
         * 		highlight browse and display the options underneath
         * 		and only the top of /admin/
         * 
         * 		- if we are in the /admin/ part of the app,
         * 		highlight admin and display the options underneath
         * 		and only the top of /browse/
         * 
         * 		- if we are a logged in user, present a "logout username" option
         * 		pointing to the logout page
         * 
         */
        
        //      start the <ul> for the whole side nav 
        out.println("<ul class=\"sidenav\">");
        
        printBrowse();
        if (uriComponents.length > 1 && uriComponents[1].equals("browse")) {
            printBrowseOptions();
        }
        printAdmin();
        if (uriComponents.length > 1 && uriComponents[1].equals("admin")) {
            printAdminOptions();
        }
        // this business with the loggedOutFlag is to account for the fact that when
        // the struts logout action forwards to a page including this tag then there
        // there is still a user principal attached to the request.
        // The LogoutAction class sets this flag so that immediately after the logout
        // it *does* appear to the user that they've logged out successfully.
        if (req.getUserPrincipal() != null && req.getAttribute("loggedOutFlag") == null) {
            printLogout();
        }
        
        // end the </ul> for the whole side nav
        out.println("</ul>");
    
        
    }
    
    

    /**
     * @param out
     */
    private void printBrowse() throws IOException {
        out.println("<li class=\"sidenav\"><a class=\"sidenav1\" href=\"" + contextPath + "/browse/index.jspx\">");
        out.print(navBundle.getString("browse"));
        out.print("</a></li>");
    }

    /**
     * @param out
     */
    private void printAdmin() throws IOException {
        out.println("<li class=\"sidenav\"><a class=\"sidenav1\" href=\"" + contextPath + "/admin/index.jspx\">");
        out.print(navBundle.getString("admin"));
        out.print("</a></li>");
    }
    
    /**
     * 
     */
    private void printAdminOptions() throws IOException {
        out.println("<li>");
        out.println("<ul class=\"sidenav\">");
        // addresses, people, messages, loading
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/admin/addresses/index.jspx\">");
        out.print(navBundle.getString("addresses"));
        out.print("</a></li>");
        
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/admin/people/index.jspx\">");
        out.print(navBundle.getString("people"));
        out.print("</a></li>");
        
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/admin/messages/index.jspx\">");
        out.print(navBundle.getString("messages"));
        out.print("</a></li>");
        
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/admin/loading/index.jspx\">");
        out.print(navBundle.getString("loading"));
        out.print("</a></li>");
        
        out.println("</ul>");
        out.println("</li>");
    }

    /**
     * @throws IOException
     * 
     */
    private void printBrowseOptions() throws IOException {
        out.println("<li>");
        out.println("<ul class=\"sidenav\">");
        // addresses, people, messages
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/browse/addresses/index.jspx\">");
        out.print(navBundle.getString("addresses"));
        out.print("</a></li>");
        
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/browse/people/index.jspx\">");
        out.print(navBundle.getString("people"));
        out.print("</a></li>");
        
        out.println("<li class=\"sidenav\"><a href=\"" + contextPath + "/browse/messages/index.jspx\">");
        out.print(navBundle.getString("messages"));
        out.print("</a></li>");
        
        out.println("</ul>");
        out.println("</li>");   
    }
    
    private void printLogout() throws IOException {
        out.println("<li class=\"sidenav\"><a class=\"sidenav1\" href=\"" + contextPath + "/admin/login/logout.action\">");
        out.print(navBundle.getString("logout"));
        out.print(" " + req.getUserPrincipal().getName());
        out.print("</a></li>");
    }
    
}
