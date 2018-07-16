package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.model.WxUser;
import com.mujugroup.wx.service.WxOpinionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/opinion")
public class WxOpinionController {
    private final Logger logger = LoggerFactory.getLogger(WxOpinionController.class);
    private WxOpinionService wxOpinionService;

    @Autowired
    public WxOpinionController(WxOpinionService wxOpinionService) {
        this.wxOpinionService = wxOpinionService;
    }


    @RequestMapping(value = "/feedback",method = RequestMethod.POST)
    public String feedback(String sessionThirdKey, String content, String did){
        logger.debug("feedback：%s", sessionThirdKey);
        if(sessionThirdKey ==null) ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        if(content == null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        wxOpinionService.feedback(sessionThirdKey, content, did);
        return  ResultUtil.success();
    }
}
