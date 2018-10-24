package com.mujugroup.core.objeck.vo.department;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ListVo implements Serializable {
    @ApiModelProperty(value = "id", notes = "主键")
    private Integer id;
    @ApiModelProperty(value = "uid", notes = "创建者ID")
    private Integer uid;
    @ApiModelProperty(value = "mid", notes = "木巨科室Id")
    private Integer mid;
    @ApiModelProperty(value = "name", notes = "科室名称")
    private String name;
    @ApiModelProperty(value = "status", notes = "1显示;0删除;")
    private Integer status;
    @ApiModelProperty(value = "hospital", notes = "医院名称")
    private String hospital;
    @ApiModelProperty(value = "department", notes = "木巨科室名称")
    private String department;
    @ApiModelProperty(value = "remark", notes = "科室描述")
    private String remark;
    @ApiModelProperty(value = "sort", notes = "排序")
    private Integer sort;
    @ApiModelProperty(value = "createDate", notes = "创建时间")
    private Date createDate;
    @ApiModelProperty(value = "enable", notes = "启用状态")
    private Integer enable;
    @ApiModelProperty(value = "level", notes = "医院等级")
    private String level;
}
