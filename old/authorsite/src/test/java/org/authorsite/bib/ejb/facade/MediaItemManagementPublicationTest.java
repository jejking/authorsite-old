/*
 * MediaItemManagementPublicationTest.java
 * JUnit based test
 *
 * Created on 13 December 2002, 15:14
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/facade/MediaItemManagementPublicationTest.java,v 1.1 2002/12/16 19:57:49 jejking Exp $
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
 * This class exercises the methods which govern the publication and removal from
 * publication of MediaItems.
 *
 * @author jejking
 * @version $Revision: 1.1 $
 */
public class MediaItemManagementPublicationTest extends TestCase {
    
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
    
    
    public MediaItemManagementPublicationTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MediaItemManagementPublicationTest.class);
        return suite;
    }
    
    /**
     * In this test, we create a fully valid and complete bookArticle, correctly contained in a book
     * with an author, publisher and details.
     */
    public void testPublicationOfAValidBookArticle() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Details Exception " + ide.getMessage());
        }
    }
    
    public void testPublicationWithMissingDetails() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // leave out the details...
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            // we want this one as we're missing obligatory details
            if (ide.getMessage().equals("There are obligatory details fields but no details object")) {
                return;
            }
            else {
                Assert.fail("Insufficient Detail Exception " + ide.getMessage());
            }
        }
    }
    
    public void testPublicationWithMissingObligatoryProductionRelationship() {
        
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // no author production relationship!!
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            // we want this one as we're missing obligatory details
            if (ide.getMessage().equals("The obligatory mediaProductionRelationship author is not yet defined for this item")) {
                return;
            }
            else {
                Assert.fail("Insufficient Detail Exception " + ide.getMessage());
            }
        }
    }
    
    public void testPublicationWithEmptyObligatoryProductionRelationship() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // NO person or org attached to obligatory relationship
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            // we want this one as we're missing obligatory details
            if (ide.getMessage().equals("The obligatory mediaProductionRelationship author does not have at least one person or organisation")) {
                return;
            }
            else {
                Assert.fail("Insufficient Detail Exception " + ide.getMessage());
            }
        }
    }
    
    public void testPublicationWithMissingObligatoryIntramediaRelationship() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // Leave out the obligatory containment relationship
            
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            if (ide.getMessage().equals("The obligatory intraMediaRelationship containment is not defined for this item")) {
                return;
            }
            else {
                Assert.fail("Insufficient Details Exception " + ide.getMessage());
            }
        }
    }
    
    public void testPublicationWithIncompleteNonpublishedContainer() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            //LEAVE OUT PUBLISHER. The container is thus incomplete...
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            if (ide.getMessage().equals("item could not be published as its container could not be published because of an insufficientDetailExcepton The obligatory mediaProductionRelationship publisher is not yet defined for this item")) {
                return;
            }
            else {
                Assert.fail("Insufficient Details Exception " + ide.getMessage());
            }
        }
    }
    
    public void testPublishWithNoLanguageSet() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            // no languages set
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            if (ide.getMessage().equals("This media item has no languages defined")) {
                return;
            }
            else {
                Assert.fail("Insufficient Details Exception " + ide.getMessage());
            }
        }
    }
    
    public void testUnpublishWithNoContainedItems() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
            
            // now what we want to do is unpublish the bookArticle
            facade.unpublishMediaItem(articlePK);
            
            // check it is unpublished by getting the dto
            MediaItemDTO retrievedDTO = facade.findByPrimaryKey(articlePK);
            if (retrievedDTO.getPublishedFlag()) {
                Assert.fail("Article was still published after it had been unpublished");
            }
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Details Exception " + ide.getMessage());
        }
    }
    
    public void testUnpublishWithContainedItems() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
            
            // unpublish the book. the bookArticle should also be unpublished
            facade.unpublishMediaItem(bookPK);
            
            // validate the article has also been unpublished
            MediaItemDTO retrievedDTO = facade.findByPrimaryKey(articlePK);
            if (retrievedDTO.getPublishedFlag()) {
                Assert.fail("we failed to unpublish the contained bookArticle");
            }
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Details Exception " + ide.getMessage());
        }
    }
    
    public void testRemovalOfLanguageFromPublishedMediaItem() {
        try {
            // create and publish a book in English
            int bookPK = facade.createMediaItem("an olde booke in ye englishe language", "book", 1543);
            int publisherPK = personAndOrganisationManagementFacade.createOrganisation("Ye olde printe shoppe");
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, publisherPK);
            // we'll make the book be in English
            facade.addLanguageToMediaItem(bookPK, "eng");
            facade.publishMediaItem(bookPK);
            // now we attempt to remove the item's language. should throw an exception
            facade.removeLanguageFromMediaItem(bookPK, "eng");
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            if (ide.getMessage().equals("removal of language would reduce published item to no languages.")) {
                return;
            }
            else {
                Assert.fail("Insufficient Details Exception " + ide.getMessage());
            }
        }
    }
    
    public void testRemovalOfOrganisationFromProductionRelationshipInPublishedItem() {
        try {
            // create and publish a book in English
            int bookPK = facade.createMediaItem("an olde booke in ye englishe language", "book", 1543);
            int publisherPK = personAndOrganisationManagementFacade.createOrganisation("Ye olde printe shoppe");
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, publisherPK);
            // we'll make the book be in English
            facade.addLanguageToMediaItem(bookPK, "eng");
            facade.publishMediaItem(bookPK);
            
            // try and remove the publisher from the production relationship
            facade.removeOrganisationFromProductionRelationship(bookPublisherRelPK, publisherPK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Details Exception " + ide.getMessage());
        }
        catch (EmptyingObligatoryRelationshipNotPermittedException eornpe) {
            return;
        }
    }
    
    public void testRemovalOfPersonFromProductionRelationshipInPublishedItem() {
        try {
            // create the bookArticle
            int articlePK = facade.createMediaItem("A Full Book Article", "bookArticle", 2002);
            // we need an author relationship
            int articleAuthorRelPK = facade.addMediaProductionRelationship(articlePK, "author");
            // we need a person to be that author
            int articleAuthorPersonPK = personAndOrganisationManagementFacade.createPerson("Surname", "FirstName");
            facade.addPersonToProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
            // we need  some details
            BookArticleDetailsDTO articleDetailsDTO = new BookArticleDetailsDTO(new Integer(articlePK));
            articleDetailsDTO.setFirstPage("1");
            articleDetailsDTO.setLastPage("10");
            facade.createMediaItemDetails(articleDetailsDTO);
            
            // create the book
            int bookPK = facade.createMediaItem("A book", "book", 2002);
            // we must have a publisher for the book
            int bookPublisherRelPK = facade.addMediaProductionRelationship(bookPK, "publisher");
            // we'll create an organisation to be that publisher
            int bookPublisherOrgPK = personAndOrganisationManagementFacade.createOrganisation("Publishing House");
            facade.addOrganisationToProductionRelationship(bookPublisherRelPK, bookPublisherOrgPK);
            // and it really ought to have an editor
            int bookEditorPK = personAndOrganisationManagementFacade.createPerson("EditorSurname", "EditorFirstName");
            int bookEditorRelPK = facade.addMediaProductionRelationship(bookPK, "editor");
            facade.addPersonToProductionRelationship(bookEditorRelPK, bookEditorPK);
            
            // make the article be contained in the book
            facade.createIntraMediaRelationship(articlePK, "containment", bookPK);
            
            // set languages for article and book
            facade.addLanguageToMediaItem(articlePK, "eng");
            facade.addLanguageToMediaItem(bookPK, "eng");
            
            // we are now in a position where the book and bookArticle are complete
            // we can publish the bookArticle and the publication should filter up to the book
            facade.publishMediaItem(articlePK);
            
            // now remove the author from the article prodRel
            facade.removePersonFromProductionRelationship(articleAuthorRelPK, articleAuthorPersonPK);
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exeption " + rnpe.getMessage());
        }
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Details Exception " + ide.getMessage());
        }
        catch (EmptyingObligatoryRelationshipNotPermittedException eornpe) {
            return;
        }
    }
}
