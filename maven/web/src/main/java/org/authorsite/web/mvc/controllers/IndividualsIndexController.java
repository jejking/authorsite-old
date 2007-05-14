/*
 * IndividualsIndexController.java
 *
 * Created on 29 March 2007, 21:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.authorsite.domain.Individual;
import org.authorsite.domain.service.IndividualManagementService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author jejking
 */
@Transactional
public class IndividualsIndexController extends AbstractController {
    
    private static final Logger LOGGER = Logger.getLogger(IndividualsIndexController.class);
    
    private IndividualManagementService individualManagementService;
    
    /** Creates a new instance of IndividualsIndexController */
    public IndividualsIndexController() {
    }

    public void setIndividualManagementService(IndividualManagementService individualManagementService) {
        this.individualManagementService = individualManagementService;
    }
    
    public IndividualManagementService getIndividualManagementService() {
        return this.individualManagementService;
    }
    
    @Transactional(readOnly=true)
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        
        // these are the defaults....
        int pageNumber = 1;
        int pageSize = 10;
        
        String pageNumberString = httpServletRequest.getParameter("pageNumber");
        if (pageNumberString != null) {
            try {
                int pageNumberTmp = Integer.parseInt(pageNumberString);
                if (pageNumberTmp > 0) {
                    pageNumber = pageNumberTmp;
                }
            }
            catch (NumberFormatException nfe)
            {
                // we can safely ignore this.
            }
        }
        
        String pageSizeString = httpServletRequest.getParameter("pageSize");
        if (pageSizeString != null) {
            try {
                int pageSizeTmp = Integer.parseInt(pageSizeString);
                if (pageSizeTmp > 0 && pageSizeTmp < 50) { // set hard limit on max page size
                    pageSize = pageSizeTmp;
                }
                    
            }
            catch (NumberFormatException nfe) {
                // ignore
            }
                    
        }
        
        int count = this.individualManagementService.countIndividuals();
        LOGGER.debug("Got count " + count);
        
        List<Individual> individuals = this.individualManagementService.findAllIndividualsPaging(pageNumber, pageSize);
        LOGGER.debug("Got list of size " + individuals.size());
        ModelAndView mav = new ModelAndView("people/individuals/index");
        mav.addObject("individuals", individuals);
        mav.addObject("pageNumberUsed", new Integer(pageNumber));
        mav.addObject("pageSizeUsed", new Integer(pageSize));
        mav.addObject("count", new Integer(count));
        return mav;
    }
    
}
