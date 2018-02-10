DROP DATABASE IF EXISTS `saber`;
CREATE DATABASE `saber` CHARACTER SET UTF8;

DROP TABLE IF EXISTS `saber`.`user`;
DROP TABLE IF EXISTS `saber`.`article`;
DROP TABLE IF EXISTS `saber`.`comment`;
DROP TABLE IF EXISTS `saber`.`board`;
DROP TABLE IF EXISTS `saber`.`tag`;

CREATE TABLE `saber`.`user` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) NOT NULL,
	`email` VARCHAR(50) NOT NULL,
	`ip` VARCHAR(64),
	`cts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`uts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=UTF8;

CREATE TABLE `saber`.`article` ( 
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(300) NOT NULL,
	`author` INT UNSIGNED NOT NULL,
	`file` VARCHAR(32),
	`preview` TEXT NOT NULL,
	`hit_count` INT UNSIGNED DEFAULT '0',
	`read_count` INT UNSIGNED DEFAULT '0',
	`useful_count` INT UNSIGNED DEFAULT '0',
	`rating_count` INT UNSIGNED DEFAULT '0',
	`rating` FLOAT DEFAULT '0',
	`type` TINYINT UNSIGNED DEFAULT '1',
	`tag` VARCHAR(100),
	`comment_count` INT UNSIGNED DEFAULT '0',
	`cts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`uts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB CHARSET=UTF8;
 
CREATE TABLE `saber`.`comment` ( 
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`article` INT UNSIGNED NOT NULL,
	`user` INT UNSIGNED NOT NULL,
	`content` TEXT,
	`reply` INT UNSIGNED,
	`cts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`uts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) Engine=InnoDB charset=UTF8;
 
CREATE TABLE `saber`.`board` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`user` INT,
	`content` TEXT,
	`reply` INT,
	`ip` VARCHAR(64),
	`cts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	`uts` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
) Engine=InnoDB charset=UTF8;

CREATE TABLE `saber`.`tag` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(10) NOT NULL,
	PRIMARY KEY (`id`)
) Engine=InnoDB charset=UTF8;

/** inc_article_comment_count trigger: after added `comment`, increase the comment count of the article. */
DELIMITER //
CREATE TRIGGER `saber`.`inc_article_comment_count` AFTER INSERT ON `saber`.`comment`
FOR EACH ROW
BEGIN
	UPDATE article SET comment_count = comment_count + 1 WHERE id = NEW.article;
END;//
DELIMITER ;

INSERT INTO `user` (name, email, create_time, create_ts) VALUES ('Nen Den', 'zhegema@126.com', NOW(), NULL);
INSERT INTO `tag` (`id`,`name`) VALUES
	(1, 'Unix'),(2, 'Linux'),(3, 'C'),(4, 'Java'),(5, 'Lua'), (6, 'JS'), (7, 'Spring'),
	(8, 'HTML'), (9, 'CSS'), (10, 'SQL'), (11, 'MySQL'), (12, 'nginx'), (13, 'SVN'), (14, 'Log4j'),
	(15, 'R');