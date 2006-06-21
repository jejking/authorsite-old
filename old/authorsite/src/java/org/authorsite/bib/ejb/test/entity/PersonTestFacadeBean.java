/*
 * PersonTestFacadeBean.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/entity/PersonTestFacadeBean.java,v 1.4 2003/03/01 13:35:55 jejking Exp $
 * Created on 30 September 2002, 15:51
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
 * Test package for person EJB
 *
 * @ejb:bean    name="PersonTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/PersonTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.entity.PersonTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.entity.PersonTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
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
 * @ejb:transaction type="required"
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class PersonTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private PersonLocalHome personLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    private MediaItemLocalHome mediaItemLocalHome;
    
    public void ejbCreate() throws CreateException {
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyPersonEJB");
            personLocalHome = (PersonLocalHome) obj;
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
    public String testCreateAndFindPerson() {
        try {
            // we should exercise all the create methods on the PersonEJB
            int personKey1 = getKeyCandidate();
            PersonLocal person1 = personLocalHome.create(personKey1, "test main name");
            
            int personKey2 = getKeyCandidate();
            PersonLocal person2 = personLocalHome.create(personKey2, "secondTestMainName", "testGivenName");
            
            int personKey3 = getKeyCandidate();
            PersonLocal person3 = personLocalHome.create(personKey3, "thirdTestMainName", "secondTestGivenName", "test other names");
            
            int personKey4 = getKeyCandidate();
            PersonLocal person4 = personLocalHome.create(personKey4, "fourthTestMainName", "thirdTestGivenName", "second test other names", "test suffix");
            
            // now that it's all ok. Exercise each get methods as well as we go through the items created with more info
            PersonLocal foundPerson1 = personLocalHome.findByPrimaryKey(new Integer(personKey1));
            if (!foundPerson1.getMainName().equals("test main name")) {
                return ("found incorrect main name on person1");
            }
            
            PersonLocal foundPerson2 = personLocalHome.findByPrimaryKey(new Integer(personKey2));
            if (!foundPerson2.getGivenName().equals("testGivenName")) {
                return ("found incorrect given name on person2");
            }
                       
            PersonLocal foundPerson3 = personLocalHome.findByPrimaryKey(new Integer(personKey3));
            if (!foundPerson3.getOtherNames().equals("test other names")) {
                return ("found incorrect other names on person3");
            }
            
            PersonLocal foundPerson4 = personLocalHome.findByPrimaryKey(new Integer(personKey4));
            if (!foundPerson4.getSuffix().equals("test suffix")) {
                return ("found incorrect suffix on person4");
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
    
    public String testOtherSetters() throws EJBException {
        try {
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "testPerson");
            person.setPrefix("Dr");
            person.setGenderCode(1);
            
            PersonLocal foundPerson = personLocalHome.findByPrimaryKey(new Integer(personKey));
            if (!foundPerson.getPrefix().equals("Dr")) {
                return ("failed to correctly set prefix");
            }
            if (foundPerson.getGenderCode() != 1) {
                return ("failed to correctly set genderCode");
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
    public String testFindByMainName() throws EJBException {
        try {
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "testMainName");
            
            boolean foundRightPerson = false;
            Collection persons = personLocalHome.findByMainName("testMainName");
            Iterator it = persons.iterator();
            while (it.hasNext()) {
                PersonLocal foundPerson = (PersonLocal) it.next();
                if (foundPerson.getPersonID().intValue() == personKey) {
                    foundRightPerson = true;
                }
                if (!foundPerson.getMainName().equals("testMainName")) {
                    return ("found incorrect main name whilst searching for \"testMainName\"");
                }
            }
            if (!foundRightPerson) {
                return ("failed to find correct person whilst searching for \"testMainName\"");
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
    public String testFindByMainNameAndGivenName() throws EJBException {
        try {
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "testMainName2", "testGivenName");
            
            Collection persons = personLocalHome.findByMainNameAndGivenName("testMainName2", "testGivenName");
            boolean foundRightPerson = false;
            Iterator it = persons.iterator();
            while (it.hasNext()) {
                PersonLocal foundPerson = (PersonLocal) it.next();
                if (foundPerson.getPersonID().intValue() == personKey) {
                    foundRightPerson = true;
                }
                if (!foundPerson.getMainName().equals("testMainName2") &&  !foundPerson.getGivenName().equals("testGivenName")) {
                    return ("found person with wrong main name or given name");
                }
            }
            if (!foundRightPerson) {
                return ("failed to find correct person whilst searching for person using specified main name and given name");
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
            // make an person with a production rel. this should not be found
            int personKey1 = getKeyCandidate();
            PersonLocal person1 = personLocalHome.create(personKey1, "person with prod rel");
            
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "editor");
            
            Set person1Rels = person1.getMediaProductionRelationships();
            person1Rels.add(rel);
            
            // make an person without a production rel. this should be found
            int personKey2 = getKeyCandidate();
            PersonLocal person2 = personLocalHome.create(personKey2, "person without prod rel");
            
            Collection foundPeople = personLocalHome.findWithoutProductionRels();
            boolean foundOurPerson = false;
            Iterator it = foundPeople.iterator();
            while (it.hasNext()) {
                PersonLocal foundPerson = (PersonLocal) it.next();
                int foundKey = foundPerson.getPersonID().intValue();
                if (foundKey == personKey1) {
                    return ("found a person with a production relationship when searching for all persons without prod rel");
                }
                else if (foundKey == personKey2) {
                    foundOurPerson = true;
                }
            }
            if (!foundOurPerson) {
                return ("failed to find specific person");
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
            // create an person with a mediaprod rel linked to a media item. this should not be found
            int personKey1 = getKeyCandidate();
            PersonLocal person1 = personLocalHome.create(personKey1, "person with a prod rel with a media item");
            
            int relKey1 =getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "person produced title", "book", 1987);
            
            Set person1Rels = person1.getMediaProductionRelationships();
            person1Rels.add(rel1);
            
            rel1.setMediaItem(item);
            
            // create an person with a media prod rel but where the media prod has no mediaItem associated
            int personKey2 = getKeyCandidate();
            PersonLocal person2 = personLocalHome.create(personKey2, "person without a prod rel with a media item");
            
            int relKey2 =getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            Set person2Rels = person2.getMediaProductionRelationships();
            person2Rels.add(rel2);
            
            // now lets have a look and see what we find
            Collection foundPeople = personLocalHome.findWithUnlinkedProductionRels();
            boolean foundOurPerson = false;
            Iterator it = foundPeople.iterator();
            while (it.hasNext()) {
                PersonLocal foundPerson = (PersonLocal) it.next();
                int foundKey = foundPerson.getPersonID().intValue();
                if (foundKey == personKey1) {
                    return ("found a person with a linked production relationship when searching for all persons with unlinked prod rels");
                }
                else if (foundKey == personKey2) {
                    foundOurPerson = true;
                }
            }
            if (!foundOurPerson) {
                return ("failed to find specific person");
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
        
    /** Creates a new instance of personTestFacadeBean */
    public PersonTestFacadeBean() {
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
