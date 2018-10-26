-- ----------------------------
-- table device
-- ----------------------------
alter table t_device modify column crtTime timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';
alter table t_device modify column update_time datetime DEFAULT NULL COMMENT '更新时间';
ALTER TABLE t_device CHANGE mac did varchar(30) DEFAULT NULL COMMENT '二维码业务ID' ;
ALTER TABLE t_device CHANGE code bid varchar(30) DEFAULT NULL COMMENT '锁设备ID';
ALTER TABLE t_device CHANGE pay bell tinyint(4) DEFAULT '0' COMMENT '是否响铃 0 响铃 其他不响';
ALTER TABLE t_device drop  column reserve_date;
ALTER TABLE t_device drop  column imgUrl;
ALTER TABLE t_device drop  column station_id;
ALTER TABLE t_device drop  column is_station;
-- 保证重新修改
ALTER TABLE t_device CHANGE did did varchar(30) DEFAULT NULL COMMENT '二维码业务ID' ;
ALTER TABLE t_device CHANGE bid bid varchar(30) DEFAULT NULL COMMENT '锁设备ID';
ALTER TABLE t_device CHANGE bell bell tinyint(4) DEFAULT '0' COMMENT '是否响铃 0 响铃 其他不响';
-- 新增类型更换
ALTER TABLE t_device CHANGE status status tinyint(4) DEFAULT NULL COMMENT '状态 14 启用 15禁用 16 借出 17 删除';
ALTER TABLE t_device CHANGE useflag useflag tinyint(4) DEFAULT '20' COMMENT '使用状态 19 使用 20 闲置';
-- ----------------------------
-- table department
-- ----------------------------
ALTER TABLE t_department CHANGE aihui_depart_id  mid int(11) DEFAULT '0' COMMENT '木巨科室Id 对应t_dict_department表';
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

