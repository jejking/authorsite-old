/*
 * IntraMediaRelationshipBean.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/IntraMediaRelationshipBean.java,v 1.7 2003/03/09 19:53:29 jejking Exp $
 *
 * Created on 25 September 2002, 10:31
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
 * The concept of the IntraMediaRelationship is absolutely central to the design of the 
 * authorsite bibliographic database application as it allows a great deal of flexibility
 * and power in relating items together in a way many other bibliographic applications cannot.
 * Its first application is in allowing us to construct graphs of mediaItems to form larger
 * logical media records. Its second application allows us to trace relationships between 
 * distinct logical media records such as translations, editions, reprints, recordings, precursor
 * manuscripts, etc.
 *
 * @ejb:bean name="IntraMediaRelationshipEJB"
 *              type="CMP"
 *              reentrant="false"
 *              cmp-version="2.x"
 *              schema="IntraMediaRelationship"
 *              primkey-field="intraMediaRelationshipID"
 *              view-type="local"
 *              local-jndi-name="ejb/IntraMediaRelationshipLocalEJB"
 * @ejb:interface   local-class="org.authorsite.bib.ejb.entity.IntraMediaRelationshipLocal"
 *                  generate="local"
 * @ejb:home        local-class="org.authorsite.bib.ejb.entity.IntraMediaRelationshipLocalHome"
 *                  generate="local"
 * @ejb:pk          class="java.lang.Integer"
 *
 * @ejb:transaction type="supports"
 *
 * @ejb:finder  signature="java.util.Collection findByMediaFromID (int mediaFromID)"
 *              query=     "select Object(IMR)
 *                          from IntraMediaRelationship as IMR
 *                          where IMR.mediaFromID = ?1"
 *
 * @ejb.finder signature="java.util.Collection findByMediaFromIDByType (int mediaFromID, java.lang.String relationshipType)"
 *             query=    "select Object(IMR)
 *                        from IntraMediaRelationship as IMR
 *                        where IMR.mediaFromID = ?1 and IMR.relationshipType = ?2"
 *
 * @ejb:finder  signature="java.util.Collection findByMediaToID(int mediaToID)"
 *              query=    "select Object(IMR)
 *                         from IntraMediaRelationship as IMR
 *                         where IMR.mediaToID = ?1"
 *
 * @ejb.finder  signature="java.util.Collection findByMediaToIDByType(int mediaToID, java.lang.String relationshipType)"
 *              query=    "select Object(IMR)
 *                         from IntraMediaRelationship as IMR
 *                         where IMR.mediaToID = ?1 and IMR.relationshipType = ?2"
 
 *
 * @jboss:table-name table-name="intraMediaRelationship"
 * @jboss:create create="false"
 * @jboss:remove remove="false"
 * @jboss:cmp-field field-name="intraMediaRelationshipID" column-name="intraMediaRelationshipID"
 * @jboss:cmp-field field-name="mediaFromID" column-name="mediaFromID"
 * @jboss:cmp-field field-name="relationshipType" column-name="relationshipType"
 * @jboss:cmp-field field-name="mediaToID" column-name="mediaToID"
 * @jboss:cmp-field field-name="relationshipQualifier" column-name="relationshipQualifier"
 *
 * @jboss.read-ahead strategy="on-find" page-size="10" eager-load-group="Main"
 *
 * @author  jejking
 * @version $Revision: 1.7 $
 */
public abstract class IntraMediaRelationshipBean implements EntityBean {
    
    private EntityContext ctx;
    
    /**
     * @ejb:create-method
     */
    public Integer ejbCreate(int newIntraMediaRelationshipID, int newMediaFromID, String newRelationshipType, int newMediaToID) 
        throws CreateException {
            setIntraMediaRelationshipID(new Integer(newIntraMediaRelationshipID));
            setMediaFromID(newMediaFromID);
            setRelationshipType(newRelationshipType);
            setMediaToID(newMediaToID);
            return null;
    }
    
    public void ejbPostCreate(int newIntraMediaRelationshipID, int newMediaFromID, String newRelationshipType, int newMediaToID) {
    }
    
    /**
     * @ejb:persistent-field
     * @ejb:pk-field
     * @ejb:interface-method view-type="local"
     */
    public abstract Integer getIntraMediaRelationshipID();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setIntraMediaRelationshipID(Integer newIntraMediaRelationshipID);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract int getMediaFromID();
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setMediaFromID(int newMediaFromID);
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract int getMediaToID();
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setMediaToID(int newMediaToID);
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getRelationshipType();
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setRelationshipType(String newRelationshipType);
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getRelationshipQualifier();
    
     /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setRelationshipQualifier(String newRelationshipQualifier);
    
    /** Creates a new instance of IntraMediaRelationshipBean */
    public IntraMediaRelationshipBean() {
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
