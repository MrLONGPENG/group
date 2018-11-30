package com.mujugroup.wx.service;

import com.lveqia.cloud.common.exception.TokenException;
import com.mujugroup.wx.bean.QueryBean;
import com.mujugroup.wx.bean.UnlockBean;
import com.mujugroup.wx.bean.UptimeBean;
import com.mujugroup.wx.bean.UsingBean;
import com.mujugroup.wx.model.WxBase;

import java.util.List;

public interface UsingApiService {

    UsingBean checkUsing(String sessionThirdKey, String did);

    UnlockBean unlock(String did, String[] arr);

    UptimeBean uptime(String[] arr);

    String generateCode(String openId, String did, String aid, String hid, String oid);

    String[] parseCode(String sessionThirdKey, String code) throws TokenException;

    boolean thirdUnlock(String did);

    QueryBean query(String sessionThirdKey, String did, String code, boolean isSync) throws TokenException;

    void paymentCompleted(List<WxBase> wxBaseList);
}
