/*
 * PersonAndOrganisationManagementSearchFacadeBean.java
 *
 * Created on 18 December 2002, 16:14
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/PersonAndOrganisationManagementSearchFacadeBean.java,v 1.4 2003/03/01 13:36:45 jejking Exp $
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
 * @ejb.bean    name="PersonAndOrganisationManagementSearchFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/PersonAndOrganisationManagementSearchFacadeEJB"
 *              view-type="remote"
 *
 * @ejb.interface   remote-class="org.authorsite.bib.ejb.facade.PersonAndOrganisationManagementSearchFacade"
 *                  generate="remote"
 * @ejb.home        remote-class="org.authorsite.bib.ejb.facade.PersonAndOrganisationManagementSearchFacadeHome"
 *                  generate="remote"
 *
 * @ejb.ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class PersonAndOrganisationManagementSearchFacadeBean implements SessionBean {
    
  
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
    public Collection findPeople(int listOfPKs[]) throws FinderException {
        ArrayList foundRefs = new ArrayList();
        for (int i = 0; i < listOfPKs.length; i++) {
            PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(listOfPKs[i]));
            foundRefs.add(person);
        }
        return buildDTOList(foundRefs, "people");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findPersonWithoutProductionRelationships() throws FinderException {
        Collection foundPeople = personLocalHome.findWithoutProductionRels();
        return buildDTOList(foundPeople, "people");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findPersonWithUnlinkedProductionRelationships() throws FinderException {
        Collection foundPeople = personLocalHome.findWithUnlinkedProductionRels();
        return buildDTOList(foundPeople, "people");
    }
 
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationWithoutProductionRelationships() throws FinderException {
        Collection foundOrgs = organisationLocalHome.findOrgWithoutProductionRels();
        return buildDTOList(foundOrgs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisations(int listOfPKs[]) throws FinderException {
        ArrayList foundRefs = new ArrayList();
        for (int i = 0; i < listOfPKs.length; i++) {
            OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(listOfPKs[i]));
            foundRefs.add(org);
        }
        return buildDTOList(foundRefs, "orgs");
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public Collection findOrganisationWithUnlinkedProductionRelationships() throws FinderException {
        Collection foundOrgs = organisationLocalHome.findOrgWithUnlinkedProductionRels();
        return buildDTOList(foundOrgs, "orgs");
    }
       
    /** Creates a new instance of PersonAndOrganisationManagementSearchFacadeBean */
    public PersonAndOrganisationManagementSearchFacadeBean() {
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
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException, java.rmi.RemoteException {
        sessionCtx = sessionContext;
    }
    
}
