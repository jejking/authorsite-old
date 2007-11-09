package org.authorsite.utils.bib.loader.ris;

import java.io.IOException;
import java.util.List;

import org.authorsite.domain.bib.AbstractWork;
import org.authorsite.security.AuthenticationMechanismHelper;
import org.authorsite.utils.DomainLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author jejking
 */
public class RISLoader 
{
    private DomainLoader domainLoader;

    public void setDomainLoader(DomainLoader domainLoader) {
        this.domainLoader = domainLoader;
    }
    
    public void load(String fileName) throws IOException, RISException {
        FileParserDriver fileParserDriver = new FileParserDriver();
        List<AbstractWork> workList = fileParserDriver.parseFile(fileName);
        domainLoader.loadList(workList);
    }
    
    public static void main( String[] args ) throws IOException, RISException
    {
        // load up spring application context
        ApplicationContext appContext = new ClassPathXmlApplicationContext("secured-appcontext.xml");
        AuthenticationMechanismHelper authenticationMechanism = new AuthenticationMechanismHelper();
	authenticationMechanism.logUserIn(args[1], args[2]);
    
        RISLoader loader = (RISLoader) appContext.getBean("risLoader");
        loader.load(args[0]);
        
        System.exit(0);
    }
}
