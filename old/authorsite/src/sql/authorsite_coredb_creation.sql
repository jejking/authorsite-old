/*
 * Authorsite.org Bibliography Application
 * Core Database Tables Creation Script for PostgreSQL
 * Revision: $Header: /cvsroot/authorsite/authorsite/src/sql/authorsite_coredb_creation.sql,v 1.11 2003/03/29 13:17:04 jejking Exp $ 
 * 
 * Function
 * This script creates and maintains the core database tables required for the 
 * authorsite.org bibliographic application. 
 *  - drops any core tables still in existence.
 *  - creates the central table, mediaItem
 *  - enables a mediaItem to have multiple language attributes by creating the
 *    tables language and mediaItemLanguage join table.
 *  - creates a common mediaProducer table, and two sub-tables:
 *    mediaProducerPerson and mediaProducerOrganisation
 *  - enables the the mediaItem to participate in multiple relationships with
 *    other mediaItems and with mediaProducers (both people and organisations)
 *    by creating the relationship table for referential integrity and by
 *    creating the join tables intraMediaRelationship and
 *    mediaProducerRelationship
 *  - annotates tables and columns using the PostgreSQL comment function
 *
 * Use
 *
 * Whilst this script may be run directly, it will normally be run by ant build scripts.
 * 
 * It must be run prior to the execution of the database scripts which create further
 * media type specific details tables and which populate the language table and the 
 * relationshipType and mediaType constraint tables.
 *
 * A further production script will define the creation of indexes, users, etc.
 *
 * Copyright (C) 2002, 2003  John King
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

-- drop tables
drop table mediaType;
drop table mediaItem;
drop table language;
drop table mediaItemLanguage;
drop table relationshipType;
drop table intraMediaRelationship;
drop table person;
drop table organisation;
drop table personMediaProdRel;
drop table orgMediaProdRel;
drop table mediaProductionRelationship;
drop table sequencesList;
drop table appUser;
drop table appRoles;
drop table appUserRoles;

/*
 * mediaItemType is a referential integrity table
 * Its contents are generated from a SQL script constructed during
 * the build process by an Ant task from the configuration XML file
 */
create table mediaType (
    mediaType           varchar(20) primary key
); 
comment on table mediaType is
	'referential integrity table for mediaItem.';

/*
 * table mediaItem
 * mediaItem is maybe *the* central table in the system. From it, mediaItems are
 * related to one another through the intraMediaRelationship table (which also allows
 * recursive construction of complex mediaItem types - eg a newspaper article contained
 * in a newspaper section contained in a newspaper printing contained in a newspaper). 
 * Relationships to people and organisations are faciliated through the mediaProducerRelationship
 * table
 *
 * column mediaID - Primary Key
 * Integer was chosen to be the type of the primary key as indexing of integer columns
 * should incur less overhead than indexing complex string columns. The primary key value to be 
 * inserted will always be determined prior to an insert by the application. Primary keys are 
 * provided by the application through the SequenceBlockPrimaryKeyGenerator EJB.
 *
 * column title
 * It is assumed that all media items will be given some sort of title or signifier, even if this
 * might be no more than a catalogue entry. At any rate, some form of human readable name is considered necessary,
 * hence the not null constraint.
 * Experience with general Western media suggests that	1000 characters should be sufficient.
 *
 * column mediaType
 * Logical type integrity is maintained at the database level by the foreign key relationship to the
 * mediaType table. Whilst the java application will do its utmost to guarantee data integrity, the extra
 * security helps ensure that possible non-java database access will not compromise data integrity. 
 * Because the actual mediaItemTypes are not known before the system is built, the fields here are also
 * reflected in the mediaDetails tables. 
 * 
 * column yearOfCreation
 * Simple integer field to contain an exact or approximate year of production.
 * 
 * column published
 * Published is a boolean field controlled by the rules engine. When a media item is first created, it exists
 * only in this table and its obligatory relationships and other attributes have not yet been created. It can 
 * therefore not constitute part of a full media record and is therefore not considered published. When the rules
 * engine establishes (at the client's behest), that all obligatory relationships have been created and that all 
 * minimum data is set, then the flag can be switched to true and the item is available to the public.
 *
 * column additionalInfo
 * The additonalInfo field allows the application user to specifiy free-form additional information.
 * it is intended to hold further bibliographically relevant information that cannot be accommodated in
 * the details fields for the particular mediaItem type.
 * 
 * column comment
 * The comment field allows the application users to insert whatever comments they consider necessary.
 * There is no limit on the size of comments, hence the text data type is used.
 */
create table mediaItem (
    mediaItemID         integer primary key,
    title               varchar (1000) not null,
    mediaType           varchar (20) not null,
	yearOfCreation		integer not null,
	publishedFlag		boolean default 'false',
	additionalInfo		text,
    comment             text,
    constraint          mediaTypeFK foreign key (mediaType) 
                            references mediaType (mediaType)
);
comment on table mediaItem is 'the central table linked via relationship tables to other mediaItems and mediaProducers	.';
comment on column mediaItem.title is 'title or other human readable identifier';
comment on column mediaItem.yearOfCreation is 'exact or approximate year of creation';
comment on column mediaItem.publishedFlag is 'Boolean flag that describes whether an item is publically viewable or not';
comment on column mediaItem.mediaType is 'identifies the type of media item';
comment on column mediaItem.additionalInfo is 'for free-form additional bibliographic information not described in details fields';
comment on column mediaItem.comment is 'for user comments about the mediaItem, such as an abstract or review';

/*
 * table language
 * Language table exists primarily to guarantee content logical integrity with regard to languages.
 * 
 * column iso639
 * Languages are constrained to those defined in the ISO 639-2 standard which provides a natural
 * primary key in the form of the 3 letter alpha code.
 *
 * column engName 
 * The English name is included for the sake of convenience when performing queries directly at the 
 * database level. It will not be used in the java applications since these will reference the three letter
 * code to internationalization resource bundles. It is set at 200 as some of the ISO 639 languages have complex
 * compound English names (Old Church Slavonic, etc, etc,).
 *
 * column priority
 * priority is a field which determines the order in which languages are presented to the user.
 * The thinking is that for a given bibliographic domain, certain languages will predominate (for instance
 * for Ernst Juenger, these would be, say, German, French, English, Italian, Spanish). These would be priority 1
 * and thus always displayed. Should a user encounter a work not in one of these main languages, he or she may then
 * decide to select the next level of languages, and so on.
 * It is set in the languages.xml file and is thus configurable (currently at build time). The default
 * languages.xml values are 2 for languages which exist in the smaller ISO 639-1 language set and 3 for
 * all other languages. Priorities 1 and 4 must be set by hand.
 * The check constraint ensures that only values of 1, 2, 3 or 4 can be set (as does languageSchema.xsd).
 */
create table language (
    iso639              char(3) primary key,
    engName             varchar(200) not null,
	priority			integer not null,
	constraint 			priorityCheck check (priority > 0 and priority < 5)
);
comment on table language is 'integrity constraint table for mediaItemLanguage';
comment on column language.iso639 is 'ISO 639-2 three letter language code';
comment on column language.engName is 'English name of language for convenience';
comment on column language.priority is 'determines order in which languages will be displayed';

/*
 * table mediaItemLanguage
 * mediaItemLanguage is a simple join table resolving the many-many relationship between
 * mediaItem and language.
 * Whilst most mediaItems will be mostly in one language, some may exist in two or more. For instance,
 * there may be a bi-lingual edition of a book or a DVD may have many soundtracks or subtitles.
 */
create table mediaItemLanguage (
	mediaItemID         integer not null,
    iso639              char(3) not null,
	constraint 			mediaItemLanguagePK primary key (mediaItemID, iso639),
    constraint          mediaFK foreign key (mediaItemID)
                            references mediaItem (mediaItemID),
    constraint          langFK foreign key (iso639)
                            references language (iso639)
);
comment on table mediaItemLanguage is 'join table between mediaItem and language';

/*
 * table Person
 * Person includes details we can reasonably expect about a given individual person within the context of a 
 * bibliographic application. It specifically does not include contact information of any description as this 
 * is not only irrelevant to the domain model but would also be  in many cases either extremely hard to come by,
 * if not utterly irrelvant (anyone looking for Homer's email?).
 *
 * column prefix
 * Some people include a prefix in their name. Examples would include (Dr, Sir, Lord, Reichsgraf, Comte). There are no
 * integrity constraints here as the list of possible prefixes is essentially limitless
 *
 * column givenName
 * For someone's 'first name' or 'Christian name', or at least from a Euro-Centric point of view. In Ernst Juenger's case,
 * this would be Ernst.
 *
 * column mainName
 * In the vast majority of cases, this would equate to the family name (e.g. Juenger, King). However, it may also be
 * used for cases where a person is known by one name only (e.g. Homer). It may also equate to anonymous or unknown in
 * those cases where, for example, the author of a particular work has *deliberately* chosen anonymity or where the 
 * author is simply not known. It would be at the user's discretion to chose appropriately.
 *
 * column otherNames
 * This would be used for a person's other names, initials, etc. For instance, in the case of Friedrich Georg Juenger, it 
 * would be Georg. But it might also just contain an initial, such as W. in the case of Dubya. It will be up to the 
 * application to process this field intelligently and display it correctly in the context.
 *
 * column suffix
 * Suffix is used for anything that individuals to further qualify their names - usually the case only for Americans 
 * or royalty, eg. George Bush, Snr, or King George III.
 *
 * column genderCode
 * Disregarding the interests of trans-sexuals and others who complicate our nicely binary view of gender identity and
 * looking instead to the authority of standards bodies who tolerate no such nonsense, the application uses the 
 * ISO 5218-1977 standard on the representation of human sexes. This standard represents gender values by four 
 * integers:
 * - 0 = Not Known
 * - 1 = Male
 * - 2 = Female
 * - 9 = Not Specified
 * For the purposes of general bibliographic applications, this should be sufficient. The gender field is included
 * so that breakdowns of, for example, research on a particular topic by gender can be studied for those who might
 * be inclined to come up with, say, a feminist analysis of why so many men write about Ernst Juenger.
 * The restriction to 0, 1, 2, and 9 is enforced by a simple constraint. It defaults to 'not specified'.
 */
create table person (
    personID     integer primary key,
    prefix              varchar(100),
    givenName           varchar(200),
    mainName            varchar(200) not null,
    otherNames          varchar(500),
    suffix              varchar(100),
    genderCode          smallint not null default 9,
    constraint 			genderCheck check ((genderCode > -1 and genderCode < 3) or genderCode = 9)
);
comment on table person is 'describes a person, normally one involved in producing a mediaItem';
comment on column person.prefix is 'prefix to a name - such as Sir, Dr, Graf';
comment on column person.givenName is 'given name, or first name/Christian name';
comment on column person.mainName is 'normally this will be family name. To be used if only one name is known or person is anonymous or otherwise unknown';
comment on column person.otherNames is 'normally for middle names or middle initials';
comment on column person.suffix is 'suffix to a name such as Snr, Jnr, III';
comment on column person.genderCode is '0 = not known, 1 = male, 2 = female, 9 = not specified';

/*
 * table organisation
 * organisation is relatively simple and only mandates an organisation name. The other columns are self-evident.
 */
create table organisation (
    organisationID      integer primary key,
    name                varchar(200) not null,
    city                varchar(100),
    country             varchar(100)
);
comment on table organisation is 'describes an organisation, normally one involved in producing a mediaItem';

/*
 * table relationshipType
 *
 * relationship is a referential integrity table
 * Its contents are generated from a SQL script constructed during
 * the build process by an Ant task from the configuration XML file. 
 * The relationship table is used to list the relationships which are possible between
 * mediaItems (edition, translation, criticism of, contained in) and between mediaItems
 * and mediaProducers (author, editor, translator, publisher). Relationships not listed in
 * the initial XML file and subsequently in this table are meaningless to the application,
 * hence the essential foreign key relationships in the intraMediaRelationship and 
 * mediaProducerRelationship tables which offer a further level of assurance regarding 
 * logical integrity.
 */
create table relationshipType (
    relationshipType    varchar(20) primary key
);
comment on table relationshipType is 'integrity constraint table for intraMediaRelationship and mediaProducerRelationship';

/*
 * table intraMediaRelationship
 * intraMediaRelationship is a join table that enables complex relationships between one or more media items with clear directionality
 * as expressed through the From and To aspects of columns mediaFromID and mediaToID. (In this respect the application
 * differs from topic maps where relationships have no direction).
 * The table is designed to be able to express two distinct (if abstractly related) relationships:
 * - containment
 * - all other relationships
 *
 * The containment relationship is particularly important as it allows the construction of recursively defined mediaItems.
 * Further examples (other than those above) include a bookArticle contained in a book or a journalArticle contained
 * in a journalIssue, contained in a journal. Following these recursive relationships to construct the final item to
 * display to a user is the responsibility of the application at the session bean layer.
 *
 * The relQualifier column is there to qualify relationships - for example, B is an edition of A. B's edition relationship
 * would be qualified by the relQualifier 1 (e.g "second"). There is a consequent need for a unique constraint. C cannot also have an 
 * edition relationship with A qualified by by relQualifier 1, as there can't be two "second" editions of A.
 */
create table intraMediaRelationship (
    intraMediaRelationshipID	integer primary key,
    mediaFromID         		integer not null,
    relationshipType    		varchar(20) not null,
    mediaToID           		integer not null,
	relationshipQualifier		varchar(1000),
    constraint          		fromFK foreign key (mediaFromID)
                            		references mediaItem (mediaItemID),
    constraint          		toFK foreign key (mediaToID)
                            		references mediaItem (mediaItemID),
	constraint					fromAndToAreDifferent check (mediaFromID != mediaToID),
    constraint          		relTypeFK foreign key (relationshipType)
                            		references relationshipType (relationshipType),
	constraint					uniqueRelQual unique (mediaFromID, relationshipType, mediaToID, relationshipQualifier)
);
comment on table intraMediaRelationship is 'complex join table between mediaItem and mediaItem';

/*
 * table mediaProductionRelationship
 *
 * A mediaItem has one or more mediaProduction relationships associated with it. Such relationships are "author", "editor",
 * "publisher", etc. Each production relationship has associated with it at least one of either person or organisation which
 * plays that role for the mediaItem concerned.
 * To find out the translators of a book, for example, one would look up the translation production relationship for the item
 * concerned and then look up the persons and organisations playing that mediaProductionRelationship. (Yes, it is quite conceivable
 * that an organisation could be credited with the translation role if the individuals concerned are not listed).
 * Whilst this does have the disadvantage of having to look up both people and organisations, the schema allows easier bidirectional
 * navigation of the relationships through EJB CMR.
 *
 * The rules which govern which mediaProducer-mediaItem relationships are logically coherent are controlled
 * by the application based on the configuration XML files (via the RulesManager EJB) and are too complex to be
 * modelled easily using simple database constraints.
 * 
 * mediaItemID is nullable because it will be set normally by the EJB container.
 *
 * I have added a unique constraint to the columns mediaItemID and relationshipType to ensure that we don't get the situation
 * where, for example, a book as two "author" mediaProductionRelationships. There is absolutely no need for this situation to arise
 * as the facility for multiple relationships is given through the M2M join tables. Although uniqueness is enforced at the EJB level
 * by defining these CMRs as returning java.util.Set, this constraint further enforces the logical integrity at the database level.
 */
create table mediaProductionRelationship (
    mediaProductionRelationshipID 	integer primary key,
    mediaItemID    			     	integer,
    relationshipType				varchar (20) not null,
	constraint          			mediaProdMediaFK foreign key (mediaItemID)
                            			references mediaItem (mediaItemID),
    constraint 						mediaProdRelTypeFK foreign key (relationshipType)
										references relationshipType (relationshipType),
	constraint						uniqueProdRelPerItem unique (mediaItemID, relationshipType)
); 
comment on table mediaProductionRelationship is 'complex join table between mediaItem and mediaProducer';

/* 
 * table personMediaProdRel
 * 
 * personMediaProdRel is a join table between the person and the mediaProductionRelationship
 * tables, facilitating many to many relationships. In other words, a person may be involved in zero or more
 * production relationships for one or more mediaItems. For example, Person A may be "author" and "publisher" of
 * book X and "author" and "illustrator" of book Y. 
 */
create table personMediaProdRel (
	mediaProductionRelationshipID	integer,
	personID						integer,
	constraint 						personMediaProdRelPK primary key (mediaProductionRelationshipID, personID),
	constraint						personMediaProdProdFK foreign key (mediaProductionRelationshipID)
										references mediaProductionRelationship (mediaProductionRelationshipID),
	constraint						personMediaProdPersonFK foreign key (personID)
										references person (personID)
);
comment on table personMediaProdRel is 'join table between mediaProductionRelationship and person';

/*
 * table orgMediaProdRel
 *
 * orgMediaProdRel is join table between the organisation and mediaProductionRelationship tables
 * which facilitates many to many relationships between these two entities.
 */
create table orgMediaProdRel (
	mediaProductionRelationshipID	integer,
	organisationID					integer,
	constraint						orgMediaProdRelPK primary key (mediaProductionRelationshipID, organisationID),
	constraint						orgMediaProdProdFK foreign key (mediaProductionRelationshipID)
										references mediaProductionRelationship (mediaProductionRelationshipID),
	constraint						orgMediaProdOrgFK foreign key (organisationID)
										references organisation (organisationID)
);
comment on table orgMediaProdRel is 'join table between mediaProductionRelationship and organisation';

/*
 * table sequencesList
 * 
 * sequencesList is used by the application primary key generation stragegy and is accessed by the 
 * Sequence entity EJB which is in turn used by a pool of SequenceBlockPrimaryKeyGenerator EJBs to dish out known
 * unique primary keys.
 */
create table sequencesList (
    name                            varchar(30) primary key,
    index                           integer
);
comment on table sequencesList is 'holds current max value for sequences used to generate suitable primary keys';

/*
 * table appUser
 *
 * appUser represents an actual user of the system (as opposed to the more abstract notion of a person). As such,
 * the table is also used for authentication purposes to enforce the declarative security constraints in the 
 * application.
 *
 * It is still a minimal representation of a user.
 */
create table appUser (
    username                        varchar(30) primary key,
    password                        varchar(10) not null
);
comment on table appUser is 'represents user authentication information'; 

create table appRoles (
    roleName                        varchar(30) primary key
);
comment on table appRoles is 'constraint table listing logical roles in the application as used in declarative security';

insert into appRoles(roleName) values ('bibManager');
insert into appRoles(roleName) values ('bibUserManager');

create table appUserRoles (
    username                        varchar(30),
    roleName                        varchar(30),
    constraint appUserRolesPK primary key (username, roleName),
    constraint appUserFK foreign key (username) references appUser (username),
    constraint appRolesFK foreign key (roleName) references appRoles (roleName)
);
comment on table appUserRoles is 'maps users to application roles';

/*
 * now we move onto user management for the database.
 * Whilst the table creation scripts are run as user postgres (so far we assume that
 * postgres has already created the database authorsite wtihin the PostgreSQL installation),
 * we do not want the applicaton accessing the database wtih the same rights as the posgres
 * user.
 * The users are:
 * - application (read write access to the core tables only. Read only access to the data integrity tables).
 * - dbreader (read access to all the tables)
 * - dbwriter (read write access to all the tables. To be used by a site admin needing direct access 
 *   to the database).
 * These users must be created and given secure passwords specific to each installation.
 */
 
 grant select, insert, update, delete on 	mediaItem,
 											mediaItemLanguage,
											person,
											organisation,
											mediaProductionRelationship,
											personMediaProdRel,
											orgMediaProdRel,
											intraMediaRelationship,
                                            appUser,
                                            appUserRoles,
                                            sequencesList
											
to application;

grant select on 							language,
											mediaType,
											relationshipType,
                                            appRoles
to application;

grant select on								mediaItem,
 											mediaItemLanguage,
											person,
											organisation,
											mediaProductionRelationship,
											personMediaProdRel,
											orgMediaProdRel,
											intraMediaRelationship,
											language,
											mediaType,
											relationshipType
to dbreader;

grant all on								mediaItem,
 											mediaItemLanguage,
											mediaProductionRelationship,
											person,
											organisation,
											personMediaProdRel,
											orgMediaProdRel,
											intraMediaRelationship,
											language,
											mediaType,
											relationshipType
to dbwriter;
