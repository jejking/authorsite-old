/*
 * EJBHomeFactory.java
 *
 * Created on 07 January 2003, 19:54
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/web/util/EJBHomeFactory.java,v 1.2 2003/03/01 13:30:43 jejking Exp $
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

package org.authorsite.bib.web.util;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

/**<p>
 * Singleton which provides easy access to the home interfaces of the EJB layer. Looks up and caches
 * references to home interfaces, returns them on request.
 * </p>
 * <p>Code as inspired by Flloyd Marinescu's <i>EJB Design Patterns</i> </p>
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class EJBHomeFactory {
    
    private HashMap homes;
    private static EJBHomeFactory ejbHomeFactory;
    private Context ctx;
    
    private EJBHomeFactory() throws NamingException {
        homes = new HashMap();
        ctx = new InitialContext();
    }
    
    public static EJBHomeFactory getInstance() throws NamingException {
        if (ejbHomeFactory == null) {
             ejbHomeFactory = new EJBHomeFactory();
        }
        return ejbHomeFactory;
    }
    
    public EJBHome lookupHome(String jndiName, Class homeClass) throws NamingException {
        EJBHome home = (EJBHome) homes.get(homeClass);
        if (home == null) {
            home = (EJBHome) PortableRemoteObject.narrow(ctx.lookup(jndiName), homeClass);
            homes.put(homeClass, home);
        }
        return home;
    }
    
}
