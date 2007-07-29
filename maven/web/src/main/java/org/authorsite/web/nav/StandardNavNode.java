package org.authorsite.web.nav;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author jejking
 *
 */
public class StandardNavNode extends AbstractNavNode {

    private final String resourceBundleName;
    private final String name;
    private final String iconUrl;
    private final NavNode parentNode;
    private final String path;
    
    /**
     * @param parentNode
     * @param name
     * @param resourceBundleName
     * @param iconUrl
     */
    public StandardNavNode(final NavNode parentNode, final String name, final String resourceBundleName, final String iconUrl) {
	super();
	if (parentNode != null) {
	    this.parentNode = parentNode;    
	}
	else {
	    this.parentNode = RootNavNode.getInstance();
	}
	
	if (name != null && name.length() > 0) {
	    this.name = name;    
	}
	else {
	    this.name = "unknown";
	}
	
	
	if (resourceBundleName != null && resourceBundleName.length() > 0) {
	    this.resourceBundleName = resourceBundleName;
	}
	else {
	    this.resourceBundleName = this.parentNode.getResourceBundleName();
	}
	    
	if (iconUrl != null && iconUrl.length() > 0) {
	    this.iconUrl = iconUrl;
	}
	else {
	    this.iconUrl = this.parentNode.getIconUrl();
	}
	
	if (this.parentNode == RootNavNode.getInstance()) {
	    this.path = this.parentNode.getPath() + this.name;
	}
	else {
	    this.path = this.parentNode.getPath() + "/" + this.name;    
	}
	
	this.parentNode.registerChild(this);
    }

    public String getIconUrl() {
	return this.iconUrl;
    }

    public String getDefaultLocalName() {
	return ResourceBundle.getBundle(resourceBundleName).getString(this.name);
    }

    public String getLocalName(Locale locale) {
	return ResourceBundle.getBundle(resourceBundleName, locale).getString(this.name);
    }

    public String getName() {
	return this.name;
    }

    public NavNode getParentNode() {
	return this.parentNode;
    }

    public String getPath() {
	return this.path;
    }

    public String getResourceBundleName() {
	return this.resourceBundleName;
    }

}
