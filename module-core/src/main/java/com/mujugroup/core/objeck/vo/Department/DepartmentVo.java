package com.mujugroup.core.objeck.vo.department;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "DepartmentVo", description = "科室添加的Vo")
public class DepartmentVo implements Serializable {

    @ApiModelProperty(value = "id", notes = "主键")
    private Integer id;
    @ApiModelProperty(value = "status", notes = "1显示;0删除;")
    private Integer status;
    @ApiModelProperty(value = "hid", notes = "医院ID", required = true)
    private Integer hid;
    @ApiModelProperty(value = "name", notes = "科室名称", required = true)
    private String name;

    @ApiModelProperty(value = "moid", notes = "木巨科室Id")
    private Integer moid;
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

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public Integer getMoid() {
        return moid;
    }

    public void setMoid(Integer moid) {
        this.moid = moid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
