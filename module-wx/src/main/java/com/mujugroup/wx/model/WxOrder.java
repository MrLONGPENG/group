package com.mujugroup.wx.model;


import com.lveqia.cloud.common.config.Constant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 订单信息表
 * 类名:WxOrder
 * 创建人:leolaurel
 * 创建时间:20180713
 */

@Data
@SuppressWarnings("serial")
@Table(name = "t_wx_order")
@EqualsAndHashCode(callSuper = true)
public class WxOrder extends WxBase {

	public static final Integer TYPE_PAY_WAITING = 1;
    public static final Integer TYPE_PAY_SUCCESS = 2;
	/*** 支付类型-晚休-晚上套餐 */
	public static final Integer ORDER_TYPE_NIGHT  = 1;
	/*** 支付类型-午休-中午套餐 */
	public static final Integer ORDER_TYPE_MIDDAY = 2;

	/**
     * 主键
     * 
     * 表字段 : t_wx_order.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一业务ID
     * 表字段 : t_wx_order.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 代理ID
     * 表字段 : t_wx_order.aid
     */
    @Column(name = "aid")
    private Integer aid;

    /**
     * 医院ID
     * 表字段 : t_wx_order.hid
     */
    @Column(name = "hid")
    private Integer hid;

    /**
     * 科室ID
     * 表字段 : t_wx_order.oid
     */
    @Column(name = "oid")
    private Integer oid;

    /**
     * 商品ID
     * 表字段 : t_wx_order.gid
     */
    @Column(name = "gid")
    private Integer gid;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_order.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 内部订单号，如20180626123456
     * 表字段 : t_wx_order.trade_no
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 微信订单号，如20180626123456
     * 表字段 : t_wx_order.transaction_id
     */
    @Column(name = "transaction_id")
    private String transactionId;


	/**
	 * 支付类型 1:晚休 2:午休
	 * 表字段 : t_wx_order.order_type
	 */
	@Column(name = "order_type")
	private Integer orderType;


    /**
     * 实际支付价格
     * 表字段 : t_wx_order.pay_price
     */
    @Column(name = "pay_price")
    private Integer payPrice;

    /**
     * 实际支付状态 1.统一下单 2.支付完成
     * 表字段 : t_wx_order.pay_status
     */
    @Column(name = "pay_status")
    private Integer payStatus;

    /**
     * 支付时10位时间戳
     * 表字段 : t_wx_order.pay_time
     */
    @Column(name = "pay_time")
    private Long payTime;

    /**
     * 结束时10位时间戳
     * 表字段 : t_wx_order.end_time
     */
    @Column(name = "end_time")
    private Long endTime;

    /**
     * 创建时间
     * 表字段 : t_wx_order.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;


	/**
	 * 组装订单各个ID  格式：AID&HID&OID&GID
	 */
    public String getKey() {
		StringBuilder sb = new StringBuilder();
		sb.append(getAid()).append(Constant.SIGN_AND).append(getHid()).append(Constant.SIGN_AND);
		sb.append(getOid()).append(Constant.SIGN_AND).append(getGid());
		return new String(sb);
    }
}