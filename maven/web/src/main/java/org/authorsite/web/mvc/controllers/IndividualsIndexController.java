/*
 * IndividualsIndexController.java
 *
 * Created on 29 March 2007, 21:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author jejking
 */
public class IndividualsIndexController extends AbstractController {
    
    /** Creates a new instance of IndividualsIndexController */
    public IndividualsIndexController() {
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("people/individuals/index");
    }
    
}
