/*
 * LanguageBean.java
 *
 * Created on 23 September 2002, 12:02
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/LanguageBean.java,v 1.8 2003/03/09 19:53:30 jejking Exp $
 *
 * Copyright (C) 2002  John King
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

package org.authorsite.bib.ejb.entity;
import javax.ejb.*;
/**
 * Language is an Entity Bean representing languages described according to the 
 * iso639 standards. Because the number of languages and their designations is 
 * circumscribed by the standard, the bean is read-only and populated by a
 * dynamically generated database script from an xml file during the build process.
 *
 * In future, site management requirements will probably necessitate that 
 * the priority be changable in a running system.
 *
 * The database column englishName is not mapped to the Entity Bean as it is a 
 * convenience field for pure database access. Language Names should be mapped
 * in internationalization resource bundles at the client level - i.e. 'eng' should
 * be mapped to 'English', 'anglais', 'Englisch', 'inglese', etc.
 *
 * @ejb:bean name="LanguageEJB"
 *              type="CMP"
 *              reentrant="false"
 *              cmp-version="2.x"
 *              schema="Language"
 *              primkey-field="iso639"
 *              view-type="local"
 *              local-jndi-name="ejb/LanguageLocalEJB"
 * @ejb:interface local-class="org.authorsite.bib.ejb.entity.LanguageLocal"
 *                generate="local,remote"
 * @ejb:home    local-class="org.authorsite.bib.ejb.entity.LanguageLocalHome"
 *              generate="local,remote"
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:transaction type="supports"
 *
 * @ejb:finder  signature="java.util.Set findByPriority(int priority)"
 *              query=     "select Object(L)
 *                          from Language as L
 *                          where L.priority = ?1"
 *
 * @jboss:table-name table-name="language"
 * @jboss:create create="false"
 * @jboss:remove remove="false"
 * @jboss:read-only read-only="true"
 *
 * @jboss.read-ahead strategy="on-find" page-size="10" 
 *
 * @jboss:cmp-field field-name="iso639" column-name="iso639"
 * @jboss:cmp-field field-name="priority" column-name="priority"
 *
 * @author  jejking
 * @version $Revision: 1.8 $
 */
public abstract class LanguageBean implements EntityBean {
    
    private EntityContext ctx;
    
    /**
     * @ejb:persistent-field
     * @ejb:pk-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getIso639();
    
    /**
     *@ejb:persistent-field
     */
    public abstract void setIso639(String iso639);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract int getPriority();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setPriority(int priority);
    
    /** Creates a new instance of LanguageBean */
    public LanguageBean() {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
    }    
    
    public void ejbLoad() throws javax.ejb.EJBException {
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.RemoveException, javax.ejb.EJBException {
    }
    
    public void ejbStore() throws javax.ejb.EJBException {
    }
    
    public void setEntityContext(javax.ejb.EntityContext entityContext) throws javax.ejb.EJBException {
        ctx = entityContext;
    }
    
    public void unsetEntityContext() throws javax.ejb.EJBException, java.rmi.RemoteException {
        ctx = null;
    }
    
}
