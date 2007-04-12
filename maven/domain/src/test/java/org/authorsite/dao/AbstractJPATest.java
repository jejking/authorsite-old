package org.authorsite.dao;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 *
 * @author jejking
 */
public abstract class AbstractJPATest extends AbstractJpaTests {
    
   static {
        BasicConfigurator.configure();
        Logger.getRootLogger().setLevel(Level.WARN);
        Logger.getLogger("org.springframework").setLevel(Level.ERROR);
        Logger.getLogger("org.hibernate").setLevel(Level.DEBUG);
        Logger.getLogger("org.authorsite").setLevel(Level.DEBUG);
        Logger.getLogger("org.acegisecurity").setLevel(Level.INFO);
    }
    
    

    
}
