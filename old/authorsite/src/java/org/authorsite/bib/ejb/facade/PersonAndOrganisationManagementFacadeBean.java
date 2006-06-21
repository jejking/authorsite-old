/*
 * PersonAndOrganisationManagementFacadeBean.java
 *
 * Created on 09 December 2002, 10:28
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/facade/PersonAndOrganisationManagementFacadeBean.java,v 1.5 2003/03/01 13:36:45 jejking Exp $
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
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.entity.*;
import org.authorsite.bib.ejb.services.database.*;
import org.authorsite.bib.ejb.services.dto.*;
import org.authorsite.bib.exceptions.*;
/**
 * @ejb.bean    name="PersonAndOrganisationManagementFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/PersonAndOrganisationManagementFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.facade.PersonAndOrganisationManagementFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.facade.PersonAndOrganisationManagementFacadeHome"
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
 * @ejb.ejb-ref ejb-name="SequenceBlockPrimaryKeyGeneratorEJB"
 *              ref-name="ejb/MySequenceBlockPrimaryKeyGeneratorEJB"
 *              view-type="local"
 *
 * @ejb.transaction type="RequiresNew"
 *
 * @author  jejking
 * @version $Revision: 1.5 $
 */
public class PersonAndOrganisationManagementFacadeBean implements SessionBean {
    
    private SessionContext sessionCtx;
    private InitialContext ctx;
    private PersonLocalHome personLocalHome;
    private OrganisationLocalHome organisationLocalHome;
    private SequenceBlockPrimaryKeyGeneratorLocalHome sequenceBlockPrimaryKeyGeneratorLocalHome;
    
    private void getLocalHomes() {
        try {
            ctx = new InitialContext();
            personLocalHome = (PersonLocalHome) ctx.lookup("java:comp/env/ejb/MyPersonEJB");
            organisationLocalHome = (OrganisationLocalHome) ctx.lookup("java:comp/env/ejb/MyOrganisationEJB");
            sequenceBlockPrimaryKeyGeneratorLocalHome = (SequenceBlockPrimaryKeyGeneratorLocalHome) ctx.lookup("java:comp/env/ejb/MySequenceBlockPrimaryKeyGeneratorEJB");
        }
        catch (NamingException ne) {
            // arrghhh! If this happens, we're a tad stitched ;-)
            ne.printStackTrace();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public PersonDTO findPersonByPrimaryKey(int pk) throws FinderException {
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(pk));
        PersonDTOAssembler assembler = new PersonDTOAssembler(person);
        return assembler.assembleDTO();
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public OrganisationDTO findOrganisationByPrimaryKey(int pk) throws FinderException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(pk));
        OrganisationDTOAssembler assembler = new OrganisationDTOAssembler(org);
        return assembler.assembleDTO();
    }
    
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createPerson(String newMainName) throws CreateException {
        // get PK
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Person");
        PersonLocal newPerson = personLocalHome.create(pk, newMainName);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createPerson(String newMainName, String newGivenName) throws CreateException {
        // get PK
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Person");
        PersonLocal newPerson = personLocalHome.create(pk, newMainName, newGivenName);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createPerson(String newMainName, String newGivenName, String newOtherNames) throws CreateException {
        // get PK
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Person");
        PersonLocal newPerson = personLocalHome.create(pk, newMainName, newGivenName, newOtherNames);
        return pk;
    }
    
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createPerson(String newMainName, String newGivenName, String newOtherNames, String newSuffix) throws CreateException {
        // get PK
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Person");
        PersonLocal newPerson = personLocalHome.create(pk, newMainName, newGivenName, newOtherNames, newSuffix);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createPerson(String newMainName, String newGivenName, String newOtherNames, String newPrefix, String newSuffix, int genderCode) throws CreateException {
        // get PK
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Person");
        PersonLocal newPerson = personLocalHome.create(pk, newMainName, newGivenName, newOtherNames, newSuffix);
        newPerson.setPrefix(newPrefix);
        if (genderCode == 1 || genderCode == 2 || genderCode == 9) {
             newPerson.setGenderCode(genderCode);
         }
         else {
            newPerson.setGenderCode(0);
         }
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removePerson(int personPK) throws FinderException, RemoveException {
        // person may only be deleted if no longer participating in any media production relationships
        // find person
        PersonLocal person = personLocalHome.findByPrimaryKey(new Integer(personPK));
        if (person.getMediaProductionRelationships().size() > 0) {
            throw new RemoveException("bib.error.personStillInRelationship");
        }
        else {
            person.remove();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
     public void editPerson(PersonDTO personDTO) throws FinderException, InsufficientDetailException {
         // find relevant personLocal entity
         PersonLocal person = personLocalHome.findByPrimaryKey(personDTO.getID());
         // we must define at least the MainName
         if (personDTO.getMainName() == null || personDTO.getMainName().equals("")) {
             throw new InsufficientDetailException("bib.error.insufficientdetail.person.MainName");
         }
         else {
             person.setMainName(personDTO.getMainName());
         }
         // go through the rest of DTO and call setters if we don't have nulls or disallowed values
         if (personDTO.getPrefix() != null) {
             person.setPrefix(personDTO.getPrefix());
         }
         if (personDTO.getGivenName() != null) {
             person.setGivenName(personDTO.getGivenName());
         }
         if (personDTO.getOtherNames() != null) {
             person.setOtherNames(personDTO.getOtherNames());
         }
         if (personDTO.getSuffix() != null) {
             person.setSuffix(personDTO.getSuffix());
         }
         /* Gender Code may only have one of four permitted values:
          * - 0 = Not Known (DEFAULT)
          * - 1 = Male
          * - 2 = Female
          * - 9 = Not Specified
          * This constraint is also enforced in the database.
          */
         if (personDTO.getGenderCode() == 1 || personDTO.getGenderCode() == 2 || personDTO.getGenderCode() == 9) {
             person.setGenderCode(personDTO.getGenderCode());
         }
         else {
            person.setGenderCode(0);
         }
     }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createOrganisation(String newName) throws CreateException {
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Organisation");
        OrganisationLocal newOrg = organisationLocalHome.create(pk, newName);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createOrganisation(String newName, String newCity) throws CreateException {
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Organisation");
        OrganisationLocal newOrg = organisationLocalHome.create(pk, newName, newCity);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public int createOrganisation(String newName, String newCity, String newCountry) throws CreateException {
        SequenceBlockPrimaryKeyGeneratorLocal pkGen = sequenceBlockPrimaryKeyGeneratorLocalHome.create();
        int pk = pkGen.getNextSequenceNumber("Organisation");
        OrganisationLocal newOrg = organisationLocalHome.create(pk, newName, newCity, newCountry);
        return pk;
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void removeOrganisation(int orgPK) throws FinderException, RemoveException {
        // person may only be deleted if no longer participating in any media production relationships
        // find organisation
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(new Integer(orgPK));
        if (org.getMediaProductionRelationships().size() > 0) {
            throw new RemoveException("bib.error.orgStillInRelationship");
        }
        else {
            org.remove();
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public void editOrganisation(OrganisationDTO orgDTO) throws FinderException, InsufficientDetailException {
        OrganisationLocal org = organisationLocalHome.findByPrimaryKey(orgDTO.getID());
        // org name cannot be null
        if (orgDTO.getName() == null || orgDTO.getName().equals("")) {
            throw new InsufficientDetailException("Organisation name may not be null");
        }
        else {
            org.setName(orgDTO.getName());
        }
        // if the other values aren't null, set them in the local interface
        if (orgDTO.getCity() != null) {
            org.setCity(orgDTO.getCity());
        }
        if (orgDTO.getCountry() != null) {
            org.setCountry(orgDTO.getCountry());
        }
    }
    
    /** Creates a new instance of PersonAndOrganisationManagementFacadeBean */
    public PersonAndOrganisationManagementFacadeBean() {
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
