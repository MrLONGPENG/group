# 更新商品表的type类型注释
ALTER TABLE t_wx_goods MODIFY COLUMN `type` TINYINT(4) COMMENT '商品类型(1:押金 2:套餐 3:午休 4:被子)';
# 更新订单表的支付状态类型注释
ALTER TABLE t_wx_order MODIFY COLUMN `pay_status` TINYINT(4) COMMENT '实际支付状态 1.统一下单 2.支付完成 4.已退款';
# 退款记录表中增加退款类型字段
ALTER TABLE t_wx_refund_record add refund_type TINYINT(4) DEFAULT NULL COMMENT '退款类型 1.押金退款 2.订单退款';
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
  `pay_status` TINYINT(4) DEFAULT NULL COMMENT '实际支付状态  1.统一下单 2.支付完成 3.支付异常',
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
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='支付记录辅表';
-- ----------------------------
-- Table structure for t_wx_refund_record(退款记录表)
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_refund_record`;
CREATE TABLE `t_wx_refund_record` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `open_id` VARCHAR(128) DEFAULT NULL COMMENT '微信对外唯一ID',
  `trade_no` VARCHAR(32) DEFAULT NULL COMMENT '内部订单号，如20180626123456',
  `refund_no`  VARCHAR(32) DEFAULT NULL COMMENT '退款订单号，如201806261234561',
  `refund_count` INT(11) DEFAULT NULL COMMENT '退款次数',
  `refundDesc` VARCHAR(200) DEFAULT NULL COMMENT '退款原因',
  `refund_price` INT(11) DEFAULT NULL COMMENT '退款金额',
  `total_price` INT(11) DEFAULT NULL COMMENT '总金额',
  `refund_type` TINYINT(4) DEFAULT NULL COMMENT '退款类型 1.押金退款 2.订单退款',
  `refund_status` TINYINT(4) DEFAULT NULL COMMENT '退款状态 1.退款失败 2.退款成功',
  `crtTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户申请退款时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='退款记录表';
-- ----------------------------
-- Table structure for t_wx_deduction_record(扣费记录表)
-- ----------------------------
DROP TABLE IF EXISTS `t_wx_deduction_record`;
CREATE TABLE `t_wx_deduction_record`(
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
   `open_id` VARCHAR(32) DEFAULT NULL COMMENT '微信对外唯一ID',
   `trade_no`VARCHAR(32) DEFAULT NULL COMMENT '内部订单号，如20180626123456',
   `did` BIGINT(20) DEFAULT NULL COMMENT '业务ID',
   `explain`VARCHAR(50) DEFAULT NULL COMMENT '扣费原因',
   `day` DATE DEFAULT NULL COMMENT '扣费记录产生日期',
   `forfeit` INT(11) DEFAULT NULL COMMENT '扣费金额',
   `timeout` INT(11) DEFAULT NULL COMMENT '超时时长',
   `type`TINYINT(4) DEFAULT NULL COMMENT '扣费类型 1:超时扣费 2:其他',
   `crtTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   UNIQUE KEY `index_key` (`open_id`,`did`,`day`) COMMENT '唯一索引',
   PRIMARY KEY (`id`)
   ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='扣费记录表';


