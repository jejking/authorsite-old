/*
 * PersonOrganisationPublicSearchFacadeBean.java
 *
 * Created on 17 December 2002, 23:22
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/PersonAndOrganisationPublicSearchFacadeBean.java,v 1.2 2003/03/01 13:36:45 jejking Exp $
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

package org.authorsite.bib.ejb.facade;
import javax.ejb.*;
import javax.naming.*;
import java.util.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.ejb.services.database.*;
import org.authorsite.bib.ejb.services.dto.*;
/**
 * @ejb.bean    name="PersonAndOrganisationPublicSearchFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/PersonAndOrganisationPublicSearchFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.facade.PersonAndOrganisationPublicSearchFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.facade.PersonAndOrganisationPublicSearchFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.2 $
 */
public class PersonAndOrganisationPublicSearchFacadeBean implements SessionBean {
    
    private SessionContext sessionCtx;
    private InitialContext ctx;
    private PersonLocalHome personLocalHome;
    private OrganisationLocalHome organisationLocalHome;
        
    private void getLocalHomes() {
        try {
            ctx = new InitialContext();
            personLocalHome = (PersonLocalHome) ctx.lookup("java:comp/env/ejb/MyPersonEJB");
            organisationLocalHome = (OrganisationLocalHome) ctx.lookup("java:comp/env/ejb/MyOrganisationEJB");
        }
        catch (NamingException ne) {
            // arrghhh! If this happens, we're a tad stitched ;-)
            ne.printStackTrace();
        }
    }
    
    private ArrayList buildDTOList(Collection collection, String type) {
        ArrayList dtoList = new ArrayList(collection.size());
        Iterator collectionIt = collection.iterator();
        while (collectionIt.hasNext()) {
            if (type.equals("people")){
                PersonLocal currentPerson = (PersonLocal) collectionIt.next();
                PersonDTOAssembler assembler = new PersonDTOAssembler(currentPerson);
                dtoList.add(assembler.assembleDTO());
            }
            else {
                OrganisationLocal currentOrg = (OrganisationLocal) collectionIt.next();
                OrganisationDTOAssembler assembler = new OrganisationDTOAssembler(currentOrg);
                dtoList.add(assembler.assembleDTO());
            }
        }
        return dtoList;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public PersonDTO findPersonByPersonID(int personID) throws FinderException {
        Integer personIDInteger = new Integer(personID);
        PersonLocal currentPerson = personLocalHome.findByPrimaryKey(personIDInteger);
        PersonDTOAssembler assembler = new PersonDTOAssembler(currentPerson);
        return assembler.assembleDTO();
    }
    
       
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findPersonByMainName(String mainName) throws FinderException {
        Collection foundPeople = personLocalHome.findByMainName(mainName);
        return buildDTOList(foundPeople, "people");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findPersonByMainNameAndGivenName(String mainName, String givenName) throws FinderException {
        Collection foundPeople = personLocalHome.findByMainNameAndGivenName(mainName, givenName);
        return buildDTOList(foundPeople, "people");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findPersonLikeMainName(String mainName) throws FinderException {
        Collection foundPeople = personLocalHome.findLikeMainName(mainName);
        return buildDTOList(foundPeople, "people");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findPersonLikeMainNameAndGivenName(String mainName, String givenName) throws FinderException {
        Collection foundPeople = personLocalHome.findLikeMainNameAndGivenName(mainName, givenName);
        return buildDTOList(foundPeople, "people");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public OrganisationDTO findOrganisationByOrganisationID(int orgID) throws FinderException {
        Integer orgIDInteger = new Integer(orgID);
        OrganisationLocal currentOrg = organisationLocalHome.findByPrimaryKey(orgIDInteger);
        OrganisationDTOAssembler assembler = new OrganisationDTOAssembler(currentOrg);
        return assembler.assembleDTO();
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByName(String name) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByOrgName(name);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationLikeName(String name) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByLikeOrgName(name);
        return buildDTOList(foundOrgs, "orgs");
    }
    
   /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByNameAndCity(String orgName, String cityName) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByOrgNameAndCity(orgName, cityName);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByLikeNameAndCity(String orgName, String cityName) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByLikeOrgNameAndCity(orgName, cityName);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByNameAndCountry(String orgName, String countryName) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByOrgNameAndCountry(orgName, countryName);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByLikeNameAndCountry(String orgName, String countryName) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByLikeOrgNameAndCountry(orgName, countryName);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByNameCityCountry(java.lang.String orgName, java.lang.String cityName, java.lang.String countryName) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByOrgNameCityCountry(orgName, cityName, countryName);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationByLikeNameCityCountry(java.lang.String orgName, java.lang.String cityName, java.lang.String countryName) throws FinderException {
        Collection foundOrgs = organisationLocalHome.findByLikeOrgNameCityCountry(orgName, cityName, countryName);
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /** Creates a new instance of PersonOrganisationPublicSearchFacadeBean */
    public PersonAndOrganisationPublicSearchFacadeBean() {
        getLocalHomes();
    }
    
    public void ejbCreate() throws CreateException {
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        sessionCtx = sessionContext;
    }
    
}
