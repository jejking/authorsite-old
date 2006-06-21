<?xml version="1.0" encoding="UTF-8" ?>
<!--
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
        <title><bean:message key="web.management.createMediaItem.itemPublished.pageTitle"/></title>
    </ia>    
    <block id="main">
        <h1><bean:message key="web.management.createMediaItem.itemPublished.pageHeader"/></h1>

        <p><bean:message key="web.management.createMediaItem.itemPublished.blurb"/></p>
        
        <table>
            <bibWebApp:mediaItem detail="full" checkBoxFlag="false" publicFlag="false" beanName="ActiveMediaItem"/>
        </table>

    </block>
</doc>