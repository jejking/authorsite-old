package org.authorsite.web.nav;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class AbstractNavNode implements NavNode {

    private final List<NavNode> navNodeList = new LinkedList<NavNode>();
    private final Map<String, NavNode> navNodeNameMap = new HashMap<String, NavNode>();

    public List<NavNode> getChildren() {
        return Collections.unmodifiableList(this.navNodeList);
    }

    public NavNode getNamedChild(String name) {
        return this.navNodeNameMap.get(name);
    }

    public void registerChild(NavNode child) {
        if (child == null ) {
            return;
        }
        boolean alreadyKnown = this.navNodeNameMap.containsKey(child.getName());
        if (!alreadyKnown) {
            this.navNodeNameMap.put(child.getName(), child);
            this.navNodeList.add(child);
        }
    }

    public boolean isAncestorOf(NavNode navNode) {
        if (navNode == null) {
            return false;
        }
            
        if (this.getNamedChild(navNode.getName()) != null) {
            return true;
        }
        else {
            return this.isAncestorOf(navNode.getParentNode());
        }
    }
    
    public NavNode getDescendantByPath(String path) {
	// assume we have string like /path/to/child
	
	if (path == null || path.length() == 0 || !(path.startsWith("/"))) {
	    return null;
	}
	
		
	// we want "path" (from char 1. ie / to next slash
	int targetEndOfString = path.indexOf("/", 1);
	if ( targetEndOfString == - 1) {
	    targetEndOfString = path.length();
	}
	
	String firstPartOfPath = path.substring(1, targetEndOfString);
	
	if (this.navNodeNameMap.get(firstPartOfPath) != null) {
	    
	    NavNode nextNode = this.navNodeNameMap.get(firstPartOfPath);
	    
	    String remainingPartOfPath = path.substring(targetEndOfString);
	    int nextTargetEndOfString = remainingPartOfPath.indexOf("/", 1);
	    if (nextTargetEndOfString == -1) {
		nextTargetEndOfString = remainingPartOfPath.length();
	    }
	    String nameOfNextChild = null;
	    
	    if ( !remainingPartOfPath.isEmpty()) {
		nameOfNextChild = remainingPartOfPath.substring(1, nextTargetEndOfString);
	    }
	    
	    if (nextNode.getNamedChild(nameOfNextChild) != null) {
		return nextNode.getDescendantByPath(remainingPartOfPath);
	    }
	    else {
		return nextNode;
	    }
	}
	else {
	    return null;    
	}
	
    }
    
    @Override
    public String toString() {
        return ("NavNode: " + this.getPath());
    }
}
