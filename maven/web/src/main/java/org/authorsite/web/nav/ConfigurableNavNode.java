/*
 * ConfigurableNavNode.java
 *
 * Created on 24 June 2007, 11:09
 *
 */

package org.authorsite.web.nav;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jejking
 */
public class ConfigurableNavNode extends AbstractNavNode {
    
    
    /** Creates a new instance of ConfigurableNavNode */
    public ConfigurableNavNode() {
    }
    
    public ConfigurableNavNode(NavNode parent) {
        this.setParent(parent);
    }
    
    public ConfigurableNavNode(String name) {
        this.setName(name);
    }
    
    public ConfigurableNavNode(String name, NavNode parent) {
        this.setName(name);
        this.setParent(parent);
    }

    
    public void setChildren(List<NavNode> children) {
        this.children = new LinkedList<NavNode>();
        this.childrenMap = new HashMap<String, NavNode>();
        
        for (NavNode navNode : children) {
            String nodeName = navNode.getName();
            if ( this.childrenMap.containsKey(nodeName)) {
                continue; // i.e. ignore duplicates
            }
            else {
                if (navNode instanceof ConfigurableNavNode) {
                    ((ConfigurableNavNode)navNode).setParent(this);
                }
                this.children.add(navNode);
                this.childrenMap.put(nodeName, navNode);
            }
        }
    }

    public void setParent(NavNode parent) {
        this.parent = parent;
    }

    
    
}
