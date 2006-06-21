/*
 * SequenceBean.java
 *
 * Created on 04 November 2002, 13:06
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/SequenceBean.java,v 1.4 2003/03/01 13:37:14 jejking Exp $
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
 * This entity bean is part of the primary key generation strategy for the authorsite.org bibliographic
 * application. It is derived in the main from the Sequence Block pattern described in Floyd
 * Marinescu's EJB Design Patterns Book (Wiley, 2002). The source code published at
 * www.theserverside.com/patterns/ejbpatterns has been adapted slightly to conform to our own
 * coding standards, build process and package design.
 *
 * @ejb:bean name="SequenceEJB"
 *              type="CMP"
 *              reentrant="false"
 *              cmp-version="2.x"
 *              schema="Sequence"
 *              primkey-field="name"
 *              view-type="local"
 *              local-jndi-name="ejb/SequenceLocalEJB"
 *
 * @ejb:interface local-class="org.authorsite.bib.ejb.entity.SequenceLocal"
 *                generate="local"
 * @ejb:home    local-class="org.authorsite.bib.ejb.entity.SequenceLocalHome"
 *              generate="local"
 * @ejb:pk class="java.lang.String"
 *
 * @ejb:transaction type="RequiresNew" 
 *
 * @jboss:table-name table-name="sequencesList"
 *
 * @jboss:create create="false"
 * @jboss:remove remove="false"
 *
 * @jboss:cmp-field field-name="name" column-name="name"
 * @jboss:cmp-field field-name="index" column-name="index"
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public abstract class SequenceBean implements EntityBean {
    
    private EntityContext ctx;
    
    /**
     * @ejb:create-method
     */
    public String ejbCreate(String newName) throws CreateException {
        this.setName(newName);
        this.setIndex(0);
        return null;
    }
    
    public void ejbPostCreate(String newName) {
    }
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public int getValueAfterIncrementingBy(int blockSize) {
        this.setIndex(this.getIndex() + blockSize);
        return this.getIndex();
    }
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setName(String newName);
    
    /**
     * @ejb:persistent-field
     */
    public abstract String getName();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setIndex(int newIndex);
    
    /**
     * @ejb:persistent-field
     */
    public abstract int getIndex();
    
    
    /** Creates a new instance of SequenceBean */
    public SequenceBean() {
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
