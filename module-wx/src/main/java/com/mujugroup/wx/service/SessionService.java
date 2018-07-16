package com.mujugroup.wx.service;

import com.mujugroup.wx.bean.WeChatSession;

public interface SessionService {

    String getSessionThirdKey(WeChatSession session);

    String getDecode(String sessionThirdKey);

    String getOpenId(String sessionThirdKey);

    String getSessionKey(String sessionThirdKey);
}
