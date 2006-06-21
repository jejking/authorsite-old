/*
 * PersonTester.java
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/entity/PersonTest.java,v 1.2 2002/10/09 13:47:34 jejking Exp $
 *
 * Created on 30 September 2002, 16:20
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
 * Test driver for PersonTestFacade.
 * 
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PersonTest extends TestCase {
    
    private InitialContext initialContext;
    private PersonTestFacade testFacade;
    
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj2 = initialContext.lookup("ejb/PersonTestFacadeEJB");
        PersonTestFacadeHome testFacadeHome = (PersonTestFacadeHome) PortableRemoteObject.narrow(obj2,  PersonTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public PersonTest(java.lang.String testName) {
        super(testName);
    }
    
    public void testCreateAndFindPerson() {
        try {
            String result = testFacade.testCreateAndFindPerson();
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
    
    public void testFindByMainName() {
        try {
            String result = testFacade.testFindByMainName();
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
    
    public void testFindByMainNameAndGivenName() {
        try {
            String result = testFacade.testFindByMainNameAndGivenName();
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
        
    
    
    public static Test suite() {
        TestSuite suite = new TestSuite(PersonTest.class);
        return suite;
    }
}
