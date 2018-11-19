package com.mujugroup.lock.objeck.vo.fail;

import com.lveqia.cloud.common.objeck.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListVo extends PageVo {
    @ApiModelProperty(value = "uid", hidden = true)
    private int uid;
    @ApiModelProperty(value = "type", notes = "异常的类型【异常标识 1:电量异常 2:信号异常 4:开关锁异常】默认查全部为0")
    private int type;
    @ApiModelProperty(value = "status", notes = "异常解决状态【1：产生异常，2：解决中，4：已解决，8：未解决】:默认查未解决的异常(状态为11),如果查询全部异常,请传入0")
    private int status = 11;
    @ApiModelProperty(value = "did", notes = "唯一业务ID")
    private String did;
    @ApiModelProperty(value = "bid", notes = "设备编号")
    private String bid;
}
