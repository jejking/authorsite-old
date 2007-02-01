package org.authorsite.domain.aspects;

import java.util.Date;
import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.authorsite.domain.AbstractEntry;
import org.authorsite.domain.Individual;
import org.authorsite.security.AuthorsiteUserDetails;
import org.authorsite.security.SystemUser;

/**
 *
 * @author jejking
 */
@Aspect
public class LifecycleTrackingAspect {
    
    private static final Logger LOGGER = Logger.getLogger(LifecycleTrackingAspect.class);
    
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
        LOGGER.debug("set created and updated dates to: " + now);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
            AuthorsiteUserDetails userDetails = (AuthorsiteUserDetails) auth.getPrincipal();
            SystemUser user = userDetails.getSystemUser();
            Individual individual = user.getIndividual();
            entry.setCreatedBy(individual);
            entry.setUpdatedBy(individual);
            LOGGER.debug("Set created and updated by to: " + individual);
        }
        
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
        LOGGER.debug("updated date is: " + now);
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            AuthorsiteUserDetails userDetails = (AuthorsiteUserDetails) auth.getPrincipal();
            SystemUser user = userDetails.getSystemUser();
            Individual individual = user.getIndividual();
            entry.setUpdatedBy(individual);
            LOGGER.debug("Set updated by to: " + individual);
        }
    }
    
}
