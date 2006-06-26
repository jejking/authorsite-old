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

    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		    DATETIME,
    updated_at		    DATETIME,
    lock_version	    INTEGER NOT NULL DEFAULT 0,
    type 			    ENUM(	'Individual',
                                'Collective',
                                'User') NOT NULL,
    name			    VARCHAR(255) NOT NULL DEFAULT 'Unknown',
    username            VARCHAR(255),
    givenNames		    VARCHAR(255),
    nameQualification 	VARCHAR(255),
    place			    VARCHAR(255),
    address			    TEXT,
    password		    VARCHAR(255)

) ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

-- add index on name, given names
ALTER TABLE humans ADD INDEX HUMAN_NAME_IDX (name(100), givenNames(100));

CREATE TABLE works (
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version	INTEGER NOT NULL DEFAULT 0,
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
                            'Unpublished' ) NOT NULL,
    title			TEXT NOT NULL,
    year			YEAR NOT NULL,
    month			INTEGER,
    day			    INTEGER,
    pages			VARCHAR(255),
    howpublished    VARCHAR(255),
    note			TEXT,
    edition			VARCHAR(255),
    volume			VARCHAR(255),
    number			VARCHAR(255),
    chapter			VARCHAR(255)
)
ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE works ADD FULLTEXT INDEX TITLE_IDX (title);

CREATE TABLE languages
(
    id	            INTEGER PRIMARY KEY,
    code	        CHAR(3) NOT NULL,
    englishName	    VARCHAR(255) NOT NULL
)
ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE languages ADD UNIQUE INDEX LANG_IDX (id, code);

CREATE TABLE languages_works
(
    languages_id	INTEGER NOT NULL REFERENCES languages(id),
    works_id	    INTEGER NOT NULL REFERENCES works(id),
    PRIMARY KEY     (languages_id, works_id)
)
ENGINE = MyISAM;

CREATE TABLE humanWorkRelationships
(
    id			    INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version	INTEGER NOT NULL DEFAULT 0,
    humans_id		INTEGER NOT NULL REFERENCES humans(id),
    works_id		INTEGER NOT NULL REFERENCES works(id),
    relationship		ENUM(	'Author',
                                'Editor',
                                'Publisher',
                                'Organization',
                                'Institution',
                                'School',
                                'Subject' ) NOT NULL
)
ENGINE = MyISAM CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE humanWorkRelationships ADD UNIQUE INDEX human2workIdx (humans_id, relationship, works_id);
ALTER TABLE humanWorkRelationships ADD INDEX work2HumanIdx (works_id, relationship, humans_id) ;

CREATE TABLE workWorkRelationships
(
    id			    INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		DATETIME,
    updated_at		DATETIME,
    lock_version	INTEGER NOT NULL DEFAULT 0,
    from_id			INTEGER NOT NULL REFERENCES works(id),
    to_id			INTEGER NOT NULL REFERENCES works(id),
    relationship	ENUM (	'containment',
                            'expression',
                            'subject',
                            'translation',
                            'citation' ) NOT NULL
)
ENGINE = MyISAM CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE workWorkRelationships ADD UNIQUE INDEX fromToIdx (from_id, relationship, to_id);
ALTER TABLE workWorkRelationships ADD INDEX toFromIdx (to_id, relationship, from_id);
