package com.mujugroup.wx.objeck.vo.deduction;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "InfoVo", description = "超时扣费的Vo")
@Data
public class ListVo implements Serializable {

    private Long id;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_deduction_record.open_id
     */
    private String openId;

    /**
     * 内部订单号，如20180626123456
     * 表字段 : t_wx_deduction_record.trade_no
     */
    private String tradeNo;


    /**
     * 业务ID
     * 表字段 : t_wx_deduction_record.did
     */
    private Long did;

    /**
     * 扣费原因
     * 表字段 : t_wx_deduction_record.explain
     */
    private String explain;

    /**
     * 扣费记录产生日期
     * 表字段 : t_wx_deduction_record.day
     */

    private Date day;

    /**
     * 扣费金额
     * 表字段 : t_wx_deduction_record.forfeit
     */

    private Double forfeit;

    /**
     * 超时时长
     * 表字段 : t_wx_deduction_record.timeout
     */

    private Integer timeout;

    /**
     * 扣费类型 1:超时扣费
     * 表字段 : t_wx_deduction_record.type
     */

    private Integer type;

    /**
     * 创建时间
     * 表字段 : t_wx_deduction_record.crtTime
     */

    private Date crtTime;
}
