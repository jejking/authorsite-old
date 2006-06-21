/*
 * RulesManagerTest.java
 *
 * Created on 11 November 2002, 18:02
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/services/rules/RulesManagerTest.java,v 1.2 2002/11/25 23:29:03 jejking Exp $
 */

package org.authorsite.bib.ejb.services.rules;
import junit.framework.*;
import java.util.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import org.authorsite.bib.ejb.test.services.rules.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class RulesManagerTest extends TestCase {
    
    private InitialContext initialContext;
    private RulesManagerTestFacade testFacade;
    
    /** Creates a new instance of RulesManagerTest */
    public RulesManagerTest(String name) {
        super(name);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        Object obj = initialContext.lookup("ejb/RulesManagerTestFacadeEJB");
        RulesManagerTestFacadeHome testFacadeHome = (RulesManagerTestFacadeHome) PortableRemoteObject.narrow(obj,  RulesManagerTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testGetFields() {
        try {
            String result = testFacade.testGetFields();
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
    
    public void testGetFieldsOfPriority() {
        try {
            String result = testFacade.testGetFieldsOfPriority();
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
    
    public void testGetProductionRelationships() {
        try {
            String result = testFacade.testGetProductionRelationships();
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
    
    public void testGetProductionRelationshipsOfPriority() {
        try {
            String result = testFacade.testGetProductionRelationshipsOfPriority();
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
    
    public void testGetFieldDescriptions() {
        try {
            String result = testFacade.testGetFieldDescriptions();
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
    
        public void testGetFieldDescriptionsOfPriority() {
        try {
            String result = testFacade.testGetFieldDescriptionsOfPriority();
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
        TestSuite suite = new TestSuite(RulesManagerTest.class);
        return suite;
    }
    
}
