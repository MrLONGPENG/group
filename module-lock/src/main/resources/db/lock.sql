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
UNIQUE KEY `index_did` (`did`) COMMENT '唯一业务ID索引',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='锁记录表'


