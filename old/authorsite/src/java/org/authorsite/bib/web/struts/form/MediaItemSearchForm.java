/*
 * MediaItemSearchForm.java
 *
 * Created on 22 February 2003, 12:17
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/MediaItemSearchForm.java,v 1.2 2003/03/01 13:32:59 jejking Exp $
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

package org.authorsite.bib.web.struts.form;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.authorsite.bib.web.util.*;
import org.authorsite.bib.web.struts.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemSearchForm extends ActionForm {
    
    // attributes
    private String personID; // if we are coming from a person specific search
    private String organisationID; // if we are coming from an organisation specific search
    private String productionRelationship;
    private String title;
    private boolean titleLikeFlag;
    private String mediaType;
    private String iso639;
    private String year;
    private String secondYear; // we will assume that if this is filled in, then a betweenYears search is required
    private String yearOperation;
    
    private boolean searchUnpublishedFlag; // this should only be set to true from /action/management/ URIs
    
    
    // getters and setters
    public String getPersonID() {
        return personID;
    }
    
    public void setPersonID(String newPersonID) {
        personID = newPersonID;
    }
    
    public String getOrganisationID() {
        return organisationID;
    }
    
    public void setOrganisationID(String newOrganisationID) {
        organisationID = newOrganisationID;
    }
    
    public String getProductionRelationship() {
        return productionRelationship;
    }
    
    public void setProductionRelationship(String newProductionRelationship) {
        productionRelationship = newProductionRelationship;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String newTitle) {
        title = InputChecker.cleanTitle(newTitle.trim());
    }
    
    public boolean getTitleLikeFlag() {
        return titleLikeFlag;
    }
    
    public void setTitleLikeFlag(boolean newTitleLikeFlag) {
        titleLikeFlag = newTitleLikeFlag;
    }
    
    public String getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(String newMediaType) {
        mediaType = newMediaType; // will be checked against valid list, so no need to clean
    }
    
    public String getIso639() {
        return iso639;
    }
    
    public void setIso639(String newIso639) {
        iso639 = newIso639; // will be checked against list of valid language codes
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String newYear) {
        year = newYear; // will be validated as integer
    }
    
    public String getSecondYear() {
        return secondYear;
    }
    
    public void setSecondYear(String newSecondYear) {
        secondYear = newSecondYear;
    }
    
    public String getYearOperation() {
        return yearOperation;
    }
    
    public void setYearOperation(String newYearOperation ) {
        yearOperation = newYearOperation;
    }
    
    public boolean getSearchUnpublishedFlag() {
        return searchUnpublishedFlag;
    }
    
    public void setSearchUnpublishedFlag(boolean newSearchUnpublishedFlag) {
        searchUnpublishedFlag = newSearchUnpublishedFlag;
    }
    
    /** Creates a new instance of MediaItemSearchForm */
    public MediaItemSearchForm() {
    }
    
    /**
     * <p>Validates data submitted to search for mediaItems according to the following rules:</p>
     * <ul>
     * <li>at least one search term must be submitted, and this must be one of: </li>
     *      <ol>
     *      <li>personID</li>
     *      <li>organisationID</li>
     *      <li>title</li>
     *      <li>mediaType</li>
     *      <li>iso639</li>
     *      <li>year</li>
     *      </ol>
     * <li>personID or organisationID cannot both be set. If one is set, it must be a valid integer</li>
     * <li>if productionRelationship is set, the there must be a personID or organisationID set as well</li>
     * <li>iso639 must be a valid language code</li>
     * <li>mediaType must be a valid mediaType</li>
     * <li>year must be valid integer</li>
     * <li>secondYear must also be a valid integer</li>
     * <li>if year is set, so must yearOperation. If is not, set it to "ExactYear"</li>
     * <li>if secondYear is set, so must year, and secondYear must be greater than year</li>
     * <li>if secondYear is set, set yearOperation to "BetweenYears"</li>
     * </ul>
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ServletContext context = req.getSession().getServletContext();
        
        // check that we at least have one field to search on...
        if ((personID == null || personID.equals("")) && (organisationID == null || organisationID.equals("")) && 
            (title == null || title.equals("")) && (mediaType == null || mediaType.equals("")) && (iso639 == null || iso639.equals(""))
            && (year == null || year.equals(""))) {
                
                ActionError newError = new ActionError("web.errors.noSearchTerm");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors; // no point trying to check anything if nothing's set....
      
        }
        
        // check we don't have both personID and organisationID submitted
        if ((personID != null && !personID.equals("")) && (organisationID != null && !organisationID.equals(""))) {
            ActionError newError = new ActionError("web.errors.mediaItemSearch.personAndOrg");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        
        // check personID is an integer if it exists
        if (personID != null && !personID.equals("")) {
            if (!InputChecker.isInteger(personID)) {
                ActionError newError = new ActionError("web.errors.notAnInteger", personID);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        // check organisationID is an integer if it exists
        if (organisationID != null && !organisationID.equals("")) {
            if (!InputChecker.isInteger(organisationID)) {
                ActionError newError = new ActionError("web.errors.notAnInteger", organisationID);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        // if productionRelationship is set, then one of personID or organisationID must also be set
        if (productionRelationship != null && !productionRelationship.equals("")) {
            if ((personID == null || personID.equals("")) && (organisationID == null || organisationID.equals(""))) {
                ActionError newError = new ActionError("web.errors.productionRelationshipNoProducer");
            }
        }
        
        // check iso639 is valid if it exists
        if (iso639 != null && !iso639.equals("")) {
            List languagesOneList = (List) context.getAttribute("languagesOneList");
            List languagesTwoList = (List) context.getAttribute("languagesTwoList");
            List languagesThreeList = (List) context.getAttribute("languagesThreeList");
            
            if (!languagesOneList.contains(iso639) && !languagesTwoList.contains(iso639) && !languagesThreeList.contains(iso639)) {
                ActionError newError = new ActionError("web.errors.nonExistentLanguageCode", iso639);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        if (mediaType != null && !mediaType.equals("")) {
            Set allMediaTypesSet = (Set) context.getAttribute("allMediaTypesSet");
            if (!allMediaTypesSet.contains(mediaType)) {
                ActionError newError = new ActionError("web.errors.invalidMediaType", mediaType);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        // check that year is an integer, if it is set
        if (year != null && !year.equals("")) {
            if (!InputChecker.isInteger(year)) {
                ActionError newError = new ActionError("web.errors.notAnInteger", year);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        // check that second year is an integer, if it is set
        if (secondYear != null && !secondYear.equals("")) {
            if (!InputChecker.isInteger(secondYear)) {
                ActionError newError = new ActionError("web.errors.notAnInteger", secondYear);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors; // don't want to compare numbers if we're not dealing with numbers...
            }
        }
        
        // check that first year is set if second year is set. Both need to be submitted
        if ((secondYear != null && !secondYear.equals("")) && (year == null || year.equals(""))) {
            ActionError newError = new ActionError("web.errors.mediaItemSearch.secondYearAndYear");
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        
        // check that second year is greater than the first, if it is set
        if (secondYear != null && !secondYear.equals("")) {
            int secondYearInt = Integer.parseInt(secondYear);
            int yearInt = Integer.parseInt(year);
            if (yearInt >= secondYearInt) {
                ActionError newError = new ActionError("web.errors.mediaItemSearch.secondYearGreaterThanYear");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        // if second year is set, set yearOperation to "BetweenYears"
        if (secondYear != null && !secondYear.equals("")) {
            yearOperation = "BetweenYears";
        }
        
        // if year is true and secondYear isn't set, check yearOperation is set to either "ExactYear", "BeforeYear" or "AfterYear"
        // if none of these is set, set yearOperation to default, "ExactYear"
        if ((year != null && !year.equals("")) && (secondYear == null || secondYear.equals(""))) {
            if (yearOperation == null && !yearOperation.equals("ExactYear") && !yearOperation.equals("BeforeYear") && !yearOperation.equals("AfterYear")) {
                yearOperation = "ExactYear";
            }
        }
        
        // check any attempt to "searchUnpublished" comes from the correct URI, i.e. someone's submitted a value they
        // shouldn't have. This flag is only permitted in from /bibWebApp/actions/management/ URIs !!
        if (searchUnpublishedFlag) {
            String cutDownURI = req.getRequestURI().substring(19); // 19 strips off /bibWebApp/actions/
            if (!cutDownURI.startsWith("management")) { // aha, this is not permitted!!
                ActionError newError = new ActionError("web.errors.invalidOption", "searchUnpublished");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        }
        
        // bit of a hack until more media item seach methods are defined.
        // if we are doing a Person or Organisation search, then we can currently only search for
        // the person/org + one of Relationship, Type, Title/LikeTitle, Language
        if ((personID != null && !personID.equals("")) || (organisationID != null && !organisationID.equals(""))) {
            
            if (productionRelationship != null && !productionRelationship.equals("")) {
                if (( title != null && !title.equals("")) || (mediaType != null && !mediaType.equals("")) || (iso639 != null && !iso639.equals(""))
                || (year != null && !year.equals(""))) {
                
                ActionError newError = new ActionError("web.errors.searchNotYetImplemented");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors;
                }
            }
            
            if (mediaType != null && !mediaType.equals("")) {
                if (( title != null && !title.equals("")) || (productionRelationship != null && !productionRelationship.equals("")) || (iso639 != null && !iso639.equals(""))
                || (year != null && !year.equals(""))) {
                
                ActionError newError = new ActionError("web.errors.searchNotYetImplemented");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors;
                }
            }
            
            if (title != null && !title.equals("")) {
                if (( mediaType != null && !mediaType.equals("")) || (productionRelationship != null && !productionRelationship.equals("")) || (iso639 != null && !iso639.equals(""))
                || (year != null && !year.equals(""))) {
                
                ActionError newError = new ActionError("web.errors.searchNotYetImplemented");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors;
                }
            }
            
            if (iso639 != null && !iso639.equals("")) {
                if (( title != null && !title.equals("")) || (productionRelationship != null && !productionRelationship.equals("")) || (mediaType != null && !mediaType.equals(""))
                || (year != null && !year.equals(""))) {
                
                ActionError newError = new ActionError("web.errors.searchNotYetImplemented");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors;
                }
            }
            
            if (year != null && !year.equals("")) {
                if (( title != null && !title.equals("")) || (productionRelationship != null && !productionRelationship.equals("")) || (iso639 != null && !iso639.equals(""))
                || (mediaType != null && !mediaType.equals(""))) {
                
                ActionError newError = new ActionError("web.errors.searchNotYetImplemented");
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
                return errors;
                }
            }
            
        }
        
        
        return errors;
    }
    
    
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
    private void resetFields() {
        personID = "";
        organisationID = "";
        productionRelationship = "";
        title = "";
        titleLikeFlag = false;
        mediaType = "";
        iso639 = "";
        year = "";
        secondYear = "";
        yearOperation = "";
        searchUnpublishedFlag = false;
    }
    
}
