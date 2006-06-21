<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/mediaItemSearch.jsp,v 1.1 2003/03/29 13:52:09 jejking Exp $
     Author: jejking
     Version: $Revision: 1.1 $
     Created: 27 March 2003
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
        <title><bean:message key="web.bibPublic.mediaItemSearch.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.bibPublic.mediaItemSearch.pageHeader"/></h1> 
        
        <bean:message key="web.bibPublic.mediaItemSearch.blurb"/>
        <html:errors/>

        <html:form action="/public/mediaItemSearch">
        <table>
            <tr>
                <td>
                    <bean:message key="web.management.clipboard.mediaItemSeach.searchUnpublished"/>
                </td>
                <td>
                    <html:checkbox property="searchUnpublishedFlag"/>
                </td>
            </tr>
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
                <td>
                    <bean:message key="web.bibPublic.formlabels.language"/>
                </td>
                <td>
                    <html:select property="iso639">
                        <html:option key="web.bibPublic.formlabels.noLanguageSearch" value=""/>
                        <logic:iterate id="iso639" name="languagesOneList" scope="application">
                            <html:option bundle="LANGUAGES_KEY" key="%= (String)iso639%" value="%= (String)iso639 %"/>
                        </logic:iterate>
                        <logic:iterate id="iso639" name="languagesTwoList" scope="application">
                            <html:option bundle="LANGUAGES_KEY" key="%= (String)iso639%" value="%= (String)iso639 %"/>
                        </logic:iterate>
                        <logic:iterate id="iso639" name="languagesThreeList" scope="application">
                            <html:option bundle="LANGUAGES_KEY" key="%= (String)iso639 %" value="%= (String)iso639 %"/>
                        </logic:iterate>
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
                    <bean:message key="web.bibPublic.formlabels.yearOperation"/>
                </td>
                <td>
                    <html:select property="yearOperation">
                        <html:option key="web.bibPublic.formlabels.ExactYear" value="ExactYear"/>
                        <html:option key="web.bibPublic.formlabels.BeforeYear" value="BeforeYear"/>
                        <html:option key="web.bibPublic.formlabels.AfterYear" value="AfterYear"/>
                    </html:select>
                </td>
             </tr>
             <tr>
                <td>
                    <bean:message key="web.bibPublic.formlabels.secondYear"/>
                </td>
                <td>
                    <html:text property="secondYear"/>
                </td>
             </tr>
             <tr>
                <td colspan="2">
                    <html:submit value="submit"/>
                </td>
             </tr>
        </table>
        </html:form>
        <!-- we will need to add management specific search options. Particularly the 
        option to search for unpublished items -->

    </block>

</doc>