/*
 * LoginFailedController.java
 *
 * Created on 15 April 2007, 14:07
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author jejking
 */
public class LoginFailedController extends AbstractController {
    
    /** Creates a new instance of LoginFailedController */
    public LoginFailedController() {
    }
    
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("auth/loginFailed");
    }
    
}
