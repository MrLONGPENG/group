package com.mujugroup.wx.bean;

import com.mujugroup.wx.model.WxUptime;

public class UptimeBean {

    // 医院开锁时间
    private Integer startTime;
    private String startDesc;
    private Integer stopTime;
    private String stopDesc;
    // 医院午休时间
    private Integer middayStartTime;
    private Integer middayStopTime;
    private String middayStartDesc;
    private String middayStopDesc;


    public UptimeBean(WxUptime uptime, WxUptime midday) {
        setStartTime(uptime.getStartTime());
        setStartDesc(uptime.getStartDesc());
        setStopTime(uptime.getStopTime());
        setStopDesc(uptime.getStopDesc());
        setMiddayStartTime(midday.getStartTime());
        setMiddayStartDesc(midday.getStartDesc());
        setMiddayStopTime(midday.getStopTime());
        setMiddayStopDesc(midday.getStopDesc());
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public String getStartDesc() {
        return startDesc;
    }

    public void setStartDesc(String startDesc) {
        this.startDesc = startDesc;
    }

    public Integer getStopTime() {
        return stopTime;
    }

    public void setStopTime(Integer stopTime) {
        this.stopTime = stopTime;
    }

    public String getStopDesc() {
        return stopDesc;
    }

    public void setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }

    public Integer getMiddayStartTime() {
        return middayStartTime;
    }

    public void setMiddayStartTime(Integer middayStartTime) {
        this.middayStartTime = middayStartTime;
    }

    public Integer getMiddayStopTime() {
        return middayStopTime;
    }

    public void setMiddayStopTime(Integer middayStopTime) {
        this.middayStopTime = middayStopTime;
    }

    public String getMiddayStartDesc() {
        return middayStartDesc;
    }

    public void setMiddayStartDesc(String middayStartDesc) {
        this.middayStartDesc = middayStartDesc;
    }

    public String getMiddayStopDesc() {
        return middayStopDesc;
    }

    public void setMiddayStopDesc(String middayStopDesc) {
        this.middayStopDesc = middayStopDesc;
    }
}
