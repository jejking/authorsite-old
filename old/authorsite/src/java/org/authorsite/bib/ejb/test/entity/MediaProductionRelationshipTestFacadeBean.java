/*
 * MediaProductionRelationshipTestFacade.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/entity/MediaProductionRelationshipTestFacadeBean.java,v 1.4 2003/03/01 13:35:55 jejking Exp $
 *
 * Created on 02 October 2002, 12:32
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
 *
 */

package org.authorsite.bib.ejb.test.entity;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;

import org.authorsite.bib.ejb.entity.*;
/**
 * Provides a series of test cases for the MediaProductionRelationship EJB.
 *
 * @ejb:bean    name="MediaProductionRelationshipTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/MediaProductionRelationshipTestFacadeEJB"
 *              view-type="remote"
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.entity.MediaProductionRelationshipTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.entity.MediaProductionRelationshipTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="MediaItemEJB"
 *              ref-name="ejb/MyMediaItemEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="MediaProductionRelationshipEJB"
 *              ref-name="ejb/MyMediaProductionRelationshipEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"
 *
 * @ejb:transaction type="required"
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class MediaProductionRelationshipTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private MediaItemLocalHome mediaItemLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    private PersonLocalHome personLocalHome;
    private OrganisationLocalHome organisationLocalHome;
    
    public void ejbCreate() throws CreateException {
    }
    
    private int getKeyCandidate() {
        Long keyLong = new Long(System.currentTimeMillis());
        return keyLong.intValue();
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyMediaItemEJB");
            mediaItemLocalHome = (MediaItemLocalHome) obj;
            Object obj2 = context.lookup("java:comp/env/ejb/MyMediaProductionRelationshipEJB");
            mediaProductionRelationshipLocalHome = (MediaProductionRelationshipLocalHome) obj2;
            Object obj3 = context.lookup("java:comp/env/ejb/MyPersonEJB");
            personLocalHome = (PersonLocalHome) obj3;
            Object obj4 = context.lookup("java:comp/env/ejb/MyOrganisationEJB");
            organisationLocalHome = (OrganisationLocalHome) obj4;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testCreateAndFind() throws EJBException {
        try {
            int key = getKeyCandidate();
            MediaProductionRelationshipLocal prodRel = mediaProductionRelationshipLocalHome.create(key, "author");
            
            if (prodRel == null) {
                return ("created object was null");
            }
            if (!prodRel.getRelationshipType().equals("author")) {
                return ("created object had incorrect relationship type");
            }
            
            MediaProductionRelationshipLocal foundRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(key));
            if (foundRel == null) {
                return ("found object was null");
            }
            // that should probably be enough little tests
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
    public String testAddProdRelToMediaItem() {
        try {
            // we must create a test media item, then two production relationships.
            // we will then add these production relationships to the item and check
            // they are returned when we call getMediaProductionRelationships on the mediaitem
            
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "relationships test", "book", 2002);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "editor");
            
            Set itemsProdRels = item.getMediaProductionRelationships();
            
            // now, add the production relationships
            itemsProdRels.add(rel1);
            itemsProdRels.add(rel2);
            
            // now lets go look for them
            MediaItemLocal foundItem = mediaItemLocalHome.findByPrimaryKey(new Integer(itemKey));
            Set foundProdRels = foundItem.getMediaProductionRelationships();
            
            boolean foundRel1 = false;
            boolean foundRel2 = false;
            Iterator it = foundProdRels.iterator();
            while (it.hasNext()) {
                MediaProductionRelationshipLocal foundRel = (MediaProductionRelationshipLocal) it.next();
                int foundKey = foundRel.getMediaProductionRelationshipID().intValue();
                if (foundKey != relKey1 && foundKey != relKey2) {
                    return ("found an incorrect mediaProductionRelationship");
                }
                if (foundKey == relKey1) {
                    foundRel1 = true;
                }
                else if (foundKey == relKey2) {
                    foundRel2 = true;
                }
            }
            if (!foundRel1) {
                return ("failed to find first relationship");
            }
            if (!foundRel2) {
                return ("failed to find second relationship");
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
    public String testAddMediaItemToRel() {
        try {
            // create a relationship, and then add a media item to it to show
            // the CMR is working both ways
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "author");
            
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "an item with an author production relationship", "book", 2002);
            
            rel.setMediaItem(item);
            
            // now lets do some looking up
            MediaProductionRelationshipLocal foundRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(relKey));
            MediaItemLocal foundItem = foundRel.getMediaItem();
            if (foundItem.getMediaItemID().equals(item.getMediaItemID())) {
                return("passed");
            }
            else {
                return ("retrieved media item was not identical to media item assigned to relationship");
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
    public String testAddPeopleToRelationship() {
        try {
            // create a relationship. Create two people. Add the people to the relationship.
            // See if you get them back.
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "author");
            
            int personKey1 = getKeyCandidate();
            PersonLocal person1 = personLocalHome.create(personKey1, "AuthorName1");
            
            int personKey2 = getKeyCandidate();
            PersonLocal person2 = personLocalHome.create(personKey2, "AuthorName2");
            
            Set relPeople = rel.getPeople();
            relPeople.add(person1);
            relPeople.add(person2);
            
            // now find that relationship, look up its people
            MediaProductionRelationshipLocal foundRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(relKey));
            boolean foundPerson1 = false;
            boolean foundPerson2 = false;
            
            Set foundPeople = foundRel.getPeople();
            Iterator it = foundPeople.iterator();
            while (it.hasNext()) {
                PersonLocal foundPerson = (PersonLocal) it.next();
                int foundKey = foundPerson.getPersonID().intValue();
                if (foundKey != personKey1 && foundKey != personKey2) {
                    return ("wrong person was retrieved from relationship");
                }
                else if (foundKey == personKey1) {
                    foundPerson1 = true;
                }
                else if (foundKey == personKey2) {
                    foundPerson2 = true;
                }
            }
            if (!foundPerson1) {
                return ("did not find person one");
            }
            else if (!foundPerson2) {
                return ("did not find person two");
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
    public String testAddProductionRelationshipsToPeople() {
        try {
            // create a person
            // create two productionrelationships.
            // add the productionRelationships to the person
            // see if we can retrieve them correctly
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "PersonWithTwoProductionRelationships");
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            Set personRels = person.getMediaProductionRelationships();
            personRels.add(rel1);
            personRels.add(rel2);
            
            PersonLocal foundPerson = personLocalHome.findByPrimaryKey(new Integer(personKey));
            Set foundRels = foundPerson.getMediaProductionRelationships();
            boolean foundRel1 = false;
            boolean foundRel2 = false;
            
            Iterator it = foundRels.iterator();
            while (it.hasNext()) {
                MediaProductionRelationshipLocal foundRel = (MediaProductionRelationshipLocal) it.next();
                int foundRelKey = foundRel.getMediaProductionRelationshipID().intValue();
                if (foundRelKey != relKey1 && foundRelKey != relKey2) {
                    return ("found an incorrect relationship for person");
                }
                else if (foundRelKey == relKey1) {
                    foundRel1 = true;
                }
                else if (foundRelKey == relKey2) {
                    foundRel2 = true;
                }
            }
            if (!foundRel1) {
                return ("failed to find first relationship for person");
            }
            else if (!foundRel2) {
                return ("failed to find second relationship for person");
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
    public String testAddOrganisationsToRelationship() {
        try {
            // create a relationship. Create two organisations. Add the organisations to the relationship.
            // See if you get them back.
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "author");
            
            int orgKey1 = getKeyCandidate();
            OrganisationLocal org1 = organisationLocalHome.create(orgKey1, "Organisation One");
            
            int orgKey2 = getKeyCandidate();
            OrganisationLocal org2 = organisationLocalHome.create(orgKey2, "Organisation Two");
            
            Set relOrgs = rel.getOrganisations();
            relOrgs.add(org1);
            relOrgs.add(org2);
            
            // now find that relationship, look up its people
            MediaProductionRelationshipLocal foundRel = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(relKey));
            boolean foundOrg1 = false;
            boolean foundOrg2 = false;
            
            Set foundOrgs = foundRel.getOrganisations();
            Iterator it = foundOrgs.iterator();
            while (it.hasNext()) {
                OrganisationLocal foundOrg = (OrganisationLocal) it.next();
                int foundKey = foundOrg.getOrganisationID().intValue();
                if (foundKey != orgKey1 && foundKey != orgKey2) {
                    return ("wrong organisation was retrieved from relationship");
                }
                else if (foundKey == orgKey1) {
                    foundOrg1 = true;
                }
                else if (foundKey == orgKey2) {
                    foundOrg2 = true;
                }
            }
            if (!foundOrg1) {
                return ("did not find organisation one");
            }
            else if (!foundOrg2) {
                return ("did not find organisation two");
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
    public String testAddProductionRelationshipsToOrganisation() {
        try {
            // create an organisation
            // create two productionrelationships.
            // add the productionRelationships to the organisation
            // see if we can retrieve them correctly
            
            int orgKey = getKeyCandidate();
            OrganisationLocal org = organisationLocalHome.create(orgKey, "PersonWithTwoProductionRelationships");
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            Set orgRels = org.getMediaProductionRelationships();
            orgRels.add(rel1);
            orgRels.add(rel2);
            
            OrganisationLocal foundOrg = organisationLocalHome.findByPrimaryKey(new Integer(orgKey));
            Set foundRels = foundOrg.getMediaProductionRelationships();
            boolean foundRel1 = false;
            boolean foundRel2 = false;
            
            Iterator it = foundRels.iterator();
            while (it.hasNext()) {
                MediaProductionRelationshipLocal foundRel = (MediaProductionRelationshipLocal) it.next();
                int foundRelKey = foundRel.getMediaProductionRelationshipID().intValue();
                if (foundRelKey != relKey1 && foundRelKey != relKey2) {
                    return ("found an incorrect relationship for organisation");
                }
                else if (foundRelKey == relKey1) {
                    foundRel1 = true;
                }
                else if (foundRelKey == relKey2) {
                    foundRel2 = true;
                }
            }
            if (!foundRel1) {
                return ("failed to find first relationship for organisation");
            }
            else if (!foundRel2) {
                return ("failed to find second relationship for organisation");
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
    
    /** Creates a new instance of MediaProductionRelationshipTestFacade */
    public MediaProductionRelationshipTestFacadeBean() {
        getLocalHomes();
    }
    
    public void ejbActivate() throws javax.ejb.EJBException {
        getLocalHomes();
    }
    
    public void ejbPassivate() throws javax.ejb.EJBException {
        mediaItemLocalHome = null;
    }
    
    public void ejbRemove() throws javax.ejb.EJBException {
        mediaItemLocalHome = null;
    }
    
    public void setSessionContext(javax.ejb.SessionContext sessionContext) throws javax.ejb.EJBException {
        ctx = sessionContext;
    }
}
