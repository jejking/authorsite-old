/*
 * PersonAndOrganisationManagementFacadeTest.java
 *
 * Created on 09 December 2002, 11:19
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/facade/PersonAndOrganisationManagementFacadeTest.java,v 1.2 2002/12/17 15:29:04 jejking Exp $
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
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PersonAndOrganisationManagementFacadeTest extends TestCase {
    
    private InitialContext context;
    private PersonAndOrganisationManagementFacade facade;
    private MediaItemManagementFacade mediaItemManagementFacade;

    protected void setUp() throws Exception {
        context = new InitialContext();
        Object obj1 = context.lookup("ejb/PersonAndOrganisationManagementFacadeEJB");
        PersonAndOrganisationManagementFacadeHome facadeHome = (PersonAndOrganisationManagementFacadeHome) PortableRemoteObject.narrow(obj1, PersonAndOrganisationManagementFacadeHome.class);
        facade = facadeHome.create();
        
        Object obj2 = context.lookup("ejb/MediaItemManagementFacadeEJB");
        MediaItemManagementFacadeHome mediaItemManagementFacadeHome = (MediaItemManagementFacadeHome) PortableRemoteObject.narrow(obj2, MediaItemManagementFacadeHome.class);
        mediaItemManagementFacade = mediaItemManagementFacadeHome.create();
    }
    
    /** Creates a new instance of PersonAndOrganisationManagementFacadeTest */
    public PersonAndOrganisationManagementFacadeTest(String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(PersonAndOrganisationManagementFacadeTest.class);
        return suite;
    }
    
    public void testCreatePersonMethod1() {
        try {
            int pk = facade.createPerson("TestMainName");
            PersonDTO dto = facade.findPersonByPrimaryKey(pk);
            if (dto.getMainName().equals("TestMainName")) {
                return;
            }
            else {
                Assert.fail("created person object had wrong main name");
            }
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
    
    public void testCreatePersonMethod2() {
        try {
            int pk = facade.createPerson("TestMainName", "TestGivenName");
            PersonDTO dto = facade.findPersonByPrimaryKey(pk);
            if (!dto.getMainName().equals("TestMainName")) {
                Assert.fail("created person object had wrong main name");
            }
            if (!dto.getGivenName().equals("TestGivenName")) {
                Assert.fail("created person object had wrong given name");
            }
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
    
    public void testCreatePersonMethod3() {
        try {
            int pk = facade.createPerson("TestMainName", "TestGivenName","Test Other Names");
            PersonDTO dto = facade.findPersonByPrimaryKey(pk);
            if (!dto.getMainName().equals("TestMainName")) {
                Assert.fail("created person object had wrong main name");
            }
            if (!dto.getGivenName().equals("TestGivenName")) {
                Assert.fail("created person object had wrong given name");
            }
            if (!dto.getOtherNames().equals("Test Other Names")) {
                Assert.fail("created person object had wrong other names");
            }
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
    
    public void testCreatePersonMethod4() {
        try {
            int pk = facade.createPerson("TestMainName", "TestGivenName","Test Other Names", "TestSuffix");
            PersonDTO dto = facade.findPersonByPrimaryKey(pk);
            if (!dto.getMainName().equals("TestMainName")) {
                Assert.fail("created person object had wrong main name");
            }
            if (!dto.getGivenName().equals("TestGivenName")) {
                Assert.fail("created person object had wrong given name");
            }
            if (!dto.getOtherNames().equals("Test Other Names")) {
                Assert.fail("created person object had wrong other names");
            }
            if (!dto.getSuffix().equals("TestSuffix")) {
                Assert.fail("created person object had wrong suffix");
            }
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
    
    public void testRemovalOfPersonWithNoProdRels() {
        try {
            // just create a person, then remove that person
            int pk = facade.createPerson("RemovalMan");
            facade.removePerson(pk);
            PersonDTO person = facade.findPersonByPrimaryKey(pk);
            Assert.fail("Found person after person had been removed");
        }
        catch (FinderException fe) {
            return; // ok, not absolutely sure it's the right finder exception...
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RemoveException reme) {
            Assert.fail("Remove Exception " + reme.getMessage());
        }
    }
    
    public void testRemovalOfPersonWithProdRels() {
        try {
            int personPK = facade.createPerson("RemovalMan2");
            int bookPK = mediaItemManagementFacade.createMediaItem("Yet another book", "book", 2002);
            // attach an author prodRel to the book
            int bookAuthorRelPK = mediaItemManagementFacade.addMediaProductionRelationship(bookPK, "author");
            // attach the person to the prodRel
            mediaItemManagementFacade.addPersonToProductionRelationship(bookAuthorRelPK, personPK);
            // now, try and remove the person. This should fail with the current business logic
            facade.removePerson(personPK);
        }
        catch (RemoveException re) {
            if (re.getMessage().equals("person is still participating in production relationships and may not be removed")) {
                return;
            }
            else {
                Assert.fail("Remove Exception " + re.getMessage());
            }
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exception " + rnpe.getMessage());
        }
        catch (RemoteException reme) {
            Assert.fail("Remote Exception " + reme.getMessage());
        }
    }
    
    public void testEditPersonWithValidData() {
        try {
            int personPK = facade.createPerson("Editor", "EditFirstName");
            PersonDTO localDTO = new PersonDTO(new Integer(personPK));
            localDTO.setMainName("EditedSurname");
            localDTO.setGivenName("EditedFirstName");
            // make this person male
            localDTO.setGenderCode(1);
            facade.editPerson(localDTO);
            PersonDTO retrievedDTO = facade.findPersonByPrimaryKey(personPK);
            
            if (!retrievedDTO.getMainName().equals("EditedSurname")) {
                Assert.fail("Surname was not edited correctly");
            }
            if (!retrievedDTO.getGivenName().equals("EditedFirstName")) {
                Assert.fail("GivenName was not edited correctly");
            }
            if (retrievedDTO.getGenderCode() != 1) {
                Assert.fail("GenderCode was not correctly set to 1");
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
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Detail Exception " + ide.getMessage());
        }
    }
    
    public void testEditRemotePersonWithNullMainName() {
        try {
            int personPK = facade.createPerson("Editor", "EditFirstName");
            PersonDTO localDTO = new PersonDTO(new Integer(personPK));
            //localDTO.setMainName("EditedSurname"); - this will be NULL
            localDTO.setGivenName("EditedFirstName");
            // make this person male
            localDTO.setGenderCode(1);
            facade.editPerson(localDTO);
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
        catch (InsufficientDetailException ide) {
            if (ide.getMessage().equals("Person MainName must be defined")) {
                return;
            }
            else {
                Assert.fail("InsufficientDetailException " + ide.getMessage());
            }
        }
    }
    
    public void testEditPersonWithInvalidGenderCode() {
        try {
            int personPK = facade.createPerson("Editor", "EditFirstName");
            PersonDTO localDTO = new PersonDTO(new Integer(personPK));
            localDTO.setMainName("EditedSurname");
            localDTO.setGivenName("EditedFirstName");
            // set GenderCode to a negative number
            localDTO.setGenderCode(-1);
            facade.editPerson(localDTO);
            PersonDTO retrievedDTO = facade.findPersonByPrimaryKey(personPK);
            
            if (retrievedDTO.getGenderCode() != 0) {
                Assert.fail("GenderCode of -1 was not correctly set to 0");
            }
            // set GenderCode to 3
            localDTO.setGenderCode(3);
            facade.editPerson(localDTO);
            PersonDTO retrievedDTO2 = facade.findPersonByPrimaryKey(personPK);
            if (retrievedDTO2.getGenderCode() != 0) {
                Assert.fail("GenderCode of 3 was not correctly set to 0");
            }
            
            // set it to something above 9
            localDTO.setGenderCode(1003040);
            PersonDTO retrievedDTO3 = facade.findPersonByPrimaryKey(personPK);
            if (retrievedDTO3.getGenderCode() != 0) {
                Assert.fail("GenderCode of 1003040 was not correctly set to 0");
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
        catch (InsufficientDetailException ide) {
            Assert.fail("Insufficient Detail Exception " + ide.getMessage());
        }
    }
    
    public void testCreateOrganisationMethod1() {
        try {
            int pk = facade.createOrganisation("Remote Organisation Name");
            OrganisationDTO dto = facade.findOrganisationByPrimaryKey(pk);
            if (!dto.getName().equals("Remote Organisation Name")) {
                Assert.fail("Remote Organisation Name");
            }
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
    
    public void testCreateOrganisationMethod2() {
        try {
            int pk = facade.createOrganisation("Remote Organisation Name", "Remote City");
            OrganisationDTO dto = facade.findOrganisationByPrimaryKey(pk);
            if (!dto.getName().equals("Remote Organisation Name")) {
                Assert.fail("Name not Remote Organisation Name");
            }
            if (!dto.getCity().equals("Remote City")) {
                Assert.fail("City not \"Remote City\"");
            }
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
    
    public void testCreateOrganisationMethod3() {
        try {
            int pk = facade.createOrganisation("Remote Organisation Name", "Remote City", "Remote Country");
            OrganisationDTO dto = facade.findOrganisationByPrimaryKey(pk);
            if (!dto.getName().equals("Remote Organisation Name")) {
                Assert.fail("Name not Remote Organisation Name");
            }
            if (!dto.getCity().equals("Remote City")) {
                Assert.fail("City not \"Remote City\"");
            }
            if (!dto.getCountry().equals("Remote Country")) {
                Assert.fail("Country not \"Remote Country\"");
            }
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
    
    public void testRemovalOfOrganisationWithNoProdRels() {
        try {
            // just create a person, then remove that person
            int pk = facade.createOrganisation("Removal Company");
            facade.removeOrganisation(pk);
            OrganisationDTO org = facade.findOrganisationByPrimaryKey(pk);
            Assert.fail("Found person after person had been removed");
        }
        catch (FinderException fe) {
            return; // ok, not absolutely sure it's the right finder exception...
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (RemoteException re) {
            Assert.fail("Remote Exception " + re.getMessage());
        }
        catch (RemoveException reme) {
            Assert.fail("Remove Exception " + reme.getMessage());
        }
    }
    
    public void testRemovalOfOrganisationWithProdRels() {
        try {
            int orgPK = facade.createOrganisation("Removal Org 2");
            int bookPK = mediaItemManagementFacade.createMediaItem("Yet another book", "book", 2002);
            // attach an author prodRel to the book
            int bookAuthorRelPK = mediaItemManagementFacade.addMediaProductionRelationship(bookPK, "author");
            // attach the org to the prodRel
            mediaItemManagementFacade.addOrganisationToProductionRelationship(bookAuthorRelPK, orgPK);
            // now, try and remove the person. This should fail with the current business logic
            facade.removeOrganisation(orgPK);
        }
        catch (RemoveException re) {
            if (re.getMessage().equals("organisation is still participating in production relationships and may not be removed")) {
                return;
            }
            else {
                Assert.fail("Remove Exception " + re.getMessage());
            }
        }
        catch (CreateException ce) {
            Assert.fail("Create Exception " + ce.getMessage());
        }
        catch (FinderException fe) {
            Assert.fail("Finder Exception " + fe.getMessage());
        }
        catch (RelationshipNotPermittedException rnpe) {
            Assert.fail("Relationship Not Permitted Exception " + rnpe.getMessage());
        }
        catch (RemoteException reme) {
            Assert.fail("Remote Exception " + reme.getMessage());
        }
    }
    
    public void testEditOrganisationWithValidData() {
        try {
            int orgPK = facade.createOrganisation("An Organisation", "London");
            OrganisationDTO localOrgDTO = new OrganisationDTO(new Integer(orgPK));
            localOrgDTO.setName("An Edited Organisation");
            localOrgDTO.setCity("An Edited, Nicer Version of London");
            localOrgDTO.setCountry("England");
            facade.editOrganisation(localOrgDTO);
            OrganisationDTO retrievedDTO = facade.findOrganisationByPrimaryKey(orgPK);
            if (!retrievedDTO.getName().equals("An Edited Organisation")) {
                Assert.fail("Organisation Name was not correctly edited");
            }
            if (!retrievedDTO.getCity().equals("An Edited, Nicer Version of London")) {
                Assert.fail("Organisation city was not correctly edited");
            }
            if (!retrievedDTO.getCountry().equals("England")) {
                Assert.fail("Organisation country was not correctly edited");
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
        catch (InsufficientDetailException ide) {
            Assert.fail("InsufficientDetailException " + ide.getMessage());
        }
    }
    
    public void testEditOrganisationWithNullName() {
        try {
            int orgPK = facade.createOrganisation("An Organisation", "London");
            OrganisationDTO localOrgDTO = new OrganisationDTO(new Integer(orgPK));
            //localOrgDTO.setName("An Edited Organisation"); it will be NULL and should throw an exception
            localOrgDTO.setCity("An Edited, Nicer Version of London");
            localOrgDTO.setCountry("England");
            facade.editOrganisation(localOrgDTO);
            OrganisationDTO retrievedDTO = facade.findOrganisationByPrimaryKey(orgPK);
            Assert.fail("No InsufficientDetailException thrown when null name submitted");
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
        catch (InsufficientDetailException ide) {
            if (ide.getMessage().equals("Organisation name may not be null")) {
                return;
            }
            else {   
                Assert.fail("InsufficientDetailException " + ide.getMessage());
            }
        }
    }
}