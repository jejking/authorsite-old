/*
 * Authorsite.org Metadata Application
 * Email Archive Database Table Creation Script for PostgreSQL
 *
 * Copyright (C) 2004 John King
 *
 * This file is part of the authorsite.org metadata
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
 * Version: $Revision: 1.1.1.1 $
 * Updated: $Date: 2004/03/09 23:18:41 $
 */

drop table content_language;
drop table emailMessage_emailAddress;
drop table emailMessage_textContent;
drop table emailMessage_binContent;
drop table person_emailAddress;

drop table mimeTypeEnum;
drop table language;
drop table contentTypeEnum;
drop table emailMessage_emailAddressEnum;
drop table emailMessage_textContentEnum;
drop table emailMessage_binContentEnum;
drop table person_emailAddressEnum;

drop table person;
drop table textContent;
drop table binContent;
drop table emailMessage;
drop table emailAddress;

drop table appUser_appRoles;
drop table appUser;
drop table appRoles;

create table mimeTypeEnum (
    mimeType            varchar(100) primary key,
    engDescription      varchar(200)
);


create table language (
    languageID          char(3) primary key,
    engName             varchar(200) not null
);

create table person (
    personID            integer primary key,
    prefix              varchar(100),
    givenName           varchar(200),
    mainName            varchar(200) not null,
    otherNames          varchar(500),
    suffix              varchar(100),
    genderCode          smallint not null default 9,
    dateOfBirth         date,
    dateOfDeath         date,
    constraint 			genderCheck check ((genderCode > -1 and genderCode < 3) or genderCode = 9)
);

create table textContent (
    textContentID           integer primary key,
    mimeType                varchar (100) references mimeTypeEnum (mimeType),
    textContent             text not null
);

create table binContent (
    binContentID         integer primary key,
    mimeType             varchar (100) references mimeTypeEnum (mimeType),
    binContent           bytea not null
);

create table content_language (
	contentID           integer not null references content (contentID),
    languageID          char(3) not null references language (languageID),
    primary key (contentID, languageID)
);

create table emailMessage (
    emailMessageID      integer primary key,
    dateSent            timestamp,                
    subject             varchar (1000),
    msgID               varchar (500),
    inReplyTo           varchar (500),
    msgReferences       varchar (500),
    importance          varchar (10),
    sensitivity         varchar (10)
);

create table emailAddress (
    emailAddressID      integer primary key,
    emailAddress        varchar (500) not null unique,
    personalName        varchar (1000),
    isList              boolean default false,
    isProcess           boolean default false
);

create table emailMessage_emailAddressEnum (
    relationshipType    varchar(50) primary key,
    minOccurs           int default 0,
    maxOccurs           int default 2147483647
);

create table emailMessage_emailAddress (
    emailMessageID      integer references emailMessage (emailMessageID),
    emailAddressID      varchar (500) references emailAddress (emailAddressID),
    relationshipType    varchar (50) references emailMessage_emailAddressEnum (relationshipType),
    primary key (emailMessageID, emailAddressID, relationshipType)
);

create table emailMessage_textContentEnum (
    relationshipType    varchar (50) primary key,
    minOccurs           int default 0,
    maxOccurs           int default 2147483647,
    check (minOccurs >=0 and minOccurs <= maxOccurs)  
);

create table emailMessage_textContent (
    emailMessageID         integer references emailMessage (emailMessageID),
    textContentID       integer references textContent (textContentID),
    relationshipType    varchar (50) references emailMessage_textContentEnum (relationshipType),
    primary key (emailMessageID, textContentID, relationshipType)
);

create table emailMessage_binContentEnum (
    relationshipType    varchar (50) primary key,
    minOccurs           int default 0,
    maxOccurs           int default 2147483647,
    check (minOccurs >=0 and minOccurs <= maxOccurs)
);

create table emailMessage_binContent (
    emailMessageID         integer references emailMessage (emailMessageID),
    binContentID     integer references binContent (binContentID),
    relationshipType    varchar (50) references emailMessage_binContentEnum (relationshipType),
    primary key (emailMessageID, binContentID, relationshipType)
);

create table person_emailAddressEnum (
    relationshipType    varchar (50) primary key references relationshipEnum (relationshipType),
    minOccurs           int default 0,
    maxOccurs           int default 2147483647,
    check (minOccurs >=0 and minOccurs <= maxOccurs)
);

create table person_emailAddress (
    personID            integer references person (personID),
    emailAddressID      integer references emailAddress (emailAddressID),
    relationshipType    varchar (50) references person_emailAddressEnum (relationshipType),
    primary key         (personID, emailAddressID, relationshipType)
);

create table appUser (
    username            varchar(50) primary key,
    password            varchar(50) not null,
    personID            integer not null unique references person (personID)
);

create table appRoles (
    rolename            varchar(50) primary key
);

create table appUser_appRoles (
    username            varchar(50) references appUser (username),
    rolename            varchar(50) references appRoles (rolename),
    primary key (username, rolename)
);


insert into emailMessage_emailAddressEnum (relationshipType) values ('From');
insert into emailMessage_emailAddressEnum (relationshipType) values ('To');
insert into emailMessage_emailAddressEnum (relationshipType) values ('cc');
insert into emailMessage_emailAddressEnum (relationshipType) values ('bcc');

insert into emailMessage_textContentEnum (relationshipType, minOccurs, maxOccurs) values ('messageBody', 0, 1);
insert into emailMessage_textContentEnum (relationshipType) values ('messageBodyPart');
insert into emailMessage_textContentEnum (relationshipType) values ('messageHeaders');

insert into emailMessage_binContentEnum (relationshipType) values ('messageBodyPart');

insert into person_emailAddressEnum (relationshipType) values ('private');
insert into person_emailAddressEnum (relationshipType) values ('business');
insert into person_emailAddressEnum (relationshipType) values ('current');
insert into person_emailAddressEnum (relationshipType) values ('unknown');
