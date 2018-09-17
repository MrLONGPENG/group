package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.util.AESUtil;
import com.lveqia.cloud.common.util.DateUtil;
import com.mujugroup.wx.bean.WeChatSession;
import com.mujugroup.wx.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("sessionService")
public class SessionServiceImpl implements SessionService {
    private final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);
    private final static String DEFAULT_KEY = "dwAfYLeWpfKObqDI_0ZYKlZD7-vFmf6u038EhZafJMQ"; //????test#W#X#test

    @Override
    public String getSessionThirdKey(WeChatSession session) {
        try{
            if(session ==null) return DEFAULT_KEY;
            String source = DateUtil.dateToString(DateUtil.TYPE_DAY_HOUR) + session.getOpenid()
                    + AESUtil.SPLIT + session.getSession_key();
            return AESUtil.aesEncrypt(source, AESUtil.PASS);
        }catch (Exception e){
            logger.warn("wx-session-getSessionThirdKey-error");
        }
        return DEFAULT_KEY;
    }

    @Override
    public String getDecode(String sessionThirdKey) {
        try{
            return Objects.requireNonNull(AESUtil.aesDecrypt(sessionThirdKey));
        }catch (Exception e){
            logger.warn("wx-session-getDecode-error");
        }
        return "????test#W#X#test";
    }

    @Override
    public String getOpenId(String sessionThirdKey) {
        try{
            return Objects.requireNonNull(AESUtil.aesDecrypt(sessionThirdKey)).split(AESUtil.SPLIT)[0].substring(4);
        }catch (Exception e){
            logger.warn("wx-session-getOpenId-error");
        }
        return "test";
    }

    @Override
    public String getSessionKey(String sessionThirdKey) {
        try{
            return Objects.requireNonNull(AESUtil.aesDecrypt(sessionThirdKey)).split(AESUtil.SPLIT)[1];
        }catch (Exception e){
            logger.warn("wx-session-getSessionKey-error");
        }
        return "test";
    }


}
