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

 Date: 10/20/2014 13:39:09 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `ControlActionDependency`
-- ----------------------------
DROP TABLE IF EXISTS `ControlActionDependency`;
CREATE TABLE `ControlActionDependency` (
  `actionID` varchar(30) NOT NULL,
  `prerequisiteActionID` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `ControlActionDependency`
-- ----------------------------
BEGIN;
INSERT INTO `ControlActionDependency` VALUES ('LSR', 'AddVM'), ('LSR', 'RmVM'), ('MLR', 'RmVM'), ('MLR', 'AddVM'), ('LSR', 'DPL'), ('MLR', 'DPL'), ('DPL', 'AddVM');
COMMIT;

-- ----------------------------
--  Table structure for `MonitoringAndControlAction`
-- ----------------------------
DROP TABLE IF EXISTS `MonitoringAndControlAction`;
CREATE TABLE `MonitoringAndControlAction` (
  `actionID` varchar(30) DEFAULT NULL,
  `actionName` varchar(30) DEFAULT NULL,
  `artifact` varchar(200) DEFAULT NULL,
  `api` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Records of `MonitoringAndControlAction`
-- ----------------------------
BEGIN;
INSERT INTO `MonitoringAndControlAction` VALUES ('LSR', 'Least Square Regression', 'http://[ip]/lsr.war', 'http://[ip]/ws/lsr'), ('AddVM', 'Add VM', 'http://[ip]/addvm.war', 'http://[ip]/ws/addvm'), ('RmVM', 'Remove VM', 'http://[ip]/rmvm.war', 'http://[ip]/ws/rmvm'), ('dataCompletenessMeasurement', 'dataCompletenessMeasurement', 'http://[ip]/dataCompletenessMeasurement.war', 'http://[ip]/ws/dataCompletenes'), ('throughputMeasurement', 'throughputMeasurement', 'http://[ip]/throughputMeasurement.war', 'http://[ip]/ws/throughputMeasu'), ('MLR', 'Multi-Linear Regression', 'http://[ip]/mlr.war', 'http://[ip]/ws/mlr'), ('DPL', 'artifact deployment', 'http://[ip]/dpl.war', 'http://[ip]/ws/dpl');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
