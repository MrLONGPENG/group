package com.mujugroup.wx.service;


import com.mujugroup.wx.model.WxUser;

public interface WxUserService {
    String getWeChatSession(String appId, String secret, String code);

    WxUser onQuery(String sessionThirdKey);

    void onUpdate(String sessionThirdKey, String phone, String nickName, String gender, String language, String country, String province, String city, String avatarUrl);
}
