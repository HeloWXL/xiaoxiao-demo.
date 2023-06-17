/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50710
 Source Host           : localhost:3306
 Source Schema         : spider

 Target Server Type    : MySQL
 Target Server Version : 50710
 File Encoding         : 65001

 Date: 18/06/2023 07:35:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `movie_name` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `director` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `category` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
