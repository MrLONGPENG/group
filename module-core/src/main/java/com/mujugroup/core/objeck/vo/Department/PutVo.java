package com.mujugroup.core.objeck.vo.Department;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

public class PutVo implements Serializable {

    @ApiModelProperty(value = "id", notes = "主键",required = true)
    private Integer id;
    @ApiModelProperty(value = "status", notes = "1显示;0删除;")
    private Integer status;


    @ApiModelProperty(value = "hospitalId", notes = "医院ID", required = true)
    private Integer hospitalId;


    @ApiModelProperty(value = "name", notes = "科室名称", required = true)
    private String name;

    @ApiModelProperty(value = "aihuiDepartId", notes = "爱汇科室Id")
    private Integer aihuiDepartId;


    @ApiModelProperty(value = "remark", notes = "科室描述")
    private String remark;


    @ApiModelProperty(value = "sort", notes = "排序")
    private Integer sort;

    @ApiModelProperty(value = "createDate", notes = "创建时间")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAihuiDepartId() {
        return aihuiDepartId;
    }

    public void setAihuiDepartId(Integer aihuiDepartId) {
        this.aihuiDepartId = aihuiDepartId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
