package org.authorsite.web.nav;

import java.util.List;

import junit.framework.TestCase;

public class XmlNavNodeFactoryTest extends TestCase {

    public void testXmlConstructionNoAttributes() {
	StandardNavNode parent = new StandardNavNode(RootNavNode.getInstance(), "parent", null, null);
	NavNode people = XmlNavNodeFactory.buildInstance("/testNav1.xml", parent);
	
	assertEquals(parent, people.getParentNode());
	assertEquals("/parent/people", people.getPath());
	
	assertEquals(2, people.getChildren().size());
	List<NavNode> children = people.getChildren();
	NavNode individuals = children.get(0);
	assertEquals("individuals", individuals.getName());
	assertEquals("/parent/people/individuals", individuals.getPath());
	assertEquals(people, individuals.getParentNode());
	
	List<NavNode> grandChildren = individuals.getChildren();
	assertEquals(3, grandChildren.size());
	assertEquals("important", grandChildren.get(0).getName());
	assertEquals("/parent/people/individuals/important", grandChildren.get(0).getPath());
	assertEquals(individuals, grandChildren.get(0).getParentNode());
	assertEquals(0, grandChildren.get(0).getChildren().size());
	assertEquals(RootNavNode.getInstance().getResourceBundleName(), grandChildren.get(0).getResourceBundleName());
	
	
	NavNode collectives = children.get(1);
	assertEquals("collectives", collectives.getName());
	assertEquals("/parent/people/collectives", collectives.getPath());
	assertEquals(people, collectives.getParentNode());
	
	assertTrue(parent.getChildren().contains(people));
	assertTrue(people.getChildren().contains(individuals));
	assertTrue(people.getChildren().contains(collectives));
	
    }
    
    public void testXmlConstructionNoAttributesNoParentSpecified() {
	NavNode people = XmlNavNodeFactory.buildInstance("/testNav1.xml");
	assertEquals(RootNavNode.getInstance(), people.getParentNode());
	assertEquals("/people", people.getPath());
	
	assertEquals(2, people.getChildren().size());
	List<NavNode> children = people.getChildren();
	NavNode individuals = children.get(0);
	assertEquals("individuals", individuals.getName());
	assertEquals("/people/individuals", individuals.getPath());
	assertEquals(people, individuals.getParentNode());
	
	List<NavNode> grandChildren = individuals.getChildren();
	assertEquals(3, grandChildren.size());
	assertEquals("important", grandChildren.get(0).getName());
	assertEquals("/people/individuals/important", grandChildren.get(0).getPath());
	assertEquals(individuals, grandChildren.get(0).getParentNode());
	assertEquals(0, grandChildren.get(0).getChildren().size());
	assertEquals(RootNavNode.getInstance().getResourceBundleName(), grandChildren.get(0).getResourceBundleName());
	
	
	NavNode collectives = children.get(1);
	assertEquals("collectives", collectives.getName());
	assertEquals("/people/collectives", collectives.getPath());
	assertEquals(people, collectives.getParentNode());
    }
    
    public void testAttributeHandling() {
	NavNode people = XmlNavNodeFactory.buildInstance("/testNav2.xml");
	
	assertEquals(RootNavNode.getInstance().getResourceBundleName(), people.getResourceBundleName());
	
	NavNode individualsNavNode = people.getNamedChild("individuals");
	assertEquals("org.authorsite.web.resources.test", individualsNavNode.getResourceBundleName());
	assertEquals("/icons/test.png", individualsNavNode.getIconUrl());
	
	NavNode plebsNavNode = individualsNavNode.getNamedChild("plebs");
	assertEquals("org.authorsite.web.resources.test", plebsNavNode.getResourceBundleName());
	assertEquals("/icons/test.png", plebsNavNode.getIconUrl());
	
	
	NavNode collectivesNavNode = people.getNamedChild("collectives");
	assertEquals(RootNavNode.getInstance().getResourceBundleName(), collectivesNavNode.getResourceBundleName());
    }
    
}
