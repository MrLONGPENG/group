package com.mujugroup.data.controller;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.to.AidHidOidTO;
import com.lveqia.cloud.common.to.OrderTO;
import com.lveqia.cloud.common.to.PageTO;
import com.mujugroup.data.objeck.bo.OrderBO;
import com.mujugroup.data.service.OrderService;
import com.mujugroup.data.service.feign.ModuleWxService;
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

import java.util.List;


@RestController
@RequestMapping("/order")
@Api(description="数据模块订单统计接口")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;
    private final ModuleWxService moduleWxService;
    @Autowired
    public OrderController(OrderService orderService, ModuleWxService moduleWxService) {
        this.orderService = orderService;
        this.moduleWxService = moduleWxService;
    }

    @ApiOperation(value="查询全部订单记录", notes="根据条件查询(代理商、医院、起止时间，以及单个订单查询)")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String info(@ApiParam(value="代理商ID", required = true) @RequestParam(name="aid") int aid
            , @ApiParam(value="医院ID") @RequestParam(name="hid", required=false, defaultValue="0") int hid
            , @ApiParam(value="科室ID") @RequestParam(name="oid", required=false, defaultValue="0") int oid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="当前页")@RequestParam(name="pageNum", required=false, defaultValue="1")int pageNum
            , @ApiParam(value="每页显示")@RequestParam(name="pageSize", required=false, defaultValue="10")int pageSize){
        logger.debug("order->list {} {} {} {} {}", aid, hid, oid, startTime, stopTime);
        AidHidOidTO aidHidOidTO = new AidHidOidTO(aid, hid, oid, startTime, stopTime, pageNum, pageSize);
        PageTO<OrderTO> pageTO = moduleWxService.getOrderList(aidHidOidTO);
        if(pageTO !=null){
            List<OrderBO> list = orderService.mergeOrderBO(pageTO.getPageList());
            if(list!=null) return ResultUtil.success(list, pageTO.getPageInfo());
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
