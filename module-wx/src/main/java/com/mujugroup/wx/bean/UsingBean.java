package com.mujugroup.wx.bean;

import com.mujugroup.wx.model.WxUptime;

import java.io.Serializable;

public class UsingBean implements Serializable {


    private int type; // 0：未支付，已扫描 1：扫描被他人占用 2：设备使用中 3：设备未激活 4: 未支付，未扫描 5.服务器异常
    private boolean isPay;    // 是否支付
    private boolean mismatch; // 扫描DID与用户已支付的DID不匹配
    private String did;

    private String code;
    private String hospital;
    private String hospitalBed;
    private String department;
    private String address;

    private String info;

    private Long payTime;
    private Long endTime;

    // 医院开锁时间
    private Integer startTime;
    private String startDesc;
    private Integer stopTime;
    private String stopDesc;

    public void setWxUptime(WxUptime wxUptime) {
        setStartTime(wxUptime.getStartTime());
        setStartDesc(wxUptime.getStartDesc());
        setStopTime(wxUptime.getStopTime());
        setStopDesc(wxUptime.getStopDesc());
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public boolean isMismatch() {
        return mismatch;
    }

    public void setMismatch(boolean mismatch) {
        this.mismatch = mismatch;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getInfo() {
        return info;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }


    public void setInfo(String info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getHospitalBed() {
        return hospitalBed;
    }

    public void setHospitalBed(String hospitalBed) {
        this.hospitalBed = hospitalBed;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public String getStartDesc() {
        return startDesc;
    }

    public void setStartDesc(String startDesc) {
        this.startDesc = startDesc;
    }

    public Integer getStopTime() {
        return stopTime;
    }

    public void setStopTime(Integer stopTime) {
        this.stopTime = stopTime;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }
}
