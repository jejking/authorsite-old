/*
 * MediaProductionRelationshipBean.java
 *
 * Created on 30 September 2002, 15:20
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/MediaProductionRelationshipBean.java,v 1.10 2003/03/09 19:53:30 jejking Exp $
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
import java.util.*;

/**
 * MediaProductionRelationship represents a collection of all people and organisation who play a specific role
 * in the production of a mediaItem. Thus, a mediaItem will have a numebr of mediaProductionRelationships involved
 * - say "author", "publisher". Each of these relationships may then in turn have a number of people or organisations
 * involved. In this way, a mediaItem can have a complex set of relationships with the people and organisations involved
 * in its production. One person or organisation can have multiple relationships with a mediaItem. For example, Ernst Junger
 * was both author and publisher of the first edition of "In Stahlgewittern". Likewise, a pamphlet can be authored by both
 * named individuals and an organisation.
 * 
 *
 * @ejb:bean name="MediaProductionRelationshipEJB"
 *              type="CMP"
 *              reentrant="false"
 *              cmp-version="2.x"
 *              schema="MediaProductionRelationship"
 *              primkey-field="mediaProductionRelationshipID"
 *              view-type="local"
 *              local-jndi-name="ejb/MediaProductionRelationshipLocalEJB"
 *
 * @ejb:interface local-class="org.authorsite.bib.ejb.entity.MediaProductionRelationshipLocal"
 *                generate="local"
 * @ejb:home    local-class="org.authorsite.bib.ejb.entity.MediaProductionRelationshipLocalHome"
 *              generate="local"
 * @ejb:pk class="java.lang.Integer"
 *
 * @ejb:transaction type="supports"
 *
 * @jboss:table-name table-name="mediaProductionRelationship"
 * @jboss:create create="false"
 * @jboss:remove remove="false"
 * @jboss:cmp-field field-name="mediaProductionRelationshipID" column-name="mediaProductionRelationshipID"
 * @jboss:cmp-field field-name="relationshipType" column-name="relationshipType"
 *
 *
 * @author  jejking 
 * @version $Revision: 1.10 $
 */
public abstract class MediaProductionRelationshipBean implements EntityBean {
   // - temporarily extracted ... @jboss.read-ahead strategy="on-load" page-size="5" eager-load-group="Main"  
    private EntityContext ctx;
    
    /**
     * @ejb:create-method
     */
    public Integer ejbCreate(int newMediaProductionRelationshipID, String newRelationshipType) throws CreateException {
        setMediaProductionRelationshipID(new Integer(newMediaProductionRelationshipID));
        setRelationshipType(newRelationshipType);
        return null;
    }
    
    public void ejbPostCreate(int newMediaProductionRelationshipID, String newRelationshipType) {
    }
    
    /**
     * @ejb:persistent-field
     * @ejb:pk-field
     * @ejb:interface-method view-type="local"
     */
    public abstract Integer getMediaProductionRelationshipID();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setMediaProductionRelationshipID(Integer newMediaProductionRelationshipID);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getRelationshipType();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setRelationshipType(String newMediaProductionRelationshipType);
    
    
    /**
     * @ejb:relation    name="media-productionRelationships"
     *                  role-name="productionRelationship-exists-for-one-MediaItem"
     *                  cascade-delete="yes"
     * @ejb:interface-method view-type="local"
     *
     * @jboss:relation related-pk-field="mediaItemID" fk-column="mediaItemID"
     */
    public abstract org.authorsite.bib.ejb.entity.MediaItemLocal getMediaItem();
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public abstract void setMediaItem(org.authorsite.bib.ejb.entity.MediaItemLocal newMediaItemLocal);
    
    /**
     * @ejb:relation    name="productionRelationships-people"
     *                  role-name="productionRelationships involve people"
     * @ejb:interface-method view-type="local"
     *
     * @jboss:relation-table table-name="personMediaProdRel"
     * @jboss:relation related-pk-field="personID" fk-column="personID"
     */
    public abstract Set getPeople();
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public abstract void setPeople(Set newPeople);
    
    /** Creates a new instance of MediaProductionRelationshipBean */
    public MediaProductionRelationshipBean() {
    }
    
    /**
     * @ejb:relation    name="productionRelationships-organisations"
     *                  role-name="productionRelationships involve organisations"
     * @ejb:interface-method view-type="local"
     *
     * @jboss:relation-table table-name="orgMediaProdRel"
     * @jboss:relation related-pk-field="organisationID" fk-column="organisationID"
     */
    public abstract Set getOrganisations();
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public abstract void setOrganisations(Set newOrganisations);
    
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
