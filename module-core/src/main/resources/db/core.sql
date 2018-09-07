-- ----------------------------
-- Table structure for t_device
-- ----------------------------
DROP TABLE IF EXISTS `t_device`;
CREATE TABLE `t_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mac` varchar(30) DEFAULT NULL COMMENT 'deviceId did',
  `agentId` int(11) DEFAULT NULL COMMENT '代理商id',
  `hospitalId` int(11) DEFAULT NULL COMMENT '医院id',
  `hospitalBed` varchar(100) DEFAULT NULL COMMENT '医院床位信息',
  `crtTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `crtId` int(11) DEFAULT NULL COMMENT '创建人',
  `status` int(2) DEFAULT NULL COMMENT '状态 14 启用 15禁用 16 借出 17 删除',
  `useflag` int(2) DEFAULT '20' COMMENT '使用状态 19 使用 20 闲置',
  `reserve_date` timestamp NULL DEFAULT NULL COMMENT '预约时间(只针对预约状态或历史预约过)',
  `imgUrl` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `depart` int(11) DEFAULT NULL COMMENT '科室（t_department）ID',
  `code` varchar(30) DEFAULT NULL COMMENT '设备/床唯一编码/邀请码',
  `pay` int(3) DEFAULT '0' COMMENT '是否扫码支付 1 是 0 否',
  `run` int(2) DEFAULT '0' COMMENT '商用',
  `station_id` int(11) DEFAULT '0' COMMENT '护士站Id',
  `is_station` int(11) DEFAULT '0' COMMENT '是否为护士站 0 否 1 是',
  `update_id` int(11) DEFAULT NULL COMMENT '修改人ID',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `issync` int(1) DEFAULT '1' COMMENT '是否与子服务器同步，0：已同步，1：未同步',
  PRIMARY KEY (`id`),
  KEY `index_run` (`run`)
) ENGINE=InnoDB AUTO_INCREMENT=628 DEFAULT CHARSET=utf8 COMMENT='设备关联表';


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
  `crtTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
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
  `aihui_depart_id` int(11) DEFAULT '0' COMMENT '爱汇科室Id 对应t_aihui_department表',
  `remark` varchar(500) DEFAULT NULL COMMENT '科室描述',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `issync` int(11) DEFAULT '1' COMMENT '是否与子服务器同步，0：已同步，1：未同步',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=601 DEFAULT CHARSET=utf8 COMMENT='科室表';