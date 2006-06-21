/*
 * CollectionBean.java
 *
 * Created on 13 January 2003, 12:32
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/struts/util/CollectionBean.java,v 1.2 2003/03/01 13:31:35 jejking Exp $
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

package org.authorsite.bib.web.struts.util;
import java.util.*;
/**
 * Wraps a Collection in a Bean so it can be used by struts custom tags.
 * This is actually not needed and was only written because I didn't quite understand
 * the logic iterate tag first time round.
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class CollectionBean {
    
    private Collection collection;
    
    /** Creates a new instance of CollectionBean */
    public CollectionBean() {
    }
    
    public Collection getCollection() {
        return collection;
    }
    
    public void setCollection(Collection newCollection) {
        collection = newCollection;
    }
    
}
