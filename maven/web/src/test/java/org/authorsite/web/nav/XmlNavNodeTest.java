/*
 * XmlNavNodeTest.java
 * 
 * Created on 29.06.2007, 16:45:04
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web.nav;

import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author jking
 */
public class XmlNavNodeTest extends TestCase{

    public XmlNavNodeTest() {
    }

    public void testSimpleAssembly() throws Exception {
        XmlNavNode navNode = new XmlNavNode("/testNav1.xml");
        assertEquals("people", navNode.getName());
        List<NavNode> children = navNode.getChildren();
        assertEquals(2, children.size());
        
        NavNode individuals = children.get(0);
        assertEquals("individuals", individuals.getName());
        
        List<NavNode> grandChildren = individuals.getChildren();
        assertEquals(3, grandChildren.size());
        assertEquals("plebs", individuals.getNamedChild("plebs"));
        
        NavNode collectives = navNode.getNamedChild("collectives");
        assertNotNull(collectives);
        assertEquals("collectives", collectives.getName());
        
        NavNode shouldBeNull = navNode.getNamedChild("blahblah");
        assertNull(shouldBeNull);
    }
    
}
