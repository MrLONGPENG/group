-- ----------------------------
-- table device
-- ----------------------------
ALTER TABLE t_device CHANGE mac did varchar(30) COMMENT '二维码业务ID' ;
ALTER TABLE t_device CHANGE code bid varchar(30) COMMENT '锁ID';
ALTER TABLE t_device CHANGE pay bell int(3) COMMENT '是否响铃';
ALTER TABLE  t_device drop  column reserve_date;
ALTER TABLE  t_device drop  column imgUrl;
ALTER TABLE  t_device drop  column station_id;
ALTER TABLE  t_device drop  column is_station;
ALTER TABLE t_device ALTER COLUMN bell DROP DEFAULT;
ALTER TABLE t_device ALTER COLUMN bell SET DEFAULT '0'
-- ----------------------------
-- table department
-- ----------------------------
ALTER TABLE device.t_department CHANGE aihui_depart_id  mid int(11) DEFAULT '0' COMMENT '木巨科室Id 对应t_dict_department表'
-- ----------------------------
-- table t_dict_department
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

