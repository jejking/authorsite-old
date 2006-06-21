/*
 * MediaItemManagementFacade.java
 *
 * Created on 04 December 2002, 10:15
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/MediaItemManagementFacadeBean.java,v 1.7 2003/03/01 13:36:45 jejking Exp $
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

package org.authorsite.bib.ejb.facade;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.ejb.services.database.*;
import org.authorsite.bib.ejb.services.dto.*;
import org.authorsite.bib.ejb.services.rules.*;
import org.authorsite.bib.exceptions.*;
import org.authorsite.bib.ejb.facade.details.*;
/**
 * @ejb.bean    name="MediaItemManagementFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/MediaItemManagementFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.facade.MediaItemManagementFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.facade.MediaItemManagementFacadeHome"
 *                  generate="remote"
 *
 * * @ejb:ejb-ref ejb-name="MediaItemEJB"
 *              ref-name="ejb/MyMediaItemEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="LanguageEJB"
 *              ref-name="ejb/MyLanguageEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="MediaProductionRelationshipEJB"
 *              ref-name="ejb/MyMediaProductionRelationshipEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="IntraMediaRelationshipEJB"
 *              ref-name="ejb/MyIntraMediaRelationshipEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="RulesManagerEJB"
 *              ref-name="ejb/MyRulesManagerEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="SequenceBlockPrimaryKeyGeneratorEJB"
 *              ref-name="ejb/MySequenceBlockPrimaryKeyGeneratorEJB"
 *              view-type="local"
 *
 * @ejb.transaction type="RequiresNew"
 *
 * @author  jejking
 * @version $Revision: 1.7 $
 */
public class MediaItemManagementFacadeBean implements SessionBean {
    
    private SessionContext sessionCtx;
    private InitialContext ctx;
    private MediaItemLocalHome mediaItemLocalHome;
    private LanguageLocalHome languageLocalHome;
    private PersonLocalHome personLocalHome;
    private OrganisationLocalHome organisationLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    private IntraMediaRelationshipLocalHome intraMediaRelationshipLocalHome;
    private RulesManagerLocalHome rulesManagerLocalHome;
    private SequenceBlockPrimaryKeyGeneratorLocalHome sequenceBlockPrimaryKeyGeneratorLocalHome;
    
    private void getLocalHomes() {
        try {
            mediaItemLocalHome = (MediaItemLocalHome) ctx.lookup("java:comp/env/ejb/MyMediaItemEJB");
            languageLocalHome = (LanguageLocalHome) ctx.lookup("java:comp/env/ejb/MyLanguageEJB");
            personLocalHome = (PersonLocalHome) ctx.lookup("java:comp/env/ejb/MyPersonEJB");
            organisationLocalHome = (OrganisationLocalHome) ctx.lookup("java:comp/env/ejb/MyOrganisationEJB");
            mediaProductionRelationshipLocalHome = (MediaProductionRelationshipLocalHome) ctx.lookup("java:comp/env/ejb/MyMediaProductionRelationshipEJB");
            intraMediaRelationshipLocalHome = (IntraMediaRelationshipLocalHome) ctx.lookup("java:comp/env/ejb/MyIntraMediaRelationshipEJB");;
            rulesManagerLocalHome = (RulesManagerLocalHome) ctx.lookup("java:comp/env/ejb/MyRulesManagerEJB");;
            sequenceBlockPrimaryKeyGeneratorLocalHome = (SequenceBlockPrimaryKeyGeneratorLocalHome) ctx.lookup("java:comp/env/ejb/MySequenceBlockPrimaryKeyGeneratorEJB");
        }
        catch (NamingException ne) {
            // arrghhh! If this happens, we're a tad stitched ;-)
            ne.printStackTrace();
        }
    }
    
    private String capitaliseFirstLetter(String capitaliseThisString) {
        char[] chars = capitaliseThisString.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }
    
    /** Creates a new instance of MediaItemManagementFacade */
    public MediaItemManagementFacadeBean() {
        try {
            ctx = new InitialContext();
            getLocalHomes();
        }
        catch (NamingException ne) {
            ne.printStackTrace();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public MediaItemDTO findByPrimaryKey(int primaryKey) throws FinderException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(primaryKey));
        MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(item);
        return assembler.assembleDTO();
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createMediaItem(String title, String mediaType, int yearOfCreation) throws CreateException {
        // get PK
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("MediaItem");
        MediaItemLocal item = mediaItemLocalHome.create(pk, title, mediaType, yearOfCreation);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createMediaItem(String title, String mediaType, int yearOfCreation, String additionalInfo, String comment) throws CreateException {
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("MediaItem");
        MediaItemLocal item = mediaItemLocalHome.create(pk, title, mediaType, yearOfCreation);
        if (additionalInfo.length() > 0) {
            item.setAdditionalInfo(additionalInfo);
        }
        if (comment.length() > 0) {
            item.setComment(comment);
        }
        return pk;
    }
    
    /**
     * This only provides functionality to alter the core attributes of a mediaItem. Use the relationship and
     * detail manipulation methods to alter other aspects of a mediaItem.
     * @ejb.interface-method view-type="remote"
     */
    public void editMediaItemCore(MediaItemDTO dto) throws FinderException, ChangeNotPermittedException {
        // locate the mediaItem we want to change
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(dto.getMediaItemID());
        // ok, we've found it. now lets see if it does bad things. It can't change mediaType or publishedFlag. Throw exception
        // if this is attempted. We'll ignore it, anyway, but better to let people know...
        if (item.getPublishedFlag() != dto.getPublishedFlag()) {
            throw new ChangeNotPermittedException("bib.error.changeNotPermitted.publishedFlag");
        }
        if (!item.getMediaType().equals(dto.getMediaType())) {
            throw new ChangeNotPermittedException("bib.error.changeNotPermitted.mediaType");
        }
        if (dto.getTitle() == null || dto.getTitle().equals("")) {
            throw new ChangeNotPermittedException("bib.error.changeNotPermitted.titleToNull");
        }
        // set the new title
        item.setTitle(dto.getTitle());
        // now, lets see if yearOfCreation, additionalInfo or comment have changed. If so, update them
        if (item.getYearOfCreation() != dto.getYearOfCreation()) {
            item.setYearOfCreation(dto.getYearOfCreation());
        }
        // note the cunning way I put the == null on the left so it is evaluated first and we don't get
        // a null pointer exception on the right hand side of the || operator. JCP here I come...
        if (item.getAdditionalInfo() == null || !item.getAdditionalInfo().equals(dto.getAdditionalInfo())) {
            item.setAdditionalInfo(dto.getAdditionalInfo());
        }
        if (item.getComment() == null || !item.getComment().equals(dto.getComment())) {
            item.setComment(dto.getComment());
        }
    }
    
    
    /**
     * @ejb.interface-method view-type="remote"
     * @todo ensure we don't leave any related mediaItems in an illegal state
     */
    public void removeMediaItem(int mediaItemID) throws RemoveException, FinderException {
        /*
         * - delete the media item's intramediarelationships (parents and children)
         * - delete any media item which is contained in this media item
         * - delete the relevant details ejb
         * - finally, delete the media item. The mediaProductionRelationships should go automatically
         * using cascade-delete.
         */
        // if we can't find this, it doesn't exist, so it'll throw a FinderException straight back. No point catching it.
        ArrayList weContainList = new ArrayList();
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        
        // get hold of its child relationships
        try {
            Collection childRels = intraMediaRelationshipLocalHome.findByMediaFromID(mediaItemID);
            // delete them ruthlessly
            Iterator childRelsIt = childRels.iterator();
            while (childRelsIt.hasNext()) {
                IntraMediaRelationshipLocal currentRel = (IntraMediaRelationshipLocal) childRelsIt.next();
                intraMediaRelationshipLocalHome.remove(currentRel.getIntraMediaRelationshipID());
            }
        }
        catch (FinderException fe) {
        }
        
        // get hold of its parent relationships
        try {
            // we get the IMRs which list this mediaItem as a target, i.e. we get hold of those IMRs which
            // have this mediaItem as a "child"
            Collection parentRels = intraMediaRelationshipLocalHome.findByMediaToID(mediaItemID);
            
            // we want to remove these links as they'll be pointing at nothing once this mediaItem goes
            Iterator parentRelsIt = parentRels.iterator();
            while (parentRelsIt.hasNext()) {
                IntraMediaRelationshipLocal currentRel = (IntraMediaRelationshipLocal) parentRelsIt.next();
                // if the parent relationship is a containment relationship, then that parent must logically be deleted as well
                if (currentRel.getRelationshipType().equals("containment")) {
                    weContainList.add(new Integer(currentRel.getMediaFromID()));
                }
                intraMediaRelationshipLocalHome.remove(currentRel.getIntraMediaRelationshipID());
            }
        }
        catch (FinderException fe) {
        }
        
        
        
        // load up the relevant MediaItemDetailsHandler and call its remove() method in order to delete
        // the attached MediaItemDetails object
        try {
            String DetailsHandlerName = "org.authorsite.bib.ejb.facade.details." + capitaliseFirstLetter(item.getMediaType()) + "DetailsHandler";
            // get the class
            Class MediaItemHandlerClass = Class.forName(DetailsHandlerName);
            MediaItemDetailsHandler handler = (MediaItemDetailsHandler) MediaItemHandlerClass.newInstance();
            handler.removeMediaItemDetails(item.getMediaItemID().intValue());
        }
        catch (FinderException fe) { // if the thing doesn't exist
            // do nothing. this is fine. we could be dealing with an item for which no details object was ever made
        }
        catch (RemoveException re) {
            // bad. shouldn't happen.
            re.printStackTrace();
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        catch (InstantiationException ie) {
            ie.printStackTrace();
        }
        catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        System.out.println("about to finally remove " + item.getMediaItemID());
        mediaItemLocalHome.remove(item.getMediaItemID()); // it's finally been zapped
        // now, zap its children
        if (weContainList.size() >0) {
            System.out.println("we have contained items. we shall now zap them");
            Iterator weContainListIt = weContainList.iterator();
            while (weContainListIt.hasNext()) {
                Integer nextID = (Integer) weContainListIt.next();
                removeMediaItem(nextID.intValue());
            }
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void createMediaItemDetails(MediaItemDetailsDTO mediaItemDetailsDTO) throws FinderException, CreateException, InsufficientDetailException {
        // some initial sanity checks
        // - does the mediaItem already exist and do its mediaType and the DTOs mediaType match
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(mediaItemDetailsDTO.getID()); // if we can't find it, FinderException
        if (item.getMediaType().equals(mediaItemDetailsDTO.getMediaType())) {
            // all ok, go and delegate to the handler to create the details object
            MediaItemDetailsHandler handler = getMediaItemDetailsHandler(item);
            handler.createMediaItemDetails(mediaItemDetailsDTO);
        }
        else {
            throw new CreateException("bib.errors.ItemAndDetailsSameType");
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void editMediaItemDetails(MediaItemDetailsDTO mediaItemDetailsDTO) throws FinderException, InsufficientDetailException {
        // do the same sanity  checks as above before we delegate to the handler
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(mediaItemDetailsDTO.getID());
        if (item.getMediaType().equals(mediaItemDetailsDTO.getMediaType())) {
            // all ok, go and delegate to the handler to edit the details object
            MediaItemDetailsHandler handler = getMediaItemDetailsHandler(item);
            handler.editMediaItemDetails(mediaItemDetailsDTO);
        }
        else {
            throw new InsufficientDetailException("bib.errors.ItemAndDetailsSameType");
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void addLanguageToMediaItem(int mediaItemID, String iso639) throws FinderException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Set languages = item.getLanguages();
        languages.add(language);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void addMultipleLanguagesToMediaItem(int mediaItemID, Set newLanguagesSet) throws FinderException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        Set itemLanguages = item.getLanguages();
                
        Iterator newLanguagesSetIt = newLanguagesSet.iterator();
        while (newLanguagesSetIt.hasNext()) {
            LanguageLocal currentLanguage = languageLocalHome.findByPrimaryKey((String) newLanguagesSetIt.next());
            itemLanguages.add(currentLanguage);
        }
        item.setLanguages(itemLanguages); // as the stupid thing doesn't do this for us if we bulk update the set
    }
        
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removeLanguageFromMediaItem(int mediaItemID, String iso639) throws FinderException, InsufficientDetailException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        LanguageLocal language = languageLocalHome.findByPrimaryKey(iso639);
        Set languages = item.getLanguages();
        
        // if the mediaItem is published, check that it will still have at least one language. Otherwise throw exception
        if (item.getPublishedFlag() && languages.size() <= 1) {
            throw new InsufficientDetailException("bib.error.insufficientdetail.language.ReductionToZero");
        }
        languages.remove(language);
    }
    
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int addMediaProductionRelationship(int mediaItemID, String relationshipType) throws FinderException, CreateException, RelationshipNotPermittedException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        // check if the relationshipType is permitted for the item's mediaType
        RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
        if (rulesManager.validateProductionRelationshipAddition(item, relationshipType)) {
            // it's ok, so create the relationship and add it to the item
            SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
            int pk = pkGen.getNextSequenceNumber("MediaProductionRelationship");
            MediaProductionRelationshipLocal newProdRel = mediaProductionRelationshipLocalHome.create(pk, relationshipType);
            Set itemProdRels = item.getMediaProductionRelationships();
            itemProdRels.add(newProdRel);
            return pk;
        }
        else {
            throw new RelationshipNotPermittedException();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void addPersonToProductionRelationship(int mediaProductionRelationshipID, int personID) throws FinderException {
        MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(mediaProductionRelationshipID));
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        Set prodRelPeople = prodRel.getPeople();
        prodRelPeople.add(person);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void addOrganisationToProductionRelationship(int mediaProductionRelationshipID, int organisationID) throws FinderException {
        MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(mediaProductionRelationshipID));
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(organisationID));
        Set prodRelOrgs = prodRel.getOrganisations();
        prodRelOrgs.add(org);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removeMediaProductionRelationship(int mediaItemID, int mediaProductionRelationshipID) throws FinderException, RelationshipRemovalNotPermittedException {
        
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(mediaProductionRelationshipID));
        // right. If the item is published, check to see if we are allowed to remove the relationship
        if (item.getPublishedFlag()) {
            try {
                RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
                if (rulesManager.validateProductionRelationshipRemoval(item, prodRel.getRelationshipType())) { // it's' not ok, bail out
                    throw new RelationshipRemovalNotPermittedException();
                }
            }
            catch (CreateException ce) {
                // wrap the exception. If we can't get a ref to the RulesManager, we're pretty stuffed anyway
                throw new RelationshipRemovalNotPermittedException(ce.getMessage());
            }
        }
        Set itemProdRels = item.getMediaProductionRelationships();
        itemProdRels.remove(prodRel);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removePersonFromProductionRelationship(int mediaProductionRelationshipID, int personID) throws FinderException, EmptyingObligatoryRelationshipNotPermittedException {
        MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(mediaProductionRelationshipID));
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personID));
        MediaItemLocal item = prodRel.getMediaItem();
        
        // if the item is published, and the production relationship is an obligatory one, then the relationship must not be emptied
        // it must have at least one person or one organisation in it
        if (item.getPublishedFlag()) {
            // get list of obligatory production relationships
            try {
                RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
                HashSet obligProdRels = rulesManager.getProductionRelationshipsOfPriority(item.getMediaType(), "obligatory");
                if (obligProdRels.contains(prodRel.getRelationshipType())) {
                    // ok, we are published and the relationship is obligatory, so lets see if removing the person will leave it empty
                    if (prodRel.getPeople().size() + prodRel.getOrganisations().size() <= 1) {
                        throw new EmptyingObligatoryRelationshipNotPermittedException();
                    }
                }
            }
            catch (CreateException ce) {
                throw new EmptyingObligatoryRelationshipNotPermittedException(ce.getMessage());
            }
        }
        
        // otherwise, just remove the person from the production relationship
        Set prodRelPeople = prodRel.getPeople();
        prodRelPeople.remove(person);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removeOrganisationFromProductionRelationship(int mediaProductionRelationshipID, int organisationID) throws FinderException, EmptyingObligatoryRelationshipNotPermittedException {
        MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(mediaProductionRelationshipID));
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(organisationID));
        MediaItemLocal item = prodRel.getMediaItem();
        
        // if the item is published, and the production relationship is an obligatory one, then the relationship must not be emptied
        // it must have at least one person or one organisation in it
        if (item.getPublishedFlag()) {
            // get list of obligatory production relationships
            try {
                RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
                HashSet obligProdRels = rulesManager.getProductionRelationshipsOfPriority(item.getMediaType(), "obligatory");
                if (obligProdRels.contains(prodRel.getRelationshipType())) {
                    // ok, we are published and the relationship is obligatory, so lets see if removing the person will leave it empty
                    if (prodRel.getPeople().size() + prodRel.getOrganisations().size() <= 1) {
                        throw new EmptyingObligatoryRelationshipNotPermittedException();
                    }
                }
            }
            catch (CreateException ce) {
                throw new EmptyingObligatoryRelationshipNotPermittedException(ce.getMessage());
            }
        }
        
        // otherwise, just remove the person from the production relationship
        Set prodRelOrgs = prodRel.getOrganisations();
        prodRelOrgs.remove(org);
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createIntraMediaRelationship(int mediaFromID, String relationshipType, int mediaToID) throws FinderException, CreateException, RelationshipNotPermittedException {
        // an intra-media relationship must point from one item to another. They cannot be self-referential
        if (mediaFromID == mediaToID) {
            throw new RelationshipNotPermittedException("bib.errors.relationshipNotPermitted.selfReferentialIntraMediaRelationship");
        }
        
        // check that the media items actually exist
        MediaItemLocal mediaFrom = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaFromID));
        MediaItemLocal mediaTo = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaToID));
        
        // temporary hack until I refactor the way in which containment relationships are managed...
        // - we can only be contained in one other mediaItem. Anything else doesn't make sense.
        // - so, if the relationship we want to create is containment,
        // we'll see if the mediaFrom already has a containment child and if it does,
        // throw an exception
        if (relationshipType.equals("containment")) {
            Set iAmContainedIn = mediaFrom.getContainer();
            if (iAmContainedIn.size() >= 1) {
                throw new RelationshipNotPermittedException("bib.errors.RelationshipNotPermitted.duplicateContainment");
            }
        }
        
        // check that the relationship is ok by consulting the rules manager
        RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
        if (rulesManager.validateIntraMediaRelationshipAddition(mediaFrom, relationshipType, mediaTo)) {
            // we're ok, create the IMR
            SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
            int pk = pkGen.getNextSequenceNumber("IntraMediaRelationship");
            IntraMediaRelationshipLocal imr = intraMediaRelationshipLocalHome.create(pk, mediaFromID, relationshipType, mediaToID);
            return pk;
        }
        else {
            throw new RelationshipNotPermittedException();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removeIntraMediaRelationship(int relationshipID) throws FinderException, RemoveException, RelationshipRemovalNotPermittedException {
        // find the IMR
        IntraMediaRelationshipLocal imr = intraMediaRelationshipLocalHome.findByPrimaryKey(new Integer(relationshipID));
        // now, if this IMR is the last one of its relationshipType for the mediaFrom, we need to check if it can be removed
        MediaItemLocal fromItem = mediaItemLocalHome.findByPrimaryKey(new Integer(imr.getMediaFromID()));
        Collection fromItemIMRsOfType = fromItem.getAllChildrenOfType(imr.getRelationshipType());
        if (fromItemIMRsOfType.size() <= 1) {
            // must make sure we can don't remove an obligatory relationsihp before we zap the last one
            try {
                RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
                if (!rulesManager.validateIntraMediaRelationshipRemoval(fromItem, imr.getRelationshipType())) {
                    // it's not ok, throw exception
                    throw new RelationshipRemovalNotPermittedException();
                }
            }
            catch (CreateException ce) {
                throw new RelationshipRemovalNotPermittedException();
            }
        }
        else {
            imr.remove();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void publishMediaItem(int mediaItemID) throws FinderException, InsufficientDetailException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        // if it's already published, just return
        if (item.getPublishedFlag()) {
            return;
        }
        else {
            try {
                RulesManagerLocal rulesManager = rulesManagerLocalHome.create();
                rulesManager.validatePublication(item);
                // this one's ok, now we need to go and check its container
                // there's no point publishing something contained in an unpublished container.
                Set itemContainer = item.getContainer();
                if (itemContainer.size() > 0) { // we have a container...
                    Iterator itemContainerIt = itemContainer.iterator();
                    while (itemContainerIt.hasNext()) {
                        MediaItemLocal container = (MediaItemLocal) itemContainerIt.next();
                        if (container.getPublishedFlag()) {
                            break;
                        }
                        else {
                            try {
                                publishMediaItem(container.getMediaItemID().intValue());
                            }
                            catch (FinderException finderEx) {
                                throw new EJBException(finderEx.getMessage());
                            }
                            catch (InsufficientDetailException ide) {
                                throw new InsufficientDetailException("bib.errors.insufficientDetail.container.insufficientDetail");
                            }
                        }
                    }
                }
                item.setPublishedFlag(true);
            }
            catch (CreateException ce) {
                throw new EJBException("could not create instance of RulesManager");
            }
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void unpublishMediaItem(int mediaItemID) throws FinderException {
        MediaItemLocal item = mediaItemLocalHome.findByPrimaryKey(new Integer(mediaItemID));
        // we must make sure that not only this item, but all items it contains are unpublished
        // there is no sense in having published items contained in an unpublished item.
        
        // get hold of those published parents which are 'containment'. i.e. get everything we contain
        Collection contained = item.getPublishedParentsOfType("containment");
        Iterator containedIterator = contained.iterator();
        while (containedIterator.hasNext()) {
            MediaItemLocal containedPublishedItem = (MediaItemLocal) containedIterator.next();
            unpublishMediaItem(containedPublishedItem.getMediaItemID().intValue());
        }
        item.setPublishedFlag(false);
    }
    
    /*
     * util method to load up the correct mediaItemDetailsHandler class to do various things with the details objects
     */
    private MediaItemDetailsHandler getMediaItemDetailsHandler(MediaItemLocal item) {
        MediaItemDetailsHandler handler = null;
        try {
            String DetailsHandlerName = "org.authorsite.bib.ejb.facade.details." + capitaliseFirstLetter(item.getMediaType()) + "DetailsHandler";
            // get the class
            Class MediaItemHandlerClass = Class.forName(DetailsHandlerName);
            handler = (MediaItemDetailsHandler) MediaItemHandlerClass.newInstance();
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        catch (InstantiationException ie) {
            ie.printStackTrace();
        }
        catch (IllegalAccessException iae) {
            iae.printStackTrace();
        }
        return handler;
    }
    
    public void ejbCreate() throws CreateException {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        sessionCtx = sessionContext;
    }
    
}
