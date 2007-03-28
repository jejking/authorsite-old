/*
 * ViewByIdController.java
 *
 * Created on 28 March 2007, 20:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.authorsite.dao.CollectiveDao;
import org.authorsite.dao.IndividualDao;
import org.authorsite.dao.SystemUserDao;
import org.authorsite.domain.Individual;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 *
 * @author jejking
 */
public class ViewByIdController extends MultiActionController {
    
    private IndividualDao individualDao;
    private CollectiveDao collectiveDao;
    private SystemUserDao systemUserDao;
    
    /**
     * Creates a new instance of ViewByIdController 
     */
    public ViewByIdController() {
        super();
    }
    
    // setters to allow IoC
    
    public void setIndividualDao(IndividualDao individualDao) {
        this.individualDao = individualDao;
    }
    
    public void setCollectiveDao(CollectiveDao collectiveDao) {
        this.collectiveDao = collectiveDao;
    }
    
    public void setSystemUserDao(SystemUserDao systemUserDao) {
        this.systemUserDao = systemUserDao;
    }
    
    
     public ModelAndView loadIndividual(HttpServletRequest request, HttpServletResponse Response) throws Exception {
         
         Long idLong = (Long) request.getAttribute("id");
         if ( idLong == null && request.getRequestURI().endsWith("/index"))
         {
            // handle index
         }
         else
         {
            long id = idLong.longValue();
            Individual individual = this.individualDao.findById(id);
         }
         // TODO
         return null;
     }
     
     public ModelAndView loadCollective(HttpServletRequest request, HttpServletResponse Response) throws Exception {
         return null;
     }
     
     public ModelAndView loadSystemUser(HttpServletRequest request, HttpServletResponse Response) throws Exception {
         return null;
     }
    
}
