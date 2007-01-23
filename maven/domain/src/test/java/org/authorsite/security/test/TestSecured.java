/*
 * TestSecured.java
 *
 * Created on 23. Januar 2007, 19:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security.test;

import org.acegisecurity.annotation.Secured;
import org.authorsite.security.test.UsernameCollector;

/**
 *
 * @author jking
 */
public interface TestSecured {
    
    public void noAccessControl(UsernameCollector collector);
    
    @Secured({"ROLE_EDITOR"})
    public void editorOnly(UsernameCollector collector);
    
    @Secured({"ROLE_ADMINISTRATOR"})
    public void adminOnly(UsernameCollector collector);
    
    @Secured({"ROLE_EDITOR", "ROLE_ADMINISTRATOR"})
    public void editorOrAdmin(UsernameCollector collector);
    
}
