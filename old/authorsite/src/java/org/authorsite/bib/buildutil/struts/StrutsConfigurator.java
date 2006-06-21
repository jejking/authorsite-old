/*
 * StrutsConfigurator.java
 *
 * Created on 07 February 2003, 14:52
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/buildutil/struts/StrutsConfigurator.java,v 1.3 2003/03/01 13:38:21 jejking Exp $
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

package org.authorsite.bib.buildutil.struts;
import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import org.jaxen.*;
import org.jaxen.jdom.*;
import org.saxpath.*;
/**
 * <p>
 * Manipulates struts-config.xml prior to building a WAR file to insert:
 * </p>
 * <ul>
 * <li>form-bean references for the <code>ActionForm</code>s generated for each mediaType</li>
 * <li>forwards to mediaType specific jsps whilst creating/editing mediaItem references</li>
 * </ul>
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class StrutsConfigurator {
    
    private Document bibTypesRelationshipsDoc;
    private Element bibTypesRelationshipsRoot;
    private Document strutsConfigDoc;
    private Element strutsConfigRoot;
    private String strutsOutPath;
    private ArrayList mediaTypeNames;
    
    /** Creates a new instance of StrutsConfigurator */
    public StrutsConfigurator(String bibTypesRelationshipsPath, String strutsInPath, String strutsOutPath) { // if any goes wrong, just abort the build...
        
        this.strutsOutPath = strutsOutPath;
        SAXBuilder builder = new SAXBuilder();
        mediaTypeNames = new ArrayList();
        try {
            // read in bibTypesRelationships.xml and construct a JDOM Document out of it
            bibTypesRelationshipsDoc = builder.build(new FileInputStream(bibTypesRelationshipsPath));
            bibTypesRelationshipsRoot = bibTypesRelationshipsDoc.getRootElement();
        
            // read in struts-config.xml and construct a JDOM Document out of it
            strutsConfigDoc = builder.build(new FileInputStream(strutsInPath));
            strutsConfigRoot = strutsConfigDoc.getRootElement();
        }
        catch (JDOMException jde) {
            jde.printStackTrace();
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StrutsConfigurator app = new StrutsConfigurator(args[0], args[1], args[2]);
        app.execute();
    }
    
    public void execute() {
        obtainMediaTypeNames();
        buildFormBeanRefs();
        buildMediaItemCreateForwards();
        buildMediaItemDetailsCreateActions();
        writeNewConfigFile();
    }
    
    /**
     * uses xpath to extract the mediaType elements and their associated name attributes
     */
    private void obtainMediaTypeNames() {
        try {
            XPath expression = new JDOMXPath("mediaType/@name");
            List nodes = expression.selectNodes(bibTypesRelationshipsRoot);
            Iterator nodesIt = nodes.iterator();
            while (nodesIt.hasNext()) {
                Attribute currentAttribute = (Attribute) nodesIt.next();
                mediaTypeNames.add(currentAttribute.getValue());
            }
        }
        catch (JaxenException je) {
            je.printStackTrace();
        }
    }
    
    /**
     * Inserts form bean references into struts-config.xml
     */
    private void buildFormBeanRefs() {
        try {
            // we need to get the struts-config/form-beans element...
            XPath expression = new JDOMXPath("/struts-config/form-beans");
            List nodes = expression.selectNodes(strutsConfigRoot);
            System.out.println("found " + nodes.size() + " nodes");
            Element formBeans = (Element) nodes.get(0); // there is only one...
        
            // then for each media type, add a form-bean element...
            Iterator mediaTypeNamesIt = mediaTypeNames.iterator();
            while (mediaTypeNamesIt.hasNext()) {
                StringBuffer typeName = new StringBuffer((String) mediaTypeNamesIt.next());
                typeName.setCharAt(0, Character.toUpperCase(typeName.charAt(0)));
                typeName.append("DetailsForm");
                
                Element newFormBeanElement = new Element("form-bean");
                newFormBeanElement.setAttribute("name", typeName.toString());
                String formBeansType = "org.authorsite.bib.web.struts.form." + typeName.toString();
                newFormBeanElement.setAttribute("type", formBeansType);
                formBeans.addContent(newFormBeanElement);
            }
        }
        catch (JaxenException je) {
            je.printStackTrace();
        }
    }
    
    private void buildMediaItemCreateForwards() {
        try {
            // for this one, we'll get the action element with the path attribute /management/createMediaItemCore
            XPath expression = new JDOMXPath("/struts-config/action-mappings/action[@path='/management/createMediaItemCore']");
            List nodes = expression.selectNodes(strutsConfigRoot);
            System.out.println("found " + nodes.size() + " nodes");
            Element action = (Element) nodes.get(0);
            
            // iterate through our list of mediaTypes
            Iterator mediaTypeNamesIt = mediaTypeNames.iterator();
            while (mediaTypeNamesIt.hasNext()) {
                String typeName = (String) mediaTypeNamesIt.next();
                StringBuffer typeNameBuffer = new StringBuffer(typeName);
                typeNameBuffer.setCharAt(0, Character.toUpperCase(typeNameBuffer.charAt(0)));
                StringBuffer jspName = new StringBuffer();
                jspName.append("/management/create");
                jspName.append(typeNameBuffer);
                jspName.append("Details.jsp");
                Element newForward = new Element("forward");
                newForward.setAttribute("name", typeName);
                newForward.setAttribute("path", jspName.toString());
                action.addContent(newForward);
            }
        }
        catch (JaxenException je) {
            je.printStackTrace();
        }
    }
    
    private void buildMediaItemDetailsCreateActions() {
        // we need to add a bunch of action elements to the actions element which will correspond with 
        // the action form targets defined in MediaItemManagementCreateDetailsJspGen.xsl
        // namely '/management/create', $capitalisedTypeName, 'Details'
        // they all have a forward of "detailsCreated" which must forward to a common jsp page
        // /management/addLanguagesToMediaItem.jsp
        try {
            XPath expression = new JDOMXPath("/struts-config/action-mappings");
            List nodes = expression.selectNodes(strutsConfigRoot);
            Element actions = (Element) nodes.get(0); // there is only one...
            
            Iterator mediaTypeNamesIt = mediaTypeNames.iterator();
            while (mediaTypeNamesIt.hasNext()) {
                
                StringBuffer typeName = new StringBuffer((String) mediaTypeNamesIt.next());
                typeName.setCharAt(0, Character.toUpperCase(typeName.charAt(0)));
                
                // create string buffer representing the action path
                StringBuffer createActionPath = new StringBuffer("/management/create");
                createActionPath.append(typeName);
                createActionPath.append("Details");
                
                // now, create string buffer representing the type
                StringBuffer createActionType = new StringBuffer("org.authorsite.bib.web.struts.action.");
                createActionType.append(typeName);
                createActionType.append("DetailsCreateAction");
                
                // string buffer to represent name of form bean used
                StringBuffer createActionName = new StringBuffer(typeName.toString());
                createActionName.append("DetailsForm");
                
                // string buffer to represent name of input jsp to return to in case of errors
                StringBuffer createActionInput = new StringBuffer("/management/create");
                createActionInput.append(typeName.toString());
                createActionInput.append("Details.jsp");
                
                Element newActionElement = new Element("action");
                newActionElement.setAttribute("path", createActionPath.toString());
                newActionElement.setAttribute("type", createActionType.toString());
                newActionElement.setAttribute("name", createActionName.toString());
                newActionElement.setAttribute("scope", "request");
                newActionElement.setAttribute("input", createActionInput.toString());
                
                // create another forward for this element (this element is not reusable)
                Element commonForward = new Element("forward");
                commonForward.setAttribute("name", "detailsCreated");
                commonForward.setAttribute("path", "/management/addLanguages.jsp");
                
                // add the common forward element to this action
                newActionElement.addContent(commonForward);
                
                // add our newly created element to actions
                actions.addContent(newActionElement);
            }
        }
        catch (JaxenException je) {
            je.printStackTrace();
        }
    }
    
    private void writeNewConfigFile() {
        XMLOutputter outputter = new XMLOutputter();
        try {
            outputter.output(strutsConfigDoc, new FileOutputStream(strutsOutPath));
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}
