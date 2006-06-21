/*
 * OrganisationDTOAssemblerTest.java
 *
 * Created on 20 November 2002, 15:44
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/services/dto/OrganisationDTOAssemblerTest.java,v 1.1 2002/11/23 11:49:39 jejking Exp $
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
 * @version $Revision: 1.1 $
 */
public class OrganisationDTOAssemblerTest extends TestCase {
    
    private InitialContext initialContext;
    private OrganisationDTOAssemblerTestFacade testFacade;
    
    /** Creates a new instance of OrganisationDTOAssemblerTest */
    public OrganisationDTOAssemblerTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj = initialContext.lookup("ejb/OrganisationDTOAssemblerTestFacadeEJB");
        OrganisationDTOAssemblerTestFacadeHome testFacadeHome =
        (OrganisationDTOAssemblerTestFacadeHome) PortableRemoteObject.narrow(obj, OrganisationDTOAssemblerTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testOrganisationDTOAssembly() {
        try {
            String result = testFacade.testOrganisationDTOAssembly();
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
        TestSuite suite = new TestSuite(OrganisationDTOAssemblerTest.class);
        return suite;
    }
    
}
