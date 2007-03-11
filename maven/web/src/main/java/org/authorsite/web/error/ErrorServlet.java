/*
 * ErrorServlet.java
 *
 * Created on 10 March 2007, 15:27
 */

package org.authorsite.web.error;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

/**
 *
 * @author jejking
 * @version
 */
public class ErrorServlet extends HttpServlet {
   
    private static final List<String> KNOWN_ERROR_CODES = new ArrayList<String>();
    private static final Logger LOGGER = Logger.getLogger(ErrorServlet.class);
    
    static {
        /**
         * List derived from http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
         * - Client Errors : 4xx
         * - Server Errors: 5xx
         */
        KNOWN_ERROR_CODES.add("400"); //bad request
        KNOWN_ERROR_CODES.add("401"); //unauthorized
        KNOWN_ERROR_CODES.add("402"); //payment required
        KNOWN_ERROR_CODES.add("403"); //forbidden
        KNOWN_ERROR_CODES.add("404"); //not found
        KNOWN_ERROR_CODES.add("405"); //method not allowed
        KNOWN_ERROR_CODES.add("406"); // not acceptable
        KNOWN_ERROR_CODES.add("407"); // proxy authentication required
        KNOWN_ERROR_CODES.add("408"); // request timeout
        KNOWN_ERROR_CODES.add("409"); // conflict
        KNOWN_ERROR_CODES.add("410"); // gone
        KNOWN_ERROR_CODES.add("411"); // length required
        KNOWN_ERROR_CODES.add("412"); // precondition failed
        KNOWN_ERROR_CODES.add("413"); // request entitys too large
        KNOWN_ERROR_CODES.add("414"); // request uri too large
        KNOWN_ERROR_CODES.add("415"); // Unsupported Media Type
        KNOWN_ERROR_CODES.add("416"); // Requested Range Not Satisfiable
        KNOWN_ERROR_CODES.add("417"); // expectation failed

        KNOWN_ERROR_CODES.add("500"); // Internal Server Error
        KNOWN_ERROR_CODES.add("501"); // Not Implemented
        KNOWN_ERROR_CODES.add("502"); // Bad Gateway
        KNOWN_ERROR_CODES.add("503"); // Service Unavailable
        KNOWN_ERROR_CODES.add("504"); // Gateway Timeout
        KNOWN_ERROR_CODES.add("505"); // HTTP Version Not Supported

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
       
        // from request object, extract error details
        String errorCode = (String) request.getAttribute("javax.servlet.error.status_code");
        String exceptionType = (String) request.getAttribute("javax.servlet.error.exception_type");
        String message = (String) request.getAttribute("javax.servlet.error.message");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String request_uri =  (String) request.getAttribute("javax.servlet.error.request_uri");
       
        // TODO from acegi, determine if there is a logged in user who caused the error
        
        if ( errorCode == null ) {
            errorCode = "500";
            request.setAttribute("javax.servlet.error.status_code", errorCode);
        }
        
        
        // log error details using log4j (logger config will determine whether to send a mail, etc)
        LOGGER.error("Error " + errorCode + ". URI: " + request_uri);
        
        if (throwable != null) {
            StringBuilder mesgBuilder = new StringBuilder();
            if (exceptionType != null) {
                mesgBuilder.append("ExeptionType: " + exceptionType);
            }
            if (message != null) {
                mesgBuilder.append("Message: " + message);
            }
            LOGGER.error(mesgBuilder.toString(), throwable);
        }
        
        // set up some attributes for error page to use
        request.setAttribute("pageTitle", "Error" + errorCode + " " + request.getSession().getServletContext().getInitParameter("siteName"));
        
        // forward to /jsp/errors/error.jsp to render the appropriate message
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/errors/error.jsp");
        requestDispatcher.forward(request, response);
        
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** 
     * @return a short description of the servlet.
     */
    public String getServletInfo() {
        return "Handles Errors";
    }
}
