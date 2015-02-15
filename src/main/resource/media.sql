-- --------------------------------------------------------
-- Host:                         192.168.0.108
-- Server version:               5.6.12 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table media.directory
CREATE TABLE IF NOT EXISTS `directory` (
  `directory`    VARCHAR(255) NOT NULL,
  `type`         VARCHAR(255) NOT NULL,
  `last_scanned` DATETIME DEFAULT NULL,
  PRIMARY KEY (`directory`, `type`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.music
CREATE TABLE IF NOT EXISTS `music` (
  `checksum`     VARCHAR(255) NOT NULL,
  `artist`       VARCHAR(255) NOT NULL,
  `artist_url`   VARCHAR(255) NOT NULL,
  `album`        VARCHAR(255) NOT NULL,
  `album_url`    VARCHAR(255) NOT NULL,
  `track_number` TINYINT(4)   NOT NULL,
  `title`        VARCHAR(255) NOT NULL,
  `year`         SMALLINT(6)  DEFAULT NULL,
  `genre`        VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`checksum`),
  CONSTRAINT `FK1_music_resource` FOREIGN KEY (`checksum`) REFERENCES `resource` (`checksum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.resource
CREATE TABLE IF NOT EXISTS `resource` (
  `checksum`     VARCHAR(255) NOT NULL,
  `directory`    VARCHAR(255) NOT NULL,
  `type`         VARCHAR(255) NOT NULL,
  `path`         VARCHAR(255) NOT NULL,
  `last_updated` DATETIME DEFAULT NULL,
  PRIMARY KEY (`checksum`),
  KEY `FK1_resource_directory_directory` (`directory`),
  KEY `FK3_resource_type_type` (`type`),
  CONSTRAINT `FK1_resource_directory_directory` FOREIGN KEY (`directory`) REFERENCES `directory` (`directory`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK2_resource_directory_type` FOREIGN KEY (`type`) REFERENCES `type` (`type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK3_resource_type_type` FOREIGN KEY (`type`) REFERENCES `type` (`type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.type
CREATE TABLE IF NOT EXISTS `type` (
  `type` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`type`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.type_extension
CREATE TABLE IF NOT EXISTS `type_extension` (
  `type`      VARCHAR(255) NOT NULL,
  `extension` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`type`, `extension`),
  CONSTRAINT `FK1_type_extension_type` FOREIGN KEY (`type`) REFERENCES `type` (`type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.user
CREATE TABLE IF NOT EXISTS `user` (
  `username`   VARCHAR(255) NOT NULL,
  `password`   VARCHAR(255) NOT NULL,
  `name`       VARCHAR(255) NOT NULL,
  `last_login` DATETIME DEFAULT NULL,
  PRIMARY KEY (`username`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.user_played
CREATE TABLE IF NOT EXISTS `user_played` (
  `username`    VARCHAR(255) NOT NULL,
  `resource`    VARCHAR(255) NOT NULL,
  `last_played` DATETIME     NOT NULL,
  `rating`      TINYINT(4) DEFAULT NULL,
  PRIMARY KEY (`username`, `resource`),
  KEY `FK2_user_played_resource` (`resource`),
  CONSTRAINT `FK1_user_played_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK2_user_played_resource` FOREIGN KEY (`resource`) REFERENCES `resource` (`checksum`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.


-- Dumping structure for table media.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `username` VARCHAR(255) NOT NULL,
  `role`     VARCHAR(255) NOT NULL,
  PRIMARY KEY (`username`, `role`),
  CONSTRAINT `FK1_user_role_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
