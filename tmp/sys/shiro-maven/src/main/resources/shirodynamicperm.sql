-- MySQL dump 10.13  Distrib 5.1.62, for Win64 (unknown)
--
-- Host: localhost    Database: shirodynamicperm
-- ------------------------------------------------------
-- Server version	5.1.62-community

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
-- Table structure for table `sys_organization`
--

DROP TABLE IF EXISTS `sys_organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_sys_organization_parent_id` (`parent_id`),
  KEY `idx_sys_organization_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_organization`
--

LOCK TABLES `sys_organization` WRITE;
/*!40000 ALTER TABLE `sys_organization` DISABLE KEYS */;
INSERT INTO `sys_organization` VALUES (1,'总公司',0,'0/',1),(2,'分公司1',1,'0/1/',1),(3,'分公司2',1,'0/1/',1),(4,'分公司11',2,'0/1/2/',1);
/*!40000 ALTER TABLE `sys_organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_resource`
--

DROP TABLE IF EXISTS `sys_resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(100) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_resource`
--

LOCK TABLES `sys_resource` WRITE;
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` VALUES (1,'资源','menu','',0,'0/','',1),(11,'组织机构管理','menu','/organization',1,'0/1/','organization:*',1),(12,'组织机构新增','button','',11,'0/1/11/','organization:create',1),(13,'组织机构修改','button','',11,'0/1/11/','organization:update',1),(14,'组织机构删除','button','',11,'0/1/11/','organization:delete',1),(15,'组织机构查看','button','',11,'0/1/11/','organization:view',1),(21,'用户管理','menu','/user',1,'0/1/','user:*',1),(22,'用户新增','button','',21,'0/1/21/','user:create',1),(23,'用户修改','button','',21,'0/1/21/','user:update',1),(24,'用户删除','button','',21,'0/1/21/','user:delete',1),(25,'用户查看','button','',21,'0/1/21/','user:view',1),(31,'资源管理','menu','/resource',1,'0/1/','resource:*',1),(32,'资源新增','button','',31,'0/1/31/','resource:create',1),(33,'资源修改','button','',31,'0/1/31/','resource:update',1),(34,'资源删除','button','',31,'0/1/31/','resource:delete',1),(35,'资源查看','button','',31,'0/1/31/','resource:view',1),(41,'角色管理','menu','/role',1,'0/1/','role:*',1),(42,'角色新增','button','',41,'0/1/41/','role:create',1),(43,'角色修改','button','',41,'0/1/41/','role:update',1),(44,'角色删除','button','',41,'0/1/41/','role:delete',1),(45,'角色查看','button','',41,'0/1/41/','role:view',1),(46,'URL管理','menu','/urlFilter',1,'0/1/','urlFilter:*',1),(47,'URL新增','button','',46,'0/1/46/','urlFilter:create',1),(48,'URL修改','button','',46,'0/1/46/','urlFilter:update',1),(49,'URL删除','button','',46,'0/1/46/','urlFilter:delete',1),(50,'URL查看','button','',46,'0/1/46/','urlFilter:view',1);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `resource_ids` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_sys_role_resource_ids` (`resource_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'admin','超级管理员','11,21,31,41,46',1),(2,'普通管理员','只拥有部分权限','21,',0),(3,'动态授权测试','动态授权测试哈哈','12,46,',0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_url_filter`
--

DROP TABLE IF EXISTS `sys_url_filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_url_filter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `roles` varchar(100) DEFAULT NULL,
  `permissions` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_url_filter_url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_url_filter`
--

LOCK TABLES `sys_url_filter` WRITE;
/*!40000 ALTER TABLE `sys_url_filter` DISABLE KEYS */;
INSERT INTO `sys_url_filter` VALUES (2,'组织机构管理','/organization','动态授权测试','organization:*');
/*!40000 ALTER TABLE `sys_url_filter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `role_ids` varchar(100) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_username` (`username`),
  KEY `idx_sys_user_organization_id` (`organization_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (2,3,'13786147292','51f024c8f642c317103a2f96e14ae8fd','8b4c4e06c26a08aa00dc8a276b8ead88','1,',0),(3,3,'test','cdd3da12da910d30c632b478c3b32da3','4f54d60c94267c5082589be1c4de5ccc','2,',0),(4,3,'test2','7d74d676378c95488c1ef15d7d50718b','b605783e8f43c70fd0e33f9c899e8863','3,',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-10 16:21:45
