<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/clipboard/selectLanguagesThree.jsp,v 1.3 2003/03/29 13:54:46 jejking Exp $
     Author: jejking
     Version: $Revision: 1.3 $
     Created: 14 February 2003

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
        <title><bean:message key="web.management.clipboard.selectLangs.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.management.clipboard.selectLangs.pageHeader"/></h1>
        <bean:message key="web.management.clipboard.selectLangs.blurb"/>

        <!-- display any languages already on the clipboard -->
        <logic:present name="LanguagesClipboardCollectionBean" scope="session">
                <h2><bean:message key="web.management.clipboard.languagesOnClipboardHeader"/></h2>
                <table>
                    <logic:iterate name="LanguagesClipboardSet" id="language" scope="session">
                        <tr>
                            <td>
                                <bean:message bundle="LANGUAGES_KEY" key="%= (String)language %"/>
                            </td>
                        </tr>
                     </logic:iterate>
                </table>
            </logic:present>
        
        <h2><bean:message key="web.management.clipboard.priority3languagesHeader"/></h2>
        <table>
            <html:form action="/management/clipboard/addLangsToClipboard">
                <logic:iterate id="language" name="languagesThreeList" scope="application">
                    <tr>
                        <td>
                            <bean:message name="language" bundle="LANGUAGES_KEY" key="%= (String)language %" />
                        </td>
                     <td>
                        <html:multibox property="languages">
                            <bean:write name="language" />
                         </html:multibox> 
                    </td>
                </tr>
            </logic:iterate>
            <tr>
                <td colspan="2">
                    <html:submit value="submit"/>
                </td>
            </tr>
            <html:hidden property="returnTarget"/>
            </html:form>
         </table>

         <p>
            <html:link action="/management/clipboard/selectLanguagesTwo"><bean:message key="web.management.clipboard.selectLangs.moreLangsLink"/></html:link>
         </p>
    </block>

</doc>