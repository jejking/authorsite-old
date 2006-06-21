<?xml version="1.0" encoding="UTF-8" ?>
<!--

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

<doc xmlns:html="/WEB-INF/struts-html.tld"
     xmlns:logic="/WEB-INF/struts-logic.tld"
     xmlns:bean="/WEB-INF/struts-bean.tld"
     xmlns:bibWebApp="/WEB-INF/bibWebApp.tld">
     
    <ia>
        <title><bean:message key="web.bibPublic.personDetailsResult.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.bibPublic.personDetailsResult.pageHeader"/></h1> 

        <table>
            <bibWebApp:person checkBoxFlag="false" detail="full"/>
        </table>
        <h2><bean:message key="web.bibPublic.mediaItemSearch.personSearchHeader"/></h2>
        <!-- build org specific search form -->
        <html:form action="/public/personMediaItemSearch">
        <!-- CUSTOM TAG TO SET ID FIELD -->
        <bibWebApp:hiddenID beanName="CurrentPersonDTO" property="personID"/>
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
                        <bean:message key="web.bibPublic.formlabels.likeFlag"/>
                    </td>
                    <td>
                        <html:checkbox property="titleLikeFlag"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <bean:message key="web.bibPublic.formlabels.productionRelationship"/>
                    </td>
                    <td>
                        <html:select property="productionRelationship">
                            <html:option key="web.bibPublic.formlabels.noProductionRelationshipSearch" value=""/>
                            <logic:iterate name="allMediaProductionRelationshipsSet" id="prodRel" scope="application">
                                <html:option bundle="MEDIA_TYPES_KEY" key="%=(String)prodRel%" value="%=(String)prodRel%"/>
                            </logic:iterate>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <bean:message key="web.bibPublic.formlabels.mediaType"/>
                    </td>
                    <td>
                        <html:select property="mediaType">
                            <html:option key="web.bibPublic.formlabels.noMediaTypeSearch" value=""/>
                            <logic:iterate id="currentMediaType" name="allMediaTypesSet" scope="application">
                                <html:option bundle="MEDIA_TYPES_KEY" key="%= (String)currentMediaType%" value="%= (String)currentMediaType%"/>
                            </logic:iterate>
                        </html:select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <html:submit value="submit"/>
                    </td>
                </tr>
            </table>
        </html:form>

    </block>

</doc>