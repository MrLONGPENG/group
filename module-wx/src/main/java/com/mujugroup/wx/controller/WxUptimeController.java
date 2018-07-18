package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxUptimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/uptime")
public class WxUptimeController {
    private final Logger logger = LoggerFactory.getLogger(WxUptimeController.class);
    private WxUptimeService wxUptimeService;

    @Autowired
    public WxUptimeController(WxUptimeService wxUptimeService) {
        this.wxUptimeService = wxUptimeService;
    }

    @RequestMapping(value = "/find")
    public String find(String sessionThirdKey, String hid){
        logger.debug("uptime-list:{} hospitalId:{}", sessionThirdKey, hid);
        WxUptime wxUptime;
        if(StringUtil.isEmpty(hid)){
            wxUptime = wxUptimeService.getDefaultWxUptime();
        }else{
            wxUptime = wxUptimeService.findListByHospital(Integer.parseInt(hid));
        }
        if(wxUptime != null){
            return ResultUtil.success(wxUptime);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
