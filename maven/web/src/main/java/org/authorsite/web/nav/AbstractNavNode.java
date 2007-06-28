/*
 * AbstractNavNode.java
 *
 * Created on 24 June 2007, 14:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.nav;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author jejking
 */
public abstract class AbstractNavNode implements NavNode {
    
    protected String name;
    private String cachedPath;
    protected NavNode parent;
    protected String iconUrl;
    
    
    protected List<NavNode> children;
    protected Map<String, NavNode> childrenMap;
    protected String resourceBundleName = "org.authorsite.web.resources.nav"; // default resource bundle
    
    
    /** Creates a new instance of AbstractNavNode */
    public AbstractNavNode() {
    }

    public NavNode getParentNode() {
        return parent;
    }

    public String getPath() {
        if (this.cachedPath == null) {
            if (this.parent != null) {
                this.cachedPath = parent.getPath()  + "/" + this.name;
            }
            else {
                this.cachedPath = this.name;
            }
        }
        return this.cachedPath;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }

    
    
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    
    public String getIconUrl() {
        return this.iconUrl;
    }


    public List<NavNode> getChildren() {
        if (this.children == null) {
            return Collections.emptyList();
        }
        else {
            return this.children;    
        }
    }


    public String getDefaultLocalName() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle(this.resourceBundleName);
            return rb.getString(this.name);
        } catch (MissingResourceException mre) {
            return this.name;
        }
    }


    public String getLocalName(Locale locale) {
        try {
            ResourceBundle rb = ResourceBundle.getBundle(this.resourceBundleName, locale);
            return rb.getString(this.name);
        } catch (MissingResourceException mre) {
            return this.name;
        }
    }


    public NavNode getNamedChild(String name) {
        if (this.childrenMap == null ) {
            return null;
        }
        else { 
            return this.childrenMap.get(name);
        }
    }


    
    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }


    
}
