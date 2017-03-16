CREATE DATABASE  IF NOT EXISTS `soccerpro` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `soccerpro`;
-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: soccerpro
-- ------------------------------------------------------
-- Server version	5.6.24

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(128) NOT NULL,
  `password` varchar(256) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` enum('active','inactive','deleted') NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `login_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `org`
--

DROP TABLE IF EXISTS `org`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  `status` enum('active','inactive','deleted') NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `org`
--

LOCK TABLES `org` WRITE;
/*!40000 ALTER TABLE `org` DISABLE KEYS */;
/*!40000 ALTER TABLE `org` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privilege`
--

DROP TABLE IF EXISTS `privilege`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privilege` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `item_name` varchar(256) DEFAULT NULL,
  `item_value` varchar(256) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `privilege_privilege_fk_idx` (`parent_id`),
  CONSTRAINT `privilege_privilege_fk` FOREIGN KEY (`parent_id`) REFERENCES `privilege` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privilege`
--

LOCK TABLES `privilege` WRITE;
/*!40000 ALTER TABLE `privilege` DISABLE KEYS */;
/*!40000 ALTER TABLE `privilege` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` enum('staff_coach','assist_coach','fitness_coach','player', 'administrator','coach') NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_privilege_association`
--

DROP TABLE IF EXISTS `role_privilege_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_privilege_association` (
  `role_id` int(11) NOT NULL DEFAULT '0',
  `privilege_id` int(11) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`,`privilege_id`),
  KEY `privilege_role_privilege_fk_idx` (`privilege_id`),
  KEY `role_role_privilege_fk_idx` (`role_id`),
  CONSTRAINT `privilege_role_privilege_fk` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_role_privilege_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_privilege_association`
--

LOCK TABLES `role_privilege_association` WRITE;
/*!40000 ALTER TABLE `role_privilege_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_privilege_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `single_training`
--

DROP TABLE IF EXISTS `single_training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `single_training` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `player_count` int(11) DEFAULT NULL,
  `goalkeeper_count` int(11) DEFAULT NULL,
  `type` enum('normal') NOT NULL DEFAULT 'normal',
  `playbook_type` enum('full','half') NOT NULL DEFAULT 'full',
  `target` enum('all') NOT NULL DEFAULT 'all',
  `yard_width` varchar(16) DEFAULT NULL,
  `yard_height` varchar(16) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `single_training`
--

LOCK TABLES `single_training` WRITE;
/*!40000 ALTER TABLE `single_training` DISABLE KEYS */;
/*!40000 ALTER TABLE `single_training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `single_training_equipment_association`
--

DROP TABLE IF EXISTS `single_training_equipment_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `single_training_equipment_association` (
  `single_training_id` int(11) NOT NULL DEFAULT '0',
  `equipment_id` int(11) NOT NULL DEFAULT '0',
  `count` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`single_training_id`,`equipment_id`),
  KEY `single_training_equipment_association_fk2_idx` (`equipment_id`),
  KEY `single_training_equipment_association_fk1_idx` (`single_training_id`),
  CONSTRAINT `single_training_equipment_association_fk1` FOREIGN KEY (`single_training_id`) REFERENCES `single_training` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `single_training_equipment_association_fk2` FOREIGN KEY (`equipment_id`) REFERENCES `equipment` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `single_training_equipment_association`
--

LOCK TABLES `single_training_equipment_association` WRITE;
/*!40000 ALTER TABLE `single_training_equipment_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `single_training_equipment_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `single_training_ext`
--

DROP TABLE IF EXISTS `single_training_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `single_training_ext` (
  `single_training_id` int(11) NOT NULL DEFAULT '0',
  `item_name` varchar(128) NOT NULL DEFAULT '',
  `item_value` varchar(256) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`single_training_id`,`item_name`),
  KEY `single_training_ext_fk_idx` (`single_training_id`),
  CONSTRAINT `single_training_ext_fk` FOREIGN KEY (`single_training_id`) REFERENCES `single_training` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `single_training_ext`
--

LOCK TABLES `single_training_ext` WRITE;
/*!40000 ALTER TABLE `single_training_ext` DISABLE KEYS */;
/*!40000 ALTER TABLE `single_training_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training`
--

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `location` varchar(256) DEFAULT NULL,
  `color` enum('red','blue','yellow','green') NOT NULL DEFAULT 'red',
  `goal` varchar(2000) DEFAULT NULL,
  `evaluation` varchar(2000) DEFAULT NULL,
  `org_id` int(11) NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training`
--

LOCK TABLES `training` WRITE;
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
/*!40000 ALTER TABLE `training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_task`
--

DROP TABLE IF EXISTS `training_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `task_date` datetime DEFAULT NULL,
  `task_time` varchar(32) DEFAULT NULL,
  `location` varchar(256) DEFAULT NULL,
  `type` enum('training','match','life','others') NOT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_task`
--

LOCK TABLES `training_task` WRITE;
/*!40000 ALTER TABLE `training_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_task_evaluation`
--

DROP TABLE IF EXISTS `training_task_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_task_evaluation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL DEFAULT '0',
  `evaluation` varchar(2000) DEFAULT NULL,
  `score` int(4) DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `training_task_evaluation_fk2_idx` (`user_id`),
  KEY `training_task_evaluation_fk1_idx` (`task_id`),
  CONSTRAINT `training_task_evaluation_fk1` FOREIGN KEY (`task_id`) REFERENCES `training_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `training_task_evaluation_fk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_task_evaluation`
--

LOCK TABLES `training_task_evaluation` WRITE;
/*!40000 ALTER TABLE `training_task_evaluation` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_task_evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_task_evaluation_ext`
--

DROP TABLE IF EXISTS `training_task_evaluation_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_task_evaluation_ext` (
  `task_evaluation_id` int(11) NOT NULL DEFAULT '0',
  `item_name` enum('train','sick','hurt','absent','mp','bp','dnp') NOT NULL DEFAULT 'train',
  `item_value` varchar(16) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_evaluation_id`,`item_name`),
  KEY `training_task_evaluation_ext_fk_idx` (`task_evaluation_id`),
  CONSTRAINT `training_task_evaluation_ext_fk` FOREIGN KEY (`task_evaluation_id`) REFERENCES `training_task_evaluation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_task_evaluation_ext`
--

LOCK TABLES `training_task_evaluation_ext` WRITE;
/*!40000 ALTER TABLE `training_task_evaluation_ext` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_task_evaluation_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_task_ext`
--

DROP TABLE IF EXISTS `training_task_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_task_ext` (
  `task_id` int(11) NOT NULL DEFAULT '0',
  `item_name` varchar(128) NOT NULL DEFAULT '',
  `item_value` varchar(256) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`task_id`,`item_name`),
  KEY `training_task_ext_fk_idx` (`task_id`),
  CONSTRAINT `training_task_ext_fk` FOREIGN KEY (`task_id`) REFERENCES `training_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_task_ext`
--

LOCK TABLES `training_task_ext` WRITE;
/*!40000 ALTER TABLE `training_task_ext` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_task_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_task_single_training_association`
--

DROP TABLE IF EXISTS `training_task_single_training_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_task_single_training_association` (
  `training_task_id` int(11) NOT NULL DEFAULT '0',
  `single_training_id` int(11) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`training_task_id`,`single_training_id`),
  KEY `training_task_single_training_association_fk2_idx` (`single_training_id`),
  KEY `training_task_single_training_association_fk1_idx` (`training_task_id`),
  CONSTRAINT `training_task_single_training_association_fk1` FOREIGN KEY (`training_task_id`) REFERENCES `training_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `training_task_single_training_association_fk2` FOREIGN KEY (`single_training_id`) REFERENCES `training` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_task_single_training_association`
--

LOCK TABLES `training_task_single_training_association` WRITE;
/*!40000 ALTER TABLE `training_task_single_training_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_task_single_training_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_training_task_association`
--

DROP TABLE IF EXISTS `training_training_task_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_training_task_association` (
  `training_id` int(11) NOT NULL DEFAULT '0',
  `training_task_id` int(11) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`training_id`,`training_task_id`),
  KEY `training_training_task_association_fk2_idx` (`training_task_id`),
  KEY `training_training_task_association_fk1_idx` (`training_id`),
  CONSTRAINT `training_training_task_association_fk1` FOREIGN KEY (`training_id`) REFERENCES `training` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `training_training_task_association_fk2` FOREIGN KEY (`training_task_id`) REFERENCES `training_task` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_training_task_association`
--

LOCK TABLES `training_training_task_association` WRITE;
/*!40000 ALTER TABLE `training_training_task_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_training_task_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `tel` varchar(128) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `home_address` varchar(256) DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `passport` varchar(128) DEFAULT NULL,
  `nationality` varchar(128) DEFAULT NULL,
  `id_card` varchar(128) DEFAULT NULL,
  `birth_place` varchar(256) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_ext`
--

DROP TABLE IF EXISTS `user_ext`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ext` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `item_name` varchar(128) NOT NULL DEFAULT '',
  `item_value` varchar(2000) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`item_name`),
  KEY `user_ext_fk_idx` (`user_id`),
  CONSTRAINT `user_ext_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_ext`
--

LOCK TABLES `user_ext` WRITE;
/*!40000 ALTER TABLE `user_ext` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_ext` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_org_association`
--

DROP TABLE IF EXISTS `user_org_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_org_association` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `org_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`org_id`,`role_id`),
  KEY `org_user_org_fk_idx` (`org_id`),
  KEY `role_user_org_fk_idx` (`role_id`),
  KEY `user_user_org_fk_idx` (`user_id`),
  CONSTRAINT `org_user_org_fk` FOREIGN KEY (`org_id`) REFERENCES `org` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role_user_org_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_user_org_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_org_association`
--

LOCK TABLES `user_org_association` WRITE;
/*!40000 ALTER TABLE `user_org_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_org_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_training_association`
--

DROP TABLE IF EXISTS `user_training_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_training_association` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `training_id` int(11) NOT NULL DEFAULT '0',
  `created_time` datetime DEFAULT NULL,
  `last_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`training_id`),
  KEY `training_user_training_fk_idx` (`training_id`),
  KEY `user_user_training_fk_idx` (`user_id`),
  CONSTRAINT `training_user_training_fk` FOREIGN KEY (`training_id`) REFERENCES `training` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_user_training_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE IF NOT EXISTS `soccerpro`.`tactics_type` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `creator_id` INT(11) NULL COMMENT '',
  `org_id` INT(11) NULL COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `category` ENUM('soccer', 'basketball') NOT NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `soccerpro`.`tactics_playground` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NULL COMMENT '',
  `abbr` VARCHAR(45) NULL COMMENT '',
  `backgroundimg` VARCHAR(1024) NULL COMMENT '',
  `width_in_meter` INT NULL COMMENT '',
  `height_in_meter` INT NULL COMMENT '',
  `category` ENUM('soccer', 'basketball') NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
CREATE TABLE IF NOT EXISTS `soccerpro`.`tactics`  (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(128) NULL COMMENT '',
  `description` VARCHAR(2048) NULL COMMENT '',
  `tactics_type_id` INT(11) NULL COMMENT '',
  `org_id` INT(11) NULL COMMENT '',
  `create_time` TIMESTAMP NULL COMMENT '',
  `last_update` TIMESTAMP NULL COMMENT '',
  `creator_id` INT(11) NULL COMMENT '',
  `imgName` VARCHAR(1024) NULL COMMENT '',
  `tacticsdata` VARCHAR(1024) NULL COMMENT '',
  `playground_id` INT(11) NULL COMMENT '',
  `status` enum('active','inactive','deleted') NOT NULL,
  `type` enum('tactic', 'starters', 'placekick') NOT NULL DEFAULT 'tactic',
  PRIMARY KEY (`id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;

  
CREATE TABLE IF NOT EXISTS  `soccerpro`.`healthdata` (
  `id` INT(11) UNSIGNED NOT NULL COMMENT '',
  `height` INT(11) NULL COMMENT '',
  `weight` INT(11) NULL COMMENT '',
  `BMI` FLOAT NULL COMMENT '',
  `oxygen_content` FLOAT NULL COMMENT '',
  `shoulder` FLOAT NULL COMMENT '',
  `haunch` FLOAT NULL COMMENT '',
  `chest` INT(11) NULL COMMENT '',
  `waist` INT(11) NULL COMMENT '',
  `morning_pulse` INT(11) NULL COMMENT '',
  `lactate` FLOAT NULL COMMENT '',
  `create_time` DATE NULL COMMENT '',
  `user_id` INT(11) NOT NULL COMMENT '',
  `creator_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`idhealthdata`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `soccerpro`.`place_kick_type` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NULL COMMENT '',
  `org_id` INT NULL COMMENT '',
  `creator_id` INT NULL COMMENT '',
  `category` ENUM('soccer', 'basketball') NULL COMMENT '',
  `status` ENUM('active', 'inactive', 'deleted') NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
CREATE TABLE if not exists `soccerpro`.`starters` (
  `formation_id` INT NOT NULL COMMENT '',
  `tactics_id` INT NULL COMMENT '',
  PRIMARY KEY (`formation_id`, `tactics_id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
CREATE TABLE if not exists `soccerpro`.`place_kick` (
  `place_kick_type_id` INT NOT NULL COMMENT '',
  `tactics_id` INT NULL COMMENT '',
  PRIMARY KEY (`place_kick_type_id`, `tactics_id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
CREATE TABLE IF NOT EXISTS `soccerpro`.`formation_type` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '',
  `name` VARCHAR(45) NOT NULL COMMENT '',
  `player_count` INT NOT NULL COMMENT '',
  `org_id` INT NULL COMMENT '',
  `creator_id` INT NULL COMMENT '',
  `category` ENUM('soccer', 'basketball') NULL COMMENT '',
  `status` ENUM('active', 'inactive', 'deleted') NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '') ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dumping data for table `user_training_association`
--

LOCK TABLES `user_training_association` WRITE;
/*!40000 ALTER TABLE `user_training_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_training_association` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-01 15:39:13