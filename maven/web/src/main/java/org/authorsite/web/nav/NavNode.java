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
     * Assembles and returns the logical application path.
     * 
     * <p>This does notinclude the web application context root, but rather 
     * from the logical application root. Implementations
     * may <b>not</b> return <code>null</code> or the
     * empty string.</p>
     * 
     * <p>The path is constructed from path of the
     * parent node and seperated with a forward 
     * slash character ("/").</p>
     * 
     * @return path from root node.
     */
    public String getPath();
    
    /**
     * @return the parent note. In the case of the root
     * node this will be <code>null</code>.
     */
    public NavNode getParentNode();
    
    /**
     * @return list of children. This may be the 
     * empty list if the node is a leaf node - it may
     * not be <code>null</code>.. Children
     * must have a unique <code>name</code> so that
     * each node in the tree has a unique path.
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
     * @param locale 
     * @return the natural language name of the node in the 
     * specified language. If not found, attempt to return the
     * default natural language name (and if that fails, the 
     * technical name).
     */
    public String getLocalName(Locale locale);
    
    /**
     * @param name 
     * @return the named node. If not found, or node has no 
     * children, return <code>null</code>.
     */
    public NavNode getNamedChild(String name);
    
    /**
     * 
     * @return link to the corresponding icon. May be <code>null</code>
     * if none configured.
     */
    public String getIconUrl();

    /**
     * Registers a child node as belonging to this node.
     * 
     * <p>If an identically named child is already registered,
     * then this method has no effect.</p>
     * 
     * @param child
     */
    public void registerChild(NavNode child);

    /**
     * @return name of resource bundle to use
     */
    public String getResourceBundleName();
    
    /**
     * Determines whether this is an ancestor (recursive
     * parent relationship) of the <code>navNode</code>
     * parameter or not.
     * 
     * @param navNode
     * @return <code>true</code> if given this is an ancestor, else <code>false</code>
     */
    public boolean isAncestorOf(NavNode navNode);
    
    /**
     * @param path
     * @return corresponding node or <code>null</code>
     */
    public NavNode getDescendantByPath(String path);
    
    public String viewAsString();
    
}
