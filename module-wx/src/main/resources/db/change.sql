# 增加字段支付类型 1:晚休 2:午休
alter table t_wx_order add order_type tinyint(4) DEFAULT NULL COMMENT '支付类型 1:晚休 2:午休';
# 更新旧数据
UPDATE t_wx_order SET order_type = 1 WHERE  gid in (SELECT id FROM t_wx_goods g WHERE g.type = 2);
UPDATE t_wx_order SET order_type = 2 WHERE  gid in (SELECT id FROM t_wx_goods g WHERE g.type = 3);
UPDATE t_wx_order SET order_type = 0 WHERE  order_type is NULL;
# 更新商品表的type类型注释
ALTER TABLE t_wx_goods MODIFY COLUMN `type` TINYINT(4) COMMENT '商品类型(1:押金 2:套餐 3:午休 4:被子)';