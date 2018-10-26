-- ----------------------------
-- Table structure for t_device
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `did` varchar(30) DEFAULT NULL COMMENT '二维码业务ID',
  `bid` varchar(30) DEFAULT NULL COMMENT '锁设备ID',
  `agentId` int(11) DEFAULT NULL COMMENT '代理商id',
  `hospitalId` int(11) DEFAULT NULL COMMENT '医院id',
  `hospitalBed` varchar(100) DEFAULT NULL COMMENT '医院床位信息',
  `crtTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `crtId` int(11) DEFAULT NULL COMMENT '创建人',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态 14 启用 15禁用 16 借出 17 删除',
  `useflag` tinyint(4) DEFAULT '20' COMMENT '使用状态 19 使用 20 闲置',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `depart` int(11) DEFAULT NULL COMMENT '科室（t_department）ID',
  `bell` tinyint(4) DEFAULT '0' COMMENT '是否响铃 0 响铃 其他不响',
  `run` int(2) DEFAULT '0' COMMENT '商用',
  `update_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `issync` int(1) DEFAULT '1' COMMENT '是否与子服务器同步，0：已同步，1：未同步',
  PRIMARY KEY (`id`),
  KEY `index_run` (`run`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='设备关联表';


-- ----------------------------
-- Table structure for t_agent
-- ----------------------------
DROP TABLE IF EXISTS `t_agent`;
CREATE TABLE `t_agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `crtTime` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `enable` tinyint(4) DEFAULT '1' COMMENT '代理商状态 1 启用 2 禁用 0 删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=360 DEFAULT CHARSET=utf8 COMMENT='代理商信息表';


-- ----------------------------
-- Table structure for t_hospital
-- ----------------------------
DROP TABLE IF EXISTS `t_hospital`;
CREATE TABLE `t_hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `agentId` varchar(100) DEFAULT NULL COMMENT '代理商id',
  `tel` varchar(20) DEFAULT NULL COMMENT '电话',
  `person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `crtTime` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `crtId` int(11) DEFAULT NULL COMMENT '创建id',
  `address` varchar(150) DEFAULT NULL,
  `country` int(11) DEFAULT NULL,
  `province` int(11) DEFAULT NULL,
  `city` int(11) DEFAULT NULL,
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `enable` int(3) DEFAULT '22' COMMENT '医院状态 22 启用 23 禁用 17 删除',
  `issync` int(11) DEFAULT '1' COMMENT '是否与子服务器同步，0：已同步，1：未同步',
  `level` varchar(255) DEFAULT '三级甲等' COMMENT '医院等级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COMMENT='医院信息表';



-- ----------------------------
-- Table structure for t_department
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(2) DEFAULT NULL COMMENT '显示状态：1显示;0删除;',
  `hospital_id` int(11) DEFAULT NULL COMMENT '医院ID',
  `name` varchar(200) DEFAULT NULL COMMENT '科室名称',
  `mid` int(11) DEFAULT '0' COMMENT '木巨科室Id 对应t_dict_department表',
  `remark` varchar(500) DEFAULT NULL COMMENT '科室描述',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `issync` int(11) DEFAULT '1' COMMENT '是否与子服务器同步，0：已同步，1：未同步',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='科室表';


-- ----------------------------
-- Table structure for t_country_province_city
-- ----------------------------
DROP TABLE IF EXISTS `t_country_province_city`;
CREATE TABLE `t_country_province_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL COMMENT '国家省份城市名称',
  `pid` int(11) DEFAULT NULL COMMENT 'pid 0 国家',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `key` varchar(10) DEFAULT NULL COMMENT '备用字段',
  `status` int(2) DEFAULT NULL COMMENT '状态 0 禁用 1 启用',
  `ord` int(5) DEFAULT NULL COMMENT '排序',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`),
  KEY `index_pid` (`pid`) USING BTREE,
  KEY `index_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=560 DEFAULT CHARSET=utf8 COMMENT='国家省份城市表';




-- ----------------------------
-- Table structure for t_auth_data
-- ----------------------------
DROP TABLE IF EXISTS `t_auth_data`;
CREATE TABLE `t_auth_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `rid` int(11) DEFAULT NULL COMMENT '关系ID [aid, hid, oid]',
  `type` tinyint(4) DEFAULT '0' COMMENT '关系类型 AID 1; HID 2; OID: 3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限关系表';


-- ----------------------------
-- Table structure for t_dict_department
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_department`;
CREATE TABLE `t_dict_department` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '科室名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP  COMMENT '创建时间',
  `enable` tinyint(4) DEFAULT '0' COMMENT '科室启用状态',
  `create_userid` int(11) DEFAULT NULL COMMENT '创建者ID',
  `update_userid` int(11) DEFAULT NULL COMMENT '更新者ID',
  `update_time` datetime DEFAULT NULL COMMENT '更新者时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='科室字典表';
