package org.authorsite.web.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.authorsite.web.nav.NavNode;
import org.authorsite.web.nav.RootNavNode;

public class NavTag extends TagSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public int doStartTag() throws JspException {

	// eg. /myWebContext/people/individuals/123

	String uriPath = ((HttpServletRequest)this.pageContext.getRequest()).getRequestURI();
	
	// eg. /myWebContext
	String contextPath = ((HttpServletRequest)this.pageContext.getRequest()).getContextPath();

	// split off /myWebContext from uri to give, e.g. /people/individuals/123, or /index or indeed /
	String remainingPath = uriPath.substring(contextPath.length());
	NavNode uriPathNode = RootNavNode.getInstance().getDescendantByPath(remainingPath); 
	
	if (uriPathNode == null) {
	    uriPathNode = RootNavNode.getInstance();
	}
	
	try {
	    printNavNode(0, RootNavNode.getInstance(), uriPathNode);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	return super.doStartTag();
    }

    private void printNavNode(final int depth, final NavNode currentNode, final NavNode uriPathNode) throws IOException {
	this.printBlanks(depth + 2);
	JspWriter out = this.pageContext.getOut();
	out.println("<ul>");
	
	for (NavNode childNode : currentNode.getChildren()) {
	    this.printBlanks(depth + 4);
	    out.print("<li>");
	    String url = this.pageContext.getServletContext().getContextPath() + childNode.getPath() + "/index";
	    String text = childNode.getLocalName(this.pageContext.getRequest().getLocale());
	    out.print("<a href=\"" + url + "\">" + text + "</a>");
	    out.println("</li>");
	    
            printNavNode(depth + 1, childNode, uriPathNode);
            
	}
	this.printBlanks(depth + 2);
	out.println("</ul>");
    }
    
    
    private void printBlanks(int i) throws IOException {
	JspWriter out = this.pageContext.getOut();
	for ( int j = 0; j < i; j++) {
	    out.print(" ");
	}
    }
    
}
