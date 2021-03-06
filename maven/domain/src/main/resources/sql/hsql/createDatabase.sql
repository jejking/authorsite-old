create table Article (id bigint not null, pages varchar(255), issue varchar(255), volume varchar(255), journal_id bigint not null, primary key (id));
create table Book (id bigint not null, volume varchar(255), primary key (id));
create table Chapter (id bigint not null, pages varchar(255), chapter varchar(255), book_id bigint not null, primary key (id));
create table Human (DTYPE varchar(31) not null, id bigint generated by default as identity (start with 1), createdAt timestamp, updatedAt timestamp, version integer not null, nameQualification varchar(255), name varchar(255), place varchar(255), givenNames varchar(255), updatedBy_id bigint not null, createdBy_id bigint not null, primary key (id));
create table Journal (id bigint not null, primary key (id));
create table SystemUser (id bigint generated by default as identity (start with 1), createdAt timestamp, updatedAt timestamp, version integer not null, enabled bit not null, password varchar(255) not null, userName varchar(255) not null, individual_id bigint not null, createdBy_id bigint not null, updatedBy_id bigint not null, primary key (id), unique (individual_id), unique (userName));
create table SystemUser_Authorities (SystemUser_id bigint not null, element integer);
create table Thesis (id bigint not null, degree varchar(255) not null, primary key (id));
create table WebResource (id bigint not null, url varchar(255), lastChecked timestamp, lastStatusCode integer not null, primary key (id));
create table Work (id bigint generated by default as identity (start with 1), createdAt timestamp, updatedAt timestamp, version integer not null, toDate date, date date, title varchar(255) not null, createdBy_id bigint not null, updatedBy_id bigint not null, primary key (id));
create table Work_workProducers (Work_id bigint not null, workProducerType varchar(255) not null, abstractHuman_id bigint not null, primary key (Work_id, workProducerType, abstractHuman_id));

alter table Article add constraint FK379164D6E03C0E39 foreign key (id) references Work;
alter table Article add constraint FK379164D688DC08AF foreign key (journal_id) references Journal;
alter table Book add constraint FK1FAF09E03C0E39 foreign key (id) references Work;
alter table Chapter add constraint FK8F45142D6C67CEE5 foreign key (book_id) references Book;
alter table Chapter add constraint FK8F45142DE03C0E39 foreign key (id) references Work;
alter table Human add constraint FK42D710D53F5596C foreign key (createdBy_id) references Human;
alter table Human add constraint FK42D710DC0602379 foreign key (updatedBy_id) references Human;
alter table Journal add constraint FKE9D4717E03C0E39 foreign key (id) references Work;
alter table SystemUser add constraint FK9D23FEBA53F5596C foreign key (createdBy_id) references Human;
alter table SystemUser add constraint FK9D23FEBAC0602379 foreign key (updatedBy_id) references Human;
alter table SystemUser add constraint FK9D23FEBAC3F171D2 foreign key (individual_id) references Human;
alter table SystemUser_Authorities add constraint FKB5AED0FC801E72D6 foreign key (SystemUser_id) references SystemUser;
alter table Thesis add constraint FK954046ECE03C0E39 foreign key (id) references Work;
alter table WebResource add constraint FKAC6B0762E03C0E39 foreign key (id) references Work;
alter table Work add constraint FK293B3153F5596C foreign key (createdBy_id) references Human;
alter table Work add constraint FK293B31C0602379 foreign key (updatedBy_id) references Human;
alter table Work_workProducers add constraint FK706C02823B240627 foreign key (Work_id) references Work;
alter table Work_workProducers add constraint FK706C028273458C02 foreign key (abstractHuman_id) references Human;

CREATE TABLE ACL_SID(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,PRINCIPAL BOOLEAN NOT NULL,SID VARCHAR_IGNORECASE(100) NOT NULL,CONSTRAINT UNIQUE_UK_1 UNIQUE(SID,PRINCIPAL));
CREATE TABLE ACL_CLASS(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,CLASS VARCHAR_IGNORECASE(100) NOT NULL,CONSTRAINT UNIQUE_UK_2 UNIQUE(CLASS));
CREATE TABLE ACL_OBJECT_IDENTITY(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,OBJECT_ID_CLASS BIGINT NOT NULL,OBJECT_ID_IDENTITY BIGINT NOT NULL,PARENT_OBJECT BIGINT,OWNER_SID BIGINT,ENTRIES_INHERITING BOOLEAN NOT NULL,CONSTRAINT UNIQUE_UK_3 UNIQUE(OBJECT_ID_CLASS,OBJECT_ID_IDENTITY),CONSTRAINT FOREIGN_FK_1 FOREIGN KEY(PARENT_OBJECT)REFERENCES ACL_OBJECT_IDENTITY(ID),CONSTRAINT FOREIGN_FK_2 FOREIGN KEY(OBJECT_ID_CLASS)REFERENCES ACL_CLASS(ID),CONSTRAINT FOREIGN_FK_3 FOREIGN KEY(OWNER_SID)REFERENCES ACL_SID(ID));
CREATE TABLE ACL_ENTRY(ID BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 100) NOT NULL PRIMARY KEY,ACL_OBJECT_IDENTITY BIGINT NOT NULL,ACE_ORDER INT NOT NULL,SID BIGINT NOT NULL,MASK INTEGER NOT NULL,GRANTING BOOLEAN NOT NULL,AUDIT_SUCCESS BOOLEAN NOT NULL,AUDIT_FAILURE BOOLEAN NOT NULL,CONSTRAINT UNIQUE_UK_4 UNIQUE(ACL_OBJECT_IDENTITY,ACE_ORDER),CONSTRAINT FOREIGN_FK_4 FOREIGN KEY(ACL_OBJECT_IDENTITY) REFERENCES ACL_OBJECT_IDENTITY(ID),CONSTRAINT FOREIGN_FK_5 FOREIGN KEY(SID) REFERENCES ACL_SID(ID));

