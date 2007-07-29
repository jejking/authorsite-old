package org.authorsite.web.nav;

import java.util.Locale;

import junit.framework.TestCase;

public class StandardNavNodeTest extends TestCase {

    public void testConstructorFull() {
	StandardNavNode parent = new StandardNavNode( RootNavNode.getInstance(), "parent", "org.test.resource.parent", "/icons/parent.png" );
	StandardNavNode child = new StandardNavNode(parent, "child", "org.test.resource.child", "/icons/child.png");
	
	assertEquals(parent, child.getParentNode());
	assertEquals("org.test.resource.child", child.getResourceBundleName());
	assertEquals("/icons/child.png", child.getIconUrl());
	assertEquals("/parent/child", child.getPath());
	assertEquals("child", child.getName());
	
	assertTrue(parent.getChildren().contains(child));
	assertNotNull(parent.getNamedChild("child"));
    }
    
    public void testConstructorNoParent() {
	StandardNavNode child = new StandardNavNode(null, "child", "org.test.resource.child", "/icons/child.png");
	assertEquals(RootNavNode.getInstance(), child.getParentNode());
	assertEquals("/child", child.getPath());
	
	assertTrue(RootNavNode.getInstance().getChildren().contains(child));
	assertNotNull(RootNavNode.getInstance().getNamedChild("child"));
    }
    
    public void testConstructorNoName() {
	StandardNavNode parent = new StandardNavNode( RootNavNode.getInstance(), "parent", "org.test.resource.parent", "/icons/parent.png" );
	StandardNavNode child = new StandardNavNode(parent, null, "org.test.resource.child", "/icons/child.png");
	assertEquals("unknown", child.getName());
    }
    
    public void testConstructorNoResourceBundleName() {
	StandardNavNode parent = new StandardNavNode( RootNavNode.getInstance(), "parent", "org.test.resource.parent", "/icons/parent.png" );
	StandardNavNode child = new StandardNavNode(parent, "child", null, "/icons/child.png");
	assertEquals("org.test.resource.parent", child.getResourceBundleName());
    }
    
    public void testConstructorNoIconUrl() {
	StandardNavNode parent = new StandardNavNode( RootNavNode.getInstance(), "parent", "org.test.resource.parent", "/icons/parent.png" );
	StandardNavNode child = new StandardNavNode(parent, "child", "org.test.resource.child", null);
	assertEquals("/icons/parent.png", child.getIconUrl());
    }
    
    public void testNameResolutionDefault() {
	StandardNavNode parent = new StandardNavNode( RootNavNode.getInstance(), "parent", "org.authorsite.web.resources.test", "/icons/parent.png" );
	Locale.setDefault(Locale.ENGLISH);
	assertEquals("parent", parent.getDefaultLocalName());
    }
    
    public void testNameResolutionLocale() {
	StandardNavNode parent = new StandardNavNode( RootNavNode.getInstance(), "parent", "org.authorsite.web.resources.test", "/icons/parent.png" );
	assertEquals("Eltern", parent.getLocalName(Locale.GERMAN));
	assertEquals("parent", parent.getLocalName(Locale.CHINESE));
    }
    
    
}
