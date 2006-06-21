/*
 * InitialBibWebAppListener.java
 *
 * Created on 12 February 2003, 17:37
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/listeners/InitialBibWebAppListener.java,v 1.2 2003/03/01 13:34:58 jejking Exp $
 *
 * Copyright (C) 2003  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.web.listeners;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.saxpath.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.*;
import org.authorsite.bib.ejb.facade.*;
/**
 * <p>
 * <code>InitialBibWebAppListener</code> initialises four Beans which contain
 * lists of ISO 639-2 language codes using a local copy of <code>languages.xml</code>.
 * Each bean holds the languages corresponding to the "priority" attribute in languages.xml.
 * They are sorted alphabetically by ISO 639-2 code. Because we do not know which locale will be
 * used to access the web application, this is the only feasible sorting.
 * </p>
 * <p>
 * The Beans are then made available to the web application as attributes of the 
 * servlet context.
 * </p>
 * <p>The listener also initialises a List of all permitted media types from <code>RulesFacade</code>.
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class InitialBibWebAppListener implements ServletContextListener {
    
    private ArrayList languagesOneList = new ArrayList(150);
    private ArrayList languagesTwoList = new ArrayList(150);
    private ArrayList languagesThreeList = new ArrayList(150);
    // private ArrayList languagesFour = new ArrayList(150);
    
    /** Creates a new instance of InitialBibWebAppListener */
    public InitialBibWebAppListener() {
    }
    
    public void contextInitialized(javax.servlet.ServletContextEvent servletContextEvent) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document languagesDoc = builder.build(new InputSource(this.getClass().getResourceAsStream("languages.xml")));
            Element root = languagesDoc.getRootElement();
            List languages = root.getChildren();
            Iterator languagesIt = languages.iterator();
            
            while (languagesIt.hasNext()) {
                Element currentElement = (Element) languagesIt.next();
                int i = currentElement.getAttribute("priority").getIntValue();
                String iso639 = currentElement.getAttribute("iso639").getValue();
                switch (i) {
                    case 1:
                        languagesOneList.add(iso639);
                        break;
                    case 2:
                        languagesTwoList.add(iso639);
                        break;
                    default:
                        languagesThreeList.add(iso639);
                        break;
                }
            }
            
            ServletContext context = servletContextEvent.getServletContext();
            context.setAttribute("languagesOneList", languagesOneList);
            context.setAttribute("languagesTwoList", languagesTwoList);
            context.setAttribute("languagesThreeList", languagesThreeList);
            
            EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
            RulesFacadeHome rfHome = (RulesFacadeHome) ejbHomeFactory.lookupHome("ejb/RulesFacadeEJB", RulesFacadeHome.class);
            RulesFacade rf = rfHome.create();
            Set allMediaTypes = rf.getAllMediaTypes();
            context.setAttribute("allMediaTypesSet", allMediaTypes);
            
            Set allMediaProductionRelationships = rf.getAllMediaProductionRelationships();
            context.setAttribute("allMediaProductionRelationshipsSet", allMediaProductionRelationships);
        }
        catch (Exception e) {
            // if we get an exception, bail out.
            e.printStackTrace();
        }
    }
    
    public void contextDestroyed(javax.servlet.ServletContextEvent servletContextEvent) {
    }
    
}
