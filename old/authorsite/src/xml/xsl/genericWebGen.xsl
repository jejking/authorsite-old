<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE stylesheet [
    <!ENTITY nbsp  " " >
<!ENTITY copy "copyright">

]>

<!--


    Document   : genericWebGen.xsl
    Created on : 16 January 2003, 14:49
    Author     : jejking
    Description:
        Generates output that is shared by both the html and the jsp pages.
    $Header$
    $Revision$
    
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
    xmlns:nav="http://www.authorsite.org/topLevelNav"
    exclude-result-prefixes="nav">

    <xsl:output method="xml" indent="yes"/>
    

        
    <xsl:template name="buildMainBody">
            <body>
                <div class="container" title="container for all content">
                    <div id="header" class="header" title="header with logo and search">
                        <div class="logo" title="logo">
                            <a id="top">
                                <img src="images/logo20.jpg" width="100" height="67" alt="site logo" title="&lt;bib/&gt;"/>
                            </a>
                        </div>
                        
                        <xsl:choose>
                            <xsl:when test="$htmlOnly">
                            </xsl:when>
                            <xsl:otherwise>
                                <div class="simpleSearch" title="site wide search">
                                    <form action="actions/search" method="post">
                                    <fieldset>
                                        <legend>search</legend>
                                        <input type="text" size="20" maxlength="100"/> 
                                        <input value="GO" type="submit"/>
                                    </fieldset>
                                </form>
                            </div>
                           </xsl:otherwise>
                        </xsl:choose>
                    </div>
                    
                    <!-- build top navigation -->
                    <map id="topNavMap">
                    <div id="topNav" class="topNav" title="navigation with links">
                        <xsl:choose>
                            <xsl:when test="$htmlOnly">
                                 <xsl:call-template name="buildNavBar">
                                    <xsl:with-param name="where" select="'top'"/>
                                    <xsl:with-param name="topLevelCategories" select="document('htmlOnly.xsl')/*/nav:toplevelCategory"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="buildNavBar">
                                    <xsl:with-param name="where" select="'top'"/>
                                    <xsl:with-param name="topLevelCategories" select="document('application.xsl')/*/nav:toplevelCategory"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                       
                    </div>
                    </map>
                    <!-- build category hierarchy if needed -->
                    
                    <!-- build main content area -->
                    <div id="mainContent" class="mainContent">
                        
                        <!-- left column. the info column -->
                        <xsl:if test="/doc/block[@id=info]">
                            <xsl:call-template name="buildColumn">
                                <xsl:with-param name="side" select="'info'"/>
                            </xsl:call-template>
                        </xsl:if>
                        
                        <!-- right column. the functions column -->
                        <xsl:if test="/doc/block[@id=functions]">
                            <xsl:call-template name="buildColumn">
                                <xsl:with-param name="side" select="'functions'"/>
                            </xsl:call-template>
                        </xsl:if>
                        
                        <!-- main column -->
                        <xsl:call-template name="buildCentre"/>
                        
                        <!-- footer -->
                        <div id="footer" class="footer" title="footer with copyright and navigation information">
                            <p>
                                <a href="#top">top</a>
                            </p>
                            <p>
                                <xsl:choose>
                            <xsl:when test="$htmlOnly">
                                 <xsl:call-template name="buildNavBar">
                                    <xsl:with-param name="where" select="'bottom'"/>
                                    <xsl:with-param name="topLevelCategories" select="document('htmlOnly.xsl')/*/nav:toplevelCategory"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:call-template name="buildNavBar">
                                    <xsl:with-param name="where" select="'bottom'"/>
                                    <xsl:with-param name="topLevelCategories" select="document('application.xsl')/*/nav:toplevelCategory"/>
                                </xsl:call-template>
                            </xsl:otherwise>
                        </xsl:choose>
                            </p>
                            <p>
                                &copy; John King, 2003
                            </p>
                        </div>
                        
                    </div>
                </div>
            </body>
    </xsl:template>
    
    <xsl:template name="buildNavBar">
        <xsl:param name="where"/>
        <xsl:param name="topLevelCategories"/>
        <xsl:variable name="toplevelCategory" select="/doc/ia/topcategory"/>
       
        <xsl:for-each select="$topLevelCategories">
            <xsl:choose>
                <xsl:when test="@catName = $toplevelCategory">
                    <xsl:value-of select="@catName"/>
                </xsl:when>
                <xsl:otherwise>
                    <a class="{$where}" href="{@dirName}/index.html" title="{@title}"><xsl:value-of select="@catName"/></a> 
                </xsl:otherwise>
             </xsl:choose>
             <xsl:if test="position() &lt; count($topLevelCategories)">
                &nbsp;|&nbsp;
             </xsl:if>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="buildColumn">
        <xsl:param name="side"/>
        <xsl:variable name="block" select="/doc/block[@id=$side]"/>
        <div id="{$side}" class="{$side}" title="{$block/@title}">
            <!-- process the boxes -->
            <xsl:for-each select="$block/box">
                <div id="{concat($side, position())}" title="{@title}" class="boxItem">
                    <div class="boxHeader"><xsl:value-of select="@title"/></div>
                    <div class="boxContent">
                        <xsl:copy-of select="./*"/>
                    </div>
                </div>
            </xsl:for-each>
        </div>
        
    </xsl:template>
    
    <xsl:template name="buildCentre">
        <xsl:variable name="mainBlock" select="/doc/block[@id='main']"/>
        <div id="centreColumn" class="centreColumn" title="{$mainBlock/@title}">
            <xsl:copy-of select="$mainBlock/*"/>
        </div>
    </xsl:template>
    
    
</xsl:stylesheet> 
