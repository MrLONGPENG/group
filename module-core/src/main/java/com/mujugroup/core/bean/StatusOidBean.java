package com.mujugroup.core.bean;


import com.github.wxiaoqi.merge.annonation.MergeField;
import com.mujugroup.core.service.feign.ModuleLockService;
import com.mujugroup.core.service.feign.ModuleWxService;

import java.io.Serializable;

public class StatusOidBean implements Serializable {

    private String did;

    private String bid;

    private int oid;

    private int hid;

    private int aid;

    @MergeField(feign = ModuleWxService.class, method = "getPaymentInfo", isValueNeedMerge = true)
    private String paymentInfo;


    @MergeField(feign = ModuleLockService.class, method = "getHardwareInfo", isValueNeedMerge = true)
    private String hardwareInfo;

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

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

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

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getHardwareInfo() {
        return hardwareInfo;
    }

    public void setHardwareInfo(String hardwareInfo) {
        this.hardwareInfo = hardwareInfo;
    }

}

