package com.mujugroup.wx.bean;

import com.mujugroup.wx.model.WxGoods;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UnlockBean implements Serializable {

    private Integer payState; // 1:未支付 2:已支付 3:已支付但被占用 4 开锁异常
    // 未支付
    private List<WxGoods> goods;
    // 已支付
    private String payDate;
    private String info;

    public List<WxGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<WxGoods> goods) {
        this.goods = goods;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
