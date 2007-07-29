/*
 * RootNavNode.java
 *
 * Created on 24 June 2007, 11:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.nav;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 *
 * @author jejking
 */
public final class RootNavNode extends AbstractNavNode {
    
    private static final AbstractNavNode INSTANCE = new RootNavNode();
    
    /** Creates a new instance of RootNavNode */
    private RootNavNode() {
        super();
    }
    
    public static NavNode getInstance() {
        return RootNavNode.INSTANCE;
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

    public String getDefaultLocalName() {
	return this.getName();
    }

    public String getLocalName(Locale locale) {
	return this.getName();
    }

    public String getResourceBundleName() {
	return "org.authorsite.web.resources.nav";
    }
    
    
    
}
