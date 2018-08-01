package com.mujugroup.core.bean;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.mujugroup.core.service.HospitalService;
import com.mujugroup.core.service.feign.ModuleWxService;

import java.io.Serializable;

public class StatusAidBean implements Serializable {

    private int hid;

    private int aid;

    private int actCount;

    @MergeField(value = "未知", feign = HospitalService.class, method = "getHidMapByAid", isQueryByParam = true)
    private String hospital;

    @MergeField(value = "0", feign = ModuleWxService.class, method = "getPayCount", isQueryByParam = true)
    private String payCount;

    public int getHid() {
        return hid;
    }

    public void setHid(int hid) {
        this.hid = hid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getActCount() {
        return actCount;
    }

    public void setActCount(int actCount) {
        this.actCount = actCount;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getPayCount() {
        return payCount;
    }

    public void setPayCount(String payCount) {
        this.payCount = payCount;
    }
}

