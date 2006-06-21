<?xml version="1.0" encoding="UTF-8" ?>
<!-- $Header: /cvsroot/authorsite/authorsite/src/web/management/addProductionRelationships.jsp,v 1.2 2003/03/29 13:52:09 jejking Exp $
     Author: jejking
     Version: $Revision: 1.2 $
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
     xmlns:bean="/WEB-INF/struts-bean.tld"
     xmlns:bibWebApp="/WEB-INF/bibWebApp.tld">
     
    <ia>
        <title><bean:message key="web.management.createMediaItem.addProdRels.pageTitle"/></title>
    </ia>    
    <block id="main">    
        <h1><bean:message key="web.management.createMediaItem.addProdRels.pageHeader"/></h1> 
        <bean:message key="web.management.createMediaItem.addProdRels.blurb"/>
        <html:errors/>
        <!-- ideally, display the mediaItem so far here. Wait till we've got our custom tag ready -->
        
        <table>
            <html:form action="/management/addProductionRelationshipsToItem">
            <!-- now, display a drop down of the permitted production relationships for the media type in question -->
            <tr>
                <td>
                    <bean:message key="web.management.createMediaItem.addProdRels.selectProdRels"/>
                </td>
                <td>
                    <html:select property="productionRelationship">
                        <logic:iterate name="ActiveProductionRelationshipsSet" id="relKey" scope="session">
                            <html:option value="%= (String) relKey %">
                                <bean:message bundle="RELATIONSHIPS_KEY" key="%= (String) relKey%"/>
                             </html:option>
                        </logic:iterate>
                    </html:select>
                </td>
            </tr>
            <!-- display all the people on the clipboard with checkboxes -->
            <logic:present name="PeopleClipboardMap" scope="session">
                <tr>
                    <td colspan="2">
                        <h2><bean:message key="web.management.clipboard.peopleOnClipboardHeader"/></h2>
                    </td>
                </tr>

                <bibWebApp:peopleClipboard>
                    <bibWebApp:person detail="concise" checkBoxFlag="true"/>
                </bibWebApp:peopleClipboard>

            </logic:present>
            
            <!-- display all the orgs on the clipboard with checkboxes -->
            <logic:present name="OrgsClipboardMap" scope="session">
                <tr>
                    <td colspan="2">
                        <h2><bean:message key="web.management.clipboard.orgsOnClipboardHeader"/></h2>
                    </td>
                </tr>
                <bibWebApp:orgsClipboard>
                    <bibWebApp:org detail="concise" checkBoxFlag="true"/>
                </bibWebApp:orgsClipboard>                

            </logic:present>
            <!-- an option to finish entering production relationships. Otherwise once form info is processed
            will return here rather than to start adding intra media relationships -->
            <tr>
                <td>
                    <bean:message key="web.management.prodRels.finishedFlagText"/>
                </td>
                <td>
                    <html:checkbox property="finishedFlag"/>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <html:submit value="submit"/>
                </td>
            </tr>
            </html:form>
        </table>
    </block>

</doc>
