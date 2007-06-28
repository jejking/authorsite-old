/*
 * JspContextStub.java
 *
 * Created on 16 June 2007, 22:59
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.authorsite.web;

import java.util.Enumeration;
import javax.el.ELContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

/**
 *
 * @author jejking
 */
public class JspContextStub extends JspContext {
    
    private MockJspWriter jspWriter;
    
    public void setMockJspWriter(MockJspWriter mockJspWriter) {
        this.jspWriter = mockJspWriter;
    }
    
        public JspWriter getOut() {
        return this.jspWriter;
    }

    public void setAttribute(String string, Object object) {
        throw new UnsupportedOperationException();
    }

    public void setAttribute(String string, Object object, int i) {
        throw new UnsupportedOperationException();
    }

    public Object getAttribute(String string) {
        throw new UnsupportedOperationException();
    }

    public Object getAttribute(String string, int i) {
        throw new UnsupportedOperationException();
    }

    public Object findAttribute(String string) {
        throw new UnsupportedOperationException();
    }

    public void removeAttribute(String string) {
        throw new UnsupportedOperationException();
    }

    public void removeAttribute(String string, int i) {
        throw new UnsupportedOperationException();
    }

    public int getAttributesScope(String string) {
        throw new UnsupportedOperationException();
    }

    public Enumeration<String> getAttributeNamesInScope(int i ) {
        throw new UnsupportedOperationException();
    }

    public ExpressionEvaluator getExpressionEvaluator() {
        throw new UnsupportedOperationException();
    }

    public VariableResolver getVariableResolver() {
        throw new UnsupportedOperationException();
    }

    public ELContext getELContext() {
        throw new UnsupportedOperationException();
    }

    
}
