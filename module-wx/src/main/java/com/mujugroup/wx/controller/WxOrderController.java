package com.mujugroup.wx.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.WxOrderService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/order")
public class WxOrderController {

    private final Logger logger = LoggerFactory.getLogger(WxOrderController.class);
    private WxOrderService wxOrderService;

    @Autowired
    public WxOrderController(WxOrderService wxOrderService) {
        this.wxOrderService = wxOrderService;
    }


    @RequestMapping(value = "/list")
    public String list(@RequestParam(name="sessionThirdKey")String sessionThirdKey
            , @RequestParam(name="pageNum", required=false, defaultValue="1")int pageNum
            , @RequestParam(name="pageSize", required=false, defaultValue="10")int pageSize){
        logger.info("order-list:"+sessionThirdKey);
        PageHelper.startPage(pageNum, pageSize);
        List<WxOrder> list = wxOrderService.listSelfOrder(sessionThirdKey);

        if(list!=null){
            PageInfo pageInfo =  PageInfo.of(list);
            List<OrderBean> orders = new ArrayList<>();
            for (WxOrder wxOrder:list){
                orders.add(new OrderBean(wxOrder));
            }
            return ResultUtil.success(orders, pageInfo);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

    @RequestMapping(value = "/details")
    public String details(String sessionThirdKey, String tradeNo){
        logger.info("order-details:"+tradeNo);
        OrderBean details = wxOrderService.details(sessionThirdKey, tradeNo);
        if(details!=null){
            return ResultUtil.success(details);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }
}
