<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : createMediaItemCoreJSP.xsl
    Created on : 06 February 2003, 17:20
    Author     : jejking
    Description:
        Writes the authorsite.org basic jsp source for createMediaItemCore.jsp

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
    xmlns:html="/WEB-INF/struts-html.tld"
    xmlns:logic="/WEB-INF/struts-logic.tld"
    xmlns:bean="/WEB-INF/struts-bean.tld"
    xmlns:bib="http://www.authorsite.org/bibTypesRelationships"
    exclude-result-prefixes="bib">
    
    <xsl:output method="xml"
                indent="yes"/>
                
    
    
    <xsl:template match="/">
    <doc xmlns:html="/WEB-INF/struts-html.tld"
     xmlns:logic="/WEB-INF/struts-logic.tld"
     xmlns:bean="/WEB-INF/struts-bean.tld">

     <ia>
        <title><bean:message key="web.management.createMediaItem.createCore.pageTitle"/></title>
     </ia>
     <block id="main">
        <h1><bean:message key="web.management.createMediaItem.createCore.pageHeader"/></h1>   
            
            <html:errors/>
            <html:form action="/management/createMediaItemCore">
                <table>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.title"/>
                        </td>
                        <td>
                            <html:text property="title"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.mediaType"/>
                        </td>
                        <td>
                            <html:select property="mediaType" >
                            <xsl:for-each select="/bib:typesAndRelationships/mediaType">
                                <html:option bundle="MEDIA_TYPES_KEY" key="{@name}" value="{@name}"/>
                            </xsl:for-each>
                            </html:select>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.year"/>
                        </td>
                        <td>
                            <html:text property="year"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.additionalInfo"/>
                        </td>
                        <td>
                            <html:textarea cols="30" rows="10" property="additionalInfo"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.comment"/>
                        </td>
                        <td>
                            <html:textarea cols="30" rows="10" property="comment"/>
                        </td>
                    </tr>
                    <tr>
                       <td colspan="2">
                            <html:submit value="Submit"/>
                       </td>
                   </tr>
                </table>
            </html:form>
     </block>
</doc>
    </xsl:template>

</xsl:stylesheet> 
