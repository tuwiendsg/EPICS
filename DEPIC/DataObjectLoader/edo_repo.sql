/*
 Navicat Premium Data Transfer

 Source Server         : DEPIC
 Source Server Type    : MySQL
 Source Server Version : 50521
 Source Host           : localhost
 Source Database       : edo_repo

 Target Server Type    : MySQL
 Target Server Version : 50521
 File Encoding         : utf-8

 Date: 07/30/2014 01:10:25 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `edo`
-- ----------------------------
DROP TABLE IF EXISTS `edo`;
CREATE TABLE `edo` (
  `key` varchar(30) DEFAULT NULL,
  `content` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `edo_delivery`
-- ----------------------------
DROP TABLE IF EXISTS `edo_delivery`;
CREATE TABLE `edo_delivery` (
  `key` varchar(255) DEFAULT NULL,
  `content` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
