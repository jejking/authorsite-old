/*
 * AbstractDTO.java
 *
 * Created on 20 November 2002, 09:48
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/AbstractDTO.java,v 1.3 2003/03/01 13:37:36 jejking Exp $
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
import java.io.*;
/**
 * <code>AbstractDTO</code> is the base class for all the core DTO types. The use
 * of an abstract super class was motivated by the possibility of using the template
 * method pattern (GOF) and the possible need to treat different DTOs as being of
 * essentially the same type.
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public abstract class AbstractDTO implements Serializable {
    
    /** Creates a new instance of AbstractDTO */
    public AbstractDTO() {
    }
    
    /** Accessor method which returns the ID of the DTO.
     * @return An Integer object representing the unique of the object represented by the DTO.
     */
    public abstract Integer getID();
    
}
