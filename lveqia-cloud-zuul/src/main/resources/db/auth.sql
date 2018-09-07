DROP TABLE IF EXISTS `t_sys_user_role`;
-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'UID',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(32) DEFAULT NULL COMMENT '电子邮箱',
  `address` varchar(64) DEFAULT NULL COMMENT '联系地址',
  `enabled` tinyint(1) DEFAULT '1',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `avatar_url` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('1', '系统管理员', '18521308791', '021-82881234', '上海浦东金桥', '1', 'admin', '$2a$10$ySG2lkvjFHY5O0./CPIE1OI8VJsuKYEzOYzqIa7AJR6sEgSzUFOAm', 'http://cdn.duitang.com/uploads/item/201508/30/20150830105732_nZCLV.jpeg', null);
INSERT INTO `t_sys_user` VALUES ('2', 'leolaurel', '18508429187', '021-11112233', '上海浦东张江', '1', 'leolaurel', '$2a$10$SFvicmWsqHrVYWxVmAN.x.csqLK76QbsOEWUvRoJ6tzx0dyfJxMoi', null, null);



DROP TABLE IF EXISTS `t_sys_menu_role`;
-- ----------------------------
-- Table structure for `t_sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) DEFAULT NULL,
  `path` varchar(64) DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `iconCls` varchar(64) DEFAULT NULL,
  `keepAlive` tinyint(1) DEFAULT NULL,
  `requireAuth` tinyint(1) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `sort` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `t_sys_menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES ('1', '/', '/home', 'Home', '数据概览', 'fa fa-user-circle-o', null, '1', null, '1', '0');
INSERT INTO `t_sys_menu` VALUES ('2', '/', '/home', 'Home', '订单详情', 'fa fa-user-circle-o', null, '1', null, '1', '0');
INSERT INTO `t_sys_menu` VALUES ('3', '/', '/home', 'Home', '系统管理', 'fa fa-address-card-o', null, '1', null, '1', '0');
INSERT INTO `t_sys_menu` VALUES ('4', '/', '/home', 'Home', '医院管理', 'fa fa-money', null, '1', null, '1', '0');
INSERT INTO `t_sys_menu` VALUES ('5', '/', '/home', 'Home', '消息管理', 'fa fa-bar-chart', null, '1', null, '1', '0');
INSERT INTO `t_sys_menu` VALUES ('6', '/', '/home', 'Home', '运营管理', 'fa fa-windows', null, '1', null, '1', '0');
INSERT INTO `t_sys_menu` VALUES ('7', '/data/*', '/data/usage', 'DataUsage', '使用数据', 'fa fa-user-circle-o', null, '1', '1', '1', '0');
INSERT INTO `t_sys_menu` VALUES ('8', '/data/*', '/data/profit', 'DataProfit', '收益数据', 'fa fa-user-circle-o', null, '1', '1', '1', '0');
INSERT INTO `t_sys_menu` VALUES ('9', '/data/*', '/data/export', 'DataExport', '数据下载', 'fa fa-user-circle-o', null, '1', '1', '1', '0');
INSERT INTO `t_sys_menu` VALUES ('10', '/order/*', '/order/list', 'OrderList', '订单统计', 'fa fa-user-circle-o', null, '1', '2', '1', '0');


-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('1', 'ROLE_admin', '系统管理员');
INSERT INTO `t_sys_role` VALUES ('2', 'ROLE_manager', '木巨管理员');
INSERT INTO `t_sys_role` VALUES ('3', 'ROLE_operate', '木巨运营者');
INSERT INTO `t_sys_role` VALUES ('4', 'ROLE_agent', '代理商');


-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `rid` (`rid`),
  KEY `user_role_ibfk_1` (`uid`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `t_sys_user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `t_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('1', '1', '1');




-- ----------------------------
-- Table structure for t_sys_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu_role`;
CREATE TABLE `t_sys_menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`),
  KEY `rid` (`rid`),
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `t_sys_menu` (`id`),
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `t_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Records of t_sys_menu_role
-- ----------------------------
INSERT INTO `t_sys_menu_role` VALUES ('1', '1', '1');
INSERT INTO `t_sys_menu_role` VALUES ('2', '2', '1');
INSERT INTO `t_sys_menu_role` VALUES ('3', '3', '1');
INSERT INTO `t_sys_menu_role` VALUES ('4', '4', '1');
INSERT INTO `t_sys_menu_role` VALUES ('5', '5', '1');
INSERT INTO `t_sys_menu_role` VALUES ('6', '6', '1');
INSERT INTO `t_sys_menu_role` VALUES ('7', '7', '1');
INSERT INTO `t_sys_menu_role` VALUES ('8', '8', '1');
INSERT INTO `t_sys_menu_role` VALUES ('9', '9', '1');
INSERT INTO `t_sys_menu_role` VALUES ('10', '10', '1');

