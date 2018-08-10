package com.mujugroup.wx.service;

import com.mujugroup.wx.bean.QueryBean;
import com.mujugroup.wx.bean.UnlockBean;
import com.mujugroup.wx.bean.UptimeBean;
import com.mujugroup.wx.bean.UsingBean;
import com.mujugroup.wx.exception.TokenException;

public interface UsingApiService {

    UsingBean checkUsing(String sessionThirdKey, String did);

    UnlockBean unlock(String did, String[] arr);

    UptimeBean uptime(String[] arr);

    String generateCode(String openId, String did, String aid, String hid, String oid);

    String[] parseCode(String sessionThirdKey, String code);

    boolean thirdUnlock(String did);

    void notify(String did, Integer lockStatus);

    QueryBean query(String sessionThirdKey, String did, String code, boolean isSync) throws TokenException;
}
