/*
 * MediaItemDetailsHandler.java
 *
 * Created on 06 December 2002, 11:04
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/details/MediaItemDetailsHandler.java,v 1.3 2003/03/01 13:36:57 jejking Exp $
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

package org.authorsite.bib.ejb.facade.details;
import javax.ejb.*;
import javax.naming.*;
import java.util.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.exceptions.*;
/**
 * <p>
 * The <code>MediaItemDetailsHandler</code> interface provides a generic access to the 
 * local and local home interfaces of the various different types of <code>xDetails</code
 * beans. The relevant implementation will be generated at build time from <code>bibTypesRelationships.xml</code>
 * using xslt and dynamically loaded at run time by the <code>MediaItemManagementFacadeBean</code> 
 * by inspecting the <code>mediaType</code> attribute of the central <code>mediaItem</code>. (One refactoring would
 * be to merge this functionality with the xDetails DTO Assembler classes). </p>
 *
 * <p>
 * The interface describes a number of methods for manipulating <code>xDetails</code> objects
 * </p>
 * <ul>
 * <li> - creation (with validation) </li>
 * <li> - removal </li>
 * <li> - edition (with validation) </li>
 * </ul>
 * 
 * @author jejking
 * @version $Revision: 1.3 $
 */
public interface MediaItemDetailsHandler {
    
    /** Obtains a reference to the relevant local home interface, calls the create method and setters for those fields present
     * in in the DTO but not required in the ejbCreate() method.
     * @param detailsDTO <code>MediaItemDetailsDTO</code> concrete subclass of the correct type for the Handler in question. This must
     * contain the mediaItemID so that the entity bean can be created with the correct ID.
     * @throws InsufficientDetailException The Handler will examine the DTO to ensure that it contains the minimum fields
     * specified in <code>bibTypesRelationships.xml</code> before further processing.
     * If fields are missing, it will throw this exception.
     * @throws CreateException Rethrows the CreateException from the local home interface.
     */
    public void createMediaItemDetails(MediaItemDetailsDTO detailsDTO) throws InsufficientDetailException, CreateException;
    
    /** Method looks up the localHome interface and calls its remove method for the
     * primary key in question.
     * @param detailsID An int representing the ID of the details object to be deleted. This will
     * converted internally into the relevant <code>Integer</code>.
     * @throws FinderException The method will ensure the details object actually exists before attempting to
     * delete it. If it cannot be found, it will rethrow the FinderException.
     */    
    public void removeMediaItemDetails(int detailsID) throws FinderException, RemoveException;
    
    /** Takes a <code>MediaItemDetailsDTO</code>, looks up the relevant local interface
     * and updates those fields which are different in the DTO. It also performs
     * validation to ensure that the local interface retains the minimum required data.
     * @param detailsDTO A concrete implementation of <code>MediaItemDetailsDTO</code> with updated
     * fields. This must contain a valid MediaItemID field.
     * @throws InsufficientDetailException If the validation routine establishes that the updated DTO does not contain the
     * minimum data required, it will throw this exception.
     * @throws FinderException If the local interface cannot be found by the local home interface, the method
     * will rethrow the FinderException
     */    
    public void editMediaItemDetails(MediaItemDetailsDTO detailsDTO) throws InsufficientDetailException, FinderException;
    
    /**
     * checks whether the MediaItemDetails object corresponding to the primary key actually exists
     */
    public boolean mediaItemDetailsExists(Integer pk);
    
}
