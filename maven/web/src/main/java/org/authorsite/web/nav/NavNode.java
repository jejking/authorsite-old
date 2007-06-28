/*
 * NavNode.java
 *
 * Created on 24 June 2007, 10:40
 *
 */

package org.authorsite.web.nav;

import java.util.List;
import java.util.Locale;

/**
 * Representation of a node in the navigation
 * hierarchy for use in displaying the navigation tree.
 *
 * <p>Different parts of the application, and their representation
 * at the web level, will implement this interface to provide a 
 * coherent view of the application and its content.</p>
 *
 * @author jejking
 */
public interface NavNode {
    
    /**
     * @return path from root node. This does not
     * include the web application context root.
     */
    public String getPath();
    
    /**
     * @return the parent note. In the case of the root
     * node this may be <code>null</code>
     */
    public NavNode getParentNode();
    
    /**
     * @return list of children. This may be the 
     * empty list if the node is a leaf node. Children
     * must have a unique <code>name</code>.
     */
    public List<NavNode> getChildren();
    
    /**
     * @return the name of the node. This refers specifically
     * to its name on the path and not necessarily to a natural
     * language name.
     */
    public String getName();
    
    /**
     * @return the default natural language name of the 
     * node. This should be in the language of the default 
     * Locale. If none is found, return the path name.
     */
    public String getDefaultLocalName();
    
    /**
     * @return the natural language name of the node in the 
     * specified language. If not found, attempt to return the
     * default natural language name (and if that fails, the 
     * technical name).
     */
    public String getLocalName(Locale locale);
    
    /**
     * @return the named node. If not found, or node has no 
     * children, return <code>null</code>.
     */
    public NavNode getNamedChild(String name);
    
    /**
     * @returns link to the corresponding icon. May be <code>null</code>
     * if none configured.
     */
    public String getIconUrl();

    
    
}
