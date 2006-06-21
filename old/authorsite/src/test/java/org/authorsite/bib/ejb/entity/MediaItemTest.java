/*
 * MediaItemTest.java
 * JUnit based test
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/entity/MediaItemTest.java,v 1.10 2002/12/04 00:23:57 jejking Exp $
 *
 * Created on 16 September 2002, 22:20
 */

package org.authorsite.bib.ejb.entity;

import junit.framework.*;
import java.util.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;

import org.authorsite.bib.ejb.test.entity.*;

/**
 * Test driver for MediaItemTestFacade.
 *
 * @author jejking
 * @version $Revision: 1.10 $
 */
public class MediaItemTest extends TestCase {
    
    private InitialContext initialContext;
    private MediaItemTestFacade testFacade;
    
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj2 = initialContext.lookup("ejb/MediaItemTestFacadeEJB");
        MediaItemTestFacadeHome testFacadeHome = (MediaItemTestFacadeHome) PortableRemoteObject.narrow(obj2,  MediaItemTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public MediaItemTest(java.lang.String testName) {
        super(testName);
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MediaItemTest.class);
        return suite;
    }
    
    /*
     * tests creation of valid media item EJB
     */
    public void testValidCreate() {
        try {
            String result = testFacade.testCreateMediaItemWithValidParams();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred");
        }
    }
    
    /*
     * tests that the other setters are working ok
     */
    public void testSetters() {
        try {
            String result = testFacade.testOtherSettersWithValidParams();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail(re.getMessage());
        }
    }
    
    public void testAddLanguage() {
        try {
            String testResult = testFacade.testAddALanguage();
            Assert.assertEquals("we should get \"passed\"", "passed", testResult);
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred" + re.getMessage());
        }
    }
    
    public void testCreateIntraMediaRelationships() {
        try {
            String result = testFacade.testAddAnIntraMediaRelationship();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
            
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testFindAllUnpublished() {
        try {
            String result = testFacade.testFindAllUnpublished();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testFindPublishedByLanguage() {
        try {
            String result = testFacade.testFindPublishedByLanguage();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail(re.getMessage());
        }
    }
    
    public void testFindPublishedByTypeAndLanguage() {
        try {
            String result = testFacade.testFindPublishedByTypeAndLanguage();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail(re.getMessage());
        }
    }
    
    /**
     * tests findByYearOfCreation
     */
    public void testFindPublishedByYearOfCreation() {
        try {
            String result = testFacade.testFindPublishedByYearOfCreation();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
        
    public void testFindPublishedCreatedBefore() {
        try {
            String result = testFacade.testFindPublishedCreatedBefore();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail(re.getMessage());
        }
    }
    
    public void testFindPublishedCreatedBetween() {
        try {
            String result = testFacade.testFindPublishedCreatedBetween();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetPublishedChildren() {
        try {
            String result = testFacade.testGetPublishedChildren();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetUnpublishedChildren() {
        try {
            String result = testFacade.testGetUnpublishedChildren();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetPublishedChildrenOfType() {
        try {
            String result = testFacade.testGetPublishedChildrenOfType();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetUnpublishedChildrenOfType() {
        try {
            String result = testFacade.testGetUnpublishedChildrenOfType();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetPublishedQualifiedChildrenOfType() {
        try {
            String result = testFacade.testGetPublishedQualifiedChildrenOfType();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetUnpublishedQualifiedChildrenOfType() {
        try {
            String result = testFacade.testGetUnpublishedQualifiedChildrenOfType();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetUnpublishedParents() {
        try {
            String result = testFacade.testGetUnpublishedParents();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetPublishedParents() {
        try {
            String result = testFacade.testGetPublishedParents();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    public void testGetUnpublishedParentsOfType() {
        try {
            String result = testFacade.testGetUnpublishedParentsOfType();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
    
    public void testGetPublishedParentsOfType() {
        try {
            String result = testFacade.testGetPublishedParentsOfType();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
    
   public void testGetContainer() {
        try {
            String result = testFacade.testGetContainer();
            if (result.equals("passed")) {
                return;
            }
            else {
                Assert.fail(result);
            }
        }
        catch (RemoteException re) {
            Assert.fail("remote exception occurred\n" + re.getMessage());
        }
    }
}