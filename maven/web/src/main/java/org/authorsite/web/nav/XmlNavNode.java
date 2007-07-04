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
 *
 * @author jejking
 */
public class XmlNavNode extends ConfigurableNavNode {
    
    private static final Logger LOGGER = Logger.getLogger(XmlNavNode.class);
    private String pathIndex;
    
    public static XmlNavNode buildInstance(String xmlPath) {
        try {
            SAXBuilder saxBuilder = new org.jdom.input.SAXBuilder();
            Document document = saxBuilder.build(org.authorsite.web.nav.XmlNavNode.class.getResourceAsStream(xmlPath));
            Element root = document.getRootElement();
            XmlNavNode rootNode = new XmlNavNode(root);
            return rootNode;
        } catch (JDOMException ex) {
            LOGGER.fatal(ex);
        } catch (IOException ex) {
            LOGGER.fatal(ex);
        }
        return null;
    }

    private XmlNavNode(Element element) {
        
        // name
        this.name = element.getName();
        
        // attributes - resourceBundleName / iconUrl
        Attribute resourceBundleNameAttr = element.getAttribute("resourceBundleName");
        if ( resourceBundleNameAttr != null ) {
            this.resourceBundleName = resourceBundleNameAttr.getValue();
        }
        
        Attribute iconUrlAttr = element.getAttribute("iconUrl");
        if (iconUrlAttr != null ) {
            this.iconUrl = iconUrlAttr.getValue();
        }
        
        // children
        List childrenList = element.getChildren();
        Iterator childrenIterator = childrenList.iterator();
        while(childrenIterator.hasNext()) {
            Element childElement = (Element) childrenIterator.next();
            XmlNavNode childNavNode = new XmlNavNode(childElement);
            this.addChild(childNavNode);
            childNavNode.setParent(this);
        }
            
    }
            
    
    @Override
    public String getPath() {
        if (this.pathIndex == null) {
            this.pathIndex = super.getPath() + "/index";
        }
        return this.pathIndex;
    }

    

    
    
    
}
