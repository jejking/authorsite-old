/*
 * MediaTypeXMLSplitter.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/buildutil/xml2java/MediaTypeXMLSplitter.java,v 1.5 2003/03/01 13:38:24 jejking Exp $
 *
 * Created on 28 October 2002, 10:50
 *
 * Copyright (C) 2002  John King
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

package org.authorsite.bib.buildutil.xml2java;
import java.io.*;
import java.text.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;

/**
 * <p>
 * This class plays an important role in the build process for the bibliography application.
 * Its role is to produce individual xml files for each mediaType described in the input xml 
 * file (which will typically be <code>bibTypesRelationships.xml</code>).
 * These xml files are then processed using an xslt stylesheet to generate the final entity
 * ejb code which will represent the persisted mediaType instances. The source outputted by the 
 * xslt transformation will include xdoclet tags for further processing along with the hand-written 
 * source during the build.
 * </p>
 *
 * <p>
 * The xml output for each mediaType element found in the input file is radically pared down to
 * describe the minimum data necessary to generate the entity bean source. There is no schema declaration
 * here as validation is carried out by the parser and if the validation produces no errors, we can
 * assume the data is syntactically correct.
 * </p>
 * 
 * <p>
 * The decision to go with this two-stage approach using interim xml files was motivated by two
 * main factors: </p>
 * <ul>
 * <li> Outputting multiple xml files from one source as the result of an xslt transformation is a 
 * non-standard xslt feature. As the use of Apache's xerces is mandated by our reliance on its xml schema
 * validation, we could move to using xerces' multiple file output feature. However, the task is sufficiently
 * simple that a simple SAX application and java i/o is adequate.</li>
 * <li> Given the complexity of the source files to be outputted, xslt offers a much neater solution than using 
 * any home-made solution using a SAXParser.
 * </ul>
 *
 * <p>
 * It should be noted that in order for the xslt file to generate xdoclet tags that correctly map
 * the ejb fields and classes to database columns and tables, the xslt file must be kept in sync
 * with the sql generation classes.
 * </p>
 *
 * @see org.authorsite.bib.buildutil.xml2sql.TypesAndRelationshipsSQLGenerator
 * @see org.authorsite.bib.ejb.services.rules.RulesManagerBean
 * @author  jejking
 * @version $Revision: 1.5 $
 */
public class MediaTypeXMLSplitter extends DefaultHandler {
    
    private InputSource xmlIn;
    private File outputDir;
    private SAXParserFactory spf;
    private PrintWriter out;
    private String currentMediaType;
    private File outputFile;
    
    /** Creates a new instance of MediaTypeXMLSplitter
     * @param newFileIn A string describing a file URL containing the file
     * to be processed into multiple output xml files.
     * @param newOutputDir A string describing a file URL referring to the directory to which the output
     * xml files should be written.
     */
    public MediaTypeXMLSplitter(String newFileIn, String newOutputDir) {
        xmlIn = new InputSource(newFileIn);
        outputDir = new File(newOutputDir);
        spf = SAXParserFactory.newInstance();
    }
    
    /** Main method. This will be normalled be executed from the ant build script.
     * @param args First argument is the input file, second argument the output directory.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            usage();
            System.exit(1);
        }
        MediaTypeXMLSplitter app = new MediaTypeXMLSplitter(args[0], args[1]);
        try {
            app.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Describes the correct parameter to use.
     */
    private static void usage() {
        System.out.println("Usage: java MediaTypeXMLSplitter xmlFileIn outputDir");
    }
    
    /**
     * <p>
     * Called by the main method after the constructor has initialised the input file and
     * output directories specified in the arguments to the main method.
     * </p>
     *
     * <p>
     * The method sets up the SAX Parser. It instructs the parser to be namespace aware,
     * validating. It assumes the presence of the xerces parsers as it specifies that the 
     * parser should perform schema validation.
     * </p>
     */
    public void execute() {
        try {
            spf.setNamespaceAware(true);
            spf.setValidating(true);
            spf.setFeature("http://apache.org/xml/features/validation/schema", true);
            
            SAXParser parser = spf.newSAXParser();
            parser.parse(xmlIn, this);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(2);
        }
    }
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        // if have a mediaType, open a file output stream to write to
        if (qName.equals("mediaType")) {
            startMediaType(atts);
        }
        else if (qName.equals("field")) {
            // if we have a field element, extract its attributes and write them to the file output stream
            startField(atts);
        }
        else {
            return;
        }
    }
    
    public void endElement(String uri,String localName,String qName)throws SAXException {
        // if it is a mediaType, finish xml document.
        // close file output stream
        if (qName.equals("mediaType")) {
            endMediaType();
        }
        // else, if it is a field element, write the end of the tag out.
        else if (qName.equals("field")) {
            endField();
        }
    }
    
    private void startMediaType(Attributes atts) {
        try {
            // make up a name for the output file
            StringBuffer outputFileNameBuffer = new StringBuffer(atts.getValue("name"));
            outputFileNameBuffer.setCharAt(0, Character.toUpperCase(outputFileNameBuffer.charAt(0)));
            outputFileNameBuffer.append("Details.xml");
            outputFile = new File(outputDir, outputFileNameBuffer.toString());
            out = new PrintWriter(new FileOutputStream(outputFile));
            
            // write out xml header
            out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>");
            
            // start tag writing
            out.println("<mediaType name=\"" + atts.getValue("name") + "\">");
        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
            System.exit(5);
        }
    }
    
    private void endMediaType() {
        // close the mediaType element
        out.println("</mediaType>");
        
        // flush and close the print stream
        out.flush();
        out.close();
        // ditch the PrintWriter object
        out = null;
    }
    
    private void startField(Attributes atts) {
        // start tag
        out.print("<field");
        int attsLength = atts.getLength();
        for (int i = 0; i < attsLength; i++ ) {
            out.print(" " + atts.getQName(i) + "=\"" + atts.getValue(i) + "\"");
        }
    }
    
    private void endField() {
        out.println(" />");
    }
    
    public void error(SAXParseException exception) throws SAXException {
        System.out.println("Parsing error");
        System.out.println("Line " + exception.getLineNumber());
        System.out.println("Message " + exception.getMessage());
        System.exit(1);
    }
    
    public void fatalError(SAXParseException exception) throws SAXException {
        System.out.println("SAX Parsing Fatal Error");
        System.out.println("Line " + exception.getLineNumber());
        System.out.println("Message " + exception.getMessage());
        System.exit(2);
    }
    
    public void warning(SAXParseException exception) throws SAXException {
        System.out.println("Sax Parsing Warning");
        System.out.println("Line " + exception.getLineNumber());
        System.out.println("Message " + exception.getMessage());
        System.exit(3);
    }
    
}
