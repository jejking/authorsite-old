/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import org.authorsite.domain.Individual;
import org.authorsite.domain.service.IndividualManagementService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author jejking
 */
public class IndividualsCreateController extends SimpleFormController {

    private IndividualManagementService individualManagementService;

    public void setIndividualManagementService(IndividualManagementService individualManagementService) {
        this.individualManagementService = individualManagementService;
    }
    
    @Override
    protected ModelAndView onSubmit(Object obj) throws Exception {
        Individual individual = (Individual) obj;
        
        this.individualManagementService.save(individual);
        return new ModelAndView("people/individuals/individual", "individual", individual);
    }

    
    
}
