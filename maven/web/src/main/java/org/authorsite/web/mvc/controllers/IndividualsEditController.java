/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.authorsite.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.authorsite.domain.Individual;
import org.authorsite.domain.service.IndividualManagementService;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author jejking
 */
public class IndividualsEditController extends SimpleFormController {

    private IndividualManagementService individualManagementService;

    public void setIndividualManagementService(IndividualManagementService individualManagementService) {
        this.individualManagementService = individualManagementService;
    }

    @Override
    protected Object formBackingObject(HttpServletRequest httpServletRequest) throws Exception {
        Long id = (Long) httpServletRequest.getAttribute("id");

        Individual individual = this.individualManagementService.findById(id.longValue());
        return individual;
    }

    @Override
    protected ModelAndView showForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BindException bindException) throws Exception {
        // we need to show the edit page
        return showForm(httpServletRequest, bindException, "people/individuals/edit");
    }

    @Override
    protected void onBind(HttpServletRequest httpServletRequest, Object object) throws Exception {
        Individual individual = (Individual) object;
        Long id = (Long) httpServletRequest.getAttribute("id");
        if (individual.getId() <= 0) {
            individual.setId(id.longValue());
        }
    }

    @Override
    protected ModelAndView onSubmit(Object obj) throws Exception {
        Individual individual = (Individual) obj;

        individual = this.individualManagementService.update(individual);
        return new ModelAndView("people/individuals/individual", "individual", individual);
    }
}
