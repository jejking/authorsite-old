/*
 * XmlNavNode.java
 *
 * Created on 24 June 2007, 14:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.nav;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


/**
 * Factory which assembles navigation nodes from information
 * encoded in an XML File which is loaded from the classpath.
 *
 *
 * @author jejking
 */
public class XmlNavNodeFactory  {
    
    private static final Logger LOGGER = Logger.getLogger(XmlNavNodeFactory.class);
    
    /**
     * @param xmlPath
     * @return nav node with {@link RootNavNode#getInstance()} as parent
     */
    public static NavNode buildInstance(String xmlPath) {
	return XmlNavNodeFactory.buildInstance(xmlPath, RootNavNode.getInstance());
    }
    
    /**
     * @param xmlPath
     * @param parent
     * @return tree of nav nodes attached to the specified parent
     */
    public static NavNode buildInstance(String xmlPath, NavNode parent) {
	try {
            SAXBuilder saxBuilder = new org.jdom.input.SAXBuilder();
            Document document = saxBuilder.build(org.authorsite.web.nav.XmlNavNodeFactory.class.getResourceAsStream(xmlPath));
            Element root = document.getRootElement();
            StandardNavNode myRootNode = XmlNavNodeFactory.buildStandardNavNodeFromElement(root, parent);
            return myRootNode;
        } catch (JDOMException ex) {
            LOGGER.fatal(ex);
        } catch (IOException ex) {
            LOGGER.fatal(ex);
        }
        // best we can do....
        return parent;
    }

    private static StandardNavNode buildStandardNavNodeFromElement(Element element, NavNode parent) {
        
        // name
        String name = element.getName();
        String resourceBundleName = null;
        String iconUrl = null;
        
        // attributes - resourceBundleName / iconUrl
        Attribute resourceBundleNameAttr = element.getAttribute("resourceBundleName");
        if ( resourceBundleNameAttr != null ) {
            resourceBundleName = resourceBundleNameAttr.getValue();
        }
        
        Attribute iconUrlAttr = element.getAttribute("iconUrl");
        if (iconUrlAttr != null ) {
            iconUrl = iconUrlAttr.getValue();
        }
        
        StandardNavNode standardNavNode = new StandardNavNode(parent, name, resourceBundleName, iconUrl);
        
        // children
        List childrenList = element.getChildren();
        Iterator childrenIterator = childrenList.iterator();
        while (childrenIterator.hasNext()) {
            Element childElement = (Element) childrenIterator.next();
            XmlNavNodeFactory.buildStandardNavNodeFromElement(childElement, standardNavNode);
        }
            
        
        return standardNavNode;
    }
            
    
}
