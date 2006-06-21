/*
 * MediaItemDetailsDTO.java
 *
 * Created on 23 November 2002, 13:03
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/MediaItemDetailsDTO.java,v 1.3 2003/03/01 13:37:36 jejking Exp $
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

package org.authorsite.bib.dto;
import java.util.*;
import java.lang.reflect.*;
/**<p>
 * <code>MediaItemDetailsDTO</code> is an abstract class that will be extended by the DetailsDTO classes generated
 * by the build process. It provides generic functionality based on the Reflection API allowing the concrete DTO objects
 * to identify themselves correctly should this be necessary without the actual implementation class being known.
 * This common functionality is a simple implementation of the <code>TemplateMethod</code> pattern (GOF).
 * </p>
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public abstract class MediaItemDetailsDTO  extends AbstractDTO {
    
    /**
     * <p>
     * Uses reflection to build the <code>mediaType</code> of the DTO class.
     * </p>
     *
     * <p>
     * It obtains the class name, removes the last three characters (assumed to be 'DTO') and, because our naming
     * convention is for <code>mediaType</code> name to start with a lowercase letter and then to use camelCase, substitutes
     * the first character for its lowercase equivalent.
     * </p>
     *
     * <p>
     * A client could use this information to look up its cached list of field descriptions corresponding to this
     * mediaType to construct whatever forms are necessary.
     * </p>
     *
     * @returns the <code>mediaType</code> represented by the DTO.
     * @see org.authorsite.bib.dto.FieldDescriptionDTO
     */
    public String getMediaType() {
        String className = this.getClass().getName();
        // a details DTO class will be of the sort org.authorsite.bib.dto.BookDetailsDTO
        // so we can do a substring, knocking off the first 23 chars (package path) and the last 10 characters (DetailsDTO)
        char[] shortenedClassNameArray =  className.substring(23, className.length() - 10).toCharArray();
        // our naming convention is that
        shortenedClassNameArray[0] = Character.toLowerCase(shortenedClassNameArray[0]);
        return new String(shortenedClassNameArray);
    }
    
    public abstract HashMap getFieldsMap();
    
}
