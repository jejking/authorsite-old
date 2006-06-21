/*
 * BibTagUtils.java
 *
 * Created on 17 February 2003, 12:30
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/BibTagUtils.java,v 1.2 2003/03/01 13:31:26 jejking Exp $
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

package org.authorsite.bib.web.taglib;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.authorsite.bib.dto.*;
import org.apache.commons.beanutils.*;
import org.apache.struts.Globals;
import org.apache.struts.util.*;
/**
 * <p>
 * Provides methods which return valid html for output in JSP pages from the various
 * TagHandlers.
 * </p>
 * <p>
 * The reason that the TagHandlers delegate their html generation to this class is because
 * of the complex nesting that is involved in rendering a full MediaItem record, which may
 * necessitate many levels of recursion - particularly when containment relationships are 
 * involved. In such a case, rendering one mediaItem involves rendering one or more further
 * mediaItems. Such a situation is not easily modelled using nested tags at the JSP level.
 * Rather, the <code>MediaItemTag</code> will call the methods presented here as many times
 * as is necessary to render the MediaItem in html with the required level of detail. As the
 * rendering of a MediaItem also involves the rendering of the people and organisations involved
 * in its production relationships, it makes sense to avoid code duplication and therefore the 
 * <code>PersonTag</code> and <code>OrganisationTag</code> avail themselves of the same methods.
 * </p>
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class BibTagUtils {
    
    /** Creates a new instance of BibTagUtils */
    public BibTagUtils() {
    }
    
    /**
     * Renders a PersonDTO as a set of table rows, with one attribute per row. The attribute name is listed
     * on the left, the value, if present, on the right. If the checkbox option is selected, then an html input 
     * element of type checkbox is set in a third column.
     */
    public String renderPersonFull(PageContext pageContext, PersonDTO dto, String[] selectedPersonIDs, boolean withCheckbox) throws JspException {
        
        String personIDText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.personID");
        String mainNameText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.mainName");
        String givenNameText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.givenName");
        String otherNamesText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.otherNames");
        String prefixText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.prefix");
        String suffixText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.suffix");
        String genderCodeText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.genderCode");
        String maleText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.genderCode.male");
        String femaleText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.genderCode.female");
        String notSpecifiedText= RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.genderCode.notSpecified");
        String unknownText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.genderCode.unknown");
        
        StringBuffer html = new StringBuffer();
        
        html.append("<tr>");
            html.append("<td>");
                html.append(personIDText);
            html.append("</td>");
            html.append("<td>");
                html.append(dto.getID());
            html.append("</td>");
            // now, do we need to render a checkbox ??
            if (withCheckbox) {
                html.append("<td rowspan=\"7\">");
                    html.append("<input type=\"checkbox\" name=\"personID\" value=\"" + dto.getID() + "\"");
                    if (selectedPersonIDs != null) {
                        if (Arrays.binarySearch(selectedPersonIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                            html.append("checked=\"checked\"");
                        }
                    }
                    html.append("/>");
                html.append("</td>");
            }
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(prefixText);
            html.append("</td>");
            html.append("<td>");
                if (dto.getPrefix() != null) {
                    html.append(dto.getPrefix());
                }
                else {
                    html.append(" ");
                }
            html.append("</td>");
        html.append("</tr>");
        
        
        html.append("<tr>");
            html.append("<td>");
                html.append(givenNameText);
            html.append("</td>");
            html.append("<td>");
                if (dto.getGivenName() != null) {
                    html.append(dto.getGivenName());
                }
                else {
                    html.append (" ");
                }
            html.append("</td>");
        html.append("</tr>");
                
        html.append("<tr>");
            html.append("<td>");
                html.append(otherNamesText);
            html.append("</td>");
            html.append("<td>");
                if (dto.getOtherNames() != null) {
                    html.append(dto.getOtherNames());
                }
                else {
                    html.append(" ");
                }
            html.append("</td>");
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(mainNameText);
            html.append("</td>");
            html.append("<td>");
                html.append(dto.getMainName()); // it's an obligatory attribute. so can't render as "null"... (hopefully)
            html.append("</td>");
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(suffixText);
            html.append("</td>");
            html.append("<td>");
                if (dto.getSuffix() != null) {
                    html.append(dto.getSuffix());
                }
                else {
                    html.append(" ");
                }
            html.append("</td>");
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(genderCodeText);
            html.append("</td>");
            html.append("<td>");
                switch (dto.getGenderCode()) {
                    case 0: // not known
                        html.append(unknownText);
                        break;
                    case 1: // male
                        html.append(maleText);
                        break;
                    case 2: // female
                        html.append(femaleText);
                        break;
                    default: // not specified
                        html.append(notSpecifiedText);
                }
            html.append("</td>");
        html.append("</tr>");
        
        return html.toString();
    }
    
    /**
     * Renders a person DTO as a single table row. One column is composed of the mainName and, if present, givenName of the
     * person. If the withCheckBox is set, then an html input element of type checkbox is rendered in a second table column.
     */ 
    public String renderPersonConcise(PageContext pageContext, PersonDTO dto, String[] selectedPersonIDs, boolean withCheckbox) throws JspException {
        StringBuffer html = new StringBuffer();
        
        html.append("<tr>");
            html.append("<td>");
                html.append(dto.getMainName());
                if (dto.getGivenName() != null) {
                    html.append(", " + dto.getGivenName());
                }
            html.append("</td>");
            if (withCheckbox) {
                html.append("<td>");
                    html.append("<input type=\"checkbox\" name=\"personID\" value=\"" + dto.getID() + "\"");
                    if (selectedPersonIDs != null) {
                        if (Arrays.binarySearch(selectedPersonIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                            html.append("checked=\"checked\"");
                        }
                    }
                    html.append("/>");
                html.append("</td>");
            }
        html.append("</tr>");
        
        return html.toString();
    }
    
    public String renderPersonConciseWithLink(PageContext pageContext, PersonDTO dto, String[] selectedPersonIDs, boolean checkBoxFlag,
        boolean publicFlag) throws JspException {
            
        StringBuffer html = new StringBuffer();
        
        html.append("<tr>");
            html.append("<td>");
                if (publicFlag) {
                    html.append("<a href=\"/bibWebApp/actions/public/getPersonDetails?personID=" + dto.getID() + "\">");
                }
                else {
                    html.append("<a href=\"/bibWebApp/actions/management/getPersonDetails?personID=" + dto.getID() + "\">");
                }
                html.append(dto.getMainName());
                if (dto.getGivenName() != null) {
                html.append(", " + dto.getGivenName());
                }
                html.append("</a>");
            html.append("</td>");
            if (checkBoxFlag) {
                html.append("<td>");
                    html.append("<input type=\"checkbox\" name=\"personID\" value=\"" + dto.getID() + "\"");
                    if (selectedPersonIDs != null) {
                        if (Arrays.binarySearch(selectedPersonIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                            html.append("checked=\"checked\"");
                        }
                    }
                    html.append("/>");
                html.append("</td>");
            }
        html.append("</tr>");
        
        return html.toString();
    }
    
    public String renderPersonUltraConciseWithLink(PersonDTO dto, boolean publicFlag) throws JspException {
        StringBuffer html = new StringBuffer();
        if (publicFlag) {
            if (publicFlag) {
                html.append("<a href=\"/bibWebApp/actions/public/getPersonDetails?personID=" + dto.getID() + "\">");
            }
            else {
                html.append("<a href=\"/bibWebApp/actions/management/getPersonDetails?personID=" + dto.getID() + "\">");
            }
            html.append(dto.getMainName());
            if (dto.getGivenName() != null) {
                html.append(", " + dto.getGivenName());
            }
            html.append("</a>");
        }
        return html.toString();
    }
    
    public String renderOrgFull(PageContext pageContext, OrganisationDTO dto, String[] selectedOrgIDs, boolean withCheckBox) throws JspException {
        
        StringBuffer html = new StringBuffer();
        
        String organisationIDText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.organisationID");
        String organisationNameText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.orgName");
        String cityText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.cityName");
        String countryText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.countryName");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(organisationIDText);
            html.append("</td>");
            html.append("<td>");
                html.append(dto.getID());
            html.append("</td>");
            // now, do we need to render a checkbox ??
            if (withCheckBox) {
                html.append("<td rowspan=\"4\">");
                    html.append("<input type=\"checkbox\" name=\"orgID\" value=\"" + dto.getID() + "\"");
                    if (selectedOrgIDs != null) {
                        if (Arrays.binarySearch(selectedOrgIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                            html.append("checked=\"checked\"");
                        }
                    }
                    html.append("/>");
                html.append("</td>");
            }
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(organisationNameText);
            html.append("</td>");
            html.append("<td>");
                html.append(dto.getName());
            html.append("</td>");
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(cityText);
            html.append("</td>");
            html.append("<td>");
                if (dto.getCity() != null) {
                    html.append(dto.getCity());
                }
                else {
                    html.append(" ");
                }
            html.append("</td>");
        html.append("</tr>");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(countryText);
            html.append("</td>");
            html.append("<td>");
                if (dto.getCountry() != null) {
                    html.append(dto.getCountry());
                }
                else {
                    html.append(" ");
                }
            html.append("</td>");
        html.append("</tr>");
        
        return html.toString();
    }
    
    public String renderOrgConcise(PageContext pageContext, OrganisationDTO dto, String[] selectedOrgIDs, boolean withCheckBox) throws JspException {
        StringBuffer html = new StringBuffer();
        
        String organisationIDText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.organisationID");
        String organisationNameText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.orgName");
        
        html.append("<tr>");
            html.append("<td>");
                html.append(dto.getName());
            html.append("</td>");
            if (withCheckBox) {
                html.append("<td>");
                    html.append("<input type=\"checkbox\" name=\"orgID\" value=\"" + dto.getID() + "\"");
                    if (selectedOrgIDs != null) {
                        if (Arrays.binarySearch(selectedOrgIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                            html.append("checked=\"checked\"");
                        }
                    }
                    html.append("/>");
                html.append("</td>");
            }
        html.append("</tr>");
        
        return html.toString();
    }
    
    public String renderOrgConciseWithLink(PageContext pageContext, OrganisationDTO dto, String[] selectedOrgIDs, boolean checkBoxFlag,
    boolean publicFlag) throws JspException {
        
        StringBuffer html = new StringBuffer();
        
        html.append("<tr>");
            html.append("<td>");
                if (publicFlag) {
                    html.append("<a href=\"/bibWebApp/actions/public/getOrgDetails?organisationID=" + dto.getID() + "\">");
                }
                else {
                    html.append("<a href=\"/bibWebApp/actions/management/getOrgDetails?organisationID=" + dto.getID() + "\">");
                }
                html.append(dto.getName());
                html.append("</a>");
            html.append("</td>");
            if (checkBoxFlag) {
                html.append("<td>");
                    html.append("<input type=\"checkbox\" name=\"orgID\" value=\"" + dto.getID() + "\"");
                    if (selectedOrgIDs != null) {
                        if (Arrays.binarySearch(selectedOrgIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                            html.append("checked=\"checked\"");
                        }
                    }
                    html.append("/>");
                html.append("</td>");
            }
        html.append("</tr>");
        
        return html.toString();
    }
    
    public String renderOrgUltraConciseWithLink(OrganisationDTO dto, boolean publicFlag) {
        StringBuffer html = new StringBuffer();
        if (publicFlag) {
            html.append("<a href=\"/bibWebApp/actions/public/getOrgDetails?organisationID=" + dto.getID() + "\">");
        }
        else {
            html.append("<a href=\"/bibWebApp/actions/management/getOrgDetails?organisationID=" + dto.getID() + "\">");
        }
        html.append(dto.getName());
        html.append("</a>");
        
        return html.toString();
    }
    
}
