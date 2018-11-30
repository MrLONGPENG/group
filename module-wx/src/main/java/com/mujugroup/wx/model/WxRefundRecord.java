package com.mujugroup.wx.model;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 退款记录表
 * 类名:WxRefundRecord
 * 创建人:leolaurel
 * 创建时间:20181130
 */
@Data
@SuppressWarnings("serial")
@Table(name = "t_wx_refund_record")
public class WxRefundRecord implements Serializable {

    public final static int PAY_FAIL = 1;//退款失败
    public final static int PAY_SUCCESS = 2;//退款成功
    /**
     * 主键
     * <p>
     * 表字段 : t_wx_refund_record.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_refund_record.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 内部订单号，如20180626123456
     * 表字段 : t_wx_refund_record.trade_no
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 退款订单号，如201806261234561
     * 表字段 : t_wx_refund_record.refund_no
     */
    @Column(name = "refund_no")
    private String refundNo;

    /**
     * 退款次数
     * 表字段 : t_wx_refund_record.refund_count
     */
    @Column(name = "refund_count")
    private Integer refundCount;

    /**
     * 退款原因
     * 表字段 : t_wx_refund_record.refundDesc
     */
    @Column(name = "refundDesc")
    private String refundDesc;

    /**
     * 退款金额
     * 表字段 : t_wx_refund_record.refund_price
     */
    @Column(name = "refund_price")
    private Integer refundPrice;

    /**
     * 总金额
     * 表字段 : t_wx_refund_record.total_price
     */
    @Column(name = "total_price")
    private Integer totalPrice;

    /**
     * 退款状态 1.退款失败 2.退款成功
     * 表字段 : t_wx_refund_record.refund_status
     */
    @Column(name = "refund_status")
    private Integer refundStatus;

    /**
     * 用户申请退款时间
     * 表字段 : t_wx_refund_record.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;


}