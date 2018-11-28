package com.mujugroup.wx.model;

import com.lveqia.cloud.common.config.Constant;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * 支付记录主表
 * 类名:WxRecordMain
 * 创建人:leolaurel
 * 创建时间:20181127
 */
@Data
@SuppressWarnings("serial")
@Table(name = "t_wx_record_main")
public class WxRecordMain implements Serializable {

    /**
     * 主键
     * <p>
     * 表字段 : t_wx_record_main.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一业务ID
     * 表字段 : t_wx_record_main.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 代理商ID
     * 表字段 : t_wx_record_main.aid
     */
    @Column(name = "aid")
    private Integer aid;

    /**
     * 医院ID
     * 表字段 : t_wx_record_main.hid
     */
    @Column(name = "hid")
    private Integer hid;

    /**
     * 科室ID
     * 表字段 : t_wx_record_main.oid
     */
    @Column(name = "oid")
    private Integer oid;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_record_main.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 内部订单号，如20180626123456
     * 表字段 : t_wx_record_main.trade_no
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 微信订单号，如20180626123456
     * 表字段 : t_wx_record_main.transaction_id
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * 支付总金额
     * 表字段 : t_wx_record_main.total_price
     */
    @Column(name = "total_price")
    private Integer totalPrice;

    /**
     * 退款次数
     * 表字段 : t_wx_record_main.refund_count
     */
    @Column(name = "refund_count")
    private Integer refundCount;

    /**
     * 退款总金额
     * 表字段 : t_wx_record_main.refund_price
     */
    @Column(name = "refund_price")
    private Integer refundPrice;

    /**
     * 实际支付状态 1.统一下单 2.支付完成
     * 表字段 : t_wx_record_main.pay_status
     */
    @Column(name = "pay_status")
    private Integer payStatus;
    /**
     * 创建时间
     * 表字段 : t_wx_record_main.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;


    private List<WxRecordAssist> assistList;

    public String getKey(int gid) {
        StringBuilder sb = new StringBuilder();
        sb.append(getAid()).append(Constant.SIGN_AND).append(getHid()).append(Constant.SIGN_AND);
        sb.append(getOid()).append(Constant.SIGN_AND).append(gid);
        return new String(sb);
    }
}