package org.authorsite.bib;

import junit.framework.Test;
import junit.framework.TestSuite;


public class BibTestSuite {

    public static Test suite() {

        TestSuite suite = new TestSuite();
        suite.addTestSuite( CollectiveTest.class);
        suite.addTestSuite( IndividualTest.class );
        return suite;
    }
    
}
