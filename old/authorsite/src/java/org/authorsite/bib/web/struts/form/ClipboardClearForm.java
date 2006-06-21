/*
 * ClipboardClearForm.java
 *
 * Created on 05 February 2003, 19:22
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/form/ClipboardClearForm.java,v 1.3 2003/03/01 13:32:59 jejking Exp $
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

package org.authorsite.bib.web.struts.form;
import javax.servlet.http.*;
import org.apache.struts.action.*;
import org.apache.struts.util.*;
import org.authorsite.bib.web.util.*;
/**
 *
 * @author  jejking
 * @version $Revision: 1.3 $
 */
public class ClipboardClearForm extends ActionForm {
    
    private String clipboard;
    
    /** Creates a new instance of ClipboardClearForm */
    public ClipboardClearForm() {
    }
    
    public String getClipboard() {
        return clipboard;
    }
    
    public void setClipboard(String newClipboard) {
        clipboard = newClipboard;       
    }
    
    /**
     * <p>
     * Ensures that clipboard value is one of:
     * </p>
     * <ul>
     *  <li>orgs</li>
     *  <li>people</li>
     *  <li>mediaItems</li>
     *  <li>all</li>
     * </ul>
     */
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        
        if (clipboard.equals("people") || clipboard.equals("orgs") || clipboard.equals("mediaItems") || clipboard.equals("languages") || clipboard.equals("all")) {
            return errors;
        }
        else {
            ActionError newError = new ActionError("web.errors.invalidOption", clipboard);
            errors.add(ActionErrors.GLOBAL_ERROR, newError);
            return errors;
        }
    }
    
}
