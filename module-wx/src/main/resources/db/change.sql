# 增加字段支付类型 1:晚休 2:午休
alter table t_wx_order add order_type tinyint(4) DEFAULT NULL COMMENT '支付类型 1:晚休 2:午休';
# 更新旧数据
UPDATE t_wx_order SET order_type = 1 WHERE  gid in (SELECT id FROM t_wx_goods g WHERE g.type = 2);
UPDATE t_wx_order SET order_type = 2 WHERE  gid in (SELECT id FROM t_wx_goods g WHERE g.type = 3);
UPDATE t_wx_order SET order_type = 0 WHERE  order_type is NULL;