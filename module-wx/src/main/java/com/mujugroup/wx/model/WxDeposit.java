package com.mujugroup.wx.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 押金表
 * 类名:WxDeposit
 * 创建人:leolaurel
 * 创建时间:20181127
 */

@Data
@SuppressWarnings("serial")
@Table(name = "t_wx_deposit")
@EqualsAndHashCode(callSuper = true)
public class WxDeposit extends WxBase {

    /**
     * 主键
     * 
     * 表字段 : t_wx_deposit.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 商品ID
     * 表字段 : t_wx_deposit.gid
     */
    @Column(name = "gid")
    private Integer gid;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_deposit.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 内部订单号，如20180626123456
     * 表字段 : t_wx_deposit.trade_no
     */
    @Column(name = "trade_no")
    private String tradeNo;

    /**
     * 押金金额
     * 表字段 : t_wx_deposit.deposit
     */
    @Column(name = "deposit")
    private Integer deposit;

    /**
     * 押金状态 1.已支付  2.退款中 4.审核通过
     * 表字段 : t_wx_deposit.status
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 创建时间
     * 表字段 : t_wx_deposit.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 更新时间
     * 表字段 : t_wx_deposit.updTime
     */
    @Column(name = "updTime")
    private Date updTime;



}