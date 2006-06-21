<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : MediaItemDetailsCreateActionGen.xsl
    Created on : 10 February 2003, 16:05
    Author     : jejking
    Description:
        Generates struts actions to create the requisite media type record in the 
        database via the facade and appropriate entity EJBs.
    Version: $Revision$
    
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
-->

<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:types="http://www.authorsite.org/bibFieldTypeMappings">
    <xsl:output method="text"/>
    <xsl:include href="textUtil.xsl"/>
    
    <!-- type map -->
    <types:type fieldType="string">java.lang.String</types:type>
    <types:type fieldType="integer">int</types:type>
    <types:type fieldType="float">float</types:type>
    <types:type fieldType="text">java.lang.String</types:type>
    <types:type fieldType="blob">java.lang.Object</types:type>
    <types:type fieldType="boolean">boolean</types:type>

    <!-- template rule matching source root element -->
    <xsl:template match="/">
        <xsl:apply-templates select="mediaType"/>
    </xsl:template>
    
    <xsl:variable name="capitalisedTypeName">
        <xsl:call-template name="capitaliseFirstLetter">
            <xsl:with-param name="word" select="mediaType/@name"/>
        </xsl:call-template>
    </xsl:variable>
    
<xsl:template match="mediaType" name="buildJavaClass">
/*
 * <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsForm.java
 * 
 * This java source file was automatically generated by MediaItemDetailsCreateActionGen.xsl from the 
 * standard bibTypesRelationships.xml as part of the authorsite.org bibliography application
 * build process. 
 *
 * Do not edit this source file directly, but make any alterations to MediaItemDetailsCreateActionGen.xsl
 */
 
package org.authorsite.bib.web.struts.action; 
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.facade.*;
import org.authorsite.bib.web.struts.form.*;
import org.authorsite.bib.web.struts.util.*;
import org.authorsite.bib.web.util.EJBHomeFactory;

public class <xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="@name"/>
    </xsl:call-template>DetailsCreateAction extends BibAbstractAction {
   
    // no args constructor
    public <xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="@name"/>
    </xsl:call-template>DetailsCreateAction() {
    }
   
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        HttpSession session = request.getSession();
    
        // make a MediaItemDTO out of what we have in the ActionForm + the mediaItemID of the "ActiveMediaItem" in the session
        <xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="@name"/>
    </xsl:call-template>DetailsDTO detailsDTO = constructDTO(form, session);
    
        // get reference to MediaItemManagementFacade
        EJBHomeFactory ejbHomeFactory = EJBHomeFactory.getInstance();
        MediaItemManagementFacadeHome home = (MediaItemManagementFacadeHome) ejbHomeFactory.lookupHome("ejb/MediaItemManagementFacadeEJB", MediaItemManagementFacadeHome.class);
        MediaItemManagementFacade facade = home.create();
        
        facade.createMediaItemDetails(detailsDTO);
        // at this point we can add the DetailsDTO to the "ActiveMediaItem" in the session
        MediaItemDTO activeMediaItem = (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        activeMediaItem.setDetailsDTO(detailsDTO);
    
        return mapping.findForward("detailsCreated");
    }
   
    <xsl:call-template name="dtoConstructionMethod">
        <xsl:with-param name="mediaItem" select="current()"/>
    </xsl:call-template>
   
   
}
</xsl:template>

<xsl:template name="dtoConstructionMethod">
    <xsl:param name="mediaItem"/>
    private <xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="$mediaItem/@name"/>
    </xsl:call-template>DetailsDTO constructDTO(ActionForm form, HttpSession session) {
    
        <!-- first we will extract the values from the form bean -->
        <xsl:for-each select="$mediaItem/field">
            <xsl:call-template name="extractFieldValueMethod">
                <xsl:with-param name="field" select="current()"/>
            </xsl:call-template>
        </xsl:for-each>
        <!-- create new DTO object -->
        <xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="@name"/>
    </xsl:call-template>DetailsDTO dto = new <xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="@name"/>
    </xsl:call-template>DetailsDTO();
    
        <!-- set the ID on the DTO -->
        
        MediaItemDTO activeMediaItem = (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        dto.set<xsl:value-of select="$capitalisedTypeName"/>DetailsID(activeMediaItem.getMediaItemID());
    
        <!-- go through the fields in the ActionForm. Use the types to determine whether or not to set something to 
        an int, float, etc and then call the appropriate setter method if that field is not null or "" -->
        <xsl:for-each select="$mediaItem/field">
            <xsl:call-template name="callDTOSetMethod">
                <xsl:with-param name="field" select="current()"/>
            </xsl:call-template>
        </xsl:for-each>
        
        <!-- having called all the appropriate set methods, return the DTO -->
        return dto;
    }
</xsl:template>

<xsl:template name="callDTOSetMethod">
    <xsl:param name="field"/>
    
    <xsl:variable name="dtoSetter">
        dto.set<xsl:call-template name="capitaliseFirstLetter">
            <xsl:with-param name="word" select="$field/@fieldName"/>
            </xsl:call-template>(<xsl:value-of select="$field/@fieldName"/>);
    </xsl:variable>
    
    <xsl:choose>
        <xsl:when test="$field/@fieldType='boolean'">
            <xsl:value-of select="$dtoSetter"/>
        </xsl:when>
        <xsl:when test="$field/@fieldType='blob'">
            if (<xsl:value-of select="$field/@fieldName"/> != null &amp;&amp; <xsl:value-of select="$field/@fieldName"/>.length() &gt; 0) {
                <xsl:value-of select="$dtoSetter"/>
            }
        </xsl:when>
        <xsl:when test="$field/@fieldType='integer'">
            if (<xsl:value-of select="$field/@fieldName"/> != null &amp;&amp; <xsl:value-of select="$field/@fieldName"/>.length() &gt; 0) {
                int i = Integer.parseInt(<xsl:value-of select="$field/@fieldName"/>);
                dto.set<xsl:call-template name="capitaliseFirstLetter">
            <xsl:with-param name="word" select="$field/@fieldName"/>
            </xsl:call-template>(i);
            }
        </xsl:when>
        <xsl:when test="$field/@fieldType='float'"> 
            if (<xsl:value-of select="$field/@fieldName"/> != null &amp;&amp; <xsl:value-of select="$field/@fieldName"/>.length() &gt; 0) {
                float f = Float.parseFloat(<xsl:value-of select="$field/@fieldName"/>);
                dto.set<xsl:call-template name="capitaliseFirstLetter">
            <xsl:with-param name="word" select="$field/@fieldName"/>
            </xsl:call-template>(f);
            }
        </xsl:when>
        <xsl:otherwise> <!-- otherwise it's a string or a text field -->
            if (<xsl:value-of select="$field/@fieldName"/> != null &amp;&amp; <xsl:value-of select="$field/@fieldName"/>.length() &gt; 0) {
                <xsl:value-of select="$dtoSetter"/>
            }
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name="extractFieldValueMethod">
    <xsl:param name="field"/>
    <xsl:choose>
        <xsl:when test="$field/@fieldType='boolean'">
            boolean <xsl:value-of select="$field/@fieldName"/> = ((<xsl:value-of select="$capitalisedTypeName"/>DetailsForm)form).get<xsl:call-template name="capitaliseFirstLetter"><xsl:with-param name="word" select="$field/@fieldName"/></xsl:call-template>();
        </xsl:when>
        <xsl:when test="$field/@fieldType='blob'">
            Object <xsl:value-of select="$field/@fieldName"/> = ((<xsl:value-of select="$capitalisedTypeName"/>DetailsForm)form).get<xsl:call-template name="capitaliseFirstLetter"><xsl:with-param name="word" select="$field/@fieldName"/></xsl:call-template>();
        </xsl:when>
        <xsl:otherwise>
            String <xsl:value-of select="$field/@fieldName"/> = ((<xsl:value-of select="$capitalisedTypeName"/>DetailsForm)form).get<xsl:call-template name="capitaliseFirstLetter"><xsl:with-param name="word" select="$field/@fieldName"/></xsl:call-template>();
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet> 
