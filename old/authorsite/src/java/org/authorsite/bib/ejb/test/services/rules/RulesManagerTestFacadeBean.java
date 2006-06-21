/*
 * RulesManagerTestFacadeBean.java
 *
 * Created on 11 November 2002, 18:01
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/services/rules/RulesManagerTestFacadeBean.java,v 1.4 2003/03/01 13:35:29 jejking Exp $
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

package org.authorsite.bib.ejb.test.services.rules;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import org.authorsite.bib.ejb.services.rules.*;
import org.authorsite.bib.dto.*;
/**
 * Test suite for the RulesManagerBean.
 *
 * Note that this facade bean will need to be updated manually to reflect the current state of the
 * bibTypesRelationships.xml document.
 *
 * @ejb:bean    name="RulesManagerTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/RulesManagerTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.services.rules.RulesManagerTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.services.rules.RulesManagerTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="RulesManagerEJB"
 *              ref-name="ejb/MyRulesManagerEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class RulesManagerTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private RulesManagerLocalHome rulesManagerLocalHome;
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetFields() {
        // get the fields for a small item, like a bookSeries
        // this has fields - isbn, startDate, endDate
        try {
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            HashSet bookSeriesFields = rulesManagerLocal.getFields("bookSeries");
            
            // check the hashSet returned is not empty
            if (bookSeriesFields.size() != 4) {
                return ("returned set was wrong size");
            }
            if (!bookSeriesFields.contains("volumesConstitutesWholeItem")) {
                return ("set did not contain volumesConstitutesWholeItem");
            }
            if (!bookSeriesFields.contains("isbn")) {
                return ("set did not contain isbn");
            }
            else if (!bookSeriesFields.contains("startDate")) {
                return ("set did not contain startDate");
            }
            else if (!bookSeriesFields.contains("endDate")) {
                return ("set did not contain endDate");
            }
            else {
                return ("passed");
            }
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetFieldsOfPriority() {
        try {
            // try and get the expected fields for book
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            HashSet expBookFields = rulesManagerLocal.getFieldsOfPriority("book", "expected");
            if (expBookFields.size() != 4) {
                return ("wrong size set returned");
            }
            else if (!expBookFields.contains("subtitle")) {
                return ("set did not contain subtitle");
            }
            else if (!expBookFields.contains("numberOfPages")) {
                return ("set did not contain numberOfPages");
            }
            else if (!expBookFields.contains("numberOfPreliminaryPages")) {
                return ("set did not contain numberOfPreliminaryPages");
            }
            else if (!expBookFields.contains("isbn")) {
                return ("set did not contain isbn");
            }
            else {
                return ("passed");
            }
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetProductionRelationships() {
        try {
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            HashSet seriesProdRels = rulesManagerLocal.getProductionRelationships("bookSeries");
            if (seriesProdRels.size() != 4) {
                return ("set returned was wrong size");
            }
            else if (!seriesProdRels.contains("publisher")) {
                return ("set did not contain publisher");
            }
            else if (!seriesProdRels.contains("editor")) {
                return ("set did not contain editor");
            }
            else if (!seriesProdRels.contains("translator")) {
                return ("set did not contain translator");
            }
            else if (!seriesProdRels.contains("technicalReviewer")) {
                return ("set did not contain technicalReviewer");
            }
            else {
                return ("passed");
            }
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetProductionRelationshipsOfPriority() {
        try {
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            HashSet obligBookProdRels = rulesManagerLocal.getProductionRelationshipsOfPriority("book", "obligatory");
            if (obligBookProdRels.size() != 1) {
                return ("set returned was wrong size");
            }
            else if (!obligBookProdRels.contains("publisher")) {
                return ("set did not contain publisher");
            }
            else {
                return ("passed");
            }
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetIntraMediaRelationships() {
        try {
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testGetFieldDescriptions() {
        try {
            // quick test of bookArticle
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            ArrayList bookArticleFieldDescriptions = rulesManagerLocal.getFieldDescriptions("bookArticle");
            if (bookArticleFieldDescriptions.size() != 2) {
                return ("bookArticleFieldDescriptions array list was wrong size, namely " + bookArticleFieldDescriptions.size());
            }
            Iterator it = bookArticleFieldDescriptions.iterator();
            while (it.hasNext()) {
                FieldDescriptionDTO field = (FieldDescriptionDTO) it.next();
                
                if (field.getFieldName().equals("firstPage")) { // make sure firstPage is correctly described in the DTO
                    if (!field.getFieldType().equals("string")) {
                        return ("firstPage fieldType was not string");
                    }
                    if (!field.getFieldSize().equals("small")) {
                        return ("firstPage fieldType was not small");
                    }
                    if (!field.getPriority().equals("obligatory")) {
                        return ("firstPage priority was not obligatory");
                    }
                    if (field.getMaxChars() != 30) {
                        return ("firstPage maxChars was not set to 30");
                    }
                    
                    // first Page now ok
                }
                else { // make sure lastPage is correctly described in the DTO
                    if (!field.getFieldName().equals("lastPage")) {
                        return ("other field was not lastPage");
                    }
                    if (!field.getFieldType().equals("string")) {
                        return ("lastPage fieldType was not string");
                    }
                    if (!field.getFieldSize().equals("small")) {
                        return ("lastPage fieldType was not small");
                    }
                    if (!field.getPriority().equals("obligatory")) {
                        return ("lastPage priority was not obligatory");
                    }
                    if (field.getMaxChars() != 30) {
                        return ("lastPage maxChars was not set to 30");
                    }
                    // lastPage now ok
                }
            }
            return ("passed");
         }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testGetFieldDescriptionsOfPriority() {
        try {
            // as we established in the test above that FieldDescriptionDTOs are correctly assembled,
            // here we'll just check the fieldNames have got through in the correct order (i.e. the order 
            // in which they are listed in the bibTypesRelationship.xml file
            RulesManagerLocal rulesManagerLocal = rulesManagerLocalHome.create();
            // get the expected fields of BookSeries
            ArrayList expectedFields = rulesManagerLocal.getFieldDescriptionsOfPriority("bookSeries", "expected");
            if (expectedFields.size() != 3) {
                return ("expectedFields did not contain three elements, but rather " + expectedFields.size());
            }
            FieldDescriptionDTO dto0 = (FieldDescriptionDTO) expectedFields.get(0);
            if (!dto0.getFieldName().equals("isbn")) {
                return ("first element was not isbn");
            }
            FieldDescriptionDTO dto1 = (FieldDescriptionDTO) expectedFields.get(1);
            if (!dto1.getFieldName().equals("startDate")) {
                return ("second element was not startDate");
            }
            FieldDescriptionDTO dto2 = (FieldDescriptionDTO) expectedFields.get(2);
            if (!dto2.getFieldName().equals("endDate")) {
                return ("third element was not endDate");
            }
            // ok, everything is there and in the correct order
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyRulesManagerEJB");
            rulesManagerLocalHome = (RulesManagerLocalHome) obj;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    /** Creates a new instance of RulesManagerTestFacadeBean */
    public RulesManagerTestFacadeBean() {
        getLocalHomes();
    }
    
    public void ejbCreate() throws CreateException {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        ctx = sessionContext;
    }
}
