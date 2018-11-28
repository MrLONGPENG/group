
# 更新旧数据
UPDATE t_wx_order SET order_type = 1 WHERE  gid in (SELECT id FROM t_wx_goods g WHERE g.type = 2);
UPDATE t_wx_order SET order_type = 2 WHERE  gid in (SELECT id FROM t_wx_goods g WHERE g.type = 3);
UPDATE t_wx_order SET order_type = 0 WHERE  order_type is NULL;
# 更新商品表的type类型注释
ALTER TABLE t_wx_goods MODIFY COLUMN `type` TINYINT(4) COMMENT '商品类型(1:押金 2:套餐 3:午休 4:被子)';
-- ----------------------------
-- Table structure for t_wx_deposit(押金表)
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_deposit`;
CREATE TABLE `t_wx_deposit` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `gid` INT(11) DEFAULT NULL COMMENT '商品ID',
  `open_id` VARCHAR(128) DEFAULT NULL COMMENT '微信对外唯一ID',
  `trade_no` VARCHAR(32) DEFAULT NULL COMMENT '内部订单号，如20180626123456',
  `deposit` INT(11) DEFAULT NULL COMMENT '押金金额',
  `status` TINYINT(4) DEFAULT NULL COMMENT '押金状态 1.已支付  2.退款中 4.审核通过',
  `crtTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updTime` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='押金表';
-- ----------------------------
-- Table structure for t_wx_record_main(支付记录主表)
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_record_main`;
CREATE TABLE `t_wx_record_main` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `did` BIGINT(20) DEFAULT NULL COMMENT '唯一业务ID',
  `aid` INT(11) DEFAULT NULL COMMENT '代理商ID',
  `hid` INT(11) DEFAULT NULL COMMENT '医院ID',
  `oid` INT(11) DEFAULT NULL COMMENT '科室ID',
  `open_id` VARCHAR(128) DEFAULT NULL COMMENT '微信对外唯一ID',
  `trade_no` VARCHAR(32) DEFAULT NULL COMMENT '内部订单号，如20180626123456',
  `transaction_id` VARCHAR(32) DEFAULT NULL COMMENT '微信订单号，如20180626123456',
  `total_price` INT(11) DEFAULT NULL COMMENT '支付总金额',
  `refund_count` INT(11) DEFAULT NULL COMMENT '退款次数',
  `refund_price` INT(11) DEFAULT NULL COMMENT '退款总金额',
  `pay_status` TINYINT(4) DEFAULT NULL COMMENT '实际支付状态 1.统一下单 2.支付完成',
  `crtTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_union` (`aid`,`hid`,`oid`) USING BTREE,
  KEY `index_payRecord` (`trade_no`) USING BTREE
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='支付记录主表';
-- ----------------------------
-- Table structure for t_wx_record_assist(支付记录辅表)
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_record_assist`;
CREATE TABLE `t_wx_record_assist` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `mid` BIGINT(20) NOT NULL COMMENT '支付主表ID',
  `gid` INT(11) DEFAULT NULL COMMENT '商品ID',
  `price` INT(11) DEFAULT NULL COMMENT '金额',
  `crtTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `type` INT(11) DEFAULT NULL COMMENT '类型：1:押金 2:套餐 3:午休 4:被子',
  PRIMARY KEY (`id`),
  KEY `fk_recordMain` (`mid`),
  CONSTRAINT `fk_recordMain` FOREIGN KEY (`mid`) REFERENCES `t_wx_record_main` (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='支付记录辅表'
