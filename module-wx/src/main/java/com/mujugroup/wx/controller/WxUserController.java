package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.config.MyConfig;
import com.mujugroup.wx.model.WxUser;
import com.mujugroup.wx.service.WxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class WxUserController {
    private final Logger logger = LoggerFactory.getLogger(WxUserController.class);
    private final MyConfig myConfig = new MyConfig();
    private final WxUserService wxUserService;

    @Autowired
    public WxUserController(WxUserService wxUserService) {
        this.wxUserService = wxUserService;
    }

    @RequestMapping(value = "/onLogin",method = RequestMethod.POST)
    public String onLogin(@RequestParam("code") String code){
        logger.debug("onLogin: %s", code);
        String sessionThirdKey = wxUserService.getWeChatSession(myConfig.getAppID(), myConfig.getSecret(),code);
        if(sessionThirdKey !=null){
            logger.info("sessionThirdKey："+ sessionThirdKey);
            return ResultUtil.success(sessionThirdKey);
        }
        return  ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public String onQuery(String sessionThirdKey){
        logger.debug("onQuery：%s", sessionThirdKey);
        if(sessionThirdKey ==null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        WxUser wxUser = wxUserService.onQuery(sessionThirdKey);
        if(wxUser!=null) {
            wxUser.setId(null);wxUser.setOpenId(null);
            wxUser.setUnionId(null);wxUser.setSessionKey(null);
            return ResultUtil.success(wxUser);
        }
        return  ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public String onUpdate(String sessionThirdKey, String phone, String nickName, String gender
            , String language, String country, String province, String city, String avatarUrl
            , String encryptedData, String iv){
        if(sessionThirdKey ==null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        WxUser wxUser;
        if(encryptedData!=null && iv!=null){
            wxUser = wxUserService.onUpdate(sessionThirdKey, encryptedData, iv);
        }else{
            wxUser = wxUserService.onUpdate(sessionThirdKey, phone, nickName, gender
                    , language, country, province, city, avatarUrl);
        }
        return  ResultUtil.success(wxUser);
    }



}
