package org.authorsite.web;

import javax.servlet.ServletContext;

import org.springframework.mock.web.MockServletContext;

public class Jee5MockServletContext extends MockServletContext implements
	ServletContext {

    private String contextPath;

    public void setContextPath(String contextPath) {
	this.contextPath = contextPath;
    }
    
    public String getContextPath() {
	return this.contextPath;
    }

}
