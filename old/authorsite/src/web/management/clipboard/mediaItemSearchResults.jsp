<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/clipboard/mediaItemSearchResults.jsp,v 1.1 2003/03/29 13:54:46 jejking Exp $
     Author: jejking
     Version: $Revision: 1.1 $
     Created: 22 February 2003

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
        <title><bean:message key="web.bibPublic.mediaItemSearchResults.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.bibPublic.mediaItemSearchResults.pageHeader"/></h1> 
        <p><bean:message key="web.bibPublic.mediaItemSearchResults.followingFound"/></p>
        <html:form action="/management/clipboard/addMediaItemsToClipboard">
            <table>
                <logic:iterate name="mediaItemsCollection" scope="request" id="CurrentMediaItemDTO">
                    <bibWebApp:mediaItem checkBoxFlag="true" publicFlag="false" detail="concise"/>
                </logic:iterate>
                <tr>
                    <td colspan="2">
                        <html:submit value="submit"/>
                    </td>
                </tr>
            </table>
        </html:form>

    </block>

</doc>