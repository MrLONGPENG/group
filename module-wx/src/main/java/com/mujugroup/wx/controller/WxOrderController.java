package com.mujugroup.wx.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.DBMap;
import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.WxOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/order")
@Api(description="微信订单信息接口")
public class WxOrderController {

    private final Logger logger = LoggerFactory.getLogger(WxOrderController.class);
    private WxOrderService wxOrderService;

    @Autowired
    public WxOrderController(WxOrderService wxOrderService) {
        this.wxOrderService = wxOrderService;
    }


    @ApiOperation(value="获取微信支付订单信息", notes="根据Token获取信息")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST })
    public String list(@ApiParam(value="访问Token") @RequestParam(name="sessionThirdKey")String sessionThirdKey
            , @ApiParam(value="当前页")@RequestParam(name="pageNum", required=false, defaultValue="1")int pageNum
            , @ApiParam(value="每页显示")@RequestParam(name="pageSize", required=false, defaultValue="10")int pageSize){
        logger.debug("order-list:{}", sessionThirdKey);
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

    @ApiOperation(value="获取订单信息详细信息", notes="根据Token与订单号获取详细信息")
    @RequestMapping(value = "/details", method = {RequestMethod.GET, RequestMethod.POST })
    public String details(String sessionThirdKey, String tradeNo){
        logger.debug("order-details:{}", tradeNo);
        OrderBean details = wxOrderService.details(sessionThirdKey, tradeNo);
        if(details!=null){
            return ResultUtil.success(details);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
