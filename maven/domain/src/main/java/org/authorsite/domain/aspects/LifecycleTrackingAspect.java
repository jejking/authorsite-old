package org.authorsite.domain.aspects;

import java.util.Date;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.authorsite.domain.AbstractEntry;

/**
 *
 * @author jejking
 */
@Aspect
public class LifecycleTrackingAspect {
    
    /** Creates a new instance of LifecycleTrackingAspect */
    public LifecycleTrackingAspect() {
    }
    

    /**
     * Injects creation and update metadata into Abstract Entity
     * objects before they are saved. 
     *
     * @param entry any abstract entity may not be <code>null</code>
     */
    @Before(value="execution (public * org.authorsite.dao..*Dao.save*(..)) && args(entry)", argNames="entry")
    public void addMetaDataOnSave(AbstractEntry entry) {
        if (entry == null) {
            throw new NullPointerException("entry parameter is null!");
        }
        Date now = new Date();
        entry.setCreatedAt(now);
        entry.setUpdatedAt(now);
        System.out.println("set created and updated dates to: " + now);
    }
    
    
    /**
     * 
     * @param entry any abstrat entry, may not be <code>null</code>
     */
    @Before(value="execution (public * org.authorsite.dao..*Dao.update*(..)) && args(entry)", argNames="entry")
    public void updateMetadataOnUpdate(AbstractEntry entry) {
        if (entry == null) {
            throw new NullPointerException("entry parameter is null");
        }
        Date now = new Date();
        entry.setUpdatedAt(now);
        System.out.println("updated date is: " + now);
    }
    
    /*
    @Before("execution (public * org.authorsite.dao..*Dao.save*(..))")
    public void printHello() {
        System.out.println("hello, I am the saving aspect...");
    }
     **/
    
    
}
