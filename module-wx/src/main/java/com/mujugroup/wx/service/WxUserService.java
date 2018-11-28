package com.mujugroup.wx.service;


import com.mujugroup.wx.model.WxUser;
import com.mujugroup.wx.objeck.vo.user.UserVo;

public interface WxUserService {
    String getWeChatSession(String appId, String secret, String code);

    UserVo onQuery(String sessionThirdKey);

    WxUser onUpdate(String sessionThirdKey, String encryptedDate, String iv);

    WxUser onUpdate(String sessionThirdKey, String phone, String nickName, String gender, String language, String country, String province, String city, String avatarUrl);

    String getTotalUserCount(String start, String end);
}
