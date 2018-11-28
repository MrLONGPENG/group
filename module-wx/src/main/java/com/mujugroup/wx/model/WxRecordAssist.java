package com.mujugroup.wx.model;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 支付记录辅表
 * 类名:WxRecordAssist
 * 创建人:leolaurel
 * 创建时间:20181127
 */
@Data
@SuppressWarnings("serial")
@Table(name = "t_wx_record_assist")
public class WxRecordAssist implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_wx_record_assist.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 支付主表ID
     * 表字段 : t_wx_record_assist.mid
     */
    @Column(name = "mid")
    private Long mid;

    /**
     * 商品ID
     * 表字段 : t_wx_record_assist.gid
     */
    @Column(name = "gid")
    private Integer gid;

    /**
     * 金额
     * 表字段 : t_wx_record_assist.price
     */
    @Column(name = "price")
    private Integer price;

    /**
     * 创建时间
     * 表字段 : t_wx_record_assist.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 类型：1:押金 2:套餐 3:午休 4:被子
     * 表字段 : t_wx_record_assist.type
     */
    @Column(name = "type")
    private Integer type;


    private String name;

}