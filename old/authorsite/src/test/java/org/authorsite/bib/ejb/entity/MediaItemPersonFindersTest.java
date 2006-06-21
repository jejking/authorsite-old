/*
 * MediaItemPersonFindersTest.java
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/entity/MediaItemPersonFindersTest.java,v 1.2 2002/12/04 00:23:57 jejking Exp $
 *
 * Created on 09 October 2002, 09:42
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
 * Test driver for MediaItemPersonFindersTestFacadeEJB.
 * 
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemPersonFindersTest extends TestCase {
    
    /** Creates a new instance of MediaItemPersonFindersTest */
    public MediaItemPersonFindersTest(String testName) {
        super(testName);
    }
    
    private InitialContext initialContext;
    private MediaItemPersonFindersTestFacade testFacade;
    
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj2 = initialContext.lookup("ejb/MediaItemPersonFindersTestFacadeEJB");
        MediaItemPersonFindersTestFacadeHome testFacadeHome = (MediaItemPersonFindersTestFacadeHome) PortableRemoteObject.narrow(obj2,  MediaItemPersonFindersTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MediaItemPersonFindersTest.class);
        return suite;
    }
    
     public void testFindPublishedByPerson() {
        try {
            String result = testFacade.testFindPublishedByPerson();
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
    
    public void testFindPublishedByPersonByLanguage() {
        try {
            String result = testFacade.testFindPublishedByPersonByLanguage();
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
    
    public void testFindPublishedByPersonByType() {
        try {
            String result = testFacade.testFindPublishedByPersonByType();
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
    
    public void testFindPublishedByPersonByLanguageByType() {
        try {
            String result = testFacade.testFindPublishedByPersonByLanguageByType();
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
    
    public void testFindPublishedByPersonByRel() {
        try {
            String result = testFacade.testFindPublishedByPersonByRel();
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
    
    public void testFindPublishedByPersonByRelByLanguage() {
        try {
            String result = testFacade.testFindPublishedByPersonByRelByLanguage();
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
    
    public void testFindPublishedByPersonByRelByType() {
        try {
            String result = testFacade.testFindPublishedByPersonByRelByType();
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
    
    public void testFindPublishedByPersonByRelByLanguageByType() {
        try {
            String result = testFacade.testFindPublishedByPersonByRelByLanguageByType();
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
    
    public void testFindPublishedByPersonBetweenYears() {
        try {
            String result = testFacade.testFindPublishedByPersonBetweenYears();
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
    
    public void testFindPublishedByPersonBetweenYearsByLanguage() {
        try {
            String result = testFacade.testFindPublishedByPersonBetweenYearsByLanguage();
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
    
    public void testFindPublishedByPersonBetweenYearsByType() {
        try {
            String result = testFacade.testFindPublishedByPersonBetweenYearsByType();
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
    
    public void testFindPublishedByPersonBetweenYearsByLanguageByType() {
        try {
            String result = testFacade.testFindPublishedByPersonBetweenYearsByLanguageByType();
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
    
    public void testFindPublishedByPersonByRelBetweenYears() {
        try {
            String result = testFacade.testFindPublishedByPersonByRelBetweenYears();
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
    
    public void testFindPublishedByPersonByRelBetweenYearsByLanguage() {
       try {
            String result = testFacade.testFindPublishedByPersonByRelBetweenYearsByLanguage();
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
    
    public void testFindPublishedByPersonByRelBetweenYearsByType() {
      try {
            String result = testFacade.testFindPublishedByPersonByRelBetweenYearsByType();
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
    
    public void testFindPublishedByPersonByRelBetweenYearsByLanguageByType() {
      try {
            String result = testFacade.testFindPublishedByPersonByRelBetweenYearsByLanguageByType();
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
