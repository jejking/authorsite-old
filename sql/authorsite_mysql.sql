DROP TABLE IF EXISTS humanWorkRelationships;

DROP TABLE IF EXISTS workWorkRelationships;

DROP TABLE IF EXISTS languages_works;

DROP TABLE IF EXISTS works;

DROP TABLE IF EXISTS humans;

DROP TABLE IF EXISTS languages;

DROP TABLE IF EXISTS folders;

DROP TABLE IF EXISTS parts;

DROP TABLE IF EXISTS headers;

DROP TABLE IF EXISTS addressings;

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
    type 			ENUM(	'FrbrWork',
                            'Article',
                            'Book',
                            'Booklet',
                            'Conference',
                            'Chapter',
                            'Incollection',
                            'Inproceedings',
                            'Journal',
                            'Manual',
                            'Misc',
                            'OnlineResource',
                            'Proceedings',
                            'Techreport',
                            'Thesis',
                            'Unpublished' ) NOT NULL,
    title			TEXT NOT NULL,
    year    		YEAR,
    toYear          YEAR,
    month			INTEGER,
    day			    INTEGER,
    pages			VARCHAR(255),
    howpublished    VARCHAR(255),
    note			TEXT,
    edition			VARCHAR(255),
    volume			VARCHAR(255),
    number			VARCHAR(255),
    chapter			VARCHAR(255),
    degree          VARCHAR(255),
    url             TEXT
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
                                'AwardingBody',
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
    relationship	ENUM (	'Containment',
                            'Expression',
                            'Subject',
                            'Translation',
                            'Citation' ) NOT NULL
)
ENGINE = MyISAM CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE workWorkRelationships ADD UNIQUE INDEX fromToIdx (from_id, relationship, to_id);
ALTER TABLE workWorkRelationships ADD INDEX toFromIdx (to_id, relationship, from_id);

/*
 * EMAIL ARCHIVE
 */

CREATE TABLE folders (
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		    DATETIME,
    updated_at		    DATETIME,
    lock_version	    INTEGER NOT NULL DEFAULT 0,
    parent_id           INTEGER NOT NULL REFERENCES folders(id),
    name                VARCHAR(255) NOT NULL,
    path                TEXT NOT NULL
) ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE folders ADD UNIQUE INDEX PATH_IDX (path(255), parent_id);

INSERT INTO folders
    (created_at, updated_at, parent_id, name, path)
VALUES
    (NOW(), NOW(), 0, "/", "/");


CREATE TABLE parts (

    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		    DATETIME,
    updated_at		    DATETIME,
    lock_version	    INTEGER NOT NULL DEFAULT 0,
    type                ENUM (  'Message',
                                'MimeMultipart',
                                'MimeBodyPart' ) NOT NULL,
    subject             VARCHAR(255),
    sentDate            DATETIME,
    receivedDate        DATETIME,
    inReplyTo           VARCHAR(255),
    msgReferences       VARCHAR (255),
    msgId               VARCHAR(255),
    textContent         MEDIUMTEXT,
    binaryContent       MEDIUMBLOB,
    size                INTEGER,
    mimeType            VARCHAR(255),
    fileName            VARCHAR(255),
    description         VARCHAR(255),
    disposition         VARCHAR(30),
    parent_id           INTEGER REFERENCES parts(id),
    multipartOrder      INTEGER,
    folder_id           INTEGER REFERENCES folders(id),
    folder_position     INTEGER

) ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE parts ADD FULLTEXT INDEX  TEXT_CONTENT_IDX (textContent);
ALTER TABLE parts ADD FULLTEXT INDEX SUBJECT_IDX (subject);


CREATE TABLE addressings (
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at		    DATETIME,
    updated_at		    DATETIME,
    lock_version	    INTEGER NOT NULL DEFAULT 0,
    addressingType      ENUM(   'From',
                                'To',
                                'cc',
                                'bcc',
                                'Reply-To',
                                'Sender') NOT NULL,
    address             VARCHAR(255) NOT NULL,
    personal            VARCHAR(255),
    part_id             INTEGER NOT NULL REFERENCES parts(id)

) ENGINE = MyISAM CHARACTER SET utf8 COLLATE utf8_general_ci;


