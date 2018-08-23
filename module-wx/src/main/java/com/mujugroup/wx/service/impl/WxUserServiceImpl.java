package com.mujugroup.wx.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lveqia.cloud.common.AESUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.wx.bean.WeChatSession;
import com.mujugroup.wx.mapper.WxUserMapper;
import com.mujugroup.wx.model.WxUser;
import com.mujugroup.wx.service.SessionService;
import com.mujugroup.wx.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.Cacheable;

import java.util.Date;

@Service("wxUserService")
public class WxUserServiceImpl implements WxUserService {
    private final Logger logger = LoggerFactory.getLogger(WxUserServiceImpl.class);
    private final Gson gson = new Gson();
    private final WxUserMapper wxUserMapper;
    private final RestTemplate restTemplate;
    private final SessionService sessionService;

    @Autowired
    public WxUserServiceImpl(WxUserMapper wxUserMapper, RestTemplate restTemplate, SessionService sessionService) {
        this.wxUserMapper = wxUserMapper;
        this.restTemplate = restTemplate;
        this.sessionService = sessionService;
    }

    @Override
    public String getWeChatSession(String appId, String secret, String code) {
        ResponseEntity<String> response  = restTemplate.exchange("https://api.weixin.qq.com/sns/jscode2session"
                , HttpMethod.POST, getTokenEntity(appId,secret,code), String.class);
        if(response.getStatusCode() == HttpStatus.OK){
            String sessionData = response.getBody();
            try{
                WeChatSession weChatSession = gson.fromJson(sessionData,WeChatSession.class);
                if(weChatSession.getOpenid()!=null){
                    WxUser wxUser = wxUserMapper.findByOpenId(weChatSession.getOpenid());
                    if(wxUser != null) {
                        wxUser.setSessionKey(weChatSession.getSession_key());
                        wxUser.setUpdateTime(new Date());
                        wxUserMapper.update(wxUser);
                    }else{
                        wxUser = new WxUser();
                        wxUser.setOpenId(weChatSession.getOpenid());
                        wxUser.setSessionKey(weChatSession.getSession_key());
                        wxUserMapper.insert(wxUser);
                    }
                    return sessionService.getSessionThirdKey(weChatSession);
                }
            }catch (Exception e){
                logger.warn("wx-user-getWeChatSession");
            }
        }
        return sessionService.getSessionThirdKey(null);
    }

    @Override
    public WxUser onQuery(String sessionThirdKey) {
        return wxUserMapper.findByOpenId(sessionService.getOpenId(sessionThirdKey));
    }


    @Override
    public WxUser onUpdate(String sessionThirdKey, String phone, String nickName, String gender
            , String language, String country, String province, String city, String avatarUrl) {
        WxUser wxUser = wxUserMapper.findByOpenId(sessionService.getOpenId(sessionThirdKey));
        if(wxUser!=null){
            if(!StringUtil.isEmpty(phone)) wxUser.setPhone(phone);
            if(!StringUtil.isEmpty(nickName)) wxUser.setNickName(nickName);
            if(!StringUtil.isEmpty(gender)) wxUser.setGender(Integer.parseInt(gender));
            if(!StringUtil.isEmpty(language)) wxUser.setLanguage(language);
            if(!StringUtil.isEmpty(country)) wxUser.setCountry(country);
            if(!StringUtil.isEmpty(province)) wxUser.setProvince(province);
            if(!StringUtil.isEmpty(city)) wxUser.setCity(city);
            if(!StringUtil.isEmpty(avatarUrl)) wxUser.setAvatarUrl(avatarUrl);
            wxUser.setUpdateTime(new Date());
            wxUserMapper.update(wxUser);
        }
        return wxUser;
    }

    @Override
    @Cacheable(value = "wx-user-total-count")
    public String getTotalUserCount(String start, String end) {
        logger.debug("getTotalUserCount real-time data");
        return wxUserMapper.getTotalUserCount(start, end);
    }

    @Override
    public WxUser onUpdate(String sessionThirdKey, String encryptedDate, String iv) {
        WxUser wxUser = null;
        try {
            wxUser = wxUserMapper.findByOpenId(sessionService.getOpenId(sessionThirdKey));
            String data = AESUtil.wxDecrypt(encryptedDate, sessionService.getSessionKey(sessionThirdKey), iv);
            if(data!=null && wxUser!=null){
                JsonObject json = new JsonParser().parse(data).getAsJsonObject();
                if(json.has("phoneNumber")  ){ // 国内手机无区号
                    wxUser.setPhone(json.get("phoneNumber").getAsString());
                }else if(json.has("purePhoneNumber")){
                    wxUser.setPhone(json.get("purePhoneNumber").getAsString());
                }
                if(json.has("nickName")) wxUser.setNickName(json.get("nickName").getAsString());
                if(json.has("gender")) wxUser.setGender(json.get("gender").getAsInt());
                if(json.has("language")) wxUser.setLanguage(json.get("language").getAsString());
                if(json.has("country")) wxUser.setCountry(json.get("country").getAsString());
                if(json.has("province")) wxUser.setProvince(json.get("province").getAsString());
                if(json.has("city")) wxUser.setCity(json.get("city").getAsString());
                if(json.has("avatarUrl")) wxUser.setAvatarUrl(json.get("avatarUrl").getAsString());
                if(json.has("unionId")) wxUser.setUnionId(json.get("unionId").getAsString());
                wxUser.setUpdateTime(new Date());
                wxUserMapper.update(wxUser);
            }
        } catch (Exception e) {
            logger.warn("解码微信用户数据更新失败", e);
        }
        return wxUser;
    }


    /**
     * 获取微信凭证拼接参数
     * @return 登录表单实体
     */
    private HttpEntity<?> getTokenEntity(String appId, String secret, String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("appid", appId);
        params.add("secret", secret);
        params.add("js_code", code);
        params.add("grant_type", "authorization_code");

        return new HttpEntity<>(params, headers);
    }
}
