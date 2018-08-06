package com.mujugroup.wx.service;

import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.model.WxUptime;

import java.util.concurrent.ExecutionException;


/**
 * @author leolaurel
 */
public interface WxUptimeService {

    WxUptime query(int type, int key, int kid);

    WxUptime findListByHospital(int hid);

    WxUptime findListByHospital(int type, int hid);

    WxUptime getDefaultWxUptime(int type);

    boolean update(int type, int key, int kid, String startDesc, String stopDesc, String explain) throws ParamException;

    boolean delete(int type, int key, int kid) throws ParamException;


}
