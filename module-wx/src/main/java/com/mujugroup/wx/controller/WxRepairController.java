package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.wx.service.WxRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/repair")
public class WxRepairController {

    private WxRepairService wxRepairService;

    @Autowired
    public WxRepairController(WxRepairService wxRepairService) {
        this.wxRepairService = wxRepairService;
    }

    @RequestMapping(value = "/report")
    public String report(String sessionThirdKey, String did, String cause, String describe, String images){
        if(wxRepairService.report(sessionThirdKey, did, cause, describe, images)){
            return ResultUtil.success();
        }
        return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
    }
}
