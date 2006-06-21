/*
 * RelationshipRemovalNotPermittedException.java
 *
 * Created on 04 December 2002, 10:23
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/exceptions/RelationshipRemovalNotPermittedException.java,v 1.2 2003/03/01 13:35:19 jejking Exp $
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

package org.authorsite.bib.exceptions;

/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class RelationshipRemovalNotPermittedException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>RelationshipRemovalNotPermittedException</code> without detail message.
     */
    public RelationshipRemovalNotPermittedException() {
    }
    
    
    /**
     * Constructs an instance of <code>RelationshipRemovalNotPermittedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RelationshipRemovalNotPermittedException(String msg) {
        super(msg);
    }
}
