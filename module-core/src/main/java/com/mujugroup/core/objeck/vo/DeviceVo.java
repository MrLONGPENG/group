package com.mujugroup.core.objeck.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "DeviceVo", description = "设备Vo")
public class DeviceVo implements Serializable {
    @ApiModelProperty(value = "did", notes = "二维码业务ID", required = true)
    private String did;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    @ApiModelProperty(value = "agentId", notes = "代理商ID")
    private Integer agentId;


    @ApiModelProperty(value = "hospitalId", notes = "医院ID", required = true)
    private Integer hospitalId;


    @ApiModelProperty(value = "hospitalBed", notes = "医院床位信息")
    private String hospitalBed;


    @ApiModelProperty(value = "crtTime", notes = "创建时间")
    private Date crtTime;
    @ApiModelProperty(value = "crtId", notes = "创建人", hidden = true)
    private Integer crtId;


    @ApiModelProperty(value = "status", notes = "状态 14 启用 15禁用 16 借出 17 删除", required = true)
    private Integer status;


    @Column(name = "useflag")
    @ApiModelProperty(value = "useflag", notes = "使用状态 19 使用 20 闲置")
    private Integer useflag;

    @ApiModelProperty(value = "reserveDate", notes = " 预约时间(只针对预约状态或历史预约过)")
    private Date reserveDate;


    @ApiModelProperty(value = "imgUrl", notes = "图片地址")
    private String imgUrl;


    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;


    @ApiModelProperty(value = "depart", notes = "科室ID", required = true)
    private Integer depart;


    @ApiModelProperty(value = "code", notes = "设备/床唯一编码/邀请码")
    private String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }


    @ApiModelProperty(value = "pay", notes = "是否扫码支付 1 是 0 否")
    private Integer pay;


    @ApiModelProperty(value = "run", notes = "商用", required = true)
    private Integer run;


    @ApiModelProperty(value = "stationId", notes = "护士站Id")
    private Integer stationId;


    @ApiModelProperty(value = "isStation", notes = "是否为护士站 0 否 1 是")
    private Integer isStation;


    @ApiModelProperty(value = "updateId", notes = "修改人ID")
    private Integer updateId;


    @ApiModelProperty(value = "updateTime", notes = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "issync", notes = "是否与子服务器同步，0：已同步，1：未同步")
    private Integer issync;
    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Integer hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalBed() {
        return hospitalBed;
    }

    public void setHospitalBed(String hospitalBed) {
        this.hospitalBed = hospitalBed;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getCrtId() {
        return crtId;
    }

    public void setCrtId(Integer crtId) {
        this.crtId = crtId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUseflag() {
        return useflag;
    }

    public void setUseflag(Integer useflag) {
        this.useflag = useflag;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDepart() {
        return depart;
    }

    public void setDepart(Integer depart) {
        this.depart = depart;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Integer getIsStation() {
        return isStation;
    }

    public void setIsStation(Integer isStation) {
        this.isStation = isStation;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIssync() {
        return issync;
    }

    public void setIssync(Integer issync) {
        this.issync = issync;
    }

}
