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

 Date: 02/05/2015 11:16:50 AM
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
INSERT INTO `ActionDependency` VALUES ('DPL', 'AddVM'), ('DIR', 'LSR'), ('DIR', 'MLR'), ('DER', 'LSR'), ('DER', 'MLR');
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
  `elasticStateSet` longblob,
  `elasticity_processes` longblob,
  `deployment_descriptions` longblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `ElasticService`
-- ----------------------------
DROP TABLE IF EXISTS `ElasticService`;
CREATE TABLE `ElasticService` (
  `actionID` varchar(100) DEFAULT NULL,
  `serviceID` varchar(100) DEFAULT NULL,
  `uri` text
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `PrimitiveAction`
-- ----------------------------
DROP TABLE IF EXISTS `PrimitiveAction`;
CREATE TABLE `PrimitiveAction` (
  `actionName` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `artifact` text,
  `type` varchar(255) DEFAULT NULL,
  `restapi` text,
  PRIMARY KEY (`actionName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `PrimitiveAction`
-- ----------------------------
BEGIN;
INSERT INTO `PrimitiveAction` VALUES ('dataaccuracyMeasurement', 'Monitor Data Accuracy', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/dataaccuracyMeasurement.sh', 'sh', '/dataaccuracyMeasurement/rest/dataaccuracy'), ('datacompletenessMeasurement', 'Monitor Data Completeness', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/datacompletenessMeasurement.sh', 'sh', '/datacompletenessMeasurement/rest/completeness'), ('DIR', 'Distance-based outiler removal', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/DIR.sh', 'sh', '/DIR/rest/control'), ('LSR', 'Linear Least Square Regression', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/LSR.sh', 'sh', '/LSR/rest/control'), ('MLR', 'Multi-linear Regression', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/MLR.sh', 'sh', '/MLR/rest/control'), ('STC', 'SYBL Throughput Control', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/STC.sh', 'sh', '/STC/rest/control'), ('throughputMeasurement', 'Monitor Throughput', 'http://128.130.172.215/salsa/upload/files/jun/artifact_sh/throughputMeasurement.sh', 'sh', '/throughputMeasurement/rest/throughput');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
