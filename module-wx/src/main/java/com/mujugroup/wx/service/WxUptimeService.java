package com.mujugroup.wx.service;

import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.objeck.vo.uptime.UptimeVo;


/**
 * @author leolaurel
 */
public interface WxUptimeService {

    WxUptime find(int type, String aid, String hid, String oid);

    WxUptime find(int type, int aid, int hid, int oid);

    WxUptime findByXid(int[] ints, int type);

    WxUptime findByXid(int[] ints, int type, boolean isDefault);

    WxUptime query(int type, int key, int kid);

    WxUptime getDefaultWxUptime(int type);

    boolean update(int type, int key, int kid, String startDesc, String stopDesc, String explain) throws ParamException;

    boolean delete(int type, int key, int kid) throws ParamException;

    UptimeVo getWXUptimeVo(int aid, int hid);

    boolean insert(WxUptime wxUptime);

    boolean insertOrModify(String eveTime, String noonTime, int eveType, int noonType, String eveExplain
            , String noonExplain, int eveId, int noonId, int key, int kid) throws ParamException;
}
