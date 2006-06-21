/*
 * NavigationTag.java, created on 28-Nov-2004 at 12:12:54
 * 
 * Copyright John King, 2004.
 *
 *  NavigationTag.java is part of authorsite.org's MailArchive program.
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
 * 
 * @author jejking
 * @version $Revision: 1.1 $
 */
public abstract class NavigationTag extends SimpleTagSupport {
    
    protected PageContext pageContext;
    protected HttpServletRequest req;
    protected String uri;
    protected String contextPath;
    protected String localAppPath;
    protected String[] uriComponents;
    protected JspWriter out;
    protected ResourceBundle navBundle;
    
    /**
     * @see javax.servlet.jsp.tagext.SimpleTag#doTag()
     */
    public void doTag() throws JspException, IOException {
        init(); // sets up common resources used by child implementation classes
        doTagImplementation(); // delegates using TemplateMethod pattern to child  implementation class
    }
    
    /**
     * 
     */
    protected abstract void doTagImplementation() throws JspException, IOException;
    
    
    /**
     * 
     */
    private void init() {
        pageContext = (PageContext) getJspContext();
        req = (HttpServletRequest) pageContext.getRequest();
        uri = req.getRequestURI();
        contextPath = req.getContextPath();
        localAppPath = uri.substring(contextPath.length());
        uriComponents = localAppPath.split("/");
        out = this.getJspContext().getOut();
        navBundle = ResourceBundle.getBundle("org.authorsite.mailarchive.view.resources.Navigation", req.getLocale());
    }
    
    
}
