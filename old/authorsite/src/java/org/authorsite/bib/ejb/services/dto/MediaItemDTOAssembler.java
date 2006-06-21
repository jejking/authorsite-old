/*
 * DTOAssembler.java
 *
 * Created on 20 November 2002, 11:56
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/services/dto/MediaItemDTOAssembler.java,v 1.1 2003/03/29 12:49:01 jejking Exp $
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

package org.authorsite.bib.ejb.services.dto;
import java.lang.reflect.*;
import java.util.*;
import javax.naming.*;
import javax.ejb.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.dto.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class MediaItemDTOAssembler {
    
    private MediaItemLocal myMediaItemLocal;
    private MediaItemDTO myMediaItemDTO;
    
    public MediaItemDTOAssembler(MediaItemLocal newMediaItemLocal) {
        myMediaItemLocal = newMediaItemLocal;
        myMediaItemDTO = new MediaItemDTO(myMediaItemLocal.getMediaItemID());
    }
    
    public MediaItemDTO assembleDTO() {
        // set up the basic data in the DTO
        myMediaItemDTO.setTitle(myMediaItemLocal.getTitle());
        myMediaItemDTO.setMediaType(myMediaItemLocal.getMediaType());
        myMediaItemDTO.setYearOfCreation(myMediaItemLocal.getYearOfCreation());
        myMediaItemDTO.setPublishedFlag(myMediaItemLocal.getPublishedFlag());
        myMediaItemDTO.setAdditionalInfo(myMediaItemLocal.getAdditionalInfo());
        myMediaItemDTO.setComment(myMediaItemLocal.getComment());
        
        // set up the languages
        Set languages = myMediaItemLocal.getLanguages();
        Set myLanguageDTOs = new HashSet();
        Iterator languagesIt = languages.iterator();
        while (languagesIt.hasNext()) {
            LanguageLocal currentLang = (LanguageLocal) languagesIt.next();
            LanguageDTOAssembler languageDTOAssembler = new LanguageDTOAssembler(currentLang);
            myLanguageDTOs.add(languageDTOAssembler.assembleDTO());
        }
        myMediaItemDTO.setLanguages(myLanguageDTOs);
        
        // set up the mediaProductionRelationships
        Set mediaProductionRelationships = myMediaItemLocal.getMediaProductionRelationships();
        Set myMediaProductionRelationshipDTOs = new HashSet();
        Iterator prodRelsIt = mediaProductionRelationships.iterator();
        while (prodRelsIt.hasNext()) {
            MediaProductionRelationshipLocal currentProdRel = (MediaProductionRelationshipLocal) prodRelsIt.next();
            myMediaProductionRelationshipDTOs.add(new MediaProductionRelationshipDTOAssembler(currentProdRel).assembleDTO());
        }
        myMediaItemDTO.setMediaProductionRelationships(myMediaProductionRelationshipDTOs);
        
        // do we have details to append??
        handleDetailsObject();
        
        // now, are we contained in something??
        Set mediaItemContainerSet = myMediaItemLocal.getContainer();
        if (!mediaItemContainerSet.isEmpty()) {
            // extract the parent
            Iterator containerIt = mediaItemContainerSet.iterator();
            MediaItemLocal container = (MediaItemLocal) containerIt.next(); // assume rules have been enforced and we can only be contained
            // in a single other mediaItem. @todo - check this is enforced!!
            // now do some recursive stuff, and build the parent in
            
            MediaItemDTOAssembler containerAssembler = new MediaItemDTOAssembler(container);
            myMediaItemDTO.setContainedIn(containerAssembler.assembleDTO());
        }
        
        // children. THIS CODE SUCKS!!! It works, but it sucks.
        Collection childrenIMRs = myMediaItemLocal.getChildIntraMediaRelationships();
        if (childrenIMRs.size() > 0) {
            HashMap childrenMap = new HashMap();
            Iterator childrenTypesIt = childrenIMRs.iterator();
            while (childrenTypesIt.hasNext()) {
                String typeKey = childrenTypesIt.next().toString();
                Collection childrenOfType = myMediaItemLocal.getAllChildrenOfType(typeKey);
                ArrayList childrenDTOsOfTypeList = new ArrayList();
                Iterator childrenOfTypeIt = childrenOfType.iterator();
                while (childrenOfTypeIt.hasNext()) {
                    MediaItemLocal childItem = (MediaItemLocal) childrenOfTypeIt.next();
                    MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(childItem);
                    childrenDTOsOfTypeList.add(assembler.assembleLightweightDTO());
                }
                childrenMap.put(typeKey, childrenDTOsOfTypeList);
            }
            myMediaItemDTO.setChildren(childrenMap);
        }
                
        // parents. Hm. This needs an urgent update too. We should be able to specify some more characteristics
        // of what we want returning. Whether we want with published parents/children or not....
        // i.e. for management or public screens ! Grrr! Should have thought of that back in September...
        Collection parentsIMRs = myMediaItemLocal.getParentIntraMediaRelationships();
        if (parentsIMRs.size() > 0) {
            HashMap parentsMap = new HashMap();
            Iterator parentsTypesIt = parentsIMRs.iterator();
            while (parentsTypesIt.hasNext()) {
                String typeKey = parentsTypesIt.next().toString();
                Collection parentsOfType = myMediaItemLocal.getPublishedParentsOfType(typeKey);
                ArrayList parentsDTOsOfTypeList = new ArrayList();
                Iterator parentsOfTypeIt = parentsOfType.iterator();
                while (parentsOfTypeIt.hasNext()) {
                    MediaItemLocal parentItem = (MediaItemLocal) parentsOfTypeIt.next();
                    MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(parentItem);
                    parentsDTOsOfTypeList.add(assembler.assembleLightweightDTO());
                }
                parentsMap.put(typeKey, parentsDTOsOfTypeList);
            }
            myMediaItemDTO.setParents(parentsMap);
        }
        return myMediaItemDTO;
    }
    
    public MediaItemDTO assembleLightweightDTO() {
        myMediaItemDTO.setTitle(myMediaItemLocal.getTitle());
        myMediaItemDTO.setMediaType(myMediaItemLocal.getMediaType());
        myMediaItemDTO.setYearOfCreation(myMediaItemLocal.getYearOfCreation());
        myMediaItemDTO.setPublishedFlag(myMediaItemLocal.getPublishedFlag());
        return myMediaItemDTO;
    }
    
    private void handleDetailsObject() {
        if (myMediaItemDTO.getMediaType() == null) { // it hasn't been set yet
            return;
        }
        try {
            // work out which DTOFactory to use
            String DTOFactoryName = "org.authorsite.bib.ejb.services.dto." + capitaliseFirstLetter(myMediaItemDTO.getMediaType()) + "DetailsDTOFactory";
            // get the class
            Class dtoFactoryClass = Class.forName(DTOFactoryName);
         
            // to get the constructor, we need to identify it using an array of Class objects.
            // we want the constructor that takes a reference to a MediaItemDetailsLocal object
            Class constructorParams[] = new Class[1];
            constructorParams[0] = Integer.class;
         
            // get the constructor
            Constructor dtoFactoryClassConstructor = dtoFactoryClass.getConstructor(constructorParams);
         
            // to initialise the object using the constructor we obtained, we need to pass it an array of objects
            // in this case, the reference to the MediaItemDetailsLocal object whose DTO we are building
            Object args[] = new Object[1];
            args[0] = myMediaItemDTO.getID();
         
            // we call the constructor with the args we assembled and cast the result to the generic MediaItemDetailsAssembler type
            MediaItemDetailsDTOFactory factory = (MediaItemDetailsDTOFactory) dtoFactoryClassConstructor.newInstance(args);
         
            // now, with all that out of the way we can call the assembleDTO() method
            MediaItemDetailsDTO detailsDTO = factory.getDTO();
            if (detailsDTO == null) {
                return;
            }
            else {
                myMediaItemDTO.setDetailsDTO(detailsDTO);
            }
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
            return;
        }
        catch (NoSuchMethodException nsme) {
            nsme.printStackTrace();
            return;
        }
        catch (InstantiationException ie) {
            ie.printStackTrace();
            return;
        }
        catch (IllegalAccessException iae) {
            iae.printStackTrace();
            return;
        }
        catch (InvocationTargetException ite) {
            ite.printStackTrace();
            return;
        }
    }
    
    private String constructJNDILookUpString(String baseMediaType) {
        String jndiLookUp = "ejb/" + capitaliseFirstLetter(baseMediaType) + "DetailsLocalEJB";
        return jndiLookUp;
    }
    
    private String capitaliseFirstLetter(String capitaliseThisString) {
        char[] chars = capitaliseThisString.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
}
