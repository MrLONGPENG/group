package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxUptime;


/**
 * @author leolaurel
 */
public interface WxUptimeService {

    WxUptime getDefaultWxUptime();

    WxUptime findListByHospital(Integer hid);

    Long getEndTimeByHid(Integer hid);
}
