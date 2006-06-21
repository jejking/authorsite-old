/*
 * PersonDTOAssemblerTest.java
 *
 * Created on 20 November 2002, 15:09
 * $Header: /cvsroot/authorsite/authorsite/src/test/java/org/authorsite/bib/ejb/services/dto/PersonDTOAssemblerTest.java,v 1.1 2002/11/23 11:49:39 jejking Exp $
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
public class PersonDTOAssemblerTest extends TestCase {
    
    private InitialContext initialContext;
    private PersonDTOAssemblerTestFacade testFacade;
    
    /** Creates a new instance of PersonDTOAssemblerTest */
    public PersonDTOAssemblerTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        initialContext = new InitialContext();
        
        Object obj = initialContext.lookup("ejb/PersonDTOAssemblerTestFacadeEJB");
        PersonDTOAssemblerTestFacadeHome testFacadeHome =
        (PersonDTOAssemblerTestFacadeHome) PortableRemoteObject.narrow(obj, PersonDTOAssemblerTestFacadeHome.class);
        testFacade = testFacadeHome.create();
    }
    
    public void testPersonDTOAssembly() {
        try {
            String result = testFacade.testPersonDTOAssembly();
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
        TestSuite suite = new TestSuite(PersonDTOAssemblerTest.class);
        return suite;
    }
    
}
