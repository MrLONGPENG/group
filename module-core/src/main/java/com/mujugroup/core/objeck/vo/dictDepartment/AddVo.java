package com.mujugroup.core.objeck.vo.dictDepartment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "AddVo", description = "木巨科室添加的Vo")
public class AddVo implements Serializable {
    @ApiModelProperty(value = "id", notes = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "name", notes = "木巨科室名称")
    @NotBlank(message = "木巨科室名称不能为空")
    private String name;
    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;

    @ApiModelProperty(value = "createDate", notes = "创建日期")
    private Date createDate;

    @ApiModelProperty(value = "enable", notes = "启用状态")
    private Integer enable;

    @ApiModelProperty(value = "createUserid", notes = "创建者ID")
    private Integer createUserid;

    @ApiModelProperty(value = "updateUserid", notes = "修改者ID")
    private Integer updateUserid;

    @ApiModelProperty(value = "updateTime", notes = "更新时间")
    private Date updateTime;

}
