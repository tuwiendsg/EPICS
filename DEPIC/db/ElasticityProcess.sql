/*
 Navicat Premium Data Transfer

 Source Server         : DEPIC
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : localhost
 Source Database       : ElasticityProcess

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : utf-8

 Date: 11/19/2014 12:07:29 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ActionDependency`
-- ----------------------------
DROP TABLE IF EXISTS `ActionDependency`;
CREATE TABLE `ActionDependency` (
  `actionID` varchar(30) NOT NULL,
  `prerequisiteActionID` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `ActionDependency`
-- ----------------------------
BEGIN;
INSERT INTO `ActionDependency` VALUES ('LSR', 'AddVM'), ('LSR', 'RmVM'), ('MLR', 'RmVM'), ('MLR', 'AddVM'), ('LSR', 'DPL'), ('MLR', 'DPL'), ('DPL', 'AddVM');
COMMIT;

-- ----------------------------
--  Table structure for `DataAssetFunction`
-- ----------------------------
DROP TABLE IF EXISTS `DataAssetFunction`;
CREATE TABLE `DataAssetFunction` (
  `edaas` varchar(255) DEFAULT NULL,
  `dataAssetID` varchar(255) DEFAULT NULL,
  `dataAssetFunction` mediumblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `ElasticDaaS`
-- ----------------------------
DROP TABLE IF EXISTS `ElasticDaaS`;
CREATE TABLE `ElasticDaaS` (
  `name` varchar(255) DEFAULT NULL,
  `elasticity_processes` longblob,
  `deployment_descriptions` longblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `InputSpecification`
-- ----------------------------
DROP TABLE IF EXISTS `InputSpecification`;
CREATE TABLE `InputSpecification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `qor` longblob,
  `elasticity_process_config` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `PrimitiveAction`
-- ----------------------------
DROP TABLE IF EXISTS `PrimitiveAction`;
CREATE TABLE `PrimitiveAction` (
  `actionID` varchar(255) NOT NULL DEFAULT '',
  `actionName` varchar(255) DEFAULT NULL,
  `artifact` text,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`actionID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `PrimitiveAction`
-- ----------------------------
BEGIN;
INSERT INTO `PrimitiveAction` VALUES ('AddVM', 'Add Virtual Machine', 'http://128.130.172.215/salsa/upload/files/jun/controlservices/AddVM.war', 'war'), ('datacompletenessMeasurement', 'Monitor Data Completeness', 'http://128.130.172.215/salsa/upload/files/jun/monitoringservices/DataCompletenessMonitor.war', 'war'), ('DPL', 'Deploy Web Service', 'http://128.130.172.215/salsa/upload/files/jun/controlservices/DPL.war', 'war'), ('LSR', 'Linear Least Square Regression', 'http://128.130.172.215/salsa/upload/files/jun/controlservices/LSR.war', 'war'), ('MLR', 'Multi-linear Regression', 'http://128.130.172.215/salsa/upload/files/jun/controlservices/MLR.war', 'war'), ('MVI', 'Missing Value Imputation', 'http://128.130.172.215/salsa/upload/files/jun/controlservices/MVI.war', 'war'), ('RmCtrl', 'Remove Control VM', 'http://128.130.172.215/salsa/upload/files/jun/controlservices/RmCtrl.war', 'war'), ('throughputMeasurement', 'Monitor Throughput', 'http://128.130.172.215/salsa/upload/files/jun/monitoringservices/ThroughputMonitor.war', 'war');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
