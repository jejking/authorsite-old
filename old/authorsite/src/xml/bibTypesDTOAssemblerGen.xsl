<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : bibTypesDTOAssemblerGen.xsl
    Created on : 26 November 2002, 12:49
    Author     : jejking
    Description:
        Generates custom DTOAssembler classes for the bibliography application. These
        will take a local reference to the corresponding EntityEJB as a parameter and
        call methods on this to assemble the specific DTO for the mediaType desribed in 
        the xml file fed into the stylesheet by ant.
        
 * Copyright (C) 2002 John King
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
    
    <!-- type map -->
    <types:type fieldType="string">java.lang.String</types:type>
    <types:type fieldType="integer">int</types:type>
    <types:type fieldType="float">float</types:type>
    <types:type fieldType="text">java.lang.String</types:type>
    <types:type fieldType="blob">java.lang.Object</types:type>
    <types:type fieldType="boolean">boolean</types:type>
    
    
    <!-- template rule matching source root element -->
    <xsl:template match="/">
        <xsl:apply-templates select="mediaType" mode="buildJavaClass"/>
    </xsl:template>
    
    <xsl:template match="mediaType" mode="buildJavaClass">
/*
 * <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsDTOAssembler.java
 * 
 * This java source file was automatically generated by bibTypesDTOAssemblerGen.xsl from the 
 * standard bibTypesRelationships.xml as part of the authorsite.org bibliography application
 * build process. 
 *
 * Do not edit this source file directly, but make any alterations to bibTypesRelationships.xml
 */
 package org.authorsite.bib.ejb.services.dto;
 import org.authorsite.bib.dto.*;
 import org.authorsite.bib.ejb.entity.*;
  public class <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsDTOAssembler {
   
   
   private <xsl:call-template name="capitaliseFirstLetter"> <!-- declare private members. Ref to entity bean and dto -->
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsLocal myEntityLocal;
   private <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsDTO dto;
   
   public <xsl:call-template name="capitaliseFirstLetter"> <!-- constructor takes ref to local interface of entity bean -->
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsDTOAssembler (<xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsLocal newEntityLocal) {
        myEntityLocal = newEntityLocal;
        dto = new <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsDTO(myEntityLocal.get<xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsID());
   }
   
   public <xsl:call-template name="capitaliseFirstLetter"> 
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsDTO assembleDTO() {
        <xsl:for-each select="field">
        dto.set<xsl:call-template name="capitaliseFirstLetter"> 
   <xsl:with-param name="word" select="@fieldName"/>
   </xsl:call-template>(myEntityLocal.get<xsl:call-template name="capitaliseFirstLetter"> 
   <xsl:with-param name="word" select="@fieldName"/>
   </xsl:call-template>());
        </xsl:for-each>
        return dto;
   }

   }
   </xsl:template> 
       <!-- to do. This is common functionality in our xslt files. Make an xslt file with common functionality in and share it -->
    <xsl:template name="capitaliseFirstLetter">
        <xsl:param name="word"/>
<xsl:value-of select="translate (substring($word, 1, 1), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/><xsl:value-of select="substring($word, 2)"/>
   </xsl:template>
</xsl:stylesheet> 
