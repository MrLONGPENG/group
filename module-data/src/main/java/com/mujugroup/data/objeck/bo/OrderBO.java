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
    private String aid;

    @MergeField(feign = ModuleCoreService.class, method = "getHospitalById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String hid;

    @MergeField(feign = ModuleCoreService.class, method = "getDepartmentById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String oid;

    @MergeField(feign = ModuleWxService.class, method = "getOrderTypeById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String gid;

    private String tradeNo;

    private Integer payPrice;

    private Long payTime;


    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }
}
