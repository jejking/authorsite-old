/*
 * MediaItemOrganisationFindersTest.java
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/entity/MediaItemOrganisationFindersTest.java,v 1.2 2002/12/04 00:23:57 jejking Exp $
 *
 * Created on 09 October 2002, 10:25
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
 * Test driver for tests on organisation related finder methods.
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemOrganisationFindersTest extends TestCase {
    
       /** Creates a new instance of MediaItemOrganisationFindersTest */
    public MediaItemOrganisationFindersTest(String testName) {
        super(testName);
    }
    
    private InitialContext initialContext;
    private MediaItemOrganisationFindersTestFacade testFacade;
    
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj2 = initialContext.lookup("ejb/MediaItemOrganisationFindersTestFacadeEJB");
        MediaItemOrganisationFindersTestFacadeHome testFacadeHome = (MediaItemOrganisationFindersTestFacadeHome) PortableRemoteObject.narrow(obj2,  MediaItemOrganisationFindersTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MediaItemOrganisationFindersTest.class);
        return suite;
    }
    
     public void testFindPublishedByOrganisation() {
        try {
            String result = testFacade.testFindPublishedByOrganisation();
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
    
    public void testFindPublishedByOrganisationByLanguage() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByLanguage();
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
    
    public void testFindPublishedByOrganisationByType() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByType();
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
    
    public void testFindPublishedByOrganisationByLanguageByType() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByLanguageByType();
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
    
    public void testFindPublishedByOrganisationByRel() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByRel();
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
    
    public void testFindPublishedByOrganisationByRelByLanguage() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByRelByLanguage();
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
    
    public void testFindPublishedByOrganisationByRelByType() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByRelByType();
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
    
    public void testFindPublishedByOrganisationByRelByLanguageByType() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByRelByLanguageByType();
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
    
    public void testFindPublishedByOrganisationBetweenYears() {
        try {
            String result = testFacade.testFindPublishedByOrganisationBetweenYears();
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
    
    public void testFindPublishedByOrganisationBetweenYearsByLanguage() {
        try {
            String result = testFacade.testFindPublishedByOrganisationBetweenYearsByLanguage();
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
    
    public void testFindPublishedByOrganisationBetweenYearsByType() {
        try {
            String result = testFacade.testFindPublishedByOrganisationBetweenYearsByType();
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
    
    public void testFindPublishedByOrganisationBetweenYearsByLanguageByType() {
        try {
            String result = testFacade.testFindPublishedByOrganisationBetweenYearsByLanguageByType();
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
    
    public void testFindPublishedByOrganisationByRelBetweenYears() {
        try {
            String result = testFacade.testFindPublishedByOrganisationByRelBetweenYears();
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
    
    public void testFindPublishedByOrganisationByRelBetweenYearsByLanguage() {
       try {
            String result = testFacade.testFindPublishedByOrganisationByRelBetweenYearsByLanguage();
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
    
    public void testFindPublishedByOrganisationByRelBetweenYearsByType() {
      try {
            String result = testFacade.testFindPublishedByOrganisationByRelBetweenYearsByType();
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
    
    public void testFindPublishedByOrganisationByRelBetweenYearsByLanguageByType() {
      try {
            String result = testFacade.testFindPublishedByOrganisationByRelBetweenYearsByLanguageByType();
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
    
}
