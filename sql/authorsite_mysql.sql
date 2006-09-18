DROP TABLE IF EXISTS human_work_relationships;

DROP TABLE IF EXISTS work_work_relationships;

DROP TABLE IF EXISTS languages_works;

DROP TABLE IF EXISTS languages;

DROP TABLE IF EXISTS works;

DROP TABLE IF EXISTS humans;

DROP TABLE IF EXISTS addressings;

DROP TABLE IF EXISTS mail_folders;

DROP TABLE IF EXISTS parts;

DROP TABLE IF EXISTS headers;



CREATE TABLE humans (

    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at          DATETIME,
    updated_at          DATETIME,
    lock_version        INTEGER NOT NULL DEFAULT 0,
    type                ENUM(   'Individual',
                                'Collective',
                                'User') NOT NULL,
    name                VARCHAR(255) NOT NULL DEFAULT 'Unknown',
    username            VARCHAR(255),
    given_names         VARCHAR(255),
    name_qualification  VARCHAR(255),
    place               VARCHAR(255),
    address             TEXT,
    password            VARCHAR(255)

) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

-- add index on name, given names
ALTER TABLE humans ADD INDEX HUMAN_NAME_IDX (name(100), given_names(100));

CREATE TABLE works (
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at      DATETIME,
    updated_at      DATETIME,
    lock_version    INTEGER NOT NULL DEFAULT 0,
    type            ENUM(   'FrbrWork',
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
    title           TEXT NOT NULL,
    year            YEAR,
    to_year         YEAR,
    month           INTEGER,
    day             INTEGER,
    pages           VARCHAR(255),
    how_published   VARCHAR(255),
    note            TEXT,
    edition         VARCHAR(255),
    volume          VARCHAR(255),
    number          VARCHAR(255),
    chapter         VARCHAR(255),
    degree          VARCHAR(255),
    url             TEXT
)
ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE languages
(
    id              INTEGER PRIMARY KEY,
    code            CHAR(3) NOT NULL,
    englishName     VARCHAR(255) NOT NULL
)
ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE languages ADD UNIQUE INDEX LANG_IDX (id, code);

CREATE TABLE languages_works
(
    language_id      INTEGER NOT NULL,
    work_id          INTEGER NOT NULL,
    PRIMARY KEY     (language_id, work_id)
)
ENGINE=InnoDB;

ALTER TABLE languages_works ADD CONSTRAINT lw_language_fk FOREIGN KEY (language_id)  REFERENCES languages(id);
ALTER TABLE languages_works ADD CONSTRAINT lw_work_fk FOREIGN KEY (work_id) REFERENCES works(id);

CREATE TABLE human_work_relationships
(
    id                INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    lock_version      INTEGER NOT NULL DEFAULT 0,
    human_id          INTEGER NOT NULL,
    work_id           INTEGER NOT NULL,
    relationship      ENUM(     'Author',
                                'Editor',
                                'Publisher',
                                'Organization',
                                'Institution',
                                'AwardingBody',
                                'Subject' ) NOT NULL
)
ENGINE=InnoDB CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE human_work_relationships ADD UNIQUE INDEX human2workIdx (human_id, relationship, work_id);
ALTER TABLE human_work_relationships ADD INDEX work2HumanIdx (work_id, relationship, human_id) ;
ALTER TABLE human_work_relationships ADD CONSTRAINT hwr_human_fk FOREIGN KEY (human_id) REFERENCES humans(id);
ALTER TABLE human_work_relationships ADD CONSTRAINT hwr_work_fk FOREIGN KEY (work_id) REFERENCES works(id);

CREATE TABLE work_work_relationships
(
    id                INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    lock_version      INTEGER NOT NULL DEFAULT 0,
    from_id           INTEGER NOT NULL,
    to_id             INTEGER NOT NULL,
    relationship      ENUM ('Containment',
                            'Expression',
                            'Subject',
                            'Translation',
                            'Citation' ) NOT NULL
)
ENGINE=InnoDB CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE work_work_relationships ADD UNIQUE INDEX fromToIdx (from_id, relationship, to_id);
ALTER TABLE work_work_relationships ADD INDEX toFromIdx (to_id, relationship, from_id);

ALTER TABLE work_work_relationships ADD CONSTRAINT ww_from_fk FOREIGN KEY (from_id) REFERENCES works(id);
ALTER TABLE work_work_relationships ADD CONSTRAINT ww_to_fk FOREIGN KEY (to_id) REFERENCES works(id);

/*
 * EMAIL ARCHIVE
 */

CREATE TABLE mail_folders (
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at          DATETIME,
    updated_at          DATETIME,
    lock_version        INTEGER NOT NULL DEFAULT 0,
    parent_id           INTEGER NOT NULL,
    name                VARCHAR(255) NOT NULL,
    path                TEXT NOT NULL
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE mail_folders ADD UNIQUE INDEX PATH_IDX (path(255), parent_id);

INSERT INTO mail_folders
    (created_at, updated_at, parent_id, name, path)
VALUES
    (NOW(), NOW(), 0, "/", "/");

UPDATE mail_folders SET id = 0;

ALTER TABLE mail_folders ADD CONSTRAINT mf_mf_fk FOREIGN KEY (parent_id) REFERENCES mail_folders(id);

CREATE TABLE parts (

    id                      INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at              DATETIME,
    updated_at              DATETIME,
    lock_version            INTEGER NOT NULL DEFAULT 0,
    type                    ENUM (  'Message',
                                'MimeMultipart',
                                'MimeBodyPart' ) NOT NULL,
    subject                 VARCHAR(255),
    sent_date               DATETIME,
    received_date           DATETIME,
    in_reply_to             VARCHAR(255),
    msg_references          VARCHAR (255),
    msg_id                  VARCHAR(255),
    text_content            MEDIUMTEXT,
    binary_content          MEDIUMBLOB,
    size                    INTEGER,
    mime_type               VARCHAR(255),
    file_name               VARCHAR(255),
    description             VARCHAR(255),
    disposition             VARCHAR(30),
    parent_id               INTEGER,
    multipart_position      INTEGER,
    mail_folder_id          INTEGER,
    mail_folder_position    INTEGER

) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE parts ADD CONSTRAINT p_p_fk FOREIGN KEY (parent_id) REFERENCES parts(id);
ALTER TABLE parts ADD CONSTRAINT p_mf_fk FOREIGN KEY (mail_folder_id) REFERENCES mail_folders(id);

CREATE TABLE addressings (
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at          DATETIME,
    updated_at          DATETIME,
    lock_version        INTEGER NOT NULL DEFAULT 0,
    addressing_type     ENUM(   'From',
                                'To',
                                'cc',
                                'bcc',
                                'Reply-To',
                                'Sender') NOT NULL,
    address             VARCHAR(255) NOT NULL,
    personal            VARCHAR(255),
    part_id             INTEGER NOT NULL

) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE addressings ADD CONSTRAINT a_p_fk FOREIGN KEY (part_id) REFERENCES parts(id);

