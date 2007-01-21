/*
 * TestSecuredClass.java
 *
 * Created on 21 January 2007, 19:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security;

import org.acegisecurity.annotation.Secured;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.userdetails.UserDetails;

/**
 *
 * @author jejking
 */
public class TestSecuredClass {
    
    
    /** Creates a new instance of TestSecuredClass */
    public TestSecuredClass() {
    }
    
    private String extractUsername() {
        AuthorsiteUserDetails userDetails = (AuthorsiteUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
    
    
    public void noAccessControl(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
    @Secured({"Editor"})
    public void editorOnly(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
    @Secured({"Administrator"})
    public void adminOnly(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
    @Secured({"Editor", "Administrator"})
    public void editorOrAdmin(UsernameCollector collector) {
        collector.setUsername(this.extractUsername());
    }
    
}
