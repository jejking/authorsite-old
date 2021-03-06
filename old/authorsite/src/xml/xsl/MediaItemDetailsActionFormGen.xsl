<?xml version="1.0" encoding="UTF-8" ?>

<!--
    Document   : MediaItemDetailsActionFormGen.xsl
    Created on : 09 February 2003, 19:16
    Author     : jejking
    Description:
        Generates struts action forms to hold and validate the data entered from the media type
        specific creation/edition JSPs.
        
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
    
<xsl:template match="mediaType" name="buildJavaClass">
/*
 * <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsForm.java
 * 
 * This java source file was automatically generated by MediaItemDetailsActionFormGen.xsl from the 
 * standard bibTypesRelationships.xml as part of the authorsite.org bibliography application
 * build process. 
 *
 * Do not edit this source file directly, but make any alterations to MediaItemDetailsActionFormGen.xml
 */
 
package org.authorsite.bib.web.struts.form; 
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.authorsite.bib.web.util.*;
import org.authorsite.bib.dto.*;

public class <xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="@name"/>
   </xsl:call-template>DetailsForm extends ActionForm {

   <!-- declare member variables -->
   <xsl:for-each select="field">
        <xsl:call-template name="generateMemberDeclaration">
            <xsl:with-param name="fieldName" select="@fieldName"/>
            <xsl:with-param name="fieldType" select="@fieldType"/>
        </xsl:call-template>
   </xsl:for-each>
   
   <!-- generate getters and setters for each field -->
   <xsl:for-each select="field">
        <xsl:call-template name="generateGetMethod">
            <xsl:with-param name="fieldName" select="@fieldName"/>
            <xsl:with-param name="fieldType" select="@fieldType"/>
        </xsl:call-template>
        <xsl:call-template name="generateSetMethod">
            <xsl:with-param name="fieldName" select="@fieldName"/>
            <xsl:with-param name="fieldType" select="@fieldType"/>
        </xsl:call-template>
   </xsl:for-each>
   
   
   /**
    * &lt;p&gt;
    * Automatically generated validation method. The validations used are the following:
    * &lt;/p&gt;
    * &lt;ul&gt;
    * &lt;li&gt;int fields are verified as integers&lt;/li&gt;
    * &lt;li&gt;float fields are verified as floats&lt;/li&gt;
    * &lt;li&gt;string field sizes are checked so that they are in accordance with
    * the sql generated by org.authorsite.bib.buildutil.xml2sql.TypesAndRelationshipsSQLGenerator&lt;/li&gt;
    * &lt;ul&gt;
    * &lt;li&gt;small - 30 chars&lt;/li&gt;
    * &lt;li&gt;medium - 255 chars&lt;/li&gt;
    * &lt;li&gt;large - 4000 chars&lt;/li&gt;
    * &lt;/ul&gt;
    * &lt;li&gt;text fields need no size checking as they are mapped to the PostgreSQL text type which does not have
    * a maximum size constraint&lt;/li&gt;
    * &lt;/ul&gt;
    */
   public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        MessageResources resources = (MessageResources)req.getAttribute(Action.MESSAGES_KEY);
        
        <!-- very first check. Do we have the correct media type as the "ActiveMediaItem"?? -->
        HttpSession session = req.getSession();
        MediaItemDTO activeMediaItem = (MediaItemDTO) session.getAttribute("ActiveMediaItem");
        if (activeMediaItem == null) {
            // eek! No ActiveMediaItem. Throw error now as any further activity is meaningless.
            ActionError newError = new ActionError(&quot;web.errors.missingActiveMediaItem&quot;);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        else if (!activeMediaItem.getMediaType().equals(&quot;<xsl:value-of select="@name"/>&quot;)) {
            ActionError newError = new ActionError(&quot;bib.errors.ItemAndDetailsSameType&quot;);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
        <!-- build references to the correct text so comprehensible error messages can be returned -->
        <xsl:for-each select="field">
            String <xsl:value-of select="@fieldName"/>Text = resources.getMessage(&quot;web.bibPublic.formlabels.<xsl:value-of select="@fieldName"/>&quot;);
        </xsl:for-each>
        <xsl:for-each select="field">
           <xsl:call-template name="generateValidationStatements">
                <xsl:with-param name="field" select="current()"/>
            </xsl:call-template>
        </xsl:for-each>
        return errors;
    }
   
    <!-- reset -->
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        resetFields();
    }
    
   <!-- private reset fields method to set all member fields to 0, false, "" or whatever according to type -->
   private void resetFields() {
   <xsl:for-each select="field">
        <xsl:call-template name="generateResetStatement">
            <xsl:with-param name="field" select="current()"/>
        </xsl:call-template>
   </xsl:for-each>
   }   
}

</xsl:template>

<xsl:template name="generateMemberDeclaration">
    <xsl:param name="fieldName"/>
    <xsl:param name="fieldType"/> <!-- if fieldType is boolean or Object use corresponding types, otherwise everything's a string -->
    <xsl:choose>
        <xsl:when test="$fieldType='boolean'">
            private boolean <xsl:value-of select="$fieldName"/> = false;
        </xsl:when>
        <xsl:when test="$fieldType='blob'">
            private Object <xsl:value-of select="$fieldName"/>;
        </xsl:when>
        <xsl:otherwise>
            private String <xsl:value-of select="$fieldName"/>;
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name="generateGetMethod">
  <xsl:param name="fieldName"/>
  <xsl:param name="fieldType"/>
  <xsl:choose>
    <xsl:when test="$fieldType='boolean'">
    public boolean get<xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="$fieldName"/></xsl:call-template>() {
        return <xsl:value-of select="$fieldName"/>;
    }
    </xsl:when>
    <xsl:when test="$fieldType='blob'">
    public Object get<xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="$fieldName"/></xsl:call-template>() {
        return <xsl:value-of select="$fieldName"/>;
    }
    </xsl:when>
    <xsl:otherwise>
    public String get<xsl:call-template name="capitaliseFirstLetter">
   <xsl:with-param name="word" select="$fieldName"/></xsl:call-template>() {
        return <xsl:value-of select="$fieldName"/>;
    }
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>
    
<xsl:template name="generateSetMethod">
    <xsl:param name="fieldName"/>
    <xsl:param name="fieldType"/>
    <xsl:choose>
    <xsl:when test="$fieldType='boolean'">
    public void set<xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="$fieldName"/></xsl:call-template> (boolean new<xsl:value-of select="$fieldName"/>) {
        <xsl:value-of select="$fieldName"/> = new<xsl:value-of select="$fieldName"/>;
    }
    </xsl:when>
    <xsl:when test="$fieldType='blob'">
    public void set<xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="$fieldName"/></xsl:call-template> (Object new<xsl:value-of select="$fieldName"/>) {
        <xsl:value-of select="$fieldName"/> = new<xsl:value-of select="$fieldName"/>;
    }
    </xsl:when> <!-- otherwise it's got to be a string, as this is a struts form -->
    <xsl:otherwise>
    public void set<xsl:call-template name="capitaliseFirstLetter">
    <xsl:with-param name="word" select="$fieldName"/></xsl:call-template>(String new<xsl:value-of select="$fieldName"/>) {
        <xsl:choose>
        <xsl:when test="$fieldType='string' or $fieldType='text'"> <!-- fields of fieldType "string" and "text" should be trimmed and pumped through cleanComment -->
            <xsl:value-of select="$fieldName"/> = InputChecker.cleanComment(new<xsl:value-of select="$fieldName"/>.trim());
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$fieldName"/> = new<xsl:value-of select="$fieldName"/>;
        </xsl:otherwise>
        </xsl:choose>
    }   
    </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name="generateValidationStatements">
    <xsl:param name="field"/>
    <xsl:if test="$field/@priority='obligatory' and $field/@fieldType != 'boolean'"> <!-- we can't have null booleans -->
        if (<xsl:value-of select="$field/@fieldName"/> == null || <xsl:value-of select="$field/@fieldName"/>.length() == 0) {
            ActionError newError = new ActionError(&quot;web.errors.requiredFieldMissing&quot;, <xsl:value-of select="$field/@fieldName"/>Text);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
    </xsl:if>
    <xsl:choose>
        <xsl:when test="$field/@fieldType='int'">
            if (!InputChecker.isInteger(<xsl:value-of select="$field/@fieldName"/>)) {
                ActionError newError = new ActionError(&quot;web.errors.notAnInteger&quot;, <xsl:value-of select="$field/@fieldName"/>Text);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        </xsl:when>
        <xsl:when test="$field/@fieldType='float'">
            if (<xsl:value-of select="$field/@fieldName"/>.length() > 0 &amp;&amp; !InputChecker.isFloat(<xsl:value-of select="$field/@fieldName"/>)) {
                ActionError newError = new ActionError(&quot;web.errors.notAFloat&quot;, <xsl:value-of select="$field/@fieldName"/>Text);
                errors.add(ActionErrors.GLOBAL_ERROR, newError);
            }
        </xsl:when>
        <xsl:when test="$field/@fieldType='string'">
            <xsl:call-template name="generateStringValidationStatement">
                <xsl:with-param name="fieldName" select="$field/@fieldName"/>
                <xsl:with-param name="fieldSize" select="$field/@fieldSize"/>
            </xsl:call-template>
        </xsl:when>
    <xsl:otherwise/>
        <!-- text, boolean, object do not need any further validation -->
    </xsl:choose>
</xsl:template>

<xsl:template name="generateStringValidationStatement">
    <xsl:param name="fieldName"/>
    <xsl:param name="fieldSize"/>
    <xsl:choose>
        <xsl:when test="$fieldSize='small'">
        if (<xsl:value-of select="$fieldName"/>.length() > 30) {
            ActionError newError = new ActionError(&quot;web.errors.fieldTooLong&quot;, <xsl:value-of select="$fieldName"/>Text, &quot;30&quot;);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        </xsl:when>
        <xsl:when test="$fieldSize='medium'">
        if (<xsl:value-of select="$fieldName"/>.length() > 255) {
            ActionError newError = new ActionError(&quot;web.errors.fieldTooLong&quot;, <xsl:value-of select="$fieldName"/>Text, &quot;255&quot;);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        </xsl:when>
        <xsl:otherwise> <!-- must otherwise be large -->
        if (<xsl:value-of select="$fieldName"/>.length() > 4000) {
            ActionError newError = new ActionError(&quot;web.errors.fieldTooLong&quot;, <xsl:value-of select="$fieldName"/>Text, &quot;4000&quot;);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
        }
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<xsl:template name="generateResetStatement">
    <xsl:param name="field"/>
        <xsl:choose>
            <xsl:when test="$field/@fieldType='boolean'">
                <xsl:value-of select="$field/@fieldName"/> = false;
            </xsl:when>
            <xsl:when test="$field/@fieldType='blob'">
                <xsl:value-of select="$field/@fieldName"/> = null;
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$field/@fieldName"/> = "";
            </xsl:otherwise>
        </xsl:choose>
</xsl:template>

</xsl:stylesheet> 
