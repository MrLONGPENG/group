package com.mujugroup.data.controller;

import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.objeck.to.RequestTo;
import com.lveqia.cloud.common.objeck.to.OrderTo;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.objeck.bo.OrderBo;
import com.mujugroup.data.service.OrderService;
import com.mujugroup.data.service.StaVOService;
import com.mujugroup.data.service.feign.ModuleWxService;
import com.mujugroup.data.utils.ExcelData;
import com.mujugroup.data.utils.ExcelUtils;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/order")
@Api(description="数据模块订单统计接口")
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final StaVOService staVOService;
    private final ModuleWxService moduleWxService;
    @Autowired
    public OrderController(OrderService orderService, StaVOService staVOService, ModuleWxService moduleWxService) {
        this.orderService = orderService;
        this.staVOService = staVOService;
        this.moduleWxService = moduleWxService;
    }

    @ApiOperation(value="查询指定订单记录", notes="根据条件查询(代理商、医院、起止时间，以及单个订单查询)")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String info(@ApiParam(value="代理商ID") @RequestParam(name="aid", defaultValue = "0") String aid
            , @ApiParam(value="医院ID") @RequestParam(name="hid", required=false, defaultValue="0") String hid
            , @ApiParam(value="科室ID") @RequestParam(name="oid", required=false, defaultValue="0") String oid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="当前页")@RequestParam(name="pageNum", required=false, defaultValue="1")int pageNum
            , @ApiParam(value="每页显示")@RequestParam(name="pageSize", required=false, defaultValue="10")int pageSize
            , @ApiParam(value="订单类型 0:全部 1:晚休 2:午休，不填默认晚休") @RequestParam(name="orderType"
            , required=false, defaultValue="1") int orderType, @ApiParam(hidden = true) String uid){
        logger.debug("order->list {} {} {} {} {}", aid, hid, oid, startTime, stopTime);
        String[] ids;
        try {
            ids = staVOService.checkIds(uid, aid, hid, oid);
        } catch (DataException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
        RequestTo to = new RequestTo(ids[0], ids[1], ids[2], orderType, startTime, stopTime, pageNum, pageSize);
        PageTo<OrderTo> pageTO = moduleWxService.getOrderList(to);
        if(pageTO !=null){
            List<OrderBo> list = orderService.mergeOrderBO(pageTO.getPageList());
            if(list!=null) return ResultUtil.success(list, pageTO.getPageInfo());
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }


    @ApiOperation(value="导出指定订单记录", notes="根据条件查询(代理商、医院、起止时间，以及单个订单查询)")
    @RequestMapping(value = "/excel",method = RequestMethod.GET)
    public void excel(@ApiParam(value="代理商ID") @RequestParam(name="aid", defaultValue = "0") String aid
            , @ApiParam(value="医院ID") @RequestParam(name="hid", required=false, defaultValue="0") String hid
            , @ApiParam(value="科室ID") @RequestParam(name="oid", required=false, defaultValue="0") String oid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="订单类型 0:全部 1:晚休 2:午休，不填默认晚休") @RequestParam(name="orderType"
            , required=false, defaultValue="1") int orderType, @ApiParam(hidden = true) String uid
            , HttpServletResponse response) throws Exception {
        logger.debug("order->excel {} {} {} {} {}", aid, hid, oid, startTime, stopTime);
        String[] ids = staVOService.checkIds(uid, aid, hid, oid);
        RequestTo aidHidOidTO = new RequestTo(ids[0], ids[1], ids[2], orderType, startTime, stopTime);
        PageTo<OrderTo> pageTO = moduleWxService.getOrderList(aidHidOidTO);
        if(pageTO !=null){
            List<OrderBo> list = orderService.mergeOrderBO(pageTO.getPageList());
            ExcelData excelData = new ExcelData();
            excelData.setName("订单统计");
            excelData.setTitles(new String[]{ "订单时间", "订单号", "代理商", "医院", "科室", "病床"
                    , "二维码", "订单类型", "订单金额", "订单状态"});
            excelData.setRows(ExcelUtils.toRowsByOrderBO(list));
            ExcelUtils.exportExcel(response, StringUtil.getExcelName(startTime, stopTime), excelData);
        }else {
            logger.warn("no find order list data");
        }
    }

}
