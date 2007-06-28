/*
 * RootNavNode.java
 *
 * Created on 24 June 2007, 11:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.nav;

import java.util.List;
import java.util.Locale;

/**
 *
 * @author jejking
 */
public class RootNavNode extends ConfigurableNavNode {
    
    /** Creates a new instance of RootNavNode */
    public RootNavNode() {
    }

    public String getPath() {
        return "/";
    }

    public NavNode getParentNode() {
        return null;
    }

    public String getName() {
        return "/";
    }
    
    public String getIconUrl() {
        return null;
    }
    
}
