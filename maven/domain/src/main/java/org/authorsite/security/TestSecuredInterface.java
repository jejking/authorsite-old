/*
 * TestSecuredInterface.java
 *
 * Created on 23. Januar 2007, 19:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.security;

import org.acegisecurity.annotation.Secured;
import org.authorsite.security.*;

/**
 *
 * @author jking
 */
public interface TestSecuredInterface {
    
    public void noAccessControl(UsernameCollector collector);
    
    @Secured({"Editor"})
    public void editorOnly(UsernameCollector collector);
    
    @Secured({"Administrator"})
    public void adminOnly(UsernameCollector collector);
    
    @Secured({"Editor", "Administrator"})
    public void editorOrAdmin(UsernameCollector collector);
    
}
