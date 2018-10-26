package com.mujugroup.core.objeck.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@ApiModel(value = "PutVo", description = "设备编辑的VO")
public class PutVo implements Serializable {
    @NotNull(message = "设备更新必须指定ID")
    @ApiModelProperty(value = "id", notes = "设备编号", required = true)
    private Integer id;

    @ApiModelProperty(value = "aid", notes = "代理商ID")
    private Integer aid;

    @ApiModelProperty(value = "hid", notes = "医院ID")
    private Integer hid;

    @ApiModelProperty(value = "oid", notes = "科室ID")
    private Integer oid;

    @ApiModelProperty(value = "bed", notes = "医院床位信息")
    private String bed;

    @Range(min = 14, max = 17, message = "状态可选[14,15,16,17]")
    @ApiModelProperty(value = "status", notes = "状态 14 启用 15禁用 16 借出 17 删除")
    private Integer status;

    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;

    @Range(max = 1, message = "响铃状态可选[0,1]")
    @ApiModelProperty(value = "bell", notes = "是否响铃 1 是 0 否")
    private Integer bell;

    @ApiModelProperty(value = "run", notes = "商用")
    private Integer run;
}
