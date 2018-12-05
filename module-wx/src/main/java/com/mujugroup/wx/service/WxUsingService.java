package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxUsing;

public interface WxUsingService {

    WxUsing insert(WxUsing wxUsing);

    WxUsing update(WxUsing wxUsing);

    WxUsing updateUsingStatus(WxUsing wxUsing, String status);

    WxUsing findUsingByOpenId(String openId, long time);

    WxUsing findUsingByDid(String did, long time, boolean isSync);

    boolean deleteByDid(String did, long time);

    int getCountByUsingDid(String did, long time);

    WxUsing getWxUsingByDidAndPayTime(String openId, String did, long payTime);
}
