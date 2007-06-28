/*
 * PersonViewController.java
 *
 * Created on 29 March 2007, 21:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Individual;
import org.authorsite.domain.service.IndividualManagementService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author jejking
 */
public class IndividualsViewController extends AbstractController {
    
    private static final Logger LOGGER = Logger.getLogger(IndividualsViewController.class);
    private IndividualManagementService individualManagementService;
    
    /** Creates a new instance of PersonViewController */
    public IndividualsViewController() {
    }
    
    public void setIndividualManagementService(IndividualManagementService individualManagementService) {
        this.individualManagementService = individualManagementService;
    }
    
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Long id = (Long) httpServletRequest.getAttribute("id");
        
        if (id==null) {
            LOGGER.debug("No id attribute available");
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
            
        
        Individual i = this.individualManagementService.findById(id.longValue());
        if ( i == null ) {
            LOGGER.debug("could not find any individual with id " + id);
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        else {
            httpServletRequest.setAttribute("entry", i);
            LOGGER.debug("returning individual" + i);
            return new ModelAndView("people/individuals/individual", "individual", i);
        }
    }
    
}
