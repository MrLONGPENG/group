package com.mujugroup.wx.objeck.vo;

import java.io.Serializable;
import java.util.List;

public class UptimeVo implements Serializable {
    //休息类型
    private int restType;
    //时间类型
    private int timeType;
    //午间休息时间
    private String noonTime;
    //晚间休息时间
    private String eveningTime;
    //封装科室集合
    private List<UptimeVo> children;

    public List<UptimeVo> getChildren() {
        return children;
    }

    public void setChildren(List<UptimeVo> children) {
        this.children = children;
    }

    public int getRestType() {
        return restType;
    }

    public void setRestType(int restType) {
        this.restType = restType;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public String getNoonTime() {
        return noonTime;
    }

    public void setNoonTime(String noonTime) {
        this.noonTime = noonTime;
    }

    public String getEveningTime() {
        return eveningTime;
    }

    public void setEveningTime(String eveningTime) {
        this.eveningTime = eveningTime;
    }


}
