/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.authorsite.domain.Individual;
import org.authorsite.domain.service.IndividualManagementService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author jejking
 */
public class IndividualsDeleteController extends AbstractController {
    
    private static final Logger LOGGER = Logger.getLogger(IndividualsDeleteController.class);
    
    private IndividualManagementService individualManagementService;
    
    public void setIndividualManagementService(IndividualManagementService individualManagementService) {
        this.individualManagementService = individualManagementService;
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        
        // get id
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
            this.individualManagementService.deleteIndividual(i);
            return new ModelAndView("people/individuals/individualDeleted", "individual", i);
        }
        
    }

}
