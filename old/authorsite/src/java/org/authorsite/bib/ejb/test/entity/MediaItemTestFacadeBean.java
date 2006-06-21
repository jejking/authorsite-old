/*
 * MediaItemTestFacade.java
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/test/entity/MediaItemTestFacadeBean.java,v 1.12 2003/03/01 13:35:55 jejking Exp $
 *
 * Created on 24 September 2002, 09:28
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
 * This is a test bean facade that will be deployed in test scenarios to test
 * methods of the MediaItemBean that depend on local interfaces - specificially
 * Container Managed Relationships. 
 *
 * It also provides a naive pass-through access to entity beans.
 *
 * @ejb:bean    name="MediaItemTestFacadeEJB"
 *              type="Stateless"
 *              jndi-name="ejb/MediaItemTestFacadeEJB"
 *              view-type="remote"
 * @ejb:interface   remote-class="org.authorsite.bib.ejb.test.entity.MediaItemTestFacade"
 *                  generate="remote"
 * @ejb:home        remote-class="org.authorsite.bib.ejb.test.entity.MediaItemTestFacadeHome"
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
 * @ejb:ejb-ref ejb-name="IntraMediaRelationshipEJB"
 *              ref-name="ejb/MyIntraMediaRelationshipEJB"
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
 * @version $Revision: 1.12 $
 */
public class MediaItemTestFacadeBean implements SessionBean {
    
    private SessionContext ctx;
    private MediaItemLocalHome mediaItemLocalHome;
    private LanguageLocalHome languageLocalHome;
    private IntraMediaRelationshipLocalHome intraMediaRelationshipLocalHome;
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
            Object obj3 = context.lookup("java:comp/env/ejb/MyIntraMediaRelationshipEJB");
            intraMediaRelationshipLocalHome = (IntraMediaRelationshipLocalHome) obj3;
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
     */
    public String testCreateMediaItemWithValidParams() throws EJBException {
        try {
            int key = getKeyCandidate();
            String testTitle = "test title";
            String itemType = "book";
            int year = 2002;
            MediaItemLocal item1 = mediaItemLocalHome.create(key, testTitle, itemType, year);
            if (item1 == null) {
                return ("create returned null object");
            }
            
            // cunningly also tests findByPrimaryKey
            MediaItemLocal item2 = mediaItemLocalHome.findByPrimaryKey(new Integer(key));
            if (!item2.getMediaItemID().equals(new Integer(key))) {
                return ("keys not equal");
            }
            if (!item2.getTitle().equals(testTitle)) {
                return("titles not equal");
            }
            if (!item2.getMediaType().equals(itemType)) {
                return ("media types not equal");
            }
            if (item2.getYearOfCreation() != year) {
                return ("years not equal");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return ("could not create item\n" + ce.getMessage());
        }
        catch (FinderException fe) {
            return("could not find MediaItem bean\n" + fe.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testOtherSettersWithValidParams() {
        try {
            int key = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(key, "test two", "book", 2002);
                        
            String newTitle = "altered title";
            int newYear = 2001;
            String additionalInfo = "some additional information";
            String comment = "some test comment";
            
            item.setTitle(newTitle);
            item.setYearOfCreation(newYear);
            item.setAdditionalInfo(additionalInfo);
            item.setComment(comment);
            
            MediaItemLocal item2 = mediaItemLocalHome.findByPrimaryKey(new Integer(key));
            
            if (!item2.getTitle().equals(newTitle)) {
                return ("new title not correct");
            }
            if (item2.getYearOfCreation() != newYear) {
                return ("new year of creation not correct");
            }
            if (!item2.getAdditionalInfo().equals(additionalInfo)) {
                return("additional info not correct");
            }
            if (!item2.getComment().equals(comment)) {
                return("comment is not correct");
            }
            return ("passed");
	}
        catch (CreateException ce) {
            return("could not create MediaItem bean\n" + ce.getMessage());
        }
        catch (FinderException fe) {
            return("coudld not find MediaItem\n " + fe.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testAddAnIntraMediaRelationship() throws EJBException {
        try {
            int key1 = getKeyCandidate();
            MediaItemLocal item = mediaItemLocalHome.create(key1, "test two", "book", 2002);
                        
            int key2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(key2, "test one", "book", 2001);
            
            
            int key3 = getKeyCandidate();
            IntraMediaRelationshipLocal testRel = intraMediaRelationshipLocalHome.create(key3, key1, "edition", key2);
            testRel.setRelationshipQualifier("test");
            
            // now, try and find the relationship and see it's ok
            IntraMediaRelationshipLocal testRel2 = intraMediaRelationshipLocalHome.findByPrimaryKey(new Integer(key3));
            
            if (!testRel2.getIntraMediaRelationshipID().equals(new Integer(key3))) {
                return("relationship primary keys not identical");
            }
            if (testRel2.getMediaFromID() != key1) {
                return("MediaFromID is not correct");
            }
            if (!testRel2.getRelationshipType().equals("edition")) {
                return("relationship type is not correct");
            }
            if (testRel2.getMediaToID() != key2) {
                return("MediaToID is not correct");
            }
            if (!testRel2.getRelationshipQualifier().equals("test")) {
                return("Relationship Qualifier is not correct");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return("could not create item\n" + ce.getMessage());
        }
        catch (FinderException fe) {
            return("could not find item\n " + fe.getMessage());
        }
    }

    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testAddALanguage() throws EJBException {
           try {
               int key = getKeyCandidate();
               MediaItemLocal item = mediaItemLocalHome.create(key, "test a language title", "book", 2002);
               LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
               Set languages = item.getLanguages();
               languages.add(english);
               return("passed");
           }
           catch (FinderException fe) {
               throw new EJBException (fe.getMessage());
           }
           catch (CreateException ce) {
               throw new EJBException (ce.getMessage());
           }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     
    public String testAddMultipleLanguages() {
         try {
             int key = getKeyCandidate();
             MediaItemLocal item = mediaItemLocalHome.create(key, "multilingual, mehrsprachig", "bookArticle", 2002);
             LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
             LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
             Set languages = item.getLanguages();
             languages.add(english);
             languages.add(german);
             
             boolean foundInGerman = false;
             boolean foundInEnglish =false;
             Collection itemsInGerman = mediaItemLocalHome.findUnpublishedByTypeAndLanguage("bookArticle", german);
             Iterator itemsInGermanIt = itemsInGerman.iterator();
             while (itemsInGermanIt.hasNext()) {
                 MediaItemLocal currentItem = (MediaItemLocal) itemsInGermanIt.next();
                 if (currentItem.getMediaItemID().intValue() == key) {
                     foundInGerman = true;
                 }
             }
             Collection itemsInEnglish = mediaItemLocalHome.findUnpublishedByTypeAndLanguage("bookArticle", english);
             Iterator itemsInEnglishIt = itemsInEnglish.iterator();
             while (itemsInEnglishIt.hasNext()) {
                 MediaItemLocal currentItem = (MediaItemLocal) itemsInEnglishIt.next();
                 if (currentItem.getMediaItemID().intValue() == key) {
                     foundInEnglish = true;
                 }
             }
             if (!foundInGerman) {
                 return ("failed to find the item amongst the bookArticles in German");
             }
             if (!foundInEnglish) {
                 return ("failed to find the item amongst the bookARticles in English");
             }
             return ("passed");
             
         }
         catch (CreateException ce) {
             return ("Create Exception " + ce.getMessage());
         }
         catch (FinderException fe) {
             return ("Finder Exception " + fe.getMessage());
         }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testFindAllUnpublished() throws EJBException {
        try {
            // create a mediaItem. Try and find it in findAllunpublished
            int key1 = getKeyCandidate();
            MediaItemLocal unpublishedItem = mediaItemLocalHome.create(key1, "book we\'ll never publish", "book", 2002);
            // we do *not* set the publishedFlag to true on item1
            
            int key2 = getKeyCandidate();
            MediaItemLocal publishedItem = mediaItemLocalHome.create(key2, "book we\'ll publish", "book", 2001);
            publishedItem.setPublishedFlag(true);
            
            boolean foundUnpublishedItem = false; // set this to true when we find the unpublished item
            Collection unpublished = mediaItemLocalHome.findAllUnpublished();
            Iterator it = unpublished.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                if (foundItem.getMediaItemID().intValue() == key2) {
                    return ("found a published item whilst searching for unpublished items");
                }
                else if (foundItem.getMediaItemID().intValue() == key1) {
                    foundUnpublishedItem = true;
                }
            }
            
            if (!foundUnpublishedItem) { // this should be set true whilst iterating through unpublished items
                return("did not find unpublished item when seaching for unpublished items");
            }
            return ("passed");
        }
        catch (FinderException fe) {
            return ("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return ("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     
    public String testFindPublishedByLanguage() throws EJBException {
        // create a mediaItem. 
        try {
            int key1 = getKeyCandidate();
            MediaItemLocal item1 = mediaItemLocalHome.create(key1, "book we\'ll publish (auch auf Deutsch)", "book", 2002);
            item1.setPublishedFlag(true);
            
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            LanguageLocal french = languageLocalHome.findByPrimaryKey("fra");
            LanguageLocal german = languageLocalHome.findByPrimaryKey("deu");
            
            Set item1Languages = item1.getLanguages();
            item1Languages.add(english);
            item1Languages.add(german);
            
            // create another mediaItem
            int key2 = getKeyCandidate();
            MediaItemLocal item2 = mediaItemLocalHome.create(key2, "livre en francais", "book", 2002);
            item2.setPublishedFlag(true);
            
            // make it be in French
            Set item2Languages = item2.getLanguages();
            item2Languages.add(french);
                        
            // search for mediaItems in English. We should only find key1, despite the fact it's also in German
            boolean foundEnglishItem = false;
            Collection itemsInEnglish = mediaItemLocalHome.findPublishedByLanguage(english);
            Iterator engIt = itemsInEnglish.iterator();
            while (engIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) engIt.next();
                if (foundItem.getMediaItemID().intValue() == key2) {
                    return("found a French item when looking for an English one");
                }
                else if (foundItem.getMediaItemID().intValue() == key1) {
                    foundEnglishItem = true;
                }
            }
            if (!foundEnglishItem) {
                return ("failed to find English item when looking for English items");
            }
            return ("passed");
            
        }
        catch (FinderException fe) {
            return("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     
    public String testFindPublishedByTypeAndLanguage() {
        /* we need to create a number of items in the database and then search for them
         * and look through the results to make sure they're ok 
         * - a book in English (should be returned by a search for book and English)
         * - a book in French (should not be retured by a search for book and English)
         * - an bookArticle in English (should not be returend by a search for book and English)
         
        try {
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            LanguageLocal french = languageLocalHome.findByPrimaryKey("fra");
            
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book", "book", 2002);
            englishBook.setPublishedFlag(true);
            Set englishBookLangs = englishBook.getLanguages();
            englishBookLangs.add(english);
                        
            int frenchBookKey = getKeyCandidate();
            MediaItemLocal frenchBook = mediaItemLocalHome.create(frenchBookKey, "livre francais", "book", 2002);
            frenchBook.setPublishedFlag(true);
            Set frenchBookLangs = frenchBook.getLanguages();
            frenchBookLangs.add(french);
            
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
            englishBookArticle.setPublishedFlag(true);
            Set englishBookArticleLangs = englishBookArticle.getLanguages();
            englishBookArticleLangs.add(english);
                        
            // now run the search for book in English
            boolean foundBookInEnglish = false;
            Collection booksInEnglish = mediaItemLocalHome.findPublishedByTypeAndLanguage("book", english);
            Iterator booksInEnglishIt = booksInEnglish.iterator();
            while(booksInEnglishIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) booksInEnglishIt.next();
                if (foundItem.getMediaItemID().intValue() == frenchBookKey) {
                    return("found French book when looking for English book");
                }
                else if (foundItem.getMediaItemID().intValue() == englishBookArticleKey) {
                    return("found English bookArticle when looking for English book");
                }
                else if (foundItem.getMediaItemID().intValue() == englishBookKey) {
                    foundBookInEnglish = true;
                }
            }
            if (!foundBookInEnglish) {
                return ("failed to find book in English when searching for book in English");
            }
            return ("passed");
        }
        catch (FinderException fe) {
            return("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     
    public String testFindPublishedByYearOfCreation() {
        try {
            int key = getKeyCandidate();
            MediaItemLocal item1999 = mediaItemLocalHome.create(key, "1999 - an interesting year", "book", 1999);
            item1999.setPublishedFlag(true);
            
            boolean foundBookFrom1999 = false;
            Collection booksOf1999 = mediaItemLocalHome.findPublishedByYearOfCreation(1999);
            Iterator it = booksOf1999.iterator();
            
            while (it.hasNext()) {
                MediaItemLocal itemFound = (MediaItemLocal) it.next();
                if (itemFound.getYearOfCreation() != 1999) {
                    return("found item not from 1999 in search for items from 1999");
                }
                else if (itemFound.getMediaItemID().intValue() == key) {
                    foundBookFrom1999 = true;
                }
            }
            if (!foundBookFrom1999) {
                return("did not find the book from 1999 in search for items from 1999");
            }
            return ("passed");
            
        }
        catch (FinderException fe) {
            return("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     *
    public String testFindPublishedCreatedBefore() throws EJBException {
        try {
            int before1940Key = getKeyCandidate();
            MediaItemLocal before1940Item = mediaItemLocalHome.create(before1940Key, "ye olde booke", "book", 1478);
            before1940Item.setPublishedFlag(true);
            
            int after1940Key = getKeyCandidate();
            MediaItemLocal after1940Item = mediaItemLocalHome.create(after1940Key, "brand new book", "book", 1960);
            after1940Item.setPublishedFlag(true);
            
            boolean foundBefore1940Item = false;
            Collection itemsBefore1940 = mediaItemLocalHome.findPublishedCreatedBefore(1940);
            Iterator it = itemsBefore1940.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                if (foundItem.getYearOfCreation() >= 1940) {
                    return ("found item  from 1940 or after in search for items before 1940");
                }
                else if (foundItem.getMediaItemID().intValue() == before1940Key) {
                    foundBefore1940Item = true;
                }
            }
            if (!foundBefore1940Item) {
                return ("failed to find item from before 1940 in search for items before 1940");
            }
            return ("passed");
        }
        catch (FinderException fe) {
            return("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     
    public String testFindPublishedCreatedAfter() throws EJBException {
        try {
            int before1940Key = getKeyCandidate();
            MediaItemLocal before1940Item = mediaItemLocalHome.create(before1940Key, "ye olde booke", "book", 1478);
            before1940Item.setPublishedFlag(true);
            
            int after1960Key = getKeyCandidate();
            MediaItemLocal after1960Item = mediaItemLocalHome.create(after1960Key, "brand new book", "book", 1961);
            after1960Item.setPublishedFlag(true);
            
            boolean foundAfter1960Item = false;
            Collection itemsAfter1960 = mediaItemLocalHome.findPublishedCreatedAfter(1960);
            Iterator it = itemsAfter1960.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                if (foundItem.getYearOfCreation() <= 1960) {
                    return ("found item  from 1960 or before in search for items after 1960");
                }
                else if (foundItem.getMediaItemID().intValue() == after1960Key) {
                    foundAfter1960Item = true;
                }
            }
            if (!foundAfter1960Item) {
                return ("failed to find item from after 1960 in search for items after 1960");
            }
            return ("passed");
        }
        catch (FinderException fe) {
            return("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     
    public String testFindPublishedCreatedBetween() {
        try {
            int key1940 = getKeyCandidate();
            MediaItemLocal item1940 = mediaItemLocalHome.create(key1940, "ye olde booke", "book", 1940);
            item1940.setPublishedFlag(true);
            
            int key1960 = getKeyCandidate();
            MediaItemLocal item1960 = mediaItemLocalHome.create(key1960, "brand new book", "book", 1960);
            item1960.setPublishedFlag(true);
            
            int key1980 = getKeyCandidate();
            MediaItemLocal item1980 = mediaItemLocalHome.create(key1980, "even newer 80s book", "book", 1980);
            item1980.setPublishedFlag(true);
            
            boolean found1940 = false;
            boolean found1960 = false;
            boolean found1980 = false;
            Collection between1940And1980 = mediaItemLocalHome.findPublishedCreatedBetween(1940, 1980);
            Iterator it = between1940And1980.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                if (foundItem.getYearOfCreation() < 1940 || foundItem.getYearOfCreation() > 1980) {
                    return ("found item from before 1940 or after 1980 in serach for items between 1940 and 1980");
                }
                if (foundItem.getMediaItemID().intValue() == key1940) {
                    found1940 = true;
                }
                else if (foundItem.getMediaItemID().intValue() == key1960) {
                    found1960 = true;
                }
                else if (foundItem.getMediaItemID().intValue() == key1980) {
                    found1980 = true;
                }
            }
            if (!found1940) {
                return ("did not find item from 1940 in search for items between 1940 and 1980");
            }
            if (!found1960) {
                return ("did not find item from 1960 in search for items between 1940 and 1980");
            }
            if (!found1980) {
                return ("did not find item from 1980 in search for items between 1940 and 1980");
            }
            return ("passed");
        }
        catch (FinderException fe) {
            return("finder exception occurred\n" + fe.getMessage());
        }
        catch (CreateException ce) {
            return("create exception occurred\n" + ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetPublishedChildren() throws EJBException {
        try {
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book with articles", "book", 2001);
            englishBook.setPublishedFlag(true);
                        
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
            englishBookArticle.setPublishedFlag(true);
            
            int secondEnglishBookArticleKey = getKeyCandidate();
            MediaItemLocal secondEnglishBookArticle = mediaItemLocalHome.create(secondEnglishBookArticleKey, "second bookArticle", "bookArticle", 2001);
            secondEnglishBookArticle.setPublishedFlag(true);
            
            // link first bookArticle to the book
            int relKey = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey, englishBookKey, "containment", englishBookArticleKey);
            
            // link second bookArticle to the book
            int relKey2 = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey2, englishBookKey, "containment", secondEnglishBookArticleKey);
            
            // now call getChildren on englishBook. We should get the two englishBookArticles book
            boolean foundFirstArticle = false;
            boolean foundSecondArticle = false;
            
            Collection englishBookChildren = englishBook.getPublishedChildren();
            Iterator englishBookChildrenIt = englishBookChildren.iterator();
            while (englishBookChildrenIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) englishBookChildrenIt.next();
                int itemKey = foundItem.getMediaItemID().intValue();
                if (itemKey != englishBookArticleKey && itemKey != secondEnglishBookArticleKey) {
                    return ("found the wrong child");
                }
                else if (itemKey == englishBookArticleKey) {
                    foundFirstArticle = true;
                }
                else if (itemKey == secondEnglishBookArticleKey) {
                    foundSecondArticle = true;
                }
            }
            if (!foundFirstArticle) {
                return ("failed to find first child");
            }
            if (!foundSecondArticle) {
                return ("failed to find second child");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetUnpublishedChildren() throws EJBException {
        // same as above (oops, cut and paste), but without setting publicationFlag to true
        try {
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book with articles", "book", 2001);
                                    
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
                        
            int secondEnglishBookArticleKey = getKeyCandidate();
            MediaItemLocal secondEnglishBookArticle = mediaItemLocalHome.create(secondEnglishBookArticleKey, "second bookArticle", "bookArticle", 2001);
            
            
            // link first bookArticle to the book
            int relKey = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey, englishBookKey, "containment", englishBookArticleKey);
            
            // link second bookArticle to the book
            int relKey2 = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey2, englishBookKey, "containment", secondEnglishBookArticleKey);
            
            // now call getChildren on englishBook. We should get the two englishBookArticles book
            boolean foundFirstArticle = false;
            boolean foundSecondArticle = false;
            
            Collection englishBookChildren = englishBook.getUnpublishedChildren();
            Iterator englishBookChildrenIt = englishBookChildren.iterator();
            while (englishBookChildrenIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) englishBookChildrenIt.next();
                int itemKey = foundItem.getMediaItemID().intValue();
                if (itemKey != englishBookArticleKey && itemKey != secondEnglishBookArticleKey) {
                    return ("found the wrong child");
                }
                else if (itemKey == englishBookArticleKey) {
                    foundFirstArticle = true;
                }
                else if (itemKey == secondEnglishBookArticleKey) {
                    foundSecondArticle = true;
                }
            }
            if (!foundFirstArticle) {
                return ("failed to find first child");
            }
            if (!foundSecondArticle) {
                return ("failed to find second child");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
        
    }    
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetPublishedChildrenOfType() {
        try {
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book with articles", "book", 2001);
            englishBook.setPublishedFlag(true);
                                    
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
            englishBook.setPublishedFlag(true);
                        
            int secondEditionKey = getKeyCandidate();
            MediaItemLocal secondEdition = mediaItemLocalHome.create(secondEditionKey, "english book with articles - take two", "book", 2002);
            secondEdition.setPublishedFlag(true);
            
            // link first bookArticle to the book
            int relKey = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey, englishBookKey, "containment", englishBookArticleKey);
            
            // link second edition to the original book
            int relKey2 = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey2, englishBookKey, "edition", secondEditionKey);
                        
            // now call getChildren on englishBook. We should get the two englishBookArticles book
            boolean foundSecondEdition = false;
            
            Collection englishBookChildren = englishBook.getPublishedChildrenOfType("edition");
            Iterator englishBookChildrenIt = englishBookChildren.iterator();
            while (englishBookChildrenIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) englishBookChildrenIt.next();
                int itemKey = foundItem.getMediaItemID().intValue();
                if (itemKey == englishBookArticleKey) {
                    return ("found a containment child whilst searching for an edition child");
                }
                else if (itemKey == secondEditionKey) {
                    foundSecondEdition = true;
                }
            }
            if (!foundSecondEdition) {
                return ("failed to find edition child in search for an edition child");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetUnpublishedChildrenOfType() {
        try {
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book with articles", "book", 2001);
                                                
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
                                    
            int secondEditionKey = getKeyCandidate();
            MediaItemLocal secondEdition = mediaItemLocalHome.create(secondEditionKey, "english book with articles - take two", "book", 2002);
            
            
            // link first bookArticle to the book
            int relKey = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey, englishBookKey, "containment", englishBookArticleKey);
            
            // link second edition to the original book
            int relKey2 = getKeyCandidate();
            intraMediaRelationshipLocalHome.create(relKey2, englishBookKey, "edition", secondEditionKey);
                        
            // now call getChildren on englishBook. We should get the two englishBookArticles book
            boolean foundSecondEdition = false;
            
            Collection englishBookChildren = englishBook.getUnpublishedChildrenOfType("edition");
            Iterator englishBookChildrenIt = englishBookChildren.iterator();
            while (englishBookChildrenIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) englishBookChildrenIt.next();
                int itemKey = foundItem.getMediaItemID().intValue();
                if (itemKey == englishBookArticleKey) {
                    return ("found a containment child whilst searching for an edition child");
                }
                else if (itemKey == secondEditionKey) {
                    foundSecondEdition = true;
                }
            }
            if (!foundSecondEdition) {
                return ("failed to find edition child in search for an edition child");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetPublishedQualifiedChildrenOfType() {
        try {
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book with articles", "book", 2001);
            englishBook.setPublishedFlag(true);
                                    
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
            englishBook.setPublishedFlag(true);
                        
            int secondEnglishBookArticleKey = getKeyCandidate();
            MediaItemLocal secondEnglishBookArticle = mediaItemLocalHome.create(secondEnglishBookArticleKey, "second bookArticle", "bookArticle", 2001);
            secondEnglishBookArticle.setPublishedFlag(true);
            
            // link first bookArticle to the book
            int relKey = getKeyCandidate();
            IntraMediaRelationshipLocal rel1 =intraMediaRelationshipLocalHome.create(relKey, englishBookKey, "containment", englishBookArticleKey);
            rel1.setRelationshipQualifier("one");
            
            // link second edition to the original book
            int relKey2 = getKeyCandidate();
            IntraMediaRelationshipLocal rel2 = intraMediaRelationshipLocalHome.create(relKey2, englishBookKey, "containment", secondEnglishBookArticleKey);
            rel2.setRelationshipQualifier("two");
                        
            // now call getChildren on englishBook. We should get the two englishBookArticles book
            boolean foundSecondArticle = false;
            
            Collection englishBookChildren = englishBook.getPublishedQualifiedChildrenOfType("containment", "two");
            Iterator englishBookChildrenIt = englishBookChildren.iterator();
            while (englishBookChildrenIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) englishBookChildrenIt.next();
                int itemKey = foundItem.getMediaItemID().intValue();
                if (itemKey == englishBookArticleKey) {
                    return ("found a containment child with wrong qualifier");
                }
                else if (itemKey == secondEnglishBookArticleKey) {
                    foundSecondArticle = true;
                }
            }
            if (!foundSecondArticle) {
                return ("failed to find containment child with correct relationship qualifier");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetUnpublishedQualifiedChildrenOfType() {
        try {
            int englishBookKey = getKeyCandidate();
            MediaItemLocal englishBook = mediaItemLocalHome.create(englishBookKey, "english book with articles", "book", 2001);
            
                                    
            int englishBookArticleKey = getKeyCandidate();
            MediaItemLocal englishBookArticle = mediaItemLocalHome.create(englishBookArticleKey, "english bookArticle", "bookArticle", 2001);
            
                        
            int secondEnglishBookArticleKey = getKeyCandidate();
            MediaItemLocal secondEnglishBookArticle = mediaItemLocalHome.create(secondEnglishBookArticleKey, "second bookArticle", "bookArticle", 2001);
            
            
            // link first bookArticle to the book
            int relKey = getKeyCandidate();
            IntraMediaRelationshipLocal rel1 =intraMediaRelationshipLocalHome.create(relKey, englishBookKey, "containment", englishBookArticleKey);
            rel1.setRelationshipQualifier("one");
            
            // link second edition to the original book
            int relKey2 = getKeyCandidate();
            IntraMediaRelationshipLocal rel2 = intraMediaRelationshipLocalHome.create(relKey2, englishBookKey, "containment", secondEnglishBookArticleKey);
            rel2.setRelationshipQualifier("two");
                        
            // now call getChildren on englishBook. We should get the two englishBookArticles book
            boolean foundSecondArticle = false;
            
            Collection englishBookChildren = englishBook.getUnpublishedQualifiedChildrenOfType("containment", "two");
            Iterator englishBookChildrenIt = englishBookChildren.iterator();
            while (englishBookChildrenIt.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) englishBookChildrenIt.next();
                int itemKey = foundItem.getMediaItemID().intValue();
                if (itemKey == englishBookArticleKey) {
                    return ("found a containment child with wrong qualifier");
                }
                else if (itemKey == secondEnglishBookArticleKey) {
                    foundSecondArticle = true;
                }
            }
            if (!foundSecondArticle) {
                return ("failed to find containment child with correct relationship qualifier");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetUnpublishedParents() {
        // make something that is an edition of a pre-existing bookArticle
        // this item will itself be contained in a book. So, it'll have an edition parent (the original bookArticle)
        // and a containment parent (the book it is contained in)...
        
        try {
            int newBookArticleKey = getKeyCandidate();
            MediaItemLocal newBookArticle = mediaItemLocalHome.create(newBookArticleKey, "a new edition of the bookArticle", "bookArticle", 2002);
            
            int oldBookArticleKey = getKeyCandidate();
            MediaItemLocal oldBookArticle = mediaItemLocalHome.create(oldBookArticleKey, "the old bookArticle", "bookArticle", 2000);
            
            int newBookKey = getKeyCandidate();
            MediaItemLocal newBook = mediaItemLocalHome.create(newBookKey, "new book containing new bookArticle", "book", 2002);
            
            int newArticleToOldArticleRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToOldArticleRel = 
                intraMediaRelationshipLocalHome.create(newArticleToOldArticleRelKey, oldBookArticleKey, "edition", newBookArticleKey);
            
            int newArticleToNewBookRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToNewBookRel = 
                intraMediaRelationshipLocalHome.create(newArticleToNewBookRelKey, newBookKey, "containment", newBookArticleKey);
           
            // now try and find them
            boolean foundOldBookArticle = false;
            boolean foundNewBook = false;
            Collection newBookArticleParents = newBookArticle.getUnpublishedParents();
            Iterator it = newBookArticleParents.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if ( foundKey != newBookKey && foundKey != oldBookArticleKey) {
                    return ("found non parent when searching for unpublished parents");
                }
                else if (foundKey == oldBookArticleKey) {
                    foundOldBookArticle = true;
                }
                else if (foundKey == newBookKey) {
                    foundNewBook = true;
                }
            }
            if (!foundOldBookArticle) {
                return ("did not find edition parent when searching for unpublished parents");
            }
            if (!foundNewBook) {
                return("did not find containment parent when searching for unpublished parents");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetPublishedParents() {
        // make something that is an edition of a pre-existing bookArticle
        // this item will itself be contained in a book. So, it'll have an edition parent (the original bookArticle)
        // and a containment parent (the book it is contained in)...
        
        try {
            int newBookArticleKey = getKeyCandidate();
            MediaItemLocal newBookArticle = mediaItemLocalHome.create(newBookArticleKey, "a new edition of the bookArticle", "bookArticle", 2002);
            
            int oldBookArticleKey = getKeyCandidate();
            MediaItemLocal oldBookArticle = mediaItemLocalHome.create(oldBookArticleKey, "the old bookArticle", "bookArticle", 2000);
            oldBookArticle.setPublishedFlag(true);
            
            int newBookKey = getKeyCandidate();
            MediaItemLocal newBook = mediaItemLocalHome.create(newBookKey, "new book containing new bookArticle", "book", 2002);
            newBook.setPublishedFlag(true);
            
            int newArticleToOldArticleRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToOldArticleRel = 
                intraMediaRelationshipLocalHome.create(newArticleToOldArticleRelKey, oldBookArticleKey, "edition", newBookArticleKey);
            
            int newArticleToNewBookRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToNewBookRel = 
                intraMediaRelationshipLocalHome.create(newArticleToNewBookRelKey, newBookKey, "containment", newBookArticleKey);
           
            // now try and find them
            boolean foundOldBookArticle = false;
            boolean foundNewBook = false;
            Collection newBookArticleParents = newBookArticle.getPublishedParents();
            Iterator it = newBookArticleParents.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if ( foundKey != newBookKey && foundKey != oldBookArticleKey) {
                    return ("found non parent when searching for published parents");
                }
                else if (foundKey == oldBookArticleKey) {
                    foundOldBookArticle = true;
                }
                else if (foundKey == newBookKey) {
                    foundNewBook = true;
                }
            }
            if (!foundOldBookArticle) {
                return ("did not find edition parent when searching for published parents");
            }
            if (!foundNewBook) {
                return("did not find containment parent when searching for published parents");
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetUnpublishedParentsOfType() {
        // make something that is an edition of a pre-existing bookArticle
        // this item will itself be contained in a book. So, it'll have an edition parent (the original bookArticle)
        // and a containment parent (the book it is contained in)...
        
        try {
            int newBookArticleKey = getKeyCandidate();
            MediaItemLocal newBookArticle = mediaItemLocalHome.create(newBookArticleKey, "a new edition of the bookArticle", "bookArticle", 2002);
            
            int oldBookArticleKey = getKeyCandidate();
            MediaItemLocal oldBookArticle = mediaItemLocalHome.create(oldBookArticleKey, "the old bookArticle", "bookArticle", 2000);
            
            int newBookKey = getKeyCandidate();
            MediaItemLocal newBook = mediaItemLocalHome.create(newBookKey, "new book containing new bookArticle", "book", 2002);
            
            int newArticleToOldArticleRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToOldArticleRel = 
                intraMediaRelationshipLocalHome.create(newArticleToOldArticleRelKey, oldBookArticleKey, "edition", newBookArticleKey);
            
            int newArticleToNewBookRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToNewBookRel = 
                intraMediaRelationshipLocalHome.create(newArticleToNewBookRelKey, newBookKey, "containment", newBookArticleKey);
           
            // now try and find the edition rel
            boolean foundOldBookArticle = false;
            Collection newBookArticleParents = newBookArticle.getUnpublishedParentsOfType("edition");
            Iterator it = newBookArticleParents.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if ( foundKey != oldBookArticleKey) {
                    return ("found item which is not an edition parent when searching for unpublished edition parents");
                }
                else if (foundKey == oldBookArticleKey) {
                    foundOldBookArticle = true;
                }
            }
            if (!foundOldBookArticle) {
                return ("did not find edition parent when searching for unpublished edition parents");
            }
            
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb:interface-method view-type="remote"
     */
    public String testGetPublishedParentsOfType() {
        // make something that is an edition of a pre-existing bookArticle
        // this item will itself be contained in a book. So, it'll have an edition parent (the original bookArticle)
        // and a containment parent (the book it is contained in)...
        
        try {
            int newBookArticleKey = getKeyCandidate();
            MediaItemLocal newBookArticle = mediaItemLocalHome.create(newBookArticleKey, "a new edition of the bookArticle", "bookArticle", 2002);
            newBookArticle.setPublishedFlag(true);
            
            int oldBookArticleKey = getKeyCandidate();
            MediaItemLocal oldBookArticle = mediaItemLocalHome.create(oldBookArticleKey, "the old bookArticle", "bookArticle", 2000);
            oldBookArticle.setPublishedFlag(true);
            
            int newBookKey = getKeyCandidate();
            MediaItemLocal newBook = mediaItemLocalHome.create(newBookKey, "new book containing new bookArticle", "book", 2002);
            newBook.setPublishedFlag(true);
            
            int newArticleToOldArticleRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToOldArticleRel = 
                intraMediaRelationshipLocalHome.create(newArticleToOldArticleRelKey, oldBookArticleKey, "edition", newBookArticleKey);
            
            int newArticleToNewBookRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal newArticleToNewBookRel = 
                intraMediaRelationshipLocalHome.create(newArticleToNewBookRelKey, newBookKey, "containment", newBookArticleKey);
           
            // now try and find the edition rel
            boolean foundOldBookArticle = false;
            Collection newBookArticleParents = newBookArticle.getPublishedParentsOfType("edition");
            Iterator it = newBookArticleParents.iterator();
            while (it.hasNext()) {
                MediaItemLocal foundItem = (MediaItemLocal) it.next();
                int foundKey = foundItem.getMediaItemID().intValue();
                if ( foundKey != oldBookArticleKey) {
                    return ("found item which is not an edition parent when searching for published edition parents");
                }
                else if (foundKey == oldBookArticleKey) {
                    foundOldBookArticle = true;
                }
            }
            if (!foundOldBookArticle) {
                return ("did not find edition parent when searching for published edition parents");
            }
            
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testGetContainer() {
        try {
            // we'll test two scenarios. First, make a book that's not in a book series or anything
            // test should make sure we get nothing back from a getContainer() call
            int bookKey = getKeyCandidate();
            MediaItemLocal book = mediaItemLocalHome.create(bookKey, "a book without a container", "book", 1999);
            Set bookContainerSet = book.getContainer();
            if (!bookContainerSet.isEmpty()) {
                return ("bookContainerSet was not empty");
            }
            
            // next scenario. We'll make a bookArticle that's contained in a book. 
            // Test - a call to the getContainer() method of the book article should return a reference
            // to the containing book.
            
            int artKey = getKeyCandidate();
            MediaItemLocal bookArticle = mediaItemLocalHome.create(artKey, "an article in a book", "bookArticle", 1999);
            
            
            int bookArticleContainedInBookRelKey = getKeyCandidate();
            IntraMediaRelationshipLocal bookArticleContainedInBookRel = 
                intraMediaRelationshipLocalHome.create(bookArticleContainedInBookRelKey, artKey, "containment", bookKey);
            
            Set bookArticleContainerSet = bookArticle.getContainer();
            if (bookArticleContainerSet.isEmpty()) {
                return ("bookArticleContainerSet was empty");
            }
            if(bookArticleContainerSet.size() != 1) {
                return ("bookArticleContainerSet contained the wrong number of elements");
            }
            Iterator bookArticleContainerSetIt = bookArticleContainerSet.iterator();
            while (bookArticleContainerSetIt.hasNext()) {
                MediaItemLocal container = (MediaItemLocal) bookArticleContainerSetIt.next();
                if (container.getMediaItemID().intValue() != bookKey) {
                    return ("bookArticle's getContainer() method returned the wrong container");
                }
            }
            return ("passed");
        }
        catch (CreateException ce) {
            return (ce.getMessage());
        }
    }
    
        
    /**
     * @ejb.interface-method view-type="remote"
     */
    public String testMediaProductionRelationshipsCascadeDelete() {
        try {
            int bookKey = getKeyCandidate();
            MediaItemLocal book = mediaItemLocalHome.create(bookKey, "a book to delete", "book", 1987);
            int prodRelKey = getKeyCandidate();
            MediaProductionRelationshipLocal author = mediaProductionRelationshipLocalHome.create(prodRelKey, "author");
            Set bookProdRels = book.getMediaProductionRelationships();
            bookProdRels.add(author);
            LanguageLocal english = languageLocalHome.findByPrimaryKey("eng");
            Set languages = book.getLanguages();
            languages.add(english); // we'll need to do a manual look in the database to ensure there's no hanging languages...
            mediaItemLocalHome.remove(new Integer(bookKey));
            
            MediaProductionRelationshipLocal authorFound = mediaProductionRelationshipLocalHome.findByPrimaryKey(new Integer(prodRelKey));
            // shouldn't be found and thus throw a FinderException
            return ("no finder exception thrown. Looks like cascade delete isn't working");
        }
        catch (CreateException ce) {
            return ce.getMessage();
        }
        catch (RemoveException re) {
            return re.getMessage();
        }
        catch (FinderException fe) {
            return ("passed");
        }
    }

    
    /** Creates a new instance of MediaItemTestFacade */
    public MediaItemTestFacadeBean() {
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
