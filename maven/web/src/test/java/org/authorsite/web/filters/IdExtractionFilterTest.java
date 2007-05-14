/*
 * IdExtractionFilterTest.java
 * JUnit based test
 *
 * Created on 28 March 2007, 21:49
 */

package org.authorsite.web.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import junit.framework.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author jejking
 */
public class IdExtractionFilterTest extends TestCase {
    
    public IdExtractionFilterTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }
    
    public void testPeopleIndividuals123() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        request.setRequestURI("/people/individuals/123");
        
        IdExtractionFilter filter = new IdExtractionFilter();
        filter.doFilter(request, response, new MockFilterChain());
        assertEquals(new Long(123), request.getAttribute("id"));
    }
    
    public void testPeopleIndex() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        request.setRequestURI("/people/individuals/index");
        
        IdExtractionFilter filter = new IdExtractionFilter();
        filter.doFilter(request, response, new MockFilterChain());
        assertNull(request.getAttribute("id"));
    }
    
    public void testErrorGen() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        request.setRequestURI("/people/individuals/wibble");
        
        IdExtractionFilter filter = new IdExtractionFilter();
        filter.doFilter(request, response, new MockFilterChain());
        assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatus());
    }
    
    private final class MockFilterChain implements FilterChain {
        
        boolean doFilterCalled = false;
        
        void init(FilterConfig filterConfig) throws ServletException {
            // do nothing
        }

        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
            this.doFilterCalled = true;
        }
    }
}
