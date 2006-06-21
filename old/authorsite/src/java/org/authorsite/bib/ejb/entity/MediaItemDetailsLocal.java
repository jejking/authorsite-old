/*
 * MediaItemDetailsLocal.java
 *
 * Created on 26 November 2002, 18:30
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/MediaItemDetailsLocal.java,v 1.2 2003/03/01 13:37:13 jejking Exp $
 *
 * Copyright (C) 2002  John King
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
 */

package org.authorsite.bib.ejb.entity;
import javax.ejb.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public interface MediaItemDetailsLocal extends EJBLocalObject {
    
    public String getMediaType();
    
}
