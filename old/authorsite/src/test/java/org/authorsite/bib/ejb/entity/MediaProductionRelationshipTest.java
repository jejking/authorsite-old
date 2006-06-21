/*
 * MediaProductionRelationshipTest.java
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/entity/MediaProductionRelationshipTest.java,v 1.2 2002/11/23 11:52:00 jejking Exp $
 *
 * Created on 02 October 2002, 13:27
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
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaProductionRelationshipTest extends TestCase {
    
    /** Creates a new instance of MediaProductionRelationshipTest */
    public MediaProductionRelationshipTest(String testName) {
        super(testName);
    }
    
    private InitialContext initialContext;
    private MediaProductionRelationshipTestFacade testFacade;
    
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj2 = initialContext.lookup("ejb/MediaProductionRelationshipTestFacadeEJB");
        MediaProductionRelationshipTestFacadeHome testFacadeHome =
        (MediaProductionRelationshipTestFacadeHome) PortableRemoteObject.narrow(obj2,  MediaProductionRelationshipTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite(MediaProductionRelationshipTest.class);
        return suite;
    }
    
    public void testCreateAndFind() {
        try {
            String result = testFacade.testCreateAndFind();
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
    
    public void testAddProdRelToMediaItem() {
        try {
            String result = testFacade.testAddProdRelToMediaItem();
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
    
    public void testAddMediaItemToRel() {
        try {
            String result = testFacade.testAddMediaItemToRel();
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
    
    public void testAddPeopleToRelationship() {
        try {
            String result = testFacade.testAddPeopleToRelationship();
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
    
    public void testAddProductionRelationshipsToPeople() {
        try {
            String result = testFacade.testAddProductionRelationshipsToPeople();
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
    
    public void testAddOrganisationsToRelationship() {
        try {
            String result = testFacade.testAddOrganisationsToRelationship();
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
    
    public void testAddProductionRelationshipsToOrganisation() {
        try {
            String result = testFacade.testAddProductionRelationshipsToOrganisation();
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
