<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/clipboard/index.jsp,v 1.5 2003/03/29 13:54:46 jejking Exp $
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
        <title><bean:message key="web.management.clipboard.index.pageTitle"/></title>
    </ia>
    <block id="main">
        <h1><bean:message key="web.management.clipboard.index.pageHeader"/></h1>   
            <html:errors/>
            <bean:message key="web.management.clipboard.index.useOfClipboard"/>
            
       <h2><bean:message key="web.management.clipboard.index.optionsHeader"/></h2>
       <h3><bean:message key="web.management.clipboard.index.searchOptionsHeader"/></h3>
      
      <ul>
            <li><html:link action="/management/clipboard/personSearchInit"><bean:message key="web.management.clipboard.personSearch"/></html:link></li>
            <li><html:link action="/management/clipboard/orgSearchInit"><bean:message key="web.management.clipboard.orgSearch"/></html:link></li>
            <li><html:link action="/management/clipboard/selectLanguagesInit"><bean:message key="web.management.clipboard.selectLanguages"/></html:link></li>
            <li><html:link action="/management/clipboard/mediaItemSearchInit"><bean:message key="web.management.clipboard.mediaItemSearch"/></html:link></li>
       </ul>
       
       <!--
       <logic:present name="MediaItemsClipboardCollectionBean" scope="session">

       </logic:present>
       -->
       <logic:present name="PeopleClipboardMap" scope="session">
            <h2><bean:message key="web.management.clipboard.peopleOnClipboardHeader"/></h2>
            <table>
            <!-- iterate through people records. make a form. checkboxes. submit button to zap the checked references -->
                    <html:form action="/management/clipboard/removePeopleFromClipboard">
                            <bibWebApp:peopleClipboard>
                                <bibWebApp:person detail="concise" checkBoxFlag="true"/>
                            </bibWebApp:peopleClipboard>
                            <tr colspan="2">
                                <td>
                                    <html:submit value="submit"/>
                                </td>
                            </tr>
                    </html:form>
                </table>
            <!-- option to clear people clipboard completely -->
            <p>
                <a href="/bibWebApp/actions/management/clipboard/clearClipboard?clipboard=people">
                    <bean:message key="web.management.clipboard.index.clearClipboard.people"/>
                </a>
            </p>
       </logic:present>

       
       <logic:present name="OrgsClipboardMap" scope="session">
            <h2><bean:message key="web.management.clipboard.orgsOnClipboardHeader"/></h2>
            <table>
            <!-- iterate through people records. make a form. checkboxes. submit button to zap the checked references -->
                    <html:form action="/management/clipboard/removeOrgsFromClipboard">
                        <bibWebApp:orgsClipboard>
                            <bibWebApp:org detail="concise" checkBoxFlag="true"/>
                        </bibWebApp:orgsClipboard>
                            <tr>
                                <td colspan="2">
                                    <html:submit value="submit"/>
                                </td>
                            </tr>
                    </html:form>
                </table>
            <!-- option to clear orgs clipboard completely -->
            <p>
                <a href="/bibWebApp/actions/management/clipboard/clearClipboard?clipboard=orgs">
                    <bean:message key="web.management.clipboard.index.clearClipboard.orgs"/>
                </a>
            </p>
       </logic:present>
       
    <!-- iterate over the media items on the clipboard -->
       <logic:present name="MediaItemsClipboardMap" scope="session">
            <h2><bean:message key="web.management.clipboard.mediaItemsOnClipboardHeader"/></h2>
                <html:form action="/management/clipboard/removeMediaItemsFromClipboard">
                    <table>
                        <bibWebApp:mediaItemsClipboard>
                            <bibWebApp:mediaItem detail="concise" publicFlag="false" checkBoxFlag="true"/>
                        </bibWebApp:mediaItemsClipboard>
                        <tr>
                            <td colspan="2">
                                <html:submit value="submit"/>
                            </td>
                        </tr>
                    </table>
                 </html:form>
                 <p>
                <a href="/bibWebApp/actions/management/clipboard/clearClipboard?clipboard=mediaItems">
                    <bean:message key="web.management.clipboard.index.clearClipboard.mediaItems"/>
                </a>
            </p>
       </logic:present>
       <!-- iterate over the languages on the clipboard -->
       <logic:present name="LanguagesClipboardSet" scope="session">
            <h2><bean:message key="web.management.clipboard.languagesOnClipboardHeader"/></h2>
            <table>
                <html:form action="/management/clipboard/removeLanguagesFromClipboard">
                    <logic:iterate name="LanguagesClipboardSet" id="language" scope="session">
                        <tr>
                            <td>
                                <bean:message bundle="LANGUAGES_KEY" key="%= (String)language %"/>
                            </td>
                            <td>
                                <html:multibox property="languages">
                                    <bean:write name="language" />
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
            <p>
                <a href="/bibWebApp/actions/management/clipboard/clearClipboard?clipboard=languages">
                    <bean:message key="web.management.clipboard.index.clearClipboard.langs"/>
                </a>
            </p>
       </logic:present>
       </block>
</doc>