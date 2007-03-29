/*
 * PersonViewController.java
 *
 * Created on 29 March 2007, 21:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.authorsite.dao.IndividualDao;
import org.authorsite.domain.Individual;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author jejking
 */
public class IndividualsViewController extends AbstractController {
    
    private IndividualDao individualDao;
    
    /** Creates a new instance of PersonViewController */
    public IndividualsViewController() {
    }
    
    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }
    

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Long id = (Long) httpServletRequest.getAttribute("id");
        Individual i = this.individualDao.findById(id.longValue());
        if ( i == null ) {
            httpServletResponse.sendError(httpServletResponse.SC_NOT_FOUND);
            return null;
        }
        else {
            return new ModelAndView("people/individuals/individual", "individual", i);
        }
    }
    
}
