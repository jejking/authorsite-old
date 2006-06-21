<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/clipboard/clipboardUpdated.jsp,v 1.5 2003/03/29 13:54:46 jejking Exp $
    Created: 04 February 2003
    Author: jejking
    Version: $Revision: 1.5 $

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
        <title><bean:message key="web.management.clipboard.clipboardUpdated.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.management.clipboard.clipboardUpdated.pageHeader"/></h1>   
            
            <logic:present name="PeopleClipboardMap" scope="session">
                <h2><bean:message key="web.management.clipboard.peopleOnClipboardHeader"/></h2>

                    <table>
                        <bibWebApp:peopleClipboard>
                            <bibWebApp:person detail="concise" checkBoxFlag="false"/>
                        </bibWebApp:peopleClipboard>
                    </table>
           </logic:present>

           <logic:present name="OrgsClipboardMap" scope="session">
                <h2><bean:message key="web.management.clipboard.orgsOnClipboardHeader"/></h2>
                <table>
                    <bibWebApp:orgsClipboard>
                        <bibWebApp:org detail="concise" checkBoxFlag="false"/>
                    </bibWebApp:orgsClipboard>
                </table>
            </logic:present>
            
            <logic:present name="MediaItemsClipboardMap" scope="session">
                <h2><bean:message key="web.management.clipboard.mediaItemsOnClipboardHeader"/></h2>
                <table>
                    <bibWebApp:mediaItemsClipboard>
                        <bibWebApp:mediaItem detail="concise" publicFlag="false" checkBoxFlag="false"/>
                    </bibWebApp:mediaItemsClipboard>
                </table>
            </logic:present>

            <logic:present name="LanguagesClipboardSet" scope="session">
                <h2><bean:message key="web.management.clipboard.languagesOnClipboardHeader"/></h2>
                <logic:iterate name="LanguagesClipboardSet" id="language" scope="session">
                    <p>
                        <bean:message name="language" bundle="LANGUAGES_KEY" key="%= (String)language %" />
                    </p>
                </logic:iterate>
            </logic:present>

       </block>
</doc>