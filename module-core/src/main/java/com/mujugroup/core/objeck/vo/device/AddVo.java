package com.mujugroup.core.objeck.vo.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "AddVo", description = "设备Vo")
public class AddVo implements Serializable {

    @NotBlank(message = "DID编号不能为空")
    @Length(min = 9, max= 9, message = "DID编号长度必须为9位")
    @Digits(integer = 9 , fraction = 0, message = "DID编号必须全部为数字")
    @ApiModelProperty(value = "did", notes = "二维码业务ID", required = true)
    private String did;

    @NotBlank(message = "BID编号不能为空")
    @Length(min = 19, max= 19, message = "BID编号长度必须为19位")
    @Digits(integer = 19 , fraction = 0, message = "BID编号必须全部为数字")
    @ApiModelProperty(value = "bid", notes = "设备ID/锁十进制ID", required = true)
    private String bid;

    @ApiModelProperty(value = "aid", notes = "代理商ID")
    private Integer aid;

    @NotNull(message = "请选择医院")
    @ApiModelProperty(value = "hid", notes = "医院ID", required = true)
    private Integer hid;

    @NotNull(message = "请选择科室")
    @ApiModelProperty(value = "oid", notes = "科室ID", required = true)
    private Integer oid;

    @NotNull(message = "床位信息不能为空")
    @ApiModelProperty(value = "bed", notes = "医院床位信息", required = true)
    private String bed;

    @ApiModelProperty(value = "uid", notes = "创建人", hidden = true)
    private String uid;

    @ApiModelProperty(value = "status", notes = "状态 14 启用 15禁用 16 借出 17 删除", required = true)
    private Integer status;

    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;

    @ApiModelProperty(value = "bell", notes = "是否响铃 1 是 0 否")
    private Integer bell;

    @ApiModelProperty(value = "run", notes = "商用")
    private Integer run;

}
