package com.mujugroup.lock.objeck.vo.unlock;

import com.lveqia.cloud.common.objeck.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode(callSuper = true)
public class ListVo extends PageVo {

    @ApiModelProperty(value = "did", notes = "业务编号")
    private String did;
    @ApiModelProperty(value = "bid", notes = "锁编号")
    private String bid;
    @ApiModelProperty(value = "startTime", notes = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "endTime", notes = "结束时间")
    private String endTime;
}
