<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/createPerson.jsp,v 1.2 2003/03/29 13:52:09 jejking Exp $
    Author: jejking
    Version: $Revision: 1.2 $
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
     xmlns:bean="/WEB-INF/struts-bean.tld">
     
    <ia>
        <title><bean:message key="web.management.peopleManagement.createPerson.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.management.peopleManagement.createPerson.pageHeader"/></h1>   
            
            <html:errors/>
            <html:form action="/management/createPerson">
                <table>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.prefix"/>
                         </td>
                         <td>
                            <html:text property="prefix" />
                         </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.givenName"/>
                         </td>
                         <td>
                            <html:text property="givenName" />
                         </td>
                     </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.mainName"/>
                         </td>
                         <td>
                            <html:text property="mainName" />
                         </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.otherNames"/>
                         </td>
                         <td>
                            <html:text property="otherNames" />
                         </td>
                     </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.suffix"/>
                         </td>
                         <td>
                            <html:text property="suffix" />
                         </td>
                     </tr>
                     <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.genderCode"/>
                        </td>
                        <td>
                            <html:select property="genderCode">
                                <html:option key="web.bibPublic.formlabels.genderCode.notSpecified" value="0"/>
                                <html:option key="web.bibPublic.formlabels.genderCode.male" value="1"/>
                                <html:option key="web.bibPublic.formlabels.genderCode.female" value="2"/>
                                <html:option key="web.bibPublic.formlabels.genderCode.unknown" value="9"/>
                            </html:select>
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