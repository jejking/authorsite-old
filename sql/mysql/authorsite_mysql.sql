DROP TABLE IF EXISTS email_addresses;

DROP TABLE IF EXISTS human_work_relationships;

DROP TABLE IF EXISTS human_work_relationship_types;

DROP TABLE IF EXISTS work_work_relationships;

DROP TABLE IF EXISTS work_work_relationship_types;

DROP TABLE IF EXISTS languages_works;

DROP TABLE IF EXISTS languages;

DROP TABLE IF EXISTS works;

DROP TABLE IF EXISTS work_types;

DROP TABLE IF EXISTS humans;

DROP TABLE IF EXISTS addressings;

DROP TABLE IF EXISTS mail_folders;

DROP TABLE IF EXISTS parts;

DROP TABLE IF EXISTS headers;

DROP TABLE IF EXISTS content_folders;

DROP TABLE IF EXISTS content_items;

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
    note                TEXT,
    password            VARCHAR(255)

) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

-- add index on name, given names
ALTER TABLE humans ADD INDEX HUMAN_NAME_IDX (name(100), given_names(100));

CREATE TABLE email_addresses(
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at          DATETIME,
    updated_at          DATETIME,
    lock_version        INTEGER NOT NULL DEFAULT 0,
    address             VARCHAR(255) NOT NULL,
    human_id            INTEGER NOT NULL
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE email_addresses ADD CONSTRAINT ea_h_fk FOREIGN KEY (human_id) REFERENCES humans(id);
ALTER TABLE email_addresses ADD UNIQUE INDEX ea_uq_idx (address);

CREATE TABLE work_types (
    type                VARCHAR(255) PRIMARY KEY
)
ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO work_types(type) VALUES ('FrBrWork');
INSERT INTO work_types(type) VALUES ('Article');
INSERT INTO work_types(type) VALUES ('Book');
INSERT INTO work_types(type) VALUES ('Booklet');
INSERT INTO work_types(type) VALUES ('Conference');
INSERT INTO work_types(type) VALUES ('Chapter');
INSERT INTO work_types(type) VALUES ('Inproceedings');
INSERT INTO work_types(type) VALUES ('Journal');
INSERT INTO work_types(type) VALUES ('Manual');
INSERT INTO work_types(type) VALUES ('OnlineResource');
INSERT INTO work_types(type) VALUES ('Proceedings');
INSERT INTO work_types(type) VALUES ('Techreport');
INSERT INTO work_types(type) VALUES ('Thesis');
INSERT INTO work_types(type) VALUES ('Unpublished');


CREATE TABLE works (
    id              INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at      DATETIME,
    updated_at      DATETIME,
    lock_version    INTEGER NOT NULL DEFAULT 0,
    type            VARCHAR (255) NOT NULL,
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
    url             TEXT,
    published       BOOLEAN NOT NULL DEFAULT 0
)
ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE works ADD CONSTRAINT w_work_type_fk FOREIGN KEY (type) REFERENCES work_types(type);

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

CREATE TABLE human_work_relationship_types (
    relationship    VARCHAR(255) PRIMARY KEY
)
ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

INSERT INTO human_work_relationship_types(relationship) VALUES ('Author');
INSERT INTO human_work_relationship_types(relationship) VALUES ('Editor');
INSERT INTO human_work_relationship_types(relationship) VALUES ('Publisher');
INSERT INTO human_work_relationship_types(relationship) VALUES ('Organization');
INSERT INTO human_work_relationship_types(relationship) VALUES ('Institution');
INSERT INTO human_work_relationship_types(relationship) VALUES ('Subject');
INSERT INTO human_work_relationship_types(relationship) VALUES ('AwardingBody');

CREATE TABLE human_work_relationships
(
    id                INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    lock_version      INTEGER NOT NULL DEFAULT 0,
    human_id          INTEGER NOT NULL,
    work_id           INTEGER NOT NULL,
    relationship      VARCHAR(255) NOT NULL
)
ENGINE=InnoDB CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE human_work_relationships ADD UNIQUE INDEX human2workIdx (human_id, relationship, work_id);
ALTER TABLE human_work_relationships ADD INDEX work2HumanIdx (work_id, relationship, human_id) ;
ALTER TABLE human_work_relationships ADD CONSTRAINT hwr_human_fk FOREIGN KEY (human_id) REFERENCES humans(id);
ALTER TABLE human_work_relationships ADD CONSTRAINT hwr_work_fk FOREIGN KEY (work_id) REFERENCES works(id);
ALTER TABLE human_work_relationships ADD CONSTRAINT hwr_hwr_type_fk FOREIGN KEY (relationship) REFERENCES human_work_relationship_types(relationship);

CREATE TABLE work_work_relationship_types
(
    relationship      VARCHAR(255) PRIMARY KEY
)
ENGINE=InnoDB CHARACTER SET UTF8 COLLATE utf8_general_ci;

INSERT INTO work_work_relationship_types (relationship) VALUES ('Containment');
INSERT INTO work_work_relationship_types (relationship) VALUES ('Expression');
INSERT INTO work_work_relationship_types (relationship) VALUES ('Subject');
INSERT INTO work_work_relationship_types (relationship) VALUES ('Translation');
INSERT INTO work_work_relationship_types (relationship) VALUES ('Citation');
INSERT INTO work_work_relationship_types (relationship) VALUES ('Review');


CREATE TABLE work_work_relationships
(
    id                INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at        DATETIME,
    updated_at        DATETIME,
    lock_version      INTEGER NOT NULL DEFAULT 0,
    from_id           INTEGER NOT NULL,
    to_id             INTEGER NOT NULL,
    relationship      VARCHAR(255) NOT NULL
)
ENGINE=InnoDB CHARACTER SET UTF8 COLLATE utf8_general_ci;

ALTER TABLE work_work_relationships ADD UNIQUE INDEX fromToIdx (from_id, relationship, to_id);
ALTER TABLE work_work_relationships ADD INDEX toFromIdx (to_id, relationship, from_id);

ALTER TABLE work_work_relationships ADD CONSTRAINT ww_from_fk FOREIGN KEY (from_id) REFERENCES works(id);
ALTER TABLE work_work_relationships ADD CONSTRAINT ww_to_fk FOREIGN KEY (to_id) REFERENCES works(id);
ALTER TABLE work_work_relationships ADD CONSTRAINT ww_ww_type_fk FOREIGN KEY (relationship) REFERENCES work_work_relationship_types(relationship);

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

ALTER TABLE mail_folders ADD UNIQUE INDEX MF_PATH_IDX (path(255), parent_id);

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

/*
 * CONTENT
 */

CREATE TABLE content_folders (
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at          DATETIME,
    updated_at          DATETIME,
    lock_version        INTEGER NOT NULL DEFAULT 0,
    parent_id           INTEGER NOT NULL,
    name                VARCHAR(255) NOT NULL,
    path                TEXT NOT NULL
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE content_folders ADD UNIQUE INDEX CF_PATH_IDX (path(255), parent_id);

INSERT INTO content_folders
    (created_at, updated_at, parent_id, name, path)
VALUES
    (NOW(), NOW(), 0, "/", "/");

UPDATE content_folders SET id = 0;

ALTER TABLE content_folders ADD CONSTRAINT cf_cf_fk FOREIGN KEY (parent_id) REFERENCES content_folders(id);

CREATE TABLE content_items (
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    created_at          DATETIME,
    updated_at          DATETIME,
    lock_version        INTEGER NOT NULL DEFAULT 0,
    parent_id           INTEGER NOT NULL,
    name                VARCHAR(255) NOT NULL,
    path                TEXT NOT NULL,
    type                ENUM('binary_content_item',
                              'text_content_item') NOT NULL,
    text_content        MEDIUMTEXT,
    binary_content      MEDIUMBLOB,
    size                INTEGER,
    mime_type           VARCHAR(255) NOT NULL,
    copyright           TEXT
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE content_items ADD CONSTRAINT ci_cf_fk FOREIGN KEY (parent_id) REFERENCES content_folders(id);
