/*
 Navicat Premium Data Transfer

 Source Server         : DEPIC
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : localhost
 Source Database       : PrimitiveActionMetadata

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : utf-8

 Date: 06/16/2015 17:23:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `AdjustmentActionParameter`
-- ----------------------------
DROP TABLE IF EXISTS `AdjustmentActionParameter`;
CREATE TABLE `AdjustmentActionParameter` (
  `ActionName` varchar(255) DEFAULT NULL,
  `AdjustmentCase` varchar(255) DEFAULT NULL,
  `ParameterName` varchar(255) DEFAULT NULL,
  `ParameterType` varchar(255) DEFAULT NULL,
  `ParameterValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `AdjustmentCase`
-- ----------------------------
DROP TABLE IF EXISTS `AdjustmentCase`;
CREATE TABLE `AdjustmentCase` (
  `ActionName` varchar(255) DEFAULT NULL,
  `AdjustmentCase` varchar(255) DEFAULT NULL,
  `EstimatedResultFrom` double DEFAULT NULL,
  `EstimatedResultTo` double DEFAULT NULL,
  `AnalyticsTaskName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `AnalyticTask`
-- ----------------------------
DROP TABLE IF EXISTS `AnalyticTask`;
CREATE TABLE `AnalyticTask` (
  `ActionName` varchar(255) DEFAULT NULL,
  `AdjustmentCase` varchar(255) DEFAULT NULL,
  `AnalyticTask` varchar(255) DEFAULT NULL,
  `ParameterName` varchar(255) DEFAULT NULL,
  `ParameterType` varchar(255) DEFAULT NULL,
  `ParameterValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `Artifact`
-- ----------------------------
DROP TABLE IF EXISTS `Artifact`;
CREATE TABLE `Artifact` (
  `ArtifactName` varchar(255) DEFAULT NULL,
  `ActionName` varchar(255) DEFAULT NULL,
  `ArtifactDescription` varchar(255) DEFAULT NULL,
  `Location` varchar(255) DEFAULT NULL,
  `Type` varchar(255) DEFAULT NULL,
  `RESTfulAPI` varchar(255) DEFAULT NULL,
  `HttpMethod` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `MonitoringActionParameter`
-- ----------------------------
DROP TABLE IF EXISTS `MonitoringActionParameter`;
CREATE TABLE `MonitoringActionParameter` (
  `ActionName` varchar(255) DEFAULT NULL,
  `ParameterName` varchar(255) DEFAULT NULL,
  `ParameterType` varchar(255) DEFAULT NULL,
  `ParameterValue` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `PrerequisiteAction`
-- ----------------------------
DROP TABLE IF EXISTS `PrerequisiteAction`;
CREATE TABLE `PrerequisiteAction` (
  `ActionName` varchar(255) DEFAULT NULL,
  `PrerequisiteAction` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `PrimitiveAction`
-- ----------------------------
DROP TABLE IF EXISTS `PrimitiveAction`;
CREATE TABLE `PrimitiveAction` (
  `ActionName` varchar(255) NOT NULL DEFAULT '',
  `AssociatedQoRMetric` varchar(255) DEFAULT NULL,
  `ArtifactName` varchar(255) DEFAULT NULL,
  `ActionType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ActionName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `ResourceControlCase`
-- ----------------------------
DROP TABLE IF EXISTS `ResourceControlCase`;
CREATE TABLE `ResourceControlCase` (
  `ResourceControlActionName` varchar(255) DEFAULT NULL,
  `ResourceControlCase` varchar(255) DEFAULT NULL,
  `EstimatedResultFrom` double DEFAULT NULL,
  `EstimatedResultTo` double DEFAULT NULL,
  `AnalyticsTaskName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `ResourceControlStrategy`
-- ----------------------------
DROP TABLE IF EXISTS `ResourceControlStrategy`;
CREATE TABLE `ResourceControlStrategy` (
  `ResourceControlActionName` varchar(255) DEFAULT NULL,
  `ResourceControlCase` varchar(255) DEFAULT NULL,
  `ControlMetric` varchar(255) DEFAULT NULL,
  `EffectedActionName` varchar(255) DEFAULT NULL,
  `ScaleInConditionFrom` double DEFAULT NULL,
  `ScaleInConditionTo` double DEFAULT NULL,
  `ScaleOutConditionFrom` double DEFAULT NULL,
  `ScaleOutConditionTo` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
