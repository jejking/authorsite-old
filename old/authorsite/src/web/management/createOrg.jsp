<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/createOrg.jsp,v 1.2 2003/03/29 13:52:09 jejking Exp $
    Created: 31 January 2003
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
        <title><bean:message key="web.management.orgManagement.createOrg.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.management.orgManagement.createOrg.pageHeader"/></h1>   
            
            <html:errors/>
            <html:form action="/management/createOrg">
                <table>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.orgName"/>
                         </td>
                         <td>
                            <html:text property="name" />
                         </td>
                    </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.cityName"/>
                         </td>
                         <td>
                            <html:text property="city" />
                         </td>
                     </tr>
                    <tr>
                        <td>
                            <bean:message key="web.bibPublic.formlabels.countryName"/>
                         </td>
                         <td>
                            <html:text property="country" />
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