/*
 * MediaItemDTOAssemblerTest.java
 *
 * Created on 22 November 2002, 16:22
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/services/dto/MediaItemDTOAssemblerTest.java,v 1.2 2002/12/09 17:41:54 jejking Exp $
 */

package org.authorsite.bib.ejb.services.dto;
import junit.framework.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.*;
import javax.naming.*;
import javax.ejb.*;
import org.authorsite.bib.ejb.test.services.dto.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class MediaItemDTOAssemblerTest extends TestCase {
    
    private InitialContext initialContext;
    private MediaItemDTOAssemblerTestFacade testFacade;
    
    /** Creates a new instance of MediaItemDTOAssemblerTest */
    public MediaItemDTOAssemblerTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj = initialContext.lookup("ejb/MediaItemDTOAssemblerTestFacadeEJB");
        MediaItemDTOAssemblerTestFacadeHome testFacadeHome =
        (MediaItemDTOAssemblerTestFacadeHome) PortableRemoteObject.narrow(obj, MediaItemDTOAssemblerTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testSimpleMediaItemDTOAssembly() {
        try {
            String result = testFacade.testSimpleMediaItemDTOAssembly();
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
    
      
    public void testContainedMediaItemDTOAssembly() {
        try {
            if (testFacade == null) {
                Assert.fail("testFacade has somehow turned into null");
            }
            String result = testFacade.testContainedMediaItemDTOAssembly();
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
        catch (NullPointerException npe) {
            npe.printStackTrace();
            Assert.fail("caught a null pointer exception");
        }
    }
    
    public void testSimpleMediaItemDTOAssemblyWithDetails() {
        try {
            String result = testFacade.testSimpleMediaItemDTOAssemblyWithDetails();
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
        TestSuite suite = new TestSuite(MediaItemDTOAssemblerTest.class);
        return suite;
    }
}
