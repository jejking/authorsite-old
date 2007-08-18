package org.authorsite.web.tags;

import java.util.Locale;

import org.authorsite.web.Jee5MockServletContext;
import org.authorsite.web.MockJspWriter;
import org.authorsite.web.WriterCapableMockPageContext;
import org.authorsite.web.nav.NavNode;
import org.authorsite.web.nav.XmlNavNodeFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

import junit.framework.TestCase;
import org.authorsite.web.nav.AbstractNavNode;
import org.authorsite.web.nav.RootNavNode;

public class NavTagTest extends TestCase {

    private NavTag navTag;
    private MockJspWriter mockJspWriter;
    private MockHttpServletRequest request; 
    private WriterCapableMockPageContext pageContext;
    private Jee5MockServletContext servletContext;
    
    
    public NavTagTest(String arg0) {
	super(arg0);
    }

    protected void setUp() throws Exception {
	super.setUp();
	
	
	this.request = new MockHttpServletRequest();
	this.request.addPreferredLocale(Locale.ENGLISH);
	this.request.setRequestURI("/myWebContext/people/individuals/123");
	this.request.setContextPath("/myWebContext");
	
	this.servletContext = new Jee5MockServletContext();
	this.servletContext.setContextPath("/myWebContext");
	
	this.pageContext = new WriterCapableMockPageContext(this.servletContext, request);
	
	this.mockJspWriter = new MockJspWriter(1, false);
	this.pageContext.setMockJspWriter(this.mockJspWriter);
	
	this.navTag = new NavTag();
	this.navTag.setPageContext(this.pageContext);
	
        ((AbstractNavNode)RootNavNode.getInstance()).clearChildren();
        
	XmlNavNodeFactory.buildInstance("/testNav3.xml");
    }

    public void testDoStartTag() throws Exception {
	this.navTag.doStartTag();
	System.out.println(mockJspWriter.getPrinted());
	
    }

}
