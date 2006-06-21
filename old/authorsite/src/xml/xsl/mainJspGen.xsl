<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : mainJspGen.xsl
    Created on : 17 January 2003, 19:38
    Author     : jejking
    Description:
        Generates full xml compatible layout for jsp source pages (JSP 1.2) using genericeWebGen.xsl
        
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
    xmlns:bibWebApp="/WEB-INF/bibWebApp.tdl">

    <xsl:output method="xml"
                indent="yes"/>
    
    <xsl:include href="genericWebGen.xsl"/>
    
    <xsl:param name="today"/>
    <xsl:param name="base"/>
    <xsl:param name="htmlOnly"/>                
                
    <!-- template rule matching source root element -->
    <xsl:template match="/">
        <jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
          xmlns:html="/WEB-INF/struts-html.tld"
          xmlns:logic="/WEB-INF/struts-logic.tld"
          xmlns:bean="/WEB-INF/struts-bean.tld"
          xmlns:bibWebApp="/WEB-INF/bibWebApp.tld"
          version="1.2">
        <jsp:directive.page contentType="text/html;charset=UTF-8"/>
            <jsp:text>
                <html:html locale="true" xhtml="true" >
                    <head>
                       <xsl:if test="$base">
                         <base href="{$base}"/>
                       </xsl:if>
                        <title>
                            <xsl:copy-of select="/doc/ia/title/*"/>
                        </title>
                        <link rel="stylesheet" href="css/main.css" type="text/css"/>
                    </head>
                    <xsl:call-template name="buildMainBody"/>
                </html:html>
            </jsp:text>
        </jsp:root>
    </xsl:template>

</xsl:stylesheet> 
