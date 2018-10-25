package com.mujugroup.core.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 设备关联表
 * 类名:device
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@SuppressWarnings("serial")
@Table(name = "t_device")
public class Device implements Serializable {
    public static final int TYPE_ENABLE = 14;
    public static final int TYPE_FORBIDDEN = 15;
    public static final int TYPE_DELETE = 17;

    /**
     * 主键
     * <p>
     * 表字段 : t_device.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * deviceId did
     * 表字段 : t_device.mac
     */
    @Column(name = "did")
    private String did;
    /**
     * 代理商id
     * 表字段 : t_device.agentId
     */
    @Column(name = "agentId")
    private Integer agentId;

    /**
     * 医院id
     * 表字段 : t_device.hospitalId
     */
    @Column(name = "hospitalId")
    private Integer hospitalId;

    /**
     * 医院床位信息
     * 表字段 : t_device.hospitalBed
     */
    @Column(name = "hospitalBed")
    private String hospitalBed;

    /**
     * 创建时间
     * 表字段 : t_device.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 创建人
     * 表字段 : t_device.crtId
     */
    @Column(name = "crtId")
    private Integer crtId;

    /**
     * 状态 14 启用 15禁用 16 借出 17 删除
     * 表字段 : t_device.status
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 使用状态 19 使用 20 闲置
     * 表字段 : t_device.useflag
     */
    @Column(name = "useflag")
    private Integer useflag;
    /**
     * 备注
     * 表字段 : t_device.remark
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 科室（t_department）ID
     * 表字段 : t_device.depart
     */
    @Column(name = "depart")
    private Integer depart;

    /**
     * 设备ID/锁十进制ID
     * 表字段 : t_device.bid
     */
    @Column(name = "bid")
    private String bid;

    /**
     * 是否响铃 1 是 0 否
     * 表字段 : t_device.bell
     */
    @Column(name = "bell")
    private Integer bell;

    /**
     * 商用
     * 表字段 : t_device.run
     */
    @Column(name = "run")
    private Integer run;
    /**
     * 修改人ID
     * 表字段 : t_device.update_id
     */
    @Column(name = "update_id")
    private Integer updateId;

    /**
     * 表字段 : t_device.update_time
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 是否与子服务器同步，0：已同步，1：未同步
     * 表字段 : t_device.issync
     */
    @Column(name = "issync")
    private Integer issync;

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

    public Integer getBell() {
        return bell;
    }

    public void setBell(Integer bell) {
        this.bell = bell;
    }
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

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
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