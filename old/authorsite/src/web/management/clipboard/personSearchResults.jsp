<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/clipboard/personSearchResults.jsp,v 1.2 2003/03/29 13:54:46 jejking Exp $ 
    Author: jejking
    Version: $Revision: 1.2 $
    Created: 04 February 2003

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
<doc     xmlns:html="/WEB-INF/struts-html.tld"
          xmlns:logic="/WEB-INF/struts-logic.tld"
          xmlns:bean="/WEB-INF/struts-bean.tld">
    
        <ia>
            <title><bean:message key="web.management.clipboard.personSearchResults.pageTitle"/></title>
        </ia>
        <block id="main">
            <h1><bean:message key="web.management.clipboard.personSearchResults.pageHeader"/></h1>
            <bean:message key="web.management.clipboard.personSearchResults.blurb"/>
            <p><bean:message key="web.management.clipboard.personSearchResults.followingFound"/></p>
                <table>
                    <html:form action="/management/clipboard/addPeopleToClipboard">
                        <logic:iterate name="PeopleFoundCollectionBean" id="person" property="collection" scope="request" type="org.authorsite.bib.dto.PersonDTO">
                            <tr>
                                <td>
                                    <bean:write name="person" property="mainName"/>
                                    <logic:notEmpty name="person" property="givenName">
                                    , <bean:write name="person" property="givenName"/>
                                    </logic:notEmpty>
                                </td>
                                <td>
                                    <html:multibox property="personID">
                                        <bean:write name="person" property="personID"/>
                                    </html:multibox>
                                </td>
                            </tr>
                        </logic:iterate>
                            <tr colspan="2">
                                <td>
                                    <html:submit value="submit"/>
                                </td>
                            </tr>
                    </html:form>
                </table>
     </block>
</doc>