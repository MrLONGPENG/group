package com.mujugroup.wx.objeck.vo.deposit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "PutVo", description = "更新状态的Vo")
public class PutVo implements Serializable {
    @NotNull(message = "id不能为空")
    private Long id;
    //用户输入的退款金额
    private int price;
    //退款描述
    private String refundDesc;
}
