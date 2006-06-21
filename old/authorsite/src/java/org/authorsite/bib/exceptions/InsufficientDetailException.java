/*
 * InsufficientDetailException.java
 *
 * Created on 04 December 2002, 11:16
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/exceptions/InsufficientDetailException.java,v 1.3 2003/03/01 13:35:19 jejking Exp $
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
 * Used by the application to report errors produced by the business rules components in which
 * logical integrity of published mediaItems would be compromised by insufficient detail being
 * provided.
 * 
 * @author  jejking 
 * @version $Revision: 1.3 $
 */
public class InsufficientDetailException extends java.lang.Exception {
    
    private String erroneousPart;
    
    /**
     * Creates a new instance of <code>InsufficientDetailException</code> without detail message.
     */
    public InsufficientDetailException() {
    }
    
    
    /**
     * Constructs an instance of <code>InsufficientDetailException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InsufficientDetailException(String msg) {
        super(msg);
    }
    
    /**
     * Constructs an instance of <code>InsufficientDetailExcepton</code> with the detail message and a 
     * further string that describes what exactly is missing.
     * @param msg the detail message
     * @param newErroneousPart the missing detail
     */
    public InsufficientDetailException(String msg, String newErroneousPart) {
        super(msg);
        erroneousPart = newErroneousPart;
    }
    
}
