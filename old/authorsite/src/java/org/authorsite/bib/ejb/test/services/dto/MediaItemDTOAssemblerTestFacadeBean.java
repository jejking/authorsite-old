/*
 * MediaItemDTOAssemblerTestFacadeBean.java
 *
 * Created on 22 November 2002, 14:41
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/services/dto/MediaItemDTOAssemblerTestFacadeBean.java,v 1.4 2003/03/01 13:35:37 jejking Exp $
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

package org.authorsite.bib.ejb.test.services.dto;
import java.util.*;
import javax.ejb.*;
import javax.naming.*;
import org.authorsite.bib.dto.*;
import org.authorsite.bib.ejb.services.dto.*;
import org.authorsite.bib.ejb.entity.*;
/**
 * @ejb:bean    name="MediaItemDTOAssemblerTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/MediaItemDTOAssemblerTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.services.dto.MediaItemDTOAssemblerTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.services.dto.MediaItemDTOAssemblerTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="OrganisationEJB"
 *              ref-name="ejb/MyOrganisationEJB"
 *              view-type="local"  
 *
 * @ejb:ejb-ref ejb-name="PersonEJB"
 *              ref-name="ejb/MyPersonEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="MediaProductionRelationshipEJB"
 *              ref-name="ejb/MyMediaProductionRelationshipEJB"
 *              view-type="local" 
 * 
 * @ejb.ejb-ref ejb-name="MediaItemEJB"
 *              ref-name="ejb/MyMediaItemEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="LanguageEJB"
 *              ref-name="ejb/MyLanguageEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="IntraMediaRelationshipEJB"
 *              ref-name="ejb/MyIntraMediaRelationshipEJB"
 *              view-type="local"
 *
 * @ejb.ejb-ref ejb-name="BookDetailsEJB"
 *              ref-name="ejb/MyBookDetailsEJB"
 *              view-type="local"
 *
 *
 * @author  jejking
 * @version $Revision: 1.4 $
 */
public class MediaItemDTOAssemblerTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private OrganisationLocalHome organisationLocalHome;
    private MediaItemLocalHome mediaItemLocalHome;
    private PersonLocalHome personLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    private LanguageLocalHome languageLocalHome;
    private IntraMediaRelationshipLocalHome intraMediaRelationshipLocalHome;
    private BookDetailsLocalHome bookDetailsLocalHome;
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testSimpleMediaItemDTOAssembly() {
        try {
            // lets make a book with an author and a publisher in English
            // make a person who'll be the author
            PersonLocal authorPerson = personLocalHome.create(getKeyCandidate(), "dtoTestAuthor");
            // make a productionRelationship called author and attach the person to it
            MediaProductionRelationshipLocal authorProdRel = mediaProductionRelationshipLocalHome.create(getKeyCandidate(), "author");
            Set authorPeople = authorProdRel.getPeople();
            authorPeople.add(authorPerson);
            
            // and an organisation who'll be the publisher
            OrganisationLocal pubOrg = organisationLocalHome.create(getKeyCandidate(), "dtoTestPublisherOrg");
            MediaProductionRelationshipLocal pubProdRel = mediaProductionRelationshipLocalHome.create(getKeyCandidate(), "publisher");
            Set pubOrgs = pubProdRel.getOrganisations();
            pubOrgs.add(pubOrg);
            
            // create the media item. a book called "testTitle" written in 1999
            MediaItemLocal mediaItem = mediaItemLocalHome.create(getKeyCandidate(), "testTitle", "book", 1999);
            
            // it's written in english
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            Set langs = mediaItem.getLanguages();
            langs.add(english);
            
            // now associate it with the author and publisher prodRels
            Set prodRels = mediaItem.getMediaProductionRelationships();
            prodRels.add(authorProdRel);
            prodRels.add(pubProdRel);
            
            // now, we have constructed our simple mediaItem. Let's get the DTO for it
            MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(mediaItem);
            MediaItemDTO dto = assembler.assembleDTO();
            
            // now we'd better check the dto has got everything in it correctly or else this isn't much of a test
            // check the base data
            if (!dto.getID().equals(mediaItem.getMediaItemID())) {
                return ("dto has wrong ID");
            }
            if (!dto.getTitle().equals("testTitle")) {
                return ("dto's tile is not equal to \"testTitle\"");
            }
            if (!dto.getMediaType().equals("book")) {
                return ("dto's mediaType is not equal to \"book\"");
            }
            if (dto.getYearOfCreation() != 1999) {
                return ("dto's yearOfCreation is not equal to 1999");
            }
            
            // check the language
            Set dtoLangs = dto.getLanguages();
            Iterator dtoLangsIt = dtoLangs.iterator();
            while (dtoLangsIt.hasNext()) {
                LanguageDTO lang = (LanguageDTO) dtoLangsIt.next();
                if (!lang.getIso639().equals("eng")) {
                    return ("dto is not in english");
                }
            }
            
            // check the production relationships
            Set dtoProdRels = dto.getMediaProductionRelationships();
            boolean foundCorrectAuthor = false;
            boolean foundCorrectPublisher = true;
            Iterator dtoProdRelsIt = dtoProdRels.iterator(); // hm, we should probably use a HashMap instead for easier access to specific prodRels
            while (dtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRel = (MediaProductionRelationshipDTO) dtoProdRelsIt.next();
                if (prodRel.getRelationshipType().equals("author")) {
                    // validate author
                    Set dtoAuthorPeople = prodRel.getPeople();
                    Iterator dtoAuthorPeopleIt = dtoAuthorPeople.iterator();
                    while (dtoAuthorPeopleIt.hasNext()) {
                        PersonDTO authorDTO = (PersonDTO) dtoAuthorPeopleIt.next();
                        if (!authorDTO.getMainName().equals("dtoTestAuthor")) {
                            return ("dto did not contain correct author");
                        }
                    }
                }
                else {
                    // validate publisher
                    Set dtoPublisherOrgs = prodRel.getOrganisations();
                    Iterator dtoPublisherOrgsIt = dtoPublisherOrgs.iterator();
                    while (dtoPublisherOrgsIt.hasNext()) {
                        OrganisationDTO publisherDTO = (OrganisationDTO) dtoPublisherOrgsIt.next();
                        if (!publisherDTO.getName().equals("dtoTestPublisherOrg")) {
                            return ("dto did not contain correct publisher");
                        }
                    }
                }
            }
            
            // just make sure we aren't contained in anything
            if (dto.getContainedIn() != null) {
                return ("the dto believes the mediaItem is contained in another one");
            }
            
            // ok, by now we've checked the basic data, the author, the publisher and the language. it should be ok
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
     * Tests assembly of a DTO representing one mediaItem contained in another.
     * @ejb.interface-method view-type="remote"
     */
    public String testContainedMediaItemDTOAssembly() {
        try {
            // we'll test with a bookArticle contained in a book
            
            // create the bookArticle first. Its author is "BookArticleAuthor"
            PersonLocal bookArticleAuthor = personLocalHome.create(getKeyCandidate(), "bookArticleAuthor");
            MediaProductionRelationshipLocal bookArticleAuthorRel = mediaProductionRelationshipLocalHome.create(getKeyCandidate(), "author");
            MediaItemLocal bookArticle = mediaItemLocalHome.create(getKeyCandidate(), "a book article", "bookArticle", 2002);
            Set bookArticleProdRels = bookArticle.getMediaProductionRelationships();
            bookArticleProdRels.add(bookArticleAuthorRel);
            
            // now create the book it is contained in. Its editor is "BookEditor". (we'll leave out publisher cos I'm feeling lazy)
            PersonLocal bookEditor = personLocalHome.create(getKeyCandidate(), "bookEditor");
            MediaProductionRelationshipLocal bookEditorRel = mediaProductionRelationshipLocalHome.create(getKeyCandidate(), "editor");
            MediaItemLocal book = mediaItemLocalHome.create(getKeyCandidate(), "a book with an article", "book", 2002);
            Set bookProdRels = book.getMediaProductionRelationships();
            bookProdRels.add(bookEditorRel);
            
            // now link them together, so that there is a containment relationship from the book to the bookArticle
            IntraMediaRelationshipLocal imr = intraMediaRelationshipLocalHome.create(getKeyCandidate(), bookArticle.getMediaItemID().intValue(),
                        "containment", book.getMediaItemID().intValue());
            
            // we now have a bookArticle contained in a book. Lets build the dto for the bookArticle
            MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(bookArticle);
            MediaItemDTO dto = assembler.assembleDTO();
            
            // we'll assume the bottom level of the dot has been built correctly as per the simple test
            // and look at the container here
            
            
            MediaItemDTO containerDto = dto.getContainedIn();
            if (containerDto == null) {
                return ("containerDTo is null");
            }
            if (containerDto.getID().intValue() != book.getMediaItemID().intValue()) {
                return ("containerDto has wrong id");
            }
            if (!containerDto.getMediaType().equals("book")) {
                return ("containerDto was of wrong mediaType");
            }
            if (!containerDto.getTitle().equals("a book with an article")) {
                return ("containerDto has wrong title");
            }
            if (containerDto.getYearOfCreation() != 2002) {
                return ("containerDto has wrong year of creation");
            }
            // now check that we've picked up the book's editor correctly
            Set containerDtoProdRels = containerDto.getMediaProductionRelationships();
            if (containerDtoProdRels.size() != 1) {
                return ("containerDto has wrong number of mediaProductionRelationships");
            }
            Iterator containerDtoProdRelsIt = containerDtoProdRels.iterator();
            while (containerDtoProdRelsIt.hasNext()) {
                MediaProductionRelationshipDTO prodRel = (MediaProductionRelationshipDTO) containerDtoProdRelsIt.next();
                if (!prodRel.getRelationshipType().equals("editor")) {
                    return ("container dto contained the wrong sort of production relationship");
                }
                Set prodRelPeople = prodRel.getPeople();
                Iterator prodRelPeopleIt = prodRelPeople.iterator();
                while (prodRelPeopleIt.hasNext()) {
                    PersonDTO person = (PersonDTO) prodRelPeopleIt.next();
                    if (person.getID().intValue() != bookEditor.getPersonID().intValue()) {
                        return ("container dto contained the wrong person");
                    }
                }
            }
            // should be ok now.
            
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        catch (NullPointerException npe) {
            return ("caught a server side null pointer " + npe.getMessage());
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testSimpleMediaItemDTOAssemblyWithDetails() {
        try {
            // we'll make a book in English with an author
            PersonLocal author = personLocalHome.create(getKeyCandidate(), "complexBookAuthor");
            MediaProductionRelationshipLocal authorProdRel = mediaProductionRelationshipLocalHome.create(getKeyCandidate(), "author");
            Set authorProdRelPeople = authorProdRel.getPeople();
            authorProdRelPeople.add(author);
            MediaItemLocal book = mediaItemLocalHome.create(getKeyCandidate(), "book with details", "book", 2002);
            Set bookProdRels = book.getMediaProductionRelationships();
            bookProdRels.add(authorProdRel);
            
            /*
             * NOTE - THIS MUST BE KEPT IN SYNC WITH ANY CHANGES IN bibTypesRelationships.xml
             */
            // now make the corresponding bookDetails item
            BookDetailsLocal bookDetailsLocal = bookDetailsLocalHome.create(book.getMediaItemID().intValue());
            bookDetailsLocal.setNumberOfPages("500");
            bookDetailsLocal.setAccompanyingMaterial("A large CD");
            
            // we now have a bookArticle contained in a book. Lets build the dto for the bookArticle
            MediaItemDTOAssembler assembler = new MediaItemDTOAssembler(book);
            MediaItemDTO dto = assembler.assembleDTO();
            
            // for the purpose of the test, we're really interested in getting the DetailsDTO
            MediaItemDetailsDTO detailsDTO = dto.getDetailsDTO();
            
            BookDetailsDTO bookDetailsDTO = (BookDetailsDTO) detailsDTO;
            if (!bookDetailsDTO.getNumberOfPages().equals("500")) {
                return ("bookDetailsDTO is not 500");
            }
            if (!bookDetailsDTO.getAccompanyingMaterial().equals("A large CD")) {
                return ("bookDetailsDTO does not specify \"A large CD\" as accompanying material");
            }
            
            // lets also look in the HashMap
            HashMap bookDetailsMap = detailsDTO.getFieldsMap();
            String numberOfPages = (String) bookDetailsMap.get("numberOfPages");
            if (!numberOfPages.equals("500")) {
                return ("fieldsMap did not contain correct value of 500 for numberOfPages");
            }
            String accompanyingMaterial = (String) bookDetailsMap.get("accompanyingMaterial");
            if (!accompanyingMaterial.equals("A large CD")) {
                return ("fieldsMap did not contain correct value of \"A large CD\" for accompanyingMaterial");
            }
            
            return ("passed");
            
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    
    /** Creates a new instance of MediaItemDTOAssemblerTestFacadeBean */
    public MediaItemDTOAssemblerTestFacadeBean() {
        getLocalHomes();
    }
    
    private int getKeyCandidate() {
        Long keyLong = new Long(System.currentTimeMillis());
        return keyLong.intValue();
    }
    
    private void getLocalHomes() throws EJBException {
        try {
            Context context = new InitialContext();
            Object obj = context.lookup("java:comp/env/ejb/MyOrganisationEJB");
            organisationLocalHome = (OrganisationLocalHome) obj;
            
            Object obj2 = context.lookup("java:comp/env/ejb/MyPersonEJB");
            personLocalHome = (PersonLocalHome) obj2;
            
            Object obj3 = context.lookup("java:comp/env/ejb/MyMediaProductionRelationshipEJB");
            mediaProductionRelationshipLocalHome = (MediaProductionRelationshipLocalHome) obj3;
            
            Object obj4 = context.lookup("java:comp/env/ejb/MyMediaItemEJB");
            mediaItemLocalHome = (MediaItemLocalHome) obj4;
            
            Object obj5 = context.lookup("java:comp/env/ejb/MyLanguageEJB");
            languageLocalHome = (LanguageLocalHome) obj5;
            
            Object obj6 = context.lookup("java:comp/env/ejb/MyIntraMediaRelationshipEJB");
            intraMediaRelationshipLocalHome = (IntraMediaRelationshipLocalHome) obj6;
            
            Object obj7 = context.lookup("java:comp/env/ejb/MyBookDetailsEJB");
            bookDetailsLocalHome = (BookDetailsLocalHome) obj7;
        }
        catch (NamingException ne) {
            throw new EJBException(ne.getMessage());
        }
        catch (ClassCastException cce) {
            throw new EJBException(cce.getMessage());
        }
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
        ctx = sessionContext;
    }
    
}
