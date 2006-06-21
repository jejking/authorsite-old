/*
 * CharSetFilter.java
 *
 * Created on 08 March 2003, 22:52
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/filters/CharSetFilter.java,v 1.1 2003/03/09 19:54:43 jejking Exp $
 */

package org.authorsite.bib.web.filters;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Simple filter that checks whether a response has a character encoding set,
 * and if it doesn't, sets that to UTF8.
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class CharSetFilter implements Filter {
    
    /** Creates a new instance of CharSetFilter */
    public CharSetFilter() {
    }
    
    public void destroy() {
    }    
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        System.out.println("hello. i am the char set filter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getCharacterEncoding() == null || req.getCharacterEncoding().equals("")) {
            try {
                req.setCharacterEncoding("UTF8");
            }
            catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
                throw new ServletException(uee);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    public void init(javax.servlet.FilterConfig filterConfig) throws javax.servlet.ServletException {
    }
    
}
