package org.authorsite;

import java.io.IOException;
import java.util.List;

import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.utils.bib.loader.ris.FileParserDriver;
import org.authorsite.utils.bib.loader.ris.RISException;

/**
 * 
 * @author jejking
 */
public class App 
{
    public static void main( String[] args ) throws IOException, RISException
    {
        FileParserDriver fileParserDriver = new FileParserDriver();
        List<AbstractWork> workList = fileParserDriver.parseFile(args[0]);
//        for (AbstractWork work : workList) {
//            System.out.println(work);
//        }
        
        /*
         * TestAuthenticationMechanism authenticationMechanism = new TestAuthenticationMechanism();
	   authenticationMechanism.logUserIn(args[1], args[2]);
         */
    }
}
