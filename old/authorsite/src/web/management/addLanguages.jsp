<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/addLanguages.jsp,v 1.3 2003/03/29 13:52:09 jejking Exp $
    Created: 11 February 2003
    Author: jejking
    Version: $Revision: 1.3 $

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
        <title><bean:message key="web.management.createMediaItem.addLanguages.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.management.createMediaItem.addLanguages.pageHeader"/></h1>   
        
        <bean:message key="web.management.createMediaItem.addLanguags.blurb"/>
        <!-- display a summary view of the active media item. Needs a custom tag I haven't written yet -->
        
        <html:form action="/management/addLanguagesToItem">
            <!-- display the languages on the clipboard, if there are languages on the clipboard -->
            <logic:present name="LanguagesClipboardSet" scope="session">
                <h2><bean:message key="web.management.clipboard.languagesOnClipboardHeader"/></h2>
                <table>
                    <logic:iterate name="LanguagesClipboardSet" id="language" scope="session">
                        <tr>
                            <td>
                                <bean:message bundle="LANGUAGES_KEY" key="%= (String)language %"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:multibox property="languages">
                                    <bean:write name="language" />
                                </html:multibox> 
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
            </logic:present>
            <!-- display the Priority 1 languages -->
            <h2><bean:message key="web.management.createMediaItem.addLanguages.PriorityOneLanguagesHeader"/></h2>
                <table>
                    <logic:iterate name="languagesOneList" id="language" scope="application">
                        <!-- ideally, we'd check to see if priority one languages are in the clipboard collection and
                            not display them here if they are -->
                        <tr>
                            <td>
                                <bean:message bundle="LANGUAGES_KEY" key="%= (String)language %"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <html:multibox property="languages">
                                    <bean:write name="language" />
                                </html:multibox> 
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
              <p>
                <html:submit value="submit"/>
              </p>
        </html:form>
        
        <p>
            <html:form action="/management/clipboard/selectLanguagesTwo">
                <html:hidden property="returnTarget" value="editing_context"/>
            <!--<a href="/action/management?returnTarget=editing_context"><bean:message key="web.management.clipboard.selectLangs.moreLangsLink"/></a>-->
                <html:submit value="More Languages"/>
            </html:form>
        </p>
    </block>
</doc>