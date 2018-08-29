package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * Business Object
 * 订单统计业务对象
 * 组装 订单、医院、支付等信息
 */
public class OrderBO implements Serializable {

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

    @MergeField(feign = ModuleWxService.class, method = "getOrderTypeById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String orderType;

    private String tradeNo;
    private Integer payPrice;
    private Integer payStatus;
    private Long payTime;

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

    public Integer getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }
}
