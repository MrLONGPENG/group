package com.mujugroup.wx.service;

import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.model.WxUptime;

import java.util.concurrent.ExecutionException;


/**
 * @author leolaurel
 */
public interface WxUptimeService {

    WxUptime query(int key, int kid);

    WxUptime findListByHospital(Integer hid);

    WxUptime getDefaultWxUptime();

    Long getEndTimeByKey(String[] keys);

    Long getEndTimeByKey(String key) throws ExecutionException;

    boolean update(int key, int kid, String startDesc, String stopDesc, String explain) throws ParamException;

    boolean delete(int key, int kid) throws ParamException;


}
