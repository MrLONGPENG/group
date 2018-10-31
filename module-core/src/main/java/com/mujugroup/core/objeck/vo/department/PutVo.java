package com.mujugroup.core.objeck.vo.department;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class PutVo implements Serializable {
    @NotNull(message = "更新必须指定ID")
    @ApiModelProperty(value = "id", notes = "主键", required = true)
    private Integer id;
    @ApiModelProperty(value = "status", notes = "1显示;0删除;")
    private Integer status;
    @ApiModelProperty(value = "hid", notes = "医院ID", required = true)
    private Integer hid;
    @ApiModelProperty(value = "name", notes = "科室名称", required = true)
    private String name;
    @ApiModelProperty(value = "mid", notes = "木巨科室Id")
    private Integer mid;
    @ApiModelProperty(value = "remark", notes = "科室描述")
    private String remark;
    @ApiModelProperty(value = "sort", notes = "排序")
    private Integer sort;
    @ApiModelProperty(value = "createDate", notes = "创建时间")
    private Date createDate;

}
