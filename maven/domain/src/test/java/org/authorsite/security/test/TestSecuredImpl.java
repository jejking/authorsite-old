/*
 * TestSecuredImpl.java
 *
 * Created on 21 January 2007, 19:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security.test;

import org.acegisecurity.annotation.Secured;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;
import org.authorsite.security.AuthorsiteUserDetails;
import org.authorsite.security.test.UsernameCollector;

/**
 *
 * @author jejking
 */
public class TestSecuredImpl implements TestSecured {
    
    
    /**
     * Creates a new instance of TestSecuredImpl
     */
    public TestSecuredImpl() {
    }
    
    private String extractUsername() {
        AuthorsiteUserDetails userDetails = (AuthorsiteUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
    
    
    public void noAccessControl(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
    @Secured({"ROLE_EDITOR"})
    public void editorOnly(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void adminOnly(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
    @Secured({"ROLE_EDITOR", "ROLE_ADMINISTRATOR"})
    public void editorOrAdmin(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
}
