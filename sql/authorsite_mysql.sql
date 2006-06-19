/*
    SQL DDL to create the basic authorsite.org
    bibliographic table structure
 */
CREATE DATABASE IF NOT EXISTS authorsite
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;


USE authorsite;

DROP TABLE IF EXISTS humanWorkRelationships;

DROP TABLE IF EXISTS workWorkRelationships;

DROP TABLE IF EXISTS languages_works;

DROP TABLE IF EXISTS works;

DROP TABLE IF EXISTS humans;


DROP TABLE IF EXISTS languages;

CREATE TABLE humans (

    id			INTEGER PRIMARY KEY,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version		INTEGER NOT NULL DEFAULT 0,
    type 			ENUM(	'Individual',
                    'Collective',
                    'User'),
    name			VARCHAR(255) NOT NULL DEFAULT 'Unknown',
    givenNames		VARCHAR(255),
    nameQualification 	VARCHAR(255),
    place			VARCHAR(255),
    address			TEXT,
    password		VARCHAR(255)

) ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE works (

    id			INTEGER PRIMARY KEY,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version		INTEGER NOT NULL DEFAULT 0,
    type 			ENUM(	'AbstractWork',
                    'Article',
                    'Book',
                    'Booklet',
                    'Conference',
                    'Inbook',
                    'Incollection',
                    'Inproceedings',
                    'Journal',
                    'Manual',
                    'MastersThesis',
                    'Misc',
                    'OnlineResource',
                    'PhdThesis',
                    'Proceedings',
                    'Techreport',
                    'Unpublished' ),
    title			TEXT NOT NULL,
    year			YEAR,
    month			INTEGER,
    day			INTEGER,
    pages			VARCHAR(255),
    howpublished		VARCHAR(255),
    note			TEXT,
    edition			VARCHAR(255),
    volume			VARCHAR(255),
    number			VARCHAR(255),
    chapter			VARCHAR(255)


)
ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE languages
(
    id	            INTEGER PRIMARY KEY,
    code	        CHAR(3) NOT NULL,
    englishName	    VARCHAR(255) NOT NULL
)
ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE languages_works
(
    languages_id	INTEGER NOT NULL,
    works_id	INTEGER NOT NULL
)
ENGINE = MyISAM;

CREATE TABLE humanWorkRelationships
(
    id			INTEGER PRIMARY KEY,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version		INTEGER NOT NULL DEFAULT 0,
    humans_id		INTEGER NOT NULL,
    works_id		INTEGER NOT NULL,
    relationship		ENUM(	'Author',
                    'Editor',
                    'Publisher',
                    'Organization',
                    'Institution',
                    'School',
                    'Subject' )
)
ENGINE = MyISAM CHARACTER SET UTF8 COLLATE utf8_general_ci;

CREATE TABLE workWorkRelationships
(
    id			INTEGER PRIMARY KEY,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version		INTEGER NOT NULL DEFAULT 0,
    from_id			INTEGER NOT NULL,
    to_id			INTEGER NOT NULL,
    relationship		ENUM (	'containment',
                    'expression',
                    'subject',
                    'translation',
                    'citation' )
)
ENGINE = MyISAM CHARACTER SET UTF8 COLLATE utf8_general_ci;
