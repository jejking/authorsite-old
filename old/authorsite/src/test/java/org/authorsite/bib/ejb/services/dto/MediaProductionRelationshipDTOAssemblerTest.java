/*
 * MediaProductionRelationshipDTOAssemblerTest.java
 *
 * Created on 20 November 2002, 17:28
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/services/dto/MediaProductionRelationshipDTOAssemblerTest.java,v 1.1 2002/11/23 11:49:39 jejking Exp $
 */

package org.authorsite.bib.ejb.services.dto;
import junit.framework.*;
import java.util.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import org.authorsite.bib.ejb.test.services.dto.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class MediaProductionRelationshipDTOAssemblerTest extends TestCase {
    
    private InitialContext initialContext;
    private MediaProductionRelationshipDTOAssemblerTestFacade testFacade;
    
    /** Creates a new instance of MediaProductionRelationshipDTOAssemblerTest */
    public MediaProductionRelationshipDTOAssemblerTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj = initialContext.lookup("ejb/MediaProductionRelationshipDTOAssemblerTestFacadeEJB");
        MediaProductionRelationshipDTOAssemblerTestFacadeHome testFacadeHome =
        (MediaProductionRelationshipDTOAssemblerTestFacadeHome) PortableRemoteObject.narrow(obj, MediaProductionRelationshipDTOAssemblerTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testMediaProductionRelationshipDTOAssembly() {
        try {
            String result = testFacade.testMediaProductionRelationshipDTOAssembly();
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
        TestSuite suite = new TestSuite(MediaProductionRelationshipDTOAssemblerTest.class);
        return suite;
    }
    
}
