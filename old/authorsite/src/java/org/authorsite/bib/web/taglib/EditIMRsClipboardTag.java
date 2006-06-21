/*
 * EditIMRsClipboardTag.java
 *
 * Created on 26 February 2003, 11:32
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/taglib/EditIMRsClipboardTag.java,v 1.1 2003/03/01 13:31:12 jejking Exp $
 *
 * Copyright (C) 2003  John King
 *
 * This file is part of the authorsite.org bibliographic
 * application.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.authorsite.bib.web.taglib;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.authorsite.bib.dto.*;
import org.apache.commons.beanutils.*;
import org.apache.struts.Globals;
import org.apache.struts.util.*;
/**
 * Custom tag to present legitimate choices regarding intra media relationships 
 * to a user. It iterates through the media items on the clipboard (i.e. in the session attribute
 * "MediaItemsClipboardMap") and if the type of those media items is permitted in an
 * intra media relationship with the current ActiveMediaItem (i.e. listed in the "ActiveIntraMediaRelationshipsMap"),
 * then these are made available in the page context as "CurrentMediaItemDTO" for processing by the MediaItemTag 
 * which will be nested within it.
 * 
 * @author  jejking
 * @version $Revision: 1.1 $
 */
public class EditIMRsClipboardTag extends BodyTagSupport {
    
    private Iterator mcmIt;
    private HashSet relsPermitted;
    
    /** Creates a new instance of EditIMRsClipboardTag */
    public EditIMRsClipboardTag() {
    }
   
    public int doStartTag() throws JspException {
        try {
            String[] mediaItemID = null;
            String imr = BeanUtils.getProperty(pageContext.findAttribute("IMRSelectForm"), "intraMediaRelationship");
            // we need to see if there is a "MediaItemsClipboardMap" attribute in session scope
            HttpSession session = pageContext.getSession();
            HashMap mediaItemsClipboardMap = (HashMap) session.getAttribute("MediaItemsClipboardMap");
            if (mediaItemsClipboardMap == null) {
                return (SKIP_BODY);
            }
            
                       
            mediaItemID = (String[]) BeanUtils.getArrayProperty(pageContext.findAttribute("IMRSelectForm"), "mediaItemID");
            Arrays.sort(mediaItemID);
            // having sorted the array, the MediaItemTags can use if more efficiently. put it in the page context for them to use
            pageContext.setAttribute("MediaItemIDs", mediaItemID);
            HashMap activeIntraMediaRelationships = (HashMap) pageContext.findAttribute("ActiveIntraMediaRelationshipsMap");
            relsPermitted = (HashSet) activeIntraMediaRelationships.get(imr);
                        
            // ok, now get an iterator for the Map
            mcmIt = mediaItemsClipboardMap.values().iterator();
            
            return iterate(pageContext, mcmIt);
            
            
        }
        catch (Exception e) {
            throw new JspException(e);
        }
    }
    
    
    public int doAfterBody() throws JspException {
         
        BodyContent bc = getBodyContent();
        if (bc != null) { // erm, copied off Struts iterate tag
            ResponseUtils.writePrevious(pageContext, bc.getString());
            bc.clearBody();
        }
        
        return iterate(pageContext, mcmIt);
    }
     
    public int doEndTag() throws JspException {
        // Continue processing this page
        return (EVAL_PAGE);
    }
    
    public void release() {
        mcmIt = null;
        relsPermitted = null;
    }
    
    private int iterate(PageContext pageContext, Iterator mcmIt) throws JspException {
        
        // now, anything left in the iterator??
        if (mcmIt.hasNext()) {
        
            // now, the difference here is that we need to check whether the item we are currently looking at 
            // on the clipboard is permitted to be in an intra media relationships with the ActiveMediaItem
            MediaItemDTO currentDTO = (MediaItemDTO) mcmIt.next();
                
            if (relsPermitted.contains(currentDTO.getMediaType())) {
                // set the DTO to be stored in the PageContext under "CurrentMediaItemDTO"
                pageContext.setAttribute("CurrentMediaItemDTO", currentDTO);
                return (EVAL_BODY_BUFFERED); // JSP 1.2
            }
            else {
                return (SKIP_BODY);
            }
        }
        else {
            return (SKIP_BODY);
        }
    }
    
}
