/*
 * OrganisationTestFacadeBean.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/entity/OrganisationTestFacadeBean.java,v 1.4 2003/03/01 13:35:55 jejking Exp $
 *
 * Created on 02 October 2002, 21:26
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

package org.authorsite.bib.ejb.test.entity;

import java.util.*;
import javax.ejb.*;
import javax.naming.*;

import org.authorsite.bib.ejb.entity.*;

/**
 * Test package for organisation EJB
 *
 * @ejb:bean    name="OrganisationTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/OrganisationTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.entity.OrganisationTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.entity.OrganisationTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="MediaProductionRelationshipEJB"
 *              ref-name="ejb/MyMediaProductionRelationshipEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="MediaItemEJB"
 *              ref-name="ejb/MyMediaItemEJB"
 *              view-type="local"
 *
 *
 * @ejb:transaction type="required"
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class OrganisationTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private OrganisationLocalHome organisationLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    private MediaItemLocalHome mediaItemLocalHome;
    
    public void ejbCreate() throws CreateException {
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyOrganisationEJB");
            organisationLocalHome = (OrganisationLocalHome) obj;
            Object obj2 = context.lookup("java:comp/env/ejb/MyMediaProductionRelationshipEJB");
            mediaProductionRelationshipLocalHome = (MediaProductionRelationshipLocalHome) obj2;
            Object obj3 = context.lookup("java:comp/env/ejb/MyMediaItemEJB");
            mediaItemLocalHome = (MediaItemLocalHome) obj3;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    private int getKeyCandidate() {
        Long keyLong = new Long(System.currentTimeMillis());
        return keyLong.intValue();
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testCreateMethods() {
        try {
            // create using minimum fields
            int orgKey1 = getKeyCandidate();
            OrganisationLocal org1 = organisationLocalHome.create(orgKey1, "organisation one");
            
            int orgKey2 = getKeyCandidate();
            OrganisationLocal org2 = organisationLocalHome.create(orgKey2, "organisation two", "london");
            
            int orgKey3 = getKeyCandidate();
            OrganisationLocal org3= organisationLocalHome.create(orgKey3, "organisation three", "london", "GB");
            
            // now look for them all in turn and test each of the getters to boot
            OrganisationLocal foundOrg1 = organisationLocalHome.findByPrimaryKey(new Integer(orgKey1));
            if (!foundOrg1.getName().equals("organisation one")) {
                return ("found organisation did not have correct name");
            }
            
            OrganisationLocal foundOrg2 = organisationLocalHome.findByPrimaryKey(new Integer(orgKey2));
            if (!foundOrg2.getCity().equals("london")) {
                return ("found organisation did not have correct city");
            }
            
            OrganisationLocal foundOrg3 = organisationLocalHome.findByPrimaryKey(new Integer(orgKey3));
            if (!foundOrg3.getCountry().equals("GB")) {
                return ("found organisation did not have correct country");
            }
            return ("passed");
                    
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        catch (FinderException fe) {
            return (fe.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testFindByName() {
        try {
            int orgKey = getKeyCandidate();
            String orgName = "An Organisation to find by name";
            OrganisationLocal org1 = organisationLocalHome.create(orgKey, orgName );
            
            Collection foundOrgs = organisationLocalHome.findByOrgName(orgName);
            boolean foundOurOrg = false;
            Iterator it = foundOrgs.iterator();
            while (it.hasNext()) {
                OrganisationLocal foundOrg = (OrganisationLocal) it.next();
                if (!foundOrg.getName().equals(orgName)) {
                    return ("found an organisation with the wrong name");
                }
                else if (foundOrg.getOrganisationID().intValue() == orgKey) {
                    foundOurOrg = true;
                }
            }
            if (!foundOurOrg) {
                return ("failed to find specifically created organisation");
            }
            else {
                return ("passed");
            }
            
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        catch (FinderException fe) {
            return (fe.getMessage());
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testFindWithoutProductionRels() {
        try {
            // make an org with a production rel. this should not be found
            int orgKey1 = getKeyCandidate();
            OrganisationLocal org1 = organisationLocalHome.create(orgKey1, "org with prod rel");
            
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "editor");
            
            Set org1Rels = org1.getMediaProductionRelationships();
            org1Rels.add(rel);
            
            // make an org without a production rel. this should be found
            int orgKey2 = getKeyCandidate();
            OrganisationLocal org2 = organisationLocalHome.create(orgKey2, "org without prod rel");
            
            Collection foundOrgs = organisationLocalHome.findOrgWithoutProductionRels();
            boolean foundOurOrg = false;
            Iterator it = foundOrgs.iterator();
            while (it.hasNext()) {
                OrganisationLocal foundOrg = (OrganisationLocal) it.next();
                int foundKey = foundOrg.getOrganisationID().intValue();
                if (foundKey == orgKey1) {
                    return ("found an organisation with a production relationship when searching for all orgs without prod rel");
                }
                else if (foundKey == orgKey2) {
                    foundOurOrg = true;
                }
            }
            if (!foundOurOrg) {
                return ("failed to find specific organisation");
            }
            else {
                return ("passed");
            }
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        catch (FinderException fe) {
            return (fe.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testFindWithUnlinkedProductionRels() {
        try {
            // create an org with a mediaprod rel linked to a media item. this should not be found
            int orgKey1 = getKeyCandidate();
            OrganisationLocal org1 = organisationLocalHome.create(orgKey1, "org with a prod rel with a media item");
            
            int relKey1 =getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "org produced title", "book", 1987);
            
            Set org1Rels = org1.getMediaProductionRelationships();
            org1Rels.add(rel1);
            
            rel1.setMediaItem(item);
            
            // create an org with a media prod rel but where the media prod has no mediaItem associated
            int orgKey2 = getKeyCandidate();
            OrganisationLocal org2 = organisationLocalHome.create(orgKey2, "org without a prod rel with a media item");
            
            int relKey2 =getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            Set org2Rels = org2.getMediaProductionRelationships();
            org2Rels.add(rel2);
            
            // now lets have a look and see what we find
            Collection foundOrgs = organisationLocalHome.findOrgWithUnlinkedProductionRels();
            boolean foundOurOrg = false;
            Iterator it = foundOrgs.iterator();
            while (it.hasNext()) {
                OrganisationLocal foundOrg = (OrganisationLocal) it.next();
                int foundKey = foundOrg.getOrganisationID().intValue();
                if (foundKey == orgKey1) {
                    return ("found an organisation with a linked production relationship when searching for all orgs with unlinked prod rels");
                }
                else if (foundKey == orgKey2) {
                    foundOurOrg = true;
                }
            }
            if (!foundOurOrg) {
                return ("failed to find specific organisation");
            }
            else {
                return ("passed");
            }
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        catch (FinderException fe) {
            return (fe.getMessage());
        }
    }
    
    /** Creates a new instance of OrganisationTestFacadeBean */
    public OrganisationTestFacadeBean() {
        getLocalHomes();
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        ctx = sessionContext;
    }
}
