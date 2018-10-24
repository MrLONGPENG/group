package com.mujugroup.core.objeck.vo.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "AgentVo", description = "代理商添加Vo")
public class AgentVo implements Serializable {
    @ApiModelProperty(value = "id", notes = "主键")
    private Integer id;
    @ApiModelProperty(value = "name", notes = "代理商名称",required = true)
    private String name;
    @ApiModelProperty(value = "crtTime", notes = "创建时间")
    private Date crtTime;
    @ApiModelProperty(value = "enable", notes = "代理商状态 1 启用 2 禁用 0 删除")
    private Integer enable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
