package com.mujugroup.wx.model;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 扣费记录表
 * 类名:WxDeductionRecord
 * 创建人:leolaurel
 * 创建时间:20181203
 */
@Data
@SuppressWarnings("serial")
@Table(name = "t_wx_deduction_record")
public class WxDeductionRecord implements Serializable {
    public static final int TIME_OUT_DEDUCTION = 1;//超时扣费
    /**
     * 主键
     * <p>
     * 表字段 : t_wx_deduction_record.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_deduction_record.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 内部订单号，如20180626123456
     * 表字段 : t_wx_deduction_record.trade_no
     */
    @Column(name = "trade_no")
    private String tradeNo;


    /**
     * 业务ID
     * 表字段 : t_wx_deduction_record.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 扣费原因
     * 表字段 : t_wx_deduction_record.explain
     */
    @Column(name = "explain")
    private String explain;

    /**
     * 扣费记录产生日期
     * 表字段 : t_wx_deduction_record.day
     */
    @Column(name = "day")
    private String day;

    /**
     * 扣费金额
     * 表字段 : t_wx_deduction_record.forfeit
     */
    @Column(name = "forfeit")
    private Integer forfeit;

    /**
     * 超时时长
     * 表字段 : t_wx_deduction_record.timeout
     */
    @Column(name = "timeout")
    private Integer timeout;

    /**
     * 扣费类型 1:超时扣费
     * 表字段 : t_wx_deduction_record.type
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 创建时间
     * 表字段 : t_wx_deduction_record.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;


}