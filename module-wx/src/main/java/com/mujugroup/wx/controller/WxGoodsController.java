package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.service.WxGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/goods")
public class WxGoodsController {
    private final Logger logger = LoggerFactory.getLogger(WxGoodsController.class);
    private WxGoodsService wxGoodsService;

    @Autowired
    public WxGoodsController(WxGoodsService wxGoodsService) {
        this.wxGoodsService = wxGoodsService;
    }

    @RequestMapping(value = "/list")
    public String list(String sessionThirdKey, String hid){
        logger.debug("goods-list:{} hospitalId:{}", sessionThirdKey, hid);
        List<WxGoods> list = wxGoodsService.findListByHospital(hid);
        if(list!=null){
            return ResultUtil.success(list);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
