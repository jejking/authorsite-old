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
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 *
 * @author jejking
 */
public class XmlNavNode extends AbstractNavNode {
    
    private static final Logger LOGGER = Logger.getLogger(XmlNavNode.class);
    private static DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    
    private String xmlPath;
    private String pathIndex;
    
    /** Creates a new instance of XmlNavNode */
    public XmlNavNode() {
    }
    
    public XmlNavNode(String xmlPath) {
        this.setXmlPath(xmlPath);
    }
    
    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
        initialiseFromXmlPath();
    }
    
    private static void configureFromElement(Element element, XmlNavNode navNode) {
        navNode.name = element.getTagName();
        String resourceBundleName = element.getAttribute("resourceBundleName");
        if (resourceBundleName.length() > 0) {
            navNode.resourceBundleName = resourceBundleName;
        }
        String iconUrl = element.getAttribute("iconUrl");
        if (iconUrl.length() > 0) {
            navNode.iconUrl = iconUrl;
        }
    }

    private void initialiseFromXmlPath() {
        this.children = new LinkedList<NavNode>();
        this.childrenMap = new HashMap<String, NavNode>();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream inputStream = XmlNavNode.class.getResourceAsStream(this.xmlPath);
            Document document = documentBuilder.parse(inputStream);
            Element root = document.getDocumentElement();
            XmlNavNode.configureFromElement(root, this);
            this.buildChildren(root, this);
        }
        catch (ParserConfigurationException ex) {
            LOGGER.fatal(ex);
        }
        catch (SAXException ex) {
            LOGGER.fatal(ex);
        }
        catch (IOException ex) {
            LOGGER.fatal(ex);
        }
    }
    
    private void buildChildren(Element element, XmlNavNode parent)  {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
               XmlNavNode childNavNode = new XmlNavNode();
               Element childElement = (Element) childNode;
               XmlNavNode.configureFromElement(childElement, childNavNode);
               parent.addChild(childNavNode);
               childNavNode.setParent(parent);
               childNavNode.buildChildren(childElement, childNavNode);
            }
        }
    }
    
    
    
    public String getPath() {
        if (this.pathIndex == null) {
            this.pathIndex = super.getPath() + "/index";
        }
        return this.pathIndex;
    }

    private void setParent(XmlNavNode parent) {
        this.parent = parent;
    }
    
    
}
