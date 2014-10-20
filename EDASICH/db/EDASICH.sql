/*
 Navicat Premium Data Transfer

 Source Server         : DEPIC
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : localhost
 Source Database       : EDASICH

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : utf-8

 Date: 10/20/2014 09:50:24 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `AnalyticEngine`
-- ----------------------------
DROP TABLE IF EXISTS `AnalyticEngine`;
CREATE TABLE `AnalyticEngine` (
  `analyticEngineID` varchar(30) DEFAULT NULL,
  `analyticEngineName` varchar(30) DEFAULT NULL,
  `ip` varchar(30) DEFAULT NULL,
  `port` varchar(30) DEFAULT NULL,
  `api` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `AnalyticEngine`
-- ----------------------------
BEGIN;
INSERT INTO `AnalyticEngine` VALUES ('es', 'esper', 'localhost', '8080', '/ESPERStreamProcessing/rest/esper'), ('jbpm', 'jbpm', 'localhost', '8080', '/JBPMEngine/rest/jbpm');
COMMIT;

-- ----------------------------
--  Table structure for `Daf`
-- ----------------------------
DROP TABLE IF EXISTS `Daf`;
CREATE TABLE `Daf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `Daf`
-- ----------------------------
BEGIN;
INSERT INTO `Daf` VALUES ('1', 'daf1', 'stop');
COMMIT;

-- ----------------------------
--  Table structure for `Event`
-- ----------------------------
DROP TABLE IF EXISTS `Event`;
CREATE TABLE `Event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `daf` varchar(30) DEFAULT NULL,
  `detected_time` varchar(50) DEFAULT NULL,
  `event_values` varchar(255) DEFAULT NULL,
  `severity` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `Event`
-- ----------------------------
BEGIN;
INSERT INTO `Event` VALUES ('1', 'daf1', 'Thu 2014.10.09 at 02:35:59 AM UTC', '[SensorY  - Value: 18699.6583591] ; [SensorY  - Value: 16088.9995849] ; ', 'EMERGENCY'), ('2', 'daf1', 'Thu 2014.10.09 at 02:36:34 AM UTC', '[SensorY  - Value: 18523.2182418] ; [SensorY  - Value: 10249.5530907] ; [SensorY  - Value: 18131.0640982] ; ', 'WARNING'), ('3', 'daf1', 'Thu 2014.10.09 at 02:36:34 AM UTC', '[SensorY  - Value: 18131.0640982] ; [SensorY  - Value: 18523.2182418] ; ', 'EMERGENCY');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
