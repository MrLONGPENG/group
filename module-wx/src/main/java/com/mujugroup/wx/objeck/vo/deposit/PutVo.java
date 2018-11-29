package com.mujugroup.wx.objeck.vo.deposit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "PutVo", description = "更新押金的Vo")
public class PutVo implements Serializable {
    @NonNull
    @ApiModelProperty(value = "id", notes = "押金的唯一编号")
    private Long id;
    @ApiModelProperty(value = "gid", notes = "商品ID")
    private Integer gid;
    @ApiModelProperty(value = "openId", notes = "微信对外唯一ID")
    private String openId;
    @ApiModelProperty(value = "tradeNo", notes = "内部订单号")
    private String tradeNo;
    @ApiModelProperty(value = "deposit", notes = "押金金额")
    private Integer deposit;
    @ApiModelProperty(value = "status", notes = "押金状态 1.已支付  2.退款中 4.审核通过")
    private Integer status;
    @ApiModelProperty(value = "status", notes = "创建时间")
    private Date crtTime;
    @ApiModelProperty(value = "status", notes = "更新时间")
    private Date updTime;
}
