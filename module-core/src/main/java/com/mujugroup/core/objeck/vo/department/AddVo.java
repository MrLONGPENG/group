package com.mujugroup.core.objeck.vo.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "AddVo", description = "科室添加的Vo")
public class AddVo implements Serializable {
    @NotNull(message = "请选择所属医院")
    @ApiModelProperty(value = "hid", notes = "医院ID", required = true)
    private Integer hid;
    @ApiModelProperty(value = "mid", notes = "木巨科室Id")
    private Integer mid;
    @NotBlank(message = "科室名称不能为空")
    @ApiModelProperty(value = "name", notes = "科室名称", required = true)
    private String name;
    @ApiModelProperty(value = "status", notes = "1显示;0删除;")
    private Integer status;
    @ApiModelProperty(value = "remark", notes = "科室描述")
    private String remark;
    @ApiModelProperty(value = "sort", notes = "排序")
    private Integer sort;
    @ApiModelProperty(value = "createDate", notes = "创建时间")
    private Date createDate;

}
