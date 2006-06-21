/*
 * RulesProcessor.java
 *
 * Created on 08 November 2002, 12:08
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/services/rules/RulesProcessor.java,v 1.1 2003/03/29 12:50:00 jejking Exp $
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

package org.authorsite.bib.ejb.services.rules;

/**
 * Uses JDOM to process bibTypesRelationships.xml to build a Document object.
 * Is a singleton.
 * Uses jaxen to excecute xpath queries. These should be made to execute once only to initiate
 * a java.util.HashMap based collection rather than traversing the JDOM tree all the time.
 * @author  jejking
 * @version $Revision: 1.1 $
 */
import java.util.*;
import org.xml.sax.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jaxen.*;
import org.jaxen.jdom.*;
import org.saxpath.*;
import org.authorsite.bib.dto.*;

public class RulesProcessor {
    
    private static RulesProcessor instance = new RulesProcessor();
    private Document doc;
    private Element root;
    
    public static RulesProcessor getInstance() {
        return instance;
    }
    
    /** Creates a new instance of RulesProcessor */
    private RulesProcessor() {
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(new InputSource(this.getClass().getResourceAsStream("bibTypesRelationships.xml")));
            root = doc.getRootElement();
        }
        catch (JDOMException jde) {
            jde.printStackTrace();
        }
    }
    
    private HashSet buildSimpleHashSet(String xpathExpression) {
        HashSet set = new HashSet();
        try {
            XPath expression = new JDOMXPath(xpathExpression);
            List results = expression.selectNodes(root);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                Attribute currentAttribute = (Attribute) it.next();
                set.add(currentAttribute.getValue());
            }
            return set;
        }
        catch (JaxenException je) {
            je.printStackTrace();
            return null;
        }
    }
    
    private ArrayList buildFieldDescriptionsList(String xpathExpression) {
        ArrayList list = new ArrayList();
        try {
            XPath expression = new JDOMXPath(xpathExpression);
            List results = expression.selectNodes(root);
            System.out.println("found " + results.size() + " elements");
            Iterator it = results.iterator();
            while (it.hasNext()) {
                // we'll get a list of Element objects. First cast to an Element
                Element field = (Element) it.next();
                String fieldName = field.getAttribute("fieldName").getValue();
                String fieldType = field.getAttribute("fieldType").getValue();
                String fieldSize = field.getAttribute("fieldSize").getValue();
                String priority = field.getAttribute("priority").getValue();
                
                FieldDescriptionDTO dto = new FieldDescriptionDTO(fieldName, fieldType, fieldSize, priority);
                    
                // set up the maxChars. Only relevant if we have a fieldType of string
                if (fieldType.equals("string")) {
                    // here we hardcode the equivalent sizes. These correspond to the values currently hardcoded
                    // into org.authorsite.bib.buildutil.xml2sql.TypesAndRelationshipsSQLGenerator
                    if (fieldSize.equals("small")) {
                        dto.setMaxChars(30);
                    }
                    else if (fieldSize.equals("medium")) {
                        dto.setMaxChars(255);
                    }
                    else {
                        dto.setMaxChars(4000);
                    }
                }
                
                // add the dto to the ArrayList. They will be added in order.
                list.add(dto);
            }
            return list;
        }
        catch (JaxenException je) {
            je.printStackTrace();
            return null;
        }
    }
    
    // will be used for building nested HashSets to represent IntraMediaRelationships back to the RulesManagerBean
    private HashMap buildHashMap(String xpathExpression) {
        HashMap biggerMap = new HashMap();
        try {
            XPath expression = new JDOMXPath(xpathExpression);
            List results = expression.selectNodes(root);
            Iterator it = results.iterator();
            while (it.hasNext()) {
                // we get back a bunch of relationshipSet nodes
                Element relationshipSetEl = (Element) it.next();
                
                // this has children, the mediaTypeRefs with attribute ref
                List relationshipTypeRefs = relationshipSetEl.getChildren();
                Iterator it2 = relationshipTypeRefs.iterator();
                
                HashSet allowedTargets = new HashSet(); // where we will put the permitted relationship participants
                while (it2.hasNext()) {
                    Element currentTarget = (Element) it2.next();
                    allowedTargets.add(currentTarget.getAttributeValue("ref"));
                }
                biggerMap.put(relationshipSetEl.getAttributeValue("ref"), allowedTargets);
            }
            return biggerMap;
        }
        catch (JaxenException je) {
            je.printStackTrace();
            return null;
        }
    }
    
    public HashSet getProductionRelationships(String mediaTypeName) {
        String prodRelsExpression = "mediaType[@name='" + mediaTypeName + "']/producerRelationshipList/relationshipRef/@ref";
        return buildSimpleHashSet(prodRelsExpression);
    }
    
    public HashSet getProductionRelationshipsOfPriority(String mediaTypeName, String priority) {
        String prodRelsExpression = "mediaType[@name='" + mediaTypeName + "']/producerRelationshipList[@priority='" + priority + "']/relationshipRef/@ref";
        return buildSimpleHashSet(prodRelsExpression);
    }
    
    public HashSet getFields(String mediaTypeName) {
        String fieldsExpression = "mediaType[@name='" + mediaTypeName + "']/field/@fieldName";
        return buildSimpleHashSet(fieldsExpression);
    }
    
    public ArrayList getFieldDescriptions(String mediaTypeName) {
        String fieldsExpression = "mediaType[@name='" + mediaTypeName + "']/field";
        return buildFieldDescriptionsList(fieldsExpression);
    }
    
    public ArrayList getFieldDescriptionsOfPriority(String mediaTypeName, String priority) {
        String expression = "mediaType[@name='" + mediaTypeName + "']/field[@priority='" + priority + "']";
        return buildFieldDescriptionsList(expression);
    }
    
    public HashSet getFieldsOfPriority(String mediaTypeName, String priority) {
        String fieldsExpression = "mediaType[@name='" + mediaTypeName + "']/field[@priority='"+ priority + "']/@fieldName";
        return buildSimpleHashSet(fieldsExpression);
    }
    
    public HashMap getIntraMediaRelationships(String mediaTypeName) {
        String relsExpression = "mediaType[@name='" + mediaTypeName + "']/intraMediaRelationshipList/relationshipSet";
        return buildHashMap(relsExpression);
    }
    
    public HashMap getIntraMediaRelationshipsOfPriority(String mediaTypeName, String priority) {
        String relsExpression = "mediaType[@name='" + mediaTypeName + "']/intraMediaRelationshipList[@priority='" + priority + "']/relationshipSet";
        return buildHashMap(relsExpression);
    }
    
    public HashSet getAllMediaTypes() {
        String expression = "mediaType/@name";
        return buildSimpleHashSet(expression);
    }
    
    public HashSet getAllMediaProductionRelationships() {
        String expression = "productionRelationship/@name";
        return buildSimpleHashSet(expression);
    }
    
}
