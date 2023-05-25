/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : halo

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2023-05-25 18:00:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for upload_file
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file` (
  `id` varchar(32) CHARACTER SET utf8mb4 NOT NULL COMMENT '主键ID',
  `file_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '文件名',
  `file_size` int(11) DEFAULT NULL COMMENT '文件大小',
  `file_path` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '路径',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of upload_file
-- ----------------------------
INSERT INTO `upload_file` VALUES ('d86f5f7718dfcb7ed907e0684cdb50df', '640.gif', '764394', '2023/05/25/1657230108225.gif', '2023-05-25 16:57:23');
INSERT INTO `upload_file` VALUES ('de137d493b71a0288daacca07804a0e3', '汉堡店美女 长头发 工作服 ai绘画 动漫高清电脑壁纸_彼岸壁纸.jpg', '1015755', '2023/05/25/1704073336849.jpg', '2023-05-25 17:04:07');
