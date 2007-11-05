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

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.authorsite.domain.AbstractEntry;
import org.authorsite.security.AuthorsiteUserDetails;
import org.authorsite.security.SystemUser;

/**
 * @author jking
 *
 */
@Aspect
public class DomainServiceLoggingAspect {

    private static final Logger LOGGER = Logger.getLogger(DomainServiceLoggingAspect.class);
    
    /**
     * 
     */
    public DomainServiceLoggingAspect() {
        super();
    }
    
    /**
     * Logs that the entry has been saved and by whom.
     * 
     * @param entry
     */
    @After(value = "execution (public * org.authorsite.domain.service..*ServiceImpl.save*(..)) && args(entry)", argNames = "entry")
    public void logAfterSave(AbstractEntry entry) {
        LOGGER.info(this.getUserString() + " saved new entry " + entry);
    }
    
    /**
     * Logs that the entry has been updated and by whom.
     * 
     * @param entry
     */
    @After(value = "execution (public * org.authorsite.domain.service..*ServiceImpl.update*(..)) && args(entry)", argNames = "entry")
    public void logAfterUpdate(AbstractEntry entry) {
         LOGGER.info(this.getUserString() + " updated existing entry " + entry);
    }
    
    /**
     * Logs that the entry has been deleted and by whom.
     * 
     * @param entry
     */
    @After(value = "execution (public * org.authorsite.domain.service..*ServiceImpl.delete*(..)) && args(entry)", argNames = "entry")
    public void logAfterDelete(AbstractEntry entry) {
        LOGGER.info(this.getUserString() + " deleted entry " + entry);
    }

    /**
     * @param auth
     * @return string for user to use in log
     */
    private String getUserString() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            AuthorsiteUserDetails userDetails = (AuthorsiteUserDetails) auth.getPrincipal();
            SystemUser user = userDetails.getSystemUser();
            return user.getUserName(); 
        }
        else {
            return "Unknown user";
        }
    }
    
}
