package org.authorsite.domain.bib;

import junit.framework.Test;
import junit.framework.TestSuite;


public class BibTestSuite {

    public static Test suite() {

        TestSuite suite = new TestSuite();
        suite.addTestSuite( CollectiveTest.class);
        suite.addTestSuite( IndividualTest.class );
        suite.addTestSuite( JournalTest.class);
        suite.addTestSuite( ArticleTest.class );
        suite.addTestSuite( BookTest.class );
        suite.addTestSuite( ChapterTest.class );
        suite.addTestSuite( ThesisTest.class );
        return suite;
    }
    
}
