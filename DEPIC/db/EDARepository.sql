/*
 Navicat Premium Data Transfer

 Source Server         : DEPIC
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : localhost
 Source Database       : EDARepository

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : utf-8

 Date: 02/06/2015 15:54:50 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `DataAsset`
-- ----------------------------
DROP TABLE IF EXISTS `DataAsset`;
CREATE TABLE `DataAsset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `daw_name` varchar(255) DEFAULT NULL,
  `partitionID` varchar(255) DEFAULT NULL,
  `da` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `ProcessingDataAsset`
-- ----------------------------
DROP TABLE IF EXISTS `ProcessingDataAsset`;
CREATE TABLE `ProcessingDataAsset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` varchar(255) DEFAULT NULL,
  `edaas` varchar(255) DEFAULT NULL,
  `daw_name` varchar(100) DEFAULT NULL,
  `partitionID` varchar(255) DEFAULT NULL,
  `da` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
