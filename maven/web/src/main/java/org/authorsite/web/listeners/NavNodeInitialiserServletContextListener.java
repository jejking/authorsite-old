/*
 * NavNodeInitialiserServletContextListener.java
 *
 * Created on 06 August 2007, 21:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.authorsite.web.nav.RootNavNode;
import org.authorsite.web.nav.XmlNavNodeFactory;

/**
 *
 * @author jejking
 */
public class NavNodeInitialiserServletContextListener implements ServletContextListener {
    
    private static final Logger LOGGER = Logger.getLogger(NavNodeInitialiserServletContextListener.class);
    
    /** Creates a new instance of NavNodeInitialiserServletContextListener */
    public NavNodeInitialiserServletContextListener() {
        super();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // do nothing
    }

    public void contextInitialized(ServletContextEvent sce) {
        // register people
        XmlNavNodeFactory.buildInstance("/conf/nav/peopleNav.xml", RootNavNode.getInstance());
        LOGGER.debug("initialised people nav");
        
        // register works
        XmlNavNodeFactory.buildInstance("/conf/nav/worksNav.xml", RootNavNode.getInstance());
        LOGGER.debug("initialised works nav");
        LOGGER.debug(RootNavNode.getInstance().viewAsString());
        
    }

}
