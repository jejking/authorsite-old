/*
 * OrganisationTest.java
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/entity/OrganisationTest.java,v 1.2 2002/10/09 12:03:08 jejking Exp $
 *
 * Created on 02 October 2002, 21:48
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
 * Test driver for OrganisationTestFacadeBean
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class OrganisationTest extends TestCase {
    
    private InitialContext initialContext;
    private OrganisationTestFacade testFacade;
    
    /** Creates a new instance of OrganisationTest */
    public OrganisationTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj2 = initialContext.lookup("ejb/OrganisationTestFacadeEJB");
        OrganisationTestFacadeHome testFacadeHome = (OrganisationTestFacadeHome) PortableRemoteObject.narrow(obj2,  OrganisationTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testCreateMethods() {
        try {
            String result = testFacade.testCreateMethods();
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
    
    public void testFindByName() {
        try {
            String result = testFacade.testFindByName();
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
    
    public void testFindByCity() {
        try {
            String result = testFacade.testFindByCity();
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
    
    public void testFindByCountry() {
        try {
            String result = testFacade.testFindByCountry();
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
    
    public void testFindWithoutProductionRels() {
        try {
            String result = testFacade.testFindWithoutProductionRels();
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
    
    public void testFindWithUnlinkedProductionRels() {
        try {
            String result = testFacade.testFindWithUnlinkedProductionRels();
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
