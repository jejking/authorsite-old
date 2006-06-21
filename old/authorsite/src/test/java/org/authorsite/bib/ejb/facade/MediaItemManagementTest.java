/*
 * MediaItemManagementTest.java
 * JUnit based test
 *
 * Created on 04 December 2002, 17:30
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/facade/MediaItemManagementTest.java,v 1.3 2002/12/16 19:58:23 jejking Exp $
 */

package org.authorsite.bib.ejb.facade;

import junit.framework.*;
import java.util.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.exceptions.*;
import org.authorsite.bib.ejb.facade.*;
/**
 *
 * @author jejking
 * @version $Revision: 1.3 $
 */
public class MediaItemManagementTest extends TestCase {
    
    private InitialContext context;
    private MediaItemManagementFacade facade;
    private PublishedMediaItemSearchFacade publishedSearchFacade;
    private UnpublishedMediaItemSearchFacade unpublishedSearchFacade;
    private PersonAndOrganisationManagementFacade personAndOrganisationManagementFacade;
    
    protected void setUp() throws Exception {
        context = new InitialContext();
        
        // set up main facade
        Object obj1 = context.lookup("ejb/MediaItemManagementFacadeEJB");
        MediaItemManagementFacadeHome facadeHome = (MediaItemManagementFacadeHome) PortableRemoteObject.narrow(obj1, MediaItemManagementFacadeHome.class);
        facade = facadeHome.create();
        
        // set up facade to search for published items
        Object obj2 = context.lookup("ejb/PublishedMediaItemSearchFacadeEJB");
        PublishedMediaItemSearchFacadeHome publishedSearchFacadeHome = (PublishedMediaItemSearchFacadeHome)
        PortableRemoteObject.narrow(obj2, PublishedMediaItemSearchFacadeHome.class);
        publishedSearchFacade = publishedSearchFacadeHome.create();
        
        // set up facade to search for unpublished items
        Object obj3 = context.lookup("ejb/UnpublishedMediaItemSearchFacadeEJB");
        UnpublishedMediaItemSearchFacadeHome unpublishedSearchFacadeHome = (UnpublishedMediaItemSearchFacadeHome)
        PortableRemoteObject.narrow(obj3, UnpublishedMediaItemSearchFacadeHome.class);
        unpublishedSearchFacade = unpublishedSearchFacadeHome.create();
        
        // set up facade to manage person objects
        Object obj4 = context.lookup("ejb/PersonAndOrganisationManagementFacadeEJB");
        PersonAndOrganisationManagementFacadeHome personAndOrganisationManagementFacadeHome = (PersonAndOrganisationManagementFacadeHome)
        PortableRemoteObject.narrow(obj4, PersonAndOrganisationManagementFacadeHome.class);
        personAndOrganisationManagementFacade = personAndOrganisationManagementFacadeHome.create();
    }
    
    public MediaItemManagementTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MediaItemManagementTest.class);
        return suite;
    }
    
    public void testRemoteMediaItemCreation() {
        try {
            int i;
            i = facade.createMediaItem("created remotely", "book", 1999);
            if (i != 0) {
                return;
            }
            else {
                Assert.fail("i is still 0");
            }
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testRetrievalOfVerySimpleRemotelyCreatedMediaItem() {
        try {
            int pk = facade.createMediaItem("another created remotely", "book", 2000);
            MediaItemDTO retrieved = facade.findByPrimaryKey(pk);
            if (retrieved.getMediaItemID().intValue() != pk) {
                Assert.fail("returned media item dto has wrong value");
            }
            if (!retrieved.getMediaType().equals("book")) {
                Assert.fail("returned media item dto has wrong mediaType");
            }
            if (retrieved.getYearOfCreation() != 2000) {
                Assert.fail("returned media item dto has wrong year of creation");
            }
            return;
        }
        catch (CreateException ce) {
            Assert.fail("create exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("finder exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("remote exception " + re.getMessage());
        }
    }
    
    public void testRemovalOfVerySimpleRemotelyCreatedMediaItem() {
        try {
            // create a media item
            int pk = facade.createMediaItem("another created remotely", "book", 2000);
            System.out.println("remotely created pk = " + pk);
            //remove it
            facade.removeMediaItem(pk);
            
            // now try and find it. this should throw a finder exception
            MediaItemDTO dto = facade.findByPrimaryKey(pk);
            Assert.fail("failed to throw a finder exception trying to look for an item which was removed");
        }
        catch (CreateException ce) {
            Assert.fail("create exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("remote exception " + re.getMessage());
        }
        catch (RemoveException reme) {
            Assert.fail("remove exception " + reme.getMessage());
        }
        catch (FinderException fe) {
            return;
        }
    }
    
    public void testAdditionOfARealLanguage() {
        try {
            int pk = facade.createMediaItem("we'll add a language to this one", "book", 2000);
            MediaItemDTO dtoNoLangs = facade.findByPrimaryKey(pk);
            if (dtoNoLangs.getLanguages().size() != 0) {
                Assert.fail("we have languages when none have been added");
            }
            facade.addLanguageToMediaItem(pk, "eng"); // we'll make it in English
            MediaItemDTO dtoEng = facade.findByPrimaryKey(pk);
            Set dtoEngLangs = dtoEng.getLanguages();
            if (dtoEngLangs.size() > 1) {
                Assert.fail("we have more than one language when only one has been added");
            }
            Iterator langsIt = dtoEngLangs.iterator();
            while (langsIt.hasNext()) {
                LanguageDTO dto = (LanguageDTO) langsIt.next();
                if (dto.getIso639().equals("eng")) {
                    return;
                }
                else {
                    Assert.fail("language wasn't English when English was the language set");
                }
            }
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception + " + fe.getMessage());
        }
        catch (ClassCastException cce) {
            Assert.fail("Class cast exception " + cce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testAdditionOfANonExistentLanguage() {
        try {
            int pk = facade.createMediaItem("a work to which we will attempt to add a nonexistent langauge", "book", 1888);
            facade.addLanguageToMediaItem(pk, "non existent"); // should throw finder exception
            Assert.fail("failed to throw finder exception when attempting to add non existent language");
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            return;
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testAdditionOfLanguages() {
        try {
            int pk = facade.createMediaItem("A multilingual Ding", "book", 2002);
            HashSet langsToAddSet = new HashSet();
            langsToAddSet.add("eng");
            langsToAddSet.add("deu");
            //facade.addMultipleLanguagesToMediaItem(pk, langsToAddSet);
            facade.addLanguageToMediaItem(pk, "eng");
            facade.addLanguageToMediaItem(pk, "deu");
            MediaItemDTO dto = facade.findByPrimaryKey(pk);
            // see if we've got the right languages
            Set dtoLangs = dto.getLanguages();
            /*
            if (dtoLangs.size() != 2) {
                Assert.fail("dto did not have two languages when only two were set");
            } */
            boolean foundEnglish = false;
            boolean foundGerman = false;
            Iterator dtoLangsIt = dtoLangs.iterator();
            while (dtoLangsIt.hasNext()) {
                LanguageDTO langDTO = (LanguageDTO) dtoLangsIt.next();
                System.err.println("LangDTO "+ langDTO.getIso639());
                if (langDTO.getIso639().equals("eng")) {
                    foundEnglish = true;
                }
                else if (langDTO.getIso639().equals("deu")) {
                    foundGerman = true;
                }
            }
            if (!foundEnglish) {
                Assert.fail("failed to find English in the dto");
            }
            if (!foundGerman) {
                Assert.fail("failed to find German in the dto");
            }
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception  " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testRemovalOfLanguageFromSimpleItem() {
        try {
            int pk = facade.createMediaItem("a multilingual item which will lose a language", "book", 1977);
            facade.addLanguageToMediaItem(pk, "spa");
            facade.addLanguageToMediaItem(pk, "por");
            // oops, it wasn't meant to be in portugese as well
            facade.removeLanguageFromMediaItem(pk, "por");
            MediaItemDTO itemDTO = facade.findByPrimaryKey(pk);
            
            Set dtoLangs = itemDTO.getLanguages();
            if (dtoLangs.size() > 1) {
                Assert.fail("we have more than one language when only one should be left");
            }
            Iterator langsIt = dtoLangs.iterator();
            while (langsIt.hasNext()) {
                LanguageDTO dto = (LanguageDTO) langsIt.next();
                if (dto.getIso639().equals("spa")) {
                    return;
                }
                if (dto.getIso639().equals("por")) {
                    Assert.fail("portugese was still there when it should have been removed");
                }
                else {
                    Assert.fail("language wasn't English when English was the language set");
                }
            }
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Detail Exception " + ide.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    
    public void testaAddPermittedMediaProductionRelationship() {
        try {
            int pk = facade.createMediaItem("a book with an author relationship", "book", 2002);
            facade.addMediaProductionRelationship(pk, "author");
            MediaItemDTO itemDTO = facade.findByPrimaryKey(pk);
            // look in the dto and see if we've got a mediaProductionRelationship in there
            Set itemDTOProdRels = itemDTO.getMediaProductionRelationships();
            if (itemDTOProdRels.size() > 1) {
                Assert.fail("item dto had more than one production relationship when only one was set");
            }
            Iterator itemDTOProdRelsIt = itemDTOProdRels.iterator();
            while (itemDTOProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) itemDTOProdRelsIt.next();
                if (prodRelDTO.getRelationshipType().equals("author")) {
                    return;
                }
                else {
                    Assert.fail("author relationship not found... found " + prodRelDTO.getRelationshipType());
                }
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exception");
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testMultiplePermittedMediaProductionRelationships() {
        try {
            int pk = facade.createMediaItem("a book with an author and editor relationship", "book", 2002);
            facade.addMediaProductionRelationship(pk, "author");
            facade.addMediaProductionRelationship(pk, "editor");
            MediaItemDTO itemDTO = facade.findByPrimaryKey(pk);
            // look in the dto and see if we've got a mediaProductionRelationship in there
            Set itemDTOProdRels = itemDTO.getMediaProductionRelationships();
            if (itemDTOProdRels.size() > 2) {
                Assert.fail("item dto had more than two production relationships when only two were set");
            }
            boolean foundAuthor = false;
            boolean foundEditor = false;
            Iterator itemDTOProdRelsIt = itemDTOProdRels.iterator();
            while (itemDTOProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) itemDTOProdRelsIt.next();
                if (prodRelDTO.getRelationshipType().equals("author")) {
                    foundAuthor = true;
                }
                else if (prodRelDTO.getRelationshipType().equals("editor")){
                    foundEditor = true;
                }
            }
            if (!foundAuthor) {
                Assert.fail("returned dto did not contain author relationship");
            }
            if (!foundEditor) {
                Assert.fail("returned dto did not contain editor relationship");
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exception");
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testNotPermittedMediaProductionRelationships() {
        try {
            int pk = facade.createMediaItem("a book with an author relationship", "book", 2002);
            facade.addMediaProductionRelationship(pk, "notAllowed");
            MediaItemDTO itemDTO = facade.findByPrimaryKey(pk);
            
            // we should not get here. A RelationshipNotPermittedException should be thrown
            Assert.fail("Relationship Not Permitted Exception was not thrown despite attempt to set illegal relationship type");
        }
        catch (RelationshipNotPermittedException rnpe) {
            return; // correct behaviour
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testNonExistentMediaProductionRelationship() {
        try {
            int pk = facade.createMediaItem("a book with an author relationship", "book", 2002);
            facade.addMediaProductionRelationship(pk, "fictional");
            MediaItemDTO itemDTO = facade.findByPrimaryKey(pk);
            
            // we should not get here. A RelationshipNotPermittedException should be thrown
            Assert.fail("Relationship Not Permitted Exception was not thrown despite attempt to set non-existent relationship type");
        }
        catch (RelationshipNotPermittedException rnpe) {
            return; // correct behaviour
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testAdditionOfPersonToProductionRelationship() {
        try {
            int itemPK = facade.createMediaItem("a book with an author and a person who is that author", "book", 2002);
            int personPK = personAndOrganisationManagementFacade.createPerson("AuthorName");
            int prodRelPK = facade.addMediaProductionRelationship(itemPK, "author");
            facade.addPersonToProductionRelationship(prodRelPK, personPK);
            MediaItemDTO dto = facade.findByPrimaryKey(itemPK);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                // now look at the PersonDTOs inside the prodRelDTO
                Set prodRelDTOPeople = prodRelDTO.getPeople();
                Iterator prodRelDTOPeopleIt = prodRelDTOPeople.iterator();
                while (prodRelDTOPeopleIt.hasNext()) {
                    PersonDTO personDTO = (PersonDTO) prodRelDTOPeopleIt.next();
                    if (personDTO.getMainName().equals("AuthorName")) {
                        return;
                    }
                }
            }
            Assert.fail("failed to find personDTO with correct name");
        }
        catch (RelationshipNotPermittedException rnpe) {
            return; // correct behaviour
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testAdditionOfPeopleToProductionRelationship() {
        try {
            int itemPK = facade.createMediaItem("a book with an author and a person who is that author", "book", 2002);
            int personPK = personAndOrganisationManagementFacade.createPerson("AuthorName");
            int person2PK = personAndOrganisationManagementFacade.createPerson("AnotherAuthorName");
            int prodRelPK = facade.addMediaProductionRelationship(itemPK, "author");
            facade.addPersonToProductionRelationship(prodRelPK, personPK);
            facade.addPersonToProductionRelationship(prodRelPK, person2PK); // we now have two authors
            MediaItemDTO dto = facade.findByPrimaryKey(itemPK);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            boolean foundAuthor1 = false;
            boolean foundAuthor2 = false;
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                // now look at the PersonDTOs inside the prodRelDTO
                Set prodRelDTOPeople = prodRelDTO.getPeople();
                Iterator prodRelDTOPeopleIt = prodRelDTOPeople.iterator();
                while (prodRelDTOPeopleIt.hasNext()) {
                    PersonDTO personDTO = (PersonDTO) prodRelDTOPeopleIt.next();
                    if (personDTO.getMainName().equals("AuthorName")) {
                        foundAuthor1 = true;
                    }
                    if (personDTO.getMainName().equals("AnotherAuthorName")) {
                        foundAuthor2 = true;
                    }
                }
            }
            if (!foundAuthor1) {
                Assert.fail("Failed to retrieve author 1 from dto");
            }
            if (!foundAuthor2) {
                Assert.fail("Failed to retrieve author 2 from dto");
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            return; // correct behaviour
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testSimpleMediaProductionRelationshipRemoval() {
        try {
            int pk = facade.createMediaItem("item from which we will remove a production relationship", "book", 2002);
            int prodRelPK = facade.addMediaProductionRelationship(pk, "author");
            int prodRel2PK =facade.addMediaProductionRelationship(pk, "editor");
            
            // hm, the editor was a mistake...
            facade.removeMediaProductionRelationship(pk, prodRel2PK);
            MediaItemDTO dto = facade.findByPrimaryKey(pk);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            if (dtoProdRels.size() > 1) {
                Assert.fail("DTO had too many production relationships. SHould only have one");
            }
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                if (prodRelDTO.getRelationshipType().equals("editor")) {
                    Assert.fail("found editor production relationship. this should have been removed");
                }
                if (!prodRelDTO.getRelationshipType().equals("author")) {
                    Assert.fail("relationship type other than author found in dto");
                }
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship not permitted exception");
        }
        catch (RelationshipRemovalNotPermittedException rrnpe) {
            Assert.fail("Relationship Removal Not Permitted Exception");
        }
        catch (CreateException ce) {
            Assert.fail("CreateException " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("FinderException " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("RemoteException " + re.getMessage());
        }
    }
    
    public void testSimpleRemovePersonFromProductionRelationship() {
        try {
            int itemPK = facade.createMediaItem("a book with an author and a person who is that author", "book", 2002);
            int personPK = personAndOrganisationManagementFacade.createPerson("AuthorName");
            int person2PK = personAndOrganisationManagementFacade.createPerson("AnotherAuthorName");
            int prodRelPK = facade.addMediaProductionRelationship(itemPK, "author");
            facade.addPersonToProductionRelationship(prodRelPK, personPK);
            facade.addPersonToProductionRelationship(prodRelPK, person2PK); // we now have two authors
            
            // oops. person2 was the wrong one. remove that person!
            facade.removePersonFromProductionRelationship(prodRelPK, person2PK);
            MediaItemDTO dto = facade.findByPrimaryKey(itemPK);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            if (dtoProdRels.size() >1 ) {
                Assert.fail("There are two authors. There should only be one after one was removed from the production relationship");
            }
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            boolean foundAuthor1 = false;
            boolean foundAuthor2 = false;
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                // now look at the PersonDTOs inside the prodRelDTO
                Set prodRelDTOPeople = prodRelDTO.getPeople();
                Iterator prodRelDTOPeopleIt = prodRelDTOPeople.iterator();
                while (prodRelDTOPeopleIt.hasNext()) {
                    PersonDTO personDTO = (PersonDTO) prodRelDTOPeopleIt.next();
                    if (personDTO.getMainName().equals("AuthorName")) {
                        foundAuthor1 = true;
                    }
                    if (personDTO.getMainName().equals("AnotherAuthorName")) {
                        foundAuthor2 = true;
                    }
                }
            }
            if (!foundAuthor1) {
                Assert.fail("Failed to retrieve author 1 from dto");
            }
            if (foundAuthor2) {
                Assert.fail("author 2 was retrieved from from dto after this person was supposedly removed from the relationship");
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("RelationshipNotPermittedException");
        }
        catch (EmptyingObligatoryRelationshipNotPermittedException eornpe) {
            Assert.fail("Emptying Obligatory Relationship Not Permitted Exception " + eornpe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testAdditionOfOrganisationToProductionRelationship() {
        try {
            int itemPK = facade.createMediaItem("a book with an author and a person who is that author", "book", 2002);
            int orgPK = personAndOrganisationManagementFacade.createOrganisation("An Authoring Organisation");
            int prodRelPK = facade.addMediaProductionRelationship(itemPK, "author");
            facade.addOrganisationToProductionRelationship(prodRelPK, orgPK);
            MediaItemDTO dto = facade.findByPrimaryKey(itemPK);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                // now look at the PersonDTOs inside the prodRelDTO
                Set prodRelDTOOrgs = prodRelDTO.getOrganisations();
                Iterator prodRelDTOOrgsIt = prodRelDTOOrgs.iterator();
                while (prodRelDTOOrgsIt.hasNext()) {
                    OrganisationDTO orgDTO = (OrganisationDTO) prodRelDTOOrgsIt.next();
                    if (orgDTO.getName().equals("An Authoring Organisation")) {
                        return;
                    }
                }
            }
            Assert.fail("failed to find organisation DTO with correct name");
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exception");
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testAdditionOfOrganisationsToProductionRelationship() {
        try {
            int itemPK = facade.createMediaItem("a book with two organisations who are its authors", "book", 2002);
            int orgPK = personAndOrganisationManagementFacade.createOrganisation("The UN");
            int org2PK = personAndOrganisationManagementFacade.createOrganisation("The EU");
            int prodRelPK = facade.addMediaProductionRelationship(itemPK, "author");
            facade.addOrganisationToProductionRelationship(prodRelPK, orgPK);
            facade.addOrganisationToProductionRelationship(prodRelPK, org2PK); // we now have two authors
            MediaItemDTO dto = facade.findByPrimaryKey(itemPK);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            boolean foundAuthor1 = false;
            boolean foundAuthor2 = false;
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                // now look at the PersonDTOs inside the prodRelDTO
                Set prodRelDTOOrgs = prodRelDTO.getOrganisations();
                Iterator prodRelDTOOrgsIt = prodRelDTOOrgs.iterator();
                while (prodRelDTOOrgsIt.hasNext()) {
                    OrganisationDTO orgDTO = (OrganisationDTO) prodRelDTOOrgsIt.next();
                    if (orgDTO.getName().equals("The UN")) {
                        foundAuthor1 = true;
                    }
                    if (orgDTO.getName().equals("The EU")) {
                        foundAuthor2 = true;
                    }
                }
            }
            if (!foundAuthor1) {
                Assert.fail("Failed to retrieve author 1 (The UN) from dto");
            }
            if (!foundAuthor2) {
                Assert.fail("Failed to retrieve author 2 (The EU) from dto");
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            return; // correct behaviour
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testSimpleRemoveOrganisationFromProductionRelationship() {
        try {
            int itemPK = facade.createMediaItem("a book with an author and some organisations who are the author", "book", 2002);
            int orgPK = personAndOrganisationManagementFacade.createOrganisation("The UN");
            int org2PK = personAndOrganisationManagementFacade.createOrganisation("The EU");
            int prodRelPK = facade.addMediaProductionRelationship(itemPK, "author");
            facade.addOrganisationToProductionRelationship(prodRelPK, orgPK);
            facade.addOrganisationToProductionRelationship(prodRelPK, org2PK); // we now have two authors
            
            // oops. the EU was the wrong one.
            facade.removeOrganisationFromProductionRelationship(prodRelPK, org2PK);
            MediaItemDTO dto = facade.findByPrimaryKey(itemPK);
            Set dtoProdRels = dto.getMediaProductionRelationships();
            if (dtoProdRels.size() >1 ) {
                Assert.fail("There are two authors. There should only be one after one was removed from the production relationship");
            }
            Iterator dtoProdRelsIt = dtoProdRels.iterator();
            boolean foundAuthor1 = false;
            boolean foundAuthor2 = false;
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRelDTO = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                // now look at the OrganisationDTOs inside the prodRelDTO
                Set prodRelDTOOrgs = prodRelDTO.getOrganisations();
                Iterator prodRelDTOOrgsIt = prodRelDTOOrgs.iterator();
                while (prodRelDTOOrgsIt.hasNext()) {
                    OrganisationDTO orgDTO = (OrganisationDTO) prodRelDTOOrgsIt.next();
                    if (orgDTO.getName().equals("The UN")) {
                        foundAuthor1 = true;
                    }
                    if (orgDTO.getName().equals("The EU")) {
                        foundAuthor2 = true;
                    }
                }
            }
            if (!foundAuthor1) {
                Assert.fail("Failed to retrieve author 1 (The UN) from dto");
            }
            if (foundAuthor2) {
                Assert.fail("author 2 (the EU) was retrieved from from dto after this person was supposedly removed from the relationship");
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("RelationshipNotPermittedException");
        }
        catch (EmptyingObligatoryRelationshipNotPermittedException eornpe) {
            Assert.fail("Emptying Obligatory Relationship Not Permitted Exception " + eornpe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testValidSimpleIntraMediaRelationshipCreation() {
        try {
            int fromItemPK = facade.createMediaItem("a book which will be translated", "book", 1999);
            int toItemPK = facade.createMediaItem("ein uebersetztes Buch", "book", 2000);
            int imrPK = facade.createIntraMediaRelationship(fromItemPK, "translation", toItemPK);
            
            // do the test by looking in the database, you cheat. Need to build the finder methods properly...
            // (seems to work according to the db...)
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("RelationshipNotPermittedException");
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testInvalidSimpleIntraMediaRelationshipCreation() {
        try {
            int fromItemPK = facade.createMediaItem("a book which will be translated", "book", 1999);
            int toItemPK = facade.createMediaItem("ein uebersetztes Buch", "book", 2000);
            // this should fail. a book cannot be contained in another book according to our rules
            int imrPK = facade.createIntraMediaRelationship(fromItemPK, "containment", toItemPK);
            Assert.fail("failed to prevent creation of invalid relationship");
        }
        catch (RelationshipNotPermittedException rnpe) {
            return; // we want this exception...
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testConstructionOfContainmentRelationship() {
        try {
            // we'll put a bookArticle into a book
            int bookArticlePK = facade.createMediaItem("a book article which will be contained in a book", "bookArticle", 2000);
            int bookPK = facade.createMediaItem("a book which will contain a book article", "book", 2000);
            int imrPK = facade.createIntraMediaRelationship(bookArticlePK, "containment", bookPK);
            
            // fetch the bookArticle
            MediaItemDTO articleDTO = facade.findByPrimaryKey(bookArticlePK);
            // lets see if it's got the write contained DTO embedded in it.
            MediaItemDTO articleContainerDTO = articleDTO.getContainedIn();
            if (articleContainerDTO.getMediaItemID().intValue() == bookPK) {
                return;
            }
            else {
                Assert.fail("the article DTO did not report hte correct container...");
            }
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exception");
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    public void testConstructionOfDuplicateContainmentRelationship() {
        try {
            // we'll put a bookArticle into a book
            int bookArticlePK = facade.createMediaItem("a book article which will be contained in a book", "bookArticle", 2000);
            int bookPK = facade.createMediaItem("a book which will contain a book article", "book", 2000);
            int book2PK = facade.createMediaItem("A book which we'll try and put a the same article into...", "book", 2000);
            int imrPK = facade.createIntraMediaRelationship(bookArticlePK, "containment", bookPK);
            // now try and make a duplicate containment relationship. this should fail
            int imr2pk = facade.createIntraMediaRelationship(bookArticlePK, "containment", book2PK);
            
        }
        catch (RelationshipNotPermittedException rnpe) {
            if (rnpe.getMessage() == null) {
                Assert.fail("Relationhsip not permitted exception with null message");
            }
            if (rnpe.getMessage().equals("duplicate containment relationships are not permitted")) {
                return;
            }
            else {
                Assert.fail("Relationsihp Not Permitted Exception with the wrong message " + rnpe.getMessage());
            }
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
    }
    
    /*
     * <p>
     * Tests whether the removal of an item which contains items (i.e. is on the "to" end of a containment
     * relationship) actually results in the removal of those contained items.
     *</p>
     * <p>
     * <b>Note</b> - whether or not this slightly drastic behaviour is actually a good idea, remains to be seen. The
     * alternative would be to remove the relationship and unpublish any contained items that were already
     * published.
     * </p>
     */
    public void testRemovalOfContainer() {
        try {
            //
            int containerPK = facade.createMediaItem("a book with articles in it that should disappear", "book", 2002);
            int containedPK = facade.createMediaItem("a book article that should go when the book it is in is removed", "bookArticle", 2002);
            int imrPK = facade.createIntraMediaRelationship(containedPK, "containment", containerPK);
            
            // the article is now contained in the book. lets delete the book
            facade.removeMediaItem(containerPK);
            
            // the article should have been removed too. Lets look for it. we expect a finderException
            MediaItemDTO articleDTO = facade.findByPrimaryKey(containedPK);
            Assert.fail("attempt to find an article contained in a book which was deleted did not result in a finder exception");
        }
        catch (FinderException fe) {
            return; // what we expect
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("RelationshipNotPermittedException " + rnpe.getMessage());
        }
        catch (RemoveException reme) {
            Assert.fail("Remove Exception " + reme.getMessage());
        }
    }
    
    public void testRemovalOfMultipleItemsFromContainer() {
        try {
            //
            int containerPK = facade.createMediaItem("a book with articles in it that should disappear", "book", 2002);
            int containedPK = facade.createMediaItem("a book article that should go when the book it is in is removed", "bookArticle", 2002);
            int contained2PK = facade.createMediaItem("a second book article that should go when the book it is in is removed", "bookArticle", 2002);
            int imrPK = facade.createIntraMediaRelationship(containedPK, "containment", containerPK);
            int imr2PK = facade.createIntraMediaRelationship(contained2PK, "containment", containerPK);
            // the article is now contained in the book. lets delete the book
            facade.removeMediaItem(containerPK);
            
            // the second article should have been removed too. Lets look for it. we expect a finderException
            MediaItemDTO articleDTO = facade.findByPrimaryKey(contained2PK);
            Assert.fail("attempt to find an article contained in a book which was deleted did not result in a finder exception");
        }
        catch (FinderException fe) {
            return; // what we expect
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("RelationshipNotPermittedException " + rnpe.getMessage());
        }
        catch (RemoveException reme) {
            Assert.fail("Remove Exception " + reme.getMessage());
        }
    }
    
    /*
     * here, we test whether removing a bookSeries containing a book also removes
     * the bookArticle contained in the book
     */
    public void testRemovalOfContainersOfContainers() {
        try {
            //
            int topContainerPK = facade.createMediaItem("a book series with books in it", "bookSeries", 2002);
            int containerPK = facade.createMediaItem("a book with articles in it that should disappear", "book", 2002);
            int containedPK = facade.createMediaItem("a book article that should go when the book it is in is removed", "bookArticle", 2002);
            int contained2PK = facade.createMediaItem("a second book article that should go when the book it is in is removed", "bookArticle", 2002);
            // make the book be part of the bookSeries
            int imrTopPK = facade.createIntraMediaRelationship(containerPK, "containment", topContainerPK);
            int imrPK = facade.createIntraMediaRelationship(containedPK, "containment", containerPK);
            int imr2PK = facade.createIntraMediaRelationship(contained2PK, "containment", containerPK);
            // the articles arenow contained in the book
            
            // lets delete the bookSeries. this should result in the book and the book articles being deleted
            facade.removeMediaItem(containerPK);
            
            // 1. look for the book
            try {
                MediaItemDTO bookDTO = facade.findByPrimaryKey(containerPK);
                Assert.fail("The book wasn't deleted");
            }
            catch (FinderException fe1) { // ok, carry on
            }
            
            // 2. look for one of the book articles
            try {
                MediaItemDTO articleDTO = facade.findByPrimaryKey(contained2PK);
                Assert.fail("the second book article wasn't deleted");
            }
            catch (FinderException fe2) { // ok. we'll return now
                return;
            }
        }
        catch (FinderException fe) {
            Assert.fail("Generic Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("RelationshipNotPermittedException " + rnpe.getMessage());
        }
        catch (RemoveException reme) {
            Assert.fail("Remove Exception " + reme.getMessage());
        }
    }
    
    public void testCorrectCreationOfDetails() {
        try {
            int pk = facade.createMediaItem("a book article with details attached", "bookArticle", 2002);
            // construct an articleDetailsDTO. this would normally be assembled by client side code
            BookArticleDetailsDTO dto = new BookArticleDetailsDTO();
            dto.setBookArticleDetailsID(new Integer(pk));
            dto.setFirstPage("1");
            dto.setLastPage("2");
            facade.createMediaItemDetails(dto);
            
            MediaItemDTO mediaItemDTO = facade.findByPrimaryKey(pk);
            BookArticleDetailsDTO retrievedDTO = (BookArticleDetailsDTO) mediaItemDTO.getDetailsDTO();
            if (retrievedDTO == null) {
                Assert.fail("retrieved details DTO was null");
            }
            if (!retrievedDTO.getFirstPage().equals("1")) {
                Assert.fail("retrieved details dto did not have first page of \"1\"");
            }
            if (!retrievedDTO.getLastPage().equals("2")) {
                Assert.fail("retrieved details dot did not have last page of \"2\"");
            }
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Detail Exception");
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
    }
    
    public void testCreationOfDetailsWithInsufficientDetail() {
        try {
            int pk = facade.createMediaItem("a book article with details attached", "bookArticle", 2002);
            // construct an articleDetailsDTO. this would normally be assembled by client side code
            BookArticleDetailsDTO dto = new BookArticleDetailsDTO();
            dto.setBookArticleDetailsID(new Integer(pk));
            dto.setFirstPage("1");
            // dto.setLastPage("2"); we leave this out!! But it is an obligatory field. Must fail...
            facade.createMediaItemDetails(dto);
            Assert.fail("Failed to throw insufficient detail exception");
        }
        catch (InsufficientDetailException ide) {
            return;
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        
    }
    
    public void testCreationOfDetailsWithWrongSortOfDetails() {
        try {
            int pk = facade.createMediaItem("a book article with details attached", "bookArticle", 2002);
            // construct a bookDetailsDTO. obviously, this shouldn't be happening in real life. but you never know.
            BookDetailsDTO dto = new BookDetailsDTO();
            dto.setBookDetailsID(new Integer(pk));
            dto.setIsbn("An isbn");
            facade.createMediaItemDetails(dto);
            Assert.fail("Failed to throw create exception when attempting to attach wrong sort of details");
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Detail Exception ");
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (CreateException ce) {
            if (ce.getMessage().equals("mediaItemDetailsDTO must have same mediaType attribute as the mediaItem it is providing the details for")) {
                return;
            }
            else {
                Assert.fail("Create Exception " + ce.getMessage());
            }
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
    }
    
    public void testCorrectEditionOfMediaItemCore() {
        try {
            int pk = facade.createMediaItem("a book we'll change some details about", "book", 2002);
            MediaItemDTO dtoWithChangesToMake = new MediaItemDTO(new Integer(pk));
            dtoWithChangesToMake.setMediaType("book");
            dtoWithChangesToMake.setTitle("the new title");
            dtoWithChangesToMake.setComment("here is a comment about my book");
            dtoWithChangesToMake.setAdditionalInfo("and some additional info about my book");
            dtoWithChangesToMake.setYearOfCreation(2001);
            facade.editMediaItemCore(dtoWithChangesToMake);
            MediaItemDTO retrievedDTO = facade.findByPrimaryKey(pk);
            
            // lets see if things have been changed/appended correctly
            if (!retrievedDTO.getTitle().equals("the new title")) {
                Assert.fail("title was not updated");
            }
            if (retrievedDTO.getYearOfCreation() != 2001) {
                Assert.fail("year of creation not updated");
            }
            if (!retrievedDTO.getAdditionalInfo().equals("and some additional info about my book")) {
                Assert.fail("additional info not added correctly");
            }
            if (!retrievedDTO.getComment().equals("here is a comment about my book")) {
                Assert.fail("comment not added correctly");
            }
        }
        catch (ChangeNotPermittedException cnpe) {
            Assert.fail("Change not permitted exception " + cnpe.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("RemoteException " + re.getMessage());
        }
    }
    
    public void testIncorrectEditionOfMediaItemCore1() {
        try {
            int pk = facade.createMediaItem("a book we'll change some details about", "book", 2002);
            MediaItemDTO dtoWithChangesToMake = new MediaItemDTO(new Integer(pk));
            dtoWithChangesToMake.setTitle("another new title");
            // this is wrong...
            dtoWithChangesToMake.setMediaType("bookArticle");
            
            facade.editMediaItemCore(dtoWithChangesToMake);
            Assert.fail("failed to throw Change Not PermittedException");
        }
        catch (ChangeNotPermittedException cnpe) {
            return;
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("RemoteException " + re.getMessage());
        }
    }
    
    public void testIncorrectEditionOfMediaItemCore2() {
        try {
            int pk = facade.createMediaItem("a book we'll change some details about", "book", 2002);
            MediaItemDTO dtoWithChangesToMake = new MediaItemDTO(new Integer(pk));
            dtoWithChangesToMake.setTitle("another new title");
            // this is wrong...
            dtoWithChangesToMake.setMediaType("bookArticle");
            
            facade.editMediaItemCore(dtoWithChangesToMake);
            Assert.fail("failed to throw Change Not PermittedException");
        }
        catch (ChangeNotPermittedException cnpe) {
            return;
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("RemoteException " + re.getMessage());
        }
    }
    
    public void testIncorrectEditionOfMediaItemCore3() {
        try {
            int pk = facade.createMediaItem("a book we'll change some details about", "book", 2002);
            MediaItemDTO dtoWithChangesToMake = new MediaItemDTO(new Integer(pk));
            dtoWithChangesToMake.setTitle("another new title");
            // this is wrong...
            dtoWithChangesToMake.setPublishedFlag(true);
            
            facade.editMediaItemCore(dtoWithChangesToMake);
            Assert.fail("failed to throw Change Not PermittedException");
        }
        catch (ChangeNotPermittedException cnpe) {
            return;
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("RemoteException " + re.getMessage());
        }
    }
    
    public void testIncorrectEditionOfMediaItemCore4() {
        try {
            int pk = facade.createMediaItem("a book we'll change some details about", "book", 2002);
            MediaItemDTO dtoWithChangesToMake = new MediaItemDTO(new Integer(pk));
            // we leave title as null. should fail
            
            facade.editMediaItemCore(dtoWithChangesToMake);
            Assert.fail("failed to throw Change Not PermittedException");
        }
        catch (ChangeNotPermittedException cnpe) {
            return;
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("RemoteException " + re.getMessage());
        }
    }
    
    public void testCorrectEditionOfMediaItemDetails() {
        try {
            int bookPK = facade.createMediaItem("a book we'll change the details ok", "book", 2002);
            BookDetailsDTO initialDetailsDTO = new BookDetailsDTO(new Integer(bookPK));
            initialDetailsDTO.setSubtitle("a subtitle to change");
            facade.createMediaItemDetails(initialDetailsDTO);
            // now, we change our mind about the subtitle and want to add another bit of detail
            BookDetailsDTO changedDetailsDTO = new BookDetailsDTO(new Integer(bookPK));
            changedDetailsDTO.setSubtitle("the new subtitle");
            changedDetailsDTO.setNumberOfPages("700");
            // submit our changes
            facade.editMediaItemDetails(changedDetailsDTO);
            // look up the item and see if the returned DTO contains our changes
            MediaItemDTO itemDTO = facade.findByPrimaryKey(bookPK);
            BookDetailsDTO retrievedDetailsDTO = (BookDetailsDTO) itemDTO.getDetailsDTO();
            if (!retrievedDetailsDTO.getSubtitle().equals("the new subtitle")) {
                Assert.fail("retrieved DTO did not have correct subtitle");
            }
            if (!retrievedDetailsDTO.getNumberOfPages().equals("700")) {
                Assert.fail("retrieved DTO did not have  correct number of pages");
            }
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Detail Exception" + ide.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exeption  " + re.getMessage());
        }
    }
    
}
