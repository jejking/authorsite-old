package org.authorsite.web.nav;

import java.util.Locale;
import java.util.MissingResourceException;
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
	try {
            return ResourceBundle.getBundle(resourceBundleName).getString(this.name);
        }
        catch (MissingResourceException mre) {
            return this.name;
        }
    }

    public String getLocalName(Locale locale) {
	try {
            return ResourceBundle.getBundle(resourceBundleName, locale).getString(this.name);
        }
        catch (MissingResourceException mre) {
            return this.name;
        }
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

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int PRIME = 31;
	int result = 1;
	result = PRIME * result + ((this.path == null) ? 0 : this.path.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final StandardNavNode other = (StandardNavNode) obj;
	if (this.path == null) {
	    if (other.path != null)
		return false;
	} else if (!this.path.equals(other.path))
	    return false;
	return true;
    }

    
    
}
