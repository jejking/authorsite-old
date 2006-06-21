/*
 * MediaItemDTO.java
 *
 * Created on 20 November 2002, 09:27
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/dto/MediaItemDTO.java,v 1.4 2003/03/29 11:56:37 jejking Exp $
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
import java.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class MediaItemDTO extends AbstractDTO {
    
    private Integer mediaItemID;
    private String title;
    private String mediaType;
    private int yearOfCreation;
    private boolean publishedFlag;
    private String additionalInfo;
    private String comment;
    private Set languages = new HashSet();
    private Set mediaProductionRelationships = new HashSet();
    private Map children = new HashMap();
    private Map parents = new HashMap();
    private MediaItemDTO containedIn;
    private MediaItemDetailsDTO detailsDTO;
    
    /** Creates a new instance of MediaItemDTO */
    public MediaItemDTO() {
    }
    
    public MediaItemDTO(Integer newID) {
        setMediaItemID(newID);
    }
    
    public Integer getID() {
        return mediaItemID;
    }
    
    public Integer getMediaItemID() {
        return mediaItemID;
    }
    
    public void setMediaItemID(Integer newID) {
        mediaItemID = newID;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String newTitle) {
        title = newTitle;
    }
    
    public String getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(String newMediaType) {
        mediaType = newMediaType;
    }
    
    public int getYearOfCreation() {
        return yearOfCreation;
    }
    
    public void setYearOfCreation(int newYearOfCreation) {
        yearOfCreation = newYearOfCreation;
    }
    
    public boolean getPublishedFlag() {
        return publishedFlag;
    }
    
    public void setPublishedFlag(boolean newPublishedFlag) {
        publishedFlag = newPublishedFlag;
    }
        
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    public void setAdditionalInfo(String newAdditionalInfo) {
        additionalInfo = newAdditionalInfo;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String newComment) {
        comment = newComment;
    }
    
    public Set getLanguages() {
        return languages;
    }
    
    public void setLanguages(Set newLanguages) {
        // check the set only contains LanguageDTO objects
        Iterator it = newLanguages.iterator();
        try {
            while (it.hasNext()) {
                LanguageDTO currentLang = (LanguageDTO) it.next();
            }
            languages = newLanguages;
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("Set newLanguages may only contain LanguageDTO objects");
        }
    }
    
    public Set getMediaProductionRelationships() {
        return mediaProductionRelationships;
    }
    
    public void setMediaProductionRelationships(Set newMediaProductionRelationships) {
        Iterator it = newMediaProductionRelationships.iterator();
        try {
            while (it.hasNext()) {
                MediaProductionRelationshipDTO currentProdRel = (MediaProductionRelationshipDTO) it.next();
            }
            mediaProductionRelationships = newMediaProductionRelationships;
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("Set newMediaProductionRelationships may only contain MediaProductionRelationshipDTO objects");
        }
    }
    
    public MediaItemDTO getContainedIn() {
        return containedIn;
    }
    
    public void setContainedIn(MediaItemDTO newContainedIn) {
        containedIn = newContainedIn;
    }
    
    public MediaItemDetailsDTO getDetailsDTO() {
        return detailsDTO;
    }
    
    public void setDetailsDTO(MediaItemDetailsDTO newDetailsDTO) {
        // must make sure it's the correct sort of dto
        if (this.getMediaType().equals(newDetailsDTO.getMediaType())) {
            detailsDTO = newDetailsDTO;
        }
        else {
            System.out.println("this.getMediaType() = " + this.getMediaType() + "dtoMediaType = " + newDetailsDTO.getMediaType());
            throw new IllegalArgumentException("newDetailsDTO must be of same type as mediaType");
        }
    }
    
    public Map getChildren() {
        return children;
    }
    
    public void setChildren(Map newChildren) {
        // map must contain MediaItemDTOs
        /*
        Iterator it = newChildren.values().iterator();
        try {
            while (it.hasNext()) {
                MediaItemDTO currentDTO = (MediaItemDTO) it.next();
            }
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("newChildren Map must contain values only of type MediaItemDTO");
        }
         **/
        children = newChildren;
    }
    
    public Map getParents() {
        return parents;
    }
    
    public void setParents(Map newParents) {
        // only MediaItemDTOs
        /*
        Iterator it = newParents.values().iterator();
        try {
            while (it.hasNext()) {
                MediaItemDTO currentDTO = (MediaItemDTO) it.next();
            }
        }
        catch (ClassCastException cce) {
            throw new IllegalArgumentException("newParents map must contain values only of type MediaItemDTO");
        }
         */
        parents = newParents;
    }
}
