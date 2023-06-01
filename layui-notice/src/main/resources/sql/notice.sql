/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : halo

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2023-06-01 18:02:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知主键',
  `notice_topic` varchar(64) DEFAULT NULL COMMENT '通知主题',
  `notice_type` tinyint(2) DEFAULT NULL COMMENT '通知类型',
  `notice_obj` tinyint(2) DEFAULT NULL COMMENT '通知对象',
  `notice_content` varchar(255) DEFAULT NULL COMMENT '通知内容',
  `publish_time` datetime DEFAULT NULL COMMENT '通知时间',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for notice_receiver
-- ----------------------------
DROP TABLE IF EXISTS `notice_receiver`;
CREATE TABLE `notice_receiver` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '接收对象主键',
  `notice_id` int(11) DEFAULT NULL COMMENT '通知ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `is_read` tinyint(2) DEFAULT NULL COMMENT '是否已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of notice_receiver
-- ----------------------------
