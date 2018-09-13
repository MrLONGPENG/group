package com.mujugroup.wx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.AidHidOidTO;
import com.lveqia.cloud.common.objeck.to.OrderTO;
import com.lveqia.cloud.common.objeck.to.PageTO;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.FeignService;
import com.mujugroup.wx.service.WxOrderService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final MapperFactory mapperFactory;
    private final WxOrderService wxOrderService;

    private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);


    public FeignServiceImpl(MapperFactory mapperFactory, WxOrderService wxOrderService) {
        this.mapperFactory = mapperFactory;
        this.wxOrderService = wxOrderService;
    }

    @Override
    public PageTO<OrderTO> getOrderList(AidHidOidTO dto) { // 分页查询
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<WxOrder> list = wxOrderService.findList(dto);
        mapperFactory.classMap(WxOrder.class, OrderTO.class).byDefault().register();
        return new PageTO<>(PageInfo.of(list), mapperFactory.getMapperFacade().mapAsList(list, OrderTO.class));
    }
}
