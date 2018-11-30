package com.mujugroup.wx.objeck.vo.deposit;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "PutVo", description = "更新状态的Vo")
public class PutVo implements Serializable {
    @NotNull(message = "ID不能为空")
    private Long id;
    //用户输入的退款金额
    @NotNull(message = "退款金额不能为空")
    private Integer price;
    //退款描述
    private String refundDesc;
}
