/*
 *  HtmlUtils.java, created on 24-Sep-2003 at 15:37:21
 * 
 *  Copyright Alan Tibbetts, 2003.
 *
 *  MemoryVocabSet.java is part of authorsite.org's VocabManager program.
 *
 *  VocabManager is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  VocabManager is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with VocabManager; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.authorsite.vocab.util;

import javax.servlet.http.*;

import org.apache.log4j.Logger;

import org.authorsite.vocab.exceptions.ChainedException;

/**
 *  A collection of handy, little utility methods for use within JSPs.
 * 
 *  @author  Alan Tibbetts
 *  @version $Revision: 1.1 $
 */
public class HtmlUtils {

    private static Logger log = Logger.getLogger("main");
    
    /**
     * Concatenates a given URL with the context root and encodes the result if required.
     * eg. a context path of http://localhost:8080/blackened and a URL of /test.do
     *  becomes http://localhost:8080/blackened/test.do;jsessionid=1213213 if cookies
     *  have been turned off.
     * 
     * @param request
     * @param response
     * @param url
     * @return
     * @throws ChainedException
     */
    public static String encodeUrl(HttpServletRequest request, 
                                   HttpServletResponse response,
                                   String url) throws ChainedException {
        String encodedUrl = null;
        
        try {
            encodedUrl = response.encodeRedirectURL(request.getContextPath() + url);
            
            if (log.isDebugEnabled()) {
                log.debug("URL encoded to " + encodedUrl);
            }
        } catch (Exception e) {
            throw new ChainedException("Unable to encode URL: " + url, e);
        }
        
        return encodedUrl;
    }
    
    /**
     * Converts line feeds in a database string to <br/> constructs for display
     * in a <p> tag.
     * 
     * @param displayText
     * @return
     * @throws ChainedException
     */
    public static String convertReturns(String displayText) throws ChainedException {
        
        StringBuffer convertedString = new StringBuffer();
        
        try {
            for (int i=0; i<displayText.length(); i++) {
                char c = displayText.charAt(i);
                if (c == '\n') {
                    convertedString.append("<br/>");
                } else {
                    convertedString.append(c);
                }
            }
        } catch (Exception e) {
            throw new ChainedException("Error converting text for display.", e);
        }
        
        return convertedString.toString();
    }
}
