/*
 * IdExtractionFilter.java
 *
 * Created on 28 March 2007, 21:00
 */

package org.authorsite.web.filters;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author  jejking
 * @version
 */

public class IdExtractionFilter implements Filter {
    
    private static final Logger LOGGER = Logger.getLogger(IdExtractionFilter.class);
    
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured.
    private FilterConfig filterConfig = null;
    
    public IdExtractionFilter() {
    }
    
    
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
    
    public void destroy() {
        // do nothing
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        /*
         * /people/individuals/123
         */
        String[] pathComponents = uri.split("/");
        Long idLong = null;
        if ( !pathComponents[pathComponents.length - 1].equals(("index"))) {
            try {
                idLong = new Long(Long.parseLong(pathComponents[pathComponents.length - 1]));
                request.setAttribute("id", idLong);
                LOGGER.debug("Set request id attribute to: "+ idLong);
            }
            catch (NumberFormatException nfe) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } 
        chain.doFilter(request, response);
    }
    
}
