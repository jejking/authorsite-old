/**
 * This file is part of the authorsite application.
 *
 * The authorsite application is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * The authorsite application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the authorsite application; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 */

package org.authorsite.domain.aspects;

import java.util.Date;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.authorsite.domain.AbstractEntry;
import org.authorsite.domain.Individual;
import org.authorsite.security.AuthorsiteUserDetails;
import org.authorsite.security.SystemUser;

/**
 * Aspect that helps manage the lifecycle metadata on 
 * {@link AbstractEntry} instances - ie. who created them
 * and when, and who updated them and when.
 * 
 * <p>This is a clear crosscutting concern as it applies
 * to all DAO objects managing {@link AbstractEntry} instances
 * of all types.</p>
 *
 * @author jejking
 */
@Aspect
public class LifecycleTrackingAspect {

    private static final Logger LOGGER = Logger.getLogger(LifecycleTrackingAspect.class);

    /** Creates a new instance of LifecycleTrackingAspect */
    public LifecycleTrackingAspect() {
	super();
    }

    /**
     * Injects creation and update metadata into Abstract Entity
     * objects before they are saved. 
     *
     * @param entry any abstract entity may not be <code>null</code>
     */
    @Before(value = "execution (public * org.authorsite.dao..*Dao.save*(..)) && args(entry)", argNames = "entry")
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
     * Injects update metadata into Abstract Entity when they
     * are updated in the database.
     * 
     * @param entry any abstract entry, may not be <code>null</code>
     */
    @Before(value = "execution (public * org.authorsite.dao..*Dao.update*(..)) && args(entry)", argNames = "entry")
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
