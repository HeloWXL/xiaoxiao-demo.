/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : halo

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2023-07-07 16:52:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_no` varchar(32) DEFAULT NULL COMMENT '学号',
  `stu_name` varchar(32) DEFAULT NULL COMMENT '学生姓名',
  `gender` tinyint(2) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `join_time` date DEFAULT NULL COMMENT '入学时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', null, null, null, null, null, null);
INSERT INTO `student` VALUES ('2', null, null, null, null, null, null);
