package org.authorsite.web;

import javax.el.ELContext;
import javax.servlet.jsp.JspWriter;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.mock.web.MockServletContext;

public class WriterCapableMockPageContext extends MockPageContext {

    private MockJspWriter jspWriter;
    
    public WriterCapableMockPageContext(MockServletContext context, MockHttpServletRequest request) {
	super(context, request);
    }

    @Override
    public ELContext getELContext() {
	// TODO Auto-generated method stub
	return null;
    }
    
    public void setMockJspWriter( MockJspWriter mockJspWriter ) {
	this.jspWriter = mockJspWriter; 
    }
    
    @Override
    public JspWriter getOut() {
        return this.jspWriter;
    }
    

}
