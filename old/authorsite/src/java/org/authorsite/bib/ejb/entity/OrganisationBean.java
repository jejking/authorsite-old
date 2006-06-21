/*
 * OrganisationBean.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/OrganisationBean.java,v 1.7 2003/03/09 19:53:30 jejking Exp $
 * Created on 02 October 2002, 21:02
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
 * @ejb:bean    name="OrganisationEJB"
 *              type="CMP"
 *              reentrant="false"
 *              cmp-version="2.x"
 *              schema="Organisation"
 *              primkey-field="organisationID"
 *              view-type="local"
 *              local-jndi-name="ejb/OrganisationLocalEJB"
 *
 * @ejb:interface local-class="org.authorsite.bib.ejb.entity.OrganisationLocal"
 *                generate="local"
 * @ejb:home    local-class="org.authorsite.bib.ejb.entity.OrganisationLocalHome"
 *              generate="local"
 *
 * @ejb:pk class="java.lang.Integer"
 *
 * @ejb:transaction type="supports"
 *
 * @ejb:finder  signature="java.util.Collection findByOrgName(java.lang.String name)"
 *              query = "select Object(ORG) from Organisation as ORG
 *                       where ORG.name = ?1"
 *
 * @ejb:finder  signature="java.util.Collection findByLikeOrgName(java.lang.String name)"
 *              query=    "select Object(ORG) from Organisation as ORG
 *                         where ORG.name like '?1' "
 *
 * @jboss:query signature="java.util.Collection findByLikeOrgName(java.lang.String name)"
 *              query=    "select Object(ORG) from Organisation as ORG
 *                         where ORG.name like ?1 "
 *
 * @ejb:finder signature="java.util.Collection findByOrgNameAndCity(java.lang.String orgName, java.lang.String cityName)"
 *             query=    "select Object(ORG) from Organisation as ORG
 *                        where ORG.name = ?1 and ORG.city = ?2"
 *
 * @ejb:finder signature="java.util.Collection findByLikeOrgNameAndCity(java.lang.String orgName, java.lang.String cityName)"
 *              query = "select Object(ORG) from Organisation as ORG
 *                       where ORG.name like '?1' and ORG.city like '?2'"
 *
 * @jboss:query signature="java.util.Collection findByLikeOrgNameAndCity(java.lang.String orgName, java.lang.String cityName)"
 *              query = "select Object(ORG) from Organisation as ORG
 *                       where ORG.name like ?1 and ORG.city like ?2"
 *
 * @ejb:finder signature="java.util.Collection findByOrgNameAndCountry(java.lang.String orgName, java.lang.String countryName)"
 *             query = "select Object(ORG) from Organisation as ORG
 *                      where ORG.name = ?1 and ORG.country = ?2"
 *
 * @ejb:finder signature="java.util.Collection findByLikeOrgNameAndCountry(java.lang.String orgName, java.lang.String countryName)"
 *             query = "select Object(ORG) from Organisation as ORG
 *                      where ORG.name like '?1' and ORG.country like '?2'"
 *
 * @jboss:query signature="java.util.Collection findByLikeOrgNameAndCountry(java.lang.String orgName, java.lang.String countryName)"
 *             query = "select Object(ORG) from Organisation as ORG
 *                      where ORG.name like ?1 and ORG.country like ?2"
 *
 * @ejb:finder signature="java.util.Collection findByOrgNameCityCountry(java.lang.String orgName, java.lang.String cityName, java.lang.String countryName)"
 *             query = "select Object(ORG) from Organisation as ORG
 *                      where ORG.name = ?1 and ORG.city = ?2 and ORG.country = ?3"
 *
 * @ejb:finder signature="java.util.Collection findByLikeOrgNameCityCountry(java.lang.String orgName, java.lang.String cityName, java.lang.String countryName)"
 *             query = "select Object(ORG) from Organisation as ORG
 *                      where ORG.name like '?1' and ORG.city like '?2' and ORG.country like '?3'"
 *
 * @jboss:query signature="java.util.Collection findByLikeOrgNameCityCountry(java.lang.String orgName, java.lang.String cityName, java.lang.String countryName)"
 *              query = "select Object(ORG) from Organisation as ORG
 *                      where ORG.name like ?1 and ORG.city like ?2 and ORG.country like ?3"
 *
 * @ejb:finder  signature="java.util.Collection findOrgWithoutProductionRels()"
 *              query=    "select Object(ORG) from Organisation as ORG
 *                         where ORG.mediaProductionRelationships is empty"
 *
 * @ejb:finder  signature="java.util.Collection findOrgWithUnlinkedProductionRels()"
 *              query=    "select Object(ORG) from Organisation as ORG, in (ORG.mediaProductionRelationships) as MPR
 *                         where MPR.mediaItem is null"
 *
 * @jboss:table-name table-name="Organisation"
 * @jboss:create create="false"
 * @jboss:remove remove="false"
 * @jboss:cmp-field field-name="organisationID" column-name="organisationID"
 * @jboss:cmp-field field-name="name" column-name="name"
 * @jboss:cmp-field field-name="city" column-name="city"
 * @jboss:cmp-field field-name="country" column-name="country"
 *
 * @author  jejking
 * @version $Revision: 1.7 $
 */
public abstract class OrganisationBean implements EntityBean {
    
    private EntityContext ctx;
    
    /**
     * @ejb:create-method
     */
    public Integer ejbCreate(int newOrganisationID, String newName) throws CreateException {
        setOrganisationID(new Integer(newOrganisationID));
        setName(newName);
        return null;
    }
    
    public void ejbPostCreate(int newOrganisationID, String newName) {
    }
    
    /**
     * @ejb:create-method
     */
    public Integer ejbCreate(int newOrganisationID, String newName, String newCity) throws CreateException {
        setOrganisationID(new Integer(newOrganisationID));
        setName(newName);
        setCity(newCity);
        return null;
    }
    
    public void ejbPostCreate(int newOrganisationID, String newName, String newCity) {
    }
    
    /**
     * @ejb:create-method
     */
    public Integer ejbCreate(int newOrganisationID, String newName, String newCity, String newCountry) throws CreateException {
        setOrganisationID(new Integer(newOrganisationID));
        setName(newName);
        setCity(newCity);
        setCountry(newCountry);
        return null;
    }
    
    public void ejbPostCreate(int newOrganisationID, String newName, String newCity, String newCountry) {
    }
    
    /**
     * @ejb:persistent-field
     * @ejb:pk-field
     * @ejb:interface-method view-type="local"
     */
    public abstract Integer getOrganisationID();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setOrganisationID(Integer newOrganisationID);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getName();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setName(String newName);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getCity();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setCity(String newCity);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getCountry();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setCountry(String newCountry);
    
    /**
     * @ejb:relation    name="productionRelationships-organisations"
     *                  role-name="organisations are involved in productionRelationships"
     * @ejb:interface-method view-type="local"
     *
     * @jboss.relation-table table-name="orgMediaProdRel"
     * @jboss:relation related-pk-field="mediaProductionRelationshipID" fk-column="mediaProductionRelationshipID"
     */
    public abstract Set getMediaProductionRelationships();
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public abstract void setMediaProductionRelationships(Set newMediaProductionRelationships);
    
    /** Creates a new instance of OrganisationBean */
    public OrganisationBean() {
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
