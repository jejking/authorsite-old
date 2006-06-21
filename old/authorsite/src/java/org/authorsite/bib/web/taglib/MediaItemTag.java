/*
 * MediaItemTag.java
 *
 * Created on 17 February 2003, 12:29
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/MediaItemTag.java,v 1.2 2003/03/29 13:10:08 jejking Exp $
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
 * Custom tag to display that most complex of beasts, the MediaItem, including any Person and Organisation
 * DTOs contained within it. Includes the possibility of describing an entire MediaItem including the full
 * containment stack.
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemTag extends TagSupport {
    
    private BibTagUtils utils;
    
    // TAG Attributes
    private boolean publicFlag;
    private boolean checkBoxFlag;
    private String detail; // can be "concise", "full", "fullWithContainers"
    private String beanName;
    
    // setters and getters
    public String getDetail() {
        return detail;
    }
    
    public void setDetail(String newDetail) {
        detail = newDetail;
    }
    
    public String getBeanName() {
        return beanName;
    }
    
    public void setBeanName(String newBeanName) {
        beanName = newBeanName;
    }
    
    public boolean getCheckBoxFlag() {
        return checkBoxFlag;
    }
    
    public void setCheckBoxFlag(boolean newCheckBoxFlag) {
        checkBoxFlag = newCheckBoxFlag;
    }
    
    public boolean getPublicFlag() {
        return publicFlag;
    }
    
    public void setPublicFlag(boolean newPublicFlag) {
        publicFlag = newPublicFlag;
    }
    
    /** Creates a new instance of MediaItemTag */
    public MediaItemTag() {
        utils = new BibTagUtils();
    }
    
    public int doStartTag() throws JspException {
        
        StringBuffer html = new StringBuffer();
        String[] mediaItemIDs = null;
        MediaItemDTO dto = null;
        
        // pick up essential data from the PageContext
        if (beanName != null && !beanName.equals("")) { // i.e. we are not using the default name
            dto = (MediaItemDTO) pageContext.getAttribute(beanName);
        }
        else {
            dto = (MediaItemDTO) pageContext.getAttribute("CurrentMediaItemDTO");
        }
        
        if (dto == null) {
            dto = (MediaItemDTO) pageContext.findAttribute("CurrentMediaItemDTO");
        }
        
        // and if we still haven't got it, why not try the "ActiveMediaItem" session attribute?
        if (dto == null) {
            dto = (MediaItemDTO) pageContext.getSession().getAttribute("ActiveMediaItem");
        }
        
        // if it's still null, bail out
        if (dto == null) {
            return (SKIP_BODY);
        }
        
        // see if we've got our sorted array of orgIDs in scope. Will be provided by MediaItemClipboardTag
        mediaItemIDs = (String[]) pageContext.getAttribute("MediaItemIDs");
        
        // let's start building
        if (detail.equals("concise")) {
            html.append(renderMediaItemConcise(pageContext, dto, mediaItemIDs, checkBoxFlag, publicFlag));
        }
        else if (detail.equals("full")) {
            html.append(renderMediaItemFull(pageContext, dto, mediaItemIDs, checkBoxFlag, publicFlag, false));
        }
        else {
            html.append(renderMediaItemFull(pageContext, dto, mediaItemIDs, checkBoxFlag, publicFlag, true));
        }
        
        try {
            pageContext.getOut().write(html.toString());
        }
        catch (IOException ioe) {
            throw new JspException(ioe);
        }
        
        return (SKIP_BODY);
    }
    
    private StringBuffer renderMediaItemConcise(PageContext pageContext, MediaItemDTO dto, String[] mediaItemIDs,
    boolean checkBoxFlag, boolean publicFlag) {
        
        StringBuffer html = new StringBuffer();
        html.append("<tr>");
        html.append("<td>");
        // build link. NOTE. This MUST be kept in sync with the action mappings on struts-config.xml
        html.append("<a href=\"");
        if (publicFlag) {
            html.append("/bibWebApp/actions/public/getMediaItemDetails?mediaItemID=" + dto.getID() + "\">");
        }
        else {
            // temporary hack. this should end up pointing to /bibWebApp/actions/management/getmediaItemDetails
            html.append("/bibWebApp/actions/public/getMediaItemDetails?mediaItemID=" + dto.getID() + "\">");
        }
        html.append(dto.getTitle() + " (" + dto.getYearOfCreation() + ")");
        html.append("</a>");
        html.append("<td>");
        if (checkBoxFlag) { // build another table cell with a checkbox in it
            html.append("<td>");
            html.append("<input type=\"checkbox\" name=\"mediaItemID\" value=\"" + dto.getID() + "\"");
            if (mediaItemIDs != null) {
                if (Arrays.binarySearch(mediaItemIDs, dto.getID().toString()) >= 0) { // i.e. it's present in the formBean
                    html.append("checked=\"checked\"");
                }
            }
            html.append("<td>");
        }
        html.append("</tr>");
        return html;
    }
    
    private StringBuffer renderMediaItemFull(PageContext pageContext, MediaItemDTO dto, String[] mediaItemIDs,
    boolean checkBoxFlag, boolean publicFlag, boolean withContainers) throws JspException {
        
        String productionRelationshipsText = RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.productionRelationships");
        
        StringBuffer html = new StringBuffer();
        html.append("<tr>");
        html.append("<td colspan=\"2\">");
        html.append("<h2>" + dto.getTitle() + " (" + dto.getYearOfCreation() + ")</h2>");
        html.append("</td>");
        html.append("</tr>");
        
        Set itemProdRels = dto.getMediaProductionRelationships();
        if (itemProdRels != null && itemProdRels.size() > 0) {
            html.append("<tr>");
            html.append("<td colspan=\"2\">");
            html.append("<h3>" + productionRelationshipsText + "</h3>");
            html.append("</td>");
            html.append("</tr>");
            Iterator itemProdRelsIt = itemProdRels.iterator();
            while (itemProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO mprDTO = (MediaProductionRelationshipDTO) itemProdRelsIt.next();
                // number of people and orgs, so we can work out how big to build the table...
                int size = mprDTO.getPeople().size() + mprDTO.getOrganisations().size();
                
                html.append("<tr>");
                html.append("<td rowspan=\"" + size + "\">");
                html.append(RequestUtils.message(pageContext, "RELATIONSHIPS_KEY", Globals.LOCALE_KEY, mprDTO.getRelationshipType()));
                html.append("</td>");
                
                // get the iterators now
                Iterator mprPeopleIt = mprDTO.getPeople().iterator();
                Iterator mprOrgsIt = mprDTO.getOrganisations().iterator();
                
                // build the first right hand cell.
                html.append("<td>");
                // 1. do we have people??
                if (mprPeopleIt.hasNext()) {
                    PersonDTO currentPersonDTO = (PersonDTO) mprPeopleIt.next();
                    html.append(utils.renderPersonUltraConciseWithLink(currentPersonDTO, publicFlag));
                }
                // else we have to put an organisation in this cell. we
                else {
                    OrganisationDTO currentOrgDTO = (OrganisationDTO) mprOrgsIt.next();
                    html.append(utils.renderOrgUltraConciseWithLink(currentOrgDTO, publicFlag));
                }
                html.append("</td>");
                html.append("</tr>");
                
                // now list the rest of any people or orgs in this media production relationship
                while (mprPeopleIt.hasNext()) {
                    PersonDTO currentPersonDTO = (PersonDTO) mprPeopleIt.next();
                    html.append("<tr>");
                    html.append("<td>");
                    html.append(utils.renderPersonUltraConciseWithLink(currentPersonDTO, publicFlag));
                    html.append("</td>");
                    html.append("</tr>");
                }
                while (mprOrgsIt.hasNext()) {
                    OrganisationDTO currentOrgDTO = (OrganisationDTO) mprOrgsIt.next();
                    html.append("<tr>");
                    html.append("<td>");
                    html.append(utils.renderOrgUltraConciseWithLink(currentOrgDTO, publicFlag));
                    html.append("</td>");
                    html.append("</tr>");
                }
            }
            
            // now, display the specific media item details, if set
            MediaItemDetailsDTO detailsDTO = dto.getDetailsDTO();
            if (detailsDTO != null && detailsDTO.getFieldsMap().size() > 0) {
                html.append("<tr>");
                html.append("<td colspan=\"2\">");
                
                html.append("</td>");
                html.append("</tr>");
                HashMap fieldsMap = detailsDTO.getFieldsMap();
                Iterator fieldsMapIt = fieldsMap.keySet().iterator();
                while (fieldsMapIt.hasNext()) {
                    Object key = fieldsMapIt.next();
                    Object value = fieldsMap.get(key);
                    if (value != null) {
                        html.append("<tr>");
                        html.append("<td>");
                        html.append(RequestUtils.message(pageContext, "MEDIA_TYPES_KEY", Globals.LOCALE_KEY, key.toString()));
                        html.append("</td>");
                        html.append("<td>");
                        html.append(value.toString());
                        html.append("</td>");
                        html.append("</tr>");
                    }
                }
            }
            // do we have any intra media relationships (other than containment) to display?
            Map childrenMap = dto.getChildren();
            if (childrenMap != null && childrenMap.size() > 0) {
                html.append("<tr>");
                html.append("<td colspan=\"2\">");
                html.append(RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.children"));
                html.append("</td>");
                html.append("</tr>");
                html.append(renderIMRs(childrenMap));
            }
            
            Map parentsMap = dto.getParents();
            if (parentsMap != null && parentsMap.size() > 0) {
                html.append("<tr>");
                html.append("<td colspan=\"2\">");
                html.append(RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.parents"));
                html.append("</td>");
                html.append("</tr>");
                html.append(renderIMRs(parentsMap));
            }
            
            // having displayed the details and IMRs, do we have a container... (and should we display it??)
            // go recursive until we're not contained in anything any more
            if (withContainers) {
                if (dto.getContainedIn() != null) {
                    html.append("<tr>");
                    html.append("<td colspan=\"2\">");
                    html.append(html.append(RequestUtils.message(pageContext, null, Globals.LOCALE_KEY, "web.bibPublic.formlabels.containedIn")));
                    html.append("</td>");
                    html.append("</tr>");
                    html.append(renderMediaItemFull(pageContext, dto.getContainedIn(), mediaItemIDs, false, publicFlag, withContainers));
                }
            }
            
        }
        
        return html;
    }
    
    private StringBuffer renderIMRs(Map map) throws JspException {
        System.out.println("renderIMRs called");
        StringBuffer html = new StringBuffer();
        Iterator mapKeysIt = map.keySet().iterator();
        while (mapKeysIt.hasNext()) {
            String imrKey = mapKeysIt.next().toString();
            System.out.println("rendering imrKey: " + imrKey);
            Collection values = (Collection) map.get(imrKey);
            System.out.println("we have " + values.size() + " members in this imr");
            Iterator valuesIt = values.iterator();
            if (valuesIt.hasNext()) {
                MediaItemDTO firstValueDTO = (MediaItemDTO) valuesIt.next();
                html.append("<tr>");
                html.append("<td rowspan=\"" + values.size() + "\">");
                html.append(RequestUtils.message(pageContext, "RELATIONSHIPS_KEY", Globals.LOCALE_KEY, imrKey));
                html.append("</td>");
                
                // display first child IMR'ed media item
                html.append("<td>");
                html.append("<a href=\"");
                if (publicFlag) {
                    html.append("/bibWebApp/actions/public/getMediaItemDetails?mediaItemID=" + firstValueDTO.getID() + "\">");
                }
                else {
                    //temporary hack. should point to /bibWebApp/actions/management/getMediaItemDetails
                    html.append("/bibWebApp/actions/public/getMediaItemDetails?mediaItemID=" + firstValueDTO.getID() + "\">");
                }
                html.append(firstValueDTO.getTitle() + " (" + firstValueDTO.getYearOfCreation() + ")");
                html.append("</a>");
                html.append("</td>");
                html.append("</tr>");
            }
            // iterate through the rest of the values
            while (valuesIt.hasNext()) {
                html.append("<tr>");
                html.append("<td>");
                MediaItemDTO valueDTO = (MediaItemDTO) valuesIt.next();
                // display first child IMR'ed media item
                html.append("<td>");
                html.append("<a href=\"");
                if (publicFlag) {
                    html.append("/bibWebApp/actions/public/getMediaItemDetails?mediaItemID=" + valueDTO.getID() + "\">");
                }
                else {
                    // temporary hack. should be /bibWebApp/actions/management/getMediaItemDetails
                    html.append("/bibWebApp/actions/public/getMediaItemDetails?mediaItemID=" + valueDTO.getID() + "\">");
                }
                html.append(valueDTO.getTitle() + " (" + valueDTO.getYearOfCreation() + ")");
                html.append("</a>");
                html.append("</td>");
                html.append("</tr>");
            }
        }
        return html;
    }
    
}
