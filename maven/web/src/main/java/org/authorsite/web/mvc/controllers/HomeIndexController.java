/*
 * HomeIndexController.java
 *
 * Created on 29 March 2007, 22:04
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.mvc.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author jejking
 */
public class HomeIndexController extends AbstractController  {
    
    private static final Logger LOGGER = Logger.getLogger(HomeIndexController.class);
    
    private String interimContent;
    
    /** Creates a new instance of HomeIndexController */
    public HomeIndexController() {
        InputStream is = HomeIndexController.class.getResourceAsStream("/org/authorsite/web/content/index.html");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ( (str = reader.readLine()) != null ) {
                stringBuilder.append( str );
            }
            reader.close();
            this.interimContent = stringBuilder.toString();
        } catch(IOException ioe) {
            this.interimContent = "<p>Could not read in interim content</p>";
        }
        LOGGER.debug("Set interim content to: " + interimContent);
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return new ModelAndView("index", "content", interimContent);
    }
    
}
