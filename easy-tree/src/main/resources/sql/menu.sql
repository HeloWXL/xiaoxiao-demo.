/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : guns

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2023-06-09 17:50:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编号',
  `pcode` varchar(255) DEFAULT NULL COMMENT '菜单父编号',
  `pcodes` varchar(255) DEFAULT NULL COMMENT '当前菜单的所有父菜单编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(255) DEFAULT NULL COMMENT 'url地址',
  `num` int(65) DEFAULT NULL COMMENT '菜单排序号',
  `levels` int(65) DEFAULT NULL COMMENT '菜单层级',
  `ismenu` int(11) DEFAULT NULL COMMENT '是否是菜单（1：是  0：不是）',
  `tips` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` int(65) DEFAULT NULL COMMENT '菜单状态 :  1:启用   0:不启用',
  `isopen` int(11) DEFAULT NULL COMMENT '是否打开:    1:打开   0:不打开',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1634099559367077895 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('105', 'system', '0', '[0],', '系统管理', 'fa-user', '#', '4', '1', '1', null, '1', '1');
INSERT INTO `sys_menu` VALUES ('106', 'mgr', 'system', '[0],[system],', '用户管理', '', '/mgr', '1', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('107', 'mgr_add', 'mgr', '[0],[system],[mgr],', '添加用户', null, '/mgr/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('108', 'mgr_edit', 'mgr', '[0],[system],[mgr],', '修改用户', null, '/mgr/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('109', 'mgr_delete', 'mgr', '[0],[system],[mgr],', '删除用户', null, '/mgr/delete', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('110', 'mgr_reset', 'mgr', '[0],[system],[mgr],', '重置密码', null, '/mgr/reset', '4', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('111', 'mgr_freeze', 'mgr', '[0],[system],[mgr],', '冻结用户', null, '/mgr/freeze', '5', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('112', 'mgr_unfreeze', 'mgr', '[0],[system],[mgr],', '解除冻结用户', null, '/mgr/unfreeze', '6', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('113', 'mgr_setRole', 'mgr', '[0],[system],[mgr],', '分配角色', null, '/mgr/setRole', '7', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('114', 'role', 'system', '[0],[system],', '角色管理', null, '/role', '2', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('115', 'role_add', 'role', '[0],[system],[role],', '添加角色', null, '/role/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('116', 'role_edit', 'role', '[0],[system],[role],', '修改角色', null, '/role/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('117', 'role_remove', 'role', '[0],[system],[role],', '删除角色', null, '/role/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('118', 'role_setAuthority', 'role', '[0],[system],[role],', '配置权限', null, '/role/setAuthority', '4', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('119', 'menu', 'system', '[0],[system],', '菜单管理', null, '/menu', '4', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('120', 'menu_add', 'menu', '[0],[system],[menu],', '添加菜单', null, '/menu/add', '1', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('121', 'menu_edit', 'menu', '[0],[system],[menu],', '修改菜单', null, '/menu/edit', '2', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('122', 'menu_remove', 'menu', '[0],[system],[menu],', '删除菜单', null, '/menu/remove', '3', '3', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('128', 'log', 'system', '[0],[system],', '业务日志', null, '/log', '6', '2', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('130', 'druid', 'system', '[0],[system],', '监控管理', null, '/druid', '7', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('131', 'dept', 'system', '[0],[system],', '专业管理', null, '/dept', '3', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('132', 'dict', 'system', '[0],[system],', '字典管理', null, '/dict', '4', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('133', 'loginLog', 'system', '[0],[system],', '登录日志', null, '/loginLog', '6', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('134', 'log_clean', 'log', '[0],[system],[log],', '清空日志', null, '/log/delLog', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('135', 'dept_add', 'dept', '[0],[system],[dept],', '添加专业', null, '/dept/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('136', 'dept_update', 'dept', '[0],[system],[dept],', '修改专业', null, '/dept/update', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('137', 'dept_delete', 'dept', '[0],[system],[dept],', '删除专业', null, '/dept/delete', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('138', 'dict_add', 'dict', '[0],[system],[dict],', '添加字典', null, '/dict/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('139', 'dict_update', 'dict', '[0],[system],[dict],', '修改字典', null, '/dict/update', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('140', 'dict_delete', 'dict', '[0],[system],[dict],', '删除字典', null, '/dict/delete', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('141', 'notice', 'system', '[0],[system],', '通知管理', null, '/notice', '9', '2', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('142', 'notice_add', 'notice', '[0],[system],[notice],', '添加通知', null, '/notice/add', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('143', 'notice_update', 'notice', '[0],[system],[notice],', '修改通知', null, '/notice/update', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('144', 'notice_delete', 'notice', '[0],[system],[notice],', '删除通知', null, '/notice/delete', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('145', 'hello', '0', '[0],', '通知', 'fa-rocket', '/notice/hello', '1', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('148', 'code', '0', '[0],', '代码生成', 'fa-code', '/code', '3', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('149', 'api_mgr', '0', '[0],', '接口文档', 'fa-leaf', '/swagger-ui.html', '2', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('150', 'to_menu_edit', 'menu', '[0],[system],[menu],', '菜单编辑跳转', '', '/menu/menu_edit', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('151', 'menu_list', 'menu', '[0],[system],[menu],', '菜单列表', '', '/menu/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('152', 'to_dept_update', 'dept', '[0],[system],[dept],', '修改专业跳转', '', '/dept/dept_update', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('153', 'dept_list', 'dept', '[0],[system],[dept],', '专业列表', '', '/dept/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('154', 'dept_detail', 'dept', '[0],[system],[dept],', '专业详情', '', '/dept/detail', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('155', 'to_dict_edit', 'dict', '[0],[system],[dict],', '修改菜单跳转', '', '/dict/dict_edit', '4', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('156', 'dict_list', 'dict', '[0],[system],[dict],', '字典列表', '', '/dict/list', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('157', 'dict_detail', 'dict', '[0],[system],[dict],', '字典详情', '', '/dict/detail', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('158', 'log_list', 'log', '[0],[system],[log],', '日志列表', '', '/log/list', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('159', 'log_detail', 'log', '[0],[system],[log],', '日志详情', '', '/log/detail', '3', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('160', 'del_login_log', 'loginLog', '[0],[system],[loginLog],', '清空登录日志', '', '/loginLog/delLoginLog', '1', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('161', 'login_log_list', 'loginLog', '[0],[system],[loginLog],', '登录日志列表', '', '/loginLog/list', '2', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('162', 'to_role_edit', 'role', '[0],[system],[role],', '修改角色跳转', '', '/role/role_edit', '5', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('163', 'to_role_assign', 'role', '[0],[system],[role],', '角色分配跳转', '', '/role/role_assign', '6', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('164', 'role_list', 'role', '[0],[system],[role],', '角色列表', '', '/role/list', '7', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('165', 'to_assign_role', 'mgr', '[0],[system],[mgr],', '分配角色跳转', '', '/mgr/role_assign', '8', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('166', 'to_user_edit', 'mgr', '[0],[system],[mgr],', '编辑用户跳转', '', '/mgr/user_edit', '9', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('167', 'mgr_list', 'mgr', '[0],[system],[mgr],', '用户列表', '', '/mgr/list', '10', '3', '0', null, '1', null);
INSERT INTO `sys_menu` VALUES ('1351001193349033985', 'dbNews', '0', '[0],', '资讯动态', '', '/dbNews', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351001193349033986', 'dbNews_list', 'dbNews', '[0],[dbNews],', '宠物资讯列表', '', '/dbNews/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351001193349033987', 'dbNews_add', 'dbNews', '[0],[dbNews],', '宠物资讯添加', '', '/dbNews/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351001193349033988', 'dbNews_update', 'dbNews', '[0],[dbNews],', '宠物资讯更新', '', '/dbNews/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351001193349033989', 'dbNews_delete', 'dbNews', '[0],[dbNews],', '宠物资讯删除', '', '/dbNews/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351001193349033990', 'dbNews_detail', 'dbNews', '[0],[dbNews],', '宠物资讯详情', '', '/dbNews/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351041999657193473', 'dbUser', '0', '[0],', '用户', '', '/dbUser', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351041999657193474', 'dbUser_list', 'dbUser', '[0],[dbUser],', '用户列表', '', '/dbUser/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351041999657193475', 'dbUser_add', 'dbUser', '[0],[dbUser],', '用户添加', '', '/dbUser/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351041999657193476', 'dbUser_update', 'dbUser', '[0],[dbUser],', '用户更新', '', '/dbUser/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351041999657193477', 'dbUser_delete', 'dbUser', '[0],[dbUser],', '用户删除', '', '/dbUser/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351041999657193478', 'dbUser_detail', 'dbUser', '[0],[dbUser],', '用户详情', '', '/dbUser/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1351448312715321345', 'dbConsult', '0', '[0],', '咨询回复', '', '/dbConsult', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1355778621250392066', 'dbVideo', '0', '[0],', '考公课程', '', '/dbVideo', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1355778621250392067', 'dbVideo_list', 'dbVideo', '[0],[dbVideo],', '考研资源列表', '', '/dbVideo/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1355778621250392068', 'dbVideo_add', 'dbVideo', '[0],[dbVideo],', '考研资源添加', '', '/dbVideo/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1355778621250392069', 'dbVideo_update', 'dbVideo', '[0],[dbVideo],', '考研资源更新', '', '/dbVideo/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1355778621250392070', 'dbVideo_delete', 'dbVideo', '[0],[dbVideo],', '考研资源删除', '', '/dbVideo/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1355778621250392071', 'dbVideo_detail', 'dbVideo', '[0],[dbVideo],', '考研资源详情', '', '/dbVideo/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1356278250333196290', 'dbTheme', '0', '[0],', '论坛主题', '', '/dbTheme', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1356278250333196291', 'dbTheme_list', 'dbTheme', '[0],[dbTheme],', '论题主题列表', '', '/dbTheme/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1356278250333196292', 'dbTheme_add', 'dbTheme', '[0],[dbTheme],', '论题主题添加', '', '/dbTheme/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1356278250333196293', 'dbTheme_update', 'dbTheme', '[0],[dbTheme],', '论题主题更新', '', '/dbTheme/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1356278250333196294', 'dbTheme_delete', 'dbTheme', '[0],[dbTheme],', '论题主题删除', '', '/dbTheme/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1356278250333196295', 'dbTheme_detail', 'dbTheme', '[0],[dbTheme],', '论题主题详情', '', '/dbTheme/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583022372484710402', 'dbActivity', '0', '[0],', '线下讲座', '', '/dbActivity', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583022372484710403', 'dbActivity_list', 'dbActivity', '[0],[dbActivity],', '校园活动列表', '', '/dbActivity/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583022372484710404', 'dbActivity_add', 'dbActivity', '[0],[dbActivity],', '校园活动添加', '', '/dbActivity/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583022372484710405', 'dbActivity_update', 'dbActivity', '[0],[dbActivity],', '校园活动更新', '', '/dbActivity/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583022372484710406', 'dbActivity_delete', 'dbActivity', '[0],[dbActivity],', '校园活动删除', '', '/dbActivity/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583022372484710407', 'dbActivity_detail', 'dbActivity', '[0],[dbActivity],', '校园活动详情', '', '/dbActivity/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042497', 'dbOrder', '0', '[0],', '讲座预约', '', '/dbOrder', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042498', 'dbOrder_list', 'dbOrder', '[0],[dbOrder],', '活动预约列表', '', '/dbOrder/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042499', 'dbOrder_add', 'dbOrder', '[0],[dbOrder],', '活动预约添加', '', '/dbOrder/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042500', 'dbOrder_update', 'dbOrder', '[0],[dbOrder],', '活动预约更新', '', '/dbOrder/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042501', 'dbOrder_delete', 'dbOrder', '[0],[dbOrder],', '活动预约删除', '', '/dbOrder/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042502', 'dbOrder_detail', 'dbOrder', '[0],[dbOrder],', '活动预约详情', '', '/dbOrder/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1583064168808042503', 'dbTeacher', '0', '[0],', '讲师管理', '', '/user/teacher', '90', '1', '1', null, '1', null);
INSERT INTO `sys_menu` VALUES ('1609104052676972545', 'dbExercises', '0', '[0],', '习题讲解', '', '/dbExercises', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1609104052676972546', 'dbExercises_list', 'dbExercises', '[0],[dbExercises],', '习题讲解列表', '', '/dbExercises/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1609104052676972547', 'dbExercises_add', 'dbExercises', '[0],[dbExercises],', '习题讲解添加', '', '/dbExercises/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1609104052676972548', 'dbExercises_update', 'dbExercises', '[0],[dbExercises],', '习题讲解更新', '', '/dbExercises/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1609104052676972549', 'dbExercises_delete', 'dbExercises', '[0],[dbExercises],', '习题讲解删除', '', '/dbExercises/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1609104052676972550', 'dbExercises_detail', 'dbExercises', '[0],[dbExercises],', '习题讲解详情', '', '/dbExercises/detail', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1634099559367077889', 'dbResources', '0', '[0],', '学习资料', '', '/dbResources', '99', '1', '1', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1634099559367077890', 'dbResources_list', 'dbResources', '[0],[dbResources],', '学习资源列表', '', '/dbResources/list', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1634099559367077891', 'dbResources_add', 'dbResources', '[0],[dbResources],', '学习资源添加', '', '/dbResources/add', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1634099559367077892', 'dbResources_update', 'dbResources', '[0],[dbResources],', '学习资源更新', '', '/dbResources/update', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1634099559367077893', 'dbResources_delete', 'dbResources', '[0],[dbResources],', '学习资源删除', '', '/dbResources/delete', '99', '2', '0', null, '1', '0');
INSERT INTO `sys_menu` VALUES ('1634099559367077894', 'dbResources_detail', 'dbResources', '[0],[dbResources],', '学习资源详情', '', '/dbResources/detail', '99', '2', '0', null, '1', '0');
