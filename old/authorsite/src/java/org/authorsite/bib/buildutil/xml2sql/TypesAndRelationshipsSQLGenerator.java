/*
 * TypesAndRelationshipsSQLGenerator.java
 *
 * Created on 23 October 2002, 14:29
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/buildutil/xml2sql/TypesAndRelationshipsSQLGenerator.java,v 1.5 2003/03/01 16:59:29 jejking Exp $
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

package org.authorsite.bib.buildutil.xml2sql;
import java.io.*;
import java.text.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.*;
/**
 * <p>
 * The <code>TypesAndRelationshipsSQLGenerator</code> class is a key part of the build process for the 
 * bibliography application. It is a simple SAX based application which parses an input xml file (which
 * will normally be <code>bibTypesRelationships.xml</code>) and generates the sql necessary for the 
 * persistence in a relational database of the specific media types defined in the input file.
 * </p>
 *
 * <p>
 * For each element, sql is generated to perform the following functions: </p>
 * <ul>
 * <li>inserts the name of the media type into the constraining table <code>mediaType</code></li>
 * <li>drops the table corresponding to the media type</li>
 * <li>(re)creates the table describing the media type</li>
 * </ul>
 * <p>
 * For each relationship type described in the input file, the class also generates sql to
 * insert the name of that relationship type into the constraining table <code>relationshipType</code>.
 * </p>
 *
 * <p>
 * It is anticipated that this class will be executed by the ant build script for the application. The sql
 * will normally be excecuted by ant immediately after its generation. It is important that the coding 
 * conventions used in this class be kept synchronised with the xslt style sheet used to generate the 
 * entity bean source which will represent the media types at the application layer.
 * </p>
 * 
 * <p>
 * It should be noted that the sql generated has only be tested with <a href="http://www.postgresql.org">PostgreSQL</a>.
 * Future versions will allow pluggable modules which will generate code compatible with other database engines.
 * </p>
 *
 * @see org.authorsite.bib.buildutil.xml2java.MediaTypeXMLSplitter
 * @todo turn this into xslt!!
 * @author  jejking
 * @version $Revision: 1.5 $
 */
public class TypesAndRelationshipsSQLGenerator extends DefaultHandler {
    
    private InputSource xmlIn;
    private File sqlOut;
    private SAXParserFactory spf;
    private PrintWriter out;
    private StringBuffer mediaTypeSQLBuffer;
    private String currentMediaType;
    
    /** Creates a new instance of TypesAndRelationshipsSQLGenerator
     * @param files An array of strings. files[0] is a file URL representing the input file.
     * files[1] is a file URL representing the sql file to be outputted.
     */
    public TypesAndRelationshipsSQLGenerator(String[] files) {
        xmlIn = new InputSource(files[0]);
        sqlOut = new File(files[1]);
        spf = SAXParserFactory.newInstance();
    }
    
    /** Main method of the class. Calls the constructor and then the
     * <code>execute()</code> method.
     * @param args An array of strings. args[0] is a file URL representing the input file.
     * args[1] is a file URL representing the sql file to be outputted.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            usage();
            System.exit(1);
        }
        TypesAndRelationshipsSQLGenerator app = new TypesAndRelationshipsSQLGenerator(args);
        try {
            app.execute();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
    
    private static void usage() {
        System.out.println("Usage: java TypesAndRelationshipsSQLGenerator xmlFileIn sqlFileOut");
    }
    
    private void execute() throws Exception {
        // set up parser
        try {
            out = new PrintWriter(new FileOutputStream(sqlOut));
            spf.setNamespaceAware(true);
            spf.setValidating(true);
            spf.setFeature("http://apache.org/xml/features/validation/schema", true);
            
            SAXParser parser = spf.newSAXParser();
            parser.parse(xmlIn, this);
        }
        catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }
    
    public void startDocument() throws SAXException {
        // write a SQL comment
        DateFormat formatter = DateFormat.getDateTimeInstance();
        Calendar cal = Calendar.getInstance();
        out.println("-- SQL generated by org.authorsite.bib.buildutil.TypesAndRelationshipsSQLGenerator on "
        + formatter.format(cal.getTime()));
        
        // empty the relationshipType table, so we populate from scratch with the values from the xml file
        out.println("truncate relationshipType;");
        
        // empty the mediaType table
        out.println("truncate mediaType;");
        
    }
    
    public void endDocument() throws SAXException {
        // write a SQL comment
        DateFormat formatter = DateFormat.getDateTimeInstance();
        Calendar cal = Calendar.getInstance();
        out.println("-- SQL generation finished by org.authorsite.bib.buildutil.TypesAndRelationshipsSQLGenerator on "
        + formatter.format(cal.getTime()));
        out.flush();
        out.close();
    }
    
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        // things we can ignore here...
        // typesAndRelationships, description, comment, anything with namespace xhtml
        // impliedObligatoryRelationshipList, relationshipRef, mediaTypeRef
        // producerRelationshipList, intraMediaRelationshipList, relationshipSet
        if (qName.equals("typesAndRelationships") || qName.equals("description") || qName.equals("comment")
        || qName.equals("impliedObligatoryRelationshipList") || qName.equals("relationshipRef") || qName.equals("mediaTypeRef")
        || qName.equals("producerRelationshipList") || qName.equals("intraMediaRelationshipList") || qName.equals("relationshipSet")
        || namespaceURI.equals("http://www.w3c.org/1999/xhtml")) {
            return;
        }
        if (qName.equals("productionRelationship") || qName.equals("intraMediaRelationship")) {
            handleRelationship(atts);
        }
        if (qName.equals("mediaType")) {
            handleMediaType(atts);
        }
        if (qName.equals("field")) {
            handleField(atts);
        }
    }
    
    public void endElement(String uri,String localName,String qName)throws SAXException {
        // when we come to the end of a mediaType element we need to
        // - add a foreign key constraint pointing to the mediaItem table
        // - grant the appropriate permissions on the table we have just created
        // - print the mediaTypeSQLBuffer to the printWriter out
        
        if (qName.equals("mediaType")) {
            mediaTypeSQLBuffer.append("constraint " + currentMediaType + "DetailsFK foreign key (" + currentMediaType + "DetailsID)");
            mediaTypeSQLBuffer.append(" references mediaItem (mediaItemID)\n");
            mediaTypeSQLBuffer.append(");\n\n");
            
            mediaTypeSQLBuffer.append("grant select, insert, update, delete on " + currentMediaType + "Details to application;\n");
            mediaTypeSQLBuffer.append("grant select on " + currentMediaType + "Details to dbreader;\n");
            mediaTypeSQLBuffer.append("grant all on " + currentMediaType + "Details to dbwriter;\n");
            out.print(mediaTypeSQLBuffer.toString());
        }
    }
    
    private void handleRelationship(Attributes atts) {
        out.println("insert into relationshipType (relationshipType) values (\'" + atts.getValue("name") + "\');");
    }
    
    private void handleMediaType(Attributes atts) {
        // clear the mediaTypeSQLBuffer
        mediaTypeSQLBuffer = null;
        mediaTypeSQLBuffer = new StringBuffer();
        
        currentMediaType = atts.getValue("name");
        // insert a ref to the mediaType into hte mediaType table
        out.println("insert into mediaType (mediaType) values (\'" + currentMediaType + "\');");
        // drop the table
        out.println("drop table " + currentMediaType + "Details;\n");
        
        // create the table. This will be done in the string buffer as the statement will be finished by handleField method
        mediaTypeSQLBuffer.append("create table " + currentMediaType + "Details (\n");
        // first, a PK field
        mediaTypeSQLBuffer.append(currentMediaType + "DetailsID integer primary key,\n");
    }
    
    private void handleField(Attributes atts) {
        String fieldName = atts.getValue("fieldName");
        String priority = atts.getValue("priority");
        String fieldType = atts.getValue("fieldType");
        String fieldSize = atts.getValue("fieldSize");
        
        // now build up a column create statement
        mediaTypeSQLBuffer.append(fieldName + " ");
        // now, we need to make sure we write the correct sql type in. I'm going to be crude and do a series of ifs and elses
        if (fieldType.equals("string")) { // use varchar
            mediaTypeSQLBuffer.append("varchar ");
            // now, what size...
            if (fieldSize.equals("small")) {
                mediaTypeSQLBuffer.append("(30) ");
            }
            else if (fieldSize.equals("medium")) {
                mediaTypeSQLBuffer.append("(255) ");
            }
            else {
                mediaTypeSQLBuffer.append("(4000) ");
            }
        }
        
        // handle integers. be lazy, make them all the same, standard postgresql integer
        if (fieldType.equals("integer")) {
            mediaTypeSQLBuffer.append("integer ");
        }
        
        if (fieldType.equals("boolean")) {
            mediaTypeSQLBuffer.append("boolean ");
        }
        
        if (fieldType.equals("text")) {
            mediaTypeSQLBuffer.append("text ");
        }
        
        if (fieldType.equals("blob")) {
            mediaTypeSQLBuffer.append("bytea ");
        }
        
        if (fieldType.equals("float")) {
            mediaTypeSQLBuffer.append("float ");
        }
        // now, any not null constraints
        if (priority.equals("obligatory")) {
            mediaTypeSQLBuffer.append("not null");
        }
        
        // add a comma, so we can start the next column
        mediaTypeSQLBuffer.append(",\n");
        
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

