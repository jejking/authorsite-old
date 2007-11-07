package org.authorsite.utils.bib.loader.ris;

import junit.framework.Test;
import junit.framework.TestSuite;

public class RISTestSuite {

        
    public static Test suite() {

        TestSuite suite = new TestSuite();
        suite.addTestSuite(ParserTest.class);
        suite.addTestSuite(RISEntryLineTest.class);
        suite.addTestSuite(RISEntryTest.class);
        suite.addTestSuite(HandlerHelperTest.class);
        suite.addTestSuite(ThesisHandlerTest.class);
        suite.addTestSuite(ArticleHandlerTest.class);
        suite.addTestSuite(BookHandlerTest.class);
        suite.addTestSuite(ChapterHandlerTest.class);
        return suite;
    }

    
}
