package com.mujugroup.wx.service;

import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.model.WxUptime;


/**
 * @author leolaurel
 */
public interface WxUptimeService {

    WxUptime find(int type, String aid, String hid, String oid);

    WxUptime find(int type, int aid, int hid, int oid);

    WxUptime findByXid(int[] ints, int type);

    WxUptime query(int type, int key, int kid);

    WxUptime getDefaultWxUptime(int type);

    boolean update(int type, int key, int kid, String startDesc, String stopDesc, String explain) throws ParamException;

    boolean delete(int type, int key, int kid) throws ParamException;



}
