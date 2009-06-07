/**
 * 
 */
package org.authorsite.domain.bib;

import junit.framework.TestCase;

/**
 * @author jking
 *
 */
public class WebResourceTest extends TestCase {

    /**
     * Test method for {@link org.authorsite.domain.bib.WebResource#setUrl(java.lang.String)}.
     */
    public final void testSetUrlOk() {
        WebResource webResource = new WebResource();
        webResource.setUrl("http://www.juenger.org/");
        assertEquals("http://www.juenger.org/", webResource.getUrl());
    }
    
    public final void testSetUrlDuffFormat() {
        WebResource webResource = new WebResource();
        try {
            webResource.setUrl("blahb balh");
            fail("expected illegal argument exception");
        }
        catch (IllegalArgumentException iae) {
            assertTrue(true);
        }
    }
    
    public final void testSetUrlStartsWithWWW() {
        WebResource webResource = new WebResource();
        webResource.setUrl("www.blah.org");
        assertEquals("http://www.blah.org", webResource.getUrl());
    }
    
    public final void testCheckUrlOk() {
        WebResource webResource = new WebResource();
        webResource.setUrl("http://www.juenger.org/contents.php");
        assertEquals(200, webResource.checkUrl());
    }


    public final void testCheckUrlNonExistent() {
        WebResource webResource = new WebResource();
        webResource.setUrl("http://www.asfasdfa34577afafasfasfasf.as/");
        //assertEquals(0, webResource.checkUrl());
    }
    
    public final void testCheckUrlNotFound() {
        WebResource webResource = new WebResource();
        webResource.setUrl("http://www.juenger.org/completelyNonexistent.php");
        assertEquals(404, webResource.checkUrl());
    }

}
