package com.mujugroup.wx.bean;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.wx.model.WxOrder;

/**
 * 订单详情返回数据
 */
public class OrderBean {

    private String did;
    private String tradeNo;
    private String transactionId;
    private String payDateTime;
    private Integer payPrice;
    private Long payTime;
    private Long endTime;

    private String hospital;
    private String hospitalBed;
    private String department;
    private String address;

    public OrderBean(WxOrder wxOrder) {
        this.did = StringUtil.autoFillDid(wxOrder.getDid());
        this.tradeNo = wxOrder.getTradeNo();
        this.transactionId = wxOrder.getTransactionId();
        this.payDateTime = tradeNo.substring(0, 12);
        this.payPrice = wxOrder.getPayPrice();
        this.payTime = wxOrder.getPayTime();
        this.endTime = wxOrder.getEndTime();
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayDateTime() {
        return payDateTime;
    }

    public void setPayDateTime(String payDateTime) {
        this.payDateTime = payDateTime;
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

    public Integer getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(Integer payPrice) {
        this.payPrice = payPrice;
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
}
