package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * Business Object
 * 订单统计业务对象
 * 组装 订单、医院、支付等信息
 */
public class OrderBO implements Serializable {

    private String did;

    @MergeField(feign = ModuleCoreService.class, method = "getAgentById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String agent;

    @MergeField(feign = ModuleCoreService.class, method = "getHospitalById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String hospital;

    @MergeField(feign = ModuleCoreService.class, method = "getDepartmentById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String department;

    @MergeField(feign = ModuleCoreService.class, method = "getBedInfoByDid"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String bedInfo;

    private String tradeNo;
    private String payPrice;
    private Integer payStatus;
    private String orderType;
    private String payTime;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBedInfo() {
        return bedInfo;
    }

    public void setBedInfo(String bedInfo) {
        this.bedInfo = bedInfo;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

}
