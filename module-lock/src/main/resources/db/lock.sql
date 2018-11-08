-- ----------------------------
-- Table structure for t_lock_did
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_did`;
CREATE TABLE `t_lock_did` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`did` bigint(20) DEFAULT NULL COMMENT '唯一业务ID',
`brand` tinyint(4) DEFAULT NULL COMMENT '设备锁厂商品牌(1:连旅；2:待定)',
`lock_id` bigint(20) DEFAULT NULL COMMENT '设备锁十进制ID',
`lock_hex` varchar(32) DEFAULT NULL COMMENT '设备锁十六进制ID',
`update_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
UNIQUE KEY `index_did` (`did`) COMMENT '唯一业务ID索引',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='柜子业务编号与锁编号关系表';


INSERT INTO `t_lock_did` VALUES (1, 8666600608, 1, 2630915848256346975, '2482e4065943db5f', '2018-06-08 14:31:37');
INSERT INTO `t_lock_did` VALUES (2, 8666600609, 1, 2630915848256348010, '2482e4065943df6a', '2018-06-08 14:31:38');
INSERT INTO `t_lock_did` VALUES (3, 8666600610, 1, 2666073827771144345, '24ffcc055943d899', '2018-06-08 14:31:39');
INSERT INTO `t_lock_did` VALUES (4, 001001001, 1, 2630915848256346975, '2482e4065943db5f', '2018-06-08 14:31:37');
INSERT INTO `t_lock_did` VALUES (5, 001001002, 1, 2630915848256348010, '2482e4065943df6a', '2018-06-08 14:31:38');
INSERT INTO `t_lock_did` VALUES (6, 001001003, 1, 2666073827771144345, '24ffcc055943d899', '2018-06-08 14:31:39');


-- ----------------------------
-- Table structure for t_lock_info
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_info`;
CREATE TABLE `t_lock_info` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`lock_id` bigint(20) DEFAULT NULL COMMENT '设备ID',
`brand` tinyint(4) DEFAULT NULL COMMENT '设备锁厂商品牌(1:连旅；2:待定)',
`mac` varchar(32) DEFAULT NULL COMMENT '设备蓝牙地址',
`key` varchar(32) DEFAULT NULL COMMENT '设备蓝牙密钥',
`sim_id` varchar(32) DEFAULT NULL COMMENT 'SIM卡ID',
`f_version` int(11) DEFAULT NULL COMMENT '固件版本',
`h_version` int(11) DEFAULT NULL COMMENT '硬件版本',
`longitude` decimal(10,7) DEFAULT NULL COMMENT '经度',
`latitude` decimal(10,7) DEFAULT NULL COMMENT '纬度',
`csq` int(11) DEFAULT NULL COMMENT '信号指标',
`temp` int(11) DEFAULT NULL COMMENT '温度',
`charge` int(11) DEFAULT NULL COMMENT '充电电压(原值:vbus)',
`voltage` int(11) DEFAULT NULL COMMENT '电池电压(原值:vbattery)',
`electric` int(11) DEFAULT NULL COMMENT '充电电流(原值:iCharge)',
`upgrade` tinyint(4)  DEFAULT NULL COMMENT '升级标识(原值:upgradeFlag)',
`battery_stat` tinyint(4) DEFAULT NULL COMMENT '剩余电量',
`lock_status` tinyint(4)  DEFAULT NULL COMMENT '锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内',
`last_refresh` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '最后上报时间',
UNIQUE KEY `index_did` (`did`) COMMENT '唯一业务ID索引',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁设备信息表';
-- ----------------------------
-- Table structure for t_lock_record
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_switch`;
CREATE TABLE `t_lock_switch`(
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`did` bigint(20) DEFAULT NULL COMMENT '业务ID',
`lock_id` bigint(20) DEFAULT NULL COMMENT '设备锁十进制ID',
`receiveTime` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '开关锁时间',
`lockStatus` tinyint(4) DEFAULT 0 COMMENT '状态 1 关闭 2 打开',
`localTime` datetime  DEFAULT NULL COMMENT '保存到本地的时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='开关锁状态表';
-- ----------------------------
-- Table structure for t_lock_record
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_record`;
CREATE TABLE `t_lock_record` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`did` bigint(20) DEFAULT NULL COMMENT '业务ID',
`lock_id` bigint(20) DEFAULT NULL COMMENT '设备锁十进制ID',
`csq` int(11) DEFAULT NULL COMMENT '信号指标',
`temp` int(11) DEFAULT NULL COMMENT '温度',
`charge` int(11) DEFAULT NULL COMMENT '充电电压(原值:vbus)',
`voltage` int(11) DEFAULT NULL COMMENT '电池电压(原值:vbattery)',
`electric` int(11) DEFAULT NULL COMMENT '充电电流(原值:iCharge)',
`battery_stat` tinyint(4) DEFAULT NULL COMMENT '剩余电量',
`lock_status` tinyint(4)  DEFAULT NULL COMMENT '锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内',
`last_refresh` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '最后上报时间',
`crtTime` datetime DEFAULT NULL COMMENT '创建时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁记录表';
-- ----------------------------
-- Table structure for t_lock_fail
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_fail`;
CREATE TABLE `t_lock_fail` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`did` bigint(20) DEFAULT NULL COMMENT '业务ID',
`lock_id` bigint(20) DEFAULT NULL COMMENT '设备锁十进制ID',
`aid` int(11) DEFAULT NULL COMMENT '代理商id',
  `hid` int(11) DEFAULT NULL COMMENT '医院id',
 `oid` int(11) DEFAULT NULL COMMENT '科室id',
 `fail_type` tinyint(4) DEFAULT NULL COMMENT '故障类型 1:电量异常 2:信号异常 4:开关锁异常',
 `error_type` tinyint(4) DEFAULT NULL COMMENT '错误类型 1:低电量 2:电量下降异常 3:无法充电 4:无信号 5:信号波动异常 6:开锁机械故障 7:关锁机械故障 8:超时未关锁 9:无订单异常开锁 10:非使用时段开锁',
`last_refresh` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '故障上报时间',
`finish_time` datetime DEFAULT NULL COMMENT '解决时间',
`status` tinyint(4) DEFAULT '1' COMMENT '故障状态 1:产生异常 2:解决中 3:已解决 4:未解决',
`resolve_man`int(11) DEFAULT NULL COMMENT '处理人',
`resolve_type` tinyint(4) DEFAULT NULL COMMENT '造成故障类型 1:没电了',
`explain` varchar(200) DEFAULT NULL COMMENT '异常产生原因及解决方法',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁故障表';
-- ----------------------------
-- Table structure for t_lock_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_lock_dict`;
CREATE TABLE `t_lock_dict` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`dict_type`varchar(20) DEFAULT NULL COMMENT '故障类型',
`dict_name` varchar(20) DEFAULT NULL COMMENT '故障名称',
`dict_key` tinyint(4) DEFAULT NULL,
`pid` int(11) DEFAULT NULL,
UNIQUE KEY `index_type_key`(`dict_type`,`dict_key`)  COMMENT '唯一联合索引',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='故障字典表';
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail','电量异常',1,0);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail','信号异常',2,0);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail','开关锁异常',4,0);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','低电量',1,1);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','电量下降异常',2,1);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','无法充电',3,1);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','无信号',4,2);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','信号波动异常',5,2);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','开锁机械故障',6,3);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','关锁机械故障',7,3);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','超时未关锁',8,3);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','无订单异常开锁',9,3);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Error','非使用时段开锁',10,3);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Resolve','没电',1,1);
insert into t_lock_dict(dict_type,dict_name,dict_key,pid)values ('Fail_Resolve','信号接收器故障',2,2);


