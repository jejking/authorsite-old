/*
 * SequenceBlockPrimaryKeyGeneratorTest.java
 *
 * Created on 04 November 2002, 16:29
 */

package org.authorsite.bib.ejb.services.database;

import junit.framework.*;
import java.util.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import org.authorsite.bib.ejb.test.services.database.*;

/**
 * Test driver for SequenceBlockPrimaryKeyGeneratorTestFacade;
 * @author  jejking
 */
public class SequenceBlockPrimaryKeyGeneratorTest extends TestCase {
    
    private InitialContext initialContext;
    private SequenceBlockPrimaryKeyGeneratorTestFacade testFacade;
    
    /** Creates a new instance of SequenceBlockPrimaryKeyGeneratorTest */
    public SequenceBlockPrimaryKeyGeneratorTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj = initialContext.lookup("ejb/SequenceBlockPrimaryKeyGeneratorTestFacadeEJB");
        SequenceBlockPrimaryKeyGeneratorTestFacadeHome testFacadeHome =
        (SequenceBlockPrimaryKeyGeneratorTestFacadeHome) PortableRemoteObject.narrow(obj,  SequenceBlockPrimaryKeyGeneratorTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testSequenceGeneration() {
        try {
            String result = testFacade.testSequenceGeneration();
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
        TestSuite suite = new TestSuite(SequenceBlockPrimaryKeyGeneratorTest.class);
        return suite;
    }
}
