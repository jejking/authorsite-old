/*
    SQL DDL to create the basic authorsite.org
    mail archive table structure
 */
CREATE DATABASE IF NOT EXISTS authorsite
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;


USE authorsite;

DROP TABLE IF EXISTS folders;

DROP TABLE IF EXISTS parts;

DROP TABLE IF EXISTS headers;

DROP TABLE IF EXISTS addressings;

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
    textContent         TEXT,
    binaryContent       BLOB,
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



