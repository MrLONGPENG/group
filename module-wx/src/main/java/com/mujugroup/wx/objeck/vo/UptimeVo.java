package com.mujugroup.wx.objeck.vo;

import java.io.Serializable;
import java.util.List;

public class UptimeVo implements Serializable {
    private int kid;
    //午休类型ID
    private int noonId;
    //运行类型ID;
    private int eveId;
    //休息类型
    private int restType;
    //时间类型
    private int timeType;
    //午间休息时间
    private String noonTime;
    //晚间休息时间
    private String eveningTime;
    //使用说明
    private String explain;
    //外键类型
    private int key;
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

    public int getNoonId() {
        return noonId;
    }

    public void setNoonId(int noonId) {
        this.noonId = noonId;
    }

    public int getEveId() {
        return eveId;
    }

    public void setEveId(int eveId) {
        this.eveId = eveId;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }
}
