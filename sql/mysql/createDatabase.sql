DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `pages` varchar(255) default NULL,
  `issue` varchar(255) default NULL,
  `volume` varchar(255) default NULL,
  `journal_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK379164D6E03C0E39` (`id`),
  KEY `FK379164D688DC08AF` (`journal_id`),
  CONSTRAINT `FK379164D688DC08AF` FOREIGN KEY (`journal_id`) REFERENCES `journal` (`id`),
  CONSTRAINT `FK379164D6E03C0E39` FOREIGN KEY (`id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` bigint(20) NOT NULL,
  `volume` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK1FAF09E03C0E39` (`id`),
  CONSTRAINT `FK1FAF09E03C0E39` FOREIGN KEY (`id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` bigint(20) NOT NULL,
  `pages` varchar(255) default NULL,
  `chapter` varchar(255) default NULL,
  `book_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK8F45142D6C67CEE5` (`book_id`),
  KEY `FK8F45142DE03C0E39` (`id`),
  CONSTRAINT `FK8F45142D6C67CEE5` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `FK8F45142DE03C0E39` FOREIGN KEY (`id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `human`
--

DROP TABLE IF EXISTS `human`;
CREATE TABLE `human` (
  `DTYPE` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL auto_increment,
  `createdAt` datetime default NULL,
  `updatedAt` datetime default NULL,
  `version` int(11) NOT NULL,
  `nameQualification` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `place` varchar(255) default NULL,
  `givenNames` varchar(255) default NULL,
  `createdBy_id` bigint(20) NOT NULL,
  `updatedBy_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK42D710D53F5596C` (`createdBy_id`),
  KEY `FK42D710DC0602379` (`updatedBy_id`),
  CONSTRAINT `FK42D710D53F5596C` FOREIGN KEY (`createdBy_id`) REFERENCES `human` (`id`),
  CONSTRAINT `FK42D710DC0602379` FOREIGN KEY (`updatedBy_id`) REFERENCES `human` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6095 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `journal`;
CREATE TABLE `journal` (
  `id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKE9D4717E03C0E39` (`id`),
  CONSTRAINT `FKE9D4717E03C0E39` FOREIGN KEY (`id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `systemuser`
--

DROP TABLE IF EXISTS `systemuser`;
CREATE TABLE `systemuser` (
  `id` bigint(20) NOT NULL auto_increment,
  `createdAt` datetime default NULL,
  `updatedAt` datetime default NULL,
  `version` int(11) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `updatedBy_id` bigint(20) NOT NULL,
  `individual_id` bigint(20) NOT NULL,
  `createdBy_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `userName` (`userName`),
  UNIQUE KEY `individual_id` (`individual_id`),
  KEY `FK9D23FEBA53F5596C` (`createdBy_id`),
  KEY `FK9D23FEBAC3F171D2` (`individual_id`),
  KEY `FK9D23FEBAC0602379` (`updatedBy_id`),
  CONSTRAINT `FK9D23FEBA53F5596C` FOREIGN KEY (`createdBy_id`) REFERENCES `human` (`id`),
  CONSTRAINT `FK9D23FEBAC0602379` FOREIGN KEY (`updatedBy_id`) REFERENCES `human` (`id`),
  CONSTRAINT `FK9D23FEBAC3F171D2` FOREIGN KEY (`individual_id`) REFERENCES `human` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


--
-- Table structure for table `systemuser_authorities`
--

DROP TABLE IF EXISTS `systemuser_authorities`;
CREATE TABLE `systemuser_authorities` (
  `SystemUser_id` bigint(20) NOT NULL,
  `element` int(11) default NULL,
  KEY `FKB5AED0FC801E72D6` (`SystemUser_id`),
  CONSTRAINT `FKB5AED0FC801E72D6` FOREIGN KEY (`SystemUser_id`) REFERENCES `systemuser` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
-- Table structure for table `thesis`
--

DROP TABLE IF EXISTS `thesis`;
CREATE TABLE `thesis` (
  `id` bigint(20) NOT NULL,
  `degree` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK954046ECE03C0E39` (`id`),
  CONSTRAINT `FK954046ECE03C0E39` FOREIGN KEY (`id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



--
-- Table structure for table `webresource`
--

DROP TABLE IF EXISTS `webresource`;
CREATE TABLE `webresource` (
  `id` bigint(20) NOT NULL,
  `url` varchar(255) default NULL,
  `lastChecked` datetime default NULL,
  `lastStatusCode` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKAC6B0762E03C0E39` (`id`),
  CONSTRAINT `FKAC6B0762E03C0E39` FOREIGN KEY (`id`) REFERENCES `work` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `work`;
CREATE TABLE `work` (
  `id` bigint(20) NOT NULL auto_increment,
  `createdAt` datetime default NULL,
  `updatedAt` datetime default NULL,
  `version` int(11) NOT NULL,
  `toDate` date default NULL,
  `date` date default NULL,
  `title` varchar(255) NOT NULL,
  `updatedBy_id` bigint(20) NOT NULL,
  `createdBy_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK293B3153F5596C` (`createdBy_id`),
  KEY `FK293B31C0602379` (`updatedBy_id`),
  CONSTRAINT `FK293B3153F5596C` FOREIGN KEY (`createdBy_id`) REFERENCES `human` (`id`),
  CONSTRAINT `FK293B31C0602379` FOREIGN KEY (`updatedBy_id`) REFERENCES `human` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1925 DEFAULT CHARSET=utf8;



--
-- Table structure for table `work_workproducers`
--

DROP TABLE IF EXISTS `work_workproducers`;
CREATE TABLE `work_workproducers` (
  `Work_id` bigint(20) NOT NULL,
  `workProducerType` varchar(255) NOT NULL,
  `abstractHuman_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`Work_id`,`workProducerType`,`abstractHuman_id`),
  KEY `FK706C02823B240627` (`Work_id`),
  KEY `FK706C028273458C02` (`abstractHuman_id`),
  CONSTRAINT `FK706C02823B240627` FOREIGN KEY (`Work_id`) REFERENCES `work` (`id`),
  CONSTRAINT `FK706C028273458C02` FOREIGN KEY (`abstractHuman_id`) REFERENCES `human` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table content
--
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `id` bigint(20) NOT NULL,
  `DTYPE` varchar(31) NOT NULL,
  `content_name` varchar(255) NOT NULL,
  `mime_type` varchar(255) NOT NULL,
  `text_content` longtext,
  `binary_content` longblob,
  PRIMARY KEY (`content_id`),
  UNIQUE KEY (`content_name`),
  KEY `FK_CONTENT_WORK` (`content_id`),
  CONSTRAINT `FK_CONTENT_WORK` FOREIGN KEY (`content_id`) REFERENCES `work` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

