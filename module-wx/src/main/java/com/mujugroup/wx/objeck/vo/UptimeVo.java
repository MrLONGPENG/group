package com.mujugroup.wx.objeck.vo;

import java.io.Serializable;
import java.util.List;

public class UptimeVo implements Serializable {

    //晚间类型ID;
    private int eveId;
    //午休类型ID
    private int noonId;
    //运行类型 0:默认 1:自定义呢
    private int eveType;
    //午休类型 0:默认 1:自定义呢
    private int noonType;
    //晚间休息时间
    private String eveTime;
    //午间休息时间
    private String noonTime;
    // 晚间使用说明
    private String eveExplain;
    // 午休使用说明
    private String noonExplain;
    // 外键类型
    private int key;
    // 外键ID
    private int kid;
    //名称
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UptimeVo(int kid, int key) {
        this.kid = kid;
        this.key = key;
    }

    public void setEveInfo(int eveId, int eveType, String eveTime, String eveExplain,String name) {
        this.eveId = eveId;
        this.eveType = eveType;
        this.eveTime = eveTime;
        this.eveExplain = eveExplain;
        this.name=name;
    }
    public void setNoonInfo(int noonId, int noonType, String noonTime, String noonExplain,String name) {
        this.noonId = noonId;
        this.noonType = noonType;
        this.noonTime = noonTime;
        this.noonExplain = noonExplain;
        this.name=name;
    }
    //封装科室集合
    private List<UptimeVo> children;

    public List<UptimeVo> getChildren() {
        return children;
    }

    public void setChildren(List<UptimeVo> children) {
        this.children = children;
    }

    public String getNoonTime() {
        return noonTime;
    }

    public void setNoonTime(String noonTime) {
        this.noonTime = noonTime;
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

    public int getEveType() {
        return eveType;
    }

    public void setEveType(int eveType) {
        this.eveType = eveType;
    }

    public int getNoonType() {
        return noonType;
    }

    public void setNoonType(int noonType) {
        this.noonType = noonType;
    }

    public String getEveTime() {
        return eveTime;
    }

    public void setEveTime(String eveTime) {
        this.eveTime = eveTime;
    }

    public String getEveExplain() {
        return eveExplain;
    }

    public void setEveExplain(String eveExplain) {
        this.eveExplain = eveExplain;
    }

    public String getNoonExplain() {
        return noonExplain;
    }

    public void setNoonExplain(String noonExplain) {
        this.noonExplain = noonExplain;
    }

}
