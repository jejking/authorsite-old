/*
 * MediaItemPersonFindersTestFacade.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/entity/MediaItemPersonFindersTestFacadeBean.java,v 1.7 2003/03/01 13:35:55 jejking Exp $
 *
 * Created on 09 October 2002, 09:36
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
 * Test facade that exercises the person related finder methods on the MediaItemLocalHome
 * interface.
 * @ejb:bean    name="MediaItemPersonFindersTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/MediaItemPersonFindersTestFacadeEJB"
 *              view-type="remote"
 *
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.entity.MediaItemPersonFindersTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.entity.MediaItemPersonFindersTestFacadeHome"
 *                  generate="remote"
 *
 * @ejb:ejb-ref ejb-name="MediaItemEJB"
 *              ref-name="ejb/MyMediaItemEJB"
 *              view-type="local"
 *
 * @ejb:ejb-ref ejb-name="LanguageEJB"
 *              ref-name="ejb/MyLanguageEJB"
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
 *
 * @ejb:transaction type="required"
 *
 * @author  jejking
 * @version $Revision: 1.7 $
 */
public class MediaItemPersonFindersTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private MediaItemLocalHome mediaItemLocalHome;
    private LanguageLocalHome languageLocalHome;
    private PersonLocalHome personLocalHome;
    private MediaProductionRelationshipLocalHome mediaProductionRelationshipLocalHome;
    
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
            Object obj2 = context.lookup("java:comp/env/ejb/MyLanguageEJB");
            languageLocalHome = (LanguageLocalHome) obj2;
            Object obj4 = context.lookup("java:comp/env/ejb/MyPersonEJB");
            personLocalHome = (PersonLocalHome) obj4;
            Object obj5 = context.lookup("java:comp/env/ejb/MyMediaProductionRelationshipEJB");
            mediaProductionRelationshipLocalHome = (MediaProductionRelationshipLocalHome) obj5;
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
     *
     
    public String testFindPublishedByPerson() {
        try {
            // create a media item
            // create a mediaProductionRelationship
            // add the mediaProductionRelationship to the mediaItem
            // create a person
            // add the person to the mediaProductionRelationship
            // then try and find the media item using the person's ID
            
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "item with author", "book", 2002);
            
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "author");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "AuthorPerson");
            
            Set itemRels = item.getMediaProductionRelationships();
            itemRels.add(rel);
            
            Set relPersons = rel.getPeople();
            relPersons.add(person);
            
            item.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPerson(person);
            boolean foundPersonItem = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                if (foundItem.getMediaItemID().intValue() != itemKey) {
                    return ("found incorrect item when searching for items created by person");
                }
                else if (foundItem.getMediaItemID().intValue() == itemKey) {
                    foundPersonItem = true;
                }
            }
            if (!foundPersonItem) {
                return ("failed to find correct item when searching for items created by person");
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
     
    public String testFindPublishedByPersonByLanguage() {
        try {
            // make two media items, one in French, one in English
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "un livre", "book", 2002);
            LanguageLocal french = languageLocalHome.findByPrimaryKey("fra");
            Set item1Languages = item1.getLanguages();
            item1Languages.add(french);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "a book", "book", 2001);
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            Set item2Languages = item2.getLanguages();
            item2Languages.add(english);
            
            // create author relationships
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            // create a person
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "MultiLingualAuthorBod");
            
            // now add the person to both the mediaproductionrelationships
            Set rel1People = rel1.getPeople();
            rel1People.add(person);
            
            Set rel2People = rel2.getPeople();
            rel2People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            // person is now defined as the author of item1 and item2, but item1 is in french and item2 is in english
            // now we need to do a findByPersonByLanguage for person's items in french
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByLanguage(person, french);
            boolean foundFrenchItem = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    return ("found item by correct person but in wrong language");
                }
                else if (foundKey != itemKey2 && foundKey != itemKey1) {
                    return ("found incorrect item altogether");
                }
                else if (foundKey == itemKey1) {
                    foundFrenchItem = true;
                }
            }
            if (!foundFrenchItem) {
                return ("failed to find correct item");
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
     
    public String testFindPublishedByPersonByType() {
        try {
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "an article in a book", "bookArticle", 2002);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "a book. plain and simple", "book", 2002);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "PersonWhoWriteMoreThanBooks");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set rel1People = rel1.getPeople();
            rel1People.add(person);
            
            Set rel2People = rel2.getPeople();
            rel2People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            // person now set up as an author of a book and a bookArticle
            // now search for books by person
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByType(person, "book");
            boolean foundBook = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey1) {
                    return ("found incorrect type of item by correct person");
                }
                else if (foundKey != itemKey1 && foundKey != itemKey2) {
                    return ("found incorrect item altogether");
                }
                else if (foundKey == itemKey2) { // the book
                    foundBook = true;
                }
            }
            if (!foundBook) {
                return ("failed to find item of a distinct type involving a distinct person");
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
     
    public String testFindPublishedByPersonByLanguageByType() {
        try {
            // make two media items, a bookArticle in French, a book in English
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "un article", "bookArticle", 2002);
            LanguageLocal french = languageLocalHome.findByPrimaryKey("fra");
            Set item1Languages = item1.getLanguages();
            item1Languages.add(french);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "a book", "book", 2001);
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            Set item2Languages = item2.getLanguages();
            item2Languages.add(english);
            
            // create author relationships
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            // create a person
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "MultiLingualAuthorBod");
            
            // now add the person to both the mediaproductionrelationships
            Set rel1People = rel1.getPeople();
            rel1People.add(person);
            
            Set rel2People = rel2.getPeople();
            rel2People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            // person is now defined as the author of item1 and item2, but item1 is in french and item2 is in english
            // now we what need to do is call findByPersonByLanguageByType for person's bookArticles items in french
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByLanguageByType(person, french, "bookArticle");
            boolean foundBookArticleInFrench = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    return ("found item of wrong type and of wrong language but correct author");
                }
                else if (foundKey != itemKey1 && foundKey != itemKey2) {
                    return("found wrong item altogether");
                }
                else if (foundKey == itemKey1) {
                    foundBookArticleInFrench = true;
                }
            }
            if (!foundBookArticleInFrench) {
                return ("failed to find item by person by language by type");
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
     
    public String testFindPublishedByPersonByRel() {
        try {
            // create a person. this person will be editor of one item and author of another
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "MultitalentedBod");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a book that has been edited", "book", 2002);
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "editor");
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            Set rel1People = rel1.getPeople();
            rel1People.add(person);
            // person is now editor of item1
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "a book which has been authored", "book", 2002);
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            Set rel2People = rel2.getPeople();
            rel2People.add(person);
            // person is now author of item2
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            // search for items of which person is the editor
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByRel(person, "editor");
            boolean foundEditedBook = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    return ("found item by correct person but with incorrect mediaproduction relationship");
                }
                else if (foundKey != itemKey1 && foundKey != itemKey2) {
                    return ("found wrong item altogether");
                }
                else if (foundKey == itemKey1) {
                    foundEditedBook = true;
                }
            }
            if (!foundEditedBook) {
                return ("failed to find item in correct relationship with correct person");
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
    
    public String testFindPublishedByPersonByRelByLanguage() {
        try {
            /* we need to create an item in a particular language to which a person
             * has a specific relationship. This will be the positive test. we must
             * find this for the test to pass.
             *
             * for negative tests we should have two items which should not be found
             * - an item in a different language but with the same relationship
             * - an item in the same language but with a different relationship
             
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "MultiLingualBod");
            
            // person will be author of this
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "an item", "book", 2001);
            // make it English
            Set item1Languages = item1.getLanguages();
            item1Languages.add(english);
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            Set rel1People = rel1.getPeople();
            rel1People.add(person);
            
            // person will be editor of this
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another item", "book", 2001);
            Set item2Languages = item2.getLanguages();
            item2Languages.add(english);
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "editor");
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            Set rel2People = rel2.getPeople();
            rel2People.add(person);
            
            // person will be author of this
            int itemKey3 = getKeyCandidate();
            MediaItemLocal item3 = mediaItemLocalHome.create(itemKey3, "ein Buch", "book", 2001);
            Set item3Languages = item3.getLanguages();
            item3Languages.add(german);
            int relKey3 = getKeyCandidate();
            MediaProductionRelationshipLocal rel3 = mediaProductionRelationshipLocalHome.create(relKey3, "author");
            Set item3Rels = item3.getMediaProductionRelationships();
            item3Rels.add(rel3);
            Set rel3People = rel3.getPeople();
            rel3People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            // so, person is now author of a book in English, editor of another book in English and author of a book in German
            // we'll look for the book in English that person is author of
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByRelByLanguage(person, "author", english);
            boolean foundBookAuthorEnglish = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey1) {
                    foundBookAuthorEnglish = true;
                }
                else if (foundKey == itemKey2) {
                    return ("found item by correct author in correct language but with incorrect relationship");
                }
                else if (foundKey == itemKey3) {
                    return ("found item by correct author with correct relationship but in incorrect language");
                }
                else if (foundKey != itemKey1 && foundKey != itemKey2 && foundKey != itemKey3) {
                    return ("found wrong item altogether");
                }
            }
            if (!foundBookAuthorEnglish) {
                return ("failed entirely to find correct item by correct author in correct language");
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
     
    public String testFindPublishedByPersonByRelByType() {
        try {
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "an item", "bookArticle", 2002);
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "Bob");
            
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "author");
            
            Set relPeople = rel.getPeople();
            relPeople.add(person);
            
            Set itemRels = item.getMediaProductionRelationships();
            itemRels.add(rel);
            
            item.setPublishedFlag(true);
            
            Collection itemsFound = mediaItemLocalHome.findPublishedByPersonByRelByType(person, "author", "bookArticle");
            // should only return one item
            if (itemsFound.size() > 1) {
                return ("found too many items. Assuming there has been an error");
            }
            boolean foundRightItem = false;
            Iterator it = itemsFound.iterator();
            while (it.hasNext()) {
                MediaItemLocal itemFound = (MediaItemLocal) it.next();
                if (itemFound.getMediaItemID().intValue() != itemKey) {
                    return ("found wrong item");
                }
                else if (itemFound.getMediaItemID().intValue() == itemKey) {
                    foundRightItem = true;
                }
            }
            if (!foundRightItem) {
                return ("failed to find correct item");
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
     
    public String testFindPublishedByPersonByRelByLanguageByType() {
        try {
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            
            int itemKey = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(itemKey, "an item", "bookArticle", 2002);
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "Bob");
            
            int relKey = getKeyCandidate();
            MediaProductionRelationshipLocal rel = mediaProductionRelationshipLocalHome.create(relKey, "author");
            
            Set relPeople = rel.getPeople();
            relPeople.add(person);
            
            Set itemRels = item.getMediaProductionRelationships();
            itemRels.add(rel);
            
            Set itemLangs = item.getLanguages();
            itemLangs.add(english);
            
            item.setPublishedFlag(true);
            
            Collection itemsFound = mediaItemLocalHome.findPublishedByPersonByRelByLanguageByType(person, "author", english, "bookArticle");
            // should only return one item
            if (itemsFound.size() > 1) {
                return ("found too many items. Assuming there has been an error");
            }
            boolean foundRightItem = false;
            Iterator it = itemsFound.iterator();
            while (it.hasNext()) {
                MediaItemLocal itemFound = (MediaItemLocal) it.next();
                if (itemFound.getMediaItemID().intValue() != itemKey) {
                    return ("found wrong item");
                }
                else if (itemFound.getMediaItemID().intValue() == itemKey) {
                    foundRightItem = true;
                }
            }
            if (!foundRightItem) {
                return ("failed to find correct item");
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
     
    public String testFindPublishedByPersonBetweenYears() {
        try {
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a book written between some years", "book", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonBetweenYears(person, 1930, 1935);
            if (foundItems.size() > 2) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem1 = false;
            boolean foundItem2 = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey1) {
                    foundItem1 = true;
                }
                else if (foundKey == itemKey2) {
                    foundItem2 = true;
                }
            }
            if (!foundItem1) {
                return ("did not find item one");
            }
            else if (!foundItem2) {
                return ("did not find item two");
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
     
    public String testFindPublishedByPersonBetweenYearsByLanguage() {
        try {
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a book written between some years", "book", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int itemKey3 = getKeyCandidate();
            MediaItemLocal item3 = mediaItemLocalHome.create(itemKey3, "ein anderes buch, das zwischen 1930 und 1935 geschrieben wurde", "book", 1933);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            int relKey3 = getKeyCandidate();
            MediaProductionRelationshipLocal rel3 = mediaProductionRelationshipLocalHome.create(relKey3, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item3Rels = item3.getMediaProductionRelationships();
            item3Rels.add(rel3);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            Set item3People = rel3.getPeople();
            item3People.add(person);
            
            Set item1Langs = item1.getLanguages();
            item1Langs.add(english);
            
            Set item2Langs = item2.getLanguages();
            item2Langs.add(english);
            
            Set item3Langs = item3.getLanguages();
            item3Langs.add(german);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            item3.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonBetweenYearsByLanguage(person, 1930, 1935, english);
            if (foundItems.size() > 2) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem1 = false;
            boolean foundItem2 = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey1) {
                    foundItem1 = true;
                }
                else if (foundKey == itemKey2) {
                    foundItem2 = true;
                }
            }
            if (!foundItem1) {
                return ("did not find item one");
            }
            else if (!foundItem2) {
                return ("did not find item two");
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
     
    public String testFindPublishedByPersonBetweenYearsByType() {
        try {
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a book written between some years", "book", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "a bookArticle written between some years", "bookArticle", 1934);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonBetweenYearsByType(person, 1930, 1935, "bookArticle");
            if (foundItems.size() > 1) {
                return ("found too many items. Assuming an error");
            }
            
            boolean foundItem2 = false;
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    foundItem2 = true;
                }
            }
            if (!foundItem2) {
                return ("did not find item two");
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
     
    public String testFindPublishedByPersonBetweenYearsByLanguageByType() {
        try {
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a book written between some years", "book", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int itemKey3 = getKeyCandidate();
            MediaItemLocal item3 = mediaItemLocalHome.create(itemKey3, "ein anderes kapitel, das zwischen 1930 und 1935 geschrieben wurde", "bookArticle", 1933);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            int relKey3 = getKeyCandidate();
            MediaProductionRelationshipLocal rel3 = mediaProductionRelationshipLocalHome.create(relKey3, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item3Rels = item3.getMediaProductionRelationships();
            item3Rels.add(rel3);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            Set item3People = rel3.getPeople();
            item3People.add(person);
            
            Set item1Langs = item1.getLanguages();
            item1Langs.add(english);
            
            Set item2Langs = item2.getLanguages();
            item2Langs.add(english);
            
            Set item3Langs = item3.getLanguages();
            item3Langs.add(german);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            item3.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonBetweenYearsByLanguageByType(person, 1930, 1935, german, "bookArticle");
            if (foundItems.size() > 1) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem3 = false;
            
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey3) {
                    foundItem3 = true;
                }
                
            }
            if (!foundItem3) {
                return ("did not find item three, the bookArticle written in german between 1930 and 1935");
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
     
    public String testFindPublishedByPersonByRelBetweenYears() {
        try {
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a book written between some years", "book", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "editor");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByRelBetweenYears(person,"editor", 1930, 1935);
            if (foundItems.size() > 1) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem2 = false;
            
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    foundItem2 = true;
                }
            }
            if (!foundItem2) {
                return ("did not find item two");
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
     
    public String testFindPublishedByPersonByRelBetweenYearsByLanguage() {
        try {
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a bookArticle written between some years", "bookArticle", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int itemKey3 = getKeyCandidate();
            MediaItemLocal item3 = mediaItemLocalHome.create(itemKey3, "ein anderes buch, das zwischen 1930 und 1935 geschrieben wurde", "book", 1933);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            int relKey3 = getKeyCandidate();
            MediaProductionRelationshipLocal rel3 = mediaProductionRelationshipLocalHome.create(relKey3, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item3Rels = item3.getMediaProductionRelationships();
            item3Rels.add(rel3);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            Set item3People = rel3.getPeople();
            item3People.add(person);
            
            Set item1Langs = item1.getLanguages();
            item1Langs.add(english);
            
            Set item2Langs = item2.getLanguages();
            item2Langs.add(english);
            
            Set item3Langs = item3.getLanguages();
            item3Langs.add(german);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            item3.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByRelBetweenYearsByLanguage(person, "author", 1930, 1935, german);
            if (foundItems.size() > 1) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem3 = false;
            
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey3) {
                    foundItem3 = true;
                }
                
            }
            if (!foundItem3) {
                return ("did not find item one");
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
     
    public String testFindPublishedByPersonByRelBetweenYearsByType() {
        try {
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a bookArticle written between some years", "bookArticle", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int itemKey3 = getKeyCandidate();
            MediaItemLocal item3 = mediaItemLocalHome.create(itemKey3, "ein anderes buch, das zwischen 1930 und 1935 geschrieben wurde", "book", 1933);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            int relKey3 = getKeyCandidate();
            MediaProductionRelationshipLocal rel3 = mediaProductionRelationshipLocalHome.create(relKey3, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item3Rels = item3.getMediaProductionRelationships();
            item3Rels.add(rel3);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            Set item3People = rel3.getPeople();
            item3People.add(person);
            
            Set item1Langs = item1.getLanguages();
            item1Langs.add(english);
            
            Set item2Langs = item2.getLanguages();
            item2Langs.add(english);
            
            Set item3Langs = item3.getLanguages();
            item3Langs.add(german);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            item3.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByRelBetweenYearsByType(person, "author", 1930, 1935, "book");
            if (foundItems.size() > 2) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem2 = false;
            boolean foundItem3 = false;
            
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    foundItem2 = true;
                }
                else if (foundKey == itemKey3) {
                    foundItem3 = true;
                }
            }
            if (!foundItem2) {
                return ("did not find item two");
            }
            else if (!foundItem3) {
                return ("did not find item three");
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
     
    public String testFindPublishedByPersonByRelBetweenYearsByLanguageByType() {
        try {
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            
            int personKey = getKeyCandidate();
            PersonLocal person = personLocalHome.create(personKey, "geezer");
            
            int itemKey1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(itemKey1, "a bookArticle written between some years", "bookArticle", 1930);
            
            int itemKey2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(itemKey2, "another book written between some years", "book", 1934);
            
            int itemKey3 = getKeyCandidate();
            MediaItemLocal item3 = mediaItemLocalHome.create(itemKey3, "ein anderes buch, das zwischen 1930 und 1935 geschrieben wurde", "book", 1933);
            
            int relKey1 = getKeyCandidate();
            MediaProductionRelationshipLocal rel1 = mediaProductionRelationshipLocalHome.create(relKey1, "author");
            
            int relKey2 = getKeyCandidate();
            MediaProductionRelationshipLocal rel2 = mediaProductionRelationshipLocalHome.create(relKey2, "author");
            
            int relKey3 = getKeyCandidate();
            MediaProductionRelationshipLocal rel3 = mediaProductionRelationshipLocalHome.create(relKey3, "author");
            
            Set item1Rels = item1.getMediaProductionRelationships();
            item1Rels.add(rel1);
            
            Set item2Rels = item2.getMediaProductionRelationships();
            item2Rels.add(rel2);
            
            Set item3Rels = item3.getMediaProductionRelationships();
            item3Rels.add(rel3);
            
            Set item1People = rel1.getPeople();
            item1People.add(person);
            
            Set item2People = rel2.getPeople();
            item2People.add(person);
            
            Set item3People = rel3.getPeople();
            item3People.add(person);
            
            Set item1Langs = item1.getLanguages();
            item1Langs.add(english);
            
            Set item2Langs = item2.getLanguages();
            item2Langs.add(english);
            
            Set item3Langs = item3.getLanguages();
            item3Langs.add(german);
            
            item1.setPublishedFlag(true);
            item2.setPublishedFlag(true);
            item3.setPublishedFlag(true);
            
            Collection foundItems = mediaItemLocalHome.findPublishedByPersonByRelBetweenYearsByLanguageByType(person, "author", 1930, 1935, english, "book");
            if (foundItems.size() > 1) {
                return ("found too many items. Assuming an error");
            }
            boolean foundItem2 = false;
            
            Iterator it = foundItems.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if (foundKey == itemKey2) {
                    foundItem2 = true;
                }
            }
            if (!foundItem2) {
                return ("did not find item two");
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
    
    /** Creates a new instance of MediaItemPersonFindersTestFacade */
    public MediaItemPersonFindersTestFacadeBean() {
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
