package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxUsing;

public interface WxUsingService {

    WxUsing insert(WxUsing wxUsing);

    WxUsing update(WxUsing wxUsing);

    WxUsing findUsingByOpenId(String openId, long time);

    WxUsing findUsingByDid(String did, long time, boolean isSync);

    WxUsing findUsingByBid(String bid, long time);

    boolean deleteByDid(String did, long time);
}
