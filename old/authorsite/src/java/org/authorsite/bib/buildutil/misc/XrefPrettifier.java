/*
 * JavadocPrettifier.java
 *
 * Created on 17 March 2003, 23:39
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/buildutil/misc/XrefPrettifier.java,v 1.1 2003/03/29 11:43:03 jejking Exp $
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

package org.authorsite.bib.buildutil.misc;
import java.io.*;
import java.util.*;
import org.w3c.tidy.*;
import org.w3c.dom.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
/**
 * Uses JTidy to parse javarsrc html and then prettifies the html and extends it
 * to better fit into the website.
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class XrefPrettifier {
    
    private File currentOutputDirectory;
    private File directory;
    private Tidy tidy;
    private DOMBuilder domBuilder;
    private XMLOutputter xmlOutputter;
    private Namespace xhtml;
    private String base;
    
    /** Creates a new instance of JavadocPrettifier */
    public XrefPrettifier(File newDirectory, String newBase) throws Exception{
        
        directory = newDirectory;
        tidy = new Tidy();
        tidy.setErrfile("/dev/null"); // must paramaterise so it works on windoze
        tidy.setErrout(new PrintWriter(new FileOutputStream("/dev/null")));
        tidy.setQuiet(true);
        tidy.setShowWarnings(false);
        tidy.setMakeClean(true);
        tidy.setDocType("omit"); // there is an annoying bug which means that JDom's DOMBuilder complains about the doctype in the DOM created by JTidy
        
        domBuilder = new DOMBuilder();
        xmlOutputter = new XMLOutputter();
        xmlOutputter.setEncoding("UTF-8");
        xhtml = Namespace.getNamespace("http://www.w3.org/1999/xhtml");
        
        base = newBase;
    }
    
    public static void main(String[] args) {
        try {
            XrefPrettifier prettifier = new XrefPrettifier(new File(args[0]), args[1]);
            prettifier.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    public void execute() throws Exception {
        processDirectory(directory);
    }
    
    private void processDirectory(File currentDirectory) throws IOException, JDOMException {
                
        File[] files = currentDirectory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) { 
                processDirectory(files[i]);
            }
            else if (files[i].getName().endsWith(".html")) {
                processHTML(files[i]);
            }
        }
        
    }
    
    private void processHTML(File htmlFile) throws IOException, JDOMException {
        System.out.println("processing file: " + htmlFile);
        FileInputStream inputStream = new FileInputStream(htmlFile);
        org.w3c.dom.Document domDocument = tidy.parseDOM(inputStream, null);
        inputStream.close();
        
        org.jdom.Document jdomDocument = domBuilder.build(domDocument);
        if (!htmlFile.toString().endsWith("index.html")) { // top level index.html is a frameset
            jdomDocument.setDocType(new DocType("html", "-//W3C//DTD XHTML 1.0 Transitional//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"));
        }
        else {
            jdomDocument.setDocType(new DocType("html", "-//W3C//DTD XHTML 1.0 Frameset//EN", "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd"));
            // set correct document title
            org.jdom.Element titleElement = jdomDocument.getRootElement().getChild("head").getChild("title");
            titleElement.setText("authorsite.org - bibliographic application - xref");
        }
        
      
        org.jdom.Element rootElement = jdomDocument.getRootElement();
        
        processElement(rootElement, htmlFile);
        rootElement.setAttribute("lang", "en");
        rootElement.setAttribute("lang", "en", Namespace.XML_NAMESPACE);
        
        FileOutputStream out = new FileOutputStream(htmlFile); // we're rewriting it in situ this time
        
        xmlOutputter.output(jdomDocument, out);
        out.flush();
        out.close();

    }
    
    private void processElement(org.jdom.Element element, File htmlFile) throws JDOMException {
        
        // remove bgcolor, style, valign, align attributes (should be handled by css)
                
        // remove script elements
        element.removeChildren("script");
        element.removeChildren("meta");
        element.removeChildren("link");
        
        // make sure it doesn't get confused about what namespace it's in...
        element.setNamespace(xhtml);
        
        // recursively process all child elements
        List children = element.getChildren();
        Iterator childrenIt = children.iterator();
        while (childrenIt.hasNext()) {
            processElement((org.jdom.Element)childrenIt.next(), htmlFile);
        }
        
        if (element.getName().equals("head")) {
            processHead(element);
        }
        else if (element.getName().equals("body") && !(htmlFile.getName().endsWith("classList.html") || htmlFile.getName().endsWith("-frame.html") || htmlFile.getName().endsWith("index.html") )) {
            processBody(element);
        }
                
    }
    
    private void processHead(org.jdom.Element head) throws JDOMException {
        // need to add to links to stylesheets
        
        org.jdom.Element linkElement = new org.jdom.Element("link", xhtml);
        linkElement.setAttribute("type", "text/css");
        linkElement.setAttribute("href", base + "css/xref.css");
        linkElement.setAttribute("rel", "stylesheet");
        
        org.jdom.Element metaElement = new org.jdom.Element("meta", xhtml);
        metaElement.setAttribute("http-equiv", "content-type");
        metaElement.setAttribute("content", "text/html; charset=UTF-8");
        
        head.addContent(linkElement);
        head.addContent(metaElement);
    }
    
    private void processBody(org.jdom.Element body) throws JDOMException {
        // adds a logo to return home
        List content = body.getContent();
        
        org.jdom.Element divElement = new org.jdom.Element("div", xhtml);
        org.jdom.Element anchorElement = new org.jdom.Element("a", xhtml);
        anchorElement.setAttribute("target", "_parent");
        anchorElement.setAttribute("href", base + "home/index.html");
        org.jdom.Element imgElement = new org.jdom.Element("img", xhtml);
        imgElement.setAttribute("title", "&lt;bib/&gt;");
        imgElement.setAttribute("alt", "site logo");
        imgElement.setAttribute("height", "67");
        imgElement.setAttribute("width", "100");
        imgElement.setAttribute("src", base + "images/logo20.jpg");
        anchorElement.addContent(imgElement);
        divElement.addContent(anchorElement);
        
        content.add(0, divElement);
    }
    
}
