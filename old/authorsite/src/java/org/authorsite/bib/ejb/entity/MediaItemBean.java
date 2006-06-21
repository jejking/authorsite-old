/*
 * MediaItemBean.java
 *
 * Created on 12 September 2002, 14:45
 * $Header: /cvsroot/authorsite/authorsite/src/java/org/authorsite/bib/ejb/entity/MediaItemBean.java,v 1.18 2003/03/29 11:59:53 jejking Exp $
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
 * <p>
 * The concept of the MediaItem at the heart of the domain model implemented in the bibliographic
 * application.
 * </p>
 *
 * <p>
 * It describes the core attributes of any media item, whether atomic or composite and manages the
 * relationships (<code>MediaProductionRelationship</code>) which describe the way it was produced. In turn,
 * the relationships record the people and organisations involved in that production. A MediaItem entity bean
 * may represent any <code>mediaType</code>, so long as that type is listed in the central configuration file,
 * <code>bibTypesRelationships.xml</code>. Note that the standard PostgreSQL backend SQL (generated at build
 * time from the configuration xml file, further enforces this rule
 * through the use of foreign key constraints - the <code>mediaType</code> column in the <code>mediaItem</code>
 * table must refer to ene of the values in the <code>mediaType</code> table. 
 * </p>
 *
 * <p>
 * The attributes which are specific to a type of media item are represented by <code>xDetails</code> objects which are custom
 * generated at build time from <code>bibTypesRelationships.xml</code>, which defines the attribute names and types
 * as well as specifying a priority level. The priority level has two main functions. First, it determines whether a field
 * is obligatory (enforced through application level code and NOT NULL constraints in the database). Second, clients can
 * use this information (obtained through the <code>RulesManager</code>) to determine the order in which to present these
 * attributes to an end user.
 * </p>
 *
 * <p>
 * As mentioned above, a MediaItem may be composite or atomic. An atomic MediaItem is the simplest sort. A
 * simple example would be a book. A composite MediaItem is one that logically belongs inside/within the context
 * of another one. The most obvious example would be a specific chapter within a book (particularly is that book is
 * a collection of essays or otherwise distinct items). A normal implementation of <code>bibTypesRelationships.xml</code>
 * will insist that the composition be obligatory for that type, as would be the case with the book chapters. A book, on the 
 * other hand, can be both atomic and composite. Whilst a book in general can stand alone, it may also be part of a book
 * series - such as a multi-volume work or a series of related books published over time, but related (say by editor or publisher).
 * </p>
 *
 * <p>
 * The composition relationships is described in the bibliographic application as <code>containment</code>. It is implemented (currently)
 * as a standard <code>IntraMediaRelationship</code> and not as a ContainerManagedRelationship. This allows for navigation of parent/child
 * relationships (including containment) via finder methods available on the local home interface. This implementation may need to 
 * be changed so that an item can manage its container directly through a field holding a reference to that container.
 * </p>
 *
 * <p>
 * The concept of the <code>IntraMediaRelationship</code> is absolutely central to the domain model of the bibliographic
 * application and one of its key distinguishing features. It allows us to depart from the notion that a bibliography is a 
 * (searchable) list of discrete items with simple attributes and to understand the bibliography instead in terms of a
 * network of inter-related items. The item ceases to be discrete and can become a node in the network, linked to other items
 * through its relationships. In this sense, the bibliographic application can be understood as a type of primitive topic
 * map. Whilst some of these relationships may be relatively "hard" (and clearly documented, such as film X is an adaptation 
 * of book Y, or A is a translation of B into French), others may be quite "soft" in that the establishment of the relationship
 * is more clearly an act of interpretation than observation (book D was inspired by painting E). One possible planned feature is
 * to allow individual users establish their own interpretative networks amongst the concrete nodes.
 * </p>
 *
 * <p>It should however, be clear, that semantic integrity would rapidly be lost if it were permitted for users to attach arbitrary
 * production relationships to media items or for them to establish arbitrary relationships between media items. This integrity
 * cannot easily be assured through the use of database constraints as it cannot be represented effectively in the form of tables. The
 * relationships at stake are represented not so much in the tuples but in the semantic content of those tuples. Whilst the database
 * will ensure that the relationship records point to actual item records, it cannot know that certain combinations of content
 * are nonsensical. Instead, we rely on the <code>RulesManager</code> to verify the addition or removal of relationships from a mediaItem
 * before methods to manipulate the mediaItem and its relationships are actually called. In compliance with the <code>SessionFacade</code>
 * pattern, the client will invoke methods on a session bean which performs a semantic integrity check via the <code>RulesManager</code>
 * before invoking the persistence layer. The facade beans are also responsible for further business logic checks including validating 
 * completeness, field size and such issues as ensuring no items are submitted with a purported year of creation in the future or 
 * before the known dawn of writing.
 * </p>
 *
 * <h3>Note on naming convention for finder methods</h3>
 * <p>In order to make the construction of clients easier (such as Struts <code>Action</code> classes), a standardised
 * naming convention for the finder methods for the LocalHome interface has been adapted. A client will be able to construct
 * a string representing the appropriate method name to call depending on which fields exactly have been submitted.</p>
 * <h4>findBy</h4>
 * <p>All finder methods start with <code>find</code>.</p>
 * <h4>Order Of items to search</h4>
 * <p>Depending on which fields are submitted, method names will be constructed in this order: </p>
 * <ol>
 * <li>Person or Organisation</li>
 * <li>Relationship</li>
 * <li>Title or LikeTitle</li>
 * <li>Type</li>
 * <li>Language</li>
 * <li>Year: ExactYear, BeforeYear, AfterYear, BetweenYears
 * </ol>
 * 
 *******************************************************************************
 * FINDER METHOD DEFINITIONS                                                   *
 ******************************************************************************* 
 *
 *
 *******************************************************************************
 *  START OF PURE MEDIA ITEM FINDER METHOD DEFINITIONS                         *
 *******************************************************************************
 *
 * TITLE ***********************************************************************
 *
 * @ejb.finder signature="java.util.Collection findByTitle (java.lang.String title, boolean publishedFlag)"
 *             query=    "select Object(MI)
 *                        from MediaItem as MI 
 *                        where MI.title = ?1 and MI.publishedFlag = ?2"
 *
 * @ejb.finder signature="java.util.Collection findByTitleType (java.lang.String title, java.lang.String type, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTitleLanguage (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and ?2 member of MI.languages and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTitleExactYear (java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.yearOfCreation = ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTitleBeforeYear (java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.yearOfCreation < ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTitleAfterYear (java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.yearOfCreation > ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTitleBetweenYears (java.lang.String title, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.yearOfCreation between ?2 and ?3 and MI.publishedFlag = ?4"
 *
 * LIKE TITLE ******************************************************************
 *
 * @ejb.finder  signature="java.util.Collection findByLikeTitle (java.lang.String title, boolean publishedFlag)"
 *              query=    "select Object (MI)
 *                         from MediaItem as MI
 *                         where MI.title like '?1' and MI.publishedFlag = ?2"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitle (java.lang.String title, boolean publishedFlag)"
 *              query =   "select Object(MI)
 *                         from MediaItem as MI
 *                         where MI.title like ?1 and MI.publishedFlag = ?2"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleType(java.lang.String title, java.lang.String mediaType, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and MI.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleType(java.lang.String title, java.lang.String mediaType, boolean publishedFlag)"
 *              query =   "select Object(MI)
 *                         from MediaItem as MI
 *                         where MI.title like ?1 and MI.mediaType = ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleLanguage(java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and ?2 member of MI.languages and MI.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleLanguage(java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and ?2 member of MI.languages and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleExactYear(java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.yearOfCreation = ?2 and MI.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleExactYear(java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.yearOfCreation = ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleBeforeYear(java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.yearOfCreation < ?2 and MI.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleBeforeYear(java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.yearOfCreation < ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleAfterYear(java.lang.String title, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.yearOfCreation > ?2 and MI.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleAfterYear(java.lang.String title, int year, boolean publishedFlag)"
 *              query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.yearOfCreation > ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleBetweenYears(java.lang.String title, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.yearOfCreation between ?2 and ?3 and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleBetweenYears(java.lang.String title, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.yearOfCreation between ?2 and ?3 and MI.publishedFlag = ?4"
 *
 * TYPE ************************************************************************
 *
 * @ejb.finder signature="java.util.Collection findByType (java.lang.String mediaType, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and MI.publishedFlag = ?2" 
 *
 * @ejb.finder signature="java.util.Collection findByTypeLanguage (java.lang.String mediaType, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and ?2 member of MI.languages and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTypeExactYear (java.lang.String mediaType, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and MI.yearOfCreation = ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTypeBeforeYear (java.lang.String mediaType, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and MI.yearOfCreation < ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTypeAfterYear (java.lang.String mediaType, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and MI.yearOfCreation > ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByTypeBetweenYears (java.lang.String mediaType, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and MI.yearOfCreation between ?2 and ?3 and MI.publishedFlag = ?4"
 *
 * LANGUAGE ********************************************************************
 *
 * @ejb.finder  signature="java.util.Collection findByLanguage(org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *              query=      "select Object(MI) 
 *                          from MediaItem as MI where ?1 member of MI.languages
 *                          and MI.publishedFlag = ?2"
 *
 * @ejb.finder signature="java.util.Collection findByLanguageExactYear(org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI where ?1 member of MI.languages
 *                        and MI.yearOfCreation = ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLanguageBeforeYear(org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI where ?1 member of MI.languages
 *                        and MI.yearOfCreation < ?2 and MI.publishedFlag = ?3" 
 *
 * @ejb.finder signature="java.util.Collection findByLanguageAfterYear(org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI where ?1 member of MI.languages
 *                        and MI.yearOfCreation > ?2 and MI.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByLanguageBetweenYears(org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI where ?1 member of MI.languages
 *                        and MI.yearOfCreation between ?2 and ?3 and MI.publishedFlag = ?4"
 *
 * TITLE AND TYPE **************************************************************
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeLanguage(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeExactYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeBeforeYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeAfterYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeBetweenYears(java.lang.String title, java.lang.String type, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * LIKE TITLE AND TYPE *********************************************************
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeLanguage(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and ?3 member of MI.languages and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeLanguage(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *              query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeExactYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeExactYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *              query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeBeforeYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeBeforeYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeAfterYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeAfterYear(java.lang.String title, java.lang.String type, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4" 
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeBetweenYears(java.lang.String title, java.lang.String type, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeBetweenYears(java.lang.String title, java.lang.String type, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * TYPE AND LANGUAGE ***********************************************************
 *
 * @ejb.finder signature="java.util.Collection findByTypeLanguageExactYear (java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and ?2 member of MI.languages and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTypeLanguageBeforeYear (java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and ?2 member of MI.languages and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTypeLanguageAfterYear (java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and ?2 member of MI.languages and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTypeLanguageBetweenYears (java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.mediaType = ?1 and ?2 member of MI.languages and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * TITLE AND LANGUAGE **********************************************************
 *
 * @ejb.finder signature="java.util.Collection findByTitleLanguageExactYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and ?2 member of MI.languages and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleLanguageBeforeYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and ?2 member of MI.languages and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleLanguageAfterYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and ?2 member of MI.languages and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByTitleLanguageBetweenYears (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and ?2 member of MI.languages and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * LIKE TITLE AND LANGUAGE *****************************************************
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleLanguageExactYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and ?2 member of MI.languages and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 * 
 * @jboss.query signature="java.util.Collection findByLikeTitleLanguageExactYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and ?2 member of MI.languages and MI.yearOfCreation = ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleLanguageBeforeYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and ?2 member of MI.languages and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleLanguageBeforeYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and ?2 member of MI.languages and MI.yearOfCreation < ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleLanguageAfterYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and ?2 member of MI.languages and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleLanguageAfterYear (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and ?2 member of MI.languages and MI.yearOfCreation > ?3 and MI.publishedFlag = ?4"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleLanguageBetweenYears (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and ?2 member of MI.languages and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleLanguageBetweenYears (java.lang.String title, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and ?2 member of MI.languages and MI.yearOfCreation between ?3 and ?4 and MI.publishedFlag = ?5"
 *
 * TITLE, TYPE AND LANGUAGE ****************************************************
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeLanguageExactYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation = ?4 and MI.publishedFlag = ?5"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeLanguageBeforeYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation < ?4 and MI.publishedFlag = ?5"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeLanguageAfterYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation > ?4 and MI.publishedFlag = ?5"
 *
 * @ejb.finder signature="java.util.Collection findByTitleTypeLanguageBetweenYears(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title = ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation between ?4 and ?5 and MI.publishedFlag = ?6"
 *
 * LIKE TITLE, TYPE AND LANGUAGE ***********************************************
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeLanguageExactYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation = ?4 and MI.publishedFlag = ?5"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeLanguageExactYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation = ?4 and MI.publishedFlag = ?5"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeLanguageBeforeYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation < ?4 and MI.publishedFlag = ?5"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeLanguageBeforeYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation < ?4 and MI.publishedFlag = ?5"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeLanguageAfterYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation > ?4 and MI.publishedFlag = ?5"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeLanguageAfterYear(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int year, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation > ?4 and MI.publishedFlag = ?5"
 *
 * @ejb.finder signature="java.util.Collection findByLikeTitleTypeLanguageBetweenYears(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like '?1' and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation between ?4 and ?5 and MI.publishedFlag = ?6"
 *
 * @jboss.query signature="java.util.Collection findByLikeTitleTypeLanguageBetweenYears(java.lang.String title, java.lang.String type, org.authorsite.bib.ejb.entity.LanguageLocal language, int firstYear, int lastYear, boolean publishedFlag)"
 *             query =   "select Object(MI)
 *                        from MediaItem as MI
 *                        where MI.title like ?1 and MI.mediaType = ?2 and ?3 member of MI.languages and MI.yearOfCreation between ?4 and ?5 and MI.publishedFlag = ?6"
 *
 * YEAR ************************************************************************
 *
 * @ejb.finder  signature="java.util.Collection findByExactYear(int year, boolean publishedFlag)"
 *              query=    "select Object(MI)
 *                         from MediaItem as MI where MI.yearOfCreation = ?1 and MI.publishedFlag = ?2"
 *
 * @ejb.finder  signature="java.util.Collection findByBeforeYear(int year, boolean publishedFlag)"
 *              query=    "select Object(MI)
 *                         from MediaItem as MI where MI.yearOfCreation < ?1 and MI.publishedFlag = ?2"
 *
 * @ejb.finder signature="java.util.Collection findByAfterYear(int year, boolean publishedFlag)"
 *             query=    "select Object (MI)
 *                        from MediaItem as MI where MI.yearOfCreation > ?1 and MI.publishedFlag = ?2"
 * 
 * @ejb.finder  signature="java.util.Collection findByBetweenYears (int firstYear, int lastYear, boolean publishedFlag)"
 *              query=    "select Object(MI)
 *                         from MediaItem as MI where MI.yearOfCreation between ?1 and ?2 and MI.publishedFlag = ?3"
 *
 *******************************************************************************
 * END OF PURE MEDIA ITEM FINDER METHOD DEFINITIONS                            *
 *******************************************************************************
 *
 *******************************************************************************
 * PERSON ORIENTATED FINDER METHODS                                            *
 *******************************************************************************
 *
 * @ejb.finder  signature="java.util.Collection findByPerson (org.authorsite.bib.ejb.entity.PersonLocal person, boolean publishedFlag)"
 *              query=     "select MPR.mediaItem
 *                          from MediaProductionRelationship as MPR
 *                          where ?1 member of MPR.people and MPR.mediaItem.publishedFlag = ?2"
 *
 * @ejb.finder  signature="java.util.Collection findByPersonRelationship(org.authorsite.bib.ejb.entity.PersonLocal person,
 *                              java.lang.String relationshipType, boolean publishedFlag)"
 *              query="    select MPR.mediaItem
 *                         from MediaProductionRelationship as MPR
 *                         where ?1 member of MPR.people and MPR.relationshipType = ?2
 *                         and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByPersonTitle(org.authorsite.bib.ejb.entity.PersonLocal person,
 *                              java.lang.String title, boolean publishedFlag)"
 *             query =   "select MPR.mediaItem
 *                        from MediaProductionRelationship as MPR
 *                        where ?1 member of MPR.people and MPR.mediaItem.title = ?2 and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByPersonLikeTitle(org.authorsite.bib.ejb.entity.PersonLocal person,
 *                              java.lang.String title, boolean publishedFlag)"
 *              query =  "select MPR.mediaItem
 *                        from MediaProductionRelationship as MPR
 *                        where ?1 member of MPR.people and MPR.mediaItem.title like '?2' and MPR.mediaItem.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByPersonLikeTitle(org.authorsite.bib.ejb.entity.PersonLocal person,
 *                              java.lang.String title, boolean publishedFlag)"
 *              query =  "select MPR.mediaItem
 *                        from MediaProductionRelationship as MPR
 *                        where ?1 member of MPR.people and MPR.mediaItem.title like ?2 and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder  signature="java.util.Collection findByPersonLanguage(org.authorsite.bib.ejb.entity.PersonLocal person,
 *                            org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *              query=    " select MPR.mediaItem
 *                          from MediaProductionRelationship as MPR
 *                          where ?1 member of MPR.people and ?2 member of MPR.mediaItem.languages
 *                          and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder  signature="java.util.Collection findByPersonType(org.authorsite.bib.ejb.entity.PersonLocal person,
 *                              java.lang.String mediatype, boolean publishedFlag)"
 *              query=     "select MPR.mediaItem
 *                          from MediaProductionRelationship as MPR
 *                          where ?1 member of MPR.people and MPR.mediaItem.mediaType = ?2
 *                          and MPR.mediaItem.publishedFlag = ?3"
 *
 * ** FOLLOWING METHODS NEED REVISION **
 *
  *******************************************************************************
 * ORGANISATION ORIENTATED FINDER METHODS                                      *
 *******************************************************************************
 *
 * @ejb.finder  signature="java.util.Collection findByOrganisation (org.authorsite.bib.ejb.entity.OrganisationLocal organisation, boolean publishedFlag)"
 *              query=     "select MPR.mediaItem
 *                          from MediaProductionRelationship as MPR
 *                          where ?1 member of MPR.organisations and MPR.mediaItem.publishedFlag = ?2"
 *
 * @ejb.finder  signature="java.util.Collection findByOrganisationRelationship(org.authorsite.bib.ejb.entity.OrganisationLocal organisation,
 *                              java.lang.String relationshipType, boolean publishedFlag)"
 *              query="    select MPR.mediaItem
 *                         from MediaProductionRelationship as MPR
 *                         where ?1 member of MPR.organisations and MPR.relationshipType = ?2
 *                         and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByOrganisationTitle(org.authorsite.bib.ejb.entity.OrganisationLocal organisation,
 *                             java.lang.String title, boolean publishedFlag)"
 *              query =  "select MPR.mediaItem
 *                        from MediaProductionRelationship as MPR
 *                        where ?1 member of MPR.organisations and MPR.mediaItem.title = ?2
 *                        and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder signature="java.util.Collection findByOrganisationLikeTitle(org.authorsite.bib.ejb.entity.OrganisationLocal organisation,
 *                             java.lang.String title, boolean publishedFlag)"
 *              query =  "select MPR.mediaItem
 *                        from MediaProductionRelationship as MPR
 *                        where ?1 member of MPR.organisations and MPR.mediaItem.title like '?2'
 *                        and MPR.mediaItem.publishedFlag = ?3"
 *
 * @jboss.query signature="java.util.Collection findByOrganisationLikeTitle(org.authorsite.bib.ejb.entity.OrganisationLocal organisation,
 *                             java.lang.String title, boolean publishedFlag)"
 *              query =  "select MPR.mediaItem
 *                        from MediaProductionRelationship as MPR
 *                        where ?1 member of MPR.organisations and MPR.mediaItem.title like ?2
 *                        and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder  signature="java.util.Collection findByOrganisationLanguage(org.authorsite.bib.ejb.entity.OrganisationLocal organisation,
 *                            org.authorsite.bib.ejb.entity.LanguageLocal language, boolean publishedFlag)"
 *              query=    " select MPR.mediaItem
 *                          from MediaProductionRelationship as MPR
 *                          where ?1 member of MPR.organisations and ?2 member of MPR.mediaItem.languages
 *                          and MPR.mediaItem.publishedFlag = ?3"
 *
 * @ejb.finder  signature="java.util.Collection findByOrganisationType(org.authorsite.bib.ejb.entity.OrganisationLocal organisation,
 *                              java.lang.String mediatype, boolean publishedFlag)"
 *              query=     "select MPR.mediaItem
 *                          from MediaProductionRelationship as MPR
 *                          where ?1 member of MPR.organisations and MPR.mediaItem.mediaType = ?2
 *                          and MPR.mediaItem.publishedFlag = ?3"
 *
 *******************************************************************************
 * and now the findUnpublished... definitions
 *******************************************************************************
 *
 * @ejb:finder  signature="java.util.Collection findAllUnpublished()"
 *              query=     "select Object(MI)
 *                          from MediaItem as MI
 *                          where MI.publishedFlag = false"
 *
 *******************************************************************************
 * END OF FINDER METHOD XDOCLET TAGS !!                                        *
 *******************************************************************************
 *
 * @ejb:bean name="MediaItemEJB"
 *              type="CMP"
 *              reentrant="false"
 *              cmp-version="2.x"
 *              schema="MediaItem"
 *              primkey-field="mediaItemID"
 *              view-type="local"
 *              local-jndi-name="ejb/MediaItemLocalEJB"
 *
 * @ejb:interface local-class="org.authorsite.bib.ejb.entity.MediaItemLocal"
 *                generate="local"
 * @ejb:home    local-class="org.authorsite.bib.ejb.entity.MediaItemLocalHome"
 *              generate="local"
 * @ejb:pk class="java.lang.Integer"
 *
 * @ejb:transaction type="supports"
 *
 * @jboss:table-name table-name="mediaItem"
 *
 * // removed eager loading for now.
 *
 * @jboss:cmp-field field-name="mediaItemID" column-name="mediaItemID"
 * @jboss:cmp-field field-name="title" column-name="title"
 * @jboss:cmp-field field-name="mediaType" column-name="mediaType"
 * @jboss:cmp-field field-name="yearOfCreation" column-name="yearOfCreation"
 * @jboss:cmp-field field-name="publishedFlag" column-name="publishedFlag"
 * @jboss:cmp-field field-name="additionalInfo" column-name="additionalInfo"
 * @jboss:cmp-field field-name="comment" column-name="comment"
 *
 * @author jejking
 * @version $Revision: 1.18 $
 * @see org.authorsite.bib.ejb.services.rules.RulesProcessor
 * @see org.authorsite.bib.ejb.services.rules.RulesManagerBean
 * @see org.authorsite.bib.ejb.services.dto.MediaItemDTOAssembler
 * @see org.authorsite.bib.dto.MediaItemDTO
 * @see org.authorsite.bib.ejb.entity.MediaProductionRelationshipBean
 * @see org.authorsite.bib.ejb.entity.IntraMediaRelationshipBean
 * @see org.authorsite.bib.ejb.entity.LanguageBean
 *
 * @todo Possible Refactoring. We need to investigate the exact structuring of the way in which intramediaRelationships
 * are implemented. It may make more sense to have them managed using CMR as a group of references managed through the 
 * parent using get/set methods returning/taking Set/Container objects. It may also make sense to treat containment
 * as a special case as it is so intrinsic to the contruction of mediaItems that it maybe should not be managed in the
 * same way...
 * @todo Tuning. Further refine the JBoss tuning parameters as far as possible.
 */

public abstract class MediaItemBean implements EntityBean {
    
    private EntityContext ctx;
    
    /** Creates a new MediaItem entity bean.
     * @ejb:create-method
     * @param mediaID The primary key. This will normally be obtained from
     * a primary key factory.
     * @param title String representing the title of the MediaItem.
     * @param mediaType String representing the mediaType. Valid mediaTypes
     * should be obtained from a session bean (TODO).
     * @param yearOfCreation Integer representing the approximate or exact date
     * of creation of the mediaItem. In the case of an
     * item created over a period of time, use the year
     * when the item was completed.
     * @throws CreateException The bean will throw a create exception when given
     * invalid or null data. Create exceptions will also
     * be thrown when data is entered that breaks the
     * database constraints.
     */
    public Integer ejbCreate(int mediaID, String title, String mediaType, int yearOfCreation) throws CreateException {
        setMediaItemID(new Integer(mediaID));
        setTitle(title);
        setMediaType(mediaType);
        setYearOfCreation(yearOfCreation);
        return null;
    }
    
    public void ejbPostCreate(int mediaID, String title, String mediaType, int yearOfCreation) {
    }
    
    /**
     * @ejb:persistent-field
     * @ejb:pk-field
     * @ejb:interface-method view-type="local"
     */
    public abstract Integer getMediaItemID();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setMediaItemID(Integer newMediaID);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getTitle();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setTitle(String newTitle);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getMediaType();
    
    /**
     * @ejb:persistent-field
     */
    public abstract void setMediaType(String newMediaType);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract int getYearOfCreation();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setYearOfCreation(int newYear);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract boolean getPublishedFlag();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setPublishedFlag(boolean newFlag);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getAdditionalInfo();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setAdditionalInfo(String newInfo);
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract String getComment();
    
    /**
     * @ejb:persistent-field
     * @ejb:interface-method view-type="local"
     */
    public abstract void setComment(String newComment);
    
    /**
     * @ejb:relation    name="media-languages"
     *                  target-ejb="LanguageEJB"
     *                  role-name="mediaItem-is-in-languages"
     *                  target-role-name="languages-are-manifested-in-mediaItems"
     *                  target-multiple="yes"
     *
     * @ejb:interface-method view-type="local"
     *
     * @jboss:relation-mapping style="relation-table"
     * @jboss:relation-table table-name="mediaItemLanguage"
     * @jboss:target-relation related-pk-field="mediaItemID" fk-column="mediaItemID"
     * @jboss:relation related-pk-field="iso639" fk-column="iso639"
     */
    public abstract Set getLanguages();
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public abstract void setLanguages(Set newLanguages);
    
    /**
     * @ejb:relation    name="media-productionRelationships"
     *                  role-name="mediaItem-has-productionRelationships"
     *                  
     * @ejb:interface-method view-type="local"
     * 
     */
    public abstract Set getMediaProductionRelationships();
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public abstract void setMediaProductionRelationships(Set newMediaProductionRelationships);
    
    /**
     * @ejb.select query="Select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                    where MI.mediaItemID = IMR.mediaToID
     *                    and IMR.mediaFromID = ?1 and IMR.relationshipType = 'containment'"
     */
    public abstract Set ejbSelectContainer(int mediaItemID) throws FinderException;
    
    /**
     * Gets the mediaItem which has a 'containment' relationship to this item. Used to build the hierarchy
     * of containment in scenarios such as a mediaArticle contained in a journalEdition contained in a journal.
     * @ejb.interface-method view-type="local"
     */
    public Set getContainer() {
        try {
            Set containerSet = ejbSelectContainer(getMediaItemID().intValue());
            return containerSet;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb.select query="select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                    where MI.mediaItemID = IMR.mediaToID
     *                    and IMR.mediaFromID = ?1"
     */
    public abstract Collection ejbSelectAllChildren(int mediaItemID) throws FinderException;
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public Collection getAllChildren() {
        try {
            Collection allChildren = ejbSelectAllChildren(getMediaItemID().intValue());
            return allChildren;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select  query="select Object (MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *              where MI.mediaItemID = IMR.mediaToID
     *              and IMR.mediaFromID = ?1 and MI.publishedFlag = true"
     */
    public abstract Collection ejbSelectPublishedChildren(int mediaItemID) throws FinderException;
    
    /**
     * Gets any children this mediaItem may have by calling ejbSelectChildren()
     * @ejb:interface-method view-type="local"
     */
    public Collection getPublishedChildren() {
        try {
            Collection myPublishedChildren = ejbSelectPublishedChildren(getMediaItemID().intValue());
            return myPublishedChildren;
        }
        catch (FinderException fe) {
            // there were no children, return empty set
            return null;
        }
    }
    
    /**
     *  @ejb:select query=   "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                        where MI.mediaItemID = IMR.mediaToID
     *                        and IMR.mediaFromID = ?1 and MI.publishedFlag = false"
     */
    public abstract Collection ejbSelectUnpublishedChildren(int mediaItemID) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getUnpublishedChildren() {
        try {
            Collection myUnpublishedChildren = ejbSelectUnpublishedChildren(getMediaItemID().intValue());
            return myUnpublishedChildren;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
       /**
     * @ejb:select  query=    "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *              where MI.mediaItemID = IMR.mediaToID
     *              and IMR.mediaFromID = ?1 and IMR.relationshipType = ?2"
        */
    public abstract Collection ejbSelectAllChildrenOfType(int mediaItemID, String relationshipType) throws FinderException;
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public Collection getAllChildrenOfType(String relationshipType) {
        try {
            Collection allMyChildrenOfType = ejbSelectAllChildrenOfType(getMediaItemID().intValue(), relationshipType);
            return allMyChildrenOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select  query=    "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *              where MI.mediaItemID = IMR.mediaToID
     *              and IMR.mediaFromID = ?1 and IMR.relationshipType = ?2 and MI.publishedFlag = true"
     */
    public abstract Collection ejbSelectPublishedChildrenOfType(int mediaItemID, String relationshipType) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getPublishedChildrenOfType(String relationshipType) {
        try {
            Collection myPublishedChildrenOfType = ejbSelectPublishedChildrenOfType(getMediaItemID().intValue(), relationshipType);
            return myPublishedChildrenOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     *  @ejb:select        query=    "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                     where MI.mediaItemID = IMR.mediaToID
     *                     and IMR.mediaFromID = ?1 and IMR.relationshipType = ?2 and MI.publishedFlag = false"
     */
    public abstract Collection ejbSelectUnpublishedChildrenOfType(int mediaItemID, String relationshipType) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getUnpublishedChildrenOfType(String relationshipType) {
        try {
            Collection myUnpublishedChildrenOfType = ejbSelectUnpublishedChildrenOfType(getMediaItemID().intValue(), relationshipType);
            return myUnpublishedChildrenOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select query =  "select Object (MI) from MediaItem as MI, IntraMediaRelationship as IMR 
     *                       where MI.mediaItemID = IMR.mediaToID
     *                       and IMR.mediaFromID = ?1 and IMR.relationshipType = ?2 and IMR.relationshipQualifier = ?3
     *                       and MI.publishedFlag = false"
     */
    public abstract Collection ejbSelectUnpublishedQualifiedChildrenOfType(int mediaItemID, String relationshipType, 
        String relationshipQualifier) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getUnpublishedQualifiedChildrenOfType(String relationshipType, String relationshipQualifier) {
        try {
            Collection myUnpublishedQualifiedChildrenOfType = ejbSelectUnpublishedQualifiedChildrenOfType(getMediaItemID().intValue(),
                                                                    relationshipType, relationshipQualifier);
            return myUnpublishedQualifiedChildrenOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select query =  "select Object (MI) from MediaItem as MI, IntraMediaRelationship as IMR 
     *                       where MI.mediaItemID = IMR.mediaToID
     *                       and IMR.mediaFromID = ?1 and IMR.relationshipType = ?2 and IMR.relationshipQualifier = ?3
     *                       and MI.publishedFlag = true" 
     */
    public abstract Collection ejbSelectPublishedQualifiedChildrenOfType(int mediaItemID, String relationshipType, 
        String relationshipQualifier) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getPublishedQualifiedChildrenOfType(String relationshipType, String relationshipQualifier) {
        try {
            Collection myPublishedQualifiedChildrenOfType = ejbSelectPublishedQualifiedChildrenOfType(getMediaItemID().intValue(),
                                                                    relationshipType, relationshipQualifier);
            return myPublishedQualifiedChildrenOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select query=   "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                       where MI.mediaItemID = IMR.mediaFromID
     *                       and IMR.mediaToID = ?1 and MI.publishedFlag = true"
     */
    public abstract Collection ejbSelectPublishedParents(int mediaItemID) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getPublishedParents() {
        try {
            Collection myPublishedParents = ejbSelectPublishedParents(getMediaItemID().intValue());
            return myPublishedParents;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select  query =  "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                        where MI.mediaItemID = IMR.mediaFromID
     *                        and IMR.mediaToID = ?1 and MI.publishedFlag = false"
     */
    public abstract Collection ejbSelectUnpublishedParents(int mediaItemID) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getUnpublishedParents() {
        try {
            Collection myUnpublishedParents = ejbSelectUnpublishedParents(getMediaItemID().intValue());
            return myUnpublishedParents;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb:select query=    "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                        where MI.mediaItemID = IMR.mediaFromID 
     *                        and IMR.mediaToID = ?1 and IMR.relationshipType = ?2 
     *                        and MI.publishedFlag = true"
     */
    public abstract Collection ejbSelectPublishedParentsOfType(int mediaItemID, String relationshipType) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getPublishedParentsOfType(String relationshipType) {
        try {
            Collection myPublishedParentsOfType = ejbSelectPublishedParentsOfType(getMediaItemID().intValue(), relationshipType);
            return myPublishedParentsOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     *  @ejb:select query=    "select Object(MI) from MediaItem as MI, IntraMediaRelationship as IMR
     *                         where MI.mediaItemID = IMR.mediaFromID 
     *                         and IMR.mediaToID = ?1 and IMR.relationshipType = ?2 
     *                         and MI.publishedFlag = false" 
     */
    public abstract Collection ejbSelectUnpublishedParentsOfType(int mediaItemID, String relationshipType) throws FinderException;
    
    /**
     * @ejb:interface-method view-type="local"
     */
    public Collection getUnpublishedParentsOfType(String relationshipType) {
        try {
            Collection myUnpublishedParentsOfType = ejbSelectUnpublishedParentsOfType(getMediaItemID().intValue(), relationshipType);
            return myUnpublishedParentsOfType;
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb.select query = "select distinct IMR.relationshipType from IntraMediaRelationship as IMR
     *                      where IMR.mediaFromID = ?1"
     */
    public abstract Collection ejbSelectChildIntraMediaRelationships(int mediaItemID) throws FinderException;
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public Collection getChildIntraMediaRelationships() {
        try {
            return ejbSelectChildIntraMediaRelationships(getMediaItemID().intValue());
        }
        catch (FinderException fe) {
            return null;
        }
    }
    
    /**
     * @ejb.select query = "select distinct IMR.relationshipType from IntraMediaRelationship as IMR
     *                      where IMR.mediaToID = ?1"
     */ 
    public abstract Collection ejbSelectParentIntraMediaRelationships(int mediaItemID) throws FinderException;
    
    /**
     * @ejb.interface-method view-type="local"
     */
    public Collection getParentIntraMediaRelationships() {
        try {
            return ejbSelectParentIntraMediaRelationships(getMediaItemID().intValue());
        }
        catch (FinderException fe) {
            return null;
        }
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
    
    /** Creates a new instance of MediaItem */
    public MediaItemBean() {
    }
    
    
}
