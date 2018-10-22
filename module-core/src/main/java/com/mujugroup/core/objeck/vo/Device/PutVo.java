package com.mujugroup.core.objeck.vo.Device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "PutVo", description = "设备编辑的VO")
public class PutVo implements Serializable {
    @ApiModelProperty(value = "id",notes = "设备编号",required = true)
    private Integer id;
    @ApiModelProperty(value = "did", notes = "二维码业务ID")
    private String did;

    @ApiModelProperty(value = "bid", notes = "设备ID/锁十进制ID")
    private String bid;

    @ApiModelProperty(value = "hid", notes = "医院ID", required = true)
    private Integer hid;

    @ApiModelProperty(value = "oid", notes = "科室ID", required = true)
    private Integer oid;

    @ApiModelProperty(value = "bed", notes = "医院床位信息", required = true)
    private String bed;

    @ApiModelProperty(value = "uid", notes = "创建人")
    private String uid;

    @ApiModelProperty(value = "status", notes = "状态 14 启用 15禁用 16 借出 17 删除", required = true)
    private Integer status;

    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;

    @ApiModelProperty(value = "pay", notes = "是否扫码支付 1 是 0 否",required = true)
    private Integer pay;

    @ApiModelProperty(value = "run", notes = "商用",required = true)
    private Integer run;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
